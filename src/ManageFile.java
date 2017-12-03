
import java.rmi.*;

public interface ManageFile extends java.rmi.Remote{
    public void add (String name, String src) throws java.rmi.RemoteException;
    public void deleteFile(String name)throws java.rmi.RemoteException;
}
