package studyCase;

//import java.awt.Color;  //Solo usado en debug
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import game.GameObject;
import game.Platform;


public class Floor extends Platform{

	private static final int TILE_SIZE = 32;
	private Image img;
	private boolean isFloor; //0 si es piso, 1 si es escalera
	
    public Floor(double x, double y, double width, double height, boolean floor) {
    	super(x,y,width,height);
    	this.isFloor = floor;
        loadImg();
    }
    
    private void loadImg() {
    	if(isFloor) img = Toolkit.getDefaultToolkit().getImage("src/images/spr_floorTile.png");
    	else img = Toolkit.getDefaultToolkit().getImage("src/images/spr_stairBlock.png");
    }
    
    //No ocupa un metodo update, no deberia actualizarse al ser estatico
	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}
    
    @Override
    public void draw(Graphics g) {
    	int startX = (int) rb.posicion.getX();
        int startY = (int) rb.posicion.getY();
        int totalWidth = (int) col.getWidth();
        int totalHeight = (int) col.getHeight();
        
    	for(int x = 0; x < totalWidth; x += TILE_SIZE) {
    		for (int y = 0; y < totalHeight; y += TILE_SIZE) {
    			g.drawImage(img, startX + x, startY + y, TILE_SIZE, TILE_SIZE, null);
    		}
    	}
        
        // Borde verde para debug
//        g.setColor(Color.GREEN);
//        g.drawRect((int)col.getLeft(), (int)col.getTop(), (int)col.getWidth(), (int)col.getHeight());
    }

	@Override
	public void onCollisionEnter(GameObject other) {
		//Su colision fisica la maneja RoE, si tuviera algun efecto como piso de lava cambiaria esto, por ahora no.
	}

}

