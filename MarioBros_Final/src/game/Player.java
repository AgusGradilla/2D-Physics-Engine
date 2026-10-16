package game;

import physicsEngine.*;

public abstract class Player extends GameObject {

//	Inputs
    protected boolean left, right, jump, run;

//  Estados genericos
    protected boolean dead = false;
    protected boolean invincible = false;
    protected double invincibilityTimer = 0;
    protected final double INVINCIBILITY_TIME = 1.5;
    
//	Datos del juego
    protected int lives = 3;

// Fisicas default de un personaje cualquiera
// Cabe recalcar que son protected para que puedan ser accesadas segun la necesidad del personaje que se cree con esta clase abstracta
    protected double aceleracion = 2000f;
    protected double velocidadMax = 300f;
    protected double aceleracionRun = 3000f;
    protected double velocidadMaxRun = 400f;
    protected double friccion = 1800f; 
    protected double fuerzaSalto = -620.0f; 
    protected double corteSalto = 0.75f; 
    
    protected int playerDir = 1; // 1 derecha, -1 izquierda

    public Player(double x, double y, int width, int height) {
        this.rb = new RigidBody(x, y, 1.0f); 
        this.col = new Collider2D(this, rb, new Vector2D(), width, height); 
    }

//	Setters para los inputs
    public void setLeft(boolean b) { this.left = b; }
    public void setRight(boolean b) { this.right = b; }
    public void setJump(boolean b) { this.jump = b; }
    public void setRun(boolean b) { this.run = b; }

//	Getters para estados y otros datos
    public boolean isDead() { return dead; }
    public boolean isInvincible() { return invincible; }
    public int getLives() { return lives; }
    public int getDirection() { return playerDir; }

//	Logica de movimiento, deberia ser similar en varios juegos
    protected void updateMovement(double dt) {
        if (dead) return;

        // Periodo de invencibilidad tras ser dañado
        if (invincible) {
            invincibilityTimer -= dt;
            if (invincibilityTimer <= 0) {
                invincible = false;
            }
        }

        // Velocidad horizontal
        double velX = rb.velocidad.getX();
        double acelActual = run ? aceleracionRun : aceleracion;
        double velMaxActual = run ? velocidadMaxRun : velocidadMax;

        if (left && !right) {
            velX -= acelActual * dt;
            playerDir = -1;
        } else if (right && !left) {
            velX += acelActual * dt;
            playerDir = 1;
        } else {
            // Friccion 
            if (velX > 0) {
                velX -= friccion * dt;
                if (velX < 0) velX = 0;
            } else if (velX < 0) {
                velX += friccion * dt;
                if (velX > 0) velX = 0;
            }
        }

        // Velocidad maxima que puede alcanzar
        if (velX > velMaxActual) velX = velMaxActual;
        if (velX < -velMaxActual) velX = -velMaxActual;
        
        rb.velocidad.setX(velX);

        // Salto
        if (jump && rb.isGrounded) { 
            rb.applyImpulse(new Vector2D(0, fuerzaSalto)); //applyImpulse tambien podria ser usado para un dash
            rb.isGrounded = false;
        }
        
        // Salto variable, si el boton se suelta antes la altura maxima es menor
        if (!jump && rb.velocidad.getY() < 0) {
            rb.velocidad.setY(rb.velocidad.getY() * corteSalto);
        }
        
        // Muerte por caida al vacio, suponiendo que todas las ventanas midan 400 y algo
        if (rb.posicion.getY() > 460) { 
            kill();
        }
    }

//	Metodo abstracto para recibir daño, cada juego lo maneja diferente
    public abstract void takeDamage();
    
//	Logica de muerte
    public void kill() {
        if (dead) return;
        dead = true;
        lives--;
        
        // Fuertemente inspirado en Mario y Sonic, al morir el personaje hace un pequeño brinco en pantalla y sale del Frame.
        rb.velocidad.setX(0); 
        rb.velocidad.setY(-350); 
        col.isTrigger = true; 
    }
}