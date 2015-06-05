package cliente.ListarRemitosCliente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

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
import javax.swing.table.TableCellRenderer;
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

public class GUIListarRemitosCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			   
    private JPanel jpDatos = null;			    private JPanel jpBuscador = null;
    private JButton jbImprimir = null;			private JButton jbSalir = null;			
    private JLabel fecha = null;				private JLabel nro = null; 
    private JLabel cliente = null;
    private JTextField jtfFecha = null;			private JTextField jtfNro = null;
    private JTextField jtfNombre = null;		private JTextField jtfCliente = null;
    public JScrollPane jspDatos = null;
    public JTable jtDatos = null;				private ModeloTabla modTabla = null;
    public String[] titulos = {"ID","Fecha","Nro de Remito","Cliente","Importe Total", "Anulada"};
    public Object[][] datos;
	private JButton jbAnular;
	private int diaLI;
	private int mesLI;
	private int anioLI;
	private JLabel jlPeriodo= null;			    private JTextField jtfPeriodo = null;
	private JButton jbCambiarPeriodo= null;		
    private JLabel jlMes = null;				private JComboBox jcbMes;
	private JLabel jlAnio = null;				private JTextField jtfAnio;
	private InputMap map = new InputMap();
	private JLabel jlDia;
	private JComboBox jcbDia;
	
    public GUIListarRemitosCliente(int dia,int mes,int anio,JFrame guiPadre) {
    	super(guiPadre);
    	diaLI=dia;
    	mesLI=mes;
		anioLI=anio;
		datos = new Object[0][titulos.length];
        this.setSize(new java.awt.Dimension(1200,640));
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setTitle("Remitos Cliente Existentes");
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
    
    public GUIListarRemitosCliente(int dia,int mes,int anio,JDialog guiPadre) {
    	super(guiPadre);
    	diaLI=dia;
    	mesLI=mes;
		anioLI=anio;
		datos = new Object[0][titulos.length];
        this.setSize(new java.awt.Dimension(1200,640));
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setTitle("Remitos Cliente Existentes");
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.add(getJBImprimir(), null);
            jpPpal.add(getJBSalir(), null);
            jpPpal.add(getJPDatos(), null);
            jpPpal.add(getJPBuscador(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }

    private JPanel getJPBuscador() {
        if (jpBuscador == null) {
            JLabel jlForm = new JLabel();
            jlForm.setBounds(new Rectangle(125,17,140,12));
            jlForm.setText("(dd/mm/aaaa)");
            jlForm.setForeground(Utils.colorTexto);
            jlForm.setFont(Utils.FuenteBasicaMinima());
            jpBuscador = new TransparentPanel();
            jpBuscador.setLayout(null);
            jpBuscador.setBorder(Utils.crearTituloYBorde("BUSCAR"));
            jpBuscador.setBounds(new Rectangle(15,10,1165,70));
            jpBuscador.add(jlForm, null);
            jpBuscador.add(getJLNroP(), null);
            jpBuscador.add(getJLCliente(), null);
            jpBuscador.add(getJTFNro(), null);
            jpBuscador.add(getJLFecha(), null);
            jpBuscador.add(getJTFFecha(), null);
            jpBuscador.add(getJTFCliente(), null);
        }
        return jpBuscador;
    }
    
    private JLabel getJLFecha() {
        if (fecha == null) {
        	fecha = new JLabel("FECHA:");
        	fecha.setHorizontalAlignment(JLabel.RIGHT);
        	fecha.setBounds(new java.awt.Rectangle(25,30,90,26));
        	fecha.setForeground(Utils.colorTexto);
        	fecha.setFont(Utils.FuenteBasica());
        }
        return fecha;
    }

    private JLabel getJLNroP() {
        if (nro == null) {
        	nro = new JLabel("NRO DE REMITO:");
        	nro.setHorizontalAlignment(JLabel.RIGHT);
        	nro.setBounds(new Rectangle(320,30,160,26));
        	nro.setForeground(Utils.colorTexto);
        	nro.setFont(Utils.FuenteBasica());
        }
        return nro;
    }
    
    private JLabel getJLCliente() {
        if (cliente == null) {
        	cliente = new JLabel("NOMBRE DEL CLIENTE:");
        	cliente.setHorizontalAlignment(JLabel.RIGHT);
        	cliente.setBounds(new Rectangle(675,30,260,26));
        	cliente.setForeground(Utils.colorTexto);
        	cliente.setFont(Utils.FuenteBasica());
        }
        return cliente;
    }
    
    public JTextField getJTFFecha() {
        if (jtfFecha == null) {
        	jtfFecha = new JTextField();
        	jtfFecha.setBounds(new Rectangle(125,30,150,26));
        	jtfFecha.setFont(Utils.FuenteCampos());
        }
        return jtfFecha;
    }
    
    public JTextField getJTFNro() {
        if (jtfNro == null) {
            jtfNro = new JTextField();
            jtfNro.setBounds(new Rectangle(490,30,200,26));
            jtfNro.setFont(Utils.FuenteCampos());
        }
        return jtfNro;
    }
    
    public JTextField getJTFCliente() {
        if (jtfCliente == null) {
        	jtfCliente = new JTextField();
        	jtfCliente.setBounds(new Rectangle(945,30,200,26));
        	jtfCliente.setFont(Utils.FuenteCampos());
        }
        return jtfCliente;
    }
    
    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBounds(new Rectangle(15,110,1165,415));
            jpDatos.setBorder(Utils.crearTituloYBorde("REMITOS CLIENTE"));
            jpDatos.add(getJSPDatos(), null);
            jpDatos.add(getJBAnularFactura(),null);
            agregarPeriodoSelec();
        }
        return jpDatos;
    }
    
    public JButton getJBAnularFactura() {
        if (jbAnular == null) {
        	jbAnular = new GlossyButton("ANULAR REMITO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbAnular.setBounds(new java.awt.Rectangle(10,370,240,26));
        	jbAnular.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
        	jbAnular.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbAnular.setInputMap(0, map);
        	jbAnular.setFont(Utils.FuenteBotonesChicos());
        }
        return jbAnular;
    }
    
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbImprimir.setBounds(new java.awt.Rectangle(650,560,200,40));
        	jbImprimir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
        	jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprimir.setInputMap(0, map);
        	jbImprimir.setFont(Utils.FuenteBotonesGrandes());
        }
        return jbImprimir;
    }
    
    public JButton getJBSalir() {
        if (jbSalir  == null) {
        	jbSalir = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbSalir.setBounds(new Rectangle(350,560,200,40));
            jbSalir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
        	jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbSalir.setInputMap(0, map);
        	jbSalir.setFont(Utils.FuenteBotonesGrandes());
        }
        return jbSalir;
    }

    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,60,1145,300));
                jspDatos.setViewportView(getJTDatos());
        }
        return jspDatos;
    }

    public JTable getJTDatos() {
        if (jtDatos == null) {
            modTabla = new ModeloTabla(titulos, datos);
            jtDatos = new JTable(modTabla){
				private static final long serialVersionUID = 1L;
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
					Component returnComp = super.prepareRenderer(renderer, row,column);
					String anulada=getValueAt(row,5).toString();
					if(anulada.compareTo("SI")==0){
						returnComp.setBackground(Utils.colorTexto);
						returnComp.setForeground(Color.RED);
					}else{
						returnComp.setBackground(Utils.colorTexto);
						returnComp.setForeground(Color.BLACK);
					}
					int[] seleccion=this.getSelectedRows();
					for(int j=0;j<seleccion.length;j++){
						if(seleccion[j]==row){
							returnComp.setBackground(new Color(176,196,222));
							returnComp.setForeground(Color.BLACK);
						}
					}
					return returnComp;
				}
			};
			jtDatos.setFont(Utils.FuenteTablasSimple());
			JTableHeader titTabla = jtDatos.getTableHeader();
			titTabla.setFont(Utils.FuenteTablasSimple());
			
			Utils.ocultarColumnaId(jtDatos);
            TableColumn columna1 = jtDatos.getColumn("Fecha");
            columna1.setPreferredWidth(100); 
            columna1.setMaxWidth(100); 
            columna1.setCellRenderer(Utils.alinearCentro());	
            TableColumn columna2 = jtDatos.getColumn("Nro de Remito");
            columna2.setPreferredWidth(150); 
            columna2.setMaxWidth(150);
            columna2.setCellRenderer(Utils.alinearCentro());
            TableColumn columna4 = jtDatos.getColumn("Importe Total");
            columna4.setPreferredWidth(150);
            columna4.setCellRenderer(Utils.alinearDerecha());	
            TableColumn columna7 = jtDatos.getColumn("Anulada");
            columna7.setPreferredWidth(90);
            columna7.setMaxWidth(90); 
            columna7.setCellRenderer(Utils.alinearCentro());
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
    
    public void setActionListeners(ActionListener lis) {
    	jbImprimir.addActionListener(lis);
    	jbSalir.addActionListener(lis);
    	jbAnular.addActionListener(lis);
    	jbCambiarPeriodo.addActionListener(lis);
    }

    public void setKeyListener(KeyListener lis) {
        jtfFecha.addKeyListener(lis);
        jtfNro.addKeyListener(lis);
        jtfCliente.addKeyListener(lis);
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
			jbCambiarPeriodo.setBounds(new java.awt.Rectangle(750,30,280,26));
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
			jtfPeriodo.setBounds(new java.awt.Rectangle(150,30,160,26));
			jtfPeriodo.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfPeriodo.setEnabled(false);
			jtfPeriodo.setFont(Utils.FuenteCampos());
		}
		return jtfPeriodo;
	}
    
    public JComboBox getJCBMes() {
		if (jcbMes == null) {
			jcbMes = new JComboBox();
			jcbMes.setBounds(new java.awt.Rectangle(510,30,60,26));
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
			jtfAnio.setBounds(new java.awt.Rectangle(650,30,80,26));
			jtfAnio.setDocument(new LimitadorNroMax(jtfAnio,4));
			jtfAnio.setText(String.valueOf(anioLI));
			jtfAnio.setFont(Utils.FuenteCampos());
		}
		return jtfAnio;
	}

    public JComboBox getJCBDia() {
		if (jcbDia == null) {
			jcbDia = new JComboBox();
			jcbDia.setBounds(new java.awt.Rectangle(380,30,60,26));
			jcbDia.setBackground(new Color(255,255,255));
			jcbDia.setForeground(java.awt.Color.black);
			jcbDia.setFont(Utils.FuenteCampos());
			jcbDia.addItem("01");
			jcbDia.addItem("02");
			jcbDia.addItem("03");
			jcbDia.addItem("04");
			jcbDia.addItem("05");
			jcbDia.addItem("06");
			jcbDia.addItem("07");
			jcbDia.addItem("08");
			jcbDia.addItem("09");
			jcbDia.addItem("10");
			jcbDia.addItem("11");
			jcbDia.addItem("12");
			jcbDia.addItem("13");
			jcbDia.addItem("14");
			jcbDia.addItem("15");
			jcbDia.addItem("16");
			jcbDia.addItem("17");
			jcbDia.addItem("18");
			jcbDia.addItem("19");
			jcbDia.addItem("20");
			jcbDia.addItem("21");
			jcbDia.addItem("22");
			jcbDia.addItem("23");
			jcbDia.addItem("24");
			jcbDia.addItem("25");
			jcbDia.addItem("26");
			jcbDia.addItem("27");
			jcbDia.addItem("28");
			jcbDia.addItem("29");
			jcbDia.addItem("30");
			jcbDia.addItem("31");
			jcbDia.setSelectedIndex(diaLI-1);
		}
		return jcbDia;
	}
    
	public void agregarPeriodoSelec(){
		jlPeriodo = new JLabel("PERIODO:");
		jlPeriodo.setHorizontalAlignment(JLabel.RIGHT);
		jlPeriodo.setBounds(new Rectangle(20,30,120,26));
		jlPeriodo.setForeground(Utils.colorTexto);
		jlPeriodo.setFont(Utils.FuenteBasica());
		jlDia = new JLabel("DIA:");
		jlDia.setForeground(Utils.colorTexto);
		jlDia.setFont(Utils.FuenteBasica());
		jlDia.setBounds(new Rectangle(320,30,50,26));
		jlDia.setHorizontalAlignment(SwingConstants.RIGHT);
		jlMes = new JLabel("MES:");
		jlMes.setForeground(Utils.colorTexto);
		jlMes.setFont(Utils.FuenteBasica());
		jlMes.setBounds(new Rectangle(450,30,50,26));
		jlMes.setHorizontalAlignment(SwingConstants.RIGHT);
		jlAnio = new JLabel("AÑO:");
		jlAnio.setForeground(Utils.colorTexto);
		jlAnio.setFont(Utils.FuenteBasica());
		jlAnio.setBounds(new Rectangle(590,30,60,26));
		jlAnio.setHorizontalAlignment(SwingConstants.RIGHT);
		jpDatos.add(jlPeriodo);
		jpDatos.add(jlDia);
		jpDatos.add(jlMes);
		jpDatos.add(jlAnio);
		jpDatos.add(getJTFPeriodo());
		jpDatos.add(getJCBDia());
		jpDatos.add(getJCBMes());
		jpDatos.add(getJTFAnio());
		jpDatos.add(getJBCambiarPeriodo());
	}
	
    public void setMouseListener(MouseListener lis) {
    	jtDatos.addMouseListener(lis);
    }
}

