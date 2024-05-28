package Practica6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MorseCode {

    EDBinaryNode<Character> morseRoot = null;

    public MorseCode() {
        morseRoot= new EDBinaryNode(null);
        readFileInfo();
    }

    private void readFileInfo() {
        Scanner input = null;
        try {
            input = new Scanner(new File("morseCodes.txt"));
        } catch (FileNotFoundException exception) {
            System.out.println("File not found!");
        }

        while (input.hasNextLine()) {
            String cars = input.next();
            char car = cars.charAt(0);
            String code = input.next();
            insert(car, code);
        }
        input.close();
    }

    public String inorderPrint() {
        return inorderPrint(morseRoot);
    }

    private String inorderPrint(EDBinaryNode<Character> n) {
        String s1, s2;
        if (n != null) {
            s1 = inorderPrint(n.left());
            s2 = inorderPrint(n.right());
            if (!n.containsNull())
                return s1 + n.data() + s2;
            else
                return s1 + s2;
        }
        return "";
    }

    /**
     *  Añade un nuevo carácter al árbol binario
     *
     *  El método debe añadir un nodo con el caracter car en el árbol binario, según la secuencia de puntos y rayas.
     *  Si el nodo ya existía se sobreescribw.
     *
     * @param car   Carácter que debe añadirse en el árbol binario
     * @param code  Codificación del carácter con puntos y rayas
     */
    public void insert(char car, String code) {
        EDBinaryNode<Character> aux = morseRoot;
        if(aux==null)
            morseRoot = new EDBinaryNode<Character>(' ');
        for(int i=0; i<code.length();i++){
            char c = code.charAt(i);
            if(c=='.'){
                if(!aux.hasLeft())
                    aux.setLeft(new EDBinaryNode<Character>(' '));
                aux=aux.left();
            }else{
                if(!aux.hasRight())
                    aux.setRight(new EDBinaryNode<Character>(' '));
                aux=aux.right();
            }
        }
        if(aux!=null)
            aux.setData(car);

    }

    /**
     * Decodifica una secuencia de puntos y rayas y la convierte en un texta alfabético.
     *
     * @param codetext Secuencia de puntos y rayas. Los códigos de cada letra independiente están separados por un
     *                 espacio en blanco.
     *
     * @return La cadena de de letras resultante
     */
    public String decode(String codetext) {
        EDBinaryNode<Character> aux = morseRoot;
        String text ="";
        for(int i=0;i<codetext.length();i++){
            char c = codetext.charAt(i);
            if(c!=' '){
                if(c=='.')
                    aux=aux.left();
                if(c=='-')
                    aux=aux.right();
            }else{
                text= text + aux.data();
                aux=morseRoot;
            }
        }
        text= text + aux.data();
        return text;
    }

    /**
     * Toma una cadena de texto y la codifica en puntos y rayas
     * @param text El texto a codificar
     *
     * @return Una cadena de puntos y rayas, se añaden espacios para separar los códigos de la letras.
     */
    public String encode(String text) {
        String codigoMorse = "";
        for(int i=0; i<text.length();i++){          //Cada letra de text
            char c = text.charAt(i);
            List camino = new ArrayList();
            boolean arbolRecorrido = false;
            Set<Character> letrasVistas =new HashSet<>();
            EDBinaryNode<Character> aux = morseRoot;
            if(c!=' ') {
                if (i > 0)
                    codigoMorse = codigoMorse + ' ';

                while (!arbolRecorrido) {              //hasta q hayamos recorrido el arbol o encontrado la letra
                    if (aux.data() != null && aux.data().equals(c))
                        break;

                    letrasVistas.add(aux.data());
                    if (aux.hasLeft() && !letrasVistas.contains(aux.left().data())) {
                        aux = aux.left();
                        camino.add('.');
                    } else {
                        if (aux.hasRight() && !letrasVistas.contains(aux.right().data())) {
                            aux = aux.right();
                            camino.add('-');
                        } else {
                            if (aux.hasParent()) {
                                aux = aux.parent();
                                camino.remove(camino.size() - 1);
                            } else
                                arbolRecorrido = true;
                        }
                    }
                }
                for (Object codigo : camino) {
                    codigoMorse = codigoMorse + codigo;
                }
            }
        }
        return codigoMorse;
    }

}
