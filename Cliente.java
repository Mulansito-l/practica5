import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente {
    private Socket socket;
    private boolean isConnected;
    private boolean isHost;

    public Cliente(){
        try {
            socket = new Socket("127.0.0.1", 5258); 
            isConnected = true; 
        } catch (Exception e) {
            System.out.println(e);
            isConnected = false;
        }
    }

    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }
    
    public boolean isHost() {
        return isHost;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void sendActions(ArrayList<AccionJuego> acciones){
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); 
            out.writeObject(acciones); 
            out.flush();
            acciones.clear();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<AccionJuego> getActions(){
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ArrayList<AccionJuego> acciones = (ArrayList<AccionJuego>) in.readObject();
            in.close();
            return acciones;
        } catch (EOFException e) {
            return null;
        } catch (IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e){
            System.out.println(e);
        }
        return null;
    }

    public void disconnect(){
        try {
            socket.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
