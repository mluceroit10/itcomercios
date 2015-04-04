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
        this.setSize(new java.awt.Dimension(760,435));
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
            jpGestion.setBounds(new java.awt.Rectangle(15,15,320,330));
            jpGestion.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b,
                    "GESTION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpGestion.add(getJBImprimir(), null);
            jpGestion.add(getJBBorrar(), null);
            jpGestion.add(getJPDatosNuevaPlanilla(),null);
        }
        return jpGestion;
    }
    
    private JPanel getJPDatosNuevaPlanilla() {
        if (jpDatosNP == null) {
            jlFecha = new JLabel();
            jlFecha.setBounds(new java.awt.Rectangle(30,40,150,15));
            jlFecha.setText("INGRESE FECHA LIMITE (*):");
            jlFecha.setForeground(Utils.colorTexto);
            jlFecha.setHorizontalAlignment(SwingConstants.RIGHT);
            jpDatosNP = new TransparentPanel();
            jpDatosNP.setLayout(null);
            jpDatosNP.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "NUEVA PLANILLA DE ES", 
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpDatosNP.setBounds(new java.awt.Rectangle(25,90,260,180));
            jpDatosNP.add(jlFecha, null);
            jpDatosNP.add(getJDateChooserFecha(), null);
            jpDatosNP.add(getJBCargar(), null);
         }
        return jpDatosNP;
    }
    
    public JButton getJBSalir() {
        if (jbSalir == null) {
        	jbSalir = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbSalir.setBounds(new Rectangle(325,365,100,30));
        	jbSalir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
        	jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbSalir.setInputMap(0, map);
        }
        return jbSalir;
    }
    
    public JButton getJBCargar() {
        if (jbCargar == null) {
            jbCargar = new GlossyButton("INGRESAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbCargar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/add.png")));
            jbCargar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCargar.setBounds(new java.awt.Rectangle(130,120,100,25));
            jbCargar.setInputMap(0, map);
        }
        return jbCargar;
    }
    
    public JButton getJBImprimir() {
        if (jbAceptar == null) {
        	jbAceptar = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
        	jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbAceptar.setBounds(new Rectangle(180,30,100,25));
        	jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }

    public JButton getJBBorrar() {
        if (jbBorrar == null) {
            jbBorrar = new GlossyButton("ELIMINAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbBorrar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
            jbBorrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBorrar.setBounds(new Rectangle(30,30,100,25));
            jbBorrar.setInputMap(0, map);
        }
        return jbBorrar;
    }
    
    private JPanel getJPBuscador() {
        if (jpBuscador == null) {
            jpBuscador = new TransparentPanel();
            jpBuscador.setLayout(null);
            jpBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "BUSCAR",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpBuscador.setBounds(new Rectangle(350,15,390,60));
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
        	jlFechaMov.setBounds(new Rectangle(180,15,90,9));
        }
        return jlFechaMov;
    }
    
    private JLabel getJLFecha() {
        if (fecha == null) {
        	fecha = new JLabel("Fecha:");
        	fecha.setHorizontalAlignment(JLabel.RIGHT);
        	fecha.setBounds(new Rectangle(110,30,60,15));
        	fecha.setForeground(Utils.colorTexto);
        }
        return fecha;
    }

    private JLabel getJLNroP() {
        if (nro == null) {
        	nro = new JLabel("Nro de Planilla:");
        	nro.setHorizontalAlignment(JLabel.RIGHT);
        	nro.setBounds(new Rectangle(110,30,100,15));
        	nro.setForeground(Utils.colorTexto);
        }
        return nro;
    }
    
    public JTextField getJTFFecha() {
        if (jtfFecha == null) {
        	jtfFecha = new JTextField();
        	jtfFecha.setBounds(new Rectangle(180,25,80,22));
        }
        return jtfFecha;
    }
    
    public JTextField getJTFNro() {
        if (jtfNro == null) {
            jtfNro = new JTextField();
            jtfNro.setBounds(new Rectangle(220,25,80,22));
        }
        return jtfNro;
    }
    
    private JComboBox getJCBDatos() {
        if (combo == null) {
            combo = new JComboBox();
            combo.setBounds(new Rectangle(10,25,100,22));
            combo.addItem("Fecha");
            combo.addItem("Nro Planilla");
            combo.setName("combo");
        }
        return combo;
    }

    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBounds(new Rectangle(350,90,390,255));
            jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "PLANILLAS E/S",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpDatos.add(getJSPDatos(), null);
            agregarPeriodoSelec();
        }
        return jpDatos;
    }
    
    
    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,80,370,170));
                jspDatos.setViewportView(getJTDatos());
        }
        return jspDatos;
    }

    public JTable getJTDatos() {
        if (jtDatos == null) {
            modTabla = new ModeloTabla(titulos, datos);
            jtDatos = new JTable(modTabla);
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
            jtfNombre.setBounds(new Rectangle(10,45,110,22));
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
			jDateChooserFecha.setBounds(new java.awt.Rectangle(30,65,200,22));
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
			jbCambiarPeriodo.setBounds(new java.awt.Rectangle(240,50,140,20));
			jbCambiarPeriodo.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/calendar.png")));
			jbCambiarPeriodo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbCambiarPeriodo.setInputMap(0, map);
			
		}
		return jbCambiarPeriodo;
	}
    
    public JTextField getJTFPeriodo() {
		if (jtfPeriodo == null) {
			jtfPeriodo = new JTextField();
			jtfPeriodo.setBounds(new java.awt.Rectangle(80,20,70,20));
			jtfPeriodo.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfPeriodo.setEnabled(false);
		}
		return jtfPeriodo;
	}
    
    public JComboBox getJCBMes() {
		if (jcbMes == null) {
			jcbMes = new JComboBox();
			jcbMes.setBounds(new java.awt.Rectangle(60,50,60,20));
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
		}
		return jcbMes;
	}
	
	public JTextField getJTFAnio() {
		if (jtfAnio == null) {
			jtfAnio = new JTextField();
			jtfAnio.setBounds(new java.awt.Rectangle(175,50,50,20));
			jtfAnio.setDocument(new LimitadorNroMax(jtfAnio,4));
			jtfAnio.setText(String.valueOf(anioLI));
		}
		return jtfAnio;
	}
	
	public void agregarPeriodoSelec(){
		jlPeriodo = new JLabel("PERIODO:");
		jlPeriodo.setBounds(new Rectangle(20,20,60,20));
		jlPeriodo.setForeground(Utils.colorTexto);
		jlMes = new JLabel("MES:");
		jlMes.setForeground(Utils.colorTexto);
		jlMes.setBounds(new Rectangle(10,50,40,20));
		jlMes.setHorizontalAlignment(SwingConstants.RIGHT);
		jlAnio = new JLabel("AÑO:");
		jlAnio.setForeground(Utils.colorTexto);
		jlAnio.setBounds(new Rectangle(140,50,35,20));
		jpDatos.add(jlPeriodo);
		jpDatos.add(jlMes);
		jpDatos.add(jlAnio);
		jpDatos.add(getJTFPeriodo());
		jpDatos.add(getJCBMes());
		jpDatos.add(getJTFAnio());
		jpDatos.add(getJBCambiarPeriodo());
	}
}

