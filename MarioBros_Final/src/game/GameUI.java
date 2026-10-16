package game;

import java.awt.Graphics;

public interface GameUI { //Extremadamente similar a GameObject, pero no ocupa RigidBody ni Collider
    void update(double dt);
    void draw(Graphics g);
}