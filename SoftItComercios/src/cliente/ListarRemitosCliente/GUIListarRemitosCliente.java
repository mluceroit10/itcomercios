package cliente.ListarRemitosCliente;

import java.awt.Color;
import java.awt.Component;
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
        this.setSize(new java.awt.Dimension(740,560));
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
        this.setSize(new java.awt.Dimension(740,560));
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
            jlForm.setBounds(new Rectangle(80,45,80,9));
            jlForm.setText("(dd/mm/aaaa)");
            jlForm.setForeground(Utils.colorTexto);
            jpBuscador = new TransparentPanel();
            jpBuscador.setLayout(null);
            jpBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "BUSCAR",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpBuscador.setBounds(new Rectangle(15,10,700,60));
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
        	fecha.setBounds(new java.awt.Rectangle(25,25,45,15));
        	fecha.setForeground(Utils.colorTexto);
        }
        return fecha;
    }

    private JLabel getJLNroP() {
        if (nro == null) {
        	nro = new JLabel("NRO DE REMITO:");
        	nro.setHorizontalAlignment(JLabel.RIGHT);
        	nro.setBounds(new Rectangle(180,25,100,15));
        	nro.setForeground(Utils.colorTexto);
        }
        return nro;
    }
    
    private JLabel getJLCliente() {
        if (cliente == null) {
        	cliente = new JLabel("NOMBRE DEL CLIENTE:");
        	cliente.setHorizontalAlignment(JLabel.RIGHT);
        	cliente.setBounds(new Rectangle(395,25,130,15));
        	cliente.setForeground(Utils.colorTexto);
        }
        return cliente;
    }
    
    public JTextField getJTFFecha() {
        if (jtfFecha == null) {
        	jtfFecha = new JTextField();
        	jtfFecha.setBounds(new Rectangle(75,22,80,22));
        }
        return jtfFecha;
    }
    
    public JTextField getJTFNro() {
        if (jtfNro == null) {
            jtfNro = new JTextField();
            jtfNro.setBounds(new Rectangle(285,22,80,22));
        }
        return jtfNro;
    }
    
    public JTextField getJTFCliente() {
        if (jtfCliente == null) {
        	jtfCliente = new JTextField();
        	jtfCliente.setBounds(new Rectangle(530,22,140,22));
        }
        return jtfCliente;
    }
    
    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBounds(new Rectangle(15,80,700,390));
            jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "REMITOS CLIENTE",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpDatos.add(getJSPDatos(), null);
            jpDatos.add(getJBAnularFactura(),null);
            agregarPeriodoSelec();
        }
        return jpDatos;
    }
    
    public JButton getJBAnularFactura() {
        if (jbAnular == null) {
        	jbAnular = new GlossyButton("ANULAR REMITO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbAnular.setBounds(new java.awt.Rectangle(10,350,150,25));
        	jbAnular.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
        	jbAnular.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbAnular.setInputMap(0, map);
        }
        return jbAnular;
    }
    
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbImprimir.setBounds(new java.awt.Rectangle(390,490,100,30));
        	jbImprimir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
        	jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprimir.setInputMap(0, map);
        }
        return jbImprimir;
    }
    
    public JButton getJBSalir() {
        if (jbSalir  == null) {
        	jbSalir = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbSalir.setBounds(new Rectangle(240,490,100,30));
            jbSalir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
        	jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbSalir.setInputMap(0, map);
        }
        return jbSalir;
    }

    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,50,680,290));
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
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
					}else{
						returnComp.setBackground(Utils.colorTexto);
						returnComp.setForeground(Color.BLACK);
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
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
			Utils.ocultarColumnaId(jtDatos);
            TableColumn columna1 = jtDatos.getColumn("Fecha");
            columna1.setMaxWidth(75); 
            columna1.setCellRenderer(Utils.alinearCentro());	
            TableColumn columna2 = jtDatos.getColumn("Nro de Remito");
            columna2.setPreferredWidth(100); 
            columna2.setMaxWidth(100);
            TableColumn columna4 = jtDatos.getColumn("Importe Total");
            columna4.setMaxWidth(150); 
            columna4.setPreferredWidth(150);
            columna4.setCellRenderer(Utils.alinearDerecha());	
            TableColumn columna7 = jtDatos.getColumn("Anulada");
            columna7.setPreferredWidth(50);
            columna7.setMaxWidth(50); 
            columna7.setCellRenderer(Utils.alinearCentro());
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
			jbCambiarPeriodo.setBounds(new java.awt.Rectangle(550,20,140,20));
			jbCambiarPeriodo.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/calendar.png")));
			jbCambiarPeriodo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbCambiarPeriodo.setInputMap(0, map);
			
		}
		return jbCambiarPeriodo;
	}
    
    public JTextField getJTFPeriodo() {
		if (jtfPeriodo == null) {
			jtfPeriodo = new JTextField();
			jtfPeriodo.setBounds(new java.awt.Rectangle(85,20,90,20));
			jtfPeriodo.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfPeriodo.setEnabled(false);
		}
		return jtfPeriodo;
	}
    

    public JComboBox getJCBDia() {
		if (jcbDia == null) {
			jcbDia = new JComboBox();
			jcbDia.setBounds(new java.awt.Rectangle(255,20,60,20));
			jcbDia.setBackground(new Color(255,255,255));
			jcbDia.setForeground(java.awt.Color.black);
			
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
    
    public JComboBox getJCBMes() {
		if (jcbMes == null) {
			jcbMes = new JComboBox();
			jcbMes.setBounds(new java.awt.Rectangle(355,20,60,20));
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
			jtfAnio.setBounds(new java.awt.Rectangle(470,20,60,20));
			jtfAnio.setDocument(new LimitadorNroMax(jtfAnio,4));
			jtfAnio.setText(String.valueOf(anioLI));
		}
		return jtfAnio;
	}
	
	public void agregarPeriodoSelec(){
		jlPeriodo = new JLabel("PERIODO:");
		jlPeriodo.setHorizontalAlignment(JLabel.RIGHT);
		jlPeriodo.setBounds(new Rectangle(20,20,60,20));
		jlPeriodo.setForeground(Utils.colorTexto);
		jlDia = new JLabel("DIA:");
		jlDia.setForeground(Utils.colorTexto);
		jlDia.setBounds(new Rectangle(200,20,50,20));
		jlDia.setHorizontalAlignment(SwingConstants.RIGHT);
		
		jlMes = new JLabel("MES:");
		jlMes.setForeground(Utils.colorTexto);
		jlMes.setBounds(new Rectangle(300,20,50,20));
		jlMes.setHorizontalAlignment(SwingConstants.RIGHT);
		jlAnio = new JLabel("AÑO:");
		jlAnio.setForeground(Utils.colorTexto);
		jlAnio.setBounds(new Rectangle(425,20,40,20));
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
}

