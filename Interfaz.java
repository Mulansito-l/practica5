import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.*;

public class Interfaz{
    private MultiPieceMino juego;
    private JFrame mainWindow;

    public Interfaz(){
        mainWindow = new JFrame("Curly's Multi Piece Mino by Diego Castañeda"); 
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mostrarMenuPrincipal(){
        MenuPrincipal menu;
        try{
            menu = new MenuPrincipal(ImageIO.read(new File("recursos/fondo.png")));
        }catch(IOException e){
            System.out.println("No se pudo crear el menu principal :'c");
            return;
        }

        mainWindow.setContentPane(menu);
        mainWindow.setVisible(true);
        mainWindow.repaint();
    }

    public void mostrarPantallaJuego(){
        Canvas canvas;
        try{
            canvas = new Canvas(10000, 10000,ImageIO.read(new File("recursos/tablero.png")));
        }catch(IOException e){
            System.out.println(e);
            return; 
        }
        JScrollPane scrollPane = new JScrollPane(canvas);
        mainWindow.setContentPane(scrollPane);
        mainWindow.repaint();
        mainWindow.revalidate();
    }

    public void mostrarSalaEspera(boolean isHost){
        SalaEspera sala;
        try{
            sala = new SalaEspera(ImageIO.read(new File("recursos/fondo.png")),isHost);
        }catch(IOException e){
            System.out.println("No se pudo unir a la sala de espera");
            return;
        }
        mainWindow.setContentPane(sala); 
        mainWindow.repaint();
        mainWindow.revalidate();
    }

    public class Canvas extends JComponent implements MouseListener, MouseMotionListener{

        private List<Object> objects;
        private HashMap<Object, Sprite> sprites;
        private BufferedImage fondo;

        // Constructor de Canvas, con título, tamaño y color de fondo
        // además se añade un par de Listeners para el uso del cursor
        public Canvas(int width, int height, BufferedImage fondo){
            objects = new ArrayList<Object>();
            sprites = new HashMap<Object, Sprite>();
            this.fondo = fondo;
            this.setPreferredSize(new Dimension(width, height));
            this.setVisible(true);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }

        // Método que se encarga de añadir un objeto al
        // HashMap de objetos dibujables, relaciona un objeto
        // con un Sprite y cada vez que se intenta dibujar el
        // objeto en el Canvas se dibuja el Sprite relacionado
        // con el objeto.
        public void draw(Object objectReference, Sprite sprite){
            objects.remove(objectReference);
            objects.add(objectReference);
            sprites.put(objectReference, sprite);
        }

        // Método que elimina el objeto dado del HashMap de objetos
        // a dibujar
        public void erase(Object objectReference){
            objects.remove(objectReference);
            this.repaint();
        }

        // Método que se encarga de actualizar el Canvas
        public void redraw(){
            this.repaint();
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            g.drawImage(fondo, 0, 0,getWidth(),getHeight(), this);

            // Aplicación de Lambda en la que se filtran los objetos que tienen
            // tienen sprite y son visibles para dibujarlos posteriormente
            objects.stream().filter(
                    object -> sprites.get(object) != null && sprites.get(object).isVisible()
                    ).forEach(
                        object ->{
                            Sprite objectSprite = sprites.get(object);
                            g.drawImage(objectSprite.getImage(), objectSprite.getXPosition(),objectSprite.getYPosition(),null);
                        }
                        );
        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseDragged(MouseEvent e)
        {

        }

        public void mouseMoved(MouseEvent e)
        {

        }

        @Override
        public void mouseClicked(MouseEvent e) 
        {

        }
    }

    public class SalaEspera extends JComponent{
        private BufferedImage fondo; 
        private JLabel mensajeEspera;
        public SalaEspera(BufferedImage fondo, boolean isHost) {
            this.fondo = fondo;
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            if(isHost){
                mensajeEspera = new JLabel("Esperando por un rival...");
                mensajeEspera.setPreferredSize(new Dimension(600, 100));
            }
            else{
                mensajeEspera = new JLabel("Esperando a que se prepare la partida...");
                mensajeEspera.setPreferredSize(new Dimension(800, 100));
            }
            mensajeEspera.setFont(new Font(mensajeEspera.getFont().getName(),mensajeEspera.getFont().getStyle(),32));
            mensajeEspera.setForeground(Color.WHITE);
            this.add(mensajeEspera,c);
            this.setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(fondo, 0, 0,getWidth(),getHeight(), this);
        }
    }

    public class MenuPrincipal extends JComponent{
        private BufferedImage fondo; 
        private JButton unirsePartida;
        private JButton crearPartida;
        private JButton salir;
        public MenuPrincipal(BufferedImage fondo) {
            this.fondo = fondo;
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.ipadx = 10;
            c.ipady = 10;
            c.insets = new Insets(10, 10, 10, 10);
            unirsePartida = new JButton("Unirse a una partida");
            unirsePartida.setPreferredSize(new Dimension(400, 100));
            unirsePartida.setFont(new Font(unirsePartida.getFont().getName(),unirsePartida.getFont().getStyle(),32));
            crearPartida = new JButton("Crear una partida");
            crearPartida.setPreferredSize(new Dimension(400, 100));
            crearPartida.setFont(new Font(unirsePartida.getFont().getName(),unirsePartida.getFont().getStyle(),32));
            salir = new JButton("Salir del juego");
            salir.setPreferredSize(new Dimension(400, 100));
            salir.setFont(new Font(unirsePartida.getFont().getName(),unirsePartida.getFont().getStyle(),32));
            this.add(unirsePartida,c);
            this.add(crearPartida,c);
            this.add(salir,c);

            unirsePartida.addActionListener(e -> unirseAPartida());
            crearPartida.addActionListener(e -> crearUnaPartida());
            salir.addActionListener(e -> terminarEjecucion());
        }

        void unirseAPartida(){
            System.out.println("Uniendose a partida"); 
            juego.crearCliente();
        }

        void crearUnaPartida(){
            System.out.println("Creando partida");
            juego.crearServidor();
        }

        void terminarEjecucion(){
            System.out.println("Gracias por jugar Curly's Multi Piece Mino by Diego Castañeda");
            System.exit(0); 
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(fondo, 0, 0,getWidth(),getHeight(), this);
        }
    } 

    public void setJuego(MultiPieceMino juego){
        this.juego = juego; 
    }
}