package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import game.GameObject;
import physicsEngine.Collider2D;
import physicsEngine.RigidBody;
import physicsEngine.Vector2D;

public class Pipe extends GameObject {

    private Image spriteSheet; // Una sola imagen para todo
    
    private int height; // Altura total deseada
    
    // Dimensiones en el sprite
    private final int SPRITE_WIDTH = 32; 
    private final int SPRITE_HEIGHT = 16;
    
    // Coordenadas de donde empiezan ambas partes del pipe
    private final int SRC_HEAD_Y = 0; 
    private final int SRC_BODY_Y = 16;

    // Dimensiones dentro del juego
    private final int SCALE = 2;
    private final int DEST_WIDTH = SPRITE_WIDTH * SCALE; 
    private final int DEST_HEIGHT = SPRITE_HEIGHT * SCALE;

    //Constructor
    public Pipe(double x, double startY, double groundY) {

        int totalHeight = (int)(groundY - startY);
        this.height = totalHeight; 

        this.rb = new RigidBody(x, startY, 0.0f); 
        this.rb.isStatic = true;
        
        this.col = new Collider2D(this, rb, new Vector2D(), DEST_WIDTH, this.height); 
        this.col.isTrigger = false;
        
        loadImg();
    }
    
    private void loadImg() {
        spriteSheet = Toolkit.getDefaultToolkit().getImage("src/images/spr_Pipe.png");
    }

    @Override
    public void update(double dt) {
        // Estatico, no ocupa
    }
    
	@Override
	public void onCollisionEnter(GameObject other) {
		//No deberia de tener soluciones logicas mas que a lo mejor el Warp, pero no se implemento en esta version.
	}

    @Override
    public void draw(Graphics g) {
        int x = (int) rb.posicion.getX();
        int y = (int) rb.posicion.getY();
        
        //Dibujar la salida
        g.drawImage(spriteSheet, x, y, x + DEST_WIDTH, y + DEST_HEIGHT, 0, SRC_HEAD_Y, SPRITE_WIDTH, SRC_HEAD_Y + SPRITE_HEIGHT, null );
        
        // Se dibuja el cuerpo justo debajo de la cabeza
        for (int i = DEST_HEIGHT; i < height; i += DEST_HEIGHT) {

            int remainingSpace = height - i;
            int currentDestHeight = (remainingSpace < DEST_HEIGHT) ? remainingSpace : DEST_HEIGHT;
            
            int currentSrcHeight = (currentDestHeight / SCALE); // Para el recorte de origen, si el espacio es menor, necesitamos recortar proporcionalmente

            g.drawImage(spriteSheet, 
                x, y + i, x + DEST_WIDTH, y + i + currentDestHeight, // Destino variable
                0, SRC_BODY_Y, SPRITE_WIDTH, SRC_BODY_Y + currentSrcHeight, // De la coordenada 16 para abajo (de la imagen)
                null
            );
        }
    }
}