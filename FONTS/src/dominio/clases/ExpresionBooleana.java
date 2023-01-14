package dominio.clases;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author haobin.guo
 */

/**
 * Estructura para guarda una expresion booleana.
 */
public class ExpresionBooleana {

    /**
     * Expresion booleana en formato texto
     */
    private String expresion;
    /**
     * Expresion booleana en formato {@link BoolNode}
     */
    private BoolNode boolTree;

    /**
     * Constructor por defecto.
     */
    public ExpresionBooleana() {
        this.expresion = "";
        this.boolTree=new BoolNode();
    }

    /**
     * Constructor por parametro, con inicializacion de expresion
     * @param expresion expresion booleana
     */
    public ExpresionBooleana(String expresion){
        this.expresion = expresion;
        this.boolTree=new BoolNode(this.expresion);
    }

    /**
     * Retorna la expresion actual
     * @return Devuelve la expresion actual
     */
    public String getExpresion() {
        return expresion;
    }

    /**
     * Retorna la lista de terminos que aparece en la expresion booleana
     * @return Devuelve la lista de terminos que aparece en la expresion, null si la lista es vacia
     */
    public ArrayList<String> getTerms(){
        String expr = this.expresion.replaceAll("[!&|(){}]","");
        ArrayList<String> list = new ArrayList<>();
        for (String a: expr.split(" "))list.add(a);
        if(list.size()==0)return null;
        return  list;
    }

    /**
     * Crear la estructura pertinente para el posterior analisis.
     * @return Devuelve true si se ha creado correctamente, si no false
     * @exception  Exception Expresion vacia
     * @exception  Exception Posicion incorrecto de (), {}, o !
     * @exception  Exception Primera o segunda posion de la expresion no valida
     * @exception  Exception No hay operadores logicos
     */
    public boolean makeTree() throws Exception {
        LinkedList<BoolNode> queue = new LinkedList<>();
        queue.add(this.boolTree);
        while (queue.size()>0){
            BoolNode nActual = queue.getFirst();
            queue.removeFirst();
            String [] next=null;
            next = analyzeExpre(nActual.getValue());
            if(next == null)return false;
            boolean containValue = next.length>=1;
            boolean containLeft = next.length>=2;
            boolean containRight = next.length>=3;

            if(containValue){
                nActual.setValue(next[0]);
                if(next[0].equals("|")) nActual.setType(BoolNode.BoolNodeType.LEFT_OR_RIGHT);
                else if(next[0].equals("&")) nActual.setType(BoolNode.BoolNodeType.LEFT_AND_RIGHT);
                else if(next[0].equals("!")) nActual.setType(BoolNode.BoolNodeType.NOT_LEFT);
                else if(next[0].equals("()")) nActual.setType(BoolNode.BoolNodeType.LEFT);
                else if(next[0].equals("{}")) nActual.setType(BoolNode.BoolNodeType.LEFT);
                else nActual.setType(BoolNode.BoolNodeType.VALUE);
            }
            if(containLeft){
                BoolNode l=new BoolNode(next[1]);
                nActual.setLeft(l);
                queue.addLast(l);
            }
            if(containRight){
                BoolNode r=new BoolNode(next[2]);
                nActual.setRight(r);
                queue.addLast(r);
            }

        }
        return true;
    }

    /**
     * Comprueba que la frase cumpla la expresion booleana.
     * @param phrase frase que se aplica la expresion booleana
     * @return Devuelve true si la frase cumple la expresion booleana.
     */
    public boolean apply(String phrase){
        return this.boolTree.apply(phrase);
    }

    /**
     * Busca el conjunto de documentos que tengan una frase que cumpla la expresin booleana
     * @param documentos conjunto de documentos que se hace la busqueda
     * @return Devuelve un conjunto de documentos que tengan una frase que cumpla la expresion booleana, null si el conjunto es vacio.
     */
    public ArrayList<String> exprBooleanSearch(ArrayList<Documento> documentos){
        if(documentos==null)return null;
        ArrayList <String> list= new ArrayList<>();
        for(Documento d : documentos){
            String contenido = d.getContenido();
            contenido = contenido.replaceAll("\n"," ");
            String [] phrase = contenido.split("\\.");
            for(String f : phrase){
                if(apply(f)){
                    list.add(d.toString());
                    break;
                }
            }
        }
        if(list.size()==0)return null;
        return list;
    }

    /**
     * Analiza la expresion
     * @param expr expresion a analizar
     * @return Devuelve un conjunto de partes de la expresion valido, null si la expresion no es valida
     * @exception  Exception Expresion vacia
     * @exception  Exception Posicion incorrecto de (), {}, o !
     * @exception  Exception Primera o segunda posion de la expresion no valida
     * @exception  Exception No hay operadores logicos
     */
    private String[] analyzeExpre(String expr)  throws Exception {
        String[] r = null;
        if(expr.equals("")) throw new Exception("Expresion vacia");
        int i= firstAndOr(expr);

        if(i==-1){
            if(expr.charAt(0)=='{'){
                // { _ _ _ }
                if(expr.charAt(expr.length()-1)=='}'){
                    expr=expr.replace(" "," & ");
                    r = new String[2];
                    r[0]="{}";
                    r[1]=expr.substring(1, (expr.length() - 1));
                    return r;
                }else return null;
            }else if(expr.charAt(0)=='('){
                // (         )
                if(expr.charAt(expr.length()-1)==')') {
                    r = new String[2];
                    r[0]="()";
                    r[1]=expr.substring(1, (expr.length() - 1));
                    return r;
                }else return null;

            }else if(expr.charAt(0)=='!'){
                r = new String [2];
                r[0]="!";
                r[1]=expr.substring(1);
                return r;
            }else if(containP(expr)) throw new Exception("Posicion incorrecto de (), {}, , ! o espacios: "+expr);
            r = new String[1];
            r[0] = expr;
            return r;
        }else if(i<2)throw new Exception("Primera o segunda posicion de la expresion no valida: "+expr);

        if(!validAndOrPosition(i,expr)) throw  new Exception("Posicion del operador logico no valido: "+expr);

        r = new String[3];
        r[0]=expr.charAt(i) + "";
        r[1]=expr.substring(0, (i - 1));
        r[2]=expr.substring(i + 2);

        return r;
    }

    /**
     * Busca el primer operador logico a complobar en expr
     * @param expr expresion en la que se busca
     * @return Devuelve la posicion del primero operador logico que se aplica, -1 si no existe
     */
        private static int firstAndOr(String expr){
        int c=0;
        int p=0;
        boolean k=false;
        for(int i=0; i<expr.length();++i){
            char v = expr.charAt(i);
            if(v =='"')k=!k;
            if(k)continue;
            if(v=='{')c++;
            else if(v=='}')c--;
            else if(v=='(')p++;
            else if(v==')')p--;
            else if(c==0 && p==0){
                if(v=='|') return i;
                else if(v=='&')return i;
            }
        }
        return -1;
    }

    /**
     * Valida la posición del operador lógico.
     * @param i posicion en la que esta el operador logico
     * @param expr expresin en la que se analiza
     * @return Devuelve true si es valido el operador logico en la posicion i, false si no lo es.
     */
    private static boolean validAndOrPosition(int i, String expr){
        if(i-1<0)return false;
        if(i+1>expr.length())return false;
        if(expr.charAt(i-1)!=' ')return false;
        if(expr.charAt(i+1)!=' ')return false;
        return true;
    }

    /**
     * Busca (), {}, ! o espacios en la expresion, ignorado los que están entre comillas "
     * @param expre expresion en la que se busca
     * @return Devuelve true si hay (), {}, ! o espacios en la expresion.
     */
    private static boolean containP(String expre){
        boolean comillas=false;
        for(char c : expre.toCharArray()){
            if(c== '"') comillas=!comillas;
            if(comillas) continue;
            if(c == ')' | c == '(' | c == '{'| c == '}' | c == '!' | c == ' ') return true;
        }
        return false;
    }

}
