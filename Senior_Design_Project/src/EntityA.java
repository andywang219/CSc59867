// EntityA's will not collide with other EntityA's

import java.awt.Graphics;

import java.awt.Rectangle;

// set required methods
public interface EntityA {
    public void tick();
    public void render(Graphics g);
    public Rectangle getBounds();

    public double getX();
    public double getY();
}