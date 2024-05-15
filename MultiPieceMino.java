import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MultiPieceMino{
    private Interfaz ui;
    private volatile Servidor servidor;
    private volatile Cliente cliente;
    private ArrayList<AccionJuego> accionesJugador;
    private int puntosMax;
    private boolean juegoTerminado;
    private boolean rondaTerminada;
    private Tablero tablero;
    private Pozo pozo;
    private ArrayList manita;
    private Ficha unaFicha;

    MultiPieceMino(){
        ui = new Interfaz(); 
        ui.setJuego(this);
        servidor = null;
        cliente = null;
        accionesJugador = new ArrayList<AccionJuego>();
        manita=new ArrayList<Ficha>();
    } 

    public void jugar(){

        menuPrincipal();
        salaEspera();
        //jugarPartida();
    }

    public void jugarPartida(){
        prepararPartida();
        while (!juegoTerminado) {
            if(cliente.isHost())
                prepararRonda();
            //jugarRonda();
            //comprobarJuego();
        } 
    }

    public void prepararPartida(){
        ui.mostrarPantallaJuego();
        if(cliente.isHost()){
            juegoTerminado = false;
            accionesJugador.add(new AccionJuego.AccionSetJuegoTerminado(juegoTerminado));
            puntosMax = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el máximo de puntos:"));
            accionesJugador.add(new AccionJuego.AccionGuardarPuntosMax(puntosMax));
        }
    }

    public void prepararRonda(){
         rondaTerminada = false;
         accionesJugador.add(new AccionJuego.AccionSetRondaTerminada(rondaTerminada));
         tablero = new Tablero();
         accionesJugador.add(new AccionJuego.AccionCrearTablero());
         pozo=new Pozo();

         // Determinar quien empieza
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
        System.out.println("Empieza partida");
    }

    public void menuPrincipal(){
        ui.mostrarMenuPrincipal(); 
        while (cliente == null) {
        }
        System.out.println("Pantalla de espera");
    }

    public void crearServidor(){
        servidor = new Servidor();
        new Thread(() -> {
            servidor.startServer();
        }).start(); 
        cliente = new Cliente();
        cliente.setHost(true);
    }

    public void crearCliente(){
        cliente = new Cliente(); 
        if(!cliente.isConnected()){
            crearServidor();
        }else{
            cliente.setHost(false);
        }
    }

    public void esperarAccionesOtroJugador(){
        ArrayList<AccionJuego> acciones = null;
        while(acciones == null){
            acciones = cliente.getActions();
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
                System.out.println("Puntos max: " + puntosMax);
            }else if(accionJuego instanceof AccionJuego.AccionCrearTablero a){
                tablero = new Tablero(); 
            }else if(accionJuego instanceof AccionJuego.AccionSetJuegoTerminado a){
                this.juegoTerminado = a.isJuegoTerminado();
            }
        }
    }
}
