package presentacion.clases;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ruben.dabrio
 */

/**
 * Interfaz hija de ActionListener que gestiona los eventos de la vista Autores
 */

public interface VAutoresEventos extends ActionListener {

    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    public void actionPerformed(ActionEvent e);
}
