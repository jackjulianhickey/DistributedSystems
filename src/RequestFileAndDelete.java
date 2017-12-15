
import java.net.Socket;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class RequestFileAndDelete implements Runnable {

    Socket client;
    String name;
    List<String> fileN;
    List<String> fileS;
    Lock lock;

    public RequestFileAndDelete(Socket client, String name, List<String> fileN, List<String> fileS, Lock lock){
        this.client = client;
        this.name = name;
        this.fileN = fileN;
        this.fileS = fileS;
        this.lock = lock;
    }
    @Override
    public void run() {
        this.lock.lock();
        try {
            Thread.sleep(3000);
            int i = fileN.indexOf(name);
            fileN.remove(i);
            fileS.remove(i);
            this.lock.unlock();
            System.out.println("this file has been deleted " + name);

        }catch (Exception e){
            System.err.println("Sorry that file does not exist");
        }


    }
}
