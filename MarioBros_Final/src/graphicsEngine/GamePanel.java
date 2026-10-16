package graphicsEngine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import game.GameObject;
import game.GameUI;
import gameInputs.KeyboardInputs;
import studyCase.Mario;

public class GamePanel extends JPanel {

	//Todos los objetos que debe dibujar
    private ArrayList<GameObject> drawList = new ArrayList<>();
    
    private GameUI ui; //El panel no sabe que tipo de UI va a poner, pero sabe que va a necesitar una.
    private int cameraX = 0; //Cuanto se desplaza el lienzo para dibujar cosas (o sea cuanto se deberia mover)
    
    public final int ANCHO = 864;
    public final int ALTO = 480;

    public GamePanel(GameUI ui) {
    	this.ui = ui;
        setPanelSize();
        setFocusable(true); //Para lo de teclas instantaneas
        requestFocusInWindow();
    }
    
    // Agregar cosas a la lista de objetos a dibujar
    private void setPanelSize() {
        Dimension size = new Dimension(ANCHO, ALTO);
        setPreferredSize(size);
    }
    
    public void addToRenderList(GameObject obj) {
        this.drawList.add(obj);
    }
    
    public void remove(GameObject obj) {
        drawList.remove(obj);
    }
    
    public void clearRenderList() {
        this.drawList.clear();
    }
    
    public void resetInputs(Mario newPlayer) {
        // 1. Quitamos todos los escuchadores viejos (el del jugador muerto)
        for (KeyListener kl : getKeyListeners()) {
            removeKeyListener(kl);
        }
        
        // 2. Agregamos uno nuevo conectado al jugador nuevo
        KeyboardInputs inputs = new KeyboardInputs(newPlayer);
        addKeyListener(inputs);
    }
    
    public void setCameraX(int x) {
        this.cameraX = x;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        
        java.awt.geom.AffineTransform original = g2d.getTransform();
        
        g2d.translate(-cameraX, 0);

        for (GameObject obj : drawList) {
            obj.draw(g);
        }
        
        g2d.setTransform(original);
        
        if (ui != null) ui.draw(g);
    }
}
