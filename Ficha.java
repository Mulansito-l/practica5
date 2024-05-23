import java.util.Random;

public class Ficha implements Movible{
    //Atributos de la ficha
    protected int ladoA;
    protected int ladoB;
    protected boolean esVisible;
    protected boolean esMula=false;
    protected Random rnd= new Random();
    protected int posX;
    protected int posY;
    protected Sprite imagen;

    //Constructores
    public Ficha()
    {
        ladoA=rnd.nextInt(6+1);
        ladoB=rnd.nextInt(6+1);
        esVisible=true;
        if (ladoA==ladoB){
            esMula=true;
        }
    }
    public Ficha(int ladoAPersonalizado, int ladoBPersonalizado)
    {
        posX = 0;
        posY = 0;
        ladoA=ladoAPersonalizado;
        ladoB=ladoBPersonalizado;
        esVisible=true;
        if (ladoA==ladoB){
            esMula=true;
        }
    }

    public Ficha(Ficha ficha){
        this.posX = ficha.posX;
        this.posY = ficha.posY;
        this.ladoA = ficha.ladoA;
        this.ladoB = ficha.ladoB;
        this.esVisible = ficha.esVisible;
        this.esMula = ficha.esMula;
    }

    public void rotateRight(){
        
    }

    public void rotateLeft(){
        
    }

    public void setImagen(Sprite imagen) {
        this.imagen = imagen;
        this.imagen.changeSize(100);
    }
    
    public void setPos(int x, int y){
        posX = x;
        posY = y;
        imagen.setXPosition(x - 50); 
        imagen.setYPosition(y - 50);
    }

    public Sprite getImagen() {
        return imagen;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean getEsMula(){
        return esMula;
    }

    //Getters y setters
    public void setladoA (int ladoA)
    {
        this.ladoA=ladoA;
    }

    public void setladoB (int ladoB)
    {
        this.ladoB=ladoB;
    }

    public int getladoA (){

        return ladoA;
    }

    public int getladoB (){

        return ladoB;
    }
    //Invierte el estado de la variable esVisible
    public void voltearFicha(){
        esVisible=!esVisible;
    }

    public boolean esVisible(){
        return esVisible;
    }

    //Intercambia los valores de los lados de la ficha
    public void girar180Grados() {
        int temp=ladoA;
        ladoA=ladoB;
        ladoB=temp;
    }
    //Obtener suma de los dos lados
    public int obtenerSuma (){
        return ladoA+ladoB;
    }

    public String toString() {
        // si no está volteada, entonces la regresa como cadena
        return "[" + ladoA + "|" + ladoB + "]";
    }
}
