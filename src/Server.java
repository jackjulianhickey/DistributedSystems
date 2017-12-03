import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.util.*;

public class Server {
    public int port;
    ServerSocket server;
    List<Socket> lClients;
    List<ObjectOutputStream> lOutput;
    private static List<StoredFiles> files;
    String serverHost = "localhost";

    Server(int port) throws IOException {
        this.port = port;
        lClients = new ArrayList<Socket>();
        server = new ServerSocket(port);
        lOutput = new ArrayList<ObjectOutputStream>();
        files = new ArrayList<>();



    }

    public void runServer() throws IOException {

        //files.add(new StoredFiles("hey ya", "C:\\heyya.mp3"));
        //files.add(new StoredFiles("you & me", "C:\\you&me.mp3"));

        while (true) {
            System.out.println("Server waiting: ");
            Socket client = server.accept();
            lClients.add(client);

            InputStream fromClient = client.getInputStream();

            OutputStream toClient = client.getOutputStream();
            ObjectOutputStream objectToClient = new ObjectOutputStream(toClient);
            ObjectInputStream objectFromServer = new ObjectInputStream(fromClient);
            //StoredFiles [] test = new StoredFiles[]{new StoredFiles("Hey Ya", "c://heyya.mp3")};
            lOutput.add(objectToClient);
            ManageFileIMPL obj = new ManageFileIMPL("AddServer");
            System.out.println("here3");
            Naming.rebind("rmi://" + serverHost + "/ArithServer", obj);
            System.out.println("Server in Registry");
            //objectToClient.close();
            //System.out.println("CR3");

            Thread t = new Thread(new ClientInput(client, lClients, lOutput, objectFromServer));
            t.start();
        }
    }

    public static void main(String[] args) {

        try {
            int port = 1234;
            Server server = new Server(port);
            server.runServer();
            //ExecutorService pool = Executors.newFixedThreadPool(10);

        } catch (Exception e) {
            System.out.println("Error main server: " + e.getMessage());
        }
    }
}
