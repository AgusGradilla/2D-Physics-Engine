package game;
import java.util.ArrayList;

import graphicsEngine.GamePanel;
import physicsEngine.RulerOfEverything;

public abstract class Stage {
    
    // Lista de todo lo que existe en el nivel
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    
    // Referencias a los motores para poder registrar y borrarles objetos a sus respectivas listas
    private RulerOfEverything roe;
    private GamePanel gamePanel;
   
    protected String stageName; //Nombre del nivel, usado en HUD

    public Stage(RulerOfEverything roe, GamePanel gamePanel) {
        this.roe = roe;
        this.gamePanel = gamePanel;
    }

    protected abstract void loadLevel(); //Aca se deberian agregar todos los objetos dentro del nivel, con el spawnObject
    public String getName() { return stageName; }

    // Que todos los objetos se agreguen automaticamente a las listas de las clases que ocupen actualizarlos
    public void spawnObject(GameObject obj) {
        gameObjects.add(obj);      // Lista logica (que se checa desde aqui
        roe.add(obj);              // Lista fisica
        gamePanel.addToRenderList(obj); // Lista grafica
    }

    public void update(double dt) {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject obj = gameObjects.get(i);

            if (obj == null) continue; // Si este espacio ya fue borrado, no hay que checar ni actualizar nada más.

            // Actualizar lógica normal
            obj.update(dt);

            // Si un objeto no esta activo, no nos interesa actualizarlo, asi que se vacia el espacio que ocupa en la lista
            if (!obj.isActive()) {
                
                // Lo quitamos de físicas y gráficos
                roe.remove(obj); 
                gamePanel.remove(obj); 
                
                gameObjects.set(i, null); // Se vuelve un espacio sin nada. 
            }
        }
    }
}