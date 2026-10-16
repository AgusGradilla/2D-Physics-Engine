package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import game.GameObject;
import physicsEngine.Collider2D;
import physicsEngine.RigidBody;
import physicsEngine.Vector2D;

public class Coin extends GameObject {

    private Image img;
    
    // Dimensiones
    private final int SCALE = 2;

    public Coin(double x, double y) {
    	//No se mueve 
        this.rb = new RigidBody(x, y, 0.0f); 
        this.rb.isStatic = true;
        
        // Este si es un trigger, no ocupa reaccionar a fisicas
        this.col = new Collider2D(this, rb, new Vector2D(), 10 * SCALE, 16 * SCALE); 
        this.col.isTrigger = true;
        
        loadImg();
    }
    
    private void loadImg() {
        img = Toolkit.getDefaultToolkit().getImage("src/images/spr_Coin.png");
    }

    @Override
    public void update(double dt) {
        // Como no hay animación ni física de movimiento, este método se queda vacío.
    }

    @Override
    public void onCollisionEnter(GameObject other) {
        // Solo Mario puede recoger monedas
        if (other instanceof Mario) {
            Mario m = (Mario) other;
            m.touchCoin(); // Suma al UI
            
            this.destroy(); // Se deja de actualizar
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, 
            (int)rb.posicion.getX(), (int)rb.posicion.getY(), 
            10 * SCALE, 16 * SCALE, 
            null
        );
    }
}