import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
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
    private BufferedImage rotatedImage = null;
    private double rotation;
    private boolean visible;

    // Constructor de Sprite, toma la ruta
    // de la imagen y la intenta cargar

    public Sprite(String imageName, int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rotation = 0;
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
        this.rotation = sprite.rotation;
        this.image = sprite.getImage();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public BufferedImage getImage(){
        if(rotation == 0)
            return image;
        return rotatedImage;
    }

    public double getRotation() {
        return rotation;
    }

    public void añadirRotacion(double angle){
        if(this.rotation + angle > 360){
            rotation = (rotation + angle - 360);
        }
        this.rotation += angle;
        rotate(rotation);
    }

    private void rotate(double angle) {
        //Convertir el angulo a radianes
        double radians = Math.toRadians(angle);
        
        // Conseguir el nuevo tamaño de la imagen 
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int w = image.getWidth();
        int h = image.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);
        
        // Crear la nueva imagen
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType()); 
        Graphics2D g2d = rotatedImage.createGraphics();
        
        // Aplicando filtros y opciones para mejorar la calidad
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Calcular la transformación 
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);
        at.rotate(radians, w / 2, h / 2);
        
        // Dibujar la imagen original con la transformación 
        g2d.drawRenderedImage(image, at);
        g2d.dispose();
        
        this.rotatedImage = rotatedImage; 
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
