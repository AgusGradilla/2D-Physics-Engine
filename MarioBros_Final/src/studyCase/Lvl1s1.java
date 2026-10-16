package studyCase;

import game.Player;
import game.Stage;
import physicsEngine.RulerOfEverything;
import graphicsEngine.GamePanel;
import graphicsEngine.Background; 

public class Lvl1s1 extends Stage {
	
	//Para poder despertar enemigos
	private Player playerRef;

    private final int TILE_SIZE = 32;
    private final int GROUND_Y = 416;
    
    private final int ROW_1 = GROUND_Y - (TILE_SIZE * 4); 
    private final int ROW_2 = GROUND_Y - (TILE_SIZE * 8); 

    public Lvl1s1(RulerOfEverything roe, GamePanel gamePanel, Player player) {
        super(roe, gamePanel);
        this.playerRef = player;
        this.stageName = "1-1";
        loadLevel();
    }

    @Override
    protected void loadLevel() {
        
        spawnObject(new Background(10000, 480));

        //Secciones del suelo, se cuentan por bloque no por pixel
        createFloorSection(0, 69);
        createFloorSection(71, 86);
        createFloorSection(89, 210); 

        //Tuberias
        spawnObject(new Pipe(28 * TILE_SIZE, ROW_1 + TILE_SIZE, GROUND_Y)); 
        spawnObject(new Pipe(38 * TILE_SIZE, ROW_1, GROUND_Y));
        spawnObject(new Pipe(46 * TILE_SIZE, ROW_1 - TILE_SIZE, GROUND_Y));
        spawnObject(new Pipe(57 * TILE_SIZE, ROW_1 - TILE_SIZE, GROUND_Y));

        //Bloques de ladrillos y de objetos
        spawnObject(new QBlock(16 * TILE_SIZE, ROW_1, this, QBlock.ITEM_COIN));
        
        spawnObject(new BrickBlock(20 * TILE_SIZE, ROW_1, this));
        spawnObject(new QBlock(21 * TILE_SIZE, ROW_1, this, QBlock.ITEM_MUSHROOM)); 
        spawnObject(new BrickBlock(22 * TILE_SIZE, ROW_1, this));
        spawnObject(new QBlock(22 * TILE_SIZE, ROW_2, this, QBlock.ITEM_COIN)); 
        spawnObject(new QBlock(23 * TILE_SIZE, ROW_1, this, QBlock.ITEM_COIN)); 
        spawnObject(new BrickBlock(24 * TILE_SIZE, ROW_1, this));
        
        spawnObject(new BrickBlock(77 * TILE_SIZE, ROW_1, this));
        spawnObject(new QBlock(78 * TILE_SIZE, ROW_1, this, QBlock.ITEM_MUSHROOM)); 
        spawnObject(new BrickBlock(79 * TILE_SIZE, ROW_1, this));

        
        for(int i = 80; i <= 87; i++) {
            spawnObject(new BrickBlock(i * TILE_SIZE, ROW_2, this));
        }

        // Zona final
        spawnObject(new BrickBlock(91 * TILE_SIZE, ROW_2, this));
        spawnObject(new QBlock(94 * TILE_SIZE, ROW_1, this, QBlock.ITEM_COIN)); 
        spawnObject(new BrickBlock(100 * TILE_SIZE, ROW_1, this));
        spawnObject(new QBlock(106 * TILE_SIZE, ROW_1, this, QBlock.ITEM_COIN)); 
        spawnObject(new QBlock(109 * TILE_SIZE, ROW_1, this, QBlock.ITEM_COIN)); 
        spawnObject(new QBlock(109 * TILE_SIZE, ROW_2, this, QBlock.ITEM_MUSHROOM)); 
        spawnObject(new QBlock(112 * TILE_SIZE, ROW_1, this, QBlock.ITEM_COIN));
        
        spawnObject(new BrickBlock(120 * TILE_SIZE, ROW_1, this));
        spawnObject(new BrickBlock(123 * TILE_SIZE, ROW_2, this));
        spawnObject(new BrickBlock(124 * TILE_SIZE, ROW_2, this));
        spawnObject(new BrickBlock(125 * TILE_SIZE, ROW_2, this));
        spawnObject(new BrickBlock(130 * TILE_SIZE, ROW_2, this));
        spawnObject(new BrickBlock(133 * TILE_SIZE, ROW_2, this));
        spawnObject(new BrickBlock(131 * TILE_SIZE, ROW_1, this));
        spawnObject(new BrickBlock(132 * TILE_SIZE, ROW_1, this));
        spawnObject(new QBlock(131 * TILE_SIZE, ROW_2, this, QBlock.ITEM_COIN));
        spawnObject(new QBlock(132 * TILE_SIZE, ROW_2, this, QBlock.ITEM_COIN));






        // Enemigos
        spawnObject(new Goomba(22 * TILE_SIZE, GROUND_Y - 32, playerRef)); 
        spawnObject(new Goomba(40 * TILE_SIZE, GROUND_Y - 32, playerRef)); 
        spawnObject(new Goomba(51 * TILE_SIZE, GROUND_Y - 32, playerRef)); 
        spawnObject(new Goomba(53 * TILE_SIZE, GROUND_Y - 32, playerRef)); 
        
        spawnObject(new Goomba(80 * TILE_SIZE, ROW_2 - 32, playerRef)); 
        spawnObject(new Goomba(82 * TILE_SIZE, ROW_2 - 32, playerRef)); 
        
        spawnObject(new Goomba(97 * TILE_SIZE, GROUND_Y - 32, playerRef));
        spawnObject(new Goomba(99 * TILE_SIZE, GROUND_Y - 32, playerRef));

        //Escaleras,
        createStairs(136, 139, true); 
        createStairs(142, 145, false); 
        createStairs(149, 153, true); 
        createStairs(155, 155, false); 
        createStairs(181, 189, true); 
    }

    // Metodos auxiliares
    private void createFloorSection(int startTile, int endTile) {
        int widthTiles = (endTile - startTile);
        int widthPixels = widthTiles * TILE_SIZE;
        spawnObject(new Floor(startTile * TILE_SIZE, GROUND_Y, widthPixels, 64, true));
    }

    private void createStairs(int startTile, int endTile, boolean ascending) {
        int maxH = (endTile - startTile);
        
        for (int i = 0; i <= maxH; i++) {
            int currentX = (startTile + i);
            int blocksHigh = ascending ? (i + 1) : (maxH - i + 1);
            int totalHeightPixel = blocksHigh * TILE_SIZE;
            int yPos = GROUND_Y - totalHeightPixel;

            spawnObject(new Floor( currentX * TILE_SIZE, yPos, 32, totalHeightPixel,false));
        }
    }
}