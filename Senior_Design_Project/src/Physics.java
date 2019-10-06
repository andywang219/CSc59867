import java.util.LinkedList;

public class Physics {

    // check if the A class runs into the B class
    public static boolean Collision(EntityA enta, LinkedList<EntityB> entb) {
        for (int i = 0; i < entb.size(); i++) { // loop through A class
            if (enta.getBounds().intersects(entb.get(i).getBounds())) {
                return true;
            }
        }
        return false;
    }

    // check if the B class runs into the A class
    public static boolean Collision(EntityB entb, LinkedList<EntityA> enta) {
        for (int i = 0; i < enta.size(); i++) { // loop through A class
            if (entb.getBounds().intersects(enta.get(i).getBounds())) {
                return true;
            }
        }
        return false;
    }
}