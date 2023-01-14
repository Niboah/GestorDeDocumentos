package presentacion.controlador;

import dominio.clases.Excepcion;
import dominio.controlador.CtrlDominio;
import presentacion.clases.*;

import javax.swing.*;
import java.util.ArrayList;

public class CtrlPresentacion {
    private CtrlDominio dominio;
    private VMain principal;
    private VExpresionBooleana vExpresionBooleana;
    private VAutores vAutores;

    private VDocumento vDocumento;

    private VPesoPalabra vPesoPalabra;

    private ArrayList<VDocumento> activeVDoc;

    public CtrlPresentacion() {
        dominio = new CtrlDominio();
        principal = new VMain(this);
        vExpresionBooleana = new VExpresionBooleana(this);
        vAutores = new VAutores(this);
        vDocumento = new VDocumento(this);
        vPesoPalabra = new VPesoPalabra(this);
        activeVDoc=new ArrayList<>();
    }

    public void inicializarCtrlPresentacion() throws Exception {
        dominio.inicializarCtrlDominio();
        principal.inicializar();
        vDocumento.inicializar();
        vAutores.inicializar();
        vPesoPalabra.inicializar();
        vExpresionBooleana.inicializar();
        principal.hacerVisible(true);
        principal.accionMostrarTodosDocumentos();
        vExpresionBooleana.mostrarExpBool();
        vAutores.mostrarAutores("");
    }


    public AccionEventoVPrincipal getAccionEventoVPrincipal() { return new AccionEventoVPrincipal(principal); }

    public AccionEventoVDocumento getAccionEventoVDocumento() { return new AccionEventoVDocumento(vDocumento); }

    public AccionEventoVAutores getAccionEventoVAutores() { return new AccionEventoVAutores(vAutores); }

    public AccionEventoVExpreBool getAccionEventoVExprBool() { return new AccionEventoVExpreBool(vExpresionBooleana); }

    public AccionEventoVPesoPalabra getAccionEventoVPesoPalabra() { return new AccionEventoVPesoPalabra(vPesoPalabra); }

    public void hacerVisibleVistaPrincipal(boolean b) { principal.hacerVisible(b); }

    public void hacerVisibleVistaEB(boolean b) {
        vExpresionBooleana.hacerVisible(b);
    }

    public void hacerVisibleVistaAutores(boolean b) { vAutores.hacerVisible(b); }

    public void hacerVisibleVistaDocumento(boolean b, String titulo, String autor, String contenido) {
        if (contenido != null) vDocumento.hacerVisible(b, titulo, autor, contenido);
        else {
            String conten = dominio.getContenido(titulo, autor);
            vDocumento.hacerVisible(b, titulo, autor, conten);
        }
    }

    public void guardarDocumento(String path, String titulo, String autor) throws Exception {
        dominio.guardar(path, titulo, autor);
    }

    public void hacerVisibleVistaPesoPalabra(boolean b, String titulo, String autor) throws Excepcion {
        vPesoPalabra.hacerVisible(b, titulo, autor);
    }

    public ArrayList<String> listaDocumentos() { return dominio.listaDocumentos(); }

    public ArrayList<String> listaExpresionesBooleanas(int ord) { return dominio.listaExprBool(ord); }

    public ArrayList<String> listaAutores(String pref) {
        return dominio.getListaAutoresPrefijo(pref, 1);
    }

    public ArrayList<String> listaPesoPalabra(String titulo, String autor) throws Excepcion {
        return dominio.listaTermFrequency(titulo, autor);
    }

    public ArrayList<String> mostrarKDocsParecidos(String titulo, String autor, int k) {
        return dominio.buscarDocumentosParecidos(titulo, autor, k);
    }

    public ArrayList<String> buscarDocumentosRelevantes(int k, String query) {
        return dominio.buscarDocumentosRelevantes(query, k);
    }

    public ArrayList<String> mostrarDocExpBool(String expr) throws Exception {
        return dominio.buscarDocumentoExprBool(expr);
    }

    public ArrayList<String> mostrarDocAutor(String autor) {
        return dominio.listaDocumentosAutor(autor, 1);
    }

    public void modificarDocumento(String tituloO, String autorO, String titulo, String autor, String contenido) throws Excepcion {
        dominio.modificarDocumento(tituloO, autorO, titulo, autor, contenido);
    }

    public String getContenido(String titulo, String autor) { return dominio.getContenido(titulo, autor); }

    public void saveAll() { dominio.saveAll(); }

    public String cargarDocumento(String path) throws Exception { return dominio.cargar(path); }

    public void eliminarDocumento(String titulo, String autor) throws Excepcion { dominio.bajaDocumento(titulo, autor); }

    public void mostrarDocumentos() { principal.accionFiltrarBoton(); }

    public void altaDocumento(String titulo, String autor, String contenido) throws Excepcion {
        dominio.altaDocumento(titulo, autor, contenido);
    }

    public void eliminarExprBool(String expr) { dominio.bajaExpresionBooleana(expr); }

    public void altaExprBool(String expr) throws Exception {
        dominio.altaExpresionBooleana(expr);
    }

    public void modificarExpresionBool(String oldExpr, String newExpr) throws Exception {
        dominio.modificarExprBool(oldExpr, newExpr);
    }

    public void modificarPesoPalabra(String titulo, String autor, String palabra, double peso) {
        dominio.modificarPesoPalabra(titulo, autor, palabra, peso);
    }

    public void comprobarExistenciaDocumento(String titulo, String autor) throws Excepcion {
        dominio.existeDocumento(titulo, autor);
    }

    public void actualizarcomboBoxExpreBool(){
        this.principal.addListaExpresionBooleana();
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater (
                () -> {
                    CtrlPresentacion presentacion = new CtrlPresentacion();
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,e.toString());
                    }
                    try {
                        presentacion.inicializarCtrlPresentacion();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,e.toString());
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }
                }
        );

    }

}
