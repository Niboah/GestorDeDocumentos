package presentacion.clases;

import java.awt.event.*;

/**
 * @author ruben.dabrio
 */

/**
 * Interfaz hija de ActionListener y de WindowListener que gestiona los eventos de la vista Documento
 */

public interface VDocumentoEventos extends ActionListener, WindowListener {

    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    public void actionPerformed(ActionEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void windowOpened(WindowEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void windowClosing(WindowEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void windowClosed(WindowEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void windowIconified(WindowEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void windowDeiconified(WindowEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void windowActivated(WindowEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void windowDeactivated(WindowEvent e);
}
