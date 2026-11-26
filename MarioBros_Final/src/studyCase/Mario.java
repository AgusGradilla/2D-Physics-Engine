package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import game.Enemy;
import game.GameObject;
import game.Player; 
import game.PowerUP;

public class Mario extends Player {
    
    // Referencia especifica de los cambios que realice o se le realicen a Mario
    private MarioHUD hud;
    
    // Cosas para animacion
    protected Image spriteSheet; 
    protected final int SPRITE_WIDTH = 16;  
    protected final int SCALE = 2; 

    private final int HEIGHT_SMALL = 16;
    private final int HEIGHT_BIG = 32;

    protected int aniTick, aniIndex, aniSpeed = 10; 
    private int spriteOffset = 0; 
    private int spriteLimit = 1; 

    // Estados específicos de Mario
    public static final int MARIO_SMALL = 0;
    public static final int MARIO_BIG   = 1;
    public static final int MARIO_FIRE  = 2;

    private int currentPower = MARIO_SMALL;

    // Estados de la bandera
    private boolean onFlag = false;
    private int flagSpriteOffset = 5;
    private boolean restartPressed = false;

    public Mario(double x, double y, MarioHUD hud) {
        super(x, y, 16 * 2, 16 * 2);
        this.hud = hud;
        loadImg();
    }

    private void loadImg() {
        spriteSheet = Toolkit.getDefaultToolkit().getImage("src/images/spr_Mario.png");
    }
    
    public MarioHUD getHud() {
        return hud;
    }

    public boolean isBig() { 
    	return currentPower != MARIO_SMALL; 
    } 

    public void setOnFlag(boolean onFlag) {
        this.onFlag = onFlag;
        if (onFlag) {
            this.playerDir = 1;
            this.rb.velocidad.setX(0);
            this.rb.velocidad.setY(0);
            this.aniIndex = 0;
            this.aniTick = 0;
        } else {
            this.rb.isStatic = false;
        }
    }

    public void setRestartPressed(boolean restartPressed) {
        // Solo guarda la pulsación si Mario ya está en la bandera
        if (this.onFlag) {
            this.restartPressed = restartPressed;
        }
    }

    public boolean canRestartFromFlag() {
        return this.onFlag && this.restartPressed;
    }

    @Override
    public void kill() {
        if (dead) return;
        
        if (hud != null) hud.removeLife();

        setPowerUp(MARIO_SMALL);
        this.spriteOffset = 8; 
        this.spriteLimit = 1;
        this.aniIndex = 0;
        
        super.kill();
    }

    public void setPowerUp(int newPower) {
        if (this.currentPower == newPower) return;

        boolean wasSmall = (this.currentPower == MARIO_SMALL);
        boolean willBeSmall = (newPower == MARIO_SMALL);

        if (wasSmall != willBeSmall) {
            int newHeightOriginal = willBeSmall ? HEIGHT_SMALL : HEIGHT_BIG;
            int newHeightScaled = newHeightOriginal * SCALE;

            double currentHeight = col.getHeight();
            double diff = newHeightScaled - currentHeight;
            
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

    @Override
    public void onCollisionEnter(GameObject other) {
        if(dead) return;

        if (other instanceof PowerUP) {
             PowerUP p = (PowerUP) other;
             p.applyEffect(this);
             p.destroy();
        } else if (other instanceof Enemy) {
            Enemy enemigo = (Enemy) other;
            if (enemigo.isDead()) return;

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

    private void updateAnimationState() {
        int oldOffset = spriteOffset;

        if (onFlag) {
            if (Math.abs(rb.velocidad.getY()) > 0) {
                spriteOffset = flagSpriteOffset; 
                spriteLimit = 2; // Alterna entre los 2 sprites de bajada
                aniSpeed = 8;
            } else {
                spriteOffset = 0; // Sprite quieto al llegar al final
                spriteLimit = 1;
            }
        } else if (!rb.isGrounded) {
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
        if (!onFlag) {
            super.updateMovement(dt);
        }

        if (dead) return;

        updateAnimationState();
        updateAnimationTick();
    }

    @Override
    public void draw(Graphics g) {
        int x = (int) rb.posicion.getX();
        int y = (int) rb.posicion.getY();
        
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

        if (playerDir == -1) {
            g.drawImage(spriteSheet, x + w, y, x, y + h, srcX, srcY, srcW, srcH, null);
        } else {
            g.drawImage(spriteSheet, x, y, x + w, y + h, srcX, srcY, srcW, srcH, null);
        }
    }
}