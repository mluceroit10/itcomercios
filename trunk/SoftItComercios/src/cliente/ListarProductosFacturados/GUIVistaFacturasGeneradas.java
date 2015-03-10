package cliente.ListarProductosFacturados;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

import cliente.ModeloTabla;

import common.Utils;

public class GUIVistaFacturasGeneradas extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDeudas = null;
	private JButton jbSalir = null;				public JButton jbImprimir;
	public JScrollPane jspDatos = null;
    public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
    public String[] titulos = {"IdF","Tipo Factura","Factura Nro","Cliente"};
    public Object[][] datos;
    public Font a = new Font(Utils.tipoLetra,Font.BOLD,18);
	public JLabel leyenda=null;
	private JCheckBox jCheckSelectAll = null;
	private String fecha; 
	private String nombreLoc;
	private InputMap map = new InputMap();
	
    public GUIVistaFacturasGeneradas(String fe,String loc,JDialog guiPadre) {
    	super(guiPadre);
    	fecha=fe;
    	nombreLoc=loc;
    	this.setSize(new java.awt.Dimension(700,525));
        this.setTitle("Obtener listado de Productos de Facturas");
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
    
    private JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal = new JPanel();
            jpPpal.setLayout(null);
            jpPpal.add(getJPDeudas(),null);
            jpPpal.add(getJBImprimir(),null);
            jpPpal.add(getJBSalir(),null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }
    
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new JButton();
        	jbImprimir.setBounds(new java.awt.Rectangle(220,455,100,30));
        	jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprimir.setText("Imprimir");
        	jbImprimir.setName("Imprimir");
        	jbImprimir.setInputMap(0, map);
        }
        return jbImprimir;
    }
    
    public JButton getJBSalir() {
        if (jbSalir == null) {
            jbSalir = new JButton();
            jbSalir.setBounds(new java.awt.Rectangle(370,455,100,30));
            jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbSalir.setText("Salir");
            jbSalir.setName("Salir");
            jbSalir.setInputMap(0, map);
        }
        return jbSalir;
    }
    
    private JPanel getJPDeudas() {
        if (jpDeudas == null) {
        	jpDeudas = new JPanel();
        	jpDeudas.setLayout(null);
        	jpDeudas.setBounds(new Rectangle(15,15,670,420));
        	jpDeudas.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, " Facturas de "+nombreLoc+" Dia "+fecha,
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,a, null));
        	jpDeudas.add(getJSPDatos(), null);
        	jpDeudas.setBackground(Utils.colorPanel);
        	jpDeudas.add(getJCheckSelectAll(), null);
        	leyenda=new JLabel("Las \"Facturas de Remito\" no serán mostradas.");
        	leyenda.setBounds(new Rectangle(200,393,300,17));
        	jpDeudas.add(leyenda, null);
        }
        return jpDeudas;
    } 
    
    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,30,650,350));
                jspDatos.setViewportView(getJTDatos());
        }
        return jspDatos;
    }

    public JTable getJTDatos() {
        if (jtDatos == null) {
        	modTabla = new ModeloTabla(titulos, datos);
        	jtDatos = new JTable(modTabla); 
        	jtDatos.getColumnModel().getColumn(0).setMaxWidth(0);
        	jtDatos.getColumnModel().getColumn(0).setMinWidth(0);
        	jtDatos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        	jtDatos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
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
			jCheckSelectAll.setBounds(new java.awt.Rectangle(10,393,130,17));
			jCheckSelectAll.setText("Seleccionar Todo");
			jCheckSelectAll.setName("SelectAll");
			jCheckSelectAll.setBackground(Utils.colorPanel);
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