/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senior_design_project;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Content_Panel extends JPanel implements Runnable
{
    public static int width;
    public static int height;
    
    private Thread thread;
    private boolean running = false;
    private BufferedImage img;
    private Graphics2D graphics;
    
    public Content_Panel(int width, int height)
    {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }
    
    @Override
    public void addNotify()
    {
        super.addNotify();
        
        if(thread == null)
        {
            thread = new Thread(this, "ContentThread");
            thread.start();
        }
    }
    
    public void init()
    {
        running = true;
        
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = (Graphics2D) img.getGraphics();
    }
    
    @Override
    public void run()
    {
        init();
        
        while(running)
        {
            update();
            render();
            draw();
        }
    }
    
    public void update() {}
    
    public void render() {} 
    
    public void draw() {}
}
