import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.image.AffineTransformOp;

import javax.imageio.ImageIO;

// La clase Sprite es una forma simplificada
// de trabajar con imagenes en el Canvas,
// la creamos para hacer más fácil el mostrar
// elementos gráficos
public class Sprite{
    private int xPosition;
    private int yPosition;
    private BufferedImage image = null;
    private boolean visible;

    // Constructor de Sprite, toma la ruta
    // de la imagen y la intenta cargar

    public Sprite(String imageName, int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        try{
            image = ImageIO.read(new File(imageName));
        }
        catch(Exception e){
            System.out.println("No se pudo cargar la imagen: " + imageName);
        }
    }

    public Sprite(Sprite sprite){
        this.xPosition = sprite.xPosition;
        this.yPosition = sprite.yPosition;
        this.image = sprite.getImage();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public BufferedImage getImage(){
        return image;
    }

    public void rotate(double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
               cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = image.getWidth();
        int h = image.getHeight();
        int neww = (int) Math.floor(w*cos + h*sin),
            newh = (int) Math.floor(h*cos + w*sin);
        BufferedImage rotated = new BufferedImage(neww, newh, image.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww-w)/2, (newh-h)/2);
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawRenderedImage(image, null);
        graphic.dispose();
        this.image = rotated;
    } 

    // Método changeSize, permite escalar la BufferedImage
    // relacionada con el Sprite, hace uso de una librería
    // externa para facilitar el escalado de la imagen
    public void changeSize(int maxSizePixels){
        image = Scalr.resize(image,maxSizePixels);
    }
    public int getXPosition(){
        return xPosition;}
    public int getYPosition(){
        return yPosition;}
    public void setPosition(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
    }

    public void setXPosition(int x){this.xPosition = x;}
    public void setYPosition(int y){this.yPosition = y;}
}
