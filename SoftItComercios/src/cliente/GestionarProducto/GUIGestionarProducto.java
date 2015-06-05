package cliente.GestionarProducto;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
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

public class GUIGestionarProducto extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;	    	private JPanel jpGestion = null;
    private JPanel jpDatos = null;	    	private JPanel jpBuscador = null;
    private JButton jbCargar = null;    	private JButton jbModif = null;
    private JButton jbAceptar = null;   	private JButton jbBorrar = null;
    private JButton jbCancelar = null;		private JButton jbImprimir = null;
    private JButton jbControlStock = null;
    private JLabel jlNombre = null;			private JLabel jlCodigo = null;
    private JTextField jtfNombre = null;	private JTextField jtfCodigo = null;
    public JScrollPane jspDatos = null;
    public JTable jtDatos = null;			private ModeloTabla modTabla = null;
    public String[] titulos = {"ID","Código","Nombre","Pr Kg?","Stock Actual","Stock Mínimo","Stock Actual Kg","Stock Mínimo Kg", "Precio Entrada","Margen Ganancia","Precio Venta Sin IVA","Precio Venta Con IVA","Proveedor"};
    public Object[][] datos;
    private InputMap map = new InputMap();
	private JButton jbControlVto = null;
    
    public GUIGestionarProducto(JFrame guiPadre) {
    	super(guiPadre);
    	datos = new Object[0][titulos.length];
        this.setSize(new java.awt.Dimension(1200,640));
        this.setLocationRelativeTo(guiPadre);  
        this.setResizable(false);
        this.setTitle("Gestión de Productos");
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
            jpPpal.add(getJBCancelar(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }

    private JPanel getJPBuscador() {
    	if (jpBuscador == null) {
            jpBuscador = new TransparentPanel();
            jlCodigo = new JLabel();
            jlCodigo.setBounds(new Rectangle(10,35,100,26));
            jlCodigo.setText("CODIGO:");
            jlCodigo.setForeground(Utils.colorTexto);
            jlCodigo.setFont(Utils.FuenteBasica());
            jlNombre = new JLabel();
            jlNombre.setBounds(new Rectangle(220,35,100,26));
            jlNombre.setText("NOMBRE:");
            jlNombre.setForeground(Utils.colorTexto);
            jlNombre.setFont(Utils.FuenteBasica());
            jpBuscador.setLayout(null);
            jpBuscador.setBorder(Utils.crearTituloYBorde("BUSCAR"));
            jpBuscador.setBounds(new java.awt.Rectangle(740,25,440,70));
            jpBuscador.add(jlCodigo, null);
            jpBuscador.add(jlNombre, null);
            jpBuscador.add(getJTFBuscadorCodigo(), null);
            jpBuscador.add(getJTFBuscadorNombre(), null);
        }
        return jpBuscador;
    }

    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBounds(new Rectangle(15,110,1165,415));
            jpDatos.setBorder(Utils.crearTituloYBorde("LISTADO DE PRODUCTOS"));
            jpDatos.add(getJSPDatos(), null);
            jpDatos.add(getJBControlVencimiento(), null);
            jpDatos.add(getJBControlStock(),null);
       }
        return jpDatos;
    }

    private JPanel getJPGestion() {
        if (jpGestion == null) {
            jpGestion = new TransparentPanel();
            jpGestion.setLayout(null);
            jpGestion.setBounds(new Rectangle(15,25,720,70));
            jpGestion.setBorder(Utils.crearTituloYBorde("GESTION"));
            jpGestion.add(getJBCargar(), null);
            jpGestion.add(getJBMod(), null);
            jpGestion.add(getJBBorrar(), null);
            jpGestion.add(getJBImprimir(), null);
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
        }
        return jbCargar;
    }

    public JButton getJBMod() {
        if (jbModif == null) {
            jbModif = new GlossyButton("MODIFICAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbModif.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/edit.png")));
            jbModif.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbModif.setBounds(new Rectangle(190,30,160,26));
            jbModif.setInputMap(0, map);
            jbModif.setFont(Utils.FuenteBotonesChicos());
        }
        return jbModif;
    }

    public JButton getJBBorrar() {
        if (jbBorrar == null) {
            jbBorrar = new GlossyButton("ELIMINAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbBorrar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
            jbBorrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBorrar.setBounds(new Rectangle(370,30,160,26));
            jbBorrar.setInputMap(0, map);
            jbBorrar.setFont(Utils.FuenteBotonesChicos());
        }
        return jbBorrar;
    }
    
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbImprimir.setName("Imprimir");
        	jbImprimir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
        	jbImprimir.setBounds(new java.awt.Rectangle(550,30,160,26));
        	jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprimir.setInputMap(0, map);
        	jbImprimir.setFont(Utils.FuenteBotonesChicos());
        }
        return jbImprimir;
    }

    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setName("Aceptar");
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
            jbAceptar.setBounds(new Rectangle(350,560,200,40));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setInputMap(0, map);
            jbAceptar.setFont(Utils.FuenteBotonesGrandes());
        }
        return jbAceptar;
    }

    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbCancelar.setName("Cancelar");
            jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
            jbCancelar.setBounds(new Rectangle(650,560,200,40));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setInputMap(0, map);
            jbCancelar.setFont(Utils.FuenteBotonesGrandes());
        }
        return jbCancelar;
    }

    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,30,1145,340));
                jspDatos.setViewportView(getJTDatos());
                
        }
        return jspDatos;
    }

    public JButton getJBControlVencimiento() {
        if (jbControlVto == null) {
        	jbControlVto = new GlossyButton("CONTROL VENCIMIENTOS",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbControlVto.setName("Vencimiento");
        	jbControlVto.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/vencimiento.png")));
        	jbControlVto.setBounds(new Rectangle(10,380,360,26));
        	jbControlVto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbControlVto.setInputMap(0, map);
        	jbControlVto.setFont(Utils.FuenteBotonesChicos());
        }
        return jbControlVto;
    }
    
    public JButton getJBControlStock() {
        if (jbControlStock == null) {
        	jbControlStock = new GlossyButton("CONTROL STOCK",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbControlStock.setName("Stock");
        	jbControlStock.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/estadistica.png")));
            jbControlStock.setBounds(new Rectangle(875,380,280,26));
            jbControlStock.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbControlStock.setInputMap(0, map);
            jbControlStock.setFont(Utils.FuenteBotonesChicos());
        }
        return jbControlStock;
    }
    
    
    public JTable getJTDatos() {
        if (jtDatos == null) {
            modTabla = new ModeloTabla(titulos, datos);
            jtDatos = new JTable(modTabla){
				private static final long serialVersionUID = 1L;
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
					Component returnComp = super.prepareRenderer(renderer, row,column);
					String stAct=getValueAt(row,4).toString();
					String stMin=getValueAt(row,5).toString();
					if(Integer.parseInt(stAct)<Integer.parseInt(stMin)){
						returnComp.setBackground(Color.YELLOW);
						returnComp.setForeground(Color.BLACK);
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
					if(column==11){
						returnComp.setFont(Utils.FuenteTablasNegrita());
					}
					return returnComp;
				}
			};
			jtDatos.setFont(Utils.FuenteTablasSimple());
			JTableHeader titTabla = jtDatos.getTableHeader();
			titTabla.setFont(Utils.FuenteTablasSimple());
			
		    Utils.ocultarColumnaId(jtDatos);
            TableColumn columna0 = jtDatos.getColumn("Código");
			columna0.setPreferredWidth(140);
			columna0.setMaxWidth(140);
			columna0.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna2 = jtDatos.getColumn("Pr Kg?");
            columna2.setPreferredWidth(70); 
            columna2.setMaxWidth(70);
            columna2.setCellRenderer(Utils.alinearCentro());
            TableColumn columna3 = jtDatos.getColumn("Stock Actual");
            columna3.setPreferredWidth(120);
            columna3.setMaxWidth(120);
            columna3.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna4 = jtDatos.getColumn("Stock Mínimo");
            columna4.setPreferredWidth(120);
            columna4.setMaxWidth(120);
            columna4.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna5 = jtDatos.getColumn("Precio Entrada");
            columna5.setPreferredWidth(120);
            columna5.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna6 = jtDatos.getColumn("Precio Venta Sin IVA");
            columna6.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna7 = jtDatos.getColumn("Precio Venta Con IVA");
            columna7.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna8 = jtDatos.getColumn("Margen Ganancia");
            columna8.setCellRenderer(Utils.alinearDerecha());
        }
        return jtDatos;
    }

    public JTextField getJTFBuscadorCodigo() {
        if (jtfCodigo == null) {
        	jtfCodigo = new JTextField();
        	jtfCodigo.setBounds(new Rectangle(110,35,100,26));
        	jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo,13));
        	jtfCodigo.setFont(Utils.FuenteCampos());
        }
        return jtfCodigo;
    }
    
    public JTextField getJTFBuscadorNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(320,35,100,26));
            jtfNombre.setFont(Utils.FuenteCampos());
        }
        return jtfNombre;
    }
    
    public void setActionListeners(ActionListener lis) {
        jbAceptar.addActionListener(lis);
        jbModif.addActionListener(lis);
        jbCargar.addActionListener(lis);
        jbImprimir.addActionListener(lis);
        jbBorrar.addActionListener(lis);
        jbCancelar.addActionListener(lis);
        jbControlStock.addActionListener(lis);
        jbControlVto.addActionListener(lis);
    }

    public void setKeyListener(KeyListener lis) {
        jtfNombre.addKeyListener(lis);
        jtfCodigo.addKeyListener(lis);
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
}

