import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements EntityA {
    //private Textures tex;
    //private Game game;
    private Player p;
    private Animation anim;
    private BufferedImage currentFruit = null;
    private double mx;
    private double my;
    private double dirX, dirY;
    private int bulletSpeed = 5;
//    private double timeStep = 1d/60d;

    public Bullet(double x, double y) {
        super(x, y);
    }

    public Bullet(double x, double y, Player p, double mx, double my) {
        super(x, y);
        this.mx = mx;
        this.my = my;
        this.p = p;
        anim = new Animation(p.getFruit().getCurrentFruit(), 3, 6, 1, 3);

        // calculate the direction vector for it's movement
        double dirX = mx - x;
        double dirY = my - y;

        // "normalize" the direction vector to be able to use the speed variable
        double dirLength= Math.sqrt(dirX * dirX + dirY * dirY);
        dirX = dirX / dirLength;
        dirY = dirY / dirLength;

        this.dirX = dirX;
        this.dirY = dirY;

    }

    public void tick() {
        // update the (x, y) position of the bullet
        x = x + (dirX * bulletSpeed);
        y = y + (dirY * bulletSpeed);

       anim.runAnimation();
    }

    public void render(Graphics g) {
        if (currentFruit == null) {
            if (p.getFruit() != null) {
                currentFruit = p.getFruit().getCurrentFruit();
                p.useFruit();
            }
        }
        // g.drawImage(currentFruit, (int) x, (int) y, null);
        anim.drawAnimation(g, x, y, 0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32); // width and height to establish the proper hitbox around a sprite
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}