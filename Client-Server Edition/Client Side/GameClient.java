// Senior Design Fall 2019
// Team 4
// Main Game Client

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameClient
{
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Random random = new Random();

    // Spawn this Player at a random location on the map
    public static Player player = new Player(random.nextInt(Player.WINDOW_WIDTH - 32) + 32, random.nextInt(Player.WINDOW_HEIGHT - 32) + 32);
    
    private static GameKeyListener keyListener = new GameKeyListener();
    private static GameMouseListener mouseListener = new GameMouseListener();
    public static final double playerMovespeed = 14.0;

    private static class GameKeyListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            if(KeyEvent.getKeyText(e.getKeyCode()).equals("W"))
                player.setMovement(0, -playerMovespeed);
            else if(KeyEvent.getKeyText(e.getKeyCode()).equals("S"))
                player.setMovement(0, playerMovespeed);
            else if(KeyEvent.getKeyText(e.getKeyCode()).equals("A"))
                player.setMovement(-playerMovespeed, 0);
            else if(KeyEvent.getKeyText(e.getKeyCode()).equals("D"))
                player.setMovement(playerMovespeed, 0);
		}

		@Override
        public void keyReleased(KeyEvent e) 
        {
            // If the Player was moving, reset their state to standing still
            if(player.getDX() != 0.0 || player.getDY() != 0.0)
                player.setMovement(0.0, 0.0);
		}
    }

    private static class GameMouseListener implements MouseListener 
    {
        @Override
        public void mouseExited(MouseEvent e) 
        {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) 
        {
            
        }

        @Override
        public void mousePressed(MouseEvent e) 
        {
            // Performing vector normalization to correctly set the Projectile's path
            double x = (double) e.getX();
            double y = (double) e.getY();

            double total = Math.sqrt(Math.pow((player.getX() - x), 2) + Math.pow((player.getY() - y), 2));

            double dx = (x - player.getX()) / total;
            double dy = (y - player.getY()) / total;

            // Only one Projectile can be still airborn from a Player at a time
            if(player.getProjectiles().size() == 0)
            {
                // Randomly choose a Projectile type
                int projectileSelect = random.nextInt(3);
                String graphicName;
                if(projectileSelect == 0)
                    graphicName = "banana.png";
                else if(projectileSelect == 1)
                    graphicName = "watermelon.png";
                else
                    graphicName = "grapes.png";
                player.getProjectiles().add(new Projectile(player.getID(), player.getX(), player.getY(), dx, dy, graphicName));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) 
        {
            
        }
    }

    public static void main(String[] args) throws Exception
    {
        Window window = new Window();
        try
        { 
            Socket socket = new Socket(args[0], 59867);
            System.out.println("Connected");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            player.setGraphic("player_standing_1.png");

            int assignedID = (int) in.readObject();
            player.setID(assignedID);
            System.out.println("ID assigned: " + assignedID);

            window.addKeyListener(keyListener);
            window.addMouseListener(mouseListener);

            Runnable task = () -> 
            {
                try
                {
                    player.move();
                    out.flush();
                    out.reset();
                    out.writeObject(player);
                    out.flush();
                    out.reset();
                    PlayerTable received = (PlayerTable) in.readObject();
                    window.setTable(received);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            };
            
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            ses.scheduleAtFixedRate(task, 0, (1000/30), TimeUnit.MILLISECONDS);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
