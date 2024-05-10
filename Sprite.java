import java.awt.image.BufferedImage;
import java.io.File;
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
            System.out.println("No se pudo cargar la imagen: " + e.toString());
        }
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
    public void setXPosition(int x){this.xPosition = x;}
    public void setYPosition(int y){this.yPosition = y;}
}
