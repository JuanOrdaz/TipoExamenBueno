
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

    private String sShot = "shot.png";
    private final int iHspace = 6;
    private final int iVspace = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(sShot));
        setImage(ii.getImage());
        setX(x + iHspace);
        setY(y - iVspace);
    }
}