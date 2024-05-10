import java.io.Serializable;
import java.util.ArrayList;
public class AccionJuego implements Serializable{
    // Crear una nueva subclase que sea tipo AccionJuego
    // cada vez que haya algo que se deba sincronizar
    // entre los clientes
    public static class AccionGuardarPuntosMax extends AccionJuego{
        private int puntosMax;
        AccionGuardarPuntosMax(int puntosMax){
            super();
            this.puntosMax = puntosMax;
        }
        public int getPuntosMax() {
            return puntosMax;
        }
    }

    public static class AccionCrearTablero extends AccionJuego{
        AccionCrearTablero(){
            super();
        }
    }

    public static class AccionSetJuegoTerminado extends AccionJuego{
        private boolean juegoTerminado;
        AccionSetJuegoTerminado(boolean juegoTerminado){
            super();
            this.juegoTerminado = juegoTerminado;
        }
        public boolean isJuegoTerminado() {
            return juegoTerminado;
        } 
    }

    public static class AccionSetRondaTerminada extends AccionJuego{
        private boolean rondaTerminada;
        AccionSetRondaTerminada(boolean rondaTerminada){
            super();
            this.rondaTerminada = rondaTerminada;
        }
        public boolean isRondaTerminada() {
            return rondaTerminada;
        }
    }
}
