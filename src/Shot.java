import java.awt.Image;
import java.awt.Toolkit;
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
public class Shot extends Sprite {

    private final int iH_SPACE = 6;
    private final int iV_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        Animacion aniShot;
            // Imágenes del disparo de la nave    
            Image imShot1 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("ball.png"));
            Image imShot2 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("ball2.png"));
            Image imShot3 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("ball3.png"));
         
         
            aniShot = new Animacion();
            aniShot.sumaCuadro(imShot1, 100);
            aniShot.sumaCuadro(imShot2, 100);
            aniShot.sumaCuadro(imShot3, 100);

            setAnimacion(aniShot);
            
        setX(x + iH_SPACE);
        setY(y - iV_SPACE);
    }
}