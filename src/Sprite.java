
import java.awt.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 */
public class Sprite {

        private boolean bolVisible;
        private Image iImage;
        protected int iX;
        protected int iY;
        protected boolean iDying;
        protected int iDx;

        public Sprite() {
            bolVisible = true;
        }

        public void die() {
            bolVisible = false;
        }

        public boolean isVisible() {
            return bolVisible;
        }

        protected void setVisible(boolean visible) {
            this.bolVisible = visible;
        }

        public void setImage(Image image) {
            this.iImage = image;
        }

        public Image getImage() {
            return iImage;
        }

        public void setX(int x) {
            this.iX = x;
        }

        public void setY(int y) {
            this.iY = y;
        }
        public int getY() {
            return iY;
        }

        public int getX() {
            return iX;
        }

        public void setDying(boolean dying) {
            this.iDying = dying;
        }

        public boolean isDying() {
            return this.iDying;
        }
}
