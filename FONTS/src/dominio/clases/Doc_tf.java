package dominio.clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Doc_tf {

    private HashMap<String,Double> doc_tf;

    /**
     * Constructor de Doc_tf a partir de Documento
     * @param Doc documento a tratar
     */
    public Doc_tf(Documento Doc) {
        this.doc_tf = getTF(Doc.getContenido());
    }

    /**
     * Constructor de Doc_tf a partir de string
     * @param termFreq string que convertiremos a mapa de frecuencia de terminos
     */
    public Doc_tf(String termFreq) {
        this.doc_tf = stringToMap(termFreq);
    }

    /**
     * Devuelve el mapa de frecuencia de terminos del documento
     * @return mapa de frecuencia de terminos del documento
     */
    public HashMap<String,Double> getDocTF() {
        return this.doc_tf;
    }

    /**
     * Devuelve un set de string de todos los terminos del documento
     * @return set de string que contiene todos los terminos del documento
     */
    public HashSet<String> getTermsDoc() {
        return new HashSet<>(doc_tf.keySet());
    }

    /**
     * Metodo para devolver el numero de terminos del documento
     * @return Devuelve el numero de terminos en el documento
     */
    public int getTermsSizeDoc(){
        return this.doc_tf.size();
    }

    /**
     * Metodo para devolver la frecuencia del termino "term" en el documento
     * @param term termino del que buscamos la frecuencia
     * @return Devuelve la frecuencia del termino "term" si esta en el documento, si no devuelve 0.0
     */
    public double getTermFrequencyDoc(String term){
        return this.doc_tf.get(term)!=null?this.doc_tf.get(term):0.0;
    }

    /**
     * Dado un nuevo contenido recalcula el mapa de frecuencia de terminos del documento
     * @param nContenido nuevo contenido a tratar
     */
    public void modificarTF(String nContenido) {
        this.doc_tf = getTF(nContenido);
    }

    /**
     * Dados el termino y su nuevo peso, cambia el peso del termino en el documento
     * @param palabra termino del que cambiar el peso
     * @param new_value nuevo peso del termino
     */
    public void modificarPesoPalabraDoc(String palabra, Double new_value) {
        this.doc_tf.put(palabra,new_value);
    }

    /**
     * Comprueba que un documento contenga algun termino de un conjunto
     * @param terms conjunto de terminos
     * @return Devuelve true si el documento contiene algun termino de terms
     */
    public boolean documentoContiene(ArrayList<String> terms){
        for(String s: terms)if(doc_tf.get(s.toLowerCase())!=null)return true;
        return false;
    }

    /**
     * Convierte un string en un formato concreto al mapa de frecuencia de terminos
     * @param termFrequency frecuencia de terminos en String
     * @return Devuelve el mapa de frecuencia de terminos a partir del string
     */
    private HashMap<String, Double> stringToMap(String termFrequency){
        HashMap<String, Double> result = new HashMap<>();
        termFrequency=termFrequency.replaceAll("[{} ]","");
        String[] terms = termFrequency.split(",");
        for(String term : terms){
            if(term.equals(""))continue;
            String[] keyvalue = term.split("=");
            result.put(keyvalue[0],Double.parseDouble(keyvalue[1]));
        }
        return result;
    }

    /**
     * Metodo privado para obtener el mapa de frecuencia de terminos de un documento segun su contenido
     * @param contenido texto a tratar
     */
    private HashMap<String,Double> getTF(String contenido) {
        String contenidoAux = eliminarCaracteresNoValidos(contenido);
        HashMap<String, Double> FreqAux = new HashMap<>();
        String[] palabras = contenidoAux.split("\\s+");
        double numPalabras = palabras.length;
        for (String palabra : palabras) {
            if (palabra.equals(""))continue;
            if (!FreqAux.containsKey(palabra)) {
                FreqAux.put(palabra, (1/numPalabras));
            } else {
                FreqAux.put(palabra,(FreqAux.get(palabra)*numPalabras+1.0)/numPalabras);
            }
        }
        return FreqAux;
    }

    /**
     * Metodo privado para eliminar todos los caracteres no validos del contenido para asi poder tratarlo
     * @param s string de contenido a tratar
     * @return devuelve el string del contenido sin valores no validos, todos en minusculas para que todos los terminos sean tratados iguales
     */
    private static String eliminarCaracteresNoValidos(String s) {
        if (s == null) {
            return null;
        }
        s = s.replaceAll("\n"," ");
        return s.replaceAll("[^A-Za-z0-9À-Úà-úñ' ]", "").toLowerCase();
    }
}
