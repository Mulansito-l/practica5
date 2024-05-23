public class FichaDeTriomino extends Ficha{
    private int ladoC;

    FichaDeTriomino (){
        super();
        ladoC= rnd.nextInt(6+1);
    }
    FichaDeTriomino(int ladoA,int ladoB,int ladoC){
        super(ladoA,ladoB);
        this.ladoC=ladoC;
    }

    FichaDeTriomino(FichaDeTriomino ficha){
        super(ficha);
        this.ladoC = ficha.ladoC;
    }
    public int getLadoC() {
        return ladoC;
    }

    public void setLadoC(int ladoC) {
        this.ladoC = ladoC;
    }

    public int obtenerSuma(){
        return ladoA + ladoB + ladoC;
    }

    public String toString() {
        return "["+ladoA+" | "+ladoB+" | "+ladoC+"]";
    }
}
