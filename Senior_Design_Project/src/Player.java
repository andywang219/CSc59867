import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
//import java.awt.image.BufferedImage;

public class Player extends GameObject implements EntityA {
    private double velX = 0;
    private double velY = 0;
    // private int speed;
    private Textures tex;
    private Animation anim;
    private double angle;
    private KeyInput input;
    private MouseInput mouseInput;
    private boolean isShooting;
    private boolean clicked = false;
    private Controller controller;
    // private Fruit has;
    // private int inventory;
    private Queue<Fruit> bag;


    public Player(double x, double y, int speed, Textures tex,/* Game game,
    */ KeyInput input, Controller controller, MouseInput mouseInput) {
        // intialize player's location in the game
        super(x, y); // from game object class
        // this.tex = tex;
        // this.game = game;
        this.input = input;
        this.mouseInput = mouseInput;
        this.controller = controller;
        anim = new Animation(tex.player, 3, 6, 1, 3); // frames, speed, 1 column by 3 rows (last 2 parameters)
        bag = new LinkedList<Fruit>();
        // format: frames, speed, col, row
    }

    public void tick() {
        x += velX;
        y += velY;

        // Keep player in bounds
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

        // move the player according to key pressed

        int tempVelX = 0;
        int tempVelY = 0;

        if (input.up.isPressed()) {
            tempVelY = -2;
        } 

        if (input.down.isPressed()) {
            tempVelY = 2;
        } 

        if (input.left.isPressed()) {
            tempVelX = -2;
        } 

        if (input.right.isPressed()) {
            tempVelX = 2;
        } 

        setVelX(tempVelX);
        setVelY(tempVelY);
        anim.runAnimation();

        if(mouseInput.getClicked()) {
            if (getFruit() != null && mouseInput.getShooting() == false) {
                System.out.println("shooting");
                mouseInput.setShooting(true);

                double xmouse = mouseInput.getX();
                double ymouse = mouseInput.getY();
                double xmain = getX();
                double ymain = getY();
                double k =
                        (ymain- ymouse) / (xmain - xmouse);
                double c = ymain - k * xmain;
                double direction = k * xmain + c;
                double d = 8D;

                double angle =
                        -Math.toDegrees(Math.atan2(xmain - xmouse,
                                ymain - ymouse)) + 180;

                System.out.println("mouse x: " + xmouse + "\nmouse y: " + ymouse + "\nk: "
                        + k + "\nc: " + c + "\ndirection: " + direction +
                        "\nd: " + d + "\nxmain: " + xmain + "\nymain: " + ymain + "\nangle: " + angle);

                controller.addEntity(new Bullet(getX(), getY(), this,
                        xmouse, ymouse, k, c, direction,
                        d));
            }
        }
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

//    @Override
//    public void mouseClicked(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        System.out.println("hello");
//        clicked = true;
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        clicked = false;
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
}