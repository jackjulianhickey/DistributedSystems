import java.net.*;
import java.io.*;
import java.util.*;

public class waitingForFile implements Runnable {
    Socket server;
    private ObjectInputStream objectFromServer;

    waitingForFile(ObjectInputStream objectFromServer) throws IOException {
        this.server = server;

		/*
		InputStream fromServer = this.server.getInputStream();
		System.out.println("ss2: "+server.getInetAddress());
		this.objectFromServer = new ObjectInputStream(fromServer);
			*/
        this.objectFromServer = objectFromServer;
    }

    public void run() {
        try {
            while(true) {
                String msg = (String) objectFromServer.readObject();
                System.out.println("\nClient: "+msg);
            }
        } catch(Exception e) {
            System.out.println("Error run ClientWaitMessage: "+e.getMessage());
        }
    }
}
