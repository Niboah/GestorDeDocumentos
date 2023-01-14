package presentacion.clases;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;

/**
 * @author ruben.dabrio
 */

/**
 * Implementa la interfaz gestionadora de los eventos de la vista Expresion Booleana
 */

public class AccionEventoVExpreBool implements VExpreBoolEventos  {

    /**
     * vista Expresion Booleana
     */
    private VExpresionBooleana vExpresionBooleana;


    /**
     * Constructora por defecto
     * @param vExpresionBooleana vista Expresion Booleana
     */
    public AccionEventoVExpreBool(VExpresionBooleana vExpresionBooleana) {
        this.vExpresionBooleana = vExpresionBooleana;
    }


    /**
     * Comprueba cual es el componente del cual viene el evento y lo redirige a la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "altaExprBool":
                try {
                    vExpresionBooleana.accionGuardarExprBool();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, ex.getMessage()); }
                break;

            case "clearExprBoolBoton":
                vExpresionBooleana.accionClearExprBoolBoton();
                break;

            case "eliminaExprBoolBoton":
                vExpresionBooleana.accionEliminaExprBoolBoton();
                break;

        }
    }


    /**
     * Redirige el evento a una accion de la vista
     * @param e el evento que va a ser procesado
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        vExpresionBooleana.setExpreTextField();
    }
}
