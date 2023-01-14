package persistencia.clases;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

/**
 * Es una clase que implementa la interfaz Fichero, representa un fichero propietario con extension .prop. Se usa el patron singleton ya que no se necesita mas de una instancia de esta clase.
 */

public class FicheroPROP implements Fichero {

    /**
     * Variable estatico para guarada la direccion del directorio por defecto de las ficheros propietarios
     */
    private final String DATADIR = "DATA"+ File.separator+".prop"+File.separator;

    /**
     * Signo para separa documentos en un fichero propietario
     */
    private final char PROPSEPARATOR = (char)27;

    /**
     * Instancia de la clase para aplicar el patron sigleton
     */
    private static FicheroPROP singletonFicheroPROP;

    /**
     * Constructor por defecto
     */
    private FicheroPROP() {}

    /**
     * Retorna la instancia de la clase
      * @return Retorna la instancia de la clase
     */
    public static FicheroPROP getInstance(){
       if(singletonFicheroPROP ==null) singletonFicheroPROP = new FicheroPROP();
       return singletonFicheroPROP;
    }

    /**
     * Lee el fichero que estan en el path especificado
     * @param path direccion donde esta el fichero
     * @return Contenido del fichero
     */
    @Override
    public String read(String path) {
        String result="";
        try {
            FileReader file = new FileReader(path);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                result += myReader.nextLine()+"\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Escribe el titulo, autor, contenido en el fichero especificado en el path
     * @param path direccion donde esta el fichero
     * @param titulo titulo del documento
     * @param autor autor del documento
     * @param contenido contenido del documeto
     * @throws IOException error en la escritura del fichero
     */
    @Override
    public void write(String path, String titulo, String autor, String contenido) throws IOException {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(titulo+"\n");
            myWriter.write(autor+"\n");
            myWriter.write(contenido);
            myWriter.close();
    }

    /**
     * Lee el fichero propietario especificado, del directorio por defecto
     * @param name nombre del fichero especificado
     * @return contenido del fichero en array de String
     */
    public String [] readList(String name){
        String path = System.getProperty("user.dir")+ File.separator+this.DATADIR +name+".prop";
        String result = read(path);
        String[] listResult= result.split(PROPSEPARATOR+"");
        return listResult;
    }

    /**
     * Escribe el array de String en fichero especificado, en el directorio por defecto
     * @param name nombre del fichero especificado
     * @param list array de string para escribir
     * @throws IOException error en la escritura del fichero
     */
    public void writeList(String name, String[] list) throws IOException {
        String path = System.getProperty("user.dir")+ File.separator+this.DATADIR +name+".prop";
        FileWriter myWriter = new FileWriter(path);
        if(list == null || list.length==0) myWriter.write("");
        else {
            for (int i=0; i<list.length-1;i++) {
                myWriter.write(list[i]+PROPSEPARATOR);
            }
            myWriter.write(list[list.length-1]);
        }
        myWriter.close();
    }
}
