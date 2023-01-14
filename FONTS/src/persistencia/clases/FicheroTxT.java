package persistencia.clases;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Es una clase que implementa la interfaz Fichero,representa un fichero de texto plano con extension .txt. Se usa el patron singleton ya que no se necesita mas de una instancia de esta clase.
 */

public class FicheroTxT implements Fichero{

    /**
     * Instancia de la clase para aplicar el patron sigleton
     */
    private static FicheroTxT singletoFicheroTxT;
    /**
     * Constructor por defecto
     */
    private FicheroTxT() {}
    /**
     * Retorna la instancia de la clase
     * @return Retorna la instancia de la clase
     */
    public static FicheroTxT getInstance(){
        if(singletoFicheroTxT==null) singletoFicheroTxT = new FicheroTxT();
        return singletoFicheroTxT;
    }
    /**
     * Lee el fichero que estan en el path especificado
     * @param path direccion donde esta el fichero
     * @return Contenido del fichero
     */
    @Override
    public String read(String path) throws FileNotFoundException {
        String result="";
        FileReader file = new FileReader(path);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            result += myReader.nextLine()+"\n";
        }
        myReader.close();
        return result;
    }

    @Override
    public void write(String path, String titulo, String autor, String contenido) {
        try{
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(titulo+"\n");
            myWriter.write(autor+"\n");
            myWriter.write(contenido);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
