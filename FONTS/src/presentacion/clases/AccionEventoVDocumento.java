package presentacion.clases;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * @author ruben.dabrio
 */

/**
 * Implementa la interfaz gestionadora de los eventos de la vista Documento
 */

public class AccionEventoVDocumento implements VDocumentoEventos {

    /**
     * vista Documento
     */
    private VDocumento vDocumento;


    /**
     * Constructora por defecto
     * @param vDocumento vista Documento
     */
    public AccionEventoVDocumento(VDocumento vDocumento) {
        this.vDocumento = vDocumento;
    }


    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "guardarBoton":
                vDocumento.accionGuardarBoton();
                break;

            case "guardarComo":
                vDocumento.accionGuardarcomo();
                break;

            case "vistaPalabra":
                vDocumento.hacerVisibleVistaPesoPalabra();
                break;

            default: break;
        }
    }


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void windowOpened(WindowEvent e) {}


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void windowClosing(WindowEvent e) { vDocumento.accionSalir(); }


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void windowClosed(WindowEvent e) {}


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void windowIconified(WindowEvent e) {}


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void windowDeiconified(WindowEvent e) {}


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void windowActivated(WindowEvent e) {}


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
