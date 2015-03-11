
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
        this.iX = ix;
        this.iY = iy;

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
        this.iX += iDirection;
    }

    public Bomb getBomb() {
        return bBomb;
    }

    public class Bomb extends Sprite {

        private final String bomb = "alien.png";
        private boolean bDestroyed; 

        public Bomb(int ix, int iy) {
            //Se posicionan las bombas
            setDestroyed(true);
            this.iX = ix;
            this.iY = iy;
            Animacion aniBomb;
            
            // Imagen de la bomba  
            Image imBomb1 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien.png"));
            Image imBomb2 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien1.png"));
            Image imBomb3 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien2.png"));
            Image imBomb4 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien3.png"));
            Image imBomb5 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien4.png"));
            Image imBomb6 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien5.png"));
            Image imBomb7 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien6.png"));
            Image imBomb8 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien7.png"));
            Image imBomb9 = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("alien8.png"));
            
            aniBomb = new Animacion();
            aniBomb.sumaCuadro(imBomb1, 100);
            aniBomb.sumaCuadro(imBomb2, 100);
            aniBomb.sumaCuadro(imBomb3, 100);
            aniBomb.sumaCuadro(imBomb4, 100);
            aniBomb.sumaCuadro(imBomb5, 100);
            aniBomb.sumaCuadro(imBomb6, 100);
            aniBomb.sumaCuadro(imBomb7, 100);
            aniBomb.sumaCuadro(imBomb8, 100);
            aniBomb.sumaCuadro(imBomb9, 100);
            
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
