package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import game.GameObject;
import physicsEngine.RigidBody;
import physicsEngine.Vector2D;

public class BrickDebris extends GameObject {

    private Image img;
    
    // Cuanto tiempo de vida tiene antes de borrarse (seguridad)
    private float lifeTime = 3.0f; 

    public BrickDebris(double x, double y, double velX, double velY, Image img) {
        // Masa 1.0 para que caiga con gravedad
        this.rb = new RigidBody(x, y, 1.0f);
        
        // NO necesitamos collider. Los escombros son solo visuales, no chocan con nada.
        this.col = null; 
        
        this.img = img;
        
        // Le damos el impulso inicial (explosión)
        this.rb.velocidad = new Vector2D(velX, velY);
    }

    @Override
    public void update(double dt) {
        // Actualizamos la física manualmente o dejamos que RoE lo haga si lo agregamos ahí.
        // Como RoE aplica gravedad a todo lo que tenga RigidBody dinámico, esto funcionará.
        
        // Timer de limpieza
        lifeTime -= dt;
        if (lifeTime <= 0 || rb.posicion.getY() > 600) {
            this.destroy();
        }
    }

    @Override
    public void draw(Graphics g) {
        // DIBUJAR SOLO UN CUARTO DEL LADRILLO
        // El ladrillo original mide 16x16 (en el PNG). Vamos a dibujar un pedazo de 8x8.
        
        int x = (int) rb.posicion.getX();
        int y = (int) rb.posicion.getY();
        
        // Queremos que el pedazo se vea de 16x16 en pantalla (Scale 2)
        int size = 16; 

        // Recorte: Tomamos la esquina superior izquierda (0,0) de 8x8 pixeles
        g.drawImage(img, 
            x, y, x + size, y + size, // Destino
            0, 0, 8, 8,               // Origen (Solo la esquinita del sprite)
            null
        );
    }

	@Override
	public void onCollisionEnter(GameObject other) {
		// TODO Auto-generated method stub
	}
}