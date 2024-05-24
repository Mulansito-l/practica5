public class FichaDeTriomino extends Ficha{
    private int ladoC;
    private boolean isPointingUp; 

    FichaDeTriomino (){
        super();
        ladoC= rnd.nextInt(6+1);
    }
    FichaDeTriomino(int ladoA,int ladoB,int ladoC){
        super(ladoA,ladoB);
        this.ladoC=ladoC;
        isPointingUp=true;
        String imageName = "recursos/TriVolteada.png";
        imagenVolteada = new Sprite(imageName, 0, 0);
        this.imagenVolteada.changeSize(100);
    }

    FichaDeTriomino(FichaDeTriomino ficha){
        super(ficha);
        this.ladoC = ficha.ladoC;
        this.isPointingUp=ficha.isPointingUp;
        String imageName = "recursos/TriVolteada.png";
        imagenVolteada = new Sprite(imageName, 0, 0);
        this.imagenVolteada.changeSize(100);

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

    public boolean getIsPointingUp(){
        return isPointingUp;
    }

    public void setIsPointingUp(boolean isPointingUp){
        this.isPointingUp=isPointingUp;
    }

    public void rotateRight(){
        if (isPointingUp){
            int newLadoC=ladoB;
            ladoB= ladoC;
            ladoC=newLadoC;
            isPointingUp=false;
            imagen.añadirRotacion(60);
        }
        else {
            int newLadoA=ladoB;
            ladoB= ladoA;
            ladoA=newLadoA;
            isPointingUp=true;
            imagen.añadirRotacion(60);
        }
        System.out.println(imagen);
    }

    public void rotateLeft(){
        if (isPointingUp){
            int newLadoA=ladoB;
            ladoB= ladoA;
            ladoA=newLadoA;
            isPointingUp=false;
            imagen.añadirRotacion(60);
            imagen.setVisible(true);
        }
        else {
            int newLadoC=ladoB;
            ladoB= ladoC;
            ladoC=newLadoC;
            isPointingUp=true;
            imagen.añadirRotacion(60);
            imagen.setVisible(true);
        }
        System.out.println(true);
    }


    public String toString() {
        return "["+ladoA+" | "+ladoB+" | "+ladoC+"]";
    }
}
