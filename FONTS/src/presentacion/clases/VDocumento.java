package presentacion.clases;

import dominio.clases.Excepcion;
import presentacion.controlador.CtrlPresentacion;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 @author sergi.campuzano
 @author ruben.dabrio
 */

/**
 * Vista que permite la visualización de un documento existente o a crear y la edición de su título, autor y contenido
 */
public class VDocumento {

    /**
     * Guarda el autor anterior a la última modificación de autor, el original si no ha habido modificación o
     * está vacío en caso de ser un nuevo documento.
     */
    private String autorOriginal;

    /**
     * Guarda el título anterior a la última modificación de título, el original si no ha habido modificación o
     * está vacío en caso de ser un nuevo documento.
     */
    private String tituloOriginal;

    /**
     * Instancia del controlador de presentacion para enlazar operaciones
     */
    private CtrlPresentacion presentacion;

    /**
     * Frame de la vista actual
     */
    private JFrame frameVista = new JFrame("Vista Documento");

    /**
     * Panel donde esta el contenido de la vista
     */
    private JPanel panelContenidos;

    /**
     * Boton usado para guardar un documento
     */
    private JButton guardarBoton;

    /**
     * Area de texto del contenido del documento
     */
    private JTextPane contenidoText;

    /**
     * Etiqueta con valor fijo ‘título’
     */
    private JLabel tituloText;

    /**
     * Area de texto del titulo del documento
     */
    private JTextField tituloArea;

    /**
     * Etiqueta con valor fijo ‘autor’
     */
    private JLabel autorText;

    /**
     * Area de texto del autor del documento
     */
    private JTextField autorArea;

    /**
     * Etiqueta con valor fijo ‘contenido’
     */
    private JLabel labelContenido;

    /**
     * Barra de menú de la vista VDocumento
     */
    private JMenuBar menuBar;


    /**
     * Constructor de la vista VDocumento
     */
    public VDocumento(CtrlPresentacion presentacion) {
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
        frameVista.addWindowListener(presentacion.getAccionEventoVDocumento());
        frameVista.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        guardarBoton.setActionCommand("guardarBoton");
        guardarBoton.addActionListener(presentacion.getAccionEventoVDocumento());

        menuBar = new JMenuBar();
        frameVista.setJMenuBar(menuBar);
        JMenu archivo = new JMenu("Archivo");

        JMenuItem guardarDocumento = new JMenuItem(("Guardar"));
        guardarDocumento.setActionCommand("guardarBoton");
        guardarDocumento.addActionListener(presentacion.getAccionEventoVDocumento());
        archivo.add(guardarDocumento);

        JMenuItem guardarComoDocumento = new JMenuItem(("Guardar como"));
        guardarComoDocumento.setActionCommand("guardarComo");
        guardarComoDocumento.addActionListener(presentacion.getAccionEventoVDocumento());
        archivo.add(guardarComoDocumento);

        JMenuItem pesoPalabra = new JMenuItem(("Peso palabra"));
        pesoPalabra.setActionCommand("vistaPalabra");
        pesoPalabra.addActionListener(presentacion.getAccionEventoVDocumento());
        archivo.add(pesoPalabra);

        menuBar.add(archivo);
    }

    /**
     * Hace visible o no esta vista y actualiza el valor de titulo, autor y contenido del documento
     *
     * @param b         booleano para saber si la vista se hace visible
     * @param titulo    titulo del documento
     * @param autor     autor del documento
     * @param contenido contenido del documento
     */
    public void hacerVisible(boolean b, String titulo, String autor, String contenido) {
        this.frameVista.setVisible(b);
        tituloText.setText("TITULO: ");
        autorText.setText("AUTOR: ");
        labelContenido.setText("CONTENIDO: ");
        tituloArea.setText(titulo);
        autorArea.setText(autor);
        contenidoText.setText(contenido);

        autorOriginal = autor;
        tituloOriginal = titulo;
    }

    /**
     * Guarda los cambios del documento
     */
    public void accionGuardarBoton() {
        String titulo = tituloArea.getText();
        String autor = autorArea.getText();
        String contenido = contenidoText.getText();

        if (titulo.equals("")) JOptionPane.showMessageDialog(null, "El titulo no puede estar vacio");
        else if (autor.equals("")) JOptionPane.showMessageDialog(null, "El autor no puede estar vacio");
        else if (tituloOriginal.equals("") && autorOriginal.equals("")) {
            try {
                presentacion.altaDocumento(titulo, autor, contenido);
                tituloOriginal = titulo;
                autorOriginal = autor;
                JOptionPane.showMessageDialog(null, "DOCUMENTO GUARDADO CORRECTAMENTE");
            } catch (Excepcion e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            try {
                presentacion.modificarDocumento(tituloOriginal, autorOriginal, titulo, autor, contenido);
                tituloOriginal = titulo;
                autorOriginal = autor;
                JOptionPane.showMessageDialog(null, "DOCUMENTO GUARDADO CORRECTAMENTE");
            } catch (Excepcion e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

        }
        presentacion.mostrarDocumentos();

    }

    /**
     * Abre un cuadro de opciones donde podremos escoger el nombre, el formato y
     * la ubicacion del guardado de nuestro documento
     *
     * @param titulo titulo del documento
     * @param autor  autor del documento
     */
    private void accionGuardarComo(String titulo, String autor) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Data (*.xml)", "xml"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text (*.txt)", "txt"));
        fileChooser.setSelectedFile(new File(titulo + "_" + autor));

        fileChooser.setAcceptAllFileFilterUsed(true);

        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            FileFilter fileFilter = fileChooser.getFileFilter();

            String path = selectedFile.getAbsolutePath();

            if (fileFilter instanceof FileNameExtensionFilter && !fileFilter.accept(selectedFile)) {
                FileNameExtensionFilter fileNameExtensionFilter = (FileNameExtensionFilter) fileChooser.getFileFilter();
                path += "." + fileNameExtensionFilter.getExtensions()[0];
            }

            try {
                presentacion.guardarDocumento(path, titulo, autor);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }


    /**
     * Consulta si hya cambios en el documento actual
     *
     * @return devuelve true si ha habido cambios respecto a su estado inicial, false en cualquier otro caso
     */
    public boolean hayCambios() {
        String titulo = tituloArea.getText();
        String autor = autorArea.getText();
        String contenidoOriginal = presentacion.getContenido(tituloOriginal, autorOriginal);
        return !titulo.equals(tituloOriginal) || !autor.equals(autorOriginal) ||
                !contenidoText.getText().equals(contenidoOriginal);
    }

    /**
     * Sale de la vista preguntando antes si queremos guardar el documento en caso de que hayan cambios en el documento
     */
    public void accionSalir() {

        if (autorArea.getText().equals("") || tituloArea.getText().equals("")) this.frameVista.dispose();
        else if (autorOriginal.equals("") || tituloOriginal.equals("") || hayCambios()) {
            int code = JOptionPane.showConfirmDialog(null, "¿Quieres guardar el documento?",
                    "Confirmación", JOptionPane.YES_NO_OPTION);
            if (code == 0) accionGuardarBoton();
        }
        presentacion.hacerVisibleVistaPrincipal(true);
    }

    /**
     * Abre un cuadro de opciones donde podremos escoger el nombre, el formato y
     * la ubicacion del guardado de nuestro documento
     */
    public void accionGuardarcomo() {
        accionGuardarBoton();
        String titulo = tituloArea.getText();
        String autor = autorArea.getText();
        if (!titulo.equals("") && !autor.equals("")) accionGuardarComo(titulo, autor);
    }

    /**
     * Hace visible la vista VPesoPalabra
     */
    public void hacerVisibleVistaPesoPalabra() {
        String titulo = tituloArea.getText();
        String autor = autorArea.getText();
        try {
            presentacion.hacerVisibleVistaPesoPalabra(true, titulo, autor);
        } catch (Excepcion e) {
            JOptionPane.showMessageDialog(null, "Documento aun no registrado");
        }
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setPreferredSize(new Dimension(10, 10));
        panelContenidos.add(panel1, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setPreferredSize(new Dimension(10, 10));
        panelContenidos.add(panel2, BorderLayout.SOUTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setPreferredSize(new Dimension(10, 10));
        panelContenidos.add(panel3, BorderLayout.WEST);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setPreferredSize(new Dimension(10, 10));
        panelContenidos.add(panel4, BorderLayout.EAST);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panelContenidos.add(panel5, BorderLayout.CENTER);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel5.add(panel6, BorderLayout.NORTH);
        tituloText = new JLabel();
        tituloText.setText("Titulo");
        panel6.add(tituloText, BorderLayout.WEST);
        tituloArea = new JTextField();
        panel6.add(tituloArea, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel5.add(panel7, BorderLayout.CENTER);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel7.add(panel8, BorderLayout.NORTH);
        autorText = new JLabel();
        autorText.setText("Autor");
        panel8.add(autorText, BorderLayout.WEST);
        autorArea = new JTextField();
        panel8.add(autorArea, BorderLayout.CENTER);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel7.add(panel9, BorderLayout.CENTER);
        labelContenido = new JLabel();
        labelContenido.setText("Contenido");
        panel9.add(labelContenido, BorderLayout.NORTH);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel9.add(scrollPane1, BorderLayout.CENTER);
        contenidoText = new JTextPane();
        scrollPane1.setViewportView(contenidoText);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        panel9.add(panel10, BorderLayout.SOUTH);
        guardarBoton = new JButton();
        guardarBoton.setText("Guardar");
        panel10.add(guardarBoton, BorderLayout.EAST);
    }

    /**
     * @hidden
     */
    public JComponent $$$getRootComponent$$$() {
        return panelContenidos;
    }

}