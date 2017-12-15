
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ManageFile extends Remote{
    public void add (String obj) throws IOException, ClassNotFoundException;
    public void deleteFile(String name) throws RemoteException;
    public void requestandsleep(String name)throws RemoteException;
    public void requestanddelete(String name)throws RemoteException;

}
