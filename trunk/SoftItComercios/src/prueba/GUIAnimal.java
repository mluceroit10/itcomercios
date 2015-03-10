package prueba;



import java.awt.BorderLayout;        import java.awt.FlowLayout;
import java.awt.GridBagConstraints;        import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;                import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;        import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;        import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;    import javax.swing.AbstractAction;
import javax.swing.BorderFactory;        import javax.swing.ImageIcon;
import javax.swing.JButton;            import javax.swing.JComboBox;
import javax.swing.JComponent;        import javax.swing.JDialog;
import javax.swing.JLabel;            import javax.swing.JPanel;
import javax.swing.JTextField;        import javax.swing.KeyStroke;
import javax.swing.WindowConstants;    import javax.swing.border.EtchedBorder;
import com.toedter.calendar.JDateChooser;


public class GUIAnimal extends JDialog{
   private static final long serialVersionUID = 1L;
   private JPanel jContentPane = null;
   private JPanel panelBotones = null;          private JPanel panelDatos;
   private JPanel panelDni;                private JPanel panelTelefono;
   private JPanel panelApellido;            private JPanel panelCuil;
   private JPanel panelDireccion;            private JPanel panelDatosAnimal = null;
   private JPanel panelNombrePropietario = null;      private JPanel panelEspecie = null;
   private JPanel panelRaza = null;

   private JLabel labelNombre = null;             private JTextField campoNombre = null;  
   private JLabel labelRaza = null;             private JComboBox campoRaza = null;
   private JLabel labelEdad = null;               private JTextField campoEdad = null;
   private JLabel labelFechaNacimiento = null;    private JDateChooser campoNacimiento = null;
   private JLabel labelEspecie = null;             private JComboBox campoEspecie = null;
   private JLabel labelSexo = null;             private JComboBox campoSexo = null;
   private JLabel labelDni = null;    private JTextField campoDni = null;    private JTextField campoNumero = null;
   private JLabel labelDatosPropietario;
   private JLabel labelApellido;            private JTextField campoApellido;
   private JLabel labelTelefono;            private JTextField campoTelefono;
   private JLabel labelCuil;                private JTextField campoCuil;
   private JLabel labelDireccion;                private JTextField campoDireccion;
   private JLabel labelNumero1 = null;
   private JLabel labelDatosAnimal = null;
   private JLabel labelNombrePropietario = null;    private JTextField campoNombrePropietario = null;
   private JLabel labelId = null;            private JTextField campoId = null;

   private JButton botonBuscarPropietario = null;        private JButton botonAgregar = null;
   private JButton botonModificar = null;        private JButton botonEliminar = null;
   private JButton botonAgregarEspecie = null;    private JButton botonAgregarRaza = null;

   public GUIAnimal() {
       super();
       cerrarVentana();
       initialize();
   }

  // @SuppressWarnings({ "serial" })
   private void cerrarVentana(){
       getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
       KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
       getRootPane().getActionMap().put("Cancel", new AbstractAction(){
           
           public void actionPerformed(ActionEvent e){
               setVisible(false);
               dispose();
           }
       });
       setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
       addWindowListener(new WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent evt){
               setVisible(false);
               dispose();
           }
       });
   }
   
   private void initialize() {  
       this.setSize(800, 400);
       //this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icono/icon.jpg")));
       this.setTitle("ANIMAL");
       this.setContentPane(getJContentPane());
       this.setLocationRelativeTo(null);
       this.setModal(true);
   }

   private JPanel getJContentPane() {
       if (jContentPane == null) {
           jContentPane = new JPanel();
           jContentPane.setLayout(new BorderLayout());
           jContentPane.add(getPanelBotones(),BorderLayout.SOUTH);
           jContentPane.add(getPanelDatosPropietario(), BorderLayout.NORTH);
           jContentPane.add(getPanelDatosAnimal(), BorderLayout.CENTER);
       }
       return jContentPane;
   }
   private JLabel getLabelDniPropietario() {
       if (labelDni == null) {
           labelDni = new JLabel();
           labelDni.setText("DNI:");
       }
       return labelDni;
   }

   private JLabel getLabelApellidoPropietario() {
       if (labelApellido == null) {
           labelApellido = new JLabel();
           labelApellido.setText("APELLIDO:");
       }
       return labelApellido;
   }

   private JLabel getLabelTelefonoPropietario() {
       if (labelTelefono == null) {
           labelTelefono = new JLabel();
           labelTelefono.setText("TELEFONO:");
       }
       return labelTelefono;
   }

   private JLabel getLabelCuilPropietario() {
       if (labelCuil == null) {
           labelCuil = new JLabel();
           labelCuil.setText("CUIL:");
       }
       return labelCuil;
   }

   private JLabel getLabelDireccionPropietario() {
       if (labelDireccion == null) {
           labelDireccion = new JLabel();
           labelDireccion.setText("DIRECCION:");
       }
       return labelDireccion;
   }



   private JLabel getLabelNombre() {
       if (labelNombre == null) {
           labelNombre = new JLabel();
           labelNombre.setText("NOMBRE:");
       }
       return labelNombre;
   }

   private JLabel getLabelRaza() {
       if (labelRaza == null) {
           labelRaza = new JLabel();
           labelRaza.setText("RAZA:");
       }
       return labelRaza;
   }

   private JLabel getLabelEdad() {
       if (labelEdad == null) {
           labelEdad = new JLabel();
           labelEdad.setText("EDAD:");
       }
       return labelEdad;
   }

   private JLabel getLabelFechaNacimiento() {
       if (labelFechaNacimiento == null) {
           labelFechaNacimiento = new JLabel();
           labelFechaNacimiento.setText("NACIMIENTO:");
       }
       return labelFechaNacimiento;
   }

   private JLabel getLabelEspecie() {
       if (labelEspecie == null) {
           labelEspecie = new JLabel();
           labelEspecie.setText("ESPECIE:");
       }
       return labelEspecie;
   }

   private JLabel getLabelSexo() {
       if (labelSexo == null) {
           labelSexo = new JLabel();
           labelSexo.setText("SEXO:");
       }
       return labelSexo;
   }

   public JTextField getCampoNombre() {
       if (campoNombre == null) {
           campoNombre = new JTextField();
           campoNombre.setPreferredSize(new Dimension(150, 20));
           campoNombre.setEditable(true);
           campoNombre.setBackground(Color.white);
       }
       return campoNombre;
   }

   public JTextField getCampoEdad() {
       if (campoEdad == null) {
           campoEdad = new JTextField();
           campoEdad.setPreferredSize(new Dimension(150, 20));
           campoEdad.addKeyListener(new KeyAdapter(){
               
               public void keyTyped(KeyEvent ev){if(!(ev.getKeyChar()>='0' && ev.getKeyChar()<='9')) ev.consume();}
           });
       }
       return campoEdad;
   }

   public JComboBox getCampoEspecie() {
       if (campoEspecie == null) {
           String[] especie=new String[]{};
           campoEspecie = new JComboBox(especie);
           campoEspecie.setPreferredSize(new Dimension(150, 20));
       }
       return campoEspecie;
   }
   
   public JComboBox getCampoRaza() {
       if (campoRaza == null) {
           String[] raza=new String[]{};
           campoRaza = new JComboBox(raza);
           campoRaza.setPreferredSize(new Dimension(150, 20));
           
       }
       return campoRaza;
   }

   public JComboBox getCampoSexo() {
       if (campoSexo == null) {
           String[] sexo=new String[]{"HEMBRA","MACHO","HEMBRA CASTRADA","MACHO CASTRADO"};
           campoSexo = new JComboBox(sexo);
           campoSexo.setPreferredSize(new Dimension(150, 20));
       }
       return campoSexo;
   }

   public JDateChooser getCampoNacimiento() {
       if (campoNacimiento == null) {
           campoNacimiento = new JDateChooser();
           campoNacimiento.setPreferredSize(new Dimension(150, 20));
       }
       return campoNacimiento;
   }


   private JPanel getPanelDatosPropietario() {
       if (panelDatos == null) {
           GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
           gridBagConstraints9.gridx = 3;
           gridBagConstraints9.gridy = 2;
           GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
           gridBagConstraints7.gridx = 0;
           gridBagConstraints7.gridy = 2;
           GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
           gridBagConstraints10.gridx = 2;
           gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
           gridBagConstraints10.gridy = 1;
           GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
           gridBagConstraints2.gridx = 0;
           gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
           gridBagConstraints2.gridy = 1;
           GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
           gridBagConstraints14.gridx = 0;
           gridBagConstraints14.gridy = 0;
           GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
           gridBagConstraints5.gridx = 3;
           gridBagConstraints5.insets = new Insets(10, 0, 10, 0);
           gridBagConstraints5.gridy = 0;
           GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
           gridBagConstraints11.gridx = 2;
           gridBagConstraints11.gridy = 2;
           GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
           gridBagConstraints8.gridx = 3;
           gridBagConstraints8.insets = new Insets(0, 0, 0, 0);
           gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
           gridBagConstraints8.gridy = 1;
           GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
           gridBagConstraints13.gridx = 3;
           gridBagConstraints13.gridy = 1;
           panelDatos = new JPanel();
           panelDatos.setLayout(new GridBagLayout());
           panelDatos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
           panelDatos.add(getPanelApellido(), gridBagConstraints8);
           panelDatos.add(getPanelDireccion(), gridBagConstraints11);
           panelDatos.add(getBotonBuscarPropietario(), gridBagConstraints5);
           panelDatos.add(getLabelDatosPropietario(), gridBagConstraints14);
           panelDatos.add(getPanelDniPropietario(), gridBagConstraints2);
           panelDatos.add(getPanelDniPropietario2(), gridBagConstraints10);
           panelDatos.add(getPanelTelefono(), gridBagConstraints7);
           panelDatos.add(getPanelCuil(), gridBagConstraints9);
       }
       return panelDatos;
   }

   private JPanel getPanelDniPropietario2() {
       if (panelNombrePropietario == null) {
          FlowLayout flowLayout2 = new FlowLayout();
           flowLayout2.setHgap(5);
           flowLayout2.setAlignment(java.awt.FlowLayout.RIGHT);
           flowLayout2.setVgap(5);
           panelNombrePropietario = new JPanel();
          panelNombrePropietario.setLayout(flowLayout2);
           panelNombrePropietario.add(getLabelNombrePropietario(), null);
           panelNombrePropietario.add(getCampoNombrePropietario(), null);
       }
       return panelNombrePropietario;
   }
   
   private JPanel getPanelDniPropietario() {
       if (panelDni == null) {
           FlowLayout flowLayout = new FlowLayout();
           flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
           panelDni = new JPanel();
           panelDni.setLayout(flowLayout);
           panelDni.setPreferredSize(new Dimension(218, 30));
           panelDni.add(getLabelDniPropietario(), null);
           panelDni.add(getCampoDniPropietario(), null);
       }
       return panelDni;
   }

   private JPanel getPanelTelefono() {
       if (panelTelefono == null) {
           FlowLayout flowLayout3 = new FlowLayout();
           flowLayout3.setAlignment(java.awt.FlowLayout.RIGHT);
           panelTelefono = new JPanel();
           panelTelefono.setLayout(flowLayout3);
           panelTelefono.add(getLabelTelefonoPropietario(), null);
           panelTelefono.add(getCampoTelefonoPropietario(), null);
       }
       return panelTelefono;
   }

   private JPanel getPanelApellido() {
       if (panelApellido == null) {
           FlowLayout flowLayout5 = new FlowLayout();
           flowLayout5.setAlignment(java.awt.FlowLayout.RIGHT);
           flowLayout5.setHgap(5);
           panelApellido = new JPanel();
           panelApellido.setLayout(flowLayout5);
           panelApellido.add(getLabelApellidoPropietario(), null);
           panelApellido.add(getCampoApellidoPropietario(), null);
       }
       return panelApellido;
   }

   private JPanel getPanelCuil() {
       if (panelCuil == null) {
           FlowLayout flowLayout1 = new FlowLayout();
           flowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
           flowLayout1.setHgap(5);
           panelCuil = new JPanel();
           panelCuil.setLayout(flowLayout1);
           panelCuil.setPreferredSize(new Dimension(228, 30));
           panelCuil.add(getLabelCuilPropietario(), null);
           panelCuil.add(getCampoCuilPropietario(), null);
       }
       return panelCuil;
   }

   private JPanel getPanelDireccion() {
       if (panelDireccion == null) {
           FlowLayout flowLayout4 = new FlowLayout();
           flowLayout4.setAlignment(java.awt.FlowLayout.RIGHT);
           panelDireccion = new JPanel();
           panelDireccion.setLayout(flowLayout4);
           panelDireccion.add(getLabelDireccionPropietario(), null);
           panelDireccion.add(getCampoDireccionPropietario(), null);
       }
       return panelDireccion;
   }
   
   public JTextField getCampoDniPropietario() {
       if (campoDni == null) {
           campoDni = new JTextField();
           campoDni.setMinimumSize(new Dimension(150, 20));
           campoDni.setEditable(false);
           campoDni.setEnabled(false);
           campoDni.setPreferredSize(new Dimension(150, 20));
       }
       return campoDni;
   }
   
   public JTextField getCampoNombrePropietario() {
       if (campoNombrePropietario == null) {
           campoNombrePropietario = new JTextField();
           campoNombrePropietario.setMinimumSize(new Dimension(150, 20));
           campoNombrePropietario.setEditable(false);
           campoNombrePropietario.setEnabled(false);
           campoNombrePropietario.setPreferredSize(new Dimension(150, 20));
       }
       return campoNombrePropietario;
   }

   public JTextField getCampoApellidoPropietario() {
       if (campoApellido == null) {
           campoApellido = new JTextField();
           campoApellido.setPreferredSize(new Dimension(150, 20));
           campoApellido.setEnabled(false);
           campoApellido.setEditable(false);
       }
       return campoApellido;
   }

   public JTextField getCampoTelefonoPropietario() {
       if (campoTelefono == null) {
           campoTelefono = new JTextField();
           campoTelefono.setPreferredSize(new Dimension(150, 20));
           campoTelefono.setEnabled(false);
           campoTelefono.setEditable(false);
       }
       return campoTelefono;
   }

   public JTextField getCampoCuilPropietario() {
       if (campoCuil == null) {
           campoCuil = new JTextField();
           campoCuil.setPreferredSize(new Dimension(150, 20));
           campoCuil.setEnabled(false);
           campoCuil.setEditable(false);
       }
       return campoCuil;
   }

   public JTextField getCampoDireccionPropietario() {
       if (campoDireccion == null) {
           campoDireccion = new JTextField();
           campoDireccion.setPreferredSize(new Dimension(150, 20));
           campoDireccion.setEnabled(false);
           campoDireccion.setEditable(false);
       }
       return campoDireccion;
   }

   public JButton getBotonBuscarPropietario() {
       if (botonBuscarPropietario == null) {
           botonBuscarPropietario = new JButton("BUSCAR PROPIETARIO");
           botonBuscarPropietario.setMnemonic('B');
           botonBuscarPropietario.registerKeyboardAction(botonBuscarPropietario.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                   JComponent.WHEN_FOCUSED);

           botonBuscarPropietario.registerKeyboardAction(botonBuscarPropietario.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                   JComponent.WHEN_FOCUSED);
           //botonBuscarPropietario.setPreferredSize(new Dimension(190, 30));
           //botonBuscarPropietario.setIcon(new ImageIcon(getClass().getResource("/icono/search.jpg")));
           
       }
       return botonBuscarPropietario;
   }

   private JPanel getPanelBotones() {
       if (panelBotones == null) {
           panelBotones = new JPanel();
           panelBotones.setLayout(new FlowLayout());
           panelBotones.setSize(new Dimension(806, 47));
           panelBotones.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
           panelBotones.add(getBotonAgregar(), null);
           panelBotones.add(getBotonModificar(), null);
           panelBotones.add(getBotonEliminar(), null);
       }
       return panelBotones;
   }

   public JButton getBotonAgregar() {
       if (botonAgregar == null) {
           botonAgregar = new JButton("AGREGAR");
           botonAgregar.setMnemonic('A');
           botonAgregar.registerKeyboardAction(botonAgregar.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                   JComponent.WHEN_FOCUSED);
           botonAgregar.registerKeyboardAction(botonAgregar.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                   JComponent.WHEN_FOCUSED);
          //botonAgregar.setPreferredSize(new Dimension(160, 30));
          // botonAgregar.setIcon(new ImageIcon(getClass().getResource("/icono/add.png")));
       }
       return botonAgregar;
   }

   public JButton getBotonModificar() {
       if (botonModificar == null) {
           botonModificar = new JButton("MODIFICAR");
           botonModificar.setMnemonic('M');
           botonModificar.registerKeyboardAction(botonModificar.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),JComponent.WHEN_FOCUSED);
           botonModificar.registerKeyboardAction(botonModificar.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),JComponent.WHEN_FOCUSED);
           //botonModificar.setPreferredSize(new Dimension(160,30));
         //  botonModificar.setIcon(new ImageIcon(getClass().getResource("/icono/script_edit.png")));
       }
       return botonModificar;
   }

   public JButton getBotonEliminar() {
       if (botonEliminar == null) {
           botonEliminar = new JButton("ELIMINAR");
           botonEliminar.setMnemonic('E');
           botonEliminar.registerKeyboardAction(botonEliminar.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                   JComponent.WHEN_FOCUSED);
          botonEliminar.registerKeyboardAction(botonEliminar.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                   JComponent.WHEN_FOCUSED);
   
           //botonEliminar.setPreferredSize(new Dimension(160,30));
         //  botonEliminar.setIcon(new ImageIcon(getClass().getResource("/icono/delete.png")));
       }
       return botonEliminar;
   }
   
   public void setearValoresRaza(String[] valores){
       this.campoRaza.removeAllItems();
       for(int i=0;i<valores.length;i++){
           this.campoRaza.addItem(valores[i]);
       }
   }
   
   public void setListenerButtons(ActionListener al){
       this.botonAgregar.addActionListener(al);
       this.botonEliminar.addActionListener(al);
       this.botonModificar.addActionListener(al);
       this.botonBuscarPropietario.addActionListener(al);
       this.campoEspecie.addActionListener(al);
       this.botonAgregarEspecie.addActionListener(al);
       this.botonAgregarRaza.addActionListener(al);
   }

   public JTextField getCampoNumero() {
       if (campoNumero == null) {
           campoNumero = new JTextField();
           campoNumero.setPreferredSize(new Dimension(150, 20));
           campoNumero.setForeground(new Color(255, 51, 51));
           campoNumero.setBackground(new Color(238, 238, 238));
           campoNumero.setEditable(false);
       }
       return campoNumero;
   }

   private JLabel getLabelDatosPropietario() {
       if (labelDatosPropietario == null) {
           labelDatosPropietario = new JLabel();
           labelDatosPropietario.setText("DATOS DEL PROPIETARIO:");
           labelDatosPropietario.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.gray));
       }
       return labelDatosPropietario;
   }

   private JLabel getLabelNumero() {
       if (labelNumero1 == null) {
           labelNumero1 = new JLabel();
           labelNumero1.setText("NUMERO:");
       }
       return labelNumero1;
   }

   private JPanel getPanelDatosAnimal() {
       if (panelDatosAnimal == null) {
           GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
           gridBagConstraints26.fill = GridBagConstraints.NONE;
           gridBagConstraints26.gridy = 0;
           gridBagConstraints26.weightx = 1.0;
           gridBagConstraints26.insets = new Insets(10, 5, 10, 5);
           gridBagConstraints26.anchor = GridBagConstraints.WEST;
           gridBagConstraints26.gridx = 1;
          GridBagConstraints gridBagConstraints112 = new GridBagConstraints();
           gridBagConstraints112.gridx = 0;
           gridBagConstraints112.anchor = GridBagConstraints.EAST;
           gridBagConstraints112.gridy = 0;
           labelId = new JLabel();
           labelId.setText("ID:");
           GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
           gridBagConstraints25.gridx = 3;
           gridBagConstraints25.anchor = GridBagConstraints.WEST;
           gridBagConstraints25.gridy = 3;
          GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
           gridBagConstraints110.gridx = 3;
           gridBagConstraints110.anchor = GridBagConstraints.WEST;
           gridBagConstraints110.gridy = 2;
          GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
           gridBagConstraints111.gridx = 1;
           gridBagConstraints111.anchor = GridBagConstraints.EAST;
           gridBagConstraints111.insets = new Insets(10, 0, 10, 20);
           gridBagConstraints111.gridy = 1;
          GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
           gridBagConstraints22.fill = GridBagConstraints.VERTICAL;
           gridBagConstraints22.gridy = 4;
           gridBagConstraints22.weightx = 1.0;
           gridBagConstraints22.insets = new Insets(10, 5, 10, 58);
           gridBagConstraints22.anchor = GridBagConstraints.WEST;
           gridBagConstraints22.gridx = 3;
           GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
           gridBagConstraints21.gridx = 2;
           gridBagConstraints21.anchor = GridBagConstraints.EAST;
           gridBagConstraints21.insets = new Insets(0, 0, 0, 5);
           gridBagConstraints21.gridy = 4;
          GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
           gridBagConstraints20.fill = GridBagConstraints.NONE;
           gridBagConstraints20.gridy = 0;
           gridBagConstraints20.weightx = 1.0;
           gridBagConstraints20.insets = new Insets(10, 5, 10, 5);
           gridBagConstraints20.anchor = GridBagConstraints.WEST;
           gridBagConstraints20.gridx = 3;
          GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
           gridBagConstraints19.gridx = 2;
           gridBagConstraints19.gridy = 0;
           GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
           gridBagConstraints17.gridx = 2;
           gridBagConstraints17.anchor = GridBagConstraints.EAST;
           gridBagConstraints17.insets = new Insets(0, 0, 0, 5);
           gridBagConstraints17.gridy = 3;
           GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
           gridBagConstraints15.gridx = 2;
           gridBagConstraints15.anchor = GridBagConstraints.EAST;
           gridBagConstraints15.insets = new Insets(0, 0, 0, 5);
           gridBagConstraints15.gridy = 2;
           GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
           gridBagConstraints12.gridx = 0;
           gridBagConstraints12.anchor = GridBagConstraints.EAST;
           gridBagConstraints12.gridy = 4;
           GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
           gridBagConstraints6.gridx = 1;
           gridBagConstraints6.insets = new Insets(10, 5, 10, 5);
           gridBagConstraints6.anchor = GridBagConstraints.WEST;
           gridBagConstraints6.gridy = 4;
           GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
           gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
           gridBagConstraints4.gridy = 3;
           gridBagConstraints4.weightx = 1.0;
           gridBagConstraints4.insets = new Insets(10, 5, 10, 5);
           gridBagConstraints4.anchor = GridBagConstraints.WEST;
           gridBagConstraints4.gridx = 1;
           GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
           gridBagConstraints3.gridx = 0;
           gridBagConstraints3.anchor = GridBagConstraints.EAST;
           gridBagConstraints3.gridy = 3;
           GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
           gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
           gridBagConstraints1.gridy = 2;
           gridBagConstraints1.weightx = 1.0;
           gridBagConstraints1.insets = new Insets(10, 5, 10, 5);
           gridBagConstraints1.anchor = GridBagConstraints.WEST;
           gridBagConstraints1.gridx = 1;
           GridBagConstraints gridBagConstraints = new GridBagConstraints();
           gridBagConstraints.gridx = 0;
           gridBagConstraints.anchor = GridBagConstraints.EAST;
           gridBagConstraints.gridy = 2;
           panelDatosAnimal = new JPanel();
          panelDatosAnimal.setLayout(new GridBagLayout());
           panelDatosAnimal.add(getLabelNombre(), gridBagConstraints);
           panelDatosAnimal.add(getCampoNombre(), gridBagConstraints1);
           panelDatosAnimal.add(getLabelEdad(), gridBagConstraints3);
           panelDatosAnimal.add(getCampoEdad(), gridBagConstraints4);
           panelDatosAnimal.add(getCampoNacimiento(), gridBagConstraints6);
           panelDatosAnimal.add(getLabelFechaNacimiento(), gridBagConstraints12);
           panelDatosAnimal.add(getLabelEspecie(), gridBagConstraints15);
           panelDatosAnimal.add(getLabelRaza(), gridBagConstraints17);
           panelDatosAnimal.add(getLabelNumero(), gridBagConstraints19);
           panelDatosAnimal.add(getCampoNumero(), gridBagConstraints20);
           panelDatosAnimal.add(getLabelSexo(), gridBagConstraints21);
           panelDatosAnimal.add(getCampoSexo(), gridBagConstraints22);
           panelDatosAnimal.add(getLabelDatosAnimal(), gridBagConstraints111);
           panelDatosAnimal.add(getPanelEspecie(), gridBagConstraints110);
           panelDatosAnimal.add(getPanelRaza(), gridBagConstraints25);
           panelDatosAnimal.add(labelId, gridBagConstraints112);
           panelDatosAnimal.add(getCampoId(), gridBagConstraints26);
       }
       return panelDatosAnimal;
   }

   private JLabel getLabelDatosAnimal() {
       if (labelDatosAnimal == null) {
           labelDatosAnimal = new JLabel();
           labelDatosAnimal.setText(" DATOS DEL ANIMAL:");
           labelDatosAnimal.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.gray));
       }
       return labelDatosAnimal;
   }

   private JLabel getLabelNombrePropietario() {
       if (labelNombrePropietario == null) {
           labelNombrePropietario = new JLabel();
           labelNombrePropietario.setText("NOMBRE:");
       }
       return labelNombrePropietario;
   }

   private JPanel getPanelEspecie() {
       if (panelEspecie == null) {
           GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
           gridBagConstraints23.fill = GridBagConstraints.VERTICAL;
           gridBagConstraints23.gridy = 0;
           gridBagConstraints23.weightx = 1.0;
           gridBagConstraints23.gridx = 0;
           GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
           gridBagConstraints16.gridx = 1;
           gridBagConstraints16.insets = new Insets(0, 3, 0, 0);
           gridBagConstraints16.gridy = 0;
           panelEspecie = new JPanel();
          panelEspecie.setLayout(new GridBagLayout());
           panelEspecie.add(getBotonAgregarEspecie(), gridBagConstraints16);
           panelEspecie.add(getCampoEspecie(), gridBagConstraints23);
       }
       return panelEspecie;
   }

   public JButton getBotonAgregarEspecie() {
       if (botonAgregarEspecie == null) {
           botonAgregarEspecie = new JButton();
          // botonAgregarEspecie.setIcon(new ImageIcon(getClass().getResource("/icono/add.png")));
           botonAgregarEspecie.registerKeyboardAction(botonAgregarEspecie.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
          botonAgregarEspecie.registerKeyboardAction(botonAgregarEspecie.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
       }
       return botonAgregarEspecie;
   }

   public JButton getBotonAgregarRaza() {
       if (botonAgregarRaza == null) {
           botonAgregarRaza = new JButton();
      //    botonAgregarRaza.setIcon(new ImageIcon(getClass().getResource("/icono/add.png")));
           botonAgregarRaza.registerKeyboardAction(botonAgregarRaza.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
          botonAgregarRaza.registerKeyboardAction(botonAgregarRaza.getActionForKeyStroke(
                   KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                   KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
       }
       return botonAgregarRaza;
   }

   private JPanel getPanelRaza() {
       if (panelRaza == null) {
           GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
           gridBagConstraints18.fill = GridBagConstraints.VERTICAL;
           gridBagConstraints18.gridy = 0;
           gridBagConstraints18.weightx = 1.0;
           gridBagConstraints18.gridx = 0;
           GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
           gridBagConstraints24.gridx = 1;
           gridBagConstraints24.insets = new Insets(0, 3, 0, 0);
           gridBagConstraints24.anchor = GridBagConstraints.CENTER;
           gridBagConstraints24.gridy = 0;
           panelRaza = new JPanel();
           panelRaza.setLayout(new GridBagLayout());
           panelRaza.add(getBotonAgregarRaza(), gridBagConstraints24);
           panelRaza.add(getCampoRaza(), gridBagConstraints18);
       }
       return panelRaza;
   }

   public JTextField getCampoId() {
       if (campoId == null) {
           campoId = new JTextField();
           campoId.setPreferredSize(new Dimension(150, 20));
           campoId.setForeground(new Color(255, 51, 51));
           campoId.setBackground(new Color(238, 238, 238));
           campoId.setEditable(false);
       }
       return campoId;
   }

}  
