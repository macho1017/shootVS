import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.DelayQueue;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameMaster extends Canvas implements KeyListener {
    // フィールド変数
    Image buf;
    Graphics buf_gc;
    Dimension d;
    Font fg = new Font("SansSerif", Font.PLAIN, 20);
    Font fm = new Font("SansSerif", Font.PLAIN, 60);
    private int imgW, imgH;

    private int ftrBltNum = 20;
    private int emyBltNum = 20;
    private int mode = -1;
    private int i, j;
    private boolean decision ;
    private int ftcost;
    private int emcost;
    private int type;
    private int time;
    int count;
    int count2 = 2;
    int count3;
    boolean ftshot;
    boolean emshot;
    Clip clip1;
    Clip clip2;
    Clip clip3;
    Clip clip4;
    
    Fighter ftr;
    Enemy emy;
    FighterBullet ftrBlt[] = new FighterBullet[ftrBltNum];
    EnemyBullet emyBlt[] = new EnemyBullet[emyBltNum];
    LeftBox lbox;
    RightBox rbox;

    // コンストラクタ
    GameMaster(int imgW, int imgH) {
        this.imgW = imgW;
        this.imgH = imgH;
        setSize(imgW, imgH);

        addKeyListener(this);

        ftr = new Fighter(imgW, imgH);
        emy = new Enemy(imgW,imgH);

        lbox = new LeftBox();
        rbox = new RightBox();

        for (i = 0; i < ftrBltNum; i++) {
            ftrBlt[i] = new FighterBullet();
        }
        for (i = 0; i < emyBltNum; i++) {
            emyBlt[i] = new EnemyBullet();
        }
        
    }

    // メソッド
    public void addNotify() {
        super.addNotify();
        buf = createImage(imgW, imgH);
        buf_gc = buf.getGraphics();
    }

    public void paint(Graphics g) {
        buf_gc.setColor(Color.black);
        buf_gc.fillRect(0, 0, imgW, imgH);
        buf_gc.setFont(fg);
        switch (mode) {
            case -2:
                if(ftr.hp == 0 || emy.hp == 0){
                clip1.stop();
                clip1.flush();
                clip1.setFramePosition(0);
                }
                if(count == 30){
                clip2 = Audio.createClip(new File("victorysound.wav"));
                clip2.loop(Clip.LOOP_CONTINUOUSLY);
                }

                buf_gc.setColor(Color.red);
                if(emy.hp==0&&ftr.hp>0){
                    buf_gc.drawString("   1P WIN!   ", imgW / 2 - 50, imgH / 2 - 20);
                }
                if(ftr.hp==0&&emy.hp>0){
                    buf_gc.drawString("   2P WIN!   ", imgW / 2 - 50, imgH / 2 - 20);
                }
                if(emy.hp==0&&ftr.hp==0){
                    buf_gc.drawString("   DRAW   ",imgW/2-46,imgH/2-20);
                }
                buf_gc.drawString("       Hit SPACE Key       ", imgW / 2 - 105, imgH / 2 + 20);
                for (j = 0; j < ftrBltNum; j++){
                    ftrBlt[j].hp = 0;
                }
                for (j = 0; j < emyBltNum; j++){
                    emyBlt[j].hp = 0;
                }
                count+=1;
                break;
            case -1:
                count = 0;
                time = 0;
                ftr.x = (int)(imgW/2);
                emy.x = (int)(imgW/2);
                emy.y = (int)(imgH*0.16);
                ftr.y = (int)(imgH*0.845);
                ftcost = 0;
                emcost = 0;
                ftr.hp = 3;
                emy.hp = 3;
                lbox.hp = 0;
                rbox.hp = 0;
                buf_gc.setColor(Color.red);
                buf_gc.setFont(fm);
                buf_gc.drawString("1P", imgW / 2 - 20 , 720);
                buf_gc.drawString("2P", imgW / 2 - 20 , 90);
                buf_gc.setColor(Color.blue);
                buf_gc.setFont(fg);
                buf_gc.drawString("   == StarShoot VS == ", imgW / 2 - 107, imgH / 2 - 20);
                buf_gc.drawString("Hit SPACE Bar to start game", imgW / 2 - 122, imgH / 2 + 20);
                if(count2 == 1){
                    clip2.stop();
                    clip2.flush();
                    clip2.setFramePosition(0);
                }
                if(count2 == 2){
                    clip3 = Audio.createClip(new File("startsound.wav"));
                    clip3.loop(Clip.LOOP_CONTINUOUSLY);
                }
                count2 += 1;
                break;
            default:
                count2 = 0;
                if(time==1){
                clip1 = Audio.createClip(new File("starshoot.wav"));
                FloatControl ctrl = (FloatControl)clip1.getControl(FloatControl.Type.MASTER_GAIN);
                ctrl.setValue((float)Math.log10((float)50 / 100)*20);
                clip1.loop(Clip.LOOP_CONTINUOUSLY);
                }
                if(time == 1){
                    clip3.stop();
                    clip3.flush();
                    clip3.setFramePosition(0);
                }
                
                Image img = getToolkit().getImage("background.jpg");
                buf_gc.drawImage(img, 0, imgH-770,imgW, 770, this);
                /*
                buf_gc.setColor(Color.red);
                buf_gc.setFont(fg);
                buf_gc.drawString(ftr.y-emy.y+"a", imgW / 2 - 20 ,imgH/2);
                /*
                if(ftr.hp==3){
                    buf_gc.setColor(Color.green);
                    buf_gc.drawString(" ● ● ● ", imgW / 20, 500);
                }
                if(ftr.hp==2){
                    buf_gc.setColor(Color.orange);
                    buf_gc.drawString(" ● ● ", imgW / 20, 500);
                }
                if(ftr.hp==1){
                    buf_gc.setColor(Color.red);
                    buf_gc.drawString(" ● ", imgW / 20, 500);
                }
                */
                buf_gc.setColor(new Color(255, 255, 255));
                buf_gc.drawString(ftcost + " / " + 10, 555, 680);
                buf_gc.setColor(new Color(0, 0, 100, 150));
                buf_gc.fillRect(1, 685, 637, 15);
                buf_gc.setColor(new Color( 0, 50, 220));
                double htcostParcentage = 637 * ftcost / 10;
                buf_gc.fillRect(1, 685, (int)htcostParcentage, 15);

                buf_gc.setColor(new Color(255, 255, 255));
                buf_gc.drawString(emcost + " / " + 10, 0, 105);
                buf_gc.setColor(new Color(100, 0, 0, 150));
                buf_gc.fillRect(1, 70, 637, 15);
                buf_gc.setColor(new Color( 220, 50, 0));
                double emcostParcentage = 637 * emcost / 10;
                buf_gc.fillRect(1, 70, (int)emcostParcentage, 15);

                if ((ftr.flag_l == true||ftr.flag_k == true||ftr.flag_j == true)&&ftr.delaytime==0) {
                    for (i = 0; i < ftrBltNum; i++) {
                        if (ftrBlt[i].hp == 0) {
                            if(ftr.flag_l){
                                ftcost-=1;
                                if(ftcost>=0){    
                                    type=1;
                                    ftshot = true;  
                                }
                                else {
                                    ftshot = false;
                                    ftcost+=1;
                                }
                            }
                            if(ftr.flag_k){
                                ftcost-=2;
                                if(ftcost>=0){     
                                    type=2; 
                                    ftshot = true;    
                                }
                                else {
                                    ftshot = false;
                                    ftcost+=2;
                                }
                            }
                            if(ftr.flag_j){
                                ftcost-=6;
                                if(ftcost>=0){     
                                    type=3;  
                                    ftshot = true;
                                }
                                else {
                                    ftshot = false;
                                    ftcost+=6;
                                }
                            }
                            if (ftshot) {
                                ftr.delaytime = 15;
                                ftrBlt[i].revive(ftr.x, ftr.y, type, 1);
                                clip4 = Audio.createClip(new File("gunsound.wav"));
		                        clip4.start();
                                if(ftr.flag_k){
                                    ftr.delaytime = 15;
                                    ftrBlt[i+1].revive(ftr.x , ftr.y , type, -1);
                                }
                            }
                            
                            break;
                        }
                    }
                    ftr.flag_l = false;
                    ftr.flag_k = false;
                    ftr.flag_j = false;
                }
                else if (ftr.delaytime > 0) {
                    ftr.delaytime--;
                }
                if ((emy.flag_a == true||emy.flag_q == true||emy.flag_1)&&emy.delaytime==0) {
                    for (i = 0; i < emyBltNum; i++) {
                        if (emyBlt[i].hp == 0) {
                            if(emy.flag_a){
                                emcost-=1;
                                if(emcost>=0){    
                                    type=1;
                                    emshot = true;  
                                }
                                else {
                                    emshot = false;
                                    emcost+=1;
                                }
                            }
                            if(emy.flag_q){
                                emcost-=2;
                                if(emcost>=0){     
                                    type=2; 
                                    emshot = true;    
                                }
                                else {
                                    emshot = false;
                                    emcost+=2;
                                }
                            }
                            if(emy.flag_1){
                                emcost-=6;
                                if(emcost>=0){     
                                    type=3;  
                                    emshot = true;
                                }
                                else {
                                    emshot = false;
                                    emcost+=6;
                                }
                            }
                            
                            if(emshot){
                                emy.delaytime = 15;
                                emyBlt[i].revive(emy.x, emy.y,type , 1);
                                Clip clip = Audio.createClip(new File("gunsound.wav"));
		                        clip.start();
                                if(emy.flag_q){
                                    emyBlt[i+1].revive(emy.x, emy.y,type , -1);
                                }
                            }
                            break;
                        }
                    }
                    emy.flag_a = false;
                    emy.flag_q = false;
                    emy.flag_1 = false;
                }
                else if (emy.delaytime > 0) {
                    emy.delaytime--;
                }
                
                if(lbox.hp==0){
                    lbox.x-=1000;
                    if(Math.random()>0.9985&&ftr.y-emy.y>250)
                    lbox.revive(emy.x,(ftr.y-emy.y)/2+emy.y + (int)(Math.random()*70-35),type,1);
                }
                if(rbox.hp==0){
                    rbox.x-=1000;
                    if(Math.random()<0.0015&&ftr.y-emy.y>250)
                    rbox.revive(emy.x,(ftr.y-emy.y)/2+emy.y + (int)(Math.random()*70-35),type,-1);
                }
                
                for (j = 0; j < ftrBltNum; j++) {
                    if (ftrBlt[j].hp > 0) {
                        decision = ftrBlt[j].collisionCheck(emy);
                        if(decision){
                            Clip clip = Audio.createClip(new File("collisionsound.wav"));
		                    clip.start();
                        }
                    }
                }
                for (j = 0; j < ftrBltNum; j++) {
                    if (ftrBlt[j].hp > 0) {
                        decision = ftrBlt[j].collisionCheck(lbox);
                        if(decision){
                            if(lbox.hp==2){
                                Clip clip = Audio.createClip(new File("3boxsound.wav"));
		                        clip.start();
                            }
                            else if(lbox.hp==1){
                                Clip clip = Audio.createClip(new File("2boxsound.wav"));
		                        clip.start();
                            }
                            else if(lbox.hp==0){
                                Clip clip = Audio.createClip(new File("1boxsound.wav"));
		                        clip.start();
                            }
                        }
                    }
                }
                for (j = 0; j < emyBltNum; j++) {
                    if (emyBlt[j].hp > 0) {
                        decision = emyBlt[j].collisionCheck(lbox);
                        if(decision){
                            if(lbox.hp==2){
                                Clip clip = Audio.createClip(new File("3boxsound.wav"));
		                        clip.start();
                            }
                            else if(lbox.hp==1){
                                Clip clip = Audio.createClip(new File("2boxsound.wav"));
		                        clip.start();
                            }
                            else if(lbox.hp==0){
                                Clip clip = Audio.createClip(new File("1boxsound.wav"));
		                        clip.start();
                            }
                        }
                    }
                }
                
                for (j = 0; j < emyBltNum; j++) {
                    if (emyBlt[j].hp > 0) {
                        decision = emyBlt[j].collisionCheck(ftr);
                        if(decision){
                            Clip clip = Audio.createClip(new File("collisionsound.wav"));
		                    clip.start();
                        }
                    }
                }
                for (j = 0; j < ftrBltNum; j++) {
                    if (ftrBlt[j].hp > 0) {
                        decision = ftrBlt[j].collisionCheck(rbox);
                        if(decision){
                            if(rbox.hp==2){
                                Clip clip = Audio.createClip(new File("3boxsound.wav"));
		                        clip.start();
                            }
                            else if(rbox.hp==1){
                                Clip clip = Audio.createClip(new File("2boxsound.wav"));
		                        clip.start();
                            }
                            else if(rbox.hp==0){
                                Clip clip = Audio.createClip(new File("1boxsound.wav"));
		                        clip.start();
                            }
                        }
                    }
                }
                for (j = 0; j < emyBltNum; j++) {
                    if (emyBlt[j].hp > 0) {
                        decision = emyBlt[j].collisionCheck(rbox);
                        if(decision){
                            if(rbox.hp==2){
                                Clip clip = Audio.createClip(new File("3boxsound.wav"));
		                        clip.start();
                            }
                            else if(rbox.hp==1){
                                Clip clip = Audio.createClip(new File("2boxsound.wav"));
		                        clip.start();
                            }
                            else if(rbox.hp==0){
                                Clip clip = Audio.createClip(new File("1boxsound.wav"));
		                        clip.start();
                            }
                        }
                    }
                }
            
                
                if (ftr.hp < 1||emy.hp < 1)
                    mode = -2;

                for (i = 0; i < ftrBltNum; i++)
                    ftrBlt[i].move(buf_gc, imgW, imgH ,ftr.y, emy.y);
                
                for (i = 0; i < emyBltNum; i++)
                    emyBlt[i].move(buf_gc, imgW, imgH, ftr.y, emy.y);
                
                buf_gc.setColor(Color.red);
                for (i = 0; i < ftr.hp; i++)  buf_gc.drawString("❤",10 + i*20, imgH - 90);
                for (i = 0; i < emy.hp; i++)  buf_gc.drawString("❤",570 + i*20, imgH - 665);        
    
                ftr.move(buf_gc, imgW, imgH, ftr.y, emy.y);

                emy.move(buf_gc,imgW,imgH, ftr.y, emy.y);

                lbox.move(buf_gc,imgW,imgH,ftr.y,emy.y);

                rbox.move(buf_gc,imgW,imgH,ftr.y,emy.y);

                buf_gc.setColor(Color.white);
                /*
                buf_gc.drawString("SCORE="+countmax,imgW / 20, 20);
                */
                
                time+=1;
                if(time%60==59){
                    if(ftcost<=9){
                        ftcost+=1;
                    }
                }
                if(time%60==59){
                    if(emcost<=9){
                        emcost+=1;
                    }
                }
                if(time>1000&&time%40==39){
                    if(ftr.y-emy.y>200){
                    ftr.y-=1;
                    emy.y+=1;
                    }
                }
        }
        
        g.drawImage(buf, 0, 0, this);
        /*
        time+=1;
        if(time%40==39){
            if(ftcost<=9){
                ftcost+=1;
            }
        }
        if(time%40==39){
            if(emcost<=9){
                emcost+=1;
            }
        }
        if(time>1000&&time%80==79){
            if(ftr.y-emy.y>100){
            ftr.y-=1;
            emy.y+=1;
            }
        }*/
    }
    

    public void update(Graphics gc) {
        paint(gc);
    }

    public void keyTyped(KeyEvent ke) {
        /*int cd = ke.getKeyCode();
        switch (cd) {
            case KeyEvent.VK_L:
                ftr.flag_l = true;
                break;
        }*/
    }

    public void keyPressed(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch (cd) {
            case KeyEvent.VK_LEFT:
                ftr.lflag = true;
                break;
            case KeyEvent.VK_C:
                emy.flag_c = true;
                break;
            case KeyEvent.VK_RIGHT:
                ftr.rflag = true;
                break;
            case KeyEvent.VK_Z:
                emy.flag_z = true;
                break;
            case KeyEvent.VK_SPACE:
                ftr.sflag = true;
                if (this.mode == -1) {
                    this.mode++;
                }
                if (this.mode == -2) {
                    this.mode++;
                }
                break;
            /*
            case KeyEvent.VK_A:
                emy.flag_a = true;
                break;
                
            case KeyEvent.VK_L:
                ftr.flag_l = true;
                break;
                */

        }
    }

    public void keyReleased(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch (cd) {
            case KeyEvent.VK_LEFT:
                ftr.lflag = false;
                break;
            case KeyEvent.VK_C:
                emy.flag_c = false;
                break;
            case KeyEvent.VK_RIGHT:
                ftr.rflag = false;
                break;
            case KeyEvent.VK_Z:
                emy.flag_z = false;
                break;  
            case KeyEvent.VK_SPACE:
                ftr.sflag = false;
                break;
            case KeyEvent.VK_A:
                emy.flag_a = true;
                break;
            case KeyEvent.VK_L:
                ftr.flag_l = true;
                break;
            case KeyEvent.VK_K:
                ftr.flag_k = true;
                break;
            case KeyEvent.VK_J:
                ftr.flag_j = true;
                break;
            case KeyEvent.VK_Q:
                emy.flag_q = true;
                break;
            case KeyEvent.VK_1:
                emy.flag_1 = true;
                break;
        }
    }
}
