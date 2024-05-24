import java.util.ArrayList;

public class Jugador {
    private ArrayList<Ficha> mano;
    private int manoMin;
    private int manoMax;
    private int puntuacion;

    Jugador(){
        manoMin = 0;
        manoMax = 14;
        puntuacion = 0; 
    }
    
    public void setMano(ArrayList<Ficha> mano) {
        this.mano = mano;
    }

    public ArrayList<Ficha> getMano() {
        return mano;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void aumentarPuntuacion(int puntos){
        puntuacion += puntos;
    }

    public void mostrarMano(boolean isHost, int jugador, Interfaz.Canvas canvasUI){
        if(isHost){
            if(jugador == 0){
int posicionInicial; 
                if (mano.size() < 14) {
                    posicionInicial = ((mano.size()/2) + (mano.size() % 2)) - mano.size();
                }else{
                    posicionInicial = -7; 
                }
                int xInicial = 1280 / 2;
                for(int i = manoMin; i <= manoMax; i++){
                    if(i < mano.size()){
                        Ficha ficha = mano.get(i);
                        ficha.setPos(xInicial + posicionInicial * 50, 650);
                        ficha.getImagen().setVisible(true);
                        canvasUI.draw(ficha, ficha.getImagen());
                        posicionInicial++;
                    }
                } 
            }else{
                ocultarFichas();
                int posicionInicial; 
                if (mano.size() < 14) {
                    posicionInicial = ((mano.size()/2) + (mano.size() % 2)) - mano.size();
                }else{
                    posicionInicial = -7; 
                }
                int xInicial = 1280 / 2;
               for(int i = manoMin; i <= manoMax; i++){
                    if(i < mano.size()){
                        Ficha ficha = mano.get(i);
                        ficha.setPos(xInicial + posicionInicial * 50, 80);
                        ficha.getImagen().setVisible(true);
                        canvasUI.draw(ficha, ficha.getImagen());
                        posicionInicial++;
                    }
                } 
            }
        }else{
            if(jugador == 0){
                ocultarFichas();
int posicionInicial; 
                if (mano.size() < 14) {
                    posicionInicial = ((mano.size()/2) + (mano.size() % 2)) - mano.size();
                }else{
                    posicionInicial = -7; 
                }
                int xInicial = 1280 / 2;
               for(int i = manoMin; i <= manoMax; i++){
                    if(i < mano.size()){
                        Ficha ficha = mano.get(i);
                        ficha.setPos(xInicial + posicionInicial * 50, 80);
                        ficha.getImagen().setVisible(true);
                        canvasUI.draw(ficha, ficha.getImagen());
                        posicionInicial++;
                    }
                } 
            }else{
                int posicionInicial; 
                if (mano.size() < 14) {
                    posicionInicial = ((mano.size()/2) + (mano.size() % 2)) - mano.size();
                }else{
                    posicionInicial = -7; 
                }
                int xInicial = 1280 / 2;
                for(int i = manoMin; i <= manoMax; i++){
                    if(i < mano.size()){
                        Ficha ficha = mano.get(i);
                        ficha.setPos(xInicial + posicionInicial * 50, 650);
                        ficha.getImagen().setVisible(true);
                        canvasUI.draw(ficha, ficha.getImagen());
                        posicionInicial++;
                    }
                }
            }
        }
    }

    public void ocultarFichas(){
        mano.forEach(ficha -> {
            if (ficha.esVisible()) {
                ficha.voltearFicha();
            }
        });
    }

    public void hacerVisiblesFichas(){
        mano.forEach(ficha -> {
            if (!ficha.esVisible()) {
                ficha.voltearFicha();
            }
        });
    }

    public void moverManoDerecha(){
        if(manoMax + 1 < mano.size()){
            manoMax++;
            manoMin++;
        }
    }
    
    public void moverManoIzquierda(){
        if(manoMin - 1 >= 0){
            manoMin--;
            if(manoMax > 14)
                manoMax--;
        }
    }
}
