package presentacion.clases;

import dominio.clases.Excepcion;
import java.awt.event.ActionEvent;

/**
 * @author ruben.dabrio
 */

/**
 * Implementa la interfaz gestionadora de los eventos de la vista Peso Palabra
 */

public class AccionEventoVPesoPalabra implements VPesoPalabraEventos {

    /**
     * vista Peso Palabra
     */
    private VPesoPalabra vPesoPalabra;


    /**
     * Constructora por defecto
     * @param vPesoPalabra
     */
    public AccionEventoVPesoPalabra(VPesoPalabra vPesoPalabra) {
        this.vPesoPalabra = vPesoPalabra;
    }


    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "confirmarNuevoPeso":
                try {
                    vPesoPalabra.accionPesoPalabraBoton();
                } catch (Excepcion ex) { throw new RuntimeException(ex); }
                break;

            default: break;
        }
    }
}
