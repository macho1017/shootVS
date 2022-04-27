import java.awt.*;

public class ShootingGame2 extends Frame implements Runnable {
    // フィールド変数
    Thread th;
    GameMaster gm;

    // mainメソッド
    /*
    public static void main(String[] args) {
        new ShootingGame2();
    } 
    */

public void setGame(GameMaster gm){
    this.gm = gm;
}

    ShootingGame2() {
        super("Star Shoot VS");
        int cW = 800, cH = 770;
        this.setSize(cW+30, cH+40);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
/*
        //gm = new GameMaster(cW, cH);
        this.add(gm);
        this.setVisible(true);

        th = new Thread(this);
        th.start();

        requestFocusInWindow();
        */
    }

    public void game2(){

        //gm = new GameMaster(cW, cH);
        this.add(gm);
        this.setVisible(true);

        th = new Thread(this);
        th.start();

        requestFocusInWindow();
    }
    public void run() {
        try {
            while(true) {
                Thread.sleep(20);
                gm.repaint();
            }
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}