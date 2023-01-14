package presentacion.clases;

import dominio.clases.Excepcion;
import presentacion.controlador.CtrlPresentacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 @author sergi.campuzano
 */

/**
 * Vista que representa el Peso de las Palabras de un documento
 */
public class VPesoPalabra {

    /**
     * Instancia del controlador de presentacion para enlazar operaciones
     */
    private CtrlPresentacion presentacion;

    /**
     * Frame de la vista actual
     */
    private JFrame frameVista = new JFrame("Vista Peso Palabra");

    /**
     * Panel donde esta el contenido de la vista
     */
    private JPanel panelContenidos;

    /**
     * Lista donde aparecen los palabras junto a su peso
     */
    private JList<String> listaPalabraPeso;

    /**
     * Valor del nuevo peso a una palabra
     */
    private JTextField nuevoPeso;

    /**
     * Botón de confirmación de un nuevo peso a una palabra
     */
    private JButton confirmarNuevoPeso;
    private JTable table1;

    /**
     * Guarda el título del documento
     */
    private String tituloDocumento;

    /**
     * Guarda el autor del documento
     */
    private String autorDocumento;


    /**
     * Constructor de la vista VPesoPalabra
     */
    public VPesoPalabra(CtrlPresentacion presentacion) {
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
        confirmarNuevoPeso.setActionCommand("confirmarNuevoPeso");
        confirmarNuevoPeso.addActionListener(presentacion.getAccionEventoVPesoPalabra());
        table1.setAutoCreateRowSorter(true);
    }

    /**
     * Hace visible o no esta vista
     *
     * @param b      booleano para saber si la vista se hace visible
     * @param titulo titulo del documento que tendremos sus palabras y peso
     * @param autor  autor del documento que tendremos sus palabras y peso
     */
    public void hacerVisible(boolean b, String titulo, String autor) throws Excepcion {
        mostrarPalabraPeso(titulo, autor);
        tituloDocumento = titulo;
        autorDocumento = autor;
        this.frameVista.setVisible(b);
    }

    /**
     * Muestra todos las palabras junto a su peso del documento identificado por título y autor
     *
     * @param titulo titulo del documento
     * @param autor  autor del documento
     */
    public void mostrarPalabraPeso(String titulo, String autor) throws Excepcion {
        ArrayList<String> list = presentacion.listaPesoPalabra(titulo, autor);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(model);
        model.addColumn("Palabra");
        model.addColumn("Peso");
        if (list != null) {
            for (String w : list) {
                String[] palabraPeso = w.split(": ");
                model.addRow(new Object[]{palabraPeso[0],  new BigDecimal(palabraPeso[1])});
            }

        }
    }

    /**
     * Guarda los cambios del peso de la palabra del documento
     */
    public void accionPesoPalabraBoton() throws Excepcion {
        if (isNumeric(nuevoPeso.getText())) {
            double peso = Double.parseDouble(nuevoPeso.getText());
            int selectedIndex = table1.getSelectedRow();
            if (selectedIndex >= 0) {
                String palabra = table1.getModel().getValueAt(selectedIndex, 0).toString();
                presentacion.modificarPesoPalabra(tituloDocumento, autorDocumento, palabra, peso);
                mostrarPalabraPeso(tituloDocumento, autorDocumento);
            } else JOptionPane.showMessageDialog(null, "Seleccionar palabra para cambiarle el peso");
        } else JOptionPane.showMessageDialog(null, "El peso tiene que ser un numero");
    }

    /**
     * Retorna si el parámetro peso es numérico o no
     *
     * @param peso string a comprobar si es un número realmente
     * @return Devuelve true si el parametro es un numero, sino devuelve false
     */
    private static boolean isNumeric(String peso) {
        boolean result;
        try {
            Double.parseDouble(peso);
            result = true;
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
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
        final JScrollPane scrollPane1 = new JScrollPane();
        panelContenidos.add(scrollPane1, BorderLayout.CENTER);
        table1 = new JTable();
        scrollPane1.setViewportView(table1);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panelContenidos.add(panel1, BorderLayout.EAST);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel2.add(panel3, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel3.add(panel4, BorderLayout.NORTH);
        confirmarNuevoPeso = new JButton();
        confirmarNuevoPeso.setHorizontalTextPosition(0);
        confirmarNuevoPeso.setText("Confirmar nuevo peso");
        panel4.add(confirmarNuevoPeso, BorderLayout.NORTH);
        nuevoPeso = new JTextField();
        panel2.add(nuevoPeso, BorderLayout.NORTH);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setPreferredSize(new Dimension(0, 150));
        panel1.add(panel5, BorderLayout.NORTH);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel6.setPreferredSize(new Dimension(10, 0));
        panel1.add(panel6, BorderLayout.WEST);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel7.setPreferredSize(new Dimension(10, 0));
        panel1.add(panel7, BorderLayout.EAST);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel8.setPreferredSize(new Dimension(10, 0));
        panelContenidos.add(panel8, BorderLayout.WEST);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel9.setPreferredSize(new Dimension(0, 10));
        panelContenidos.add(panel9, BorderLayout.NORTH);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        panel10.setPreferredSize(new Dimension(0, 10));
        panelContenidos.add(panel10, BorderLayout.SOUTH);
    }

    /**
     * @hidden
     */
    public JComponent $$$getRootComponent$$$() {
        return panelContenidos;
    }

}