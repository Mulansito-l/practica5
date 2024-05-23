import java.util.ArrayList;

public class Tablero {
    ArrayList<Ficha> fichasJugadas;
    public Tablero(){
        fichasJugadas = new ArrayList<Ficha>();
    }

    public boolean colocarFicha(Ficha ficha){
        if(fichasJugadas.size() == 0){
            ficha.setPos(960, 540);
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
        fichaAColocar.getImagen().rotate(90);
        fichaAColocar.setPos(masCercana.getPosX() + 80, masCercana.getPosY());
        fichasJugadas.add(fichaAColocar);
        jugador.getMano().remove(fichaAColocar);
        return true;
    }

    @Override
    public String toString() {
        return fichasJugadas.toString(); 
    }
}
