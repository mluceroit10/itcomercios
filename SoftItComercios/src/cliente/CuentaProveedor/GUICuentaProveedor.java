package cliente.CuentaProveedor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

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

public class GUICuentaProveedor extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpCompromisos = null;
	private JButton jbSalir = null;
	public JScrollPane jspDatos = null;
    public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
    public String[] titulos = {"Detalle","Fecha","Debe","Haber","Saldo Factura","Saldo Total"};
    public Object[][] datos;
    public Font a = new Font(Utils.tipoLetra,Font.BOLD,18);
	public JButton jbImprimir;
	private Vector detalleItM= new Vector();
	private Vector fechaM = new Vector();
	private Vector debeM= new Vector();
	private Vector haberM= new Vector();
	private Vector saldoF= new Vector();
	private Vector saldoT= new Vector();
	public JLabel leyenda=null;
	private JCheckBox jCheckSelectAll = null;
	private InputMap map = new InputMap();
    
    public GUICuentaProveedor(Vector detalleIt, Vector fecha2, Vector debe, Vector haber, Vector saldof, Vector saldot, String cliente,JDialog guiPadre) {
    	super(guiPadre);
    	detalleItM=detalleIt;
    	fechaM=fecha2;
    	debeM=debe;
    	haberM=haber;
    	saldoF=saldof;
    	saldoT=saldot;
        this.setSize(new java.awt.Dimension(1000,600));
        this.setTitle("Estado de Cuenta con el Proveedor: "+cliente);
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
            jpPpal.add(getJPCompromisos(),null);
            jpPpal.add(getJBImprimir(),null);
            jpPpal.add(getJBSalir(),null);
            
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
        }
        return jbSalir;
    } 
    
    private JPanel getJPCompromisos() {
        if (jpCompromisos == null) {
        	jpCompromisos = new TransparentPanel();
        	jpCompromisos.setLayout(null);
        	jpCompromisos.setBounds(new Rectangle(15,15,970,470));
        	jpCompromisos.setBorder(Utils.crearTituloYBorde(" MOVIMIENTOS "));
        	leyenda= new JLabel();
        	leyenda.setBounds(new java.awt.Rectangle(430,441,527,20));
        	leyenda.setForeground(Utils.colorTexto);
        	leyenda.setFont(Utils.FuenteBasicaMini());
        	leyenda.setText("ANTE UN IMPORTE NEGATIVO SE REGISTRA DEUDA CON EL PROVEEDOR");
        	jpCompromisos.add(leyenda, null);
        	jpCompromisos.add(getJSPDatos(), null);
        	jpCompromisos.add(getJCheckSelectAll(), null);
        }
        return jpCompromisos;
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
        	datos= new Object[detalleItM.size()][titulos.length];
        	for(int i=0; i<detalleItM.size();i++){
       	 		Object[] temp = {detalleItM.elementAt(i),fechaM.elementAt(i),
       	 				debeM.elementAt(i),haberM.elementAt(i),saldoF.elementAt(i),saldoT.elementAt(i)};
       	 			datos[i] = temp;
        	}		
       	    modTabla = new ModeloTabla(titulos, datos);
            jtDatos = new JTable(modTabla){
				private static final long serialVersionUID = 1L;
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
					Component returnComp = super.prepareRenderer(renderer, row,column);
					String detalle=getValueAt(row,0).toString();
					if(detalle.compareTo("SALDO ACTUAL")==0){
						float impRestante= Float.parseFloat(getValueAt(row,5).toString());
						if(impRestante<0){
							returnComp.setBackground(Utils.colorTexto);
							returnComp.setForeground(Color.RED);
						}else{
							returnComp.setBackground(Utils.colorTexto);
							returnComp.setForeground(Color.BLUE);
						}
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
			TableColumn columna1 = jtDatos.getColumn("Fecha");
			columna1.setPreferredWidth(100);
			columna1.setMaxWidth(100);
			columna1.setCellRenderer(Utils.alinearCentro());
			TableColumn columna2 = jtDatos.getColumn("Debe");
			columna2.setPreferredWidth(100);
			columna2.setMaxWidth(100);
			columna2.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna3 = jtDatos.getColumn("Haber");
			columna3.setPreferredWidth(100);
			columna3.setMaxWidth(100);
			columna3.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna4 = jtDatos.getColumn("Saldo Factura");
			columna4.setPreferredWidth(140);
			columna4.setMaxWidth(140);
			columna4.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna5 = jtDatos.getColumn("Saldo Total");
			columna5.setPreferredWidth(140);
			columna5.setMaxWidth(140);
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
			jCheckSelectAll.setBounds(new java.awt.Rectangle(10,443,228,17));
			jCheckSelectAll.setText("SELECCIONAR TODO");
			jCheckSelectAll.setBackground(Utils.colorTexto);
			jCheckSelectAll.setName("SelectAll");
			jCheckSelectAll.setForeground(Utils.colorTexto);
			jCheckSelectAll.setFont(Utils.FuenteBasica());
			jCheckSelectAll.setOpaque(false);
		}
		return jCheckSelectAll;
	}
}