package prueba;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import cliente.LimitadorNroMax;
import cliente.ModeloTabla;

import common.Utils;

public class GUIGestionarProveedorP extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;		    private JPanel jpDatos = null;
    private JPanel jpGestion = null;	    private JPanel jpBuscador = null;
    private JButton jbIngresar = null;		private JButton jbModif = null;
    private JButton jbEliminar = null;    	private JButton jbAceptar = null;
    private JButton jbCancelar = null;	    private JButton jbImprimir = null;
    private JLabel jlNombre = null;			private JLabel jlCodigo = null;
    private JTextField jtfNombre = null;    private JTextField jtfCodigo = null;
    private ModeloTabla modTabla = null;
    public JScrollPane jspDatos = null;
    public final String[] titulos ={"ID","Código","Nombre","Teléfono","Dirección", "Localidad"};
    public Object[][] datos;
    public JTable tabla;
	private JButton jbECuenta=null;
	private InputMap map = new InputMap();
	private JPanel jpSur;
	private JPanel jpNorte;
	
    public GUIGestionarProveedorP(JFrame guiPadre) {
        super(guiPadre);
        datos = new Object[0][titulos.length];
    	this.setSize(740,540);
    	this.setLocationRelativeTo(guiPadre);
    //    this.setResizable(false);
        this.setTitle("Gestión de Proveedores");
        this.setContentPane(getJPPpal());
        this.getContentPane().setName("GUIGestionarProveedor");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public GUIGestionarProveedorP(JDialog guiPadre) {
        super(guiPadre);
        datos = new Object[0][titulos.length];
    	this.setSize(740,540);
    	this.setLocationRelativeTo(guiPadre);
     //   this.setResizable(false);
        this.setTitle("Gestión de Proveedores");
        this.setContentPane(getJPPpal());
        this.getContentPane().setName("GUIGestionarProveedor");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
    
    public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal = new JPanel();
            jpPpal.setLayout(new BorderLayout());
            jpPpal.add(getJPNorte(), BorderLayout.NORTH);
            jpPpal.add(getJPSur(),BorderLayout.SOUTH);
            jpPpal.add(getJPDatos(), BorderLayout.CENTER);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }

    public JPanel getJPNorte() {
        if (jpNorte == null) {
        	jpNorte = new JPanel();
        	jpNorte.setPreferredSize(new Dimension(740,80));
        	GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
        	gridBagConstraints00.fill = GridBagConstraints.BOTH;
        	gridBagConstraints00.weightx = 1.0;
        	gridBagConstraints00.gridx = 0;
            gridBagConstraints00.anchor = GridBagConstraints.WEST;
            gridBagConstraints00.gridy = 0;
            
            gridBagConstraints00.insets = new Insets(0,0, 0, 10);
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.fill = GridBagConstraints.BOTH;
            gridBagConstraints01.weightx = 1.0;
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.anchor = GridBagConstraints.EAST;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(0,0, 0, 0);
        	jpNorte.setLayout(new GridBagLayout());
        	jpNorte.add(getJPGestion(),gridBagConstraints00);
        	jpNorte.add(getJPBuscador(), gridBagConstraints01);
        //	jpNorte.setBackground(Utils.colorPanel);
        }
        return jpNorte;
    }
    
    public JPanel getJPSur() {
        if (jpSur == null) {
        	jpSur = new JPanel();
        	jpSur.setPreferredSize(new Dimension(740,60));
        	GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
            gridBagConstraints00.gridx = 0;
            gridBagConstraints00.gridy = 0;
            gridBagConstraints00.insets = new Insets(0,0, 0, 40);
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(0, 0, 0, 0);
            jpSur.setLayout(new GridBagLayout());
            jpSur.add(getJBAceptar(),gridBagConstraints00);
            jpSur.add(getJBCancelar(), gridBagConstraints01);
            jpSur.setBackground(Utils.colorFondo);
        }
        return jpSur;
    }

    private JPanel getJPBuscador() {
        if (jpBuscador == null) {
            jpBuscador = new JPanel();
            GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
            gridBagConstraints00.fill = GridBagConstraints.BOTH;
        	gridBagConstraints00.weightx = 1.0;
            gridBagConstraints00.gridx = 0;
            gridBagConstraints00.gridy = 0;
            gridBagConstraints00.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.fill = GridBagConstraints.BOTH;
        	gridBagConstraints01.weightx = 1.0;
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints02 = new GridBagConstraints();
            gridBagConstraints02.fill = GridBagConstraints.BOTH;
        	gridBagConstraints02.weightx = 1.0;
            gridBagConstraints02.gridx = 2;
            gridBagConstraints02.gridy = 0;
            gridBagConstraints02.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints03 = new GridBagConstraints();
            gridBagConstraints03.fill = GridBagConstraints.BOTH;
        	gridBagConstraints03.weightx = 1.0;
            gridBagConstraints03.gridx = 3;
            gridBagConstraints03.gridy = 0;
            gridBagConstraints03.insets = new Insets(5,0, 8, 0);

            jlCodigo = new JLabel();
            jlCodigo.setPreferredSize(new Dimension(50,15));//jlCodigo.setBounds(new Rectangle(15,30,60,15));
            jlCodigo.setText("Código:");
            jlCodigo.setHorizontalAlignment(SwingConstants.RIGHT);

            jlNombre = new JLabel();
            jlNombre.setPreferredSize(new Dimension(60,15));//jlNombre.setBounds(new Rectangle(130,30,60,15));
            jlNombre.setText("Nombre:");
            jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);

            jpBuscador.setLayout(new GridBagLayout());
            jpBuscador.setSize(new Dimension(290,65));//
            jpBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "Buscar",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
            //jpBuscador.setBounds(new java.awt.Rectangle(415,25,300,65));
            jpBuscador.add(jlCodigo, gridBagConstraints00);
            jpBuscador.add(getJTFBuscadorCodigo(), gridBagConstraints01);
            jpBuscador.add(jlNombre, gridBagConstraints02);
            jpBuscador.add(getJTFBuscadorNombre(), gridBagConstraints03);
            jpBuscador.setBackground(Utils.colorPanel);
        }
        return jpBuscador;
    }

    private JPanel getJPGestion() {
        if (jpGestion == null) {
            jpGestion = new JPanel();
            jpGestion.setLayout(new GridBagLayout());//new FlowLayout());
            GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
            gridBagConstraints00.fill = GridBagConstraints.BOTH;
        	gridBagConstraints00.weightx = 1.0;
            gridBagConstraints00.gridx = 0;
            gridBagConstraints00.gridy = 0;
            gridBagConstraints00.insets = new Insets(5,5, 8, 5);
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.fill = GridBagConstraints.BOTH;
        	gridBagConstraints01.weightx = 1.0;
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints02 = new GridBagConstraints();
            gridBagConstraints02.fill = GridBagConstraints.BOTH;
        	gridBagConstraints02.weightx = 1.0;
            gridBagConstraints02.gridx = 2;
            gridBagConstraints02.gridy = 0;
            gridBagConstraints02.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints03 = new GridBagConstraints();
            gridBagConstraints03.fill = GridBagConstraints.BOTH;
        	gridBagConstraints03.weightx = 1.0;
            gridBagConstraints03.gridx = 3;
            gridBagConstraints03.gridy = 0;
            gridBagConstraints03.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints04 = new GridBagConstraints();
            gridBagConstraints04.fill = GridBagConstraints.BOTH;
            gridBagConstraints04.weightx = 1.0;
            gridBagConstraints04.gridx = 4;
            gridBagConstraints04.gridy = 0;
            gridBagConstraints04.insets = new Insets(5,0, 8, 0);
            jpGestion.setSize(new Dimension(380,65));
            jpGestion.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "Gestión", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
            jpGestion.add(getJBIngresar(),gridBagConstraints00);
            jpGestion.add(getJBModificar(),gridBagConstraints01);
            jpGestion.add(getJBEliminar(),gridBagConstraints02);
            jpGestion.add(getJBImprimir(),gridBagConstraints03);
            jpGestion.add(getJBECuenta(), gridBagConstraints04);
            jpGestion.setBackground(Utils.colorPanel);
        }
        return jpGestion;
    }

    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new JPanel();
            jpDatos.setSize(new Dimension(700,340));
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(0,5, 5, 5);
			 GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			//	gridBagConstraints10.fill = GridBagConstraints.BOTH;
				gridBagConstraints10.gridy = 1;
			//	gridBagConstraints10.weightx = 1.0;
				gridBagConstraints10.gridx = 0;
				gridBagConstraints10.insets = new Insets(0,5, 5,5);
            jpDatos.setLayout(new GridBagLayout());
            jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "Listado de Proveedores",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
            jpDatos.add(getJSPDatos(), gridBagConstraints);
         //   jpDatos.add(getJBECuenta(), gridBagConstraints10);
            jpDatos.setBackground(Utils.colorPanel);
        }
        return jpDatos;
    }

    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,20,680,280));
                jspDatos.setViewportView(getJTDatos());
        }
        return jspDatos;
    }

    public JTable getJTDatos() {
        if (tabla == null) {
            modTabla = new ModeloTabla(titulos, datos);
            tabla = new JTable(modTabla);
            Utils.ocultarColumnaId(tabla);
            TableColumn columna0 = tabla.getColumn("Código");
			columna0.setPreferredWidth(70);
			columna0.setCellRenderer(Utils.alinearDerecha());
        }
        return tabla;
    }

    public JButton getJBIngresar() {
        if (jbIngresar == null) {
            jbIngresar = new JButton();
            jbIngresar.setName("Alta");
            jbIngresar.setText("Ingresar");
            jbIngresar.setPreferredSize(new Dimension(70,25));//.setBounds(new java.awt.Rectangle(15,25,80,25));
            jbIngresar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbIngresar.setInputMap(0, map);
        }
        return jbIngresar;
    }

    public JButton getJBModificar() {
        if (jbModif == null) {
            jbModif = new JButton();
            jbModif.setName("Modificar");
            jbModif.setText("Modificar");
            jbModif.setPreferredSize(new Dimension(70,25));//.setBounds(new java.awt.Rectangle(105,25,80,25));
            jbModif.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbModif.setInputMap(0, map);
        }
        return jbModif;
    }
    
    public JTextField getJTFBuscadorCodigo() {
        if (jtfCodigo == null) {
        	jtfCodigo = new JTextField();
        	jtfCodigo.setPreferredSize(new Dimension(50, 22));//.setBounds(new Rectangle(65,30,50,22));
        	jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo,6));
        } 
        return jtfCodigo;
    }
    
    public JTextField getJTFBuscadorNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setPreferredSize(new Dimension(100, 22));//.setBounds(new Rectangle(190,30,100,22));
        }
        return jtfNombre;
    }

    public JButton getJBEliminar() {
        if (jbEliminar == null) {
        	jbEliminar = new JButton();
        	jbEliminar.setName("Baja");
        	jbEliminar.setText("Eliminar");
        	jbEliminar.setPreferredSize(new Dimension(70,25));//.setBounds(new java.awt.Rectangle(195,25,80,25));
        	jbEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbEliminar.setInputMap(0, map);
        }
        return jbEliminar;
    }
    
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new JButton();
        	jbImprimir.setName("Imprimir");
        	jbImprimir.setText("Imprimir");
        	jbImprimir.setPreferredSize(new Dimension(70,25));//.setBounds(new java.awt.Rectangle(285,25,80,25));
        	jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprimir.setInputMap(0, map);
        }
        return jbImprimir;
    }

    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new JButton();
            jbAceptar.setName("Aceptar");
            jbAceptar.setText("Aceptar");
            jbAceptar.setPreferredSize(new Dimension(100,30));//.setBounds(new Rectangle(240,470,100,30));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }

    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new JButton();
            jbCancelar.setName("Cancelar");
            jbCancelar.setText("Cancelar");
            jbCancelar.setPreferredSize(new Dimension(100,30));//.setBounds(new Rectangle(390,470,100,30));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setInputMap(0, map);
        }
        return jbCancelar;
    }

    public void setKeyListener(KeyListener lis) {
        jtfNombre.addKeyListener(lis);
        jtfCodigo.addKeyListener(lis);
    }

    public void setListSelectionListener(ListSelectionListener lis) {
        tabla.getSelectionModel().addListSelectionListener(lis);
    }

    public JButton getJBECuenta() {
        if (jbECuenta == null) {
        	jbECuenta = new JButton();
        	jbECuenta.setName("Cuenta");
        	jbECuenta.setText("E. Cuenta");
        	jbECuenta.setPreferredSize(new Dimension(70,25));//.setBounds(new java.awt.Rectangle(10,305,75,25));
        	jbECuenta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbECuenta.setInputMap(0, map);
        }
        return jbECuenta;
    }
    
    public void setActionListeners (ActionListener lis) {
        jbIngresar.addActionListener(lis);
        jbModif.addActionListener(lis);
        jbEliminar.addActionListener(lis);
        jbImprimir.addActionListener(lis);
        jbAceptar.addActionListener(lis);
        jbCancelar.addActionListener(lis);
        jbECuenta.addActionListener(lis);
    }

    public void repaint() {
        super.repaint();
    }
    
    public void actualizarTabla(){
        jpPpal.remove(getJPDatos());
        jpDatos = null;
        tabla = null;
        modTabla = null;
        jspDatos = null;
        jpPpal.add(getJPDatos(), null);
    }
}
