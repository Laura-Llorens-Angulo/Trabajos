package client;

import common.IntCallbackCliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImpCallbackCliente extends UnicastRemoteObject implements IntCallbackCliente {
    public ImpCallbackCliente() throws RemoteException {
        super();
    }

    @Override
    public void notifyMe(String message) throws RemoteException {
        System.out.println("\n SERVIDOR DICE: " + message);
    }
}
