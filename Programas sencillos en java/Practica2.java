package Practica2;

import java.util.*;

public class Practica2 {

    //Dado un iterador al inicio de una lista y un elemento elem, debe borrar las ocurrencias de elem en la
    //lista que ocupen posiciones pares. El 0 se considerará par
    public static<T> void borrarEnPares(ListIterator<T> it, T elem) {
        int index = 0;
        while(it.hasNext()){
            T s = it.next();
            if(index%2 == 0 && s.equals(elem))
            it.remove();
            index++;
        }
    }

    /** Invierte el orden de los elmentos de una lista.
     *
     * @param iter Un iterador de la lista. Puede estar en cualqueir posición de la lista.
     */
    static public void invierte(ListIterator<String> iter) {

        while(iter.hasNext())
            iter.next();                        // Coloco el iterador al final
        int Final = iter.previousIndex();       // Guardo el indice del último elemento para saber cuantos hay
        for(int i=0;i<Final;i++){               // Repito el proceso n-1 veces (siendo n el número de elementos)
            while(iter.hasPrevious())
                iter.previous();                // Coloco el iterador al principio
            String s = iter.next();             // Guardo el primer elemento en s
            iter.remove();                      // Borro el primer elemento
            for(int x=0; x<(Final-i);x++)       // Coloco el iterador justo despues del elemento
                iter.next();                    // que era el último antes de modificar la lista
            iter.add(s);                        // Añado s (el elemento borrado) en la posición del iterador
        }

    }

    //Busca la palabra y cambia la anterior palabra en la frase por **** (mismo número de * que
    //longitud de la palabra).
    public static int changeWord (List<List<String>> mytext, String word) {
        int borrados = 0;

        for(List<String> l : mytext){
            ListIterator<String> prev = l.listIterator();
            if(prev.hasNext()) {
                ListIterator<String> s = l.listIterator(1);
                while(s.hasNext()) {
                    String T = s.next();
                    String P = prev.next();
                    if (T.equalsIgnoreCase(word)) {
                        prev.set("*".repeat(P.length()));
                        borrados++;
                    }
                }
            }
        }
        return borrados;
    }
 

}
