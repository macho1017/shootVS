import java.awt.*;
import java.awt.event.*;

class EnemyBullet extends MovingObject {

    int type;
    int time;
    EnemyBullet(){
        w = h = 6;
        dx = 0;
        dy = 3;
        hp = 0;
    }

    void move(Graphics buf, int apWidth, int apHeight, int ftry, int emyy) {
        if (hp > 0) {
            if(type==1){
                w = h = 6;
                dx = 0;
                buf.setColor(Color.red);
            }
            else if(type==2){
                w = h = 12;
                buf.setColor(Color.red);
            }
            else if(type==3){
                time += 1;
                dx = 0;
                buf.setColor(Color.red);
                if(y >= (ftry - emyy)/2 + emyy){
                    if(time%2 == 1 && w <=90 && h <= 90){
                        w+=4;
                        h+=4;
                    }
                }
            }
            buf.fillRect(x - w, y - h, 2*w, 2*h);
            
            if (y > 0 && y < apHeight && x > 0 && x < apWidth){
                x = x + dx;
                y = y + dy;
            } 
            else hp = 0;
        }
    }
    void revive(int w, int h ){}
    void revive(int x, int y, int type , int dx) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        hp=1;
        this.type=type;
        w = h = 6;
        }
}