import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    private boolean clicked = false;
    private boolean isShooting;
    private double x;
    private double y;

    public MouseInput(Game game) {
        game.addMouseListener(this);
    }

    public boolean getClicked() {
        return clicked;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean getShooting() {
        return isShooting;
    }

    public void setShooting(boolean shot) {
        isShooting = shot;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        clicked = true;
        isShooting = false;
        x = e.getX();
        y = e.getY();

        System.out.println("mx: " + x);
        System.out.println("my: " + y);
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("release");
        clicked = false;
        isShooting = true;
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
