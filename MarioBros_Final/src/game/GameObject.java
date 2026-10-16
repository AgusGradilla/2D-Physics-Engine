package game;
import java.awt.Graphics;

import physicsEngine.*;

public abstract class GameObject {
    
	// Componentes de todos los GameObjects
    protected RigidBody rb;
    protected Collider2D col;
    protected boolean active;
    
    public GameObject() {
    	this.active = true;
    }
    
    // Getters para que RoE pueda acceder a ellos
    public RigidBody getRigidBody() { return rb; }
    public Collider2D getCollider() { return col; }
    public boolean isActive() { return active; }

    public void destroy() {
        this.active = false;
    }
 
    
    //Aca se pone todo lo que debe actualizarse de este objeto en especifico
    public abstract void update(double dt);
    
    // Sprites
    public abstract void draw(Graphics g);
    

    // Cambios logicos a partir de la colision, como, si Player toca a un enemigo, restar vida o morir
    public abstract void onCollisionEnter(GameObject other);
}