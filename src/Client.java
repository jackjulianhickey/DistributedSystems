
import java.io.*;
import java.net.Socket;
import java.rmi.*;
import java.util.Scanner;

public class Client {
    private static final String fileSrc = "C:\\Users\\jackj\\Desktop\\College Year 3\\Distrubuted Systems Programming\\FileRead\\Tim-input.txt";
    private static Scanner scan = null;


    public static void main(String[] args) throws IOException {
        ManageFile serverObject;
        Remote RemoteObject;
        int serverPort = 1234;
        String serverHost = "localhost";
        Socket server;
        ObjectOutputStream objectToServer;

        server = new Socket(serverHost, serverPort);


        InputStream fromServer = server.getInputStream();
        ObjectInputStream objectFromServer = new ObjectInputStream(fromServer);

        OutputStream toServer = server.getOutputStream();
        objectToServer = new ObjectOutputStream(toServer);

        Thread t = new Thread(new waitingForFile(objectFromServer));
        t.start();


        try {
            String name = "rmi://localhost/Files";
            RemoteObject = Naming.lookup(name);
            serverObject = (ManageFile) RemoteObject;
            scan = new Scanner(new File(fileSrc));

        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(" ");

            if (line[0].equalsIgnoreCase("wait")) {
                try {
                    System.out.println("Wait");
                    int x = Integer.parseInt(line[1]);
                    Thread.sleep(x*1000);
                } catch (Exception e) {
                    System.err.println(line[2] + " is not recognized as a number");
                }
            } else if (line[0].equalsIgnoreCase("share")) {
                try {
                    Files f = new Files(line[2], line[1].toString());
                    objectToServer.writeObject(f);
                    objectToServer.flush();
                } catch (Exception e) {
                    System.err.println(line[3] + " could not be added");
                }
            } else if (line[0].equalsIgnoreCase("delete")) {
                try {
                    serverObject.deleteFile(line[1]);
                } catch (Exception e) {
                    System.err.println(line[2] + " is not a recognized file");
                }
            } else if (line[0].equalsIgnoreCase("requestS")) {
                try {
                   serverObject.requestandsleep(line[1]);
                } catch (Exception e) {
                    System.err.println("requestfileandsleep did not work");
                    System.err.println(e);
                }

            } else if (line[0].equalsIgnoreCase("requestD")) {
                try {
                    serverObject.requestanddelete(line[1]);
                } catch (Exception e) {
                    System.err.println("requestD did not work");
                    System.err.println(e);
                }
            } else if (line[0].equalsIgnoreCase("release")) {
                System.out.println("Releasing " + line[1] + " not implemented");

            } else {
                System.err.println(line[1] + " not recognized for " + line[0]);
            }
        }

        } catch(Exception e) {
            System.out.println("Error in invoking object method: "+e.toString()+" "+e.getMessage());
            e.printStackTrace();
        }
    }
}
