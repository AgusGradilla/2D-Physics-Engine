package studyCase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import game.GameUI; 
import graphicsEngine.FontLoader; 

public class MarioHUD implements GameUI {

    private FontLoader font;
    private Image coinIcon, livesIcon;
    
    private int score = 0;
    private int coins = 0;
    private int lives = 3;
    private float time = 400; 
    private String worldName = "DEFAULT";

    private boolean timerPaused = false;
    private boolean showRestartText = false;

    public MarioHUD() {
        this.font = new FontLoader("src/images/fontMayus.png");
        this.coinIcon = Toolkit.getDefaultToolkit().getImage("src/images/spr_CoinIcon.png"); 
        this.livesIcon = Toolkit.getDefaultToolkit().getImage("src/images/1-UP.png");
    }
    
    public void setWorldName(String name) {
        this.worldName = name;
    }

    public void pauseTimer() {
        this.timerPaused = true;
    }

    public void setShowRestartText(boolean show) {
        this.showRestartText = show;
    }

    public int getLives() { return lives; }
    public double getTime() { return time; }
    
    public void resetTime() { 
        this.time = 400; 
        this.timerPaused = false;
        this.showRestartText = false;
    }
    
    public void removeLife() { lives--; }
    public void addScore(int points) { score += points; }
    public void addCoin() { coins++; addScore(200); }

    @Override
    public void update(double dt) {
        if (!timerPaused) {
            time -= dt;
            if (time < 0) {
                time = 0;
            }
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

        if (showRestartText) {
            font.drawText(g, "PRESIONA R PARA REINICIAR EL NIVEL", 140, topY + 225);
        }
    }
}