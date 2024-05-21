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
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Cliente(){
        isConnected = false;
        try {
            socket = new Socket("127.0.0.1", 5258); 
            isConnected = true;
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
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
            out.reset();
            out.writeObject(acciones); 
            out.flush();
            acciones.clear();
        } catch (Exception e) {
            System.out.println("Excepcion en sendActions: " + e);
        }
    }

    public ArrayList<AccionJuego> getActions(){
        if(in == null){
            try{
                in = new ObjectInputStream(socket.getInputStream());
            }catch(IOException e){
                System.out.println("IOException: " + e);
            }
            return null;
        }
        try {
            ArrayList<AccionJuego> acciones = (ArrayList<AccionJuego>) in.readObject();
            return acciones;
        } catch (EOFException e) {
            return null;
        } catch (IOException e){
            System.out.println("Excepcion en getActions IOException: " + e);
        } catch (ClassNotFoundException e){
            System.out.println("Excepcion en getActions ClassNotFound: " + e);
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
