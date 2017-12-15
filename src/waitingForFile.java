import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class waitingForFile implements Runnable {
    Socket server;
    private ObjectInputStream objectFromServer;

    waitingForFile(ObjectInputStream objectFromServer) throws IOException {
        this.server = server;
        this.objectFromServer = objectFromServer;
    }

    @Override
    public void run() {

        try {
            while(true) {
                Files msg = (Files) objectFromServer.readObject();
                System.out.println("\nClient: "+msg.getName().toString());
            }
        } catch(Exception e) {
            System.out.println("Error run ClientWaitMessage: "+e.getMessage());
        }

    }
}
