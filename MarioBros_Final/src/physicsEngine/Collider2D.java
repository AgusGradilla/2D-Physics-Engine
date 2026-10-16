package physicsEngine;
import game.GameObject;

public class Collider2D {
    private GameObject owner; // Para poder diferenciar distintos tipos de colisiones
    private RigidBody body;
    private Vector2D offset;
    private double width, height; //TO DO: Double
    
    public boolean isTrigger = false; // Es solido o solo un posible trigger?

    public Collider2D(GameObject owner, RigidBody body, Vector2D offset, double width, double height) {
        this.owner = owner;
        this.body = body;
        this.offset = offset;
        this.width = width;
        this.height = height;
    }

    //A que objeto y body esta asociado.
    public GameObject getOwner() { return owner; }
    public RigidBody getBody() { return body; }

    //Poder cambiar el tamaño del collider, puesto despues de cuestionarse el tamaño que tendria cuando Mario tocara power-ups.
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    // Posicion de todos los lados del colisionador
    public double getLeft() { return body.posicion.getX() + offset.getX(); }
    public double getRight() { return getLeft() + width; }
    public double getTop() { return body.posicion.getY() + offset.getY(); }
    public double getBottom() { return getTop() + height; }

    // Cuanto mide
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    
    //TO DO: Decidir si se incluyen estos metodos en gameObject o separarlos en una clase CollisionHelper

}
