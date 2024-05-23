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

    public static class AccionSetJugadorInicial extends AccionJuego{
        private int jugadorInicial;
        AccionSetJugadorInicial(int jugadorInicial){
            super();
            this.jugadorInicial = jugadorInicial;
        }
        public int getJugadorInicial() {
            return jugadorInicial;
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

    public static class AccionCrearPozo extends AccionJuego{
        AccionCrearPozo(){
            super();
        }
    }

    public static class AccionMezclarPozo extends AccionJuego{
        private long seed;
        AccionMezclarPozo(long seed){
            super();
            this.seed = seed;
        }
        public long getSeed() {
            return seed;
        }
    }

    public static class AccionCrearJugadores extends AccionJuego{
        AccionCrearJugadores(){
            super();
        }
    }

    public static class AccionSetManoInicial extends AccionJuego{
        AccionSetManoInicial(){
            super();
        }
    }

    public static class AccionSetTurno extends AccionJuego{
        private int nuevoTurno;
        AccionSetTurno(int nuevoTurno){
            this.nuevoTurno = nuevoTurno;
        }

        public int getNuevoTurno() {
            return nuevoTurno;
        }
    }

    public static class AccionColocarFicha extends AccionJuego{
        private boolean primeraFicha;
        private int index;
        private boolean isHost;
        private int x;
        private int y;

        AccionColocarFicha(int index, boolean isHost, boolean primeraFicha){
            this.index = index;
            this.isHost = isHost;
            this.x = 0;
            this.y = 0;
            this.primeraFicha = primeraFicha;
        }

        AccionColocarFicha(int index, boolean isHost, int x, int y, boolean primeraFicha){
            this.index = index;
            this.isHost = isHost;
            this.x = x;
            this.y = y;
            this.primeraFicha = primeraFicha;
        }

        public boolean isPrimeraFicha() {
            return primeraFicha;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getIndex() {
            return index;
        }

        public boolean isHost() {
            return isHost;
        }
    }

    public static class AccionSetPuntosFichaInicialHost extends AccionJuego{
        private int puntos;
        AccionSetPuntosFichaInicialHost(int puntos){
            this.puntos = puntos;
        }
        public int getPuntos() {
            return puntos;
        }
    }
    public static class AccionSetPuntosFichaInicialInvitado extends AccionJuego{
        private int puntos;
        AccionSetPuntosFichaInicialInvitado(int puntos){
            this.puntos = puntos;
        }
        public int getPuntos() {
            return puntos;
        }
    }

    public static class AccionTomarDelPozo extends AccionJuego{

        private boolean isHost;
        private int fichasTomadas;

        AccionTomarDelPozo(boolean isHost, int fichasTomadas){
            this.isHost = isHost;
            this.fichasTomadas = fichasTomadas;
        }

        public boolean isHost() {
            return isHost;
        }

        public int getFichasTomadas() {
            return fichasTomadas;
        }
    }
}
