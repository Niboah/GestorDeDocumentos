package dominio.clases;
import java.util.ArrayList;


/**
 * @author ruben.dabrio
 */

/**
 * Estructura que representa una lista de autores
 */

public class ListaAutor {

    /**
     * Conjunto de nombre de los autores implementado como arbol de prefijos
     */
    private TrieNode trie;

    /**
     * Constructor por defecto, el primer nodo esta vacio
     */
    public ListaAutor() { this.trie = new TrieNode(); }


    /**
     * Añade un autor al conjunto de autores
     * @param n nombre del autor
     * @return Devuelve true si la operacion se ha efectuado correctamente y false si ya existe el autor
     */
    public boolean altaAutor(String n) throws Excepcion {

        if (!trie.buscarAutor(n)) {
            trie.insertaAutor(n);
            return true;
        }
        else throw new Excepcion("El autor ya existe");
    }


    /**
     * Indica si existe el autor o no
     * @param n nombre del autor
     * @return true si el autor existe, false en cualquier otro caso
     */
    public boolean existeAutor(String n) { return this.trie.buscarAutor(n); }


    /**
     * Devuelve la lista de nombres de los autores que empiecen por cierto prefijo
     * @param pref prefijo dado
     * @return devuelve la lista de los nombres de autores que empiecen por el prefijo, si ningun autor cumple
     * la condicion se devuelve null. En caso de dar un prefijo vacio ("") se devuelve la lista de los nombres
     * de todos los autores, pero si no existe ninguno se devuelve null
     */
    public ArrayList<String> getListaAutoresPrefijo(String pref) {

        ArrayList<String> autores;
        if (pref.equals("") || pref.equals("\n")) autores = trie.getTodosAutores();
        else autores = trie.getAutoresPref(pref);
        return autores;
    }


    /**
     * Elimina el autor del conjunto de autores
     * @param autor nombre del autor
     * @return Devuelve true si la operacion se ha efectuado correctamente y false si no existe
     */
    public boolean eliminaAutor(String autor) throws Excepcion {
        if (trie.buscarAutor(autor)) return trie.bajaAutor(this.trie, autor, 0);
        else throw new Excepcion("El autor no existe");
    }

}