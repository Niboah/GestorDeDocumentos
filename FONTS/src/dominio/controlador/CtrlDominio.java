package dominio.controlador;

import dominio.clases.*;
import persistencia.controlador.CtrlPersistencia;

import java.util.*;

public class CtrlDominio {

    private CtrlPersistencia persistencia;
    private ListaDocumentos lDocumentos;

    public CtrlDominio() {
        this.persistencia = new CtrlPersistencia();
        this.lDocumentos = new ListaDocumentos();
    }
    public void inicializarCtrlDominio() throws Exception {
        restoreListaExpresionBooleana();
        restoreListaDocumentos();
    }

    public void guardar(String path, String titulo, String autor) throws Exception {
        Documento documento = lDocumentos.getDocumento(titulo,autor);
        persistencia.write(path,documento.getTitulo(),documento.getAutor(),documento.getContenido());
    }

    public String cargar(String path) throws Exception {
        String documento = persistencia.read(path);
        lDocumentos.altaDocumento(documento);
        return documento;
    }


    public void saveAll(){
        persistencia.writeListaExpresionBooleana(lDocumentos.getlExpreBool().getAll(0).toArray(String[] ::new));

        ArrayList<String> lDocs = lDocumentos.getListaDocumentoSeparado();
        if (lDocs != null)persistencia.writeListaDocumentos(lDocs.toArray(String[] ::new));
        else persistencia.writeListaDocumentos(null);

    }

    private void restoreListaExpresionBooleana() throws Exception {
        for(String expr: persistencia.restoreListaExpresionBooleana()){
            if(expr=="")continue;
            ExpresionBooleana e = new ExpresionBooleana(expr);
            if(!e.makeTree()) System.out.println("No add expresion:" +expr);
            lDocumentos.getlExpreBool().add(e);
        }
    }

    private void restoreListaDocumentos() throws Excepcion {
        for(String documento: persistencia.restoreListaDocumentos()){
            lDocumentos.restoreDocumento(documento);
        }
    }

    public boolean altaDocumento(String titulo, String autor, String contenido) throws Excepcion {
        return lDocumentos.altaDocumento(titulo, autor, contenido);
    }

    public boolean bajaDocumento(String titulo, String autor) throws Excepcion {
        return lDocumentos.bajaDocumento(titulo, autor);
    }

    public boolean modificarDocumento(String tituloID, String autorID, String titulo, String autor, String contenido) throws Excepcion {
        return lDocumentos.modificarDocumento(tituloID, autorID, titulo, autor, contenido);
    }

    public String consultarContenidoDocumento(String titulo, String autor) {
        return lDocumentos.getDocumento(titulo, autor).getContenido();
    }

    public HashMap<String,Double> consultaPesoPalabra(String titulo, String autor){
        return lDocumentos.consultaPesoPalabra(titulo, autor);
    }

    public boolean modificarPesoPalabra(String titulo, String autor, String palabra, double peso) {
        return lDocumentos.modificarPesoPalabra(titulo, autor, palabra, peso);
    }

    public boolean altaExpresionBooleana(String expr) throws Exception {
        ExpresionBooleana e = new ExpresionBooleana(expr);
        if(!e.makeTree()) return false;
        return lDocumentos.getlExpreBool().add(e);
    }

    public boolean bajaExpresionBooleana(String expr) {
        return lDocumentos.getlExpreBool().remove(expr);
    }

    public ArrayList<String> listaExprBool(int ord) {
        return lDocumentos.getlExpreBool().getAll(ord);
    }

    public boolean modificarExprBool(String oldExpr, String newExpr) throws Exception {
        return lDocumentos.getlExpreBool().replace(oldExpr, newExpr);
    }

    public ArrayList<String> buscarDocumentoExprBool(String expr) throws Exception {
        return lDocumentos.buscarDocumentoExprBool(expr);
    }
    /*
    /////////////////////////////////////////VERSION ANTIGUA//////////////////////////////////////////
    public ArrayList<String> buscarDocumentoExprBool(String expr) throws Exception {
       ExpresionBooleana exprBool = lDocumentos.getlExpreBool().get(expr);
       if (exprBool == null){
           exprBool = new ExpresionBooleana(expr);
           if(!exprBool.makeTree()) return null;
           lDocumentos.getlExpreBool().add(exprBool);
       }
       return exprBool.exprBooleanSearch(lDocumentos.getListaDocumentoConTerminos(exprBool.getTerms()));
   }*/

    public ArrayList<String> listaDocumentosAutor(String autor, int code) {
        return lDocumentos.listaDocumentosAutor(autor, code);
    }

    public ArrayList<String> getListaAutoresPrefijo(String pref, int code) {
        return lDocumentos.getListaAutoresPrefijo(pref, code);
    }

    public String getContenido(String titulo, String autor) { return lDocumentos.getDocumento(titulo, autor).getContenido(); }

    public ArrayList<String> buscarDocumentosParecidos(String titulo, String autor, int k) {
        return lDocumentos.buscarDocumentosParecidos(titulo, autor, k);
    }

    public ArrayList<String> buscarDocumentosRelevantes(String query, int k) {
        return lDocumentos.buscarDocumentosRelevantes(query, k);
    }
    public ArrayList<String> listaDocumentos() {
        ArrayList<String> result=new ArrayList<>();
        ArrayList<Documento> lDocs = lDocumentos.getListaDocumento();
        if (lDocs != null) {
            for (Documento d :lDocs) {
                result.add(d.toString());
            }
        }
        if(result.size()==0)return null;
        return lDocumentos.ordenarLista(result, 1);
    }

    public ArrayList<String> listaTermFrequency(String titulo, String autor) throws Excepcion {
        return lDocumentos.listaTermFrequency(titulo, autor);
    }

    public boolean existeDocumento(String titulo, String autor) throws Excepcion {
        return lDocumentos.existeDocumento(titulo, autor);
    }

}
