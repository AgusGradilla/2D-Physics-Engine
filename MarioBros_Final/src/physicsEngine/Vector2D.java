package physicsEngine;

public class Vector2D {

	private double x,y;
	
	public Vector2D(){
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//Metodos estaticos
	//Vector nuevo (más para la logica del juego que para las actualizaciones)
	public static Vector2D add(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.x + v2.x, v1.y + v2.y);
	}
	
	//Distancia entre 2 puntos
	public static float distance(Vector2D a, Vector2D b) {
		double dx = a.x - b.x;
		double dy = a.y - b.y;
		return (float) Math.sqrt((dx*dx) + (dy*dy));
	}
	
	//Setters y getters
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	// Otros metodos
	//Sumar otro vector a este, es decir, acumular fuerzas
	public void add(Vector2D v) {
		this.x += v.x;
		this.y += v.y;
	}
	
	//Util para sacar direccion entre 2 puntos
	public void sub(Vector2D v) {
		this.x -= v.x;
		this.y -= v.y;
	}
	
	//Multiplicar por un escalar, sirve para aumentar velocidades
	public void scale(double num) {
		this.x *= num;
		this.y *= num;
	}
	
	//Sirve para calcular velocidad total
	public double magnitude() {
		return Math.sqrt(magnitudeSqr());
	}
	
	//Más rapida que el otro magnitude y menos costoso en memoria, util para radio de vision
	public double magnitudeSqr() {
		return (x * x) + (y * y);
	}
	
	//Convierte el vector en longitud 1, manteniendo su direccion
	//Util para saber la direccion sin importar la velocidad
	public void normalize() {
		double m = magnitude();
		if (m>0) {
			this.x /= m;
			this.y /= m;
		}
	}
	
	//Sirve para detectar angulos ( 
	public double punto(Vector2D v) {
		return (this.x * v.x) + (this.y * v.y);
	}
}
