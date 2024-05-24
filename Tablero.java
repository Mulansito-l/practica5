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
            // Si ambas son de Triomino y la ficha que está puesta está apuntando hacia arriba
            if (laFichaAColocar instanceof FichaDeTriomino && ((FichaDeTriomino) laFichaPuesta).getIsPointingUp()) {
                ((FichaDeTriomino) laFichaAColocar).setIsPointingUp(false);
                if (laFichaPuesta.getladoA() == ((FichaDeTriomino) laFichaAColocar).getLadoC() && ((FichaDeTriomino) laFichaPuesta).getLadoC() == laFichaAColocar.getladoA()) {
                    return true;
                }
                else if (laFichaPuesta.getladoA() == laFichaAColocar.getladoA() && ((FichaDeTriomino) laFichaPuesta).getLadoC() == laFichaAColocar.getladoB()) {
                    return true;
                }
                else return laFichaPuesta.getladoA() == laFichaAColocar.getladoB() && ((FichaDeTriomino) laFichaPuesta).getLadoC() == ((FichaDeTriomino) laFichaAColocar).getLadoC();

            }
            else if (!(laFichaAColocar instanceof FichaDeTriomino)  && !((FichaDeTriomino) laFichaPuesta).getIsPointingUp()) {
                return laFichaPuesta.getladoB() == laFichaAColocar.getladoB() || laFichaPuesta.getladoB() == laFichaAColocar.getladoA();
            }
        }
        // Si solo la que colocaremos es de Triomino y la puesta es doble
        else {
            if (laFichaAColocar instanceof FichaDeTriomino) {
                if (laFichaPuesta.getladoA() == laFichaAColocar.getladoA() ||
                        laFichaPuesta.getladoA() == laFichaAColocar.getladoB() ||
                        laFichaPuesta.getladoA() == ((FichaDeTriomino) laFichaAColocar).getLadoC()) {
                    ((FichaDeTriomino) laFichaAColocar).setIsPointingUp(true);
                    return true;
                }
            } else {
                return laFichaPuesta.getladoA() == laFichaAColocar.getladoB() || laFichaPuesta.getladoA() == laFichaAColocar.getladoA();
            }
        }
        return false;
    }
 

    @Override
    public String toString() {
        return fichasJugadas.toString(); 
    }
}
