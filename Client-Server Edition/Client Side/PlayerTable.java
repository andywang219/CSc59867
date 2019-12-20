// Senior Design Fall 2019
// Team 4
// Player Table Class

import java.io.Serializable;
import java.util.Vector;

// Implementing Serializable allows instances of this class to be sent and received over the network with Object Streams
public class PlayerTable implements Serializable
{
    private Vector<Player> table;
    private Vector<Projectile> loot;

    public PlayerTable()
    {
        table = new Vector<Player>();
        loot = new Vector<Projectile>();
    }

    public Vector<Player> getTable()
    {
        return this.table;
    }

    public Vector<Projectile> getLoot()
    {
        return this.loot;
    }

    public int getSize()
    {
        return this.table.size();
    }

    public void update(Player player)
    {
        if(table.size() < player.getID())
        {
            table.add(player);
        }
        else
        {
            table.set(player.getID() - 1, player);
        }
    }
}