import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    private static final int port = 5258;
    private ServerSocket serverSocket;
    private Socket socket;
    private int connectedClients;
    private ArrayList<Socket> clientes; 
    private int ultimoJugador; // 0 == HOST, 1 == OTHER PLAYER
    
    public Servidor(){
        try{
             serverSocket = new ServerSocket(port);
             clientes = new ArrayList<Socket>();
             ultimoJugador = 0;
        }
        catch(IOException e){
            System.out.println(e);
        }

    }
    
    public int getConnectedClients() {
        return connectedClients;
    }

    public void startServer(){
        connectedClients = 0;
        while(true){
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new Thread(() -> {
                if(connectedClients == 0){
                    handleClient(socket, true);
                }else{
                    handleClient(socket, false);
                }
            }).start(); 
        }
    }

    public void handleClient(Socket client, boolean isHost){
        connectedClients++;
        clientes.add(client);
        System.out.println("Se ha unido un cliente nuevo");
        try{
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            while(true){
                try{
                    ArrayList<AccionJuego> acciones = (ArrayList<AccionJuego>) in.readObject();
                    sendActionsToOtherClients(client, acciones);
                }catch(EOFException e){
                    
                }
            }
        }catch(Exception e){
            System.out.println(e);
        } 
    }
    
    public void sendActionsToOtherClients(Socket sender, ArrayList<AccionJuego> actions){
        for (Socket socket : clientes) {
            try{
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                if(socket != sender){
                    out.writeObject(actions);
                    out.flush();
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
