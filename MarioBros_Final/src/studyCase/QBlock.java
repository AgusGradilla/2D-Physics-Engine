package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import game.Stage;

public class QBlock extends Block {

    private Image imgActive;
    private Image imgEmpty;
    private boolean isActive = true;
    private Stage stageRef;
    
    // Tipos de objeto
    // Podria volverse una enumeracion facilmente
    public static final int ITEM_COIN = 0;
    public static final int ITEM_MUSHROOM = 1;
    
    private int content; // Que tiene este bloque especificamente

    // Constructor, recibe el numero del objeto que debe sacar
    public QBlock(double x, double y, Stage stage, int content) {
        super(x, y);
        this.stageRef = stage;
        this.content = content; 
        loadImgs();
    }
    
    private void loadImgs() {
        imgActive = Toolkit.getDefaultToolkit().getImage("src/images/spr_QBlock.png");
        imgEmpty = Toolkit.getDefaultToolkit().getImage("src/images/spr_EmptyBlock.png");
    }

    @Override
    public void activate(Mario p) {
        if (!isActive) return;

        isActive = false;
        startBump(); 

        //Donde debe generar su objeto
        double spawnX = rb.posicion.getX();
        double spawnY = rb.posicion.getY() - 32; 
        
        //Que debe generar
        switch (content) {
            case ITEM_MUSHROOM:
                Mushroom hongo = new Mushroom(spawnX, spawnY);
                // Pequeño impulso para que salga saltando
                hongo.getRigidBody().velocidad.setY(-100); 
                stageRef.spawnObject(hongo);
                break;
                
            case ITEM_COIN:
                // Nota: no funciona exactamente como en el juego que deberia agregarlo automaticamente al contador, solo las hace aparecer
                Coin moneda = new Coin(spawnX, spawnY);
                
                stageRef.spawnObject(moneda);
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        Image sprite = isActive ? imgActive : imgEmpty;
        g.drawImage(sprite, (int)rb.posicion.getX(), getDrawY(), 32, 32, null);
    }
}