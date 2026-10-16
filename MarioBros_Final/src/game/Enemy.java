package game;

import physicsEngine.*;

public abstract class Enemy extends GameObject {

    protected int direction = -1; 
    protected double speed = 100.0f; 
    protected boolean isDead = false;
    
    //Activacion del enemigo
    protected boolean isAsleep = true; 
    protected Player playerRef; 

    public Enemy(double x, double y, int width, int height, Player player) {
        this.rb = new RigidBody(x, y, 1.0f);
        this.col = new Collider2D(this, rb, new Vector2D(), width, height);
        this.col.isTrigger = false; 
        this.playerRef = player;
    }

    public abstract void getSquashed();
    public boolean isDead() { return isDead; }
    
    private void wakeUp() {
        isAsleep = false;
        rb.isStatic = false; 
    }

    @Override
    public void update(double dt) {
        if (isDead) return;
        
        if (isAsleep) {
        	double dist = Vector2D.distance(this.rb.posicion, playerRef.getRigidBody().posicion);
            
        	//Si Player esta cerca (como en el ancho de la pantalla) activarse
        	if (dist < 800) {
                wakeUp();
            }
            return;
        }
        rb.velocidad.setX(speed * direction);
    }

    @Override
    public void onCollisionEnter(GameObject other) {
        if (isDead) return;

        if (other.getRigidBody() != null && other.getRigidBody().isStatic) {
            
            Vector2D mtv = CollisionHelper.getMTV(this.col, other.getCollider()); //Se le pregunta al Helper que tipo de choque fue

            //Si fue de lado es una pared o tuberia
            if (mtv.getX() != 0) { 
                 direction *= -1; 
                 rb.posicion.setX(rb.posicion.getX() + (direction * 2)); //Empujon en la otra direccion
            }
        }
    }
}