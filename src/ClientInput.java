import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientInput implements Runnable {

    Socket client;
    List<Socket> lClients;
    InputStream fromClient;
    ObjectInputStream objectFromClient;
    List<String> names;
    List<String> src;
    List<ObjectOutputStream> lOutput;

    ClientInput(Socket client, List<Socket> lClients, List<ObjectOutputStream> lOutput,ObjectInputStream objectFromClient, List<String> names, List<String> src) throws IOException{
        this.client = client;
        this.lClients = lClients;
        this.lOutput = lOutput;
        this.objectFromClient = objectFromClient;
        this.names = names;
        this.src = src;
    }

    public void run() {
        try {
            System.out.println("Running");
            while(true) {
                System.out.println("Waiting for object :");
                if (this.objectFromClient == null) {
                    System.out.println("NULL");
                }
                Files s = (Files) objectFromClient.readObject();
                names.add(s.getName().toString());
                src.add(s.getSrc().toString());

                System.out.println("Object received -- "+s.getName().toString());
                for(int i=0;i<this.lClients.size(); i++) {
                    Socket c = this.lClients.get(i);
                    if(c != client) {
                        ObjectOutputStream objectToClient = this.lOutput.get(i);
                        objectToClient.writeObject(s);
                        objectToClient.flush();
                    }
                }
            }
        } catch( Exception e) {
            System.out.println("Error run ClientReading: "+e.getMessage());
        }
    }


}

