// EntityB's will not collide with other EntityB's

import java.awt.Graphics;

import java.awt.Rectangle;

// set required methods
public interface EntityB {
    public void tick();
    public void render(Graphics g);
    public Rectangle getBounds();

    public double getX();
    public double getY();
}