import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable { // taking everything from canvas class and bringing it to Game
                                                       // (ruunable requires a certain method)
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 1050;//950;
    public static final int HEIGHT = 800;//600;
    public static final int SCALE = 4;
    public final String TITLE = "Fruit Dodgeball";
    public KeyInput input;
    public MouseInput mouseInput;
    public static JFrame gameFrame;

    private boolean running = false;
    private Thread thread;

    // buffer image: buffer/load the image before it projects and renders it -
    // buffers whole window and access RGB
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage spriteSheet2 = null;
    private BufferedImage background = null;
    private BufferedImage fruits = null;

    private int enemy_count = 3; // how many space ships to spawn
    private int enemy_killed = 0; // # of space ships killed

    private Player p; //p7777;
    private Controller c;
    private Textures tex;
    private int count;

    public LinkedList<EntityA> ea;
    public LinkedList<EntityB> eb;

    public void init() {
        requestFocus(); // bring focus to the screen
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            spriteSheet = loader.loadImage("./res/pink_ss.png");
            spriteSheet2 = loader.loadImage("./res/red_ss.png");
            fruits = loader.loadImage("./res/fruits_ss.png");
            background = loader.loadImage("./res/background.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // addKeyListener(new KeyInput(this));
        input = new KeyInput(this);
        mouseInput = new MouseInput(this);
        tex = new Textures(this);
        c = new Controller(tex, this);
        
        p = new Player(200, 200, 1, tex, input, c, mouseInput);
        // p7777 = new Player(400, 400, 1, tex, input, c, mouseInput);
        c.createPlayer(p);
        // c.createPlayer(p7777);
        c.createEnemy(enemy_count);
        ea = c.getEntityA();
        eb = c.getEntityB();
    }

    private synchronized void start() { // to start our thread
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() { // to end our thread
        if (!running)
            return;
        running = false;
        try {
            thread.join(); // joins all threads together and waits for them to die
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    public void run() {
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0; // 60 fps
        double ns = 1000000000 / amountOfTicks;
        double delta = 0; // time passed so that it can catch up
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        // updates the game a certain amount of times
        while (running) {
            // this would be the game loop
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000; // prevent looping again
                // System.out.println(updates + " Ticks, Fps " + frames);
                gameFrame.setTitle(updates + " Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    // eveything in game that updates
    private void tick() {
        p.tick();
        c.tick();
        if (enemy_killed >= enemy_count) {
            enemy_count += 2;
            enemy_killed = 0;
            c.createEnemy(enemy_count);
        }
        if (count++ % 150 == 0) {
            c.createFruit(p);
        }
        // System.out.println(count++);
    }

    // eveything in game that renders
    private void render() {
        // buffer strategy handles all buffering behind the scenes
        BufferStrategy bs = this.getBufferStrategy(); // accessed from Canvas; eventually need to dispose of this

        // initialize once and done
        if (bs == null) {
            createBufferStrategy(3); // 3 means 3 buffers (buffer behind buffer behind screen and shoots to screen:
                                     // improves time/performance)
            return;
        }

        Graphics g = bs.getDrawGraphics();
        ////////////////////////// In between these, we can draw out stuff
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(background, 0, 0, null);
        p.render(g);
        c.render(g);
        //////////////////////////
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Dimension: class to encapsulate a width and heigh
                                                             // dimension
        game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        game.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        JFrame frame = new JFrame(game.TITLE);
        gameFrame = frame;

        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public BufferedImage getSpriteSheet2() {
        return spriteSheet2;
    }

    public BufferedImage getFruitsSheet() {
        return fruits;
    }

    public void setEnemy_count(int enemy_count) {
        this.enemy_count = enemy_count;
    }

    public void setEnemy_killed(int enemy_killed) {
        this.enemy_killed = enemy_killed;
    }

    public int getEnemy_count() {
        return enemy_count;
    }

    public int getEnemy_killed() {
        return enemy_killed;
    }
}