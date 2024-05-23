import java.util.ArrayList;

public class Jugador {
    private ArrayList<Ficha> mano;
    Jugador(){

    }
    
    public void setMano(ArrayList<Ficha> mano) {
        this.mano = mano;
    }

    public ArrayList<Ficha> getMano() {
        return mano;
    }

    public void mostrarMano(boolean isHost, int jugador, Interfaz.Canvas canvasUI){
        if(isHost){
            if(jugador == 0){
                int posicionInicial = ((mano.size()/2) + (mano.size() % 2)) - mano.size();
                int xInicial = 1920 / 2;
                for (Ficha ficha : mano) {
                    if(ficha instanceof Ficha){
                        ficha.setPos(xInicial + posicionInicial * 100, 940);
                        ficha.getImagen().setVisible(true);
                        canvasUI.draw(ficha, ficha.getImagen());
                        posicionInicial++;
                    }
                } 
            }else{
                ocultarFichas();
                int posicionInicial = ((mano.size()/2) + (mano.size() % 2)) - mano.size();
                int xInicial = 1920 / 2;
                for (Ficha ficha : mano) {
                    ficha.setPos(xInicial + posicionInicial * 100, 50); 
                    ficha.getImagen().setVisible(true);
                    canvasUI.draw(ficha, ficha.getImagen());
                    posicionInicial++;
                }
            }
        }else{
            if(jugador == 0){
                ocultarFichas();
                int posicionInicial = ((mano.size()/2) + (mano.size() % 2)) - mano.size();
                int xInicial = 1920 / 2;
                for (Ficha ficha : mano) {
                    ficha.setPos(xInicial + posicionInicial * 100, 50);    
                    ficha.getImagen().setVisible(true);
                    canvasUI.draw(ficha, ficha.getImagen());
                    posicionInicial++;
                } 
            }else{
                int posicionInicial = ((mano.size()/2) + (mano.size() % 2)) - mano.size();
                int xInicial = 1920 / 2;
                for (Ficha ficha : mano) {
                    ficha.setPos(xInicial + posicionInicial * 100, 960);    
                    ficha.getImagen().setVisible(true);
                    canvasUI.draw(ficha, ficha.getImagen());
                    posicionInicial++;
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
}
