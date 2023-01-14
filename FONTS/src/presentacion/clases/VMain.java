package presentacion.clases;

import dominio.clases.Excepcion;
import presentacion.controlador.CtrlPresentacion;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class VMain {

    /**
     * Controlador de presentacion para enlazar acciones
     */
    private CtrlPresentacion presentacion;


    /**
     * Frame de la vista
     */
    private JFrame frameVista = new JFrame("Vista Principal");


    /**
     * Panel que contiene los componentes de la vista
     */
    private JPanel panelContenidos;


    /**
     * Permite explorar diferentes opciones, como abrir otras vistas
     */
    private JComboBox edicionCB;


    /**
     * Permite abrir la vista Documento para crear un nuevo documento
     */
    private JButton altaDocumentoBoton;


    /**
     * Permite abrir la vista Documento con los datos del documento seleccionado de la lista
     */
    private JButton abrirDocumentoB;


    /**
     * Permite eliminar del sistema el documento seleccionado de la lista
     */
    private JButton eliminarDocumentoB;


    /**
     * Permite seleccionar entre diferentes tipos de filtrados de documento
     */
    private JComboBox filtrador;


    /**
     * Permite cargar un documento del sistema local en formato txt o xml
     */
    private JButton cargarDocumentoButton;


    /**
     * Permite indicar el parametro de filtrado segun el tipo de filtrado
     */
    private JTextField filtra1;


    /**
     * Permite indicar el parametro de filtrado segun el tipo de filtrado
     */
    private JTextField filtra2;


    /**
     * Permite indicar el parametro de filtrado segun el tipo de filtrado
     */
    private JTextField filtra3;


    /**
     * Permite pasar a la lista la informacion de filtrado
     */
    private JButton filtrarBoton;


    /**
     * Permite mostrar todos los documentos existentes y resetear los ajustes de filtrado
     */
    private JButton mostrarDocumentosBoton;


    /**
     * Permite seleccionar la expresion booleana de las existentes para el filtrado por expresion booleana
     */
    private JComboBox comboBox1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTable table1;


    /**
     * Menu 'archivo' que permite seleccionar algunas opciones como cargar documento
     */
    private JMenuBar menuBar;


    /**
     * Constructora por defecto
     *
     * @param presentacion controlador de presentacion
     */
    public VMain(CtrlPresentacion presentacion) {
        this.presentacion = presentacion;
    }


    /**
     * Inicializa los ajustes de la vista, asi como su tamaño o menu bar
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
     * Inicializa los componentes de la vista, asi como su comando de accion y gestionador de eventos
     */
    private void inicializarComponentes() {
        frameVista.addWindowListener(presentacion.getAccionEventoVPrincipal());
        frameVista.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        frameVista.setJMenuBar(menuBar);
        JMenu archivo = new JMenu("Archivo");
        JMenuItem cargarDocumento = new JMenuItem(("Cargar documento"));
        cargarDocumento.setActionCommand("cargarDocumentoButton");
        cargarDocumento.addActionListener(presentacion.getAccionEventoVPrincipal());
        JMenuItem salir = new JMenuItem("Salir");
        salir.setActionCommand("salir");
        salir.addActionListener(presentacion.getAccionEventoVPrincipal());
        archivo.add(cargarDocumento);
        archivo.add(salir);
        menuBar.add(archivo);
        JMenu editar = new JMenu("Editar");
        JMenuItem expresionBooleana = new JMenuItem("Expresion Booleana");
        expresionBooleana.addActionListener(e -> presentacion.hacerVisibleVistaEB(true));
        JMenuItem listaAutor = new JMenuItem("Lista Autor");
        listaAutor.addActionListener(e -> presentacion.hacerVisibleVistaAutores(true));
        editar.add(expresionBooleana);
        editar.add(listaAutor);
        menuBar.add(editar);
        edicionCB.setActionCommand("edicionCB");
        edicionCB.addActionListener(presentacion.getAccionEventoVPrincipal());
        eliminarDocumentoB.setActionCommand("eliminarDocumentoB");
        eliminarDocumentoB.addActionListener(presentacion.getAccionEventoVPrincipal());
        altaDocumentoBoton.setActionCommand("altaDocumentoBoton");
        altaDocumentoBoton.addActionListener(presentacion.getAccionEventoVPrincipal());
        abrirDocumentoB.setActionCommand("abrirDocumentoB");
        abrirDocumentoB.addActionListener(presentacion.getAccionEventoVPrincipal());
        cargarDocumentoButton.setActionCommand("cargarDocumentoButton");
        cargarDocumentoButton.addActionListener(presentacion.getAccionEventoVPrincipal());
        mostrarDocumentosBoton.setActionCommand("mostrarDocumentosBoton");
        mostrarDocumentosBoton.addActionListener(presentacion.getAccionEventoVPrincipal());
        filtrador.setActionCommand("filtrador");
        filtrador.addActionListener(presentacion.getAccionEventoVPrincipal());
        filtrador.setSelectedItem("Filtrar documento por");
        filtrarBoton.setActionCommand("filtrarBoton");
        filtrarBoton.addActionListener(presentacion.getAccionEventoVPrincipal());
        filtra1.setVisible(false);
        filtra2.setVisible(false);
        filtra3.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);

        table1.addMouseListener(presentacion.getAccionEventoVPrincipal());
    }


    /**
     * Cierra la vista
     */
    public void dispose() {
        this.frameVista.dispose();
    }


    /**
     * Guarda el estado actual de los documentos, autores... de la sesion
     */
    public void saveAll() {
        presentacion.saveAll();
    }


    /**
     * Hace visible o invisible la vista actual
     *
     * @param b determina si se hace visible o invisible
     */
    public void hacerVisible(boolean b) {
        this.frameVista.setVisible(b);
        altaDocumentoBoton.setEnabled(true);
        abrirDocumentoB.setEnabled(true);
        eliminarDocumentoB.setEnabled(true);
    }


    /**
     * Gestiona las acciones del componente ArchivoCB segun la opcion escogida
     */
    public void accionArchivoCB() {
        String item = (String) edicionCB.getSelectedItem();
        if (item != null) {
            edicionCB.setSelectedIndex(0);
            switch (item) {
                case "Expresion booleana":
                    presentacion.hacerVisibleVistaEB(true);
                    break;

                case "Autores":
                    presentacion.hacerVisibleVistaAutores(true);
                    break;

                case "Salir":
                    int code = JOptionPane.showConfirmDialog(null, "¿Quieres salir?",
                            "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (code == 0) {
                        presentacion.saveAll();
                        frameVista.dispose();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * Elimina el documento seleccionado del sistema y actualiza la lista de documentos. Si no se ha seleccionado
     * ninguno, se abre un panel de alerta que indica que se debe seleccionar el documento a eliminar
     */
    public void accionEliminarDocumentoBoton() {

        int selectedRow = table1.getSelectedRow();
        if (selectedRow >= 0) {
            String titulo = table1.getModel().getValueAt(selectedRow, 0).toString();
            String autor = table1.getModel().getValueAt(selectedRow, 1).toString();
            try {
                presentacion.eliminarDocumento(titulo, autor);
            } catch (Excepcion e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            accionFiltrarBoton();
        } else JOptionPane.showMessageDialog(null, "Seleccionar documento que se quiera eliminar");
    }


    /**
     * Abre la vista Documento con informacion vacia para rellenar y asi dar de alta un nuevo documento, ademas
     * actualiza la lista de documentos
     */
    public void accionAltaDocumentoBoton() {
        altaDocumentoBoton.setEnabled(false);
        abrirDocumentoB.setEnabled(false);
        eliminarDocumentoB.setEnabled(false);
        presentacion.hacerVisibleVistaDocumento(true, "", "", "");
        accionFiltrarBoton();
    }


    /**
     * Abre un cuadro de eleccion de ficheros para escoger la extension (txt o xml) y localizacion del documento
     * que se quiere cargar. Ademas actualiza la lista de ficheros
     */
    public void accionCargarDocumentoBoton() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Data (*.xml)", "xml"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text (*.txt)", "txt"));
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("txt, xml", "txt", "xml");
        fileChooser.setFileFilter(filtro);
        fileChooser.setApproveButtonText("Cargar");

        int seleccion = fileChooser.showOpenDialog(new Container());
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fileChooser.getSelectedFile();
            try {
                presentacion.cargarDocumento(fichero.getAbsolutePath());
                accionFiltrarBoton();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }


    /**
     * Abre un panel donde pregunta si se quiere salir de la aplicacion, en caso afirmativo cierra la vista Principal
     */
    public void accionSalir() {
        int code = JOptionPane.showConfirmDialog(null, "¿Quieres salir?",
                "Confirmación", JOptionPane.YES_NO_OPTION);
        if (code == 0) {
            presentacion.saveAll();
            dispose();
        }
    }


    /**
     * Abre la vista Documento con la informacion del documento que se ha seleccionado. Si no se selecciona documento
     * se abre un panel de informacion que informa que se debe seleccionar un documento
     */
    public void accionAbrirDocumentoBoton() {

        int selectedRow = table1.getSelectedRow();
        if (selectedRow >= 0) {
            String titulo = table1.getModel().getValueAt(selectedRow, 0).toString();
            String autor = table1.getModel().getValueAt(selectedRow, 1).toString();
            altaDocumentoBoton.setEnabled(false);
            abrirDocumentoB.setEnabled(false);
            eliminarDocumentoB.setEnabled(false);

            presentacion.hacerVisibleVistaDocumento(true, titulo, autor, null);
        } else JOptionPane.showMessageDialog(null, "Seleccionar documento que se quiera abrir");
    }


    /**
     * Resetea los ajustes de filtrado y actualiza la lista de documentos de manera que se muestran todos los
     * documentos existentes
     */
    public void accionMostrarTodosDocumentos() {
        ArrayList<String> documentos = presentacion.listaDocumentos();
        filtra1.setText("");
        filtra2.setText("");
        filtra3.setText("");
        filtrador.setSelectedIndex(0);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Titulo");
        model.addColumn("Autor");
        table1.setModel(model);
        if (documentos == null) return;
        for (String doc : documentos) {
            String[] tituloAutor = doc.split(" \0");
            model.addRow(new Object[]{tituloAutor[0], tituloAutor[1]});
        }

    }


    /**
     * Segun la opcion seleccionada limpia los ajustes de filtrado y deja visibles solamente los necesarios para
     * el filtrado seleccionado
     */
    public void accionFiltrador() {
        String item = (String) filtrador.getSelectedItem();
        comboBox1.setVisible(false);
        if (item != null) {
            switch (item) {
                case "Filtrar documento por:":
                    label1.setText("");
                    label2.setText("");
                    label3.setText("");
                    filtra1.setText("");
                    filtra2.setText("");
                    filtra3.setText("");
                    filtra1.setVisible(false);
                    filtra2.setVisible(false);
                    filtra3.setVisible(false);
                    label1.setVisible(false);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    accionMostrarTodosDocumentos();
                    break;
                case "k docs parecidos(titulo, autor, k)":
                    label1.setText("Titulo");
                    label2.setText("Autor");
                    label3.setText("K");
                    filtra1.setText("");
                    filtra2.setText("");
                    filtra3.setText("");
                    filtra1.setVisible(true);
                    filtra2.setVisible(true);
                    filtra3.setVisible(true);
                    label1.setVisible(true);
                    label2.setVisible(true);
                    label3.setVisible(true);
                    break;
                case "query(k, query palabras)":
                    label1.setText("K");
                    label2.setText("Query");
                    label3.setText("");
                    filtra1.setText("");
                    filtra2.setText("");
                    filtra3.setText("");
                    filtra1.setVisible(true);
                    filtra2.setVisible(true);
                    filtra3.setVisible(false);
                    label1.setVisible(true);
                    label2.setVisible(true);
                    label3.setVisible(false);
                    break;

                case "expresion booleana(expression bool)":
                    label1.setText("Expresion booleana");
                    label2.setText("");
                    label3.setText("");
                    filtra1.setVisible(false);
                    filtra2.setVisible(false);
                    filtra3.setVisible(false);
                    label1.setVisible(true);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    comboBox1.setVisible(true);
                    comboBox1.setEditable(true);
                    addListaExpresionBooleana();
                    comboBox1.setSelectedItem("");
                    comboBox1.setPreferredSize(new Dimension(150, 40));
                    break;
                case "autor(autor)":
                    label1.setText("Autor");
                    label2.setText("");
                    label3.setText("");
                    filtra1.setText("");
                    filtra2.setText("");
                    filtra3.setText("");
                    filtra1.setVisible(true);
                    filtra2.setVisible(false);
                    filtra3.setVisible(false);
                    label1.setVisible(true);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    break;

                default:
                    break;
            }
        }
    }


    /**
     * Añade las expresiones booleanas existenes al seleccionador de expresion booleana del filtrado por expresion
     * booleana
     */
    public void addListaExpresionBooleana() {
        comboBox1.removeAllItems();
        for (String exprBool : presentacion.listaExpresionesBooleanas(0)) {
            comboBox1.addItem(exprBool);
        }

    }


    /**
     * Segun el filtrado seleccionado gestiona las excepciones y los documentos que se tienen que mostrar en la lista
     */
    public void accionFiltrarBoton() {
        String op = Objects.requireNonNull(filtrador.getSelectedItem()).toString();
        String autor, titulo;
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Titulo");
        model.addColumn("Autor");
        table1.setModel(model);

        switch (op) {
            case "Filtrar documento por:":
                accionMostrarTodosDocumentos();
                break;

            case "k docs parecidos(titulo, autor, k)":
                titulo = filtra1.getText();
                autor = filtra2.getText();
                if (titulo.equals("") || autor.equals("")) {
                    JOptionPane.showMessageDialog(null, "El titulo y el autor no pueden ser vacios");
                    break;
                }
                try {
                    presentacion.comprobarExistenciaDocumento(titulo, autor);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "No existe ningun documento con este titulo y autor");
                    break;
                }

                try {
                    int k = Integer.parseInt(filtra3.getText());
                    mostrarDocumentosFiltrados(presentacion.mostrarKDocsParecidos(titulo, autor, k));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "El tercer argumento debe ser un numero");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "El tercer argumento tiene que ser mayor a 0 y menor al numero total de documentos");
                }
                break;

            case "query(k, query palabras)":
                try {
                    int k = Integer.parseInt(filtra1.getText());
                    String query = filtra2.getText();
                    mostrarDocumentosFiltrados(presentacion.buscarDocumentosRelevantes(k, query));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "El primer argumento debe ser un numero");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "El primer argumento tiene que ser mayor a 0 y menor al numero total de documentos");
                }
                break;

            case "expresion booleana(expression bool)":
                String expr = comboBox1.getSelectedItem().toString();
                if (expr.equals("")) {
                    JOptionPane.showMessageDialog(null, "La expresion no puede ser vacia");
                    break;
                }
                try {
                    mostrarDocumentosFiltrados(presentacion.mostrarDocExpBool(expr));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }

                addListaExpresionBooleana();
                break;

            case "autor(autor)":
                autor = filtra1.getText();
                try {
                    mostrarDocumentosFiltrados(presentacion.mostrarDocAutor(autor));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "No existen documentos con un autor que tenga este prefijo");
                }
                break;

            default:
                break;
        }
    }


    /**
     * Añade los documentos al modelo de la lista, que son los que mostrara
     *
     * @param documentos documentos que se quieren mostrar
     */
    public void mostrarDocumentosFiltrados(ArrayList<String> documentos) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Titulo");
        model.addColumn("Autor");
        table1.setModel(model);

        if (documentos != null) {
            for (String doc : documentos) {
                String[] tituloAutor = doc.split(" \0");
                model.addRow(new Object[]{tituloAutor[0], tituloAutor[1]});
            }
        } else {
            JOptionPane.showMessageDialog(null, "No existen documentos que cumplan la condicion");
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
        panelContenidos.setEnabled(true);
        panelContenidos.setMinimumSize(new Dimension(0, 114));
        panelContenidos.setPreferredSize(new Dimension(800, 234));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setPreferredSize(new Dimension(800, 214));
        panelContenidos.add(panel1, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, BorderLayout.WEST);
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setMargin(new Insets(0, 0, 0, 0));
        toolBar1.setVisible(false);
        panel2.add(toolBar1, BorderLayout.NORTH);
        toolBar1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        edicionCB = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Editar");
        defaultComboBoxModel1.addElement("Expresion booleana");
        defaultComboBoxModel1.addElement("Autores");
        defaultComboBoxModel1.addElement("Salir");
        edicionCB.setModel(defaultComboBoxModel1);
        toolBar1.add(edicionCB);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.setMaximumSize(new Dimension(200, 32767));
        panel3.setPreferredSize(new Dimension(200, 48));
        panel1.add(panel3, BorderLayout.EAST);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setPreferredSize(new Dimension(10, 10));
        panel3.add(panel4);
        mostrarDocumentosBoton = new JButton();
        mostrarDocumentosBoton.setPreferredSize(new Dimension(190, 38));
        mostrarDocumentosBoton.setText("Mostrar Todos Documentos");
        panel3.add(mostrarDocumentosBoton);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setPreferredSize(new Dimension(10, 10));
        panel3.add(panel5);
        abrirDocumentoB = new JButton();
        abrirDocumentoB.setPreferredSize(new Dimension(190, 38));
        abrirDocumentoB.setText("Abrir Documento");
        panel3.add(abrirDocumentoB);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel6.setPreferredSize(new Dimension(10, 10));
        panel3.add(panel6);
        altaDocumentoBoton = new JButton();
        altaDocumentoBoton.setBorderPainted(true);
        altaDocumentoBoton.setContentAreaFilled(true);
        altaDocumentoBoton.setFocusCycleRoot(false);
        altaDocumentoBoton.setHorizontalAlignment(0);
        altaDocumentoBoton.setHorizontalTextPosition(0);
        altaDocumentoBoton.setInheritsPopupMenu(false);
        altaDocumentoBoton.setMaximumSize(new Dimension(190, 38));
        altaDocumentoBoton.setPreferredSize(new Dimension(190, 38));
        altaDocumentoBoton.setText("Crear Documento");
        altaDocumentoBoton.setVerticalAlignment(0);
        altaDocumentoBoton.setVerticalTextPosition(0);
        panel3.add(altaDocumentoBoton);
        cargarDocumentoButton = new JButton();
        cargarDocumentoButton.setPreferredSize(new Dimension(190, 38));
        cargarDocumentoButton.setText("Cargar Documento");
        panel3.add(cargarDocumentoButton);
        eliminarDocumentoB = new JButton();
        eliminarDocumentoB.setHorizontalAlignment(0);
        eliminarDocumentoB.setHorizontalTextPosition(0);
        eliminarDocumentoB.setPreferredSize(new Dimension(190, 38));
        eliminarDocumentoB.setText("Eliminar Documento");
        panel3.add(eliminarDocumentoB);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel1.add(panel7, BorderLayout.SOUTH);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel1.add(panel8, BorderLayout.CENTER);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setEnabled(false);
        panel8.add(scrollPane1, BorderLayout.CENTER);
        table1 = new JTable();
        table1.setAutoCreateRowSorter(false);
        table1.setDragEnabled(false);
        table1.setOpaque(false);
        table1.setRowHeight(30);
        table1.setRowMargin(5);
        table1.setUpdateSelectionOnSort(false);
        scrollPane1.setViewportView(table1);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel8.add(panel9, BorderLayout.NORTH);
        filtrarBoton = new JButton();
        filtrarBoton.setText("Filtrar");
        panel9.add(filtrarBoton, BorderLayout.EAST);
        filtrador = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Filtrar documento por:");
        defaultComboBoxModel2.addElement("k docs parecidos(titulo, autor, k)");
        defaultComboBoxModel2.addElement("query(k, query palabras)");
        defaultComboBoxModel2.addElement("expresion booleana(expression bool)");
        defaultComboBoxModel2.addElement("autor(autor)");
        filtrador.setModel(defaultComboBoxModel2);
        filtrador.setPreferredSize(new Dimension(308, 38));
        panel9.add(filtrador, BorderLayout.CENTER);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel10.setPreferredSize(new Dimension(500, 48));
        panel9.add(panel10, BorderLayout.SOUTH);
        label1 = new JLabel();
        label1.setText("Titulo");
        panel10.add(label1);
        comboBox1 = new JComboBox();
        comboBox1.setVisible(true);
        panel10.add(comboBox1);
        filtra1 = new JTextField();
        filtra1.setPreferredSize(new Dimension(200, 38));
        panel10.add(filtra1);
        label2 = new JLabel();
        label2.setText("Autor");
        panel10.add(label2);
        filtra2 = new JTextField();
        filtra2.setPreferredSize(new Dimension(100, 38));
        panel10.add(filtra2);
        label3 = new JLabel();
        label3.setText("K");
        panel10.add(label3);
        filtra3 = new JTextField();
        filtra3.setPreferredSize(new Dimension(100, 38));
        panel10.add(filtra3);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new BorderLayout(0, 0));
        panel9.add(panel11, BorderLayout.NORTH);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new BorderLayout(0, 0));
        panel12.setMinimumSize(new Dimension(0, 10));
        panel12.setPreferredSize(new Dimension(0, 10));
        panelContenidos.add(panel12, BorderLayout.NORTH);
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new BorderLayout(0, 0));
        panel13.setMinimumSize(new Dimension(10, 0));
        panel13.setPreferredSize(new Dimension(10, 0));
        panelContenidos.add(panel13, BorderLayout.EAST);
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new BorderLayout(0, 0));
        panel14.setMinimumSize(new Dimension(10, 0));
        panel14.setPreferredSize(new Dimension(0, 10));
        panelContenidos.add(panel14, BorderLayout.SOUTH);
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new BorderLayout(0, 0));
        panel15.setMinimumSize(new Dimension(10, 0));
        panel15.setPreferredSize(new Dimension(10, 0));
        panelContenidos.add(panel15, BorderLayout.WEST);
    }

    /**
     * @hidden  ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelContenidos;
    }

}
