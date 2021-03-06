package cliente.ListarDeudaClientes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import cliente.ModeloTabla;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIListarDeudaClientes extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDeudas = null;
	private JButton jbSalir = null;				public JButton jbImprimir;
	public JScrollPane jspDatos = null;
	public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
	public String[] titulos = {"Cliente","Fecha Ultima Facturación","Saldo a Favor","Adeudado"};
	public Object[][] datos;
	public Font a = new Font(Utils.tipoLetra,Font.BOLD,18);
	public JLabel leyenda=null;					
	private JCheckBox jCheckSelectAll = null;
	private InputMap map = new InputMap();
	
	public GUIListarDeudaClientes(JDialog guiPadre) {
		super(guiPadre);
		this.setSize(new java.awt.Dimension(1000,600));
		this.setTitle("Deudas Pendientes");
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	private JPanel getJPPpal() {
		if (jpPpal == null) {
			jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
			jpPpal.setLayout(null);
			jpPpal.setSize(new java.awt.Dimension(1000,600));
			jpPpal.add(getJPDeudas(),null);
			jpPpal.add(getJBImprimir(),null);
			jpPpal.add(getJBSalir(),null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	public JButton getJBImprimir() {
		if (jbImprimir == null) {
			jbImprimir = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
			jbImprimir.setBounds(new java.awt.Rectangle(250,520,200,40));
			jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbImprimir.setName("Imprimir");
			jbImprimir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
			jbImprimir.setInputMap(0, map);
			jbImprimir.setFont(Utils.FuenteBotonesGrandes());
			jbImprimir.setMnemonic('P');
		}
		return jbImprimir;
	}
	
	public JButton getJBSalir() {
		if (jbSalir == null) {
			jbSalir = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
			jbSalir.setBounds(new java.awt.Rectangle(550,520,200,40));
			jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbSalir.setName("Salir");
			jbSalir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
			jbSalir.setInputMap(0, map);
			jbSalir.setFont(Utils.FuenteBotonesGrandes());
			jbSalir.setMnemonic('S');
		}
		return jbSalir;
	}
	
	private JPanel getJPDeudas() {
		if (jpDeudas == null) {
			jpDeudas = new TransparentPanel();
			jpDeudas.setLayout(null);
			jpDeudas.setBounds(new Rectangle(15,15,970,470));
			jpDeudas.setBorder(Utils.crearTituloYBorde(" ESTADO DE CUENTA DE LOS CLIENTES "));
			jpDeudas.add(getJSPDatos(), null);
			jpDeudas.add(getJCheckSelectAll(), null);
		}
		return jpDeudas;
	} 
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(10,30,950,400));
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
					String deuda=getValueAt(row,3).toString();
					String favor=getValueAt(row,2).toString();
					if(deuda.compareTo("")!=0){
						float impRestante= Float.parseFloat(deuda);
						if(impRestante>0){
							returnComp.setBackground(Utils.colorTexto);
							returnComp.setForeground(Color.RED);
						}
					}else{
						if(favor.compareTo("")!=0){
							float impRestante= Float.parseFloat(favor);
							if(impRestante>0){
								returnComp.setBackground(Utils.colorTexto);
								returnComp.setForeground(Color.BLUE);
							}else{
								returnComp.setBackground(Utils.colorTexto);
								returnComp.setForeground(Color.BLACK);
							}
						}else{
							returnComp.setBackground(Utils.colorTexto);
							returnComp.setForeground(Color.BLACK);
						}
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
			
			TableColumn columna3 = jtDatos.getColumn("Fecha Ultima Facturación");
			columna3.setMaxWidth(250); 
			columna3.setPreferredWidth(250);
			columna3.setCellRenderer(Utils.alinearCentro());
			TableColumn columna4 = jtDatos.getColumn("Saldo a Favor");
			columna4.setMaxWidth(150); 
			columna4.setPreferredWidth(150);
			columna4.setCellRenderer(Utils.alinearDerecha());	
			TableColumn columna5 = jtDatos.getColumn("Adeudado");
			columna5.setPreferredWidth(150);
			columna5.setMaxWidth(150);
			columna5.setCellRenderer(Utils.alinearDerecha());	
		}
		return jtDatos;
	} 
	
	public void setActionListeners(ActionListener lis) {
		jbSalir.addActionListener(lis);
		jbImprimir.addActionListener(lis);
		jCheckSelectAll.addActionListener(lis);
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		jtDatos.getSelectionModel().addListSelectionListener(lis);
	}
	
	public JCheckBox getJCheckSelectAll() {
		if (jCheckSelectAll == null) {
			jCheckSelectAll = new JCheckBox();
			jCheckSelectAll.setBounds(new java.awt.Rectangle(10,436,228,25));
			jCheckSelectAll.setText("SELECCIONAR TODO");
			jCheckSelectAll.setName("SelectAll");
			jCheckSelectAll.setForeground(Utils.colorTexto);
			jCheckSelectAll.setFont(Utils.FuenteBasica());
			jCheckSelectAll.setOpaque(false);
			jCheckSelectAll.setMnemonic('T');
		}
		return jCheckSelectAll;
	}
	
	
	public void actualizarTabla(){
		jpPpal.remove(getJPDeudas());
		jpDeudas = null;
		jtDatos = null;
		modTabla = null;
		jspDatos = null;
		jpPpal.add(getJPDeudas(), null);
	}
	
}