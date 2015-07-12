package cliente.LibroIva;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import cliente.ModeloTabla;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIMostrarLibroIva extends JDialog {
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
	
    public GUIMostrarLibroIva(int mes,int anio,JDialog guiPadre) {
    	super(guiPadre);
    	mesLI=mes;
		anioLI=anio;
    	this.setSize(new java.awt.Dimension(1200,640));
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
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.setSize(new java.awt.Dimension(1200,640));
            jpPpal.add(getJPFacturado(),null);
            jpPpal.add(getJBImprimir(),null);
            jpPpal.add(getJBSalir(),null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }
 
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbImprimir.setBounds(new java.awt.Rectangle(350,560,200,40));
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
            jbSalir.setBounds(new java.awt.Rectangle(650,560,200,40));
            jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbSalir.setName("Salir");
            jbSalir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
            jbSalir.setInputMap(0, map);
            jbSalir.setFont(Utils.FuenteBotonesGrandes());
            jbSalir.setMnemonic('S');
        }
        return jbSalir;
    }
    
    private JPanel getJPFacturado() {
        if (jpFacturado == null) {
        	jpFacturado = new TransparentPanel();
        	jpFacturado.setLayout(null);
        	jpFacturado.setBounds(new Rectangle(15,15,1170,510));
        	jpFacturado.setBorder(Utils.crearTituloYBorde(" FACTURAS DEL MES "+mesLI+"/"+anioLI));
        	JLabel totales= new JLabel("TOTALES");
        	totales.setForeground(Utils.colorTexto);
        	totales.setFont(Utils.FuenteBasica());
        	totales.setBounds(new Rectangle(250,475,100,26));
        	totales.setHorizontalAlignment(SwingConstants.RIGHT);
        	JLabel neto= new JLabel("NETO:");
        	neto.setForeground(Utils.colorTexto);
        	neto.setFont(Utils.FuenteBasica());
        	neto.setBounds(new Rectangle(360,475,100,26));
        	neto.setHorizontalAlignment(SwingConstants.RIGHT);
        	JLabel iva= new JLabel("IVA:");
        	iva.setForeground(Utils.colorTexto);
        	iva.setFont(Utils.FuenteBasica());
        	iva.setBounds(new Rectangle(630,475,100,26));
        	iva.setHorizontalAlignment(SwingConstants.RIGHT);
        	JLabel total= new JLabel("TOTAL:");
        	total.setForeground(Utils.colorTexto);
        	total.setFont(Utils.FuenteBasica());
        	total.setBounds(new Rectangle(900,475,100,26));
        	total.setHorizontalAlignment(SwingConstants.RIGHT);
        	jpFacturado.add(totales,null);
        	jpFacturado.add(neto,null);
        	jpFacturado.add(iva,null);
        	jpFacturado.add(total,null);
        	jpFacturado.add(getJTFNeto(),null);
        	jpFacturado.add(getJTFIva(),null);
        	jpFacturado.add(getJTFTotal(),null);
        	jpFacturado.add(getJSPDatos(), null);
        }
        return jpFacturado;
    } 
    
    public JTextField getJTFNeto() {
    	if (jtfNeto == null) {
    		jtfNeto = new JTextField();
    		jtfNeto.setBounds(new Rectangle(470,475,150,26));
    		jtfNeto.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNeto.setEnabled(false);
    		jtfNeto.setHorizontalAlignment(SwingConstants.RIGHT);
    		jtfNeto.setFont(Utils.FuenteCampos());
    	}
    	return jtfNeto;
    }
    
    public JTextField getJTFIva() {
    	if (jtfIva == null) {
    		jtfIva = new JTextField();
    		jtfIva.setBounds(new Rectangle(740,475,150,26));
    		jtfIva.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfIva.setEnabled(false);
    		jtfIva.setHorizontalAlignment(SwingConstants.RIGHT);
    		jtfIva.setFont(Utils.FuenteCampos());
    	}
    	return jtfIva;
    }
    
    public JTextField getJTFTotal() {
    	if (jtfTotal == null) {
    		jtfTotal = new JTextField();
    		jtfTotal.setBounds(new Rectangle(1010,475,150,26));
    		jtfTotal.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfTotal.setEnabled(false);
    		jtfTotal.setHorizontalAlignment(SwingConstants.RIGHT);
    		jtfTotal.setFont(Utils.FuenteCampos());
    	}
    	return jtfTotal;
    }
    
    
    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,30,1150,430));
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
        	TableColumn columna0 = jtDatos.getColumn("Fecha");
            columna0.setPreferredWidth(100);
			columna0.setMaxWidth(100);
			columna0.setCellRenderer( Utils.alinearCentro());
			TableColumn columna1 = jtDatos.getColumn("Tipo");
            columna1.setPreferredWidth(60);
			columna1.setMaxWidth(60);
			columna1.setCellRenderer( Utils.alinearCentro());
			TableColumn columna2 = jtDatos.getColumn("L");
            columna2.setPreferredWidth(30);
			columna2.setMaxWidth(30);
			columna2.setCellRenderer( Utils.alinearCentro());
			TableColumn columna3 = jtDatos.getColumn("PV");
            columna3.setPreferredWidth(60);
			columna3.setMaxWidth(60);
			columna3.setCellRenderer( Utils.alinearCentro());
			TableColumn columna4 = jtDatos.getColumn("Nº");
            columna4.setPreferredWidth(100);
			columna4.setMaxWidth(100);
			columna4.setCellRenderer( Utils.alinearCentro());
			TableColumn columna7 = jtDatos.getColumn("CUIT");
            columna7.setPreferredWidth(130);
			columna7.setMaxWidth(130);
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