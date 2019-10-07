import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Queue;
import java.util.LinkedList;
//import java.awt.image.BufferedImage;

public class Player extends GameObject implements EntityA {
    private double velX = 0;
    private double velY = 0;
    private Textures tex;
    private Game game;
    private Animation anim;
    // private Fruit has;
    // private int inventory;
    private Queue<Fruit> bag;


    public Player(double x, double y, Textures tex, Game game) {
        // intialize player's location in the game
        super(x, y); // from game object class
        this.tex = tex;
        this.game = game;
        anim = new Animation(tex.player, 3, 6, 1, 3); // frames, speed, 1 column by 3 rows (last 2 parameters)
        bag = new LinkedList<Fruit>();
        // format: frames, speed, col, row
    }

    public void tick() {
        x += velX;
        y += velY;

        if(x <= 0) {
            x = 0;
        }
        if(x >= Game.WIDTH - 32) {
            x = Game.WIDTH - 32;
        }
        if(y <= 0) {
            y = 0;
        }
        if(y >= Game.HEIGHT - 32) {
            y = Game.HEIGHT - 32;
        }
        anim.runAnimation();
    }

    public void render(Graphics g) {
        // g.drawImage(tex.player, (int)x, (int)y, null);
        anim.drawAnimation(g, x, y, 0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32); // width and height to establish the proper hitbox around a sprite
    }

    public void addFruit(Fruit f) {
        if (bag.size() < 3) {
            bag.add(f);
        }
    }

    public Fruit getFruit() {
        if (bag.peek() != null) {
            return bag.peek();
        }
        return null;
    }

    public int getInventory() {
        return bag.size();
    }

    public void useFruit() {
        bag.remove();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }
}