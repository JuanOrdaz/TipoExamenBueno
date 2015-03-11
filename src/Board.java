
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  
 *
 */

/**
 *
 * @author http://zetcode.com/
 * 
 * @author Diego Ponce García A00812547
 * @author Viridiana González Castro A01203167
 * 
 */

public class Board extends JPanel implements Runnable, Commons { 

    private final String sExpl = "explosion.png";
    private final String sAlienpix = "alien.png";
    
    private Dimension Dim;
    private ArrayList arrAliens;
    private Player plaPlayer;
    private Shot shot;
    
    //Coordenadas de alien
    private int iAlienX;
    private int iAlienY;
    
    private boolean bPause; //Booleana de Pausa
    private boolean bAgrad; //Booleana de Agradecimientos
    private boolean bInst;  //Booleana de Instrucciones
    
    //Direccion del movimiento
    private int iDirection;
    
    //Cantidad de vidas
    private int iDead;
    
    private SoundClip sndMuerte;        //Sonido muerte de jugador
    private SoundClip sndDisparo;       //Sonido disparo de nave
    private SoundClip sndColisionMalo;  //Sonido de colision con marciano
    private SoundClip sndSonidoFondo;	//Sonido de fondo
    
    long lTimePrev; //Tiempo en microsegundos del juego
    long lTimeAct; //Tiempo de actualizacion del juego
    
    //Booleana de juego corriendo
    private boolean bIngame;
    
    //Mensaje de Game Over
    private String sMessage;

    //Tread del juego
    private Thread thAnimator;
    
    

    public Board() 
    {

        addKeyListener(new TAdapter());
        setFocusable(true);
        Dim = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
        setBackground(Color.black);

        init();
        setDoubleBuffered(true);
    }

    public void addNotify() {
        super.addNotify();
        init();
    }

    public void init() {
        
        lTimePrev = 0; //Tiempo en microsegundos del juego
        lTimeAct = 0; //Tiempo de actualizacion en microsegundos del juego
        
        //X y Y del alien
        iAlienX = 150;
        iAlienY = 5;
        
        //Direccion del alien
        iDirection = -1;
        iDead = 0;
        
        //Estado del juego se inicia en true
        bIngame = true;
    
        //Mensaje de Game Over
        sMessage = "Game Over";

        //Arreglo de aliens
        arrAliens = new ArrayList();
        
        //Se inicia la pausa en falso
        bPause = false;
        
        //Se inician las instrucciones en falso
        bInst = false;
        
        //Se inician los agradecimientos en falso
        bAgrad = false;
        
        sndColisionMalo = new SoundClip("explosion1.wav");
        sndMuerte = new SoundClip("explosion2.wav");
        sndDisparo = new SoundClip("shoot.wav");
        // Sonido para la música de fondo
        sndSonidoFondo = new SoundClip("fondo.wav");
        sndSonidoFondo.setLooping(true);
        sndSonidoFondo.play();
        
        //Arreglo de los 24 aliens
        for (int i=0; i < 4; i++) {
            for (int j=0; j < 6; j++) {
                Alien alien = new Alien(iAlienX + 45 * j, iAlienY + 35 * i);
                arrAliens.add(alien);
            }
        }
        //Se crea al jugador
        plaPlayer = new Player();
        
        //Se crean los disparos
        shot = new Shot(-100,-100);
        
        //Thread
        if (thAnimator == null || !bIngame) {
            thAnimator = new Thread(this);
            thAnimator.start();
        }
    }

    public void drawAliens(Graphics graGraphics) 
    {
        //Iterador de aliens
        Iterator it = arrAliens.iterator();
        
        //Posicionamiento de aliens
        while (it.hasNext()) {

            Alien alien = (Alien) it.next();
     
            if (alien.isVisible()) {
                graGraphics.drawImage(alien.getImagen(), alien.getX(), 
                                                            alien.getY(), this);
            }

            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    public void drawPlayer(Graphics graGraphics) {

        if (plaPlayer.isVisible()) {
            graGraphics.drawImage(plaPlayer.getImagen(), plaPlayer.getX(), 
                                                        plaPlayer.getY(), this);
        }

        if (plaPlayer.isDying()) {
            plaPlayer.die();
            bIngame = false;
        }
    }

    public void drawShot(Graphics graGrafico) {
        if (shot.isVisible())
         graGrafico.drawImage(shot.getImagen(), shot.getX(), shot.getY(), this);
    }

    public void drawBombing(Graphics graGrafico) {

        Iterator i3 = arrAliens.iterator();

        while (i3.hasNext()) {
            Alien a = (Alien) i3.next();
            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                graGrafico.drawImage(b.getImagen(), b.getX(), b.getY(), this); 
            }
        }
    }
    
    public void drawPause(Graphics graGrafico) {
        if(bAgrad){
            Image imaImagen = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("autores.png"));
            graGrafico.drawImage(imaImagen, 0,0, this);
        }
        else if(bInst){
            Image imaImagen = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("instrucciones.png"));
            graGrafico.drawImage(imaImagen, 0, 0, this);
        }
        else{
            Image imaImagen = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("pausa.png"));
            graGrafico.drawImage(imaImagen, 0, 0, this);
        }
    }

    public void paint(Graphics graGrafico)
    {
        super.paint(graGrafico);
        Image imaFondo = Toolkit.getDefaultToolkit().getImage(
            this.getClass().getResource("fondo.png"));
        graGrafico.setColor(Color.black);
        graGrafico.fillRect(0, 0, Dim.width, Dim.height);
        graGrafico.setColor(Color.green);   
        graGrafico.drawImage(imaFondo, 0, 0, this);
        if(bIngame){
            if(!bPause){
                graGrafico.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
                drawAliens(graGrafico);
                drawPlayer(graGrafico);
                drawShot(graGrafico);
                drawBombing(graGrafico); 
            }
            else{
                drawPause(graGrafico);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        graGrafico.dispose();
    }

    public void gameOver()
    {
        Graphics graGrafico = this.getGraphics();

        graGrafico.setColor(Color.black);
        graGrafico.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);

        graGrafico.setColor(new Color(0, 32, 48));
        graGrafico.fillRect(50, BOARD_WIDTH/2 - 30, BOARD_WIDTH-100, 50);
        graGrafico.setColor(Color.white);
        graGrafico.drawRect(50, BOARD_WIDTH/2 - 30, BOARD_WIDTH-100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = this.getFontMetrics(small);

        graGrafico.setColor(Color.white);
        graGrafico.setFont(small);
        graGrafico.drawString(sMessage, (BOARD_WIDTH - metr.stringWidth(sMessage))/2, 
            BOARD_WIDTH/2);
    }

    public void animationCycle()  {

        lTimeAct = System.currentTimeMillis() - lTimePrev;
        lTimePrev += lTimeAct;
        
        Iterator itAliterator = arrAliens.iterator();
        while (itAliterator.hasNext()) {
            Alien a1 = (Alien) itAliterator.next();
            a1.setActualiza(lTimeAct);  
        }
        
        shot.setActualiza(lTimeAct);
        
        plaPlayer.setActualiza(lTimeAct);
         
        if (iDead == NUMBER_OF_ALIENS_TO_DESTROY) {
            bIngame = false;
            sMessage = "Game won!";
        }

        plaPlayer.act();

        //Controlando disparos
        if (shot.isVisible()) {
            Iterator it = arrAliens.iterator();
            

            while (it.hasNext()) {
                Alien alien = (Alien) it.next();
                

                if (alien.isVisible() && shot.isVisible()) {
                    
                        if(shot.intersecta(alien)) {    
                            alien.setDying(true);
                            iDead++;
                            shot.die();
                            sndColisionMalo.play();
                        }
                }
            }

            int iY = shot.getY();
            iY -= 4;
            if (iY < 0)
                shot.die();
            else shot.setY(iY);
        }

        //Iterador de aliens

         Iterator it1 = arrAliens.iterator();

         while (it1.hasNext()) {
             Alien a1 = (Alien) it1.next();
             int x = a1.getX();

             if (x  >= BOARD_WIDTH - BORDER_RIGHT && iDirection != -1) {
                 iDirection = -1;
                 Iterator i1 = arrAliens.iterator();
                 while (i1.hasNext()) {
                     Alien a2 = (Alien) i1.next();
                     a2.setY(a2.getY() + GO_DOWN);
                 }
             }

            if (x <= BORDER_LEFT && iDirection != 1) {
                iDirection = 1;

                Iterator i2 = arrAliens.iterator();
                while (i2.hasNext()) {
                    Alien a = (Alien)i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }


        Iterator it = arrAliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    bIngame = false;
                    sMessage = "Invasion!";
                }

                alien.act(iDirection);
            }
        }

        //Iterador de bombas
        Iterator i3 = arrAliens.iterator();
        Random generator = new Random();

        while (i3.hasNext()) {
            int shot = generator.nextInt(15);
            Alien a = (Alien) i3.next();
            Alien.Bomb b = a.getBomb();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());   
            }

            

            if (plaPlayer.isVisible() && !b.isDestroyed()) {
                
                    if(plaPlayer.intersecta(b)) {
                        plaPlayer.setDying(true);
                        b.setDestroyed(true);
                        sndMuerte.play();
                    }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 2);   
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    public void run() {

        lTimePrev = System.currentTimeMillis();

        while (bIngame) {
            
            repaint();
            
            if(!bPause) {
              animationCycle();  
            }
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        }
        gameOver();
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent keyEvent) {
            plaPlayer.keyReleased(keyEvent);
            
            if(keyEvent.getKeyCode() == keyEvent.VK_P) {
                if(bPause)
                    bPause = false;
                else
                    bPause = true;
            }
            else if(keyEvent.getKeyCode() == keyEvent.VK_I){
                if(bPause){
                    if(!bInst){
                        bInst = true;
                    }
                    else{
                        bInst = false;
                        bPause = false;
                    }
                }
                else{
                    bInst = true;
                    bPause = true;
                }
            }
            else if(keyEvent.getKeyCode() == keyEvent.VK_R){
                if(bPause){
                    if(!bAgrad){
                        bAgrad = true;
                    }
                    else{
                        bAgrad = false;
                        bPause = false;
                    }
                }
                else{
                    bAgrad = true;
                    bPause = true;
                }     
            }
            else if(keyEvent.getKeyCode() == keyEvent.VK_S) {
                try {
                    guardar();
                } catch (IOException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(keyEvent.getKeyCode() == keyEvent.VK_C) {
                try {
                    cargar();
                } catch (IOException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public void keyPressed(KeyEvent KeyEvent) {

            plaPlayer.keyPressed(KeyEvent);

            int iX = plaPlayer.getX();
            int iY = plaPlayer.getY();

            if (bIngame)
            {
                if (KeyEvent.getKeyCode() == KeyEvent.VK_ALT) {
                    if (!shot.isVisible())
                        shot = new Shot(iX, iY);
                        sndDisparo.play();
                }
            }
        }
    }
    
    /*
     * Guardar
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Container.<P>
     * En este metodo se guarda en un archivo de texto, todas las posiciones
     * de todo eljuego
     * 
     */
    
    public void guardar()  throws IOException{
       PrintWriter prwArchivo = new PrintWriter(new 
                                        FileWriter("save.txt"));
        //Coordenadas de los aliens
        Iterator it1 = arrAliens.iterator();

         while (it1.hasNext()) {
             Alien a1 = (Alien) it1.next();
             prwArchivo.println(a1.getX());
             prwArchivo.println(a1.getY());
             prwArchivo.println(a1.isVisible());
         }
         prwArchivo.println();
        //Coordenadas de player
        prwArchivo.println(plaPlayer.getX());
        prwArchivo.println(plaPlayer.getY());
        //Coordenadas del disparo
        prwArchivo.println(shot.getX());
        prwArchivo.println(shot.getY());
        
        prwArchivo.println(iDirection); //direcciones de los aliens
        prwArchivo.println(iDead); //Se guardan los aliens muertos
        
        prwArchivo.close(); //Se cierra el archivo
    }
     /*
     * Cargar
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Container.<P>
     * En este metodo se carga de un archivo de texto, todas las posiciones
     * de todo el juego
     * 
     */
    public void cargar() throws IOException{
        BufferedReader brwEntrada; //Archivo entrada
        try{
            brwEntrada = new BufferedReader(new FileReader("save.txt"));
        } catch (FileNotFoundException e){
            guardar();
            brwEntrada = new BufferedReader(new FileReader("save.txt"));
        }
        String sAux = brwEntrada.readLine(); 
        
        arrAliens.clear();
        //Coordenadas de los aliens
         while (sAux != "") {
             
             int iX = Integer.parseInt(sAux);
             int iY = Integer.parseInt(brwEntrada.readLine());
             Alien aux = new Alien(iX, iY);
             sAux = brwEntrada.readLine();
             if(sAux == "true")
                 aux.setDying(true);
             else
                 aux.setDying(false);
             arrAliens.add(aux);
             sAux = brwEntrada.readLine();
         }
         
         plaPlayer.setX(Integer.parseInt(brwEntrada.readLine()));
         plaPlayer.setY(Integer.parseInt(brwEntrada.readLine()));  
         
         shot.setX(Integer.parseInt(brwEntrada.readLine()));
         shot.setY(Integer.parseInt(brwEntrada.readLine()));
         
         iDirection = Integer.parseInt(brwEntrada.readLine());
         iDead = Integer.parseInt(brwEntrada.readLine());
         bPause = true;
         
        brwEntrada.close();
    }
}
