package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import game.Stage;

public class BrickBlock extends Block {

    private Image img;
    private Stage levelRef;
    
    public BrickBlock(double x, double y, Stage level) {
        super(x, y);
        this.levelRef = level;
        loadImg();
    }
    
    private void loadImg() {
        img = Toolkit.getDefaultToolkit().getImage("src/images/spr_BrickBlock.png");
    }

    @Override
    public void activate(Mario p) {
        if (!p.isBig()) return;
        else breakBlock();
    }

    private void breakBlock() {
        this.destroy();
       
        double x = rb.posicion.getX();
        double y = rb.posicion.getY();

        levelRef.spawnObject(new BrickDebris(x, y, -200, -500, img));
        levelRef.spawnObject(new BrickDebris(x + 16, y, 200, -500, img));
        levelRef.spawnObject(new BrickDebris(x, y + 16, -150, -300, img));
        levelRef.spawnObject(new BrickDebris(x + 16, y + 16, 150, -300, img));
    }

    @Override
    public void draw(Graphics g) {
        if (!isActive()) return;
        
        // Tiene getDrawY por si le pega Mario pequeño
        g.drawImage(img, (int)rb.posicion.getX(), getDrawY(), 32, 32, null);
    }
}