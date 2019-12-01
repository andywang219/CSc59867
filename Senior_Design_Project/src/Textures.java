import java.awt.image.BufferedImage;

public class Textures {
    private SpriteSheet ss = null;
    private SpriteSheet ss2 = null;
    private SpriteSheet fruits_ss = null;
    public BufferedImage orange, apple, grape, watermelon, banana; // static images
    public BufferedImage orange_a, apple_a, grape_a, watermelon_a, banana_a; // animated
    public BufferedImage player;
    public BufferedImage missile;
    public BufferedImage enemy;

    public Textures(Game game) {
        ss = new SpriteSheet(game.getSpriteSheet());
        ss2 = new SpriteSheet(game.getSpriteSheet2());
        fruits_ss = new SpriteSheet(game.getFruitsSheet());
        getTextures();
    }

    private void getTextures() { 
        player = ss.grabImage(3, 1, 32, 192); // 96 for animation
        enemy = ss2.grabImage(2, 1, 32, 128);

        grape = fruits_ss.grabImage(1, 1, 32, 32); // unanimated
        grape_a = fruits_ss.grabImage(1, 1, 32, 128); // animated

        orange = fruits_ss.grabImage(2, 1, 32, 32); // unanimated
        orange_a = fruits_ss.grabImage(2, 1, 32, 128); // animated

        watermelon = fruits_ss.grabImage(3, 1, 32, 32); // unanimated
        watermelon_a = fruits_ss.grabImage(3, 1, 32, 128); // animated

        apple = fruits_ss.grabImage(4, 1, 32, 32); // unanimated
        apple_a = fruits_ss.grabImage(4, 1, 32, 128); // animated

        banana = fruits_ss.grabImage(5, 1, 32, 32); // unanimated
        banana_a = fruits_ss.grabImage(5, 1, 32, 128); // animated
        
        missile = fruits_ss.grabImage(3, 1, 32, 128);
    }
}