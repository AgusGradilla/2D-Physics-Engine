package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import game.Enemy;
import game.Player;

public class Goomba extends Enemy {

    private Image img;
    private int aniTick = 0;
    private int aniSpeed = 15; 
    private boolean stepFlip = false; 

    public Goomba(double x, double y, Player player) {
        super(x, y, 32, 32, player); 
        this.speed = 100.0f; 
        loadImg();
    }

    private void loadImg() {
        img = Toolkit.getDefaultToolkit().getImage("src/images/spr_Goomba.png");
    }

    @Override
    public void update(double dt) {
        super.update(dt); 

        if (!isDead) {
            aniTick++;
            if (aniTick >= aniSpeed) {
                aniTick = 0;
                stepFlip = !stepFlip; 
            }
        } else {
            aniTick++;
            if (aniTick > 60) { 
                this.destroy();
            }
        }
    }

    @Override
    public void getSquashed() {
        isDead = true;
        rb.velocidad.setX(0);
        rb.isStatic = true; 
        col.isTrigger = true; 
        aniTick = 0;
    }

    @Override
    public void draw(Graphics g) {
        int x = (int)rb.posicion.getX();
        int y = (int)rb.posicion.getY();
        int w = (int)col.getWidth();
        int h = (int)col.getHeight();

        if (isDead) {
            g.drawImage(img, x, y + (h/2), w, h/2, null);
        } else {
            if (stepFlip) {
                g.drawImage(img, x, y, w, h, null);
            } else {
                g.drawImage(img, x + w, y, -w, h, null);
            }
        }
    }
}