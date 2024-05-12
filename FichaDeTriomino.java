public class FichaDeTriomino extends Ficha{
    private int ladoC;

    FichaDeTriomino (){
        super  ();
        ladoC= rnd.nextInt(6+1);
    }
    FichaDeTriomino(int ladoA,int ladoB,int ladoC){
        super(ladoA,ladoB);
        this.ladoC=ladoC;
    }
    public int getLadoC() {
        return ladoC;
    }

    public void setLadoC(int ladoC) {
        this.ladoC = ladoC;
    }
    public void girarALaDerecha(){
        int ladoBTemp=ladoB;

        ladoB=ladoA;
        ladoA=ladoC;
        ladoC=ladoBTemp;

    }
    public void girarALaIzquierda(){
        int ladoATemp=ladoA;

        ladoA=ladoB;
        ladoB=ladoC;
        ladoC=ladoATemp;
    }
    public String toString() {
        return "["+ladoA+" | "+ladoB+" | "+ladoC+"]";
    }
}
