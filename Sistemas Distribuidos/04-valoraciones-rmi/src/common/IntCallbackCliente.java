package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntCallbackCliente extends Remote {
    public void notifyMe(String message) throws RemoteException;
}
