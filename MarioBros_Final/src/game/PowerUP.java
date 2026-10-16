package game;

import java.awt.Graphics;

import physicsEngine.Collider2D;
import physicsEngine.CollisionHelper;
import physicsEngine.RigidBody;
import physicsEngine.Vector2D;

public abstract class PowerUP extends GameObject {

	protected int direction = 1; // 1 derecha, -1 izquierda
    protected float moveSpeed = 80.0f; 
    protected boolean isMoving = true; // Para diferenciar objetos que se mueven de los que no (por ejemplo, el hongo lo hace, la flor no)

    public PowerUP(double x, double y, double width, double height) {
        this.rb = new RigidBody(x, y, 1.0f);
        this.col = new Collider2D(this, rb, new Vector2D(), width, height);
        this.col.isTrigger = false; 
    }

    public abstract void applyEffect(Player p); //Depende de cada PowerUP y Player el efecto que se tenga

    @Override
    public void update(double dt) {
        if (!isMoving) return;
        rb.velocidad.setX(moveSpeed * direction);
    }
    
    @Override
    public void onCollisionEnter(GameObject other) {
        // Ignoramos choque con Player, la logica de ese choque se manejara en los hijos de Player
        if (other instanceof Player) {
            applyEffect((Player) other);
            this.destroy(); 
            return;
        }

        // Rebote contra muros (al tocar en eje X)
        if (other.getRigidBody() != null && other.getRigidBody().isStatic) {
             
             Vector2D mtv = CollisionHelper.getMTV(this.col, other.getCollider());
             if (mtv == null) return;

             if (mtv.getX() != 0) {
                 direction *= -1;
                 rb.posicion.setX(rb.posicion.getX() + (direction * 2));
             }
        }
    }

    public abstract void draw(Graphics g);
}
