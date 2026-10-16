package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import game.Enemy;
import game.GameObject;
import game.Player; 
import game.PowerUP;

public class Mario extends Player {
    
//	Referencia especifica de los cambios que realice o se le realicen a Mario
    private MarioHUD hud;
    
// 	Cosas para animacion
    protected Image spriteSheet; 
    protected final int SPRITE_WIDTH = 16;  
    protected final int SCALE = 2; 

    private final int HEIGHT_SMALL = 16;
    private final int HEIGHT_BIG = 32;

    protected int aniTick, aniIndex, aniSpeed = 10; 
    private int spriteOffset = 0; 
    private int spriteLimit = 1; 

//	Estados específicos de Mario
    // Podria ser un enum a parte
    public static final int MARIO_SMALL = 0;
    public static final int MARIO_BIG   = 1;
    public static final int MARIO_FIRE  = 2;

    private int currentPower = MARIO_SMALL;

    public Mario(double x, double y, MarioHUD hud) {
        super(x, y, 16 * 2, 16 * 2); // Llamar al constructor del padre con el tamaño inicial
        this.hud = hud;
        loadImg();
    }

//	En teoria, el desarrollador cambiaria el path a lo que sea que el tenga
    private void loadImg() {
        spriteSheet = Toolkit.getDefaultToolkit().getImage("src/images/spr_Mario.png");
    }
    
    public boolean isBig() { 
    	return currentPower != MARIO_SMALL; 
    } 

//	Se sobreescribe kill para agregar logica visual especifica de Mario
    @Override
    public void kill() {
        if (dead) return;
        
        if (hud != null) hud.removeLife(); //Actualizar vidas

        setPowerUp(MARIO_SMALL); // Mario siempre muere en su forma pequeña
        this.spriteOffset = 8; // Sprite de muerte
        this.spriteLimit = 1;
        this.aniIndex = 0;
        
        super.kill(); // Llamar a la lógica física del padre (salto, trigger, booleano dead)
    }

//	Como cambia el estado de Mario
    public void setPowerUp(int newPower) {
        if (this.currentPower == newPower) return;

        boolean wasSmall = (this.currentPower == MARIO_SMALL);
        boolean willBeSmall = (newPower == MARIO_SMALL);

        // Ajustar tamaño del collider si cambiamos de tamaño
        if (wasSmall != willBeSmall) {
            int newHeightOriginal = willBeSmall ? HEIGHT_SMALL : HEIGHT_BIG;
            int newHeightScaled = newHeightOriginal * SCALE;

            double currentHeight = col.getHeight();
            double diff = newHeightScaled - currentHeight;
            
            // Ajustamos posición Y para que crezca hacia arriba
            this.rb.posicion.setY(this.rb.posicion.getY() - diff);
            this.col.setSize(SPRITE_WIDTH * SCALE, newHeightScaled);
        }

        this.currentPower = newPower;
    }
    
    public void touchCoin() {
        if (hud != null) {
            hud.addCoin();
        }
    }

    @Override
    public void takeDamage() {
        if (invincible) return;
        
        if (currentPower == MARIO_SMALL) {
            kill();
        } else {
            setPowerUp(MARIO_SMALL);
            invincible = true;
            invincibilityTimer = INVINCIBILITY_TIME;
        }
    }

//	Resolucion de colisiones en cuanto a logica, para resoluciones fisicas ver RulerOfEverything    
    @Override
    public void onCollisionEnter(GameObject other) {
        if(dead) return;

        if (other instanceof PowerUP) {
             PowerUP p = (PowerUP) other;
             p.applyEffect(this); // PowerUP necesita saber que es un Player
             p.destroy();
        } else if (other instanceof Enemy) {
            Enemy enemigo = (Enemy) other;
            if (enemigo.isDead()) return;

            //Si se activa la colision desde arriba del enemigo, y desde abajo de Mario, es que lo piso.
            boolean isFalling = this.rb.velocidad.getY() > 0;
            boolean isAbove = this.col.getBottom() < (enemigo.getCollider().getTop() + 20);

            if (isFalling && isAbove) {
                enemigo.getSquashed(); 
                this.rb.velocidad.setY(-450); 
                this.rb.isGrounded = false;
            } else {
                takeDamage();
            }
        }
    }

//	Animaciones
    private void updateAnimationState() {
        int oldOffset = spriteOffset;

        if (!rb.isGrounded) {
            spriteOffset = 7; 
            spriteLimit = 1; 
        } else if (Math.abs(rb.velocidad.getX()) > 10) { 
            spriteOffset = 1;
            spriteLimit = 3;
            if (run) aniSpeed = 7; else aniSpeed = 10; 
            if ((right && rb.velocidad.getX() < 0) || (left && rb.velocidad.getX() > 0)) {
                spriteOffset = 4; spriteLimit = 1;
            }
        } else {
            spriteOffset = 0; spriteLimit = 1;
        }

        if (oldOffset != spriteOffset) {
            aniIndex = 0; aniTick = 0;
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= spriteLimit) aniIndex = 0;
        }
    }
    
    @Override
    public void update(double dt) {
        super.updateMovement(dt); //El movimiento ya esta definido en el padre

        if (dead) return;

        // Animaciones especificas de Mario
        updateAnimationState();
        updateAnimationTick();
    }

    @Override
    public void draw(Graphics g) {
        int x = (int) rb.posicion.getX();
        int y = (int) rb.posicion.getY();
        
        // Parpadeo para mostrar graficamente el periodo de invencibilidad
        if (invincible) { 
            if ((int)(invincibilityTimer * 20) % 2 == 0) return; 
        }

        int currentSpriteHeight = (currentPower == MARIO_SMALL) ? HEIGHT_SMALL : HEIGHT_BIG;
        int w = SPRITE_WIDTH * SCALE;
        int h = currentSpriteHeight * SCALE;

        int srcX = (spriteOffset + aniIndex) * SPRITE_WIDTH;
        int srcY = currentPower * 32; 
        if (currentPower == MARIO_SMALL) srcY += 16; 
        
        int srcW = srcX + SPRITE_WIDTH;
        int srcH = srcY + currentSpriteHeight;

        // Dependiendo de la direccion es si se dibuja en espejo los sprites o no
        if (playerDir == -1) {
            g.drawImage(spriteSheet, x + w, y, x, y + h, srcX, srcY, srcW, srcH, null);
        } else {
            g.drawImage(spriteSheet, x, y, x + w, y + h, srcX, srcY, srcW, srcH, null);
        }
    }
}