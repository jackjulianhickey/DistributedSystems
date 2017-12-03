import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientInput implements Runnable {

    Socket client;
    List<Socket> lClients;
    InputStream fromClient;
    ObjectInputStream objectFromClient;

    ClientInput(Socket client, List<Socket> lClients) throws IOException {
        this.client = client;
        this.lClients = lClients;

        fromClient = client.getInputStream();
        //InputStream fromClient = client.getInputStream();

        OutputStream toClient = client.getOutputStream();
        ObjectOutputStream objectToClient = new ObjectOutputStream(client.getOutputStream());

        objectFromClient = new ObjectInputStream(fromClient);

    }

    List<ObjectOutputStream> lOutput;
    ClientInput(Socket client, List<Socket> lClients, List<ObjectOutputStream> lOutput,ObjectInputStream objectFromClient) throws IOException{
    this.client = client;
		this.lClients = lClients;
		this.lOutput = lOutput;
		this.objectFromClient = objectFromClient;
    }

    public void run() {
        try {
            System.out.println("Running");
            while(true) {
                System.out.println("Waiting for object :");
                if (this.objectFromClient == null) {
                    System.out.println("NULL");
                }
                String s = (String) objectFromClient.readObject();
                System.out.println("Object received -- "+s);
                for(int i=0;i<this.lClients.size(); i++) {
                    Socket c = this.lClients.get(i);
                    if(c != client) {
                        ObjectOutputStream objectToClient = this.lOutput.get(i);
                        objectToClient.writeObject(s);
                        objectToClient.flush();
                    }
                }
				/*
				for( Socket c : this.lClients ) {
					if(c != client) {
						OutputStream toClient = c.getOutputStream();
						ObjectOutputStream objectToClient = new ObjectOutputStream(toClient);
						objectToClient.writeObject(s);
						objectToClient.flush();
						System.out.println("Sending to client: "+s);
					}
				}
				*/
            }
        } catch( Exception e) {
            System.out.println("Error run ClientReading: "+e.getMessage());
        }
    }


    }

