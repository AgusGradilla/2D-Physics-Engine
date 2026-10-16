package studyCase;

import game.Integrator;
import game.Stage;
import gameInputs.KeyboardInputs;
import graphicsEngine.*;
import physicsEngine.*;

public class marioIntegrator extends Integrator {
	
    // Objetos específicos del juego
    protected Mario player;
    protected Stage level;
    
    // Referencia específica al HUD de Mario (para ver vidas y tiempo)
    protected MarioHUD hud; 

    // Variables de cámara
    private final int SCREEN_WIDTH = 850;

	@Override
	protected void initClasses() {
        // Sistemas base
        roe = new RulerOfEverything();
        
        // UI especifica de mario
        hud = new MarioHUD(); 
        
        // Graficos, panel y ventana
        gamePanel = new GamePanel(hud); 
        gameWindow = new GameWindow(gamePanel);

        // Entitades
        // Player necesita acceso al HUD para restar vidas al morir
        player = new Mario(64, 64, hud);
        
        // El nivel carga los bloques y enemigos en el RoE y GamePanel
//      level = new Lvltest(roe, gamePanel); 
        level = new Lvl1s1(roe,gamePanel,player);
        
        hud.setWorldName(level.getName());

        // Solo faltan agregar las fisicas del jugador
        roe.add(player); 
        
        // Agregamos graficos (al final para que se dibuje frente al fondo)
        gamePanel.addToRenderList(player);

        // Inputs
        gamePanel.addKeyListener(new KeyboardInputs(player));
        gamePanel.requestFocus(); 
	}
	
	 // Decide si reiniciar el nivel o acabar el juego
    private void handleRespawn() {
        if (hud.getLives() > 0) {
            resetLevel();
        }
    }
    
    // Destruye el estado actual y crea uno nuevo limpio
    private void resetLevel() {
    	gamePanel.clearRenderList();
    	
    	roe = new RulerOfEverything(); 
        hud.resetTime();
        
        // Creamos al jugador (pero aun no lo metemos a dibujar)
        player = new Mario(64, 64, hud); 
        
        // Recrear el nivel
        level = new Lvl1s1(roe, gamePanel, player);
        
        //Volver a agregar al player al nivel, para que este por encima
        roe.add(player);
        gamePanel.addToRenderList(player); 
        gamePanel.resetInputs(player); 
        gamePanel.setCameraX(0);
    }
    

    // Logica para mover la camara siguiendo a Mario
    private void updateCamera() {
        int playerX = (int) player.getRigidBody().posicion.getX();
        
        // Se mueve a partir de que Mario este centrado
        int offset = playerX - (SCREEN_WIDTH / 2);
        
        // No se muestra nada antes del inicio del nivel
        if (offset < 0) {
            offset = 0;
        }
        
        // Enviamos el dato al panel para que desplace la camara
        gamePanel.setCameraX(offset);
    }


	@Override
	protected void updateLogic(double dt) {
        
        // Si mario sigue vivo,
        if (!player.isDead()) {
            
            // Actualizar entidades
            hud.update(dt);
            level.update(dt);
            player.update(dt);
            
            // Si el tiempo se agota,
            if (hud.getTime() <= 0) {
                player.kill(); // Matar a mario
            }
            
            // Actualizar camara
            updateCamera();
        } 
        
        // Secuencia de muerte
        else {
            // Solo actualizamos al jugador para que la gravedad lo jale hacia abajo y salga de la ventana
            player.update(dt);
            
            // Si sale, respawnear
            if (player.getRigidBody().posicion.getY() > 800) {
                handleRespawn();
            }
        }
	}
}