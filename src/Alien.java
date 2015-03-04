
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
    private final String sShot = "alien.png";

    public Alien(int x, int y) {
        this.iX = x;
        this.iX = y;

        bBomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sShot));
        setImage(ii.getImage());

    }

    public void act(int direction) {
        this.iX += direction;
    }

    public Bomb getBomb() {
        return bBomb;
    }

    public class Bomb extends Sprite {

        private final String sBomb = "bomb.png";
        private boolean bolDestroyed;

        public Bomb(int x, int y) {
            setDestroyed(true);
            this.iX = x;
            this.iX = y;
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sBomb));
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {
            this.bolDestroyed = destroyed;
        }

        public boolean isDestroyed() {
            return bolDestroyed;
        }
    }
}
