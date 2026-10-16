package game;
import java.awt.Graphics;

import physicsEngine.Collider2D;
import physicsEngine.RigidBody;
import physicsEngine.Vector2D;

public abstract class Platform extends GameObject {

	public Platform (double x, double y, double width, double height) {
		this.rb = new RigidBody(x, y,0.0f); 
	    this.col = new Collider2D(this, rb, new Vector2D(), width, height); 
	    rb.isStatic = true;
	    this.col.isTrigger = false;
	}
	     
	@Override
	public abstract void draw(Graphics g);
}