package studyCase;

import game.Stage;
import graphicsEngine.Background;
import graphicsEngine.GamePanel;
import physicsEngine.RulerOfEverything;

public class Lvltest extends Stage {

	public Lvltest(RulerOfEverything roe, GamePanel gamePanel) {
		super(roe, gamePanel);
		this.stageName = "TEST";
		loadLevel();
	}

	@Override
	protected void loadLevel() {
		//Floor eventualmente tendra que tener distintos tipos
		//TO-DO: Ver como crear apuntadores a ciertos objetos como lo es el Goomba, para reciclarlo. Proximas versiones
		spawnObject(new Background(1000,500));
		spawnObject(new Pipe(800,352,416));
		spawnObject(new Floor(0, 416, 864, 64, true)); //Piso base
		spawnObject(new Floor(128, 256, 96, 32, true)); //Plataforma
		spawnObject(new Floor(288, 192, 32, 160, false)); //Pared
		spawnObject(new Mushroom(352,192));
//		spawnObject(new Goomba(480,192));
		spawnObject(new QBlock(256, 192, this, QBlock.ITEM_MUSHROOM));
		spawnObject(new BrickBlock(384,192,this));
		spawnObject(new Pipe(800,352,416));
		spawnObject(new Coin(352, 320));
		spawnObject(new Coin(416, 320));
	}
}
