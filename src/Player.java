
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 */
public class Player extends Sprite implements Commons{

    private final int iSTART_Y = 440; 
    private final int iSTART_X = 340;

    private final String sPlayer = "ship.png";
    private int iWidth;

    public Player() {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(sPlayer));

        iWidth = ii.getImage().getWidth(null); 

        setImage(ii.getImage());
        setX(iSTART_X);
        setY(iSTART_Y);
    }

    public void act() {
        iX += iDx;
        if (iX <= 2) 
            iX = 2;
        if (iX >= iBOARD_WIDTH - 2*iWidth) 
            iX = iBOARD_WIDTH - 2*iWidth;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            iDx = -2;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            iDx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            iDx = 0;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            iDx = 0;
        }
    }
}
