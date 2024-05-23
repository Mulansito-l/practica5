import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MultiPieceMino{
    private Interfaz ui;
    private volatile Servidor servidor;
    private volatile Cliente cliente;
    private ArrayList<AccionJuego> accionesJugador;
    private ArrayList<Jugador> jugadores; // JUGADOR 0 = HOST, JUGADOR 1 = INVITADO
    private Sprite cuadroEspera;
    private int puntosMax;
    private boolean juegoTerminado;
    private boolean rondaTerminada;
    private Tablero tablero;
    private Pozo pozo;
    private Ficha fichaSostenida;
    private int indiceFichaSostenida;
    private int jugadorInicial;
    private int puntosFichaInicialHost;
    private int puntosFichaInicialInvitado;
    private Ficha fichaInicial;
    private int turnoActual;
    private boolean tomoDelPozo;

    MultiPieceMino(){
        ui = new Interfaz(); 
        ui.setJuego(this);
        servidor = null;
        cliente = null;
        accionesJugador = new ArrayList<AccionJuego>();
        fichaSostenida = null;
        cuadroEspera = new Sprite("recursos/espera.png", 960, 540);
    } 

    public void jugar(){
        menuPrincipal();
        salaEspera();
        jugarPartida();
    }

    public void jugarPartida(){
        prepararPartida();
        while (!juegoTerminado) {
            if(cliente.isHost())
                prepararRonda();
            while(jugadorInicial == -1){
                determinarJugadorInicial();
            }
            jugarRonda();
            //comprobarJuego();
        } 
    }

    public void prepararPartida(){
        ui.mostrarPantallaJuego();
        if(cliente.isHost()){
            jugadorInicial = -1;
            accionesJugador.add(new AccionJuego.AccionSetJugadorInicial(jugadorInicial));
            juegoTerminado = false;
            accionesJugador.add(new AccionJuego.AccionSetJuegoTerminado(juegoTerminado));
            puntosMax = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el máximo de puntos:"));
            accionesJugador.add(new AccionJuego.AccionGuardarPuntosMax(puntosMax));
            jugadores = new ArrayList<Jugador>();
            jugadores.add(new Jugador());
            jugadores.add(new Jugador());
            accionesJugador.add(new AccionJuego.AccionCrearJugadores());
        }
    }

    public void jugarRonda(){
        while (!rondaTerminada) {
            if(cliente.isHost() && turnoActual == 0 || !cliente.isHost() && turnoActual == 1){
                //
            }else{
                System.out.println("Esperando que el otro jugador coloque una ficha");
                ui.esperarTurno(cuadroEspera);
                esperarAccionesOtroJugador();
                ui.esperarTurno(cuadroEspera);
                tablero.dibujar(ui.getTableroCanvas());
                ui.actualizar();
                mostrarManos(); 
                System.out.println("Mano Jugador 1: " + jugadores.get(0).getMano());
                System.out.println("Mano Jugador 2: " + jugadores.get(1).getMano());
                System.out.println("Tablero" + tablero);
            }
        }
    }

    public void mostrarManos(){
        ui.getUICanvas().clearObjects();
        jugadores.get(0).mostrarMano(cliente.isHost(), 0, ui.getUICanvas());
        jugadores.get(1).mostrarMano(cliente.isHost(), 1, ui.getUICanvas());
        ui.getUICanvas().redraw();
    }

    public void determinarJugadorInicial(){
        if(cliente.isHost()){
            jugadores.get(0).hacerVisiblesFichas(); 
            jugadores.get(1).ocultarFichas();
        }
        else{
            jugadores.get(1).hacerVisiblesFichas(); 
            jugadores.get(0).ocultarFichas();
        }
        mostrarManos(); 
        JOptionPane.showMessageDialog(ui.getUICanvas(), 
                "Elija una ficha y arrastrela al tablero, si su ficha es mayor a la del rival el juego empezará con su ficha");
        if(cliente.isHost()){
            System.out.println("Elija una ficha para empezar");
            esperarAccionesOtroJugador();
            ui.esperarTurno(cuadroEspera);
            if(puntosFichaInicialHost > puntosFichaInicialInvitado){
                jugadorInicial = 0;
                turnoActual = 1;
            }
            else{
                jugadorInicial = 1;
                turnoActual = 0;
            }
            if (jugadorInicial != -1){
                accionesJugador.add(new AccionJuego.AccionSetJugadorInicial(jugadorInicial));
                cliente.sendActions(accionesJugador);
            }
        }else{
            System.out.println("Esperando a que el otro jugador seleccione una ficha");
            ui.esperarTurno(cuadroEspera);
            esperarAccionesOtroJugador();
            ui.esperarTurno(cuadroEspera);
            System.out.println("Elija una ficha para empezar");
            esperarAccionesOtroJugador();
        }
        System.out.println("El jugador inicial es: " + jugadorInicial);
        if(!cliente.isHost()){
            ui.esperarTurno(cuadroEspera);
            esperarAccionesOtroJugador();
            ui.esperarTurno(cuadroEspera);
        }
        if(cliente.isHost() && jugadorInicial == 0){
            tablero.colocarFicha(fichaInicial);
            jugadores.get(0).getMano().remove(fichaInicial);
            accionesJugador.add(new AccionJuego.AccionColocarFicha(indiceFichaSostenida, cliente.isHost(),true));
        }
        if(cliente.isHost() && jugadorInicial == 1){
            ui.getTableroCanvas().erase(fichaInicial);
            fichaInicial.getImagen().setVisible(true);
            ui.getUICanvas().draw(fichaInicial, fichaInicial.getImagen());
            ui.actualizar();
        }
        if(!cliente.isHost() && jugadorInicial == 1){
            tablero.colocarFicha(fichaInicial);
            jugadores.get(1).getMano().remove(fichaInicial);
            accionesJugador.add(new AccionJuego.AccionColocarFicha(indiceFichaSostenida, cliente.isHost(),true));
        }
        if(!cliente.isHost() && jugadorInicial == 0){
            ui.getTableroCanvas().erase(fichaInicial);
            fichaInicial.getImagen().setVisible(true);
            ui.getUICanvas().draw(fichaInicial, fichaInicial.getImagen());
            ui.actualizar();
        }
        if(cliente.isHost()){
            cliente.sendActions(accionesJugador);
            esperarAccionesOtroJugador();
        }else{
            cliente.sendActions(accionesJugador);
        }
        tablero.dibujar(ui.getTableroCanvas());
        mostrarManos();
        System.out.println("TERMINADO DE SELECCIONAR QUIEN EMPIEZA");
        System.out.println("Jugador 1: " + jugadores.get(0).getMano());
        System.out.println("Jugador 2: " + jugadores.get(1).getMano());
        System.out.println("Tablero: " + tablero);
    } 

    public void prepararRonda(){
        tomoDelPozo = false;
        rondaTerminada = false;
        accionesJugador.add(new AccionJuego.AccionSetRondaTerminada(rondaTerminada));
        tablero = new Tablero();
        accionesJugador.add(new AccionJuego.AccionCrearTablero());
        pozo=new Pozo();
        accionesJugador.add(new AccionJuego.AccionCrearPozo());
        long semillaMezclar = new Random().nextLong();
        pozo.mezclarPozo(semillaMezclar);
        accionesJugador.add(new AccionJuego.AccionMezclarPozo(semillaMezclar));
        jugadores.get(0).setMano(pozo.sacarFichasDelPozo(10));
        jugadores.get(1).setMano(pozo.sacarFichasDelPozo(10));
        accionesJugador.add(new AccionJuego.AccionSetManoInicial());
        cliente.sendActions(accionesJugador);
    }

    public void tomarDelPozo(){
        int fichasTomadas = 0;
        if(tomoDelPozo){
            pasarTurno();
            cliente.sendActions(accionesJugador);
            System.out.println("Mandando acciones"); 
            return;
        }
        if(cliente.isHost() && turnoActual == 0){
            Ficha ficha = pozo.getFicha();
            System.out.println(ficha);
            if(ficha != null){
                jugadores.get(0).getMano().add(ficha);
                fichasTomadas++;
            }else{
                JOptionPane.showMessageDialog(ui.getUICanvas(), "Ya no hay más fichas en el Pozo");
                return;
            }

            ficha = pozo.getFicha();
            System.out.println(ficha);
            if(ficha != null){
                jugadores.get(0).getMano().add(ficha);
                fichasTomadas++;
            }else{
                JOptionPane.showMessageDialog(ui.getUICanvas(), "Ya no hay más fichas en el Pozo");
            }
        }else if(!cliente.isHost() && turnoActual == 1){
            Ficha ficha = pozo.getFicha();
            System.out.println(ficha);
            if(ficha != null){
                jugadores.get(1).getMano().add(ficha);
                fichasTomadas++;
            }else{
                JOptionPane.showMessageDialog(ui.getUICanvas(), "Ya no hay más fichas en el Pozo");
                return;
            }

            ficha = pozo.getFicha();
            System.out.println(ficha);
            if(ficha != null){
                jugadores.get(1).getMano().add(ficha);
                fichasTomadas++;
            }else{
                JOptionPane.showMessageDialog(ui.getUICanvas(), "Ya no hay más fichas en el Pozo");
            } 
        }
        accionesJugador.add(new AccionJuego.AccionTomarDelPozo(cliente.isHost(), fichasTomadas));
        tomoDelPozo = true;
        mostrarManos();
        ui.getUICanvas().cambiarTextoTomarDelPozo(tomoDelPozo);
        ui.actualizar();
    }

    public void salaEspera(){
        ui.mostrarSalaEspera(cliente.isHost());
        if(cliente.isHost()){
            while (servidor.getConnectedClients() < 2) {
            }
        }
        else{
            esperarAccionesOtroJugador();
        }
    }

    public void menuPrincipal(){
        ui.mostrarMenuPrincipal(); 
        while (cliente == null) {
        }
    }

    public void crearServidor(){
        servidor = new Servidor();
        new Thread(() -> {
            servidor.start();
        }).start(); 
        cliente = new Cliente();
        cliente.setHost(true);
    }

    public void crearCliente(){
        cliente = new Cliente(); 
        cliente.setHost(false); 
    }

    public void esperarAccionesOtroJugador(){
        ArrayList<AccionJuego> acciones = null;
        while(acciones == null){
            if(cliente.isConnected()){
                acciones = cliente.getActions();
            }
        }
        procesarAcciones(acciones);
    }

    public void procesarAcciones(ArrayList<AccionJuego> acciones){
        if(cliente.isHost()){
            System.out.println("Procesando acciones del host");
        }
        else{
            System.out.println("Procesando acciones del otro cliente");
        }

        for (AccionJuego accionJuego : acciones) {
            if(accionJuego instanceof AccionJuego.AccionGuardarPuntosMax a){  
                puntosMax = a.getPuntosMax();
            }else if(accionJuego instanceof AccionJuego.AccionCrearTablero a){
                tablero = new Tablero(); 
            }else if(accionJuego instanceof AccionJuego.AccionSetJuegoTerminado a){
                this.juegoTerminado = a.isJuegoTerminado();
            }else if(accionJuego instanceof AccionJuego.AccionSetJugadorInicial a){
                this.jugadorInicial = a.getJugadorInicial();
                if(this.jugadorInicial == 0){
                    turnoActual = 1;
                }else if(this.jugadorInicial == 1){
                    turnoActual = 0;
                }
            }else if(accionJuego instanceof AccionJuego.AccionCrearPozo a){
                this.pozo = new Pozo();
            }else if(accionJuego instanceof AccionJuego.AccionMezclarPozo a){
                this.pozo.mezclarPozo(a.getSeed());
            }else if(accionJuego instanceof AccionJuego.AccionCrearJugadores a){
                jugadores = new ArrayList<Jugador>();
                jugadores.add(new Jugador());
                jugadores.add(new Jugador());
            }else if(accionJuego instanceof AccionJuego.AccionSetManoInicial a){
                jugadores.get(0).setMano(pozo.sacarFichasDelPozo(10));
                jugadores.get(1).setMano(pozo.sacarFichasDelPozo(10));
            }else if(accionJuego instanceof AccionJuego.AccionColocarFicha a){
                if(a.isHost() && a.isPrimeraFicha()){
                    Ficha fichaAColocar = jugadores.get(0).getMano().get(a.getIndex());                    
                    tablero.colocarFicha(fichaAColocar);
                    jugadores.get(0).getMano().remove(fichaAColocar);
                    System.out.println("Quitado ficha del HOST");
                }else if(!a.isHost() && a.isPrimeraFicha()){
                    Ficha fichaAColocar = jugadores.get(1).getMano().get(a.getIndex());
                    tablero.colocarFicha(fichaAColocar);
                    jugadores.get(1).getMano().remove(fichaAColocar);
                    System.out.println("Quitado ficha del Invitado");
                }else if(a.isHost() && jugadorInicial != -1){
                    tablero.colocarFichaEn(a.getX(), a.getY(), jugadores.get(0), a.getIndex());
                    mostrarManos();
                    System.out.println("Mano jugador 1: " + jugadores.get(0).getMano());
                    System.out.println("Mano jugador 2: " + jugadores.get(1).getMano());
                }else if(!a.isHost() && jugadorInicial != -1){
                    tablero.colocarFichaEn(a.getX(), a.getY(), jugadores.get(1), a.getIndex());
                    mostrarManos();
                    System.out.println("Mano jugador 1: " + jugadores.get(0).getMano());
                    System.out.println("Mano jugador 2: " + jugadores.get(1).getMano());
                }
            }else if(accionJuego instanceof AccionJuego.AccionSetPuntosFichaInicialHost a){
                this.puntosFichaInicialHost = a.getPuntos();
            }else if(accionJuego instanceof AccionJuego.AccionSetPuntosFichaInicialInvitado a){
                this.puntosFichaInicialHost = a.getPuntos();
            }else if(accionJuego instanceof AccionJuego.AccionSetTurno a){
                this.turnoActual = a.getNuevoTurno();
                ui.getUICanvas().cambiarTextoTomarDelPozo(tomoDelPozo);
            }else if(accionJuego instanceof AccionJuego.AccionTomarDelPozo a){
                if(a.isHost()){
                    Ficha ficha;
                    if(a.getFichasTomadas() > 0){
                        ficha = pozo.getFicha();
                        jugadores.get(0).getMano().add(ficha);
                    }
                    if(a.getFichasTomadas() > 1){
                        ficha = pozo.getFicha();
                        jugadores.get(0).getMano().add(ficha);
                    }
                }else{
                    Ficha ficha;
                    if(a.getFichasTomadas() > 0){
                        ficha = pozo.getFicha();
                        jugadores.get(1).getMano().add(ficha);
                    }
                    if(a.getFichasTomadas() > 1){
                        ficha = pozo.getFicha();
                        jugadores.get(1).getMano().add(ficha);
                    }
                }
            }
        }
    }

    public void seleccionarFicha(int xpos, int ypos){
        Ficha fichaMasCercana = null;
        int indiceFicha = -1;
        int distanciaX = 61;
        int distanciaY = 151;
        ArrayList<Ficha> mano;
        if(cliente.isHost()){
            mano = jugadores.get(0).getMano();
        }else{
            mano = jugadores.get(1).getMano();
        }
        for(int i = 0; i < mano.size(); i++){
            int fichaPosX = mano.get(i).getPosX();
            int fichaPosY = mano.get(i).getPosY();
            if (Math.abs(fichaPosX - xpos) < distanciaX && Math.abs(fichaPosY - ypos) < distanciaY) {
                distanciaX = Math.abs(fichaPosX - xpos);
                distanciaY = Math.abs(fichaPosY - ypos);
                fichaMasCercana = mano.get(i);
                indiceFicha = i;
            }
        }
        fichaSostenida = fichaMasCercana;
        indiceFichaSostenida = indiceFicha;
        ui.getUICanvas().erase(fichaSostenida);
    }

    public void arrastrarFicha(int x, int y){
        if(fichaSostenida == null){
            return;
        }
        Point pos = new Point(x,y);
        SwingUtilities.convertPointToScreen(pos, ui.getUICanvas());
        SwingUtilities.convertPointFromScreen(pos, ui.getTableroCanvas());

        fichaSostenida.setPos(pos.x,pos.y);
        fichaSostenida.getImagen().setVisible(true);
        ui.getTableroCanvas().draw(fichaSostenida, fichaSostenida.getImagen());
        ui.actualizar();
    }
    public void soltarFicha(int x, int y){
        if(fichaSostenida == null){
            return;
        }
        Point pos = new Point(x,y);
        SwingUtilities.convertPointToScreen(pos, ui.getUICanvas());
        SwingUtilities.convertPointFromScreen(pos, ui.getTableroCanvas());
        fichaSostenida.setPos(pos.x,pos.y);
        fichaSostenida.getImagen().setVisible(true);
        ui.getTableroCanvas().draw(fichaSostenida, fichaSostenida.getImagen());
        ui.actualizar();

        if(jugadorInicial == -1){
            if(cliente.isHost()){
                puntosFichaInicialHost = fichaSostenida.obtenerSuma();
                fichaInicial = fichaSostenida;
                accionesJugador.add(new AccionJuego.AccionSetPuntosFichaInicialHost(puntosFichaInicialHost));
                cliente.sendActions(accionesJugador);
                ui.esperarTurno(cuadroEspera);
            }else{
                puntosFichaInicialInvitado = fichaSostenida.obtenerSuma();
                fichaInicial = fichaSostenida;
                accionesJugador.add(new AccionJuego.AccionSetPuntosFichaInicialInvitado(puntosFichaInicialInvitado));
                cliente.sendActions(accionesJugador);
            } 
        }else{
            if(cliente.isHost()){
                int index = jugadores.get(0).getMano().indexOf(fichaSostenida);
                if(tablero.colocarFichaEn(pos.x, pos.y, jugadores.get(0), index)){
                    accionesJugador.add(new AccionJuego.AccionColocarFicha(index
                                ,cliente.isHost(), pos.x, pos.y, false));
                    jugadores.get(0).getMano().remove(fichaSostenida);
                    mostrarManos();
                    pasarTurno();
                    cliente.sendActions(accionesJugador);
                    System.out.println("Mandando acciones");
                }else{
                    ui.getTableroCanvas().erase(fichaSostenida);
                    mostrarManos();
                    ui.actualizar();
                }
            }
            else{
                int index = jugadores.get(1).getMano().indexOf(fichaSostenida);
                if(tablero.colocarFichaEn(pos.x, pos.y, jugadores.get(1), index)){
                    accionesJugador.add(new AccionJuego.AccionColocarFicha(index
                                , cliente.isHost(), pos.x, pos.y, false));
                    jugadores.get(1).getMano().remove(fichaSostenida);
                    mostrarManos();
                    pasarTurno();
                    cliente.sendActions(accionesJugador);
                    System.out.println("Mandando acciones");
                }else{
                    ui.getTableroCanvas().erase(fichaSostenida);
                    mostrarManos();
                    ui.actualizar();
                }
            }
        }
        fichaSostenida = null;
    }

    public void pasarTurno(){
        tomoDelPozo = false;
        ui.getUICanvas().cambiarTextoTomarDelPozo(tomoDelPozo);
        if(turnoActual == 0){
            turnoActual = 1;
            accionesJugador.add(new AccionJuego.AccionSetTurno(1));
        }else{
            turnoActual = 0;
            accionesJugador.add(new AccionJuego.AccionSetTurno(0));
        }
    }
}
