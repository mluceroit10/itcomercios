package prueba;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import cliente.ModeloTabla;

import common.Utils;

public class GUIMostrarLibroIvaP extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpFacturado = null;
	private JButton jbSalir = null;				public JButton jbImprimir;
	public JScrollPane jspDatos = null;
    public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
    public String[] titulos = {"Fecha","Tipo","L","PV","Nº","Cliente","Categ.","CUIT","Neto","Iva","Total"};
    public Object[][] datos;
    public Font a = new Font(Utils.tipoLetra,Font.BOLD,18);
	public JLabel leyenda=null;					
	private int mesLI;
	private int anioLI;
	public Vector localidades= new Vector();
	private JTextField jtfNeto;
	private JTextField jtfIva;
	private JTextField jtfTotal;
	private InputMap map = new InputMap();
	
    public GUIMostrarLibroIvaP(int mes,int anio,JDialog guiPadre) {
    	super(guiPadre);
    	mesLI=mes;
		anioLI=anio;
    	this.setSize(new java.awt.Dimension(750,530));
        this.setTitle("Libro Iva Ventas");
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
            jpPpal.add(getJPFacturado(),null);
            jpPpal.add(getJBImprimir(),null);
            jpPpal.add(getJBSalir(),null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }
 
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new JButton();
        	jbImprimir.setBounds(new java.awt.Rectangle(245,460,100,30));
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
            jbSalir.setBounds(new java.awt.Rectangle(395,460,100,30));
            jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbSalir.setText("Salir");
            jbSalir.setName("Salir");
            jbSalir.setInputMap(0, map);
        }
        return jbSalir;
    }
    
    private JPanel getJPFacturado() {
        if (jpFacturado == null) {
        	jpFacturado = new JPanel();
        	jpFacturado.setLayout(null);
        	jpFacturado.setBounds(new Rectangle(15,15,720,425));
        	jpFacturado.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, " Facturas del Mes "+mesLI+"/"+anioLI,
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,a, null));
        	JLabel totales= new JLabel("TOTALES");
        	totales.setBounds(new Rectangle(150,400,70,22));
        	totales.setHorizontalAlignment(SwingConstants.RIGHT);
        	JLabel neto= new JLabel("NETO:");
        	neto.setBounds(new Rectangle(250,400,50,22));
        	neto.setHorizontalAlignment(SwingConstants.RIGHT);
        	JLabel iva= new JLabel("IVA:");
        	iva.setBounds(new Rectangle(400,400,50,22));
        	iva.setHorizontalAlignment(SwingConstants.RIGHT);
        	JLabel total= new JLabel("TOTAL:");
        	total.setBounds(new Rectangle(560,400,50,22));
        	total.setHorizontalAlignment(SwingConstants.RIGHT);
        	jpFacturado.add(totales,null);
        	jpFacturado.add(neto,null);
        	jpFacturado.add(iva,null);
        	jpFacturado.add(total,null);
        	jpFacturado.add(getJTFNeto(),null);
        	jpFacturado.add(getJTFIva(),null);
        	jpFacturado.add(getJTFTotal(),null);
        	jpFacturado.add(getJSPDatos(), null);
        	jpFacturado.setBackground(Utils.colorPanel);
        	
        }
        return jpFacturado;
    } 
    
    public JTextField getJTFNeto() {
    	if (jtfNeto == null) {
    		jtfNeto = new JTextField();
    		jtfNeto.setBounds(new Rectangle(305,398,90,20));
    		jtfNeto.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNeto.setEnabled(false);
    	}
    	return jtfNeto;
    }
    
    public JTextField getJTFIva() {
    	if (jtfIva == null) {
    		jtfIva = new JTextField();
    		jtfIva.setBounds(new Rectangle(455,398,90,20));
    		jtfIva.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfIva.setEnabled(false);
    	}
    	return jtfIva;
    }
    
    public JTextField getJTFTotal() {
    	if (jtfTotal == null) {
    		jtfTotal = new JTextField();
    		jtfTotal.setBounds(new Rectangle(615,398,90,20));
    		jtfTotal.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfTotal.setEnabled(false);
    	}
    	return jtfTotal;
    }
    
    
    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,30,700,360));
                jspDatos.setViewportView(getJTDatos());
        }
        return jspDatos;
    }

    public JTable getJTDatos() {
        if (jtDatos == null) {
        	modTabla = new ModeloTabla(titulos, datos);
        	jtDatos = new JTable(modTabla); 
        	TableColumn columna0 = jtDatos.getColumn("Fecha");
            columna0.setPreferredWidth(72);
			columna0.setMaxWidth(72);
			columna0.setCellRenderer( Utils.alinearCentro());
			TableColumn columna1 = jtDatos.getColumn("Tipo");
            columna1.setPreferredWidth(40);
			columna1.setMaxWidth(40);
			columna1.setCellRenderer( Utils.alinearCentro());
			TableColumn columna2 = jtDatos.getColumn("L");
            columna2.setPreferredWidth(20);
			columna2.setMaxWidth(20);
			columna2.setCellRenderer( Utils.alinearCentro());
			TableColumn columna3 = jtDatos.getColumn("PV");
            columna3.setPreferredWidth(40);
			columna3.setMaxWidth(40);
			columna3.setCellRenderer( Utils.alinearCentro());
			TableColumn columna4 = jtDatos.getColumn("Nº");
            columna4.setPreferredWidth(62);
			columna4.setMaxWidth(62);
			columna4.setCellRenderer( Utils.alinearCentro());
			TableColumn columna7 = jtDatos.getColumn("CUIT");
            columna7.setPreferredWidth(90);
			columna7.setMaxWidth(90);
			columna7.setCellRenderer( Utils.alinearCentro());
			TableColumn columna8 = jtDatos.getColumn("Neto");
            columna8.setCellRenderer( Utils.alinearDerecha());
			TableColumn columna9 = jtDatos.getColumn("Iva");
            columna9.setCellRenderer( Utils.alinearDerecha());
			TableColumn columna10 = jtDatos.getColumn("Total");
            columna10.setCellRenderer( Utils.alinearDerecha());
        }
        return jtDatos;
    } 
    
    public void setActionListeners(ActionListener lis) {
        jbSalir.addActionListener(lis);
        jbImprimir.addActionListener(lis);
    }
	     
    public void actualizarTabla(){
        jpPpal.remove(getJPFacturado());
        jpFacturado = null;
        jtDatos = null;
        modTabla = null;
        jspDatos = null;
        jpPpal.add(getJPFacturado(), null);
    }
}