import java.util.LinkedList;
import java.util.Random;
import java.awt.Graphics;

public class Controller {
    // Linked lists are nice for things we may have more than 5 of on a screen at once
    private LinkedList<EntityA> ea = new LinkedList<EntityA>();
    private LinkedList<EntityB> eb = new LinkedList<EntityB>();
    private Game game;
    EntityA enta;
    EntityB entb;

    Random r = new Random();
    private Textures tex;

    public Controller(Textures tex, Game game) {
        this.tex = tex;
        this.game = game;
        // for (int i = 0; i < 20; i+=5) {
        //     addEntity(new Enemy(r.nextInt(640), 10, tex));
        // }
    }

    public void createEnemy(int enemy_count) {
        for (int i = 0; i < enemy_count; i++) {
            addEntity(new Enemy(r.nextInt(Game.WIDTH - 32), -10, tex, this, game));
        }
    }

    public void createPlayer(Player p) {
        addEntity(p);
    }

    public void createFruit(Player p) {
        addEntity(new Fruit(r.nextInt(Game.WIDTH - 32), r.nextInt(Game.HEIGHT - 32), tex, this, game, p)); 
    }

    public void tick() {
        // 'A' CLASS
        for (int i = 0; i < ea.size(); i++) {
            enta = ea.get(i);
            enta.tick();
        }

        // 'B' CLASS
        for (int i = 0; i < eb.size(); i++) {
            entb = eb.get(i);
            entb.tick();
        }
    }

    public void render(Graphics g) {
        // 'A' CLASS
        for (int i = 0; i < ea.size(); i++) {
            enta = ea.get(i);
            enta.render(g);
        }

        // 'B' CLASS
        for (int i = 0; i < eb.size(); i++) {
            entb = eb.get(i);
            entb.render(g);
        }
    }

    public void addEntity(EntityA block) {
        ea.add(block);
    }

    public void removeEntity(EntityA block) {
        ea.remove(block);
    }

    public void addEntity(EntityB block) {
        eb.add(block);
    }

    public void removeEntity(EntityB block) {
        eb.remove(block);
    }

    public LinkedList<EntityA> getEntityA() {
        return ea;
    }

    public LinkedList<EntityB> getEntityB() {
        return eb;
    }

}