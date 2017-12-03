import java.net.*;
import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    int port;
    String serverHost = "localhost";
    StoredFiles files;

    public static void main(String[] args) throws IOException {
        int serverPort = 1234;
        Socket server;
        String serverHost = "localhost";
        String name1 = "hey ya";
        String src1 = "HeyYa.mp3";
        String name2 = "you and me";
        String src2 = "youandme.mp3";

        ObjectOutputStream objectToServer;


        try {
            server = new Socket(serverHost, serverPort);
            //Client connects to server

            //Client connected
            System.out.println("Connected to: "+server.getInetAddress());


            //Get client's input stream
            InputStream fromServer = server.getInputStream();
            ObjectInputStream objectFromServer = new ObjectInputStream(fromServer);

            OutputStream toServer = server.getOutputStream();
            objectToServer = new ObjectOutputStream(toServer);

            Thread t = new Thread(new waitingForFile(objectFromServer));
            t.start();
            ManageFile obj = (ManageFile) Naming.lookup("rmi://" +serverHost + "/ArithServer");
            obj.add(name1, src1);
            obj.add(name2, src2);
            obj.deleteFile(name1);

            //Scanner sc = new Scanner(System.in);
            //int choice;

            //choice = sc.nextInt();

           /* if(choice == 1){
                Scanner fileIn = new Scanner(System.in);
                System.out.println("Please enter the file name");
                String name = fileIn.nextLine();
                Scanner srcIn = new Scanner(System.in);
                System.out.println("Please enter the file source");
                String src = srcIn.nextLine();
                Client a = new Client();
                a.test(name, src);


            } */

        } catch(Exception e) {
            System.out.println("Error: "+e.getMessage());
        }



    }

    /*public StoredFiles test(String name, String src) throws RemoteException, NotBoundException, MalformedURLException {
        ManageFile obj = (ManageFile) Naming.lookup("rmi://" +serverHost + "files");
        files = obj.add(name, src);
        return files;
    }
   /* public static boolean addFile(ObjectOutputStream objectToServer){
        Boolean bool = false;
        Scanner fileIn = new Scanner(System.in);
        System.out.println("Please enter the file name");
        String fileName = fileIn.nextLine();
        Scanner srcIn = new Scanner(System.in);
        System.out.println("Please enter the file source");
        String src = srcIn.nextLine();

        StoredFiles addNew = new StoredFiles("fileName", "src");
        try {
            objectToServer.writeObject(addNew);
            objectToServer.flush();
            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bool;
    } */
}
