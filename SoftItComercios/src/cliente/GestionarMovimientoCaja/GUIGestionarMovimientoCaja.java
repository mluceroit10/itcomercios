package cliente.GestionarMovimientoCaja;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import cliente.LimitadorNroMax;
import cliente.ModeloTabla;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIGestionarMovimientoCaja extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpGestion = null;
    private JPanel jpDatos = null;			    private JPanel jpBuscador = null;
    private JButton jbCargar = null;		    
    private JButton jbAceptar = null;		    private JButton jbBorrar = null;
    private JLabel jlFechaMov = null;			
    private JLabel jlCodigo= null;				private JTextField jtfCodigo = null;
    private JLabel jlFecha= null;			    private JTextField jtfFecha = null;			
    private JLabel jlPeriodo= null;			    private JTextField jtfPeriodo = null;
    private JButton jbCambiarPeriodo= null;		
    private JLabel jlMes = null;				private JComboBox jcbMes;
	private JLabel jlAnio = null;				private JTextField jtfAnio;
    private JScrollPane jspDatos = null;
    public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
    public String[] titulos = {"ID","Nro. Código","Fecha","Tipo de Movimiento","Importe","Forma de Pago","Descripción","Tipo y Nro Factura"};
    public Object[][] datos;
    private int mesLI;
	private int anioLI;

    private InputMap map = new InputMap();		
    
    public GUIGestionarMovimientoCaja(int mes,int anio,JFrame guiPadre) {
    	super(guiPadre);
    	mesLI=mes;
		anioLI=anio;
    	datos = new Object[0][titulos.length];
        this.setSize(new java.awt.Dimension(1200,640));
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setTitle("Gestión de Movimientos de Caja");
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.add(getJPGestion(), null);
            jpPpal.add(getJBAceptar(), null);
            jpPpal.add(getJPDatos(), null);
            jpPpal.add(getJPBuscador(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }

    private JPanel getJPBuscador() {
    	if (jpBuscador == null) {
            jpBuscador = new TransparentPanel();
            jlCodigo = new JLabel();
            jlCodigo.setBounds(new Rectangle(15,35,100,26));
            jlCodigo.setText("CODIGO:");
            jlCodigo.setForeground(Utils.colorTexto);
            jlCodigo.setFont(Utils.FuenteBasica());
            jlFecha = new JLabel();
            jlFecha.setBounds(new Rectangle(230,35,90,26));
            jlFecha.setText("FECHA:");
            jlFecha.setForeground(Utils.colorTexto);
            jlFecha.setFont(Utils.FuenteBasica());
            jpBuscador.setLayout(null);
            jpBuscador.setBorder(Utils.crearTituloYBorde("BUSCAR"));
            jpBuscador.setBounds(new java.awt.Rectangle(600,25,580,70));
            jpBuscador.add(jlCodigo, null);
            jpBuscador.add(jlFecha, null);
            jpBuscador.add(getJLFechaMov(), null);
            jpBuscador.add(getJTFBuscadorCodigo(), null);
            jpBuscador.add(getJTFBuscadorFecha(), null);
        }
        return jpBuscador;
    }
    
    public JTextField getJTFBuscadorCodigo() {
        if (jtfCodigo == null) {
        	jtfCodigo = new JTextField();
        	jtfCodigo.setBounds(new Rectangle(110,35,100,26));
        	jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo));
        	jtfCodigo.setFont(Utils.FuenteCampos());
        }
        return jtfCodigo;
    }
    
    public JTextField getJTFBuscadorFecha() {
        if (jtfFecha == null) {
        	jtfFecha = new JTextField();
        	jtfFecha.setBounds(new Rectangle(320,35,100,26));
        	jtfFecha.setFont(Utils.FuenteCampos());
        }
        return jtfFecha;
    }
    
    private JLabel getJLFechaMov() {
        if (jlFechaMov == null) {
        	jlFechaMov = new JLabel("(dd/mm/aaaa)");
        	jlFechaMov.setBounds(new java.awt.Rectangle(430,35,140,26));
        	jlFechaMov.setForeground(Utils.colorTexto);
        	jlFechaMov.setFont(Utils.FuenteBasicaMini());
        }
        return jlFechaMov;
    }
    
    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBounds(new Rectangle(15,110,1165,415));
            jpDatos.setBorder(Utils.crearTituloYBorde("MOVIMIENTOS DE CAJA"));
            jpDatos.add(getJSPDatos(), null);
            agregarPeriodoSelec();
        }
        return jpDatos;
    }

    private JPanel getJPGestion() {
        if (jpGestion == null) {
            jpGestion = new TransparentPanel();
            jpGestion.setLayout(null);
            jpGestion.setBounds(new Rectangle(15,25,360,70));
            jpGestion.setBorder(Utils.crearTituloYBorde("GESTION"));
            jpGestion.add(getJBCargar(), null);
            jpGestion.add(getJBBorrar(), null);
        }
        return jpGestion;
    }

    public JButton getJBCargar() {
        if (jbCargar == null) {
            jbCargar = new GlossyButton("INGRESAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbCargar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/add.png")));
            jbCargar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCargar.setBounds(new Rectangle(10,30,160,26));
            jbCargar.setInputMap(0, map);
            jbCargar.setFont(Utils.FuenteBotonesChicos());
            jbCargar.setMnemonic('I');
        }
        return jbCargar;
    }

    public JButton getJBBorrar() {
        if (jbBorrar == null) {
            jbBorrar = new GlossyButton("ELIMINAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbBorrar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
            jbBorrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBorrar.setBounds(new Rectangle(190,30,160,26));
            jbBorrar.setInputMap(0, map);
            jbBorrar.setFont(Utils.FuenteBotonesChicos());
            jbBorrar.setMnemonic('E');
        }
        return jbBorrar;
    }

    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new java.awt.Rectangle(500,560,200,40));
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setInputMap(0, map);
            jbAceptar.setFont(Utils.FuenteBotonesGrandes());
            jbAceptar.setMnemonic('S');
        }
        return jbAceptar;
    }

    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,60,1145,340));
                jspDatos.setViewportView(getJTDatos());
        }
        return jspDatos;
    }

    public JTable getJTDatos() {
        if (jtDatos == null) {
            modTabla = new ModeloTabla(titulos, datos);
            jtDatos = new JTable(modTabla);
            jtDatos.setFont(Utils.FuenteTablasSimple());
			JTableHeader titTabla = jtDatos.getTableHeader();
			titTabla.setFont(Utils.FuenteTablasSimple());
            Utils.ocultarColumnaId(jtDatos);
            TableColumn columna1 = jtDatos.getColumn("Nro. Código");
            columna1.setPreferredWidth(120);
			columna1.setMaxWidth(120);
			columna1.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna2 = jtDatos.getColumn("Fecha");
			columna2.setPreferredWidth(100);
			columna2.setMaxWidth(100);
			columna2.setCellRenderer(Utils.alinearCentro());
			TableColumn columna3 = jtDatos.getColumn("Tipo de Movimiento");
			columna3.setPreferredWidth(180);
			columna3.setMaxWidth(180);
			columna3.setCellRenderer(Utils.alinearCentro());
			
			TableColumn columna4 = jtDatos.getColumn("Importe");
         	columna4.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna5 = jtDatos.getColumn("Forma de Pago");
			columna5.setCellRenderer(Utils.alinearCentro());
        }
        return jtDatos;
    }

    public void setActionListeners(ActionListener lis) {
        jbAceptar.addActionListener(lis);
        jbCargar.addActionListener(lis);
        jbBorrar.addActionListener(lis);
        jbCambiarPeriodo.addActionListener(lis);
    }
    
    public void repaint() {
        super.repaint();
    }
    
    public void setKeyListener(KeyListener lis) {
        jtfCodigo.addKeyListener(lis);
        jtfFecha.addKeyListener(lis);
    }

    public void setListSelectionListener(ListSelectionListener lis) {
        jtDatos.getSelectionModel().addListSelectionListener(lis);
    }

    public void actualizarTabla(){
        jpPpal.remove(getJPDatos());
        jpDatos = null;
        jtDatos = null;
        modTabla = null;
        jspDatos = null;
        jpPpal.add(getJPDatos(), null);
    }
    
    public JButton getJBCambiarPeriodo() {
		if (jbCambiarPeriodo == null) {
			jbCambiarPeriodo = new GlossyButton("CAMBIAR PERIODO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
			jbCambiarPeriodo.setBounds(new java.awt.Rectangle(650,30,280,26));
			jbCambiarPeriodo.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/calendar.png")));
			jbCambiarPeriodo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbCambiarPeriodo.setInputMap(0, map);
			jbCambiarPeriodo.setFont(Utils.FuenteBotonesChicos());
			jbCambiarPeriodo.setMnemonic('C');
			
		}
		return jbCambiarPeriodo;
	}
    
    public JTextField getJTFPeriodo() {
		if (jtfPeriodo == null) {
			jtfPeriodo = new JTextField();
			jtfPeriodo.setBounds(new java.awt.Rectangle(150,30,140,26));
			jtfPeriodo.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfPeriodo.setEnabled(false);
			jtfPeriodo.setFont(Utils.FuenteCampos());
		}
		return jtfPeriodo;
	}
    
    public JComboBox getJCBMes() {
		if (jcbMes == null) {
			jcbMes = new JComboBox();
			jcbMes.setBounds(new java.awt.Rectangle(380,30,60,26));
			jcbMes.setBackground(new Color(255,255,255));
			jcbMes.setForeground(java.awt.Color.black);
			jcbMes.addItem("01");
			jcbMes.addItem("02");
			jcbMes.addItem("03");
			jcbMes.addItem("04");
			jcbMes.addItem("05");
			jcbMes.addItem("06");
			jcbMes.addItem("07");
			jcbMes.addItem("08");
			jcbMes.addItem("09");
			jcbMes.addItem("10");
			jcbMes.addItem("11");
			jcbMes.addItem("12");
			jcbMes.setSelectedIndex(mesLI-1);
			jcbMes.setFont(Utils.FuenteCampos());
		}
		return jcbMes;
	}
	
	public JTextField getJTFAnio() {
		if (jtfAnio == null) {
			jtfAnio = new JTextField();
			jtfAnio.setBounds(new java.awt.Rectangle(520,30,80,26));
			jtfAnio.setDocument(new LimitadorNroMax(jtfAnio,4));
			jtfAnio.setText(String.valueOf(anioLI));
			jtfAnio.setFont(Utils.FuenteCampos());
		}
		return jtfAnio;
	}
	
	public void agregarPeriodoSelec(){
		jlPeriodo = new JLabel("PERIODO:");
		jlPeriodo.setHorizontalAlignment(JLabel.RIGHT);
		jlPeriodo.setBounds(new Rectangle(20,30,120,26));
		jlPeriodo.setForeground(Utils.colorTexto);
		jlPeriodo.setFont(Utils.FuenteBasica());
		jlMes = new JLabel("MES:");
		jlMes.setForeground(Utils.colorTexto);
		jlMes.setFont(Utils.FuenteBasica());
		jlMes.setBounds(new Rectangle(320,30,50,26));
		jlMes.setHorizontalAlignment(SwingConstants.RIGHT);
		jlAnio = new JLabel("AÑO:");
		jlAnio.setForeground(Utils.colorTexto);
		jlAnio.setFont(Utils.FuenteBasica());
		jlAnio.setBounds(new Rectangle(450,30,60,26));
		jlAnio.setHorizontalAlignment(SwingConstants.RIGHT);
		jpDatos.add(jlPeriodo);
		jpDatos.add(jlMes);
		jpDatos.add(jlAnio);
		jpDatos.add(getJTFPeriodo());
		jpDatos.add(getJCBMes());
		jpDatos.add(getJTFAnio());
		jpDatos.add(getJBCambiarPeriodo());
	}
	
}

