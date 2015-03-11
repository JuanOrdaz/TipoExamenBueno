
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 * 
 * @author Diego Ponce García A00812547
 * @author Viridiana González Castro A01203167
 * 
 */

public class Sprite {

        private boolean bVisible;       //Visibilidad del sprite
        private Animacion aniSprite;    //Animacion del sprite
        protected int iX;               //X del sprite
        protected int iY;               //Y del sprite
        protected boolean bDying;       //Muerte del sprite
        protected int iDx;

        public Sprite() {
            bVisible = true;
        }

        public void die() {
            bVisible = false;
        }

        public boolean isVisible() {
            return bVisible;
        }

        protected void setVisible(boolean bvisible) {
            this.bVisible = bvisible;
        }

        public void setAnimacion(Animacion aniAnimacion) {
            this.aniSprite = aniAnimacion;
        }
        
        public void setActualiza(long lTime) {
            this.aniSprite.actualiza(lTime);
        }

        public Animacion getAnimacion() {
            return aniSprite;
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
        
        //Regresa a la imagen original
        public Image getImagen() {
            return aniSprite.getImagen();
        }
        
        //Ancho de la imagen
        public int getAncho() {
            ImageIcon imaIcono;
            imaIcono = new ImageIcon(this.getImagen());
            return imaIcono.getIconWidth();
        }
        
        //Alto de la imagen
        public int getAlto() {
            ImageIcon imaIcono;
            imaIcono = new ImageIcon(this.getImagen());
            return imaIcono.getIconHeight();
        }

        public void setDying(boolean dying) {
            this.bDying = dying;
        }

        public boolean isDying() {
            return this.bDying;
        }
        
        //Intersección de sprites
        public boolean intersecta(Object objObjeto) {
        if (objObjeto instanceof Sprite) {
            Rectangle rctEsteObjeto = new Rectangle(this.getX(), this.getY(),
                    this.getAncho(), this.getAlto());
            Sprite spriObjeto = (Sprite) objObjeto;
            Rectangle rctObjetoParam = new Rectangle(spriObjeto.getX(),
                    spriObjeto.getY(), spriObjeto.getAncho(), spriObjeto.getAlto());
            return rctEsteObjeto.intersects(rctObjetoParam);
        } 
        else {
            return false;
        }
    }
}

