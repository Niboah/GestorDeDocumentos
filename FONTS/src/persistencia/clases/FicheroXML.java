package persistencia.clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Es una clase que implementa la interfaz Fichero, representa un fichero XML (Extensible Markup Language) con extension .xml. Se usa el patron singleton ya que no se necesita mas de una instancia de esta clase.
 */

public class FicheroXML implements Fichero {
    /**
     * Instancia de la clase para aplicar el patron sigleton
     */
    private static FicheroXML singletoFicheroXML;
    /**
     * Constructor por defecto
     */
    private FicheroXML() {}
    /**
     * Retorna la instancia de la clase
     * @return Retorna la instancia de la clase
     */
    public static FicheroXML getInstance(){
        if(singletoFicheroXML==null) singletoFicheroXML = new FicheroXML();
        return singletoFicheroXML;
    }
    /**
     * Lee el fichero que estan en el path especificado
     * @param path direccion donde esta el fichero
     * @return Contenido del fichero
     */
    @Override
    public String read(String path) throws Exception {
        String result="";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            org.w3c.dom.Document doc = db.parse(new File(path));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("documento");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    String titulo = element.getElementsByTagName("titulo").item(0).getTextContent();
                    String autor = element.getElementsByTagName("autor").item(0).getTextContent();
                    String contenido = element.getElementsByTagName("contenido").item(0).getTextContent();

                    result = titulo+"\n"+autor+"\n"+contenido;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new Exception(e.getMessage());
        }
        return  result;
    }
    /**
     * Escribe el titulo, autor, contenido en el fichero especificado en el path
     * @param path direccion donde esta el fichero
     * @param titulo titulo del documento
     * @param autor autor del documento
     * @param contenido contenido del documeto
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     * @throws TransformerException
     */
    @Override
    public void write(String path, String titulo, String autor, String contenido) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        org.w3c.dom.Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("documento");

        doc.appendChild(rootElement);

        Element tit = doc.createElement("titulo");
        tit.setTextContent(titulo);
        Element aut = doc.createElement("autor");
        aut.setTextContent(autor);
        Element cont = doc.createElement("contenido");
        cont.setTextContent(contenido);

        rootElement.appendChild(tit);
        rootElement.appendChild(aut);
        rootElement.appendChild(cont);
        writeXml(doc, new FileOutputStream(path));
    }

    /**
     * Escribe el documento een el fichero especificado en el path
     * @param doc documento a escribir
     * @param output direccion donde esta el fichero
     * @throws TransformerException
     * @throws IOException
     */
    private static void writeXml(org.w3c.dom.Document doc, OutputStream output) throws TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
        output.close();
    }

}
