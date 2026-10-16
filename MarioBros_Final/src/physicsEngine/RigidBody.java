package physicsEngine;

public class RigidBody {
    public Vector2D posicion;
    public Vector2D velocidad;
    public Vector2D fuerzaAcumulada;
    
    public double masaInv;
    public double drag = 0.98f; // Fricción del aire
    public boolean isStatic = false; 
    public boolean isGrounded = false; // Controlado por RoE 

    public RigidBody(double x, double y, double masa) {
        this.posicion = new Vector2D(x, y);
        this.velocidad = new Vector2D(0, 0);
        this.fuerzaAcumulada = new Vector2D(0, 0);
        
        if (masa <= 0) {
            this.masaInv = 0;
            this.isStatic = true;
        } else {
            this.masaInv = 1.0f / masa;
        }
    }

    public void addForce(Vector2D f) {
        if (isStatic) return;
        fuerzaAcumulada.add(f);
    }

    //Util para saltos o dash
    public void applyImpulse(Vector2D impulse) {
        if (isStatic) return;
        Vector2D deltaV = new Vector2D(impulse.getX(), impulse.getY());
        deltaV.scale(masaInv);
        velocidad.add(deltaV);
    }

    public void update(double dt) {
        if (isStatic) return; //Si no debe moverse no necesita actualizarse
        
        // Falso hasta que confirmamos que estamos tocando piso
        isGrounded = false; 

        // Aceleracion y velocidad
        Vector2D aceleracion = new Vector2D(fuerzaAcumulada.getX(), fuerzaAcumulada.getY());
        aceleracion.scale(masaInv);
        
        velocidad.add(new Vector2D(aceleracion.getX() * dt, aceleracion.getY() * dt));
        
        if (velocidad.getY() > 600) { // 600 px por segundo máximo
            velocidad.setY(600);
        }
        
        // Fricción simple para que no acelere eternamente.
        velocidad.setX(velocidad.getX() * 0.90f);

        // Nueva posicion
        posicion.add(new Vector2D(velocidad.getX() * dt, velocidad.getY() * dt));

        // Reiniciar fuerzas para el siguiente update
        fuerzaAcumulada.setX(0);
        fuerzaAcumulada.setY(0);
    }
}