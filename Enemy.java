import java.awt.*;

class Enemy extends MovingObject {
    boolean lflag;
    boolean rflag;
    boolean flag_z;
    boolean flag_c;  
    boolean sflag;
    boolean flag_a;
    boolean flag_q;
    boolean flag_1;
    int delaytime;
    
    Enemy(int apWidth, int apHeight) {
        x = (int)(apWidth/2);
        y = (int)(apHeight*0.16);
        dx = 2;
        dy = 5;
        w = 15;
        h = 15;
        lflag = false;
        rflag = false;
        flag_z = false;
        flag_c = false;
        sflag = false;
        flag_a = false;
        delaytime = 5;
    }

    void revive(int apWidth, int apHeight) {}
    void revive(int w, int h , int type , int dx){}

    void move(Graphics buf, int apWidth, int apHeight, int ftry, int emyy) {

        Image img = getToolkit().getImage("kaeruのコピー.png");
        buf.drawImage(img,x-w, y-h, w*2, h*2,this);
        if (flag_z && !flag_c && x > w)            x = x - dx;
        if (flag_c && !flag_z && x < apWidth - w)  x = x + dx; 
    }
}
