package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import game.Player;
import game.PowerUP;

public class Mushroom extends PowerUP {

    private Image img;
    private final static int SIZE = 16;
    private final static int SCALE = 2;

    public Mushroom(double x, double y) {
        super(x, y, SIZE * SCALE, SIZE * SCALE);
        loadImg();
//      this.moveSpeed = 100.0f; Si se quisiera cambiar, pero en la velocidad default de PowerUP esta bien
        this.isMoving = true;
    }

    private void loadImg() {
        img = Toolkit.getDefaultToolkit().getImage("src/images/spr_Mushroom.png");
    }

    @Override
    public void applyEffect(Player p) {
    	if (p instanceof Mario) { //Al recibir una clase generica necesitamos saber que SI es Mario
            Mario m = (Mario) p;
            m.setPowerUp(Mario.MARIO_BIG); 
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!active) return;
        g.drawImage(img, (int)rb.posicion.getX(), (int)rb.posicion.getY(), 
                    (int)col.getWidth(), (int)col.getHeight(), null);
    }
}