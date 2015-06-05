package cliente.GestionarPlanillaES;

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

import com.toedter.calendar.JDateChooser;
import common.Utils;

public class GUIGestionarPlanillaES extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDatosNP = null;			   
    private JPanel jpDatos = null;			    private JPanel jpBuscador = null;
    private JPanel jpGestion = null;
    private JButton jbBorrar = null;			private JButton jbCargar = null;
	private JButton jbSalir = null;				private JButton jbAceptar = null;	
	private JLabel fecha = null;				private JLabel nro = null;
    private JLabel jlFecha = null;
    private JTextField jtfFecha = null;			private JTextField jtfNro= null;
    private JTextField jtfNombre = null;
    private JComboBox combo;
	private JDateChooser jDateChooserFecha;
    public JScrollPane jspDatos = null;
    public JTable jtDatos = null;				private ModeloTabla modTabla = null;
    public String[] titulos = {"ID","Nro. Planilla","Fecha"};
    public Object[][] datos;
	private JLabel jlFechaMov=null;
	private JLabel jlPeriodo= null;			    private JTextField jtfPeriodo = null;
	private JButton jbCambiarPeriodo= null;		
    private JLabel jlMes = null;				private JComboBox jcbMes;
	private JLabel jlAnio = null;				private JTextField jtfAnio;
	private int mesLI;
	private int anioLI;
	private InputMap map = new InputMap();
	
    public GUIGestionarPlanillaES(int mes,int anio,JFrame guiPadre) {
    	super(guiPadre);
    	mesLI=mes;
		anioLI=anio;
    	datos = new Object[0][titulos.length];
        this.setSize(new java.awt.Dimension(945,580));
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setTitle("Planillas E/S existentes");
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    } 

    public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.add(getJPDatos(), null);
            jpPpal.add(getJPBuscador(), null);
            jpPpal.add(getJPGestion(), null);
            jpPpal.add(getJBSalir(), null);
            jpPpal.setBackground(Utils.colorFondo);
            
        }
        return jpPpal;
    }

    private JPanel getJPGestion() {
        if (jpGestion == null) {
            jpGestion = new TransparentPanel();
            jpGestion.setLayout(null);
            jpGestion.setBounds(new java.awt.Rectangle(15,15,360,310));
            jpGestion.setBorder(Utils.crearTituloYBorde("GESTION"));
            jpGestion.add(getJBImprimir(), null);
            jpGestion.add(getJBBorrar(), null);
            jpGestion.add(getJPDatosNuevaPlanilla(),null);
        }
        return jpGestion;
    }
    
    private JPanel getJPDatosNuevaPlanilla() {
        if (jpDatosNP == null) {
            jlFecha = new JLabel();
            jlFecha.setBounds(new java.awt.Rectangle(10,50,270,26));
            jlFecha.setText("INGRESE FECHA LIMITE (*):");
            jlFecha.setForeground(Utils.colorTexto);
            jlFecha.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFecha.setFont(Utils.FuenteBasica());
            jpDatosNP = new TransparentPanel();
            jpDatosNP.setLayout(null);
            jpDatosNP.setBorder(Utils.crearTituloYBorde("NUEVA PLANILLA DE ES"));
            jpDatosNP.setBounds(new java.awt.Rectangle(25,90,310,180));
            jpDatosNP.add(jlFecha, null);
            jpDatosNP.add(getJDateChooserFecha(), null);
            jpDatosNP.add(getJBCargar(), null);
         }
        return jpDatosNP;
    }
    
    public JButton getJBSalir() {
        if (jbSalir == null) {
        	jbSalir = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbSalir.setBounds(new Rectangle(372,500,200,40));
        	jbSalir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
        	jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbSalir.setInputMap(0, map);
        	jbSalir.setFont(Utils.FuenteBotonesGrandes());
        }
        return jbSalir;
    }
    
    public JButton getJBCargar() {
        if (jbCargar == null) {
            jbCargar = new GlossyButton("INGRESAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbCargar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/add.png")));
            jbCargar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCargar.setBounds(new java.awt.Rectangle(75,130,160,26));
            jbCargar.setInputMap(0, map);
            jbCargar.setFont(Utils.FuenteBotonesChicos());
        }
        return jbCargar;
    }
    
    public JButton getJBImprimir() {
        if (jbAceptar == null) {
        	jbAceptar = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
        	jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbAceptar.setBounds(new Rectangle(190,30,160,26));
        	jbAceptar.setInputMap(0, map);
        	jbAceptar.setFont(Utils.FuenteBotonesChicos());
        }
        return jbAceptar;
    }

    public JButton getJBBorrar() {
        if (jbBorrar == null) {
            jbBorrar = new GlossyButton("ELIMINAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbBorrar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
            jbBorrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBorrar.setBounds(new Rectangle(10,30,160,26));
            jbBorrar.setInputMap(0, map);
            jbBorrar.setFont(Utils.FuenteBotonesChicos());
            
        }
        return jbBorrar;
    }
    
    private JPanel getJPBuscador() {
        if (jpBuscador == null) {
            jpBuscador = new TransparentPanel();
            jpBuscador.setLayout(null);
            jpBuscador.setBorder(Utils.crearTituloYBorde("BUSCAR"));
            jpBuscador.setBounds(new Rectangle(390,15,530,70));
            jpBuscador.add(getJCBDatos(), null);
            if (((String)combo.getSelectedItem()).compareTo("Nro Planilla")==0) {
                jpBuscador.add(getJLNroP(), null);
                jpBuscador.add(getJTFNro(), null);
            }
            if (((String)combo.getSelectedItem()).compareTo("Fecha")==0) {
                jpBuscador.add(getJLFecha(), null);
                jpBuscador.add(getJTFFecha(), null);
                jpBuscador.add(getJLFechaFormato(), null);
                getJTFNro();
            }
         }
        return jpBuscador;
    }
    
    private JLabel getJLFechaFormato() {
        if (jlFechaMov == null) {
        	jlFechaMov = new JLabel("(dd/mm/aaaa)");
        	jlFechaMov.setBounds(new Rectangle(360,17,140,12));
        	jlFechaMov.setForeground(Utils.colorTexto);
        	jlFechaMov.setFont(Utils.FuenteBasicaMinima());
        }
        return jlFechaMov;
    }
    
    private JLabel getJLFecha() {
        if (fecha == null) {
        	fecha = new JLabel("Fecha:");
        	fecha.setHorizontalAlignment(JLabel.RIGHT);
        	fecha.setBounds(new Rectangle(145,30,200,26));
        	fecha.setForeground(Utils.colorTexto);
        	fecha.setFont(Utils.FuenteBasica());
        }
        return fecha;
    }

    private JLabel getJLNroP() {
        if (nro == null) {
        	nro = new JLabel("Nro de Planilla:");
        	nro.setHorizontalAlignment(JLabel.RIGHT);
        	nro.setBounds(new Rectangle(145,30,200,26));
        	nro.setForeground(Utils.colorTexto);
        	nro.setFont(Utils.FuenteBasica());
        }
        return nro;
    }
    
    public JTextField getJTFFecha() {
        if (jtfFecha == null) {
        	jtfFecha = new JTextField();
        	jtfFecha.setBounds(new Rectangle(355,30,160,26));
        	jtfFecha.setFont(Utils.FuenteCampos());
        }
        return jtfFecha;
    }
    
    public JTextField getJTFNro() {
        if (jtfNro == null) {
            jtfNro = new JTextField();
            jtfNro.setBounds(new Rectangle(355,30,160,26));
            jtfNro.setFont(Utils.FuenteCampos());
        }
        return jtfNro;
    }
    
    private JComboBox getJCBDatos() {
        if (combo == null) {
            combo = new JComboBox();
            combo.setBounds(new Rectangle(10,30,170,26));
            combo.addItem("Fecha");
            combo.addItem("Nro Planilla");
            combo.setName("combo");
            combo.setFont(Utils.FuenteCampos());
        }
        return combo;
    }

    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBounds(new Rectangle(390,110,530,355));
            jpDatos.setBorder(Utils.crearTituloYBorde("PLANILLAS E/S"));
            jpDatos.add(getJSPDatos(), null);
            agregarPeriodoSelec();
        }
        return jpDatos;
    }
    
    
    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,100,510,245));
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
            TableColumn columna4 = jtDatos.getColumn("Nro. Planilla");
         	columna4.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna5 = jtDatos.getColumn("Fecha");
			columna5.setCellRenderer(Utils.alinearCentro());
        }
        return jtDatos;
    }

    public JTextField getJTFBuscador() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(10,45,110,26));
            jtfNombre.setFont(Utils.FuenteCampos());
        }
        return jtfNombre;
    }

    void mostrarJTFFecha() {
        this.getJPBuscador().remove(getJLNroP());
        this.getJPBuscador().remove(getJTFNro());
        this.getJPBuscador().add(getJTFFecha(),null);
        this.getJPBuscador().add(getJLFecha(),null);
        this.getJPBuscador().add(getJLFechaFormato(),null);
        this.repaint();
        this.show();
    }
    
    void mostrarJTFNro() {
        this.getJPBuscador().remove(getJTFFecha());
        this.getJPBuscador().remove(getJLFecha());
        this.getJPBuscador().remove(getJLFechaFormato());
        this.getJPBuscador().add(getJLNroP(),null);
        this.getJPBuscador().add(getJTFNro(),null);
        this.repaint();
        this.show();
    }
    
    public JDateChooser getJDateChooserFecha() {
		if (jDateChooserFecha == null) {
			jDateChooserFecha = new JDateChooser("dd - MMMMM - yyyy",false);
			jDateChooserFecha.setBounds(new java.awt.Rectangle(30,90,200,26));
		}
		return jDateChooserFecha;
	}
    
    public void setActionListeners(ActionListener lis) {
        jbAceptar.addActionListener(lis);
        jbCargar.addActionListener(lis);
        jbBorrar.addActionListener(lis);
        jbSalir.addActionListener(lis);
        combo.addActionListener(lis);
        jbCambiarPeriodo.addActionListener(lis);
    }

    public void setKeyListener(KeyListener lis) {
        jtfFecha.addKeyListener(lis);
        jtfNro.addKeyListener(lis);
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
			jbCambiarPeriodo.setBounds(new java.awt.Rectangle(280,65,240,26));
			jbCambiarPeriodo.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/calendar.png")));
			jbCambiarPeriodo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbCambiarPeriodo.setInputMap(0, map);
			jbCambiarPeriodo.setFont(Utils.FuenteBotonesChicos());
			
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
			jcbMes.setBounds(new java.awt.Rectangle(65,65,60,26));
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
			jtfAnio.setBounds(new java.awt.Rectangle(195,65,80,26));
			jtfAnio.setDocument(new LimitadorNroMax(jtfAnio,4));
			jtfAnio.setText(String.valueOf(anioLI));
			jtfAnio.setFont(Utils.FuenteCampos());
		}
		return jtfAnio;
	}
	
	public void agregarPeriodoSelec(){
		jlPeriodo = new JLabel("PERIODO:");
		jlPeriodo.setHorizontalAlignment(JLabel.RIGHT);
		jlPeriodo.setBounds(new Rectangle(10,30,120,26));
		jlPeriodo.setForeground(Utils.colorTexto);
		jlPeriodo.setFont(Utils.FuenteBasica());
		jlMes = new JLabel("MES:");
		jlMes.setForeground(Utils.colorTexto);
		jlMes.setFont(Utils.FuenteBasica());
		jlMes.setBounds(new Rectangle(10,65,50,26));
		jlMes.setHorizontalAlignment(SwingConstants.RIGHT);
		jlAnio = new JLabel("AÑO:");
		jlAnio.setForeground(Utils.colorTexto);
		jlAnio.setFont(Utils.FuenteBasica());
		jlAnio.setBounds(new Rectangle(130,65,60,26));
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

