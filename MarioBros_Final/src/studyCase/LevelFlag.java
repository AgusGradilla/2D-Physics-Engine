package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import game.GameObject;
import physicsEngine.Collider2D;
import physicsEngine.RigidBody;
import physicsEngine.Vector2D;

public class LevelFlag extends GameObject {

    private Image structureSheet;
    private double bottomY;
    private double flagY;
    private double mastHeight;
    private double width = 32;

    private boolean triggered = false;
    private boolean finished = false;
    private Mario marioRef;
    private MarioHUD hud;

    private final int POLE_SRC_X = 0,  POLE_SRC_Y = 32, POLE_SRC_W = 16, POLE_SRC_H = 168;
    private final int FLAG_SRC_X = 32, FLAG_SRC_Y = 32, FLAG_SRC_W = 16, FLAG_SRC_H = 16;

    public LevelFlag(double x, double y, double height, MarioHUD hud) {
        super();
        this.hud = hud;
        this.mastHeight = height;
        this.bottomY = y + height; // 416 (GROUND_Y)
        this.flagY = y + 18;       // Justo debajo de la esfera verde[cite: 12]

        this.rb = new RigidBody(x, y, 0);
        this.col = new Collider2D(this, this.rb, new Vector2D(0, 0), width, height);
        this.col.isTrigger = true;

        this.structureSheet = Toolkit.getDefaultToolkit().getImage("src/images/spr_structures.png");
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void onCollisionEnter(GameObject other) {
        if (!triggered && other instanceof Mario) {
            triggered = true;
            marioRef = (Mario) other;

            if (hud != null) {
                hud.pauseTimer();
                int remainingTime = (int) hud.getTime();
                hud.addScore(remainingTime * 50);
            }

            marioRef.setOnFlag(true);
            marioRef.getRigidBody().posicion.setX(this.rb.posicion.getX() - 16);
        }
    }

    @Override
    public void update(double dt) {
        if (triggered && !finished && marioRef != null) {
            double slideSpeed = 160.0;
            double marioHeight = marioRef.getCollider().getHeight();
            
            // El bloque café de la base mide 16px * 2 = 32px de alto; Mario y la bandera paran justo encima de él[cite: 12]
            double baseBlockHeight = 32.0;
            double targetMarioY = bottomY - baseBlockHeight - marioHeight;
            double targetFlagY = bottomY - baseBlockHeight - 32.0;

            marioRef.getRigidBody().isStatic = true;
            marioRef.getRigidBody().velocidad.setY(slideSpeed);

            double nextMarioY = marioRef.getRigidBody().posicion.getY() + (slideSpeed * dt);

            if (flagY < targetFlagY) {
                flagY += slideSpeed * dt;
                if (flagY > targetFlagY) flagY = targetFlagY;
            }

            if (nextMarioY >= targetMarioY) {
                nextMarioY = targetMarioY;
                finished = true;

                marioRef.getRigidBody().velocidad.setY(0);
                marioRef.getRigidBody().posicion.setX(this.rb.posicion.getX() + width);

                if (hud != null) {
                    hud.setShowRestartText(true);
                }
            }

            marioRef.getRigidBody().posicion.setY(nextMarioY);
        }
    }

    @Override
    public void draw(Graphics g) {
        int x = (int) rb.posicion.getX();
        int y = (int) rb.posicion.getY();

        // Mástil completo[cite: 12]
        g.drawImage(structureSheet,
                x, y, x + (int) width, y + (int) mastHeight,
                POLE_SRC_X, POLE_SRC_Y, POLE_SRC_X + POLE_SRC_W, POLE_SRC_Y + POLE_SRC_H, null);

        // Banderín desplazado 6px a la izquierda para no montarse sobre el tubo verde[cite: 12]
        g.drawImage(structureSheet,
                x - 22, (int) flagY, x + 10, (int) flagY + 32,
                FLAG_SRC_X, FLAG_SRC_Y, FLAG_SRC_X + FLAG_SRC_W, FLAG_SRC_Y + FLAG_SRC_H, null);
    }
}