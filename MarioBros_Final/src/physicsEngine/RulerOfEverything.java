package physicsEngine;

import java.util.ArrayList;
import game.GameObject;
import game.Player;

public class RulerOfEverything {

	private ArrayList<RigidBody> bodies = new ArrayList<>();
    private ArrayList<Collider2D> colliders = new ArrayList<>();
    
    private Vector2D gravity = new Vector2D(0, 9.8f * 115); 

    public void add(GameObject obj) {
        if (obj.getRigidBody() != null) bodies.add(obj.getRigidBody());
        if (obj.getCollider() != null) colliders.add(obj.getCollider());
    }
    
    public void remove(GameObject obj) {
        if (obj.getRigidBody() != null) bodies.remove(obj.getRigidBody());
        if (obj.getCollider() != null) colliders.remove(obj.getCollider());
    }
    
    public void update(float dt) {
        for (RigidBody rb : bodies) {
            if (!rb.isStatic) {
            	rb.addForce(new Vector2D(gravity.getX(), gravity.getY()));
            }
        }

        for (RigidBody rb : bodies) {
            rb.update(dt);
        }

        resolveCollisions();
    }

    private void resolveCollisions() {
        for (int i = 0; i < colliders.size(); i++) {
            for (int j = i + 1; j < colliders.size(); j++) {
                Collider2D c1 = colliders.get(i);
                Collider2D c2 = colliders.get(j);

                if(!c1.getOwner().isActive() || !c2.getOwner().isActive()) continue;

                Vector2D mtv = CollisionHelper.getMTV(c1, c2);
                if (mtv != null) {
                    c1.getOwner().onCollisionEnter(c2.getOwner());
                    c2.getOwner().onCollisionEnter(c1.getOwner());

                    boolean isTrigger = c1.isTrigger || c2.isTrigger;
                    
                    boolean isCombat = (c1.getOwner() instanceof Player && c2.getOwner() instanceof game.Enemy) ||
                                       (c1.getOwner() instanceof game.Enemy && c2.getOwner() instanceof Player);

                    if (!isTrigger && !isCombat) {
                        applyPhysicsResolution(c1, c2, mtv);
                    }
                }
            }
        }
    }

    private void applyPhysicsResolution(Collider2D c1, Collider2D c2, Vector2D mtv) {
        RigidBody rb1 = c1.getBody();
        RigidBody rb2 = c2.getBody();

        if (!rb1.isStatic && rb2.isStatic) {
            rb1.posicion.add(mtv);

            if (mtv.getY() < 0) {
                rb1.velocidad.setY(0);
                rb1.isGrounded = true; 
            }
            else if (mtv.getY() > 0) {
                rb1.velocidad.setY(0);
            }
        }
        else if (rb1.isStatic && !rb2.isStatic) {
            rb2.posicion.sub(mtv); 

            if (mtv.getY() > 0) { 
                rb2.velocidad.setY(0);
                rb2.isGrounded = true;
            }
            else if (mtv.getY() < 0) {
                rb2.velocidad.setY(0);
            }
        }
    }
}