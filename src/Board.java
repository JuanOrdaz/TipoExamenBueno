
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 */
public class Board extends JPanel implements Runnable, Commons { 

    private Dimension dDim;
    private ArrayList alAliens;
    private Player plaPlayer;
    private Shot shoShot;

    private int iAlienX = 150;
    private int iAlienY = 5;
    private int iDirection = -1;
    private int iDeaths = 0;

    private boolean bIngame = true;
    private final String sExpl = "sExplosion.png";
    private final String sAlienpix = "alien.png";
    private String sMessage = "Game Over";

    private Thread thrAnimator;

    public Board() 
    {

        addKeyListener(new TAdapter());
        setFocusable(true);
        dDim = new Dimension(iBOARD_WIDTH, iBOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    public void addNotify() {
        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        alAliens = new ArrayList();

        ImageIcon ii = new ImageIcon(this.getClass().getResource(sAlienpix));

        for (int i=0; i < 4; i++) {
            for (int j=0; j < 6; j++) {
                Alien aliAlien = new Alien(iAlienX + 18*j, iAlienY + 18*i);
                aliAlien.setImage(ii.getImage());
                alAliens.add(aliAlien);
            }
        }

        plaPlayer = new Player();
        shoShot = new Shot();

        if (thrAnimator == null || !bIngame) {
            thrAnimator = new Thread(this);
            thrAnimator.start();
        }
    }

    public void drawAliens(Graphics g) 
    {
        Iterator iteIter = alAliens.iterator();

        while (iteIter.hasNext()) {
            Alien aliAlien = (Alien) iteIter.next();

            if (aliAlien.isVisible()) {
                g.drawImage(aliAlien.getImage(), aliAlien.getX(), aliAlien.getY(), this);
            }

            if (aliAlien.isDying()) {
                aliAlien.die();
            }
        }
    }

    public void drawPlayer(Graphics g) {

        if (plaPlayer.isVisible()) {
            g.drawImage(plaPlayer.getImage(), plaPlayer.getX(), plaPlayer.getY(), this);
        }

        if (plaPlayer.isDying()) {
            plaPlayer.die();
            bIngame = false;
        }
    }

    public void drawShot(Graphics g) {
        if (shoShot.isVisible())
            g.drawImage(shoShot.getImage(), shoShot.getX(), shoShot.getY(), this);
    }

    public void drawBombing(Graphics g) {

        Iterator iteIter = alAliens.iterator();

        while (iteIter.hasNext()) {
            Alien aliAlien = (Alien) iteIter.next();

            Alien.Bomb albBomb = aliAlien.getBomb();

            if (albBomb.isDestroyed()) {
                g.drawImage(albBomb.getImage(), albBomb.getX(), albBomb.getY(), this); 
            }
        }
    }

    public void paint(Graphics g)
    {
      super.paint(g);

      g.setColor(Color.black);
      g.fillRect(0, 0, dDim.width, dDim.height);
      g.setColor(Color.green);   

      if (bIngame) {

        g.drawLine(0, iGROUND, iBOARD_WIDTH, iGROUND);
        drawAliens(g);
        drawPlayer(g);
        drawShot(g);
        drawBombing(g);
      }

      Toolkit.getDefaultToolkit().sync();
      g.dispose();
    }

    public void gameOver()
    {

        Graphics graGraph = this.getGraphics();

        graGraph.setColor(Color.black);
        graGraph.fillRect(0, 0, iBOARD_WIDTH, iBOARD_HEIGHT);

        graGraph.setColor(new Color(0, 32, 48));
        graGraph.fillRect(50, iBOARD_WIDTH/2 - 30, iBOARD_WIDTH-100, 50);
        graGraph.setColor(Color.white);
        graGraph.drawRect(50, iBOARD_WIDTH/2 - 30, iBOARD_WIDTH-100, 50);

        Font fonSmall = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(fonSmall);

        graGraph.setColor(Color.white);
        graGraph.setFont(fonSmall);
        graGraph.drawString(sMessage, (iBOARD_WIDTH - metr.stringWidth(sMessage))/2, 
            iBOARD_WIDTH/2);
    }

    public void animationCycle()  {

        if (iDeaths == iNUMBER_OF_ALIENS_TO_DESTROY) {
            bIngame = false;
            sMessage = "Game won!";
        }

        // plaPlayer

        plaPlayer.act();

        // shoShot
        if (shoShot.isVisible()) {
            Iterator it = alAliens.iterator();
            int shoShotX = shoShot.getX();
            int shoShotY = shoShot.getY();

            while (it.hasNext()) {
                Alien alien = (Alien) it.next();
                int iAlienX = alien.getX();
                int iAlienY = alien.getY();

                if (alien.isVisible() && shoShot.isVisible()) {
                    if (shoShotX >= (iAlienX) && 
                        shoShotX <= (iAlienX + iALIEN_WIDTH) &&
                        shoShotY >= (iAlienY) &&
                        shoShotY <= (iAlienY+iALIEN_HEIGHT) ) {
                            ImageIcon ii = 
                                new ImageIcon(getClass().getResource(sExpl));
                            alien.setImage(ii.getImage());
                            alien.setDying(true);
                            iDeaths++;
                            shoShot.die();
                        }
                }
            }

            int y = shoShot.getY();
            y -= 4;
            if (y < 0)
                shoShot.die();
            else shoShot.setY(y);
        }

        // alAliens

         Iterator it1 = alAliens.iterator();

         while (it1.hasNext()) {
             Alien a1 = (Alien) it1.next();
             int x = a1.getX();

             if (x  >= iBOARD_WIDTH - iBORDER_RIGHT && iDirection != -1) {
                 iDirection = -1;
                 Iterator i1 = alAliens.iterator();
                 while (i1.hasNext()) {
                     Alien a2 = (Alien) i1.next();
                     a2.setY(a2.getY() + iGO_DOWN);
                 }
             }

            if (x <= iBORDER_LEFT && iDirection != 1) {
                iDirection = 1;

                Iterator i2 = alAliens.iterator();
                while (i2.hasNext()) {
                    Alien a = (Alien)i2.next();
                    a.setY(a.getY() + iGO_DOWN);
                }
            }
        }


        Iterator it = alAliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > iGROUND - iALIEN_HEIGHT) {
                    bIngame = false;
                    sMessage = "Invasion!";
                }

                alien.act(iDirection);
            }
        }

        // bombs

        Iterator i3 = alAliens.iterator();
        Random generator = new Random();

        while (i3.hasNext()) {
            int shoShot = generator.nextInt(15);
            Alien a = (Alien) i3.next();
            Alien.Bomb b = a.getBomb();
            if (shoShot == iCHANCE && a.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());   
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int plaPlayerX = plaPlayer.getX();
            int plaPlayerY = plaPlayer.getY();

            if (plaPlayer.isVisible() && !b.isDestroyed()) {
                if ( bombX >= (plaPlayerX) && 
                    bombX <= (plaPlayerX+iPLAYER_WIDTH) &&
                    bombY >= (plaPlayerY) && 
                    bombY <= (plaPlayerY+iPLAYER_HEIGHT) ) {
                        ImageIcon ii = 
                            new ImageIcon(this.getClass().getResource(sExpl));
                        plaPlayer.setImage(ii.getImage());
                        plaPlayer.setDying(true);
                        b.setDestroyed(true);;
                    }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 1);   
                if (b.getY() >= iGROUND - iBOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (bIngame) {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = iDELAY - timeDiff;

            if (sleep < 0) 
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
        gameOver();
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            plaPlayer.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {

          plaPlayer.keyPressed(e);

          int x = plaPlayer.getX();
          int y = plaPlayer.getY();

          if (bIngame)
          {
            if (e.isAltDown()) {
                if (!shoShot.isVisible())
                    shoShot = new Shot(x, y);
            }
          }
        }
    }
}
