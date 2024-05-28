package Practica1;

import java.util.*;

public class Practica1 {

    //Calcula la diferencia simétrica de s1 y s2 en s1. s2 contiene los elementos que están en ambos conjuntos
    public static <T> void difSimetrica (Set<T> s1, Set<T> s2) {
        Set<T> s3 = new TreeSet<>(s2);
        s2.retainAll(s1);
        s3.removeAll(s2);
        s1.removeAll(s2);
        s1.addAll(s3);
    }

    //Calcula y devuelve la diferencia simétrica de una colección de conjuntos
    public static <T> Set<T> difSimetricaCol (Collection<Set<T>> col) {
        Set<T> dif = new HashSet<>();
        for (Set<T> s : col) {
            difSimetrica(dif, s);
        }
        return dif;
    }

    //Dado un iterador a una colleción de elementos, devolver un conjunto con los elementos que no se repiten
    //en la colección inicial deben quedar solo los elementos repetidos
    public static <T> Set<T> unicos (Iterator<T> it) {
        Set<T> unicos = new HashSet<>();
        while(it.hasNext()){
            T s = it.next();
            if(unicos.contains(s))
               unicos.remove(s);
            else
                unicos.add(s);
        }
        return unicos;
    }

    //Dada una colección y un conjunto de elementos del mismo tipo, devuelve cuántas veces ocurre el conjunto
    //en la colección (teniendo en cuenta que cada elemento de la colección solo puede ser usado una vez)
    public static <T> int  numOcurrecias  (Collection<T> col, Set<T> s) {
        int ocuTotal = 0;
        for(T its : s){
            int ocuelem=0;
            for(T itcol : col){
                if(its.equals(itcol))
                    ocuelem++;
            }
            if(ocuelem == 0)
                return 0;
            else{
                if(ocuTotal == 0 || ocuTotal>ocuelem)
                    ocuTotal=ocuelem;
            }
        }
        return ocuTotal;
    }

    //Dividir una colección de elementos en conjuntos, según el orden de los elementos en la colección
    public static <T> Collection<Set<T>>  split  (Collection<T> col) {
        Collection<Set<T>> divididos = new LinkedList<>();
        Set<T> aux = new HashSet<>();
        for(T s : col){
            if(aux.contains(s)){
                Set<T> e = new HashSet<>(aux);
                divididos.add(e);
                aux.clear();
            }
            aux.add(s);
        }
        divididos.add(aux);
        return divididos;
    }
}
