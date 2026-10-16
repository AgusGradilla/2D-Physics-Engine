package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import game.GameUI; 
import graphicsEngine.FontLoader; 

public class MarioHUD implements GameUI {

    private FontLoader font;
    private Image coinIcon, livesIcon;
    
    // Variables especificas de Mario
    private int score = 0;
    private int coins = 0;
    private int lives = 3;
    private float time = 400; 
    private String worldName = "DEFAULT";

    public MarioHUD() {
        this.font = new FontLoader("src/images/fontMayus.png");
        this.coinIcon = Toolkit.getDefaultToolkit().getImage("src/images/spr_CoinIcon.png"); 
        this.livesIcon = Toolkit.getDefaultToolkit().getImage("src/images/1-UP.png");
    }
    
    //Setter
    public void setWorldName(String name) {
        this.worldName = name;
    }

    //Getters
    public int getLives() { return lives;}
    public double getTime() { return time; }
    
    //Metodos para actualizar estado actual del juego
    public void resetTime() { this.time = 400; }
    public void removeLife() { lives--; }
    
    // Metodos propios de Mario (no están en la interfaz, pero el Integrador los usa)
    public void addScore(int points) { score += points; }
    public void addCoin() { coins++; addScore(200); }

    @Override
    public void update(double dt) {
        time -= dt;
        if (time < 0) {
        	time = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        int topY = 30; 
        
        font.drawText(g, "MARIO", 40, topY);
        font.drawText(g, String.format("%06d", score), 40, topY + 25);
        
        g.drawImage(livesIcon, 268, topY, 16, 16, null);
        font.drawText(g, String.format("x%02d", lives), 290, topY);

        g.drawImage(coinIcon, 272, topY + 25, 10, 16, null); 
        font.drawText(g, String.format("x%02d", coins), 290, topY + 25);

        font.drawText(g, "WORLD", 480, topY);
        font.drawText(g, worldName, 495, topY + 25);

        font.drawText(g, "TIME", 680, topY);
        font.drawText(g, String.format("%03d", (int)time), 695, topY + 25);
        
        if(lives <= 0) {
        	font.drawText(g, "GAME OVER", 350, topY + 225);
        }
        
    }
}