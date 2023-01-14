package dominio.clases;

/**
 * @author ruben.dabrio
 */

/**
 * Representa una excepcion personalizada
 */

public class Excepcion extends Exception{
    public Excepcion(String msg) { super(msg); }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
