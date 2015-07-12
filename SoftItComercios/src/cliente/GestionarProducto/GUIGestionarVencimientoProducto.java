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
import javax.swing.JCheckBox;
import javax.swing.JDialog;
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

public class GUIGestionarVencimientoProducto extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;	    	private JPanel jpGestion = null;
    private JPanel jpDatos = null;	    	private JPanel jpBuscador = null;
    private JButton jbAceptar = null;   	private JButton jbBorrar = null;
    private JButton jbImprimir = null;
    private JLabel jlNombre = null;			private JLabel jlCodigo = null;
    private JTextField jtfNombre = null;	private JTextField jtfCodigo = null;
    public JScrollPane jspDatos = null;
    public JTable jtDatos = null;			private ModeloTabla modTabla = null;
    public String[] titulos = {"Id","Código","Nombre","Proveedor","Pr Kg?","Stock Unid.","Stock Kilos","Fecha de Vencimiento","Dias Restantes"};
    public Object[][] datos;
    private InputMap map = new InputMap();
	private JCheckBox jbControlStock;
    
    public GUIGestionarVencimientoProducto(JDialog guiPadre) {
    	super(guiPadre);
    	datos = new Object[0][titulos.length];
        this.setSize(new java.awt.Dimension(1000,600));
        this.setLocationRelativeTo(guiPadre);  
        this.setResizable(false);
        this.setTitle("Gestión de Vencimientos de Productos");
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
            jpPpal.add(getJBSalir(), null);
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
            jpBuscador.setBounds(new java.awt.Rectangle(540,25,440,70));
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
            jpDatos.setBounds(new Rectangle(15,110,965,370));
            jpDatos.setBorder(Utils.crearTituloYBorde("LISTADO DE PRODUCTOS"));
            jpDatos.add(getJSPDatos(), null);
            jpDatos.add(getControlStock(),null);
       }
        return jpDatos;
    }

    private JPanel getJPGestion() {
        if (jpGestion == null) {
            jpGestion = new TransparentPanel();
            jpGestion.setLayout(null);
            jpGestion.setBounds(new Rectangle(15,25,360,70));
            jpGestion.setBorder(Utils.crearTituloYBorde("GESTION"));
            jpGestion.add(getJBBorrar(), null);
            jpGestion.add(getJBImprimir(), null);
        }
        return jpGestion;
    }

    public JButton getJBBorrar() {
        if (jbBorrar == null) {
            jbBorrar = new GlossyButton("ELIMINAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbBorrar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
            jbBorrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBorrar.setBounds(new Rectangle(10,30,160,26));
            jbBorrar.setInputMap(0, map);
            jbBorrar.setFont(Utils.FuenteBotonesChicos());
            jbBorrar.setMnemonic('E');
        }
        return jbBorrar;
    }
    
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbImprimir.setName("Imprimir");
        	jbImprimir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
        	jbImprimir.setBounds(new java.awt.Rectangle(190,30,160,26));
        	jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprimir.setInputMap(0, map);
        	jbImprimir.setFont(Utils.FuenteBotonesChicos());
        	jbImprimir.setMnemonic('P');
        }
        return jbImprimir;
    }

    public JCheckBox getControlStock() {
        if (jbControlStock == null) {
        	jbControlStock = new JCheckBox();
        	jbControlStock.setBounds(new Rectangle(20,335,500,25));
        	jbControlStock.setText("INCLUIR VENCIMIENTOS SIN STOCK");
        	jbControlStock.setName("mostrarTodos");
        	jbControlStock.setOpaque(false);
        	jbControlStock.setForeground(Utils.colorTexto);
        	jbControlStock.setFont(Utils.FuenteBasica());
        	jbControlStock.setMnemonic('V');
        }
        return jbControlStock;
    }
    
    public JButton getJBSalir() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setName("Salir");
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
            jbAceptar.setBounds(new Rectangle(400,520,200,40));
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
                jspDatos.setBounds(new Rectangle(10,30,945,300));
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
					int dif = Integer.parseInt(getValueAt(row,8).toString());
					if(dif<=0){
						returnComp.setBackground(Color.RED);
						returnComp.setForeground(Color.BLACK);
					}else if(dif>7){
						returnComp.setBackground(Color.GREEN);
						returnComp.setForeground(Color.BLACK);
					}else{
						returnComp.setBackground(Color.YELLOW);
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
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
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
            TableColumn columna3 = jtDatos.getColumn("Stock Unid.");
            columna3.setPreferredWidth(120);
            columna3.setMaxWidth(120);
            columna3.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna4 = jtDatos.getColumn("Stock Kilos");
            columna4.setPreferredWidth(120);
            columna4.setMaxWidth(120);
            columna4.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna5 = jtDatos.getColumn("Dias Restantes");
            columna5.setCellRenderer(Utils.alinearDerecha());
        }
        return jtDatos;
    }

    public JTextField getJTFBuscadorCodigo() {
        if (jtfCodigo == null) {
        	jtfCodigo = new JTextField();
        	jtfCodigo.setBounds(new Rectangle(110,35,100,26));
        	jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo,6));
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
        jbImprimir.addActionListener(lis);
        jbBorrar.addActionListener(lis);
        jbControlStock.addActionListener(lis);
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

