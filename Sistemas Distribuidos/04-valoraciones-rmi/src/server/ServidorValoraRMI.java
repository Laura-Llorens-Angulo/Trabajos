package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorValoraRMI {
    public static void main (String args[]) throws RemoteException {

        try {
            int RMIPortNum = 1099;
            startRegistry(RMIPortNum);
            ImplServidorValora servidorValoraciones = new ImplServidorValora();
            String urlRegistro = "rmi://localhost:" + RMIPortNum + "/Valoraciones";
            Naming.rebind(urlRegistro, servidorValoraciones);
        } catch (Exception e) {
            System.err.println("Excepcion en ServidorViajes:");
            System.exit(1);
        }
    }

    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registro = LocateRegistry.getRegistry(RMIPortNum);
            registro.list();
        }catch (RemoteException e){
            System.out.println("El registro RMI no se puede localizar en el puerto: " + RMIPortNum);
            Registry registro = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("El registro RMI creado en el puerto: " + RMIPortNum);
        }
    }
}
