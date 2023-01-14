package presentacion.clases;

import java.awt.event.ActionEvent;

/**
 * @author ruben.dabrio
 */

/**
 * Implementa la interfaz gestionadora de los eventos de la vista Autores
 */

public class AccionEventoVAutores implements VAutoresEventos {

    /**
     * vista Autores
     */
    private VAutores vAutores;


    /**
     * Constructora por defecto
     * @param vAutores vista Autores
     */
    public AccionEventoVAutores(VAutores vAutores) {
        this.vAutores = vAutores;
    }


    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "prefijoAutor":
                vAutores.accionPrefijoAutor();
                break;

            case "listarTodosAutores":
                vAutores.accionListarTodos();
                break;

            case "filtrar":
                vAutores.accionPrefijoAutor();
                break;

            default: break;
        }
    }
}
