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
            fichaAColocar.voltearFicha();
    
        if (!sePudoColocar(fichaAColocar, masCercana)) {
            return false;
        }
        else {
            masCercana.setOcupada(true);
            fichaAColocar.setPos(masCercana.getPosX(), masCercana.getPosY() + 80);
            fichasJugadas.add(fichaAColocar);
            jugador.aumentarPuntuacion(fichaAColocar.obtenerSuma());
            jugador.getMano().remove(fichaAColocar);
            return true;
        }
    }
    
    public boolean sePudoColocar(Ficha laFichaAColocar, Ficha laFichaPuesta){
        if(laFichaPuesta.isOcupada())
            return false;
        System.out.println("Ficha puesta: " + laFichaPuesta);
        System.out.println("Ficha a colocar: " + laFichaAColocar);
        if (laFichaPuesta instanceof FichaDeTriomino) {
            System.out.println("Ficha puesta is pointing up: " + ((FichaDeTriomino)laFichaPuesta).getIsPointingUp());
            System.out.println("La puesta es de 3 lados");
            // Ficha colocada de 3 lados y ficha a colocar de 3 lados
            if (laFichaAColocar instanceof FichaDeTriomino) {
                System.out.println("La ficha a colocar es de 3 lados");
                if (((FichaDeTriomino) laFichaPuesta).getIsPointingUp()) {
                    laFichaAColocar.rotateRight();
                    
                    System.out.println("La ficha colocada si esta viendo hacia arriba");
                    if (laFichaPuesta.getLadoA() == ((FichaDeTriomino) laFichaAColocar).getLadoA() && ((FichaDeTriomino) laFichaPuesta).getLadoC() == ((FichaDeTriomino)laFichaAColocar).getLadoC()) {
                        return true;
                    }
                    laFichaAColocar.rotateRight();
                    laFichaAColocar.rotateRight();
                    if (laFichaPuesta.getLadoA() == ((FichaDeTriomino) laFichaAColocar).getLadoA() && ((FichaDeTriomino) laFichaPuesta).getLadoC() == ((FichaDeTriomino)laFichaAColocar).getLadoC()) {
                        
                        return true;
                    }
                    laFichaAColocar.rotateRight();
                    laFichaAColocar.rotateRight();
                    if (laFichaPuesta.getLadoA() == ((FichaDeTriomino) laFichaAColocar).getLadoA() && ((FichaDeTriomino) laFichaPuesta).getLadoC() == ((FichaDeTriomino)laFichaAColocar).getLadoC()) {
                        
                        return true;
                    }
                    laFichaAColocar.rotateRight();
                }
                else {
                    
                    System.out.println("La ficha colocada no esta viendo hacia arriba");
                    return false;
                }
                
                
            }
            // Ficha colocada de 3 lados y ficha a colocar de 2 lados
            else {
                System.out.println("La ficha a colocar es de 2 lados");
                
                if (!((FichaDeTriomino) laFichaPuesta).getIsPointingUp()) {
                    
                    if (laFichaPuesta.getLadoB() == laFichaAColocar.getLadoB()) {
                        System.out.println("Coincidio el lado A con la ficha puesta");
                        return true;
                    }
                    
                    laFichaAColocar.rotateRight();
                    
                    if (laFichaPuesta.getLadoB() == laFichaAColocar.getLadoB()) {
                        return true;
                    }
                }
                
                else {
                    return false;
                }
            }
            
        }
        else {
            System.out.println("La puesta es de 2 lados");
            // Ficha colocada de 2 lados y ficha a colocar de 3 lados
            if (laFichaAColocar instanceof FichaDeTriomino) {
                System.out.println("La ficha a colocar es de 3 lados");
                if (laFichaPuesta.getLadoA() == laFichaAColocar.getLadoB()) {
                    System.out.println("Coincidio el lado "+laFichaAColocar.getLadoB());
                    return true;
                }
                laFichaAColocar.rotateRight();
                laFichaAColocar.rotateRight();
                if (laFichaPuesta.getLadoA() == laFichaAColocar.getLadoB()) {
                    System.out.println("Coincidio el lado "+laFichaAColocar.getLadoB());
                    return true;
                }
                laFichaAColocar.rotateRight();
                laFichaAColocar.rotateRight();
                if (laFichaPuesta.getLadoA() == laFichaAColocar.getLadoB()) {
                    System.out.println("Coincidio el lado "+laFichaAColocar.getLadoB());
                    return true;
                }
                laFichaAColocar.rotateRight();
            }
            // Ficha colocada de 2 lados y ficha a colocar de 2 lados
            else {
                System.out.println("La ficha a colocar es de 2 lados");
                if (laFichaPuesta.getLadoA() == laFichaAColocar.getLadoB()) {
                    System.out.println("Coincidio el lado "+laFichaAColocar.getLadoA());
                    return true;
                }
                laFichaAColocar.rotateRight();
                if (laFichaPuesta.getLadoA() == laFichaAColocar.getLadoB()) {
                    System.out.println("Coincidio el lado "+laFichaAColocar.getLadoA());
                    return true;
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
