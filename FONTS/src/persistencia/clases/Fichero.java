package persistencia.clases;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Controlador de la parte de Persistencia que se encarga de enlazar los demás controladores que constituyen la arquitectura, con las funciones de la persistencia.
 */
public interface Fichero {
    /**
     *Lee el fichero que estan en el path especificado
     * @param path direccion donde esta el fichero
     * @return Contenido del fichero
     * @throws FileNotFoundException
     */
    public String read(String path) throws Exception;
    /**
     * Escribe el titulo, autor, contenido en el fichero especificado en el path
     * @param path direccion donde esta el fichero
     * @param titulo titulo del documento
     * @param autor autor del documento
     * @param contenido contenido del documeto
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public void write(String path, String titulo, String autor, String contenido) throws IOException, ParserConfigurationException, TransformerException;
}
