import java.util.Random;
public class Ficha
{
    //Atributos de la ficha
    protected int ladoA;
    protected int ladoB;
    protected boolean esVisible;
    protected boolean esMula=false;
    protected Random rnd= new Random();
    private int posicionDeFichaX;

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
        ladoA=ladoAPersonalizado;
        ladoB=ladoBPersonalizado;

        esVisible=true;

        if (ladoA==ladoB){
            esMula=true;
        }
    }
    public int getPosicionDeFichaX(){
        return posicionDeFichaX;
    }


    public boolean getEsMula(){
        return esMula;
    }
    //Dibujar ficha en el canvas
    public void setPosicionDeFichaX(int posicion){
        posicionDeFichaX=posicion;
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
        // si la ficha está volteada regresa 7 espacios
        if (!esVisible) {
            return "[     ]"; // son 7 espacios en blanco
        }
        // si no está volteada, entonces la regresa como cadena
        return "[" + ladoA + "|" + ladoB + "]";
    }
}