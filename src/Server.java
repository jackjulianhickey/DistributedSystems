
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends UnicastRemoteObject implements ManageFile {
    List<String> names = new ArrayList<String>();
    List<String> source = new ArrayList<String>();
    private String name;
    ServerSocket server;
    List<Socket> lClients;
    int port;
    Socket client;
    List<ObjectOutputStream> lOutput;
    Lock lock =  new ReentrantLock();

    Server(int port) throws IOException {
        this.port = port;
        lClients = new ArrayList<Socket>();
        server = new ServerSocket(port);
        lOutput = new ArrayList<ObjectOutputStream>();
        names = new ArrayList<String>();
        source = new ArrayList<String>();


    }

    public void runServer() throws IOException {

        while (true) {
            System.out.println("Server waiting: ");
            client = server.accept();
            lClients.add(client);

            InputStream fromClient = client.getInputStream();
            OutputStream toClient = client.getOutputStream();
            ObjectOutputStream objectToClient = new ObjectOutputStream(toClient);
            lOutput.add(objectToClient);
            ObjectInputStream objectFromServer = new ObjectInputStream(fromClient);

            Thread t = new Thread(new ClientInput(client, lClients, lOutput, objectFromServer, names, source));
            t.start();

        }
    }

    //main method
    public static void main(String[] args) {
        try {
            int port = 1234;
            Server server = new Server(port);
            Naming.rebind("Files", server);

            // Server server2 = new Server(port);
            server.runServer();
        } catch(java.net.MalformedURLException e) {
            System.out.println("Malformed URL of Server name: "+e.toString());
        }
        catch(RemoteException e) {
            System.out.println("Communication error: "+e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void add(String obj) throws ClassNotFoundException, IOException {
        //String name = (String) obj.readObject();
        names.add(obj);
        //source.add(src);
        System.out.println("This file has been added: " + obj);

    }

    public synchronized void deleteFile(String name) throws RemoteException {
        this.lock.lock();
        int i = names.indexOf(name);
        names.remove(i);
        this.lock.unlock();
        //source.remove(i);
        System.out.println("This file has been removed: " + name);

    }

    @Override
    public void requestandsleep(String name) {

        Thread x = new Thread(new RequestFileAndSleep(client, name, names, lock));
        x.start();

    }

    @Override
    public void requestanddelete (String name) {

        Thread x = new Thread(new RequestFileAndDelete(client, name, names, source, lock));
        x.start();

    }
}
