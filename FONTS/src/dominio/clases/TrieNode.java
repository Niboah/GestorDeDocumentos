package dominio.clases;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ruben.dabrio
 */

/**
 * Estructura que representa un nodo de un arbol de prefijos
 */

public class TrieNode {

    /**
     * Indica si el nodo es el final de un nombre de autor o no
     */
    private boolean autor;

    /**
     * Conjunto de los nodos hijos del mismo
     */
    private HashMap<Character, TrieNode> hijos;

    /**
     * Valor del nodo actual
     */
    private char valor;


    /**
     * Constructora por defecto
     */
    public TrieNode(char valor) {
        this.hijos = new HashMap<>();
        this.autor = false;
        this.valor = valor;
    }

    public TrieNode() {
        this.hijos = new HashMap<>();
        this.autor = false;
        this.valor = ' ';
    }


    /**
     * Inserta en el arbol el autor
     * @param autor nombre del autor
     */
    public void insertaAutor(String autor) {

        TrieNode actual = this;
        for (char c : autor.toCharArray()) {

            if (!actual.hijos.containsKey(c)) actual.hijos.put(c, new TrieNode(c));
            actual = actual.hijos.get(c);
        }
        actual.autor = true;
    }

    /**
     * Comprueba si existe un autor en el arbol
     * @param autor nombre del autor
     * @return devuelve true si el autor existe, false en cualquier otro caso
     */
    public boolean buscarAutor(String autor) {

        TrieNode actual = this;

        for (char c : autor.toCharArray()) {
            if (!actual.hijos.containsKey(c)) return false;
            actual = actual.hijos.get(c);
        }
        return actual.autor;
    }

    /**
     * Elimina el autor del conjunto de autores
     * @param actual nodo actual en el que estamos buscando
     * @param autor nombre del autor
     * @param i indice del caracter del nombre del autor en el que estamos actualmente
     * @return devuelve true si se ha efectuado correctamente la eliminacion, false en cualquier otro caso
     */
    public boolean bajaAutor(TrieNode actual, String autor, int i) {

        if (i < autor.length()) {
            char c = autor.toCharArray()[i];
            if (actual.hijos.get(c) == null) return false;
            if (actual.hijos.size() > 0 && bajaAutor(actual.hijos.get(c), autor, i+1)) {
                actual.hijos.remove(c);
                return actual.hijos.size() == 0;
            }
        }

        else if (i == autor.length()) {
            return actual.hijos.size() == 0;
        }
        return false;

    }

    /**
     * Devuelve el conjunto de los autores que contengan un prefijo
     * @param pref prefijo a cumplir
     * @return devuelve null si no hay ningun autor que cumpla el prefijo, y devuelve la lista de los autores que
     * lo cumplen en caso contrario
     */
    public ArrayList<String> getAutoresPref(String pref) {
        TrieNode actual = this;

        for (char c : pref.toCharArray()) {

            if (!actual.hijos.containsKey(c)) return null;
            actual = actual.hijos.get(c);
        }
        return getAutoresPrefAux(actual, new ArrayList<>(), pref);
    }



    /**
     * Funcion auxiliar que devuelve la lista de los nombres de autor que ya cumplen con contener el prefijo dado
     * @param actual nodo del arbol actual donde se esta buscando
     * @param autores lista de nombres actuales
     * @param nombre sufijo del nombre que llevamos hasta ahora
     * @return devuelve la lista de los nombres de autor dentro del arbol actual
     */
    private ArrayList<String> getAutoresPrefAux(TrieNode actual, ArrayList<String> autores, String nombre) {

        if (actual.autor) autores.add(nombre);
        if (actual.hijos.size() == 0) return autores;
        for (TrieNode n : actual.hijos.values()) getAutoresPrefAux(n, autores, nombre.concat(String.valueOf(n.valor)));
        return autores;
    }

    /**
     * Devuelve el conjunto de todos los autores que existen
     * @return devuelve null en caso de no haber ningun autor, y devuelve la lista de todos los autores
     * existentes en caso contrario
     */
    public ArrayList<String> getTodosAutores() {
        ArrayList<String> autores = new ArrayList<>();
        TrieNode actual = this;

        for (TrieNode n : actual.hijos.values()) {
            autores.addAll((getAutoresPrefAux(n, new ArrayList<>(), String.valueOf(n.valor))));
        }
        if (autores.size() == 0) return null;
        return autores;
    }

}
