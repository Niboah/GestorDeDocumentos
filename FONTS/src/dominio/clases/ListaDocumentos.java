package dominio.clases;

import java.util.*;
import java.util.stream.IntStream;

/**
  @author sergi.campuzano
 */

/**
 * Estructura para guardar un conjunto de {@link Documento}, ordenado por insercion.
 */
public class ListaDocumentos {

    private class Doc {
        Documento documento;
        Doc_tf doc_tf;
    }
    /**
     * Conjunto documentos, ordenado por orden de insercion
     * Key: Autor
     * Value: HashMap<titulo, Classe Document>
     */
    private HashMap<String, HashMap<String, Doc>> documentos;

    /**
     * Contador de todos los documentos de todos los autores
     */
    private int numDocumentos;

    /**
     * Conjunto palabras con el numero de documentos que contiene la palabra
     * Key: Palabra
     * Value: numero total documentos que contienen la palabra
     */
    private HashMap<String, Double> numDocHaveTerm;

    private ListaAutor lAutor;
    private ListaExpresionBooleana lExpreBool;


    /**
     * Constructor por defecto
     */
    public ListaDocumentos(){
        this.documentos = new HashMap<>();
        this.numDocHaveTerm = new HashMap<>();
        this.numDocumentos = 0;
        this.lAutor = new ListaAutor();
        this.lExpreBool = ListaExpresionBooleana.getInstance();
    }

    public ListaExpresionBooleana getlExpreBool(){
        return this.lExpreBool;
    }


    public ArrayList<String> getListaDocumentoSeparado(){
        ArrayList<String> listaDoc = new ArrayList<>();
        ArrayList<Doc> documentos = getListaDocs();
        if (documentos != null) {
            for (Doc doc : documentos) {
                String documento = doc.documento.getTitulo() + "\n" + doc.documento.getAutor() +"\n"+
                        doc.doc_tf.getDocTF().toString() +"\n" + doc.documento.getContenido();
                listaDoc.add(documento);
            }
            return listaDoc;
        }
        return null;
    }

    public boolean altaDocumento(String documento) throws Excepcion {
        String [] documentoParts=documento.split("\n");
        if(documentoParts.length<2) throw new Excepcion("Estructura documento incorrecto o titulo autor vacio");
        String titulo = documentoParts[0];
        String autor = documentoParts[1];
        if(titulo.equals("") || autor.equals(""))throw new Excepcion("titulo o autor vacio");
        int contenidoStartIndex = titulo.length()+autor.length()+2;
        String contenido = documento.substring(contenidoStartIndex);
        return altaDocumento(titulo,autor,contenido);
    }

    public boolean restoreDocumento(String documento) throws Excepcion {
        String [] documentoParts=documento.split("\n");
        if(documentoParts.length<3)return false;
        String titulo = documentoParts[0];
        String autor = documentoParts[1];
        String termFrequecy = documentoParts[2];
        int contenidoStartIndex = titulo.length()+autor.length()+termFrequecy.length()+3;
        String contenido = documento.substring(contenidoStartIndex);
        return altaDocumento(titulo,autor,termFrequecy,contenido);
    }

    private boolean altaDocumento(String titulo, String autor,String termFrequency, String cont) throws Excepcion {
        if (this.documentos.containsKey(autor) && this.documentos.get(autor).containsKey(titulo)) return false;
        if (!this.documentos.containsKey(autor)) {
            this.documentos.put(autor, new HashMap<>());
        }
        Doc doc = new Doc();
        doc.documento = new Documento(titulo, autor,cont);
        doc.doc_tf  = new Doc_tf(termFrequency);
        this.documentos.get(autor).put(titulo, doc);
        ++numDocumentos;
        incrementarNumDocHaveTerm(doc);
        if (!lAutor.existeAutor(autor)) lAutor.altaAutor(autor);
        return true;
    }

    /**
     * Añade un documento al conjunto de documentos
     * @param titulo titulo del documento a añadir
     * @param autor autor del documento a añadir
     * @param cont contenido del documento a añadir
     * @return Devuelve true si la operacion se ha efectuado correctamente y falso si ya existe el documento
     */
    public boolean altaDocumento(String titulo, String autor, String cont) throws Excepcion {
        if (this.documentos.containsKey(autor) && this.documentos.get(autor).containsKey(titulo))
            throw new Excepcion("Ya existe un documento con el mismo titulo y autor");
        if (!this.documentos.containsKey(autor)) {
            this.documentos.put(autor, new HashMap<>());
        }
        Doc doc = new Doc();
        doc.documento = new Documento(titulo, autor,cont);
        doc.doc_tf  = new Doc_tf(doc.documento);
        this.documentos.get(autor).put(titulo, doc);
        ++numDocumentos;
        incrementarNumDocHaveTerm(doc);
        if (!lAutor.existeAutor(autor)) lAutor.altaAutor(autor);
        return true;
    }

    /**
     * Elimina el documento del conjunto de documentos
     * @param titulo titulo del documento
     * @param autor autor del documento
     * @return Devuelve false si falta modificar el autor, sino retorna true
     */
    public boolean bajaDocumento(String titulo, String autor) throws Excepcion {
        if (!this.documentos.containsKey(autor) || !this.documentos.get(autor).containsKey(titulo)) {
            throw new Excepcion("No existe este titulo para el autor especificado");
        }
        decrementarNumDocHaveTerm(this.documentos.get(autor).get(titulo));

        this.documentos.get(autor).remove(titulo);
        --numDocumentos;
        if (this.documentos.get(autor).size() == 0) {
            this.documentos.remove(autor);
            lAutor.eliminaAutor(autor);
        }
        return true;
    }


    /**
     * Decrementa las palabras del contenido del documento registradas en numDocHaveTerm
     * @param d documento
     */
    private void decrementarNumDocHaveTerm(Doc d) {
        HashMap<String, Double> terminosDoc = d.doc_tf.getDocTF();
        for (String paraula : terminosDoc.keySet()) {
            double valor = this.numDocHaveTerm.get(paraula) - 1;
            if (valor == 0) this.numDocHaveTerm.remove(paraula);
            else this.numDocHaveTerm.put(paraula, valor);
        }
    }

    /**
     * Incrementa las palabras del contenido de documento en numDocHaveTerm
     * @param d documento
     */
    private void incrementarNumDocHaveTerm(Doc d) {
        HashMap<String, Double> terminosDoc = d.doc_tf.getDocTF();
        for (String paraula : terminosDoc.keySet()) {
            this.numDocHaveTerm.putIfAbsent(paraula, 0.0);
            this.numDocHaveTerm.put(paraula, this.numDocHaveTerm.get(paraula) + 1);
        }
    }

    /**
     * Modifica el documento identificado con tituloID y autorID
     * @param tituloID titulo del documento a modificar
     * @param autorID autor del documento a modificar
     * @param titulo nuevo titulo (puede ser null)
     * @param autor nuevo autor (puede ser null)
     * @param contenido nuevo contenido (puede ser null)
     * @return Devuelve falso si queda por modificar aun autor de lista autores, sino devuelve true
     */
    public boolean modificarDocumento(String tituloID, String autorID, String titulo, String autor, String contenido) throws Excepcion {
        if (!this.documentos.containsKey(autorID)) {
            throw new Excepcion("No existe ningun documento con el autor"+autorID);
        } else if (!this.documentos.get(autorID).containsKey(tituloID)) {
            throw new Excepcion("No existe ningun documento con el titulo"+tituloID);
        }
        modificarContenido(tituloID, autorID, contenido);

        if (!titulo.equals(tituloID) && !autor.equals(autorID)) {
            modificaTituloIAutor(tituloID, autorID, titulo, autor, contenido);
        }
        else if (!titulo.equals(tituloID)) {
            modificaSoloTitulo(tituloID, autorID, titulo, contenido);
        }
        else if(!autor.equals(autorID)) {
            modificaSoloAutor(tituloID, autorID, autor, contenido);
        }

        if (!autor.equals(autorID)) if (!lAutor.existeAutor(autor)) lAutor.altaAutor(autor);
        return true;
    }

    /**
     * Modifica el contenido del documento identificado con tituloID y autorID
     * @param tituloID titulo del documento a modificar
     * @param autorID autor del documento a modificar
     * @param contenido nuevo contenido del documento
     */
    private void modificarContenido(String tituloID, String autorID, String contenido) {
        decrementarNumDocHaveTerm(this.documentos.get(autorID).get(tituloID));
        this.documentos.get(autorID).get(tituloID).documento.modificarContenidoDocumento(contenido);
        this.documentos.get(autorID).get(tituloID).doc_tf.modificarTF(contenido);
        incrementarNumDocHaveTerm(this.documentos.get(autorID).get(tituloID));
    }


    /**
     * Modifica Titulo y Autor del documento identificado con tituloID y autorID
     * @param tituloID titulo del documento a modificar
     * @param autorID autor del documento a modificar
     * @param titulo nuevo titulo
     * @param autor nuevo autor
     * @param contenido contenido del documento
     */
    private void modificaTituloIAutor(String tituloID, String autorID, String titulo, String autor, String contenido) throws Excepcion {
        if (this.documentos.containsKey(autor) && this.documentos.get(autor).containsKey(titulo)) {
            throw new Excepcion("Ya existe un documento del futuro autor con el nombre de futuro titulo");
        }
        bajaDocumento(tituloID, autorID);
        altaDocumento(titulo, autor, contenido);
    }

    /**
     * Modifica solo Titulo del documento identificado con tituloID y autorID
     * @param tituloID titulo del documento a modificar
     * @param autorID autor del documento a modificar
     * @param titulo nuevo titulo
     * @param contenido contenido del documento
     */
    private void modificaSoloTitulo(String tituloID, String autorID, String titulo, String contenido) throws Excepcion {
        if (this.documentos.containsKey(autorID) && this.documentos.get(autorID).containsKey(titulo)) {
            throw new Excepcion("Ya existe un documento del autor con el nombre de futuro titulo");
        }
        bajaDocumento(tituloID, autorID);
        altaDocumento(titulo, autorID, contenido);
    }

    /**
     * Modifica solo Autor del documento identificado con tituloID y autorID
     * @param tituloID titulo del documento a modificar
     * @param autorID autor del documento a modificar
     * @param autor nuevo autor
     * @param contenido contenido del documento
     */
    private void modificaSoloAutor(String tituloID, String autorID, String autor, String contenido) throws Excepcion {
        if (this.documentos.containsKey(autor) && this.documentos.get(autor).containsKey(tituloID)) {
            throw new Excepcion("Ya existe un documento con este titulo hecho por el futuro autor");
        }
        bajaDocumento(tituloID, autorID);
        altaDocumento(tituloID, autor, contenido);
    }

    /**
     * Obtiene un documento
     * @param titulo titulo del documento
     * @param autor autor del documento
     * @return Si existe un documento con titulo y autor especificados, devuelve el documento. Sino devuelve null
     */
    public Documento getDocumento(String titulo, String autor) {
        if (this.documentos.get(autor).get(titulo) == null) return null;
        else return this.documentos.get(autor).get(titulo).documento;
    }

    /**
     * Ordena una lista de String segun el code
     * @param docs lista de String a ordenar
     * @param code codigo de ordenacion
     * @return Devuelve la lista ordenada segun el code. Si es 1 la ordenacion es alfabetica,
     * si es 2 la ordenacion es alfabetica inversa, y queda desordenada en cualquier otro caso.
     */
    public ArrayList<String> ordenarLista(ArrayList<String> docs, int code) {
        switch(code) {
            case 1:
                Collections.sort(docs);
                break;
            case 2:
                Comparator<String> comparador = Collections.reverseOrder();
                docs.sort(comparador);
                break;
            default:
                break;
        }
        return docs;
    }

    /**
     * Consulta la lista de los titulos de los documentos del autor, que existe
     * @param autor nombre del autor
     * @return Devuelve el conjunto de los titulos de los documentos creados por el autor mas el autor,
     * pero si el autor no tiene ningun documento creado, devuelve null
     */
    public ArrayList<String> getTitulosDocumentosAutor(String autor) {
        if (this.documentos.containsKey(autor) && this.documentos.get(autor).size() > 0) {
            ArrayList<String> titulos = new ArrayList<>();
            for (String s : documentos.get(autor).keySet()) titulos.add(s+" \0"+autor);
            return titulos;
        }
        return null;
    }

    /**
     * Consulta la lista de los documentos de los documentos de los autores
     * @param autores conjunto de nombres de los autores
     * @return Devuelve el conjunto de los titulos de los documentos creados por los autores,
     * pero si ningun autor tiene algun documento creado, devuelve null
     */
    public ArrayList<String> getTitulosDocumentosAutores(ArrayList<String> autores) {
        ArrayList<String> documentos = new ArrayList<>();
        for (String a : autores) {
            if (this.documentos.containsKey(a) && this.documentos.get(a).size() > 0) documentos.addAll(getTitulosDocumentosAutor(a));
        }
        if (documentos.size() > 0) { return documentos; }
        return null;
    }

    /**
     * Consulta la lista de los documentos de los autores que empiecen por un prefijo
     * @param prefijo prefijo deseado
     * @param code codigo de ordenacion del resultado
     * @return devuelve la lista de los documentos de los autores que empiecen por cierto prefijo. Si el prefijo
     * es vacio se devuelve la lista de los documentos de todos los autores, y si ningun autor cumple la condicion
     * se devuelve null. En los dos primeros casos se ordena la lista de cierta forma.
     */
    public ArrayList<String> listaDocumentosAutor(String prefijo, int code) {
        ArrayList<String> docs = getTitulosDocumentosAutores(lAutor.getListaAutoresPrefijo(prefijo));
        if (docs != null) return ordenarLista(docs, code);
        return null;
    }

    /**
     * Consulta la lista de los autores que empiezan por cierto prefijo
     * @param prefijo prefijo que han de contener los autores
     * @param code codigo de ordenacion
     * @return devuelve la lista de los autores que empiecen por el prefijo, si ningun autor cumplela condicion devuelve
     * null. El resultado se ordena segun el code.
     */

    public ArrayList<String> getListaAutoresPrefijo(String prefijo, int code) {
        ArrayList<String> autores = lAutor.getListaAutoresPrefijo(prefijo);
        if (autores != null) return ordenarLista(autores, code);
        return null;
    }

    /**
     * Obtiene la lista de todos los documentos del HashMap documentos
     * @return Devuelve una lista con todos los documentos, si es vacia devuelve null
     */
    public ArrayList<Documento> getListaDocumento() {
        ArrayList<Documento> listaDocumento = new ArrayList<>();
        for (String autor : this.documentos.keySet()) {
            for (String titulo : this.documentos.get(autor).keySet()) {
                listaDocumento.add(this.documentos.get(autor).get(titulo).documento);
            }
        }
        if(listaDocumento.size() == 0) return null;
        return listaDocumento;
    }

    public ArrayList<Doc> getListaDocs() {
        ArrayList<Doc> listaDoc = new ArrayList<>();
        for (String autor : this.documentos.keySet()) listaDoc.addAll(this.documentos.get(autor).values());
        if(listaDoc.size() == 0) return null;
        return listaDoc;
    }


    /**
     * Obtiene la lista de todos los documentos del HashMap documentos que contenga algun termino de la lista
     * @return Devuelve una lista con todos los documentos que contenga algun termino de la lista, si es vacia devuelve null
     */
    public ArrayList<Documento> getListaDocumentoConTerminos(ArrayList<String> terms) {
        ArrayList<Documento> listaDoc = new ArrayList<>();
        for (HashMap<String, Doc> h : this.documentos.values()){
            for(Doc d : h.values()){
                //if(d.doc_tf.documentoContiene(terms))
                listaDoc.add(d.documento);
            }
        }
        if(listaDoc.size()==0)return null;
        return listaDoc;
    }


    /**
     * Consulta los "k" documentos mas parecidos a "d"(formado por titulo y autor)
     * @param titulo del documento modelo "d"
     * @param autor del documento modelo "d"
     * @param k numero de documentos a retornar
     * @return Devuelve una lista de los "k" documentos mas parecidos al documento "d"
     */
    public ArrayList<String> buscarDocumentosParecidos(String titulo, String autor, int k) {
        if (k < 1) {
            throw new RuntimeException("ERROR: k tiene que ser mayor a 0");
        }
        if (k >= numDocumentos) {
            throw new RuntimeException("ERROR: k tiene que ser menor al numero de documentos");
        }
        Doc d = this.documentos.get(autor).get(titulo);
        HashMap<Doc,Double> MapScore = calcularPuntuacionesSimilitud(d);
        return obtenerDocumentosMasSimilares(MapScore,k);
    }

    private HashMap<Doc,Double> calcularPuntuacionesSimilitud(Doc d) {
        HashMap<Doc,Double> puntuaciones = new HashMap<>();
        ArrayList<Doc> allDocumentos = getListaDocs();
        allDocumentos.remove(d);
        for (Doc documento : allDocumentos) {
            double puntuacion = similitud(d, documento);
            puntuaciones.put(documento, puntuacion);
        }
        return puntuaciones;
    }


    /**
     * Calcula los vectores de peso y retorna el grado de similitud entre el documento modelo y otro documento
     * @param d1 documento modelo
     * @param d2 documento a comparar con el modelo
     * @return Retorna el grado de similiridad entre el documento modelo y otro documento
     */
    private double similitud(Doc d1, Doc d2) {
        HashSet<String> allTerms = new HashSet<>();
        allTerms.addAll(d1.doc_tf.getTermsDoc());
        allTerms.addAll(d2.doc_tf.getTermsDoc());
        int vector_size = allTerms.size();
        double[] vectorA = new double[vector_size];
        double[] vectorB = new double[vector_size];
        int i = 0;
        for(String s : allTerms) {
            double variableGlobal = Math.log(this.numDocumentos/this.numDocHaveTerm.get(s));
            vectorA[i] = d1.doc_tf.getTermFrequencyDoc(s) * variableGlobal;
            vectorB[i] = d2.doc_tf.getTermFrequencyDoc(s) * variableGlobal;
            i++;
        }
        return calcSimilitudCosenos(vectorA, vectorB);
    }

    /**
     * Consulta los "k" documentos mas relevantes a la query de palabras "query"
     * @param query conjunto de palabras a las que encontrar los documentos mas relevantes
     * @param k numero de documentos a retornar
     * @return Devuelve el conjunto de los "k" documentos mas relevantes para la "query"
     */
    public ArrayList<String> buscarDocumentosRelevantes(String query, Integer k) {
        if (k < 1) {
            throw new RuntimeException("ERROR: k tiene que ser mayor a 0");
        }
        if (k >= numDocumentos) {
            throw new RuntimeException("ERROR: k tiene que ser menor al numero de documentos");
        }
        Doc docQuery = new Doc();
        docQuery.documento = new Documento("query","query",query);
        docQuery.doc_tf = new Doc_tf(docQuery.documento);
        HashMap<Doc,Double> MapScore = calcularPuntuacionesSimilitudQuery(docQuery);
        return obtenerDocumentosMasSimilares(MapScore,k);
    }

    private HashMap<Doc,Double> calcularPuntuacionesSimilitudQuery(Doc query) {
        HashMap<Doc,Double> puntuaciones = new HashMap<>();
        ArrayList<Doc> listaDocumentos = getListaDocs();
        for (Doc documento : listaDocumentos) {
            double puntuacion = similitudQuery(query, documento);
            puntuaciones.put(documento, puntuacion);
        }
        return puntuaciones;
    }

    /**
     * Calcula los vectores de peso y devuelve el grado de similitud entre la query y el documento
     * @param queryD documento modelo
     * @param toCompareD documento a comparar con el modelo
     * @return Devuelve el grado de similiridad entre el documento modelo y otro documento
     */
    private double similitudQuery(Doc queryD, Doc toCompareD){
        HashSet<String> allTerms = queryD.doc_tf.getTermsDoc();
        double[] vectorA = new double[queryD.doc_tf.getTermsSizeDoc()];
        double[] vectorB = new double[queryD.doc_tf.getTermsSizeDoc()];
        int i = 0;
        for (String term : allTerms) {
            if (!this.numDocHaveTerm.containsKey(term)) {
                vectorA[i] = 0.0;
                vectorB[i] = 0.0;
            }
            else {
                double variableGlobal = Math.log(this.numDocumentos/this.numDocHaveTerm.get(term));
                vectorA[i] = queryD.doc_tf.getTermFrequencyDoc(term) * variableGlobal;
                vectorB[i] = toCompareD.doc_tf.getTermFrequencyDoc(term) * variableGlobal;
            }
            i++;
        }
        return calcSimilitudCosenos(vectorA,vectorB);
    }

    /**
     * Devuelve el calculo de la similitud de Cosenos
     * @param vectorA vector con las frecuencias de las palabras del documento modelo
     * @param vectorB vector con las frecuencias de las palabras del documento a comparar
     * @return Devuelve el grado de similiridad entre vectorA y vectorB
     */
    private double calcSimilitudCosenos(double [] vectorA, double [] vectorB) {
        double dotProduct = calcDotProduct(vectorA, vectorB);
        double magnitudVectorA = calcMagnitud(vectorA);
        double magnitudVectorB = calcMagnitud(vectorB);
        double productOfMagnitudes = magnitudVectorA * magnitudVectorB;

        return (productOfMagnitudes != 0.0) ? dotProduct / productOfMagnitudes : 0.0;
    }

    private double calcDotProduct(double[] vectorA, double[] vectorB) {
        return IntStream.range(0, vectorA.length)
                .mapToDouble(i -> vectorA[i] * vectorB[i])
                .reduce(0, Double::sum);
    }

    private double calcMagnitud(double[] vector) {
        return Arrays.stream(vector).reduce(Math::hypot).orElse(0.0);
    }

    /**
     * Comparador privado para ordenar la lista de Documentos en buscarDocumentosRelevantes y buscarDocumentosParecidos
     * Compara si la puntuación de dos Documentos es igual, en cuyo caso compara si el Autor es el mismo y si es asi compara los titulos
     */
    private final Comparator<Map.Entry<Doc, Double>> comp = (o1, o2) -> {
        int valueComparison = o2.getValue().compareTo(o1.getValue());
        if (valueComparison != 0) {
            return valueComparison;
        } else {
            int authorComparison = o1.getKey().documento.getAutor().compareToIgnoreCase(o2.getKey().documento.getAutor());
            if (authorComparison != 0) {
                return authorComparison;
            } else {
                return o1.getKey().documento.getTitulo().compareToIgnoreCase(o2.getKey().documento.getTitulo());
            }
        }
    };

    private ArrayList<String> obtenerDocumentosMasSimilares(Map<Doc, Double> puntuacionesSimilitud, int k) {
        PriorityQueue<Map.Entry<Doc, Double>> resultados = new PriorityQueue<>(comp);
        resultados.addAll(puntuacionesSimilitud.entrySet());
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < k && !resultados.isEmpty(); i++) {
            result.add(resultados.poll().getKey().documento.toString());
        }
        return result;
    }


    /**
     * Devuelve una lista con todos los terminos del documento y su peso
     * @param titulo titulo del documento a añadir
     * @param autor autor del documento a añadir
     * @return Devuelve una lista con cada termino seguido de su peso en formato string tambien
     */
    public ArrayList<String> listaTermFrequency(String titulo, String autor) throws Excepcion {
        if (!this.documentos.containsKey(autor) || !this.documentos.get(autor).containsKey(titulo)) {
            throw new Excepcion("No existe este titulo para el autor especificado");
        }
        ArrayList<String> result = new ArrayList<>();
        HashMap <String,Double> termFrequency = documentos.get(autor).get(titulo).doc_tf.getDocTF();
        for (String term : termFrequency.keySet()) {
            result.add(term+": "+ termFrequency.get(term));
        }
        return result;
    }

    public boolean existeDocumento(String titulo, String autor) throws Excepcion {
        if (!this.documentos.containsKey(autor) || !this.documentos.get(autor).containsKey(titulo)) {
            throw new Excepcion("No existe este documento");
        }
        return true;
    }

    /**
     * Busca el conjunto de documentos que tengan una frase que cumpla la expresin booleana, mantiene la lista de expresion booleana ordenada por uso.
     * @param expr expresion booleana usada para la busquda
     * @return Devuelve un conjunto de documentos que tengan una frase que cumpla la expresion booleana, null si el conjunto es vacio.
     * @throws Exception
     */
    public ArrayList<String> buscarDocumentoExprBool(String expr) throws Exception {
        ExpresionBooleana exprBool = this.lExpreBool.get(expr);
        if (exprBool == null){
            exprBool = new ExpresionBooleana(expr);
            if(!exprBool.makeTree()) throw new Exception("Expresion booleana no valida");
            this.lExpreBool.add(exprBool);
        }else{
            this.lExpreBool.remove(expr);
            this.lExpreBool.add(exprBool);
        }
        return exprBool.exprBooleanSearch(getListaDocumentoConTerminos(exprBool.getTerms()));
    }

    public HashMap<String,Double> consultaPesoPalabra (String titulo, String autor) {
        Doc d = this.documentos.get(autor).get(titulo);
        if (d != null) return d.doc_tf.getDocTF();
        else return null;
    }

    public boolean modificarPesoPalabra(String titulo, String autor, String palabra, double peso) {
        Doc d = this.documentos.get(autor).get(titulo);
        if (d == null) return false;
        else {
            d.doc_tf.modificarPesoPalabraDoc(palabra,peso);
            return true;
        }
    }
}