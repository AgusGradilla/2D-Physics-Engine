package graphicsEngine;
//NOTA
import java.awt.Color;
import java.awt.Graphics;
import game.GameObject;

public class Background extends GameObject {

	private Color color;
	private int width;  // Agregamos variable para el ancho
	private int height; // Agregamos variable para el alto

	public Background(int width, int height) {
		// Azul clásico de Super Mario Bros (NES)
		this.color = new Color(161, 173, 255);
		this.width = width;
		this.height = height;
		
		// El background no necesita rigidbody ni collider
		// pero GameObject lo pide, así que lo dejamos nulo o básico
		// (Dependiendo de tu implementación de GameObject, esto puede variar)
	}
	
	@Override
	public void update(double dt) {
		// El fondo es estático, no hace nada
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		// Dibujamos un rectángulo gigante que cubra todo el nivel
		g.fillRect(0, 0, width, height);
	}

	@Override
	public void onCollisionEnter(GameObject other) {
		// No ocupa	
	}
}
