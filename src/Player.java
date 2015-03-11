
import java.awt.Image;
import java.awt.Toolkit;
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

    private final int iSTART_Y = 460;       //inicio del Jugador en Y
    private final int iSTART_X = 279;       //Inicio del jugador en X

    //private final String player = "player.png"; //Imagen

    public Player() {

        Animacion aniPlayer;
        // Imagen de player   
	Image imPlayer1 = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("ship.png"));
        Image imPlayer2 = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("ship2.png"));
        Image imPlayer3 = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("ship3.png"));
       
        
        aniPlayer = new Animacion();
        aniPlayer.sumaCuadro(imPlayer1, 200);
        aniPlayer.sumaCuadro(imPlayer2, 200);
        aniPlayer.sumaCuadro(imPlayer3, 200);

        setAnimacion(aniPlayer);
        setX(iSTART_X);
        setY(iSTART_Y);
    }

    public void act() {
        //Movimiento de la nave
        iX += iDx;
        //No deja que se salga la nave
        if (iX <= 2) 
            iX = 2;
        if (iX >= iBOARD_WIDTH - 2*this.getAncho()) 
            iX = iBOARD_WIDTH - 2*this.getAncho();
    }

    public void keyPressed(KeyEvent keE) {
        int ikey = keE.getKeyCode();
        //Detecta hacia donde se mueve la nave y lo manda.
        if (ikey == KeyEvent.VK_LEFT)
        {
            iDx = -3;
        }

        if (ikey == KeyEvent.VK_RIGHT)
        {
            iDx = 3;
        }
    }

    public void keyReleased(KeyEvent keE) {
        int ikey = keE.getKeyCode();
        if (ikey == KeyEvent.VK_LEFT)
        {
            iDx = 0;
        }

        if (ikey == KeyEvent.VK_RIGHT)
        {
            iDx = 0;
        }
    }
}
