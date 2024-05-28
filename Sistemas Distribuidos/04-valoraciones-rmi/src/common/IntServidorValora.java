package common;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorValora extends Remote {

    public void guardaDatos() throws RemoteException;
    public JSONArray consultaValoraciones(String nomRest) throws RemoteException;
    public JSONObject hazValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) throws RemoteException;
    public JSONObject borraValoracion(String codcliente, String nomRest, String fecha) throws RemoteException;
    public JSONObject modificaValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) throws RemoteException;
    public JSONArray listaRestaurantes() throws RemoteException;

    public boolean registerForCallback(IntCallbackCliente callbackClientObject, String nomRest) throws RemoteException;
    public boolean unregisterForCallback(IntCallbackCliente callbackClientObject, String nomRest) throws RemoteException;

}
