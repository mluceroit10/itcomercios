package cliente.LibroIva;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import cliente.LimitadorNroMax;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIBuscarLibroIva extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDatos = null;
	private JButton jbAceptar = null;		    private JButton jbCancelar = null;
	private JLabel jlMes = null;				private JLabel jlAnio = null;
	public JScrollPane jspDatos = null;
	private JTextField jtfAnio;
	private JComboBox jcbMes;
	Date hoy=new Date();
	private InputMap map = new InputMap();
	
	public GUIBuscarLibroIva(JFrame guiPadre){
		super(guiPadre);
		this.setSize(new java.awt.Dimension(346,205));
		this.setTitle("Libro Iva");
		this.setResizable(false);
		this.setLocationRelativeTo(guiPadre);
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	private JPanel getJPPpal() {
		if (jpPpal == null) {
			jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
			jpPpal.setLayout(null);
			jpPpal.add(getJPDatos(),null);
			jpPpal.add(getJBCancelar(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	private JPanel getJPDatos() {
		if (jpDatos == null) {
			jlMes = new JLabel("MES:");
			jlMes.setForeground(Utils.colorTexto);
			jlMes.setBounds(new Rectangle(20,25,50,22));
			jlMes.setHorizontalAlignment(SwingConstants.RIGHT);
			jlAnio = new JLabel("A�O:");
			jlAnio.setForeground(Utils.colorTexto);
			jlAnio.setBounds(new Rectangle(155,25,40,22));
			jlAnio.setHorizontalAlignment(SwingConstants.RIGHT);
			jpDatos = new TransparentPanel();
			jpDatos.setLayout(null);
			jpDatos.setSize(new java.awt.Dimension(300,130));
			jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "CALENDARIO", 
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
			jpDatos.setBounds(new Rectangle(15,15,310,100));
			jpDatos.add(jlMes, null);
			jpDatos.add(jlAnio, null);
			jpDatos.add(getJCBMes(),null);
			jpDatos.add(getJTFAnio(), null);
			jpDatos.add(getJBAceptar(), null);
			
		}
		return jpDatos;
	}
	
	public JButton getJBAceptar() {
		if (jbAceptar == null) {
			jbAceptar = new GlossyButton("VERIFICAR FACTURAS",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
			jbAceptar.setBounds(new java.awt.Rectangle(40,62,230,22));
			jbAceptar.setName("Verificar Facturas");
			jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/verificar.png")));
			jbAceptar.setInputMap(0, map);
		}
		return jbAceptar;
	}
		
	public JButton getJBCancelar() {
		if (jbCancelar == null) {
			jbCancelar = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
			jbCancelar.setBounds(new java.awt.Rectangle(113,135,100,30));
			jbCancelar.setName("Salir");
			jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
			jbCancelar.setInputMap(0, map);
		}
		return jbCancelar;
	}
	
	public JComboBox getJCBMes() {
		if (jcbMes == null) {
			jcbMes = new JComboBox();
			jcbMes.setBounds(new java.awt.Rectangle(75,25,60,22));
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
			jcbMes.setSelectedIndex(Utils.getMes(hoy)-1);
		}
		return jcbMes;
	}
	
	public JTextField getJTFAnio() {
		if (jtfAnio == null) {
			jtfAnio = new JTextField();
			jtfAnio.setBounds(new java.awt.Rectangle(200,25,70,22));
			jtfAnio.setDocument(new LimitadorNroMax(jtfAnio,4));
			jtfAnio.setText(String.valueOf(Utils.getAnio(hoy)));
		}
		return jtfAnio;
	}
	
	public void setActionListeners(ActionListener lis) {
		jbAceptar.addActionListener(lis);
		jbCancelar.addActionListener(lis);
	}
	
	
}