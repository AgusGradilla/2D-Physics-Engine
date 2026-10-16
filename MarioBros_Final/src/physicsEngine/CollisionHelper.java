package physicsEngine;

public class CollisionHelper {
    //Los ejes interseccionan con el del objeto en el argumento?
	public static boolean intersects(Collider2D a, Collider2D b) {
        return !(a.getRight() <= b.getLeft() || a.getLeft() >= b.getRight() ||
                 a.getBottom() <= b.getTop() || a.getTop() >= b.getBottom());
    }

	//Si hay interseccion, que tanto chocan los objetos?
    public static Vector2D getMTV(Collider2D a, Collider2D b) {
        if (!intersects(a, b)) return null;

        double dx1 = b.getRight() - a.getLeft();
        double dx2 = b.getLeft() - a.getRight();
        double dy1 = b.getBottom() - a.getTop();
        double dy2 = b.getTop() - a.getBottom();

        double xOverlap = Math.abs(dx1) < Math.abs(dx2) ? dx1 : dx2;
        double yOverlap = Math.abs(dy1) < Math.abs(dy2) ? dy1 : dy2;

        if (Math.abs(xOverlap) < Math.abs(yOverlap)) {
            return new Vector2D(xOverlap, 0);
        } else {
            return new Vector2D(0, yOverlap);
        }
    }
}
