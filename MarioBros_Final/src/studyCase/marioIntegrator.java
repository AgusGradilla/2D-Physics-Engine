package studyCase;

import game.Integrator;
import game.Stage;
import gameInputs.KeyboardInputs;
import graphicsEngine.*;
import physicsEngine.*;

public class marioIntegrator extends Integrator {
	
    protected Mario player;
    protected Stage level;
    protected MarioHUD hud; 
    private KeyboardInputs keyboardInputs;

    private final int SCREEN_WIDTH = 850;

	@Override
	protected void initClasses() {
        roe = new RulerOfEverything();
        hud = new MarioHUD(); 
        
        gamePanel = new GamePanel(hud); 
        gameWindow = new GameWindow(gamePanel);

        player = new Mario(64, 64, hud);
        level = new Lvl1s1(roe, gamePanel, player);
        
        hud.setWorldName(level.getName());

        roe.add(player); 
        gamePanel.addToRenderList(player);

        keyboardInputs = new KeyboardInputs(player);
        gamePanel.addKeyListener(keyboardInputs);
        gamePanel.requestFocus(); 
	}
	
    private void handleRespawn() {
        if (hud.getLives() > 0) {
            resetLevel();
        }
    }
    
    private void resetLevel() {
    	gamePanel.clearRenderList();
    	
    	roe = new RulerOfEverything(); 
        hud.resetTime();
        
        player = new Mario(64, 64, hud); 
        level = new Lvl1s1(roe, gamePanel, player);
        
        roe.add(player);
        gamePanel.addToRenderList(player); 
        gamePanel.resetInputs(player); 
        gamePanel.setCameraX(0);
    }

    private void updateCamera() {
        int playerX = (int) player.getRigidBody().posicion.getX();
        int offset = playerX - (SCREEN_WIDTH / 2);
        
        if (offset < 0) {
            offset = 0;
        }
        
        gamePanel.setCameraX(offset);
    }

	@Override
	protected void updateLogic(double dt) {
        if (!player.isDead()) {
            hud.update(dt);
            level.update(dt);
            player.update(dt);
            
            if (hud.getTime() <= 0) {
                player.kill();
            }
            
            updateCamera();

            //Reiniciar nivel si el jugador ha llegado a la bandera y presiona R
            if (level instanceof Lvl1s1) {
                LevelFlag flag = ((Lvl1s1) level).getLevelFlag();
                if (flag != null && flag.isFinished() && player.canRestartFromFlag()) {
                    resetLevel();
                }
            }
        } else {
            player.update(dt);
            
            if (player.getRigidBody().posicion.getY() > 800) {
                handleRespawn();
            }
        }
	}
}