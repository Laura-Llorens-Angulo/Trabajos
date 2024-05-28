package server;

import common.IntCallbackCliente;
import common.IntServidorValora;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ImplServidorValora extends UnicastRemoteObject implements IntServidorValora {
    GestorValoraciones gestor;
    Map<String, Vector<IntCallbackCliente>> clientesCallbacks;


    public ImplServidorValora() throws RemoteException {
        super();
        this.gestor = new GestorValoraciones();
        this.clientesCallbacks = new HashMap<String, Vector<IntCallbackCliente>>();
    }

    @Override
    public void guardaDatos() throws RemoteException {
        gestor.guardaDatos();
    }

    @Override
    public JSONArray consultaValoraciones(String nomRest) throws RemoteException {
        return gestor.consultaValoraciones(nomRest);
    }

    @Override
    public JSONObject hazValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) throws RemoteException {
        JSONObject respuesta = gestor.hazValoracion(codcliente, nomRest, fecha, estrellas, comentario);
        if(!respuesta.isEmpty()) doCallbacks(nomRest);
        return respuesta;
    }

    @Override
    public JSONObject borraValoracion(String codcliente, String nomRest, String fecha) throws RemoteException {
        JSONObject respuesta = gestor.borraValoracion(codcliente, nomRest, fecha);
        if(!respuesta.isEmpty()) doCallbacks(nomRest);
        return respuesta;
    }

    @Override
    public JSONObject modificaValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) throws RemoteException {
        JSONObject respuesta = gestor.modificaValoracion(codcliente, nomRest, fecha, estrellas, comentario);
        if(!respuesta.isEmpty()) doCallbacks(nomRest);
        return respuesta;
    }

    @Override
    public JSONArray listaRestaurantes() throws RemoteException {
        return gestor.listaRestaurantes();
    }

    @Override
    public synchronized boolean registerForCallback(IntCallbackCliente callbackClientObject, String nomRest) throws RemoteException {

        if(clientesCallbacks.containsKey(nomRest)){
            if (!clientesCallbacks.get(nomRest).contains(callbackClientObject)) {
                clientesCallbacks.get(nomRest).add(callbackClientObject);
                System.out.println("Se ha dado de alta al cliente.");
                return true;
            } else {
                System.out.println("El cliente ya estaba suscrito");
                return false;
            }
        }else{
            if(gestor.listaRestaurantes().contains(nomRest)){
                Vector<IntCallbackCliente> aux = new Vector<>();
                aux.add(callbackClientObject);
                clientesCallbacks.put(nomRest,aux);
                return true;
            }else{
                System.out.println("No existe el restaurante.");
                return false;
            }
        }

    }


    @Override
    public synchronized boolean unregisterForCallback(IntCallbackCliente callbackClientObject, String nomRest) throws RemoteException {
        if (clientesCallbacks.containsKey(nomRest) && clientesCallbacks.get(nomRest).contains(callbackClientObject)) {
            clientesCallbacks.get(nomRest).remove(callbackClientObject);
            System.out.println("Se ha dado de baja al cliente.");
            return true;
        } else {
            System.out.println("No se ha podido dar de baja al cliente");
            return false;
        }
    }

    private synchronized void doCallbacks(String nomRest) throws RemoteException {
        System.out.println("*********\nCallbacks initiated ---");
        Vector<IntCallbackCliente> clientes = clientesCallbacks.get(nomRest);
        if(clientes != null) {
            for (int i = clientes.size() - 1; i >= 0; i--) {       //IMPORTANTE RECORRERLO DESDE EL FINAL AL INICIO PARA NO SALTARNOS UN ELEMENTO AL BORRAR
                System.out.println("Haciendo " + i + "º callback\n");
                IntCallbackCliente nextClient = clientes.elementAt(i);

                try {
                    nextClient.notifyMe("Una valoracion del restaurante " + nomRest + " ha sido añadida, modificada o borrada.");
                } catch (RemoteException e) {
                    System.out.println("Eliminando un cliente inactivo de la lista de callbacks.");
                    clientesCallbacks.get(nomRest).remove(i);
                }

            }
        }
        System.out.println("********\nServer completed callbacks ---");
    }



}
