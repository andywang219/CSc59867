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
    private double k;
    private double c;
    private double d, direction;
    private double dx, dy;

    public Bullet(double x, double y) {
        super(x, y);
    }

    public Bullet(double x, double y, /*Textures tex, Game game,*/ Player p ,
                  double mx, double my, double k, double c, double direction,
                  double d) {
        super(x, y);
        this.mx = mx;
        this.my = my;
        this.k = k;
        this.c = c;
        this.d = d;
        this.direction = direction;
        //this.tex = tex;
        this.p = p;
        //this.game = game;
        anim = new Animation(p.getFruit().getCurrentFruit(), 3, 6, 1, 3);
        double angle =
                -Math.toDegrees(Math.atan2(x - mx,
                        y - my)) + 180;
        if (x < mx) {
            dx = 3;
        }
        if (x > mx) {
            dx = -3;
        }
        if (y < my) {
            dy = 3;
        }
        if (y > my) {
            dy = -3;
        }
    }

    public void tick() {

        x += dx;
        y += dy;

        System.out.println("x: " + x);
        System.out.println("y: " + y);

        /*if (Physics.Collision(this, game.eb)) {
            System.out.println("COLLISION DETECTED");
        }*/
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