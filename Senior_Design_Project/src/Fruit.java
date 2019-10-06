import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Random;

// For static fruit on the screen
public class Fruit extends GameObject implements EntityB {
    private Textures fruits;
    private ArrayList<BufferedImage> current_fruit;
    private ArrayList<BufferedImage> current_fruit_a;
    private Game game;
    private Player p;
    private Controller c;
    private Random r = new Random();
    private int index;
    private boolean is_spawned = false;

    public Fruit(double x, double y, Textures fruits, Controller c, Game game, Player p) {
        super(x, y);
        this.fruits = fruits;
        this.c = c;
        this.p = p;
        this.game = game;
        current_fruit = new ArrayList<BufferedImage>();
        current_fruit_a = new ArrayList<BufferedImage>();
        // The current fruit
        current_fruit.add(fruits.apple);
        current_fruit.add(fruits.orange);
        current_fruit.add(fruits.watermelon);
        current_fruit.add(fruits.grape);
        current_fruit.add(fruits.banana);
        // animated fruit
        current_fruit_a.add(fruits.apple_a);
        current_fruit_a.add(fruits.orange_a);
        current_fruit_a.add(fruits.watermelon_a);
        current_fruit_a.add(fruits.grape_a);
        current_fruit_a.add(fruits.banana_a);
    }

    public void tick() {
        // Prevent fruit from spawning off screen in case it does
        if(x <= 0) x = 0;
        if(x >= Game.WIDTH - 32) x = Game.WIDTH - 32;
        if(y <= 0) y = 0;
        if(y >= Game.HEIGHT - 32) y = Game.HEIGHT - 32;
        if (Physics.Collision(this, game.ea)) {
            p.addFruit(this);
            c.removeEntity(this);
            //game.setEnemy_killed(game.getEnemy_killed() + 1);
        }
    }

    public void render(Graphics g) {
        if (!is_spawned) {
            index = r.nextInt(5); // random index for current fruit
            g.drawImage(current_fruit.get(index), (int) x, (int) y, null); // randomly spawn specific fruit
            is_spawned = true;
        }
        g.drawImage(current_fruit.get(index), (int) x, (int) y, null); // randomly spawn specific fruit
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32); // width and height to establish the proper hitbox around a sprite
    }

    public BufferedImage getCurrentFruit() {
        return current_fruit_a.get(index);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}