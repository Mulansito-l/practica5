import java.util.ArrayList;

public class Tablero {
    ArrayList<Ficha> fichasJugadas;
    public Tablero(){
        fichasJugadas = new ArrayList<Ficha>();
    }

    public boolean colocarFicha(Ficha ficha){
        if(fichasJugadas.size() == 0){
            ficha.setPos(640, 360);
            if (!ficha.esVisible())
                ficha.voltearFicha();
            fichasJugadas.add(ficha);
            return true; 
        }else{
            if (!ficha.esVisible())
                ficha.voltearFicha();
            fichasJugadas.add(ficha);
            return true;
        }
    }

    public void dibujar(Interfaz.Canvas canvasTablero){
        for (Ficha ficha : fichasJugadas) {
            ficha.getImagen().setVisible(true);
            canvasTablero.draw(ficha, ficha.getImagen());
        }
    }

    public boolean colocarFichaEn(int x, int y, Jugador jugador, int index){
        // FALTA COLOCAR EN EL TABLERO Y CAMBIAR LA POSICION
        int xMax = 30;
        int yMax = 30;
        Ficha masCercana = null;
        for (Ficha ficha : fichasJugadas) {
            if(Math.abs(x - ficha.getPosX()) < xMax && Math.abs(y - ficha.getPosY()) < yMax){
                masCercana = ficha;
                xMax = Math.abs(x - ficha.getPosX());
                yMax = Math.abs(y - ficha.getPosY());
            }
        }
        if(masCercana == null){
            return false;
        }
        Ficha fichaAColocar = jugador.getMano().get(index);
        if(!fichaAColocar.esVisible())
            fichaAColocar.esVisible();
        if (!sePudoColocar(fichaAColocar, masCercana)) {
            return false;
        }
        else {
            fichaAColocar.setPos(masCercana.getPosX(), masCercana.getPosY() + 150);
            fichasJugadas.add(fichaAColocar);
            jugador.getMano().remove(fichaAColocar);
            return true;
        }
    }

    private boolean sePudoColocar(Ficha laFichaAColocar, Ficha laFichaPuesta){

        if ((laFichaPuesta instanceof FichaDeTriomino)) {
            System.out.println("Soy una ficha puesta de 3 lados");
            System.out.println("Mis lados son:");
            System.out.println("Lado A:"+laFichaPuesta.getladoA());
            System.out.println("Lado B:"+laFichaPuesta.getladoB());
            System.out.println("Lado C:"+((FichaDeTriomino) laFichaPuesta).getLadoC());
            System.out.println("Estoy pointing up?"+ ((FichaDeTriomino) laFichaPuesta).getIsPointingUp());
            // Si ambas son de Triomino
            if (laFichaAColocar instanceof FichaDeTriomino) {
                //Si la ficha que está puesta está apuntando hacia arriba, se puede intentar colocar otra de 3 lados
                System.out.println("Soy una ficha a colocar de 3 lados");
                if (((FichaDeTriomino) laFichaPuesta).getIsPointingUp()) {
                    ((FichaDeTriomino) laFichaAColocar).setIsPointingUp(false);
                    System.out.println("Mis lados son:");
                    System.out.println("Lado A:"+laFichaAColocar.getladoA());
                    System.out.println("Lado B:"+laFichaAColocar.getladoB());
                    System.out.println("Lado C:"+((FichaDeTriomino) laFichaAColocar).getLadoC());
                    System.out.println("Estoy pointing up?"+ ((FichaDeTriomino) laFichaAColocar).getIsPointingUp());
                    if (laFichaPuesta.getladoA() == ((FichaDeTriomino) laFichaAColocar).getLadoC() && ((FichaDeTriomino) laFichaPuesta).getLadoC() == laFichaAColocar.getladoA()) {
                        return true;
                    }
                    else {
                        laFichaAColocar.rotateRight();
                        laFichaAColocar.rotateRight();
                        System.out.println("Se giró la ficha a colocar de 3 lados");
                        System.out.println("Mis nuevos lados son lado A: "+laFichaAColocar.getladoA()+", Lado B: "+laFichaAColocar.getladoB()+" y lado C: "+((FichaDeTriomino) laFichaAColocar).getLadoC());
                    }
                    if (laFichaPuesta.getladoA() == ((FichaDeTriomino) laFichaAColocar).getLadoC() && ((FichaDeTriomino) laFichaPuesta).getLadoC() == laFichaAColocar.getladoA()) {
                        return true;
                    }
                    else {
                        laFichaAColocar.rotateRight();
                        laFichaAColocar.rotateRight();
                        System.out.println("Se giró la ficha a colocar de 3 lados");
                        System.out.println("Mis nuevos lados son lado A: "+laFichaAColocar.getladoA()+", Lado B:"+laFichaAColocar.getladoB()+" y lado C"+((FichaDeTriomino) laFichaAColocar).getLadoC());
                    }
                    if (laFichaPuesta.getladoA() == ((FichaDeTriomino) laFichaAColocar).getLadoC()
                            && ((FichaDeTriomino) laFichaPuesta).getLadoC() == laFichaAColocar.getladoA()) {
                        return true;
                    }
                }
                else {
                    return false;
                }
            }
            // Si estamos intentando colocar una ficha de 2 lados
            else {
                System.out.println("Soy una ficha a colocar de 2 lados");
                System.out.println("Mi lado A es:"+laFichaAColocar.getladoA());
                System.out.println("Mi lado B es:"+laFichaAColocar.getladoB());
                if (laFichaPuesta.getladoB() == laFichaAColocar.getladoB()) {
                    return true;
                }
                else {
                    laFichaAColocar.rotateRight();
                    System.out.println("Se giró la ficha a colocar de 2 lados");
                    System.out.println("Mis nuevos lados son lado A: "+laFichaAColocar.getladoA()+" y Lado B:"+laFichaAColocar.getladoB());
                }
                if (laFichaPuesta.getladoB() == laFichaAColocar.getladoB()) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        // Si solo la que colocaremos es de Triomino y la puesta es doble
        else {
            System.out.println("Soy una ficha puesta de 2 lados");
            System.out.println("Mis lados son:");
            System.out.println("Lado A:"+laFichaPuesta.getladoA());
            System.out.println("Lado B:"+laFichaPuesta.getladoB());
            // Si la ficha que intentaremos colocar es de 3 lados
            if (laFichaAColocar instanceof FichaDeTriomino) {
                System.out.println("Soy una ficha a colocar de 3 lados");
                ((FichaDeTriomino) laFichaAColocar).setIsPointingUp(true);
                System.out.println("Mis lados son:");
                System.out.println("Lado A:"+laFichaAColocar.getladoA());
                System.out.println("Lado B:"+laFichaAColocar.getladoB());
                System.out.println("Lado C:"+((FichaDeTriomino) laFichaAColocar).getLadoC());
                System.out.println("Estoy pointing up?"+ ((FichaDeTriomino) laFichaAColocar).getIsPointingUp());
                if (laFichaPuesta.getladoA() == laFichaAColocar.getladoB()) {
                    return true;
                }
                else {
                    laFichaAColocar.rotateRight();
                    laFichaAColocar.rotateRight();
                    System.out.println("Se giró la ficha a colocar de 3 lados");
                    System.out.println("Mis nuevos lados son lado A: "+laFichaAColocar.getladoA()+", Lado B:"+laFichaAColocar.getladoB()+" y lado C"+((FichaDeTriomino) laFichaAColocar).getLadoC());
                }
                if (laFichaPuesta.getladoA() == laFichaAColocar.getladoB()) {
                    return true;
                }
                else {
                    laFichaAColocar.rotateRight();
                    laFichaAColocar.rotateRight();
                    System.out.println("Se giró la ficha a colocar de 3 lados");
                    System.out.println("Mis nuevos lados son lado A: "+laFichaAColocar.getladoA()+", Lado B:"+laFichaAColocar.getladoB()+" y lado C"+((FichaDeTriomino) laFichaAColocar).getLadoC());
                }
                if (laFichaPuesta.getladoA() == laFichaAColocar.getladoB()) {
                    return true;
                } else {
                    return false;
                }
            }
            // Si ambas fichas son de 2 lados
            else {
                System.out.println("Soy una ficha a colocar de 2 lados");
                System.out.println("Mi lado A es:"+laFichaAColocar.getladoA());
                System.out.println("Mi lado B es:"+laFichaAColocar.getladoB());
                if (laFichaPuesta.getladoA() == laFichaAColocar.getladoB()) {
                    return true;
                }
                else {
                    laFichaAColocar.rotateRight();
                    System.out.println("Se giró la ficha a colocar de 2 lados");
                    System.out.println("Mis nuevos lados son lado A: "+laFichaAColocar.getladoA()+" y Lado B:"+laFichaAColocar.getladoB());
                }
                if (laFichaPuesta.getladoA() == laFichaAColocar.getladoB()) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
 

    @Override
    public String toString() {
        return fichasJugadas.toString(); 
    }
}
