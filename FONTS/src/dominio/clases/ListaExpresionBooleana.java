package dominio.clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * @author haobin.guo
 */

/**
 * Estructura para guardar un conjunto de {@link ExpresionBooleana}
 */
public class ListaExpresionBooleana {

    /**
     * Instancia de la clase para aplicar el patron sigleton
     */
    private static ListaExpresionBooleana singletonListaExpresionBooleana;

    /**
     * Conjunto expresiones booleanas, ordenado por orden de insercion
     * Key: Expresion en text
     * Value: Clase ExpesionBooleana
     */
    private final LinkedHashMap<String, ExpresionBooleana> lExprBool;

    /**
     * Constructor por defecto
     */
    private ListaExpresionBooleana(){
        this.lExprBool = new LinkedHashMap<>();
    }

    /**
     * Retorna la instancia de la clase
     * @return Retorna la instancia de la clase
     */
    public static ListaExpresionBooleana getInstance(){
        if(singletonListaExpresionBooleana ==null)return new ListaExpresionBooleana();
        else return singletonListaExpresionBooleana;
    }

    /**
     * Buscar una expresion en el conjunto.
     * @param expr expresion a buscar
     * @return Devuelve un {@link ExpresionBooleana} de la expresion buscada, null si no existe en el conjunto
     */
    public ExpresionBooleana get(String expr){
        return this.lExprBool.get(expr);
    }


    /**
     * Retorna el conjunto de expresion booleana como String en orden especificado
     * @return Devuelve el conjunto de expresion booleana como un conjunto de String por orden, 0 orden de insercion, 1 alfabeticamente, 2 alfabeticamente inverso
     */
    public ArrayList<String> getAll(int ord){
        if(ord==0) {
            ArrayList<String> r = new ArrayList<> (this.lExprBool.keySet());
            Collections.reverse(r);
            return r;
        }
        ArrayList<String> list=new ArrayList<>(this.lExprBool.keySet());
        if(ord==1) Collections.sort(list);
        else if(ord==2) Collections.sort(list,Collections.reverseOrder());
        return  list;
    }

    /**
     * Comprueba la existencia de una expresion en el conjunto de expresiones booleanas
     * @param expr expresion a comprobar
     * @return Devuelve true si conjunto de expresiones booleans contiene la expresion
     */
    public boolean exist(String expr){
        return this.lExprBool.containsKey(expr);
    }

    /**
     * Añade una expresion booleana al conjunto de expresiones booleans
     * @param expr expresion a añadir
     * @return Devuelve true, si se ha añadido
     */
    public boolean add(ExpresionBooleana expr){
        if(exist(expr.getExpresion()))this.lExprBool.remove(expr.getExpresion());
        this.lExprBool.put(expr.getExpresion(),expr);
        return true;
    }

    /**
     * Elimina la expr del conjunto de expresiones booleanas
     * @param expr expresion a añadir
     * @return Devuelve true si se ha eliminado, false, si no hay cambios
     */
    public boolean remove(String expr){
        return this.lExprBool.remove(expr) == null ? false : true;
    }

    /**
     * Reemplaza una expresion por otra
     * @param oldExpre expresion vieja
     * @param newExpre expresion nueva
     * @return Devuelve true si se ha hecho correctamente la operacion, sino false
     */
    public boolean replace(String oldExpre, String newExpre) throws Exception {
        ExpresionBooleana nE = new ExpresionBooleana(newExpre);
        if(exist(newExpre))throw new Exception("La nueva expresion ya existe");
        if(nE.makeTree())
            if(exist(oldExpre))
                if(this.add(nE))
                    return this.remove(oldExpre);
        return false;
    }

}
