package presentacion.clases;

import presentacion.controlador.CtrlPresentacion;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 @author sergi.campuzano
 @author ruben.dabrio
 */

/**
 * Vista que muestra listadas todas las expresiones booleanas y que te permite hacer altas, bajas y modificaciones
 */
public class VExpresionBooleana {

    /**
     * Instancia del controlador de presentacion para enlazar operaciones
     */
    private CtrlPresentacion presentacion;

    /**
     * Frame de la vista actual
     */
    private JFrame frameVista = new JFrame("Vista Expresion Booleana");

    /**
     * Panel donde esta el contenido de la vista
     */
    private JPanel panelContenidos;

    /**
     * Boton para guardar una expresion booleana
     */
    private JButton saveExpreBoton;

    /**
     * Lista de expresiones guardadas
     */
    private JList<String> listaExprBool;

    /**
     * Area de texto para una expresion booleana
     */
    private JTextField expreTextField;

    /**
     * Boton para eliminar una expresión booleana
     */
    private JButton deleteExpresionBoton;

    /**
     * Panel contenedor de elementos principales de la vista
     */
    private JPanel centerPanel;

    /**
     * Panel contenedor de spacer para delimitar
     */
    private JPanel rightPanel;

    /**
     * Panel contenedor de spacer para delimitar
     */
    private JPanel leftPanel;

    /**
     * Panel contenedor de spacer para delimitar
     */
    private JPanel topPanel;

    /**
     * Panel contenedor de spacer para delimitar
     */
    private JPanel bottonPanel;

    /**
     * Boton para borrar el contenido de la área de texto "expreTextField"
     */
    private JButton clearBotton;


    /**
     * Constructor de la vista VExpresionBooleana
     */
    public VExpresionBooleana(CtrlPresentacion presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * Inicializa las características de la vista
     */
    public void inicializar() {
        frameVista.setContentPane(panelContenidos);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() / 1.5, height = screenSize.getHeight() / 1.5;
        frameVista.setBounds((int) (width / 3.7), (int) (height / 3.7), (int) width, (int) height);
        frameVista.setResizable(false);

        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la vista
     */
    private void inicializarComponentes() {
        frameVista.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        saveExpreBoton.setActionCommand("altaExprBool");
        saveExpreBoton.addActionListener(presentacion.getAccionEventoVExprBool());

        deleteExpresionBoton.setActionCommand("eliminaExprBoolBoton");
        deleteExpresionBoton.addActionListener(presentacion.getAccionEventoVExprBool());

        clearBotton.setActionCommand("clearExprBoolBoton");
        clearBotton.addActionListener(presentacion.getAccionEventoVExprBool());

        listaExprBool.addListSelectionListener(presentacion.getAccionEventoVExprBool());
    }

    /**
     * Copia la expresion seleccionada en la área de texto "expreTextField"
     */
    public void setExpreTextField() {
        expreTextField.setText(listaExprBool.getSelectedValue());
    }

    /**
     * Borra el contenido de la área de texto "expreTextField" y deja la lista listaExprBool si elementos seleccionados.
     */
    public void accionClearExprBoolBoton() {
        expreTextField.setText("");
        listaExprBool.clearSelection();
    }

    /**
     * Hace visible o no esta vista
     *
     * @param b booleano para saber si la vista se hace visible
     */
    public void hacerVisible(boolean b) {
        this.frameVista.setVisible(b);
        mostrarExpBool();
    }

    /**
     * Guarda la expresion booleana que hay en el área de texto
     */
    public void accionGuardarExprBool() throws Exception {
        String selExpr = listaExprBool.getSelectedValue();
        String expr = expreTextField.getText();

        if (selExpr == null) presentacion.altaExprBool(expr);
        else presentacion.modificarExpresionBool(selExpr, expr);
        mostrarExpBool();
        presentacion.actualizarcomboBoxExpreBool();
    }

    /**
     * Borra la expresion booleana seleccionada en listaExprBool
     */
    public void accionEliminaExprBoolBoton() {
        String infoDocumento = listaExprBool.getSelectedValue();
        if (infoDocumento != null) {
            presentacion.eliminarExprBool(infoDocumento);
            mostrarExpBool();
            presentacion.actualizarcomboBoxExpreBool();
        } else JOptionPane.showMessageDialog(null, "Seleccionar expresion para eliminar");
    }

    /**
     * Muestra todas las expresiones booleanas
     */
    public void mostrarExpBool() {
        ArrayList<String> expr = presentacion.listaExpresionesBooleanas(1);
        if (expr != null) {
            DefaultListModel<String> modelo = new DefaultListModel<>();
            modelo.addAll(expr);
            listaExprBool.setModel(modelo);
        } else listaExprBool.setModel(new DefaultListModel<>());
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelContenidos = new JPanel();
        panelContenidos.setLayout(new BorderLayout(0, 0));
        panelContenidos.putClientProperty("html.disable", Boolean.FALSE);
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(0, 0));
        panelContenidos.add(centerPanel, BorderLayout.CENTER);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(400, 128));
        centerPanel.add(scrollPane1, BorderLayout.CENTER);
        listaExprBool = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        listaExprBool.setModel(defaultListModel1);
        scrollPane1.setViewportView(listaExprBool);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        centerPanel.add(panel1, BorderLayout.EAST);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setPreferredSize(new Dimension(-1, 200));
        panel1.add(panel2, BorderLayout.NORTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setPreferredSize(new Dimension(200, 200));
        panel1.add(panel3, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setMaximumSize(new Dimension(500, 32767));
        panel4.setPreferredSize(new Dimension(500, 200));
        panel3.add(panel4, BorderLayout.NORTH);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel4.add(panel5, BorderLayout.CENTER);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel5.add(panel6, BorderLayout.NORTH);
        expreTextField = new JTextField();
        expreTextField.setHorizontalAlignment(0);
        expreTextField.setInheritsPopupMenu(true);
        expreTextField.setMinimumSize(new Dimension(200, 50));
        expreTextField.setPreferredSize(new Dimension(200, 50));
        panel6.add(expreTextField, BorderLayout.CENTER);
        clearBotton = new JButton();
        clearBotton.setPreferredSize(new Dimension(50, 50));
        clearBotton.setText("X");
        panel6.add(clearBotton, BorderLayout.EAST);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel5.add(panel7, BorderLayout.CENTER);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel8.setPreferredSize(new Dimension(200, 80));
        panel7.add(panel8, BorderLayout.CENTER);
        saveExpreBoton = new JButton();
        saveExpreBoton.setActionCommand("Guardar");
        saveExpreBoton.setMaximumSize(new Dimension(100, 38));
        saveExpreBoton.setMinimumSize(new Dimension(100, 38));
        saveExpreBoton.setPreferredSize(new Dimension(100, 38));
        saveExpreBoton.setText("Guardar");
        panel8.add(saveExpreBoton, BorderLayout.NORTH);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel8.add(panel9, BorderLayout.CENTER);
        deleteExpresionBoton = new JButton();
        deleteExpresionBoton.setActionCommand("Eliminar");
        deleteExpresionBoton.setMaximumSize(new Dimension(100, 38));
        deleteExpresionBoton.setMinimumSize(new Dimension(100, 38));
        deleteExpresionBoton.setOpaque(false);
        deleteExpresionBoton.setPreferredSize(new Dimension(100, 38));
        deleteExpresionBoton.setText("Eliminar");
        panel9.add(deleteExpresionBoton, BorderLayout.NORTH);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        panel10.setPreferredSize(new Dimension(10, 0));
        panel1.add(panel10, BorderLayout.WEST);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(0, 0));
        rightPanel.setPreferredSize(new Dimension(10, 0));
        panelContenidos.add(rightPanel, BorderLayout.EAST);
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(0, 0));
        leftPanel.setPreferredSize(new Dimension(10, 0));
        panelContenidos.add(leftPanel, BorderLayout.WEST);
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        topPanel.setPreferredSize(new Dimension(0, 10));
        panelContenidos.add(topPanel, BorderLayout.NORTH);
        bottonPanel = new JPanel();
        bottonPanel.setLayout(new BorderLayout(0, 0));
        bottonPanel.setPreferredSize(new Dimension(0, 10));
        panelContenidos.add(bottonPanel, BorderLayout.SOUTH);
    }

    /**
     * @hidden
     */
    public JComponent $$$getRootComponent$$$() {
        return panelContenidos;
    }

}


