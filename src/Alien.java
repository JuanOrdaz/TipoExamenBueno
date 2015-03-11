
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
public class Alien extends Sprite {

    private Bomb bBomb;

    public Alien(int ix, int iy) {
        //Posición del alien
        this.ix = ix;
        this.iy = iy;

        //Se crea la bomba donde está el alien
        bBomb = new Bomb(ix, iy);
        Animacion aniAlien;
        // Imagenes del alien    
	Image imAlien1 = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("alien.png"));
        Image imAlien2 = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("alien2.png"));
        
        aniAlien = new Animacion();
        aniAlien.sumaCuadro(imAlien1, 500);
        aniAlien.sumaCuadro(imAlien2, 500);
        
        setAnimacion(aniAlien);

    }

    public void act(int iDirection) {
        this.ix += iDirection;
    }

    public Bomb getBomb() {
        return bBomb;
    }

    public class Bomb extends Sprite {

        private final String bomb = "bomb.png";
        private boolean bDestroyed; 

        public Bomb(int ix, int iy) {
            //Se posicionan las bombas
            setDestroyed(true);
            this.ix = ix;
            this.iy = iy;
            Animacion aniBomb;
            
            // Imagen de la bomba  
            Image imBomb1 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("bomb.png"));
            Image imBomb2 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("bomb2.png"));
            
            aniBomb = new Animacion();
            aniBomb.sumaCuadro(imBomb1, 100);
            aniBomb.sumaCuadro(imBomb2, 100);
            setAnimacion(aniBomb);
        }
        //set
        public void setDestroyed(boolean bdestroyed) {
            this.bDestroyed = bdestroyed;
        }
        //get
        public boolean isDestroyed() {
            return bDestroyed;
        }
    }
}
