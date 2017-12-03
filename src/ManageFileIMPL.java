
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class ManageFileIMPL extends UnicastRemoteObject implements ManageFile {
    private String sname;
    private Lock lock;
    int j=0;
    List<String> files = new ArrayList<>();
    List<String> filesSrc = new ArrayList<>();
    public ManageFileIMPL(String s) throws RemoteException {
        sname =s;
    }

    @Override
    public void add(String name, String src) throws RemoteException {
        this.lock.lock();
        try {
            int local = j;
            lock.lock();
            files.add(name);
            filesSrc.add(src);
            lock.unlock();
            j++;
            System.out.println(files.get(local) + " has been added");
        }catch (Exception e){
            System.out.println("File could not be added\n"+ e);
        }
        this.lock.unlock();

    }

    @Override
    public void deleteFile(String name) throws RemoteException{
        this.lock.lock();
        try {
            int index = files.indexOf(name);
            System.out.println("this is the index " + index);
            lock.lock();
            files.remove(index);
            filesSrc.remove(index);
            lock.unlock();
            //files.remove(index+1);
            System.out.println(name + " has been removed");
        }catch (Exception e){
            System.out.println("File not found\n" + e);
        }
        this.lock.unlock();

    }
}
