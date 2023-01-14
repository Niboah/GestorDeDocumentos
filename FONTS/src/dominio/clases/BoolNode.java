package dominio.clases;

/**
 * @author  haobin.guo
 */

/**
 * Estructura para representar expresiones booleanas, expresiones que tengan operadores logicos (ej, &amp;, |, !).<br>
 * La estructura se basa en la idea de Binary Tree, tiene un valor, un tipo, un nodo izquierdo y un nodo derecho.<br>
 * El valor puede ser un operador logico aplicado a un nodo o dos nodos, o un valor, dependiendo del tipo<br>
 * Tipo:<br>
 *  -1  : no definido <br>
 *  0   : valor<br>
 *  1   : nodo izquierdo | nodo derecho<br>
 *  2   : nodo izquierdo &amp; nodo derecho<br>
 *  3   : ! nodo izquierdo<br>
 *  4   : ! nodo derecho<br>
 *  5   : nodo izquierdo<br>
 *  6   : nodo derecho<br>
 */
public class BoolNode {
    enum BoolNodeType {
        UNDEFINED(-1),
        VALUE(0),
        LEFT_OR_RIGHT(1),
        LEFT_AND_RIGHT(2),
        NOT_LEFT(3),
        NOT_RIGHT(4),
        LEFT(5),
        RIGHT(6);

        private final int code;

        BoolNodeType(int code) {
            this.code = code;
        }
    }


    /**
     * Valor del BoolNode
     */
    private String value;
    /**
     * Tipo del BoolNode
     */
    private BoolNodeType type;

    /**
     * Nodo izquierdo del BoolNode
     */
    private BoolNode left;
    /**
     * Nodo derecho del BoolNode
     */
    private BoolNode right;
    /**
     * Constructor por defecto, con inicializacion del valor a vacio, tipo a -1, nodos a null
     */
    public BoolNode(){
        this.value="";
        this.type= BoolNodeType.UNDEFINED;
        this.left=null;
        this.right=null;
    }

    /**
     * Constructor por parametro, con inicializacion del valor a expresion, tipo a -1, nodos a null
     * @param expresion expresion
     */
    public BoolNode(String expresion){
        this.value=expresion;
        this.type= BoolNodeType.UNDEFINED;
        this.left=null;
        this.right=null;
    }

    /**
     * Retorna el valor del nodo actual
     * @return Devuelve valor actual
     */
    public String getValue() {
        return value;
    }

    /**
     * Modifica el valor del nodo actual
     * @param value nuevo valor
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Modifica el nodo izquiedo
     * @param left nuevo nodo izquiedo
     */
    public void setLeft(BoolNode left) {
        this.left = left;
    }

    /**
     * Modifica el nodo derecho
     * @param right nuevo nodo derecho
     */
    public void setRight(BoolNode right) {
        this.right = right;
    }

    /**
     * Modifica el tipo de nodo
     * @param type nievo tipo del nodo
     */
    public void setType(BoolNodeType type) {
        this.type =type;
    }

    /**
     * Comprueba que la frase cumpla la sub expresion booleana partiendo de este nodo
     * @param phrase frase que se aplica la expresion booleana
     * @return Devuelve true si la frase cumple la expresion booleana representada a partir de este nodo
     */
    public boolean apply(String phrase){
        if (this.type == BoolNodeType.VALUE || (this.right == null && this.left == null)) {
            String cleanValue = value.replaceAll("\"", "");
            int indexOfValue = phrase.indexOf(cleanValue);
            while(indexOfValue != -1 && indexOfValue < phrase.length()){
                int indexEndOfValue = indexOfValue + cleanValue.length();
                boolean valueWrongStart = indexOfValue > 0 && phrase.charAt(indexOfValue - 1) != ' ';
                boolean valueInEndOfPhrase = phrase.length()==indexEndOfValue;
                boolean valueWrongEnd = false;
                if(! valueInEndOfPhrase) valueWrongEnd = phrase.charAt(indexEndOfValue) != ' ';
                boolean phraseFails = valueWrongStart || valueWrongEnd;
                if(!phraseFails)return true;
                indexOfValue = phrase.indexOf(cleanValue,indexOfValue+1);
            }
            return false;
        } else if (this.type == BoolNodeType.LEFT_OR_RIGHT) return left.apply(phrase) || right.apply(phrase);
        else if (this.type == BoolNodeType.LEFT_AND_RIGHT) return left.apply(phrase) && right.apply(phrase);
        else if (this.type == BoolNodeType.NOT_LEFT && this.left != null) return !left.apply(phrase);
        else if (this.type == BoolNodeType.NOT_RIGHT && this.right != null) return !right.apply(phrase);
        else if (this.type == BoolNodeType.LEFT || this.right == null) return left.apply(phrase);
        else if (this.type == BoolNodeType.RIGHT || this.left == null) return right.apply(phrase);
        return false;
    }
}
