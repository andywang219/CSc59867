/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senior_design_project;

import javax.swing.JFrame;

public class Window extends JFrame
{
    public Window()
    {
        setTitle("Senior Design Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new Content_Panel(720, 480));
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
