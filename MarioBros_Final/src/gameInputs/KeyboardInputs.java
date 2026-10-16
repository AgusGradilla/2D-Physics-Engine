package gameInputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Player;


public class KeyboardInputs implements KeyListener {

    private Player player; 

    // Recibe al jugador para poder moverlo
    public KeyboardInputs(Player player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Que detecte la letra como tal, no lo ocupamos
    }

    //Cuando se mantiene presionada x tecla
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_Z:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
                
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                player.setLeft(true);
                break;
                
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                // Para esta implementacion, no se utilizo.
                break;
                
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                player.setRight(true);
                break;
            case KeyEvent.VK_X:
            case KeyEvent.VK_SHIFT:
            	player.setRun(true);
            	break;
        }
    }

    //Cuando se suelta x letra
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_Z:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
                
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;
                
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                break;
                
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;
            case KeyEvent.VK_X:
            case KeyEvent.VK_SHIFT:
            	player.setRun(false);
            	break;
        }
    }
}
