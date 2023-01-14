package presentacion.clases;

import java.awt.event.*;

/**
 * @author ruben.dabrio
 */

/**
 * Interfaz hija de ActionListener, WindowListener y MouseListener que gestiona los eventos de la vista Main
 */

public interface VPrincipalEventos extends ActionListener, WindowListener, MouseListener {

    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    public void actionPerformed(ActionEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void mouseClicked(MouseEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void mousePressed(MouseEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void mouseReleased(MouseEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void mouseEntered(MouseEvent e);


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    public void mouseExited(MouseEvent e);


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
