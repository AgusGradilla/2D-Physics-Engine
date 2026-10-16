package studyCase;

import java.awt.Graphics;

import game.GameObject;
import physicsEngine.*;

public abstract class Block extends GameObject{

	protected boolean bump = false;
	protected double bumpY = 0;
	protected double bumpSpeed = -200.0f; //Impulso que tiene hacia arriba cuando se le pega
	protected double startY;
	
	private final int SIZE = 32;
	
	public Block(double x, double y) {
		this.rb = new RigidBody(x,y,0.0f);
		this.col = new Collider2D(this, rb, new Vector2D(),SIZE,SIZE);
		
		this.rb.isStatic = true;
		this.col.isTrigger = false;
		
		this.startY = (double) y;
	}
	
	public abstract void activate(Mario p); //Cada objeto que herede decide que se hace cuando se le pega
	
	@Override
	public void update(double dt) {
		if(bump) {
			bumpY += bumpSpeed * dt;
			bumpSpeed += 800 * dt;
			
			if(bumpY >= 0) {
				bumpY = 0;
				bump = false;
				bumpSpeed = -200.0f; //Reset por si hay siguiente bump
			}
		}
	}

	@Override
    public void onCollisionEnter(GameObject other) {
        if (other instanceof Mario) {
            Mario p = (Mario) other;

            Vector2D mtv = CollisionHelper.getMTV(this.col, p.getCollider());
            
            // Si no hay toque pues no hay nada
            if (mtv == null) return;

            // Tipo de colision
            // Si Y no es 0, es que la colision es por arriba o abajo.
            boolean isVerticalCollision = mtv.getY() != 0;

            if (isVerticalCollision) {
                // Si la cabeza de Mario esta por debajo del bloque es que si fue un golpe por abajo.
                boolean isBelow = p.getCollider().getTop() > this.col.getTop();
                
                // Mario debe estar subiendo para que se active el golpe
                if (isBelow && !bump) {
                    startBump();
                    activate(p);
                }
            }
        }   
	}
	
	protected void startBump() {
        bump = true;
        bumpSpeed = -150.0f;
    }
    
    protected int getDrawY() {
        return (int) (startY + bumpY);
    }
    
    public abstract void draw(Graphics g);

}
