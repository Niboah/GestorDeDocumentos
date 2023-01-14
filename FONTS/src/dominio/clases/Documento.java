package dominio.clases;

public class Documento {
    private String autor;
    private String titulo;
    private String contenido;

    /**
     * Constructor de Objeto documento vació
     */
    public Documento() {
    }

    /**
     * Contructor de objeto documento con parametros
     * @param titulo titulo del documento
     * @param autor autor del documento
     * @param contenido contenido del documento
     */
    public Documento(String titulo, String autor, String contenido) {
        this.titulo = titulo;
        this.autor = autor;
        this.contenido = contenido;
    }

    /**
     * Metodo para devolver autor del documento
     * @return Devuelve el autor del documento
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Metodo para devolver titulo del documento
     * @return Devuelve el titulo del documento
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Metodo para devolver contenido del documento
     * @return Devuelve el contenido del documento
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Dado un nuevo contenido sustituye el contenido y recalcula el mapa de frequencia del documento
     * @param nContenido nuevo contenido que sustituira al contenido antiguo
     */
    public void modificarContenidoDocumento(String nContenido) {
        this.contenido = nContenido;
    }

    @Override
    public String toString() {
        return titulo + " \0" +autor;
    }
}