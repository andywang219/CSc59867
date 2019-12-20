// Senior Design Fall 2019
// Team 4
// Projectile Class

import java.io.Serializable;
import java.util.Vector;
import java.lang.Math;

// Implementing Serializable allows instances of this class to be sent and received over the network with Object Streams
public class Projectile implements Serializable
{
    // Constructor 1: a static Projectile (lootable: it can be picked up by players)
    public Projectile(double x, double y, String graphic)
    {
        this.sourceID = -1;
        this.x = x;
        this.y = y;
        this.graphic = graphic;
        this.lootable = true;
    }

    // Constructor 2: a mobile Projectile (thrown by a Player)
    public Projectile(int sourceID, double x, double y, double dx, double dy, String graphic)
    {
        this.sourceID = sourceID;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.sx = x;
        this.sy = y;
        this.graphic = graphic;
        this.lootable = false;
        setStats();
    }

    // Value for max distance Projectile travels before disappearing
    public static double MAX_FLY;
    // Value for travel speed 
    public static double FLY_SPEED;

    private int sourceID;
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected double sx;
    protected double sy;
    private String graphic;
    private boolean lootable;

    // Sets the speed and range of the Projectile based on which kind it is
    public void setStats()
    {
        if(graphic.equals("banana.png"))
        {
            MAX_FLY = 240.0;
            FLY_SPEED = 40.0;
        }
        else if(graphic.equals("watermelon.png"))
        {
            MAX_FLY = 140.0;
            FLY_SPEED = 20.0;
        }
        else if(graphic.equals("grapes.png"))
        {
            MAX_FLY = 180.0;
            FLY_SPEED = 50.0;
        }
    }

    public boolean move()
    {
        // Returns false if the Projectile has reached its max range
        if(Math.sqrt(Math.pow((x - sx), 2) + Math.pow((y - sy), 2)) > MAX_FLY)
        {
            // System.out.println("Projectile path ended");
            return false;
        }
        x += dx * FLY_SPEED;
        y += dy * FLY_SPEED;
        return true;
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

    public void setMovement(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }
}