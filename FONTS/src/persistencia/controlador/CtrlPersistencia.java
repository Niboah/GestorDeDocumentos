package persistencia.controlador;

import persistencia.clases.Fichero;
import persistencia.clases.FicheroXML;
import persistencia.clases.FicheroPROP;
import persistencia.clases.FicheroTxT;

import java.io.FileNotFoundException;
import java.io.IOException;


public class CtrlPersistencia {

    public String read(String path) throws Exception {
        String extension="";
        int index = path.lastIndexOf('.');
        if(index > 0)  extension = path.substring(index + 1);
        Fichero fichero;

        switch (extension){
            case "txt":
                fichero = FicheroTxT.getInstance();
                return fichero.read(path);
            case "xml":
                fichero = FicheroXML.getInstance();
                return fichero.read(path);
            case "prop":
                fichero = FicheroPROP.getInstance();
                return fichero.read(path);
            default:
                throw new Exception("Extension incorrecta");
        }

    }

    public void write(String path,String titulo,String autor, String contenido) throws Exception {
        String extension="";
        int index = path.lastIndexOf('.');
        if(index > 0)  extension = path.substring(index + 1);
        Fichero fichero;
        switch (extension) {
            case "txt":
                fichero = FicheroTxT.getInstance();
                fichero.write(path, titulo, autor, contenido);
                break;
            case "xml":
                fichero = FicheroXML.getInstance();
                fichero.write(path, titulo, autor, contenido);
                break;
            case "prop":
                fichero = FicheroPROP.getInstance();
                fichero.write(path, titulo, autor, contenido);
                break;
            default:
                throw new Exception("Extension incorrecta");
        }
    }

    public String[] restoreListaExpresionBooleana() {
        FicheroPROP fichero = FicheroPROP.getInstance();
        String[] result = fichero.readList("expresionBooleana");
        return result;
    }

    public String[] restoreListaDocumentos(){
        FicheroPROP fichero = FicheroPROP.getInstance();
        String [] result = fichero.readList("documentos");
        return result;
    }

    public void writeListaExpresionBooleana(String[] list){
        FicheroPROP fichero = FicheroPROP.getInstance();
        try {
            fichero.writeList("expresionBooleana",list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeListaDocumentos(String[] list){
        FicheroPROP fichero = FicheroPROP.getInstance();
        try {
            fichero.writeList("documentos",list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
