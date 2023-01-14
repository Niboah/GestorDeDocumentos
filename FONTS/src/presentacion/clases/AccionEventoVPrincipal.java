package presentacion.clases;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

/**
 * @author ruben.dabrio
 */

/**
 * Implementa la interfaz gestionadora de los eventos de la vista Principal
 */

public class AccionEventoVPrincipal implements VPrincipalEventos {


    /**
     * vista Principal
     */
    private VMain principal;


    /**
     * Constructora por defecto
     * @param principal vista Principal
     */
    public AccionEventoVPrincipal(VMain principal) {
        this.principal = principal;
    }


    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "edicionCB":
                principal.accionArchivoCB();
                break;

            case "eliminarDocumentoB":
                principal.accionEliminarDocumentoBoton();
                break;

            case "altaDocumentoBoton":
                principal.accionAltaDocumentoBoton();
                break;

            case "abrirDocumentoB":
                principal.accionAbrirDocumentoBoton();
                break;

            case "cargarDocumentoButton":
                principal.accionCargarDocumentoBoton();
                break;

            case "filtrarBoton":
                principal.accionFiltrarBoton();
                break;

            case "mostrarDocumentosBoton":
                principal.accionMostrarTodosDocumentos();
                break;

            case "filtrador":
                try {
                    principal.accionFiltrador();
                } catch (Exception ex) { throw new RuntimeException(ex); }
                break;

            case "salir":
                principal.accionSalir();
                break;

            default: break;
        }
    }


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void mouseClicked(MouseEvent e) { if (e.getClickCount() == 2)  principal.accionAbrirDocumentoBoton(); }


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void mousePressed(MouseEvent e) {}


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void mouseReleased(MouseEvent e) {}


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void mouseEntered(MouseEvent e) {}


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void mouseExited(MouseEvent e) {}


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
    public void windowClosing(WindowEvent e) { principal.saveAll(); }


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
