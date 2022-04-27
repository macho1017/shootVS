import java.awt.*;

class Fighter extends MovingObject {
    boolean lflag;
    boolean rflag;
    boolean uflag;
    boolean dflag;  
    boolean sflag;
    boolean flag_a;
    boolean flag_l;
    boolean flag_k;
    boolean flag_j;
    int delaytime;
    
    Fighter(int apWidth, int apHeight) {
        x = (int)(apWidth/2);
        y = (int)(apHeight*0.845);
        dx = 2;
        dy = 5;
        w = 15;
        h = 15;
        lflag = false;
        rflag = false;
        sflag = false;
        flag_l = false;
        flag_k = false;
        flag_j = false;
        delaytime = 5;
    }

    void revive(int apWidth, int apHeight) {}
    void revive(int w, int h , int type , int dx){}

    void move(Graphics buf, int apWidth, int apHeight, int ftry, int emyy) {

        Image img = getToolkit().getImage("kaeru.png");
        buf.drawImage(img,x - w, y - h, w*2, h*2,this);
        if (lflag && !rflag && x > w)            x = x - dx;
        if (rflag && !lflag && x < apWidth - w)  x = x + dx; 
    }
}