// Senior Design Fall 2019
// Team 4
// Window (Graphics Handling)

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
public class Window extends JFrame 
{
    private GameCanvas canvas;
    PlayerTable table;
 
    public Window() 
    {
        canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(Player.WINDOW_WIDTH, Player.WINDOW_HEIGHT));
    
        Container cp = getContentPane();
        cp.add(canvas);
    
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Senior Design Project");
        setVisible(true); 
        setResizable(false);
    }

    public GameCanvas getCanvas()
    {
        return this.canvas;
    }

    public void setTable(PlayerTable table)
    {
        this.table = table;
        canvas.repaint();
    }
 
    private class GameCanvas extends JPanel 
    {
        @Override
        public void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            g.clearRect(0, 0, Player.WINDOW_WIDTH, Player.WINDOW_HEIGHT);
            setBackground(Color.BLACK);
            try
            {
                for(int i = 0; i < table.getLoot().size(); i++)
                {
                    BufferedImage img = ImageIO.read(new File(table.getLoot().get(i).getGraphic()));
                    g.drawImage(img, (int)table.getLoot().get(i).getX(), (int)table.getLoot().get(i).getY(), null);
                }
                for(int i = 0; i < table.getSize(); i++)
                {
                    if(table.getTable().get(i).isActive())
                    {
                        BufferedImage img;
                        if(i == GameClient.player.getID() - 1)
                            img = ImageIO.read(new File("this_player_standing_1.png"));
                        else
                            img = ImageIO.read(new File(table.getTable().get(i).getGraphic()));
                        g.drawImage(img, (int)table.getTable().get(i).getX(), (int)table.getTable().get(i).getY(), null);
                        for(int j = 0; j < table.getTable().get(i).getProjectiles().size(); j++)
                        {
                            BufferedImage projectile = ImageIO.read(new File(table.getTable().get(i).getProjectiles().get(j).getGraphic()));
                            g.drawImage(projectile, (int)table.getTable().get(i).getProjectiles().get(j).getX(), (int)table.getTable().get(i).getProjectiles().get(j).getY(), null);
                        }
                    }
                }
            }
            catch(Exception e)
            {
                // System.out.println(e);
            }
        }
    }
}