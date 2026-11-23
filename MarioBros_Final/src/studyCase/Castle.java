package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import game.GameObject;
import physicsEngine.Collider2D;
import physicsEngine.RigidBody;
import physicsEngine.Vector2D;

public class Castle extends GameObject {

    private final Image structureSheet =
        Toolkit.getDefaultToolkit().getImage("src/images/spr_structures.png");

    private final int SRC_X = 30;
    private final int SRC_Y = 120;
    private final int SRC_W = 80;
    private final int SRC_H = 80;

    private final int CASTLE_WIDTH = 157;
    private final int CASTLE_HEIGHT = 160;


    public Castle(double x, double y) {
        super();

        this.rb = new RigidBody(x, y, 0);
        this.col = new Collider2D(
            this,
            rb,
            new Vector2D(0, 0),
            CASTLE_WIDTH,
            CASTLE_HEIGHT
        );
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void draw(Graphics g) {
        int x = (int) rb.posicion.getX();
        int y = (int) rb.posicion.getY();

        g.drawImage(
            structureSheet,
            x, y,
            x + CASTLE_WIDTH,
            y + CASTLE_HEIGHT,
            SRC_X,
            SRC_Y,
            SRC_X + SRC_W,
            SRC_Y + SRC_H,
            null
        );
    }

    @Override
    public void onCollisionEnter(GameObject other) {
    }
}