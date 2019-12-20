// Senior Design Fall 2019
// Team 4
// Central Game Server

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Vector;
import java.util.Random;
import java.lang.System;

public class GameServer
{
    // Thread pool with a thread limit
    static ExecutorService pool = Executors.newFixedThreadPool(32);
    protected static PlayerTable table = new PlayerTable();
    static int ID = 1;
    static Vector<Integer> disconnectedIDs = new Vector<Integer>();    
    static Random random = new Random();

    // Runnable task for spawning loot randomly
    public static class LootSpawn implements Runnable
    {
        @Override
        public void run()
        {
            // Loot spawn is limited by the number of players in game
            if(table.getLoot().size() < table.getSize() * 2)
            {
                int lootSelect = random.nextInt(3);
                String lootGraphic;
                if(lootSelect == 0)
                    lootGraphic = "grapes.png";
                else if(lootSelect == 1)
                    lootGraphic = "watermelon.png";
                else
                    lootGraphic = "banana.png";
                table.getLoot().add(new Projectile(random.nextInt(Player.WINDOW_WIDTH - 64) + 32, random.nextInt(Player.WINDOW_HEIGHT - 64) + 32, lootGraphic));
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        try
        {
            ServerSocket listener = new ServerSocket(59867);
            System.out.println("The server is up.");

            // Start loot spawning on a new thread 
            LootSpawn lootSpawner = new LootSpawn();
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            // Fixed to attempt a spawn every 3 seconds
            ses.scheduleAtFixedRate(lootSpawner, 0, 3000, TimeUnit.MILLISECONDS);

            // Each client gets a dedicated thread
            while(true)
            {
                pool.execute(new Connection(listener.accept()));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    // This method is "synchronized" -- only one thread can be running this method at one time
    private static synchronized int generateID()
    {
        // Players who previously disconnected release their IDs for new players to re-claim
        if(disconnectedIDs.size() == 0)
            return(ID++);
        int appropriateID = disconnectedIDs.get(0);
        disconnectedIDs.remove(0);
        return appropriateID;
    }

    private static class Connection implements Runnable
    {
        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;
        int ID;

        // Variables used for measuring statistics
        long timeClientConnected = System.currentTimeMillis();
        long timeThreadWasBusy = 0;

        public Connection(Socket socket) throws IOException
        {
            this.socket = socket;
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.ID = generateID();
            System.out.println("Player " + ID + " connected.");
        }

        @Override
        public void run()
        {
            try
            {
                // Firstly, inform the client what ID they have been assigned
                out.writeObject(new Integer(ID));
                Player received;
                while(((received = (Player) in.readObject()) != null))
                {
                    long startTime = System.currentTimeMillis();

                    // Register player's local game state
                    table.update(received);
                    out.reset();

                    // Inform the client of the global game state
                    out.writeObject(table);
                    out.flush();
                    out.reset();

                    // Gather statistics
                    long endTime = System.currentTimeMillis();
                    timeThreadWasBusy += (endTime - startTime);
                }
            }
            catch(EOFException e)
            {
                System.out.println("End of stream reached.");
            }
            catch(IOException e)
            {
                System.out.println("An IOException occurred.");
                System.out.println(e);
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException occurred.");
            }
            finally
            {
                // Display statistics
                System.out.println("Player " + ID + " stats:");
                long totalTimeConnected = System.currentTimeMillis() - timeClientConnected;
                System.out.println("Time the dedicated thread was busy / Total time client was connected: ");
                System.out.println(timeThreadWasBusy + " / " + totalTimeConnected);
                double ratio = ((double)timeThreadWasBusy) / ((double)totalTimeConnected);
                System.out.println("Ratio: " + ratio);
                try
                {
                    // Close socket 
                    socket.close();

                    // Ensure the disconnected Player's ID will be re-claimed when a new Player connects
                    disconnectedIDs.add(this.ID);

                    // Set the Player to inactive to prevent rendering after disconnect
                    table.getTable().get(this.ID - 1).setInactive();
                    
                    System.out.println("Socket closed (Player " + ID + ").");
                }
                catch(Exception e)
                {
                    System.out.println("Error closing socket.");
                }
            }
        }
    }
}
