// Senior Design Fall 2019
// Team 4
// Player Class

import java.io.Serializable;
import java.util.Vector;

// Implementing Serializable allows instances of this class to be sent and received over the network with Object Streams
public class Player implements Serializable
{
    public Player(double x, double y)
    {
        graphic = "";
        this.x = x;
        this.y = y;
        this.ID = -1;
        setActive();
        projectiles = new Vector<Projectile>();
        inventory = new Vector<Projectile>();
    }

    // These values are in the Player class instead of the Window class so that a Server won't need the Window class at all
    public static final int WINDOW_WIDTH  = 640;
    public static final int WINDOW_HEIGHT = 480;

    private boolean isActive;
    private int ID;
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    private String graphic;
    private Vector<Projectile> projectiles;
    private Vector<Projectile> inventory;

    public Vector<Projectile> getProjectiles()
    {
        return projectiles;
    }

    public void setMovement(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDX()
    {
        return this.dx;
    }

    public double getDY()
    {
        return this.dy;
    }

    public void move()
    {
        // Border handling; Player objects cannot move out of bounds
        // There is an evident "bounce" when Players attempt to do so
        // This was kept for style
        if(x < 16)
            x = 16;
        else if(x > WINDOW_WIDTH - 48)
            x = WINDOW_WIDTH - 48;
        if(y < 16)
            y = 16;
        else if(y > WINDOW_HEIGHT - 48)
            y = WINDOW_HEIGHT - 48;
        if(dx > 0 && x < WINDOW_WIDTH - 16 || dx < 0 && x >= 16)
            x += dx;
        else if(dy > 0 && y < WINDOW_HEIGHT - 16 || dy < 0 && y >= 16)
            y += dy;

        // For convenient Object Streaming, Projectiles are stored and moved in the Player class
        for(int i = 0; i < projectiles.size(); i++)
        {
            if(!projectiles.get(i).move())
                projectiles.remove(i);
        }
    }

    // The active property is linked to rendering; if a Player is not "active", they will not be rendered
    public void setActive()
    {
        isActive = true;
    }

    public void setInactive()
    {
        isActive = false;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public String getGraphic()
    {
        return graphic;
    }

    public void setGraphic(String graphic)
    {
        this.graphic = graphic;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getID()
    {
        return ID;
    }
}