import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements EntityA {
    private Textures tex;
    private Game game;
    private Player p;
    private Animation anim;
    private BufferedImage currentFruit = null;

    public Bullet(double x, double y, Textures tex, Game game, Player p) {
        super(x, y);
        this.tex = tex;
        this.p = p;
        this.game = game;
        anim = new Animation(p.getFruit().getCurrentFruit(), 3, 6, 1, 3);
    }

    public void tick() {
        y -= 5;
        /*if (Physics.Collision(this, game.eb)) {
            System.out.println("COLLISION DETECTED");
        }*/
       anim.runAnimation();
    }

    public void render(Graphics g) {
        if (currentFruit == null) {
            currentFruit = p.getFruit().getCurrentFruit();
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