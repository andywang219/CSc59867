import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends GameObject implements EntityB {
    private double x, y;
    private Textures tex;
    private Game game;
    private Controller c;
    private Animation anim;
    private Random r = new Random();
    private int speed = r.nextInt(3) + 1;
    

    public Enemy(double x, double y, Textures tex, Controller c, Game game) {
        super(x, y);
        this.tex = tex;
        this.c = c;
        this.game = game;
        anim = new Animation(tex.enemy, 3, 6, 1, 3);
    }

    // if object moves, you want a tick method -> it updates
    public void tick() {
        y += speed;
        // When ship gets to bottom of screen, make it warp back to the top and at a random new x
        if(y > Game.HEIGHT - 32) {
            speed = (r.nextInt(3) + 1);
            y = -10;
            x = r.nextInt(Game.WIDTH) - 32;
        }
        if (Physics.Collision(this, game.ea)) {
            c.removeEntity(this);
            game.setEnemy_killed(game.getEnemy_killed() + 1);
        }
        anim.runAnimation();
    }

    public void render(Graphics g) {
        // g.drawImage(tex.enemy, (int)x, (int)y, null);
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

    public void setY(double y) {
        this.y = y;
    }
}