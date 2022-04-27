import java.awt.*;
import java.awt.event.*;

import javax.swing.text.AttributeSet.ColorAttribute;

class LeftBox extends MovingObject {

    int time;
    LeftBox(){
        x = 0;
        w = h = 20;
        dx = 1;
        dy = 0;
        hp = 0;
    }

    void move(Graphics buf, int apWidth, int apHeight, int ftry, int emyy) {
        if (hp > 0) {
            if(hp==3){
                Image img = getToolkit().getImage("3box.jpg");
                buf.drawImage(img,x - w, y - h, w*2, h*2,this);
            }
            else if(hp==2){
                Image img = getToolkit().getImage("2box.jpg");
                buf.drawImage(img,x - w, y - h, w*2, h*2,this);
            }
            else if(hp==1){
                Image img = getToolkit().getImage("1box.jpg");
                buf.drawImage(img,x - w, y - h, w*2, h*2,this);
            }
                x = x + dx;
            if (x < 0 || x > apWidth) hp = 0;
        }
    }
    void revive(int w, int h ){}
    void revive(int x, int y, int type , int dx) {
        time+=1;
            this.x=0;
            this.y=y;
            dx=1;
            hp=3;
            w = h = 20;
        }
}