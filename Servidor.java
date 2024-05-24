import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler extends Thread {
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private ObjectOutputStream outputStream;

    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.clientSocket = socket;
        this.clients = clients;
    }

    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                ArrayList<AccionJuego> action = (ArrayList<AccionJuego>) inputStream.readObject();
                // Broadcast the received action to all other clients
                broadcast(action);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(ArrayList<AccionJuego> action) throws IOException {
        for (ClientHandler client : clients) {
            if (client != this) {
                client.outputStream.writeObject(action);
                client.outputStream.flush();
            }
        }
    }
}

public class Servidor {
    private static final int PORT = 5258;
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;

    public Servidor() {
        clients = new ArrayList<>();
    }

    public int getConnectedClients(){
        return clients.size();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en el puerto: "+ PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente");
                ClientHandler clientHandler = new ClientHandler(clientSocket, clients);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try{
        serverSocket.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}
