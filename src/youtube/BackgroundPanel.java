/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtube;

/**
 *
 * @author Wise-SW
 */
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel  
    {  
        Image image;  
        public BackgroundPanel()  
        {  
            try  
            {  
                image = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("/ToolIcon/background.jpg"), "background.jpg"));  
            }  
            catch (Exception e) { /*handled in paintComponent()*/ }  
        }  
  
        @Override  
        protected void paintComponent(Graphics g)  
        {  
        super.paintComponent(g);   
        if (image != null)  
            g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);  
        }  
    }  
