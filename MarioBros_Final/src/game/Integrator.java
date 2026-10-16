package game;

import graphicsEngine.*;
import physicsEngine.*;

public abstract class Integrator implements Runnable {

    // Partes que se van a integrar
    protected GameWindow gameWindow;
    protected GamePanel gamePanel;
    protected RulerOfEverything roe; 
    protected Thread gameThread;
    
    // Limitadores para el loop y variables para manejar el loop por segundo
    protected final static int FPS_SET = 60;
    protected final static int UPS_SET = 120; 
    
    static final double timePerFrame = 1000000000.0 / FPS_SET;
    static final double timePerUpdate = 1000000000.0 / UPS_SET;
   
    private long previousTime;
    private long lastCheck;
    
    private double deltaU = 0;
    private double deltaF = 0;
    
    private int frames = 0;
    private int updates = 0;
    
    // Constructor
    public Integrator() {
        initClasses();
        startGameLoop();
    }

    //Esto depende completamente del juego que se vaya a crear
    protected abstract void initClasses();
    protected abstract void updateLogic(double dt); 

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    //Funciones auxiliares para run (hecho para dividir run y hacerlo legible)
    //Iniciar todo lo relacionado a medir frames y updates por tiempo en run
    private void initLoopVariables() {
        previousTime = System.nanoTime();
        lastCheck = System.currentTimeMillis();
        frames = 0;
        updates = 0;
        deltaU = 0;
        deltaF = 0;
    }

    //Cuanto deberia tardar hasta el siguiente frame/update
    private void calculateDeltas(long currentTime) {
        // Acumular tiempo transcurrido
        deltaU += (currentTime - previousTime) / timePerUpdate;
        deltaF += (currentTime - previousTime) / timePerFrame;
    }

    //Si tiene una actualizacion de fisica/logica pendiente, actualizar
    private void processPhysics() {
        if (deltaU >= 1) {
            update();
            updates++;
            deltaU--;
        }
    }

    //Igual que el anterior pero actualizacion grafica
    private void processRendering() {
        if (deltaF >= 1) {
            gamePanel.repaint();
            frames++;
            deltaF--;
        }
    }

    //Funcion debug, cada segundo que pase actualizar cuantos frames se han hecho y cuantas updates
    //Deberian ser iguales al limite que se impuso como atributo al inicio de esta clase
    private void checkFPSUPS() {
        if (System.currentTimeMillis() - lastCheck >= 1000) {
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames + " | UPS: " + updates);
            frames = 0;
            updates = 0;
        }
    }
    
    // Actualizaciones de todo
    public final void update() {
        float dt = 1.0f / UPS_SET; //Delta time en segundos

        //A ambos se les pasa el tiempo que tarda en realizarse un update
        roe.update(dt); // Fisicas
        updateLogic(dt); // Logica del juego
    }

    // Game loop 
    @Override
    public void run() { 
    	initLoopVariables();

        while (true) {
            long currentTime = System.nanoTime();
            
            calculateDeltas(currentTime);
            processPhysics();
            processRendering();
//            checkFPSUPS();
            
            previousTime = currentTime;
        }
    }
}