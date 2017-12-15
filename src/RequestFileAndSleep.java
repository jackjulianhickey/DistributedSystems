import java.net.Socket;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class RequestFileAndSleep implements Runnable {

    Socket client;
    String name;
    List<String> file;
    Lock lock;

    public RequestFileAndSleep(Socket client, String name, List<String> file, Lock lock){
        this.client = client;
        this.name = name;
        this.file = file;
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
        this.lock.lock();

        if(file.indexOf(name) >= 0) {
            System.out.println("Client is currently using file " + name);

            Thread.sleep(3000);
            this.lock.unlock();
            System.out.println("Client is no longer using this file " + name);
        }else{
            System.out.println("Sorry could not find the file " + name);
        }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
