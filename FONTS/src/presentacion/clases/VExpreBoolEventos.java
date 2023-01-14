package presentacion.clases;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ruben.dabrio
 */

/**
 * Interfaz hija de ActionListener y de ListSelectionListener que gestiona los eventos de la vista Expresion Booleana
 */

public interface VExpreBoolEventos extends ActionListener, ListSelectionListener {

    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    public void actionPerformed(ActionEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void valueChanged(ListSelectionEvent e);

}
