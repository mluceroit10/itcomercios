package cliente.ListarProductosFacturados;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import com.toedter.calendar.JDateChooser;
import common.Utils;

public class GUIListarProductosFacturados extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDatos = null;
	private JButton jbAceptar = null;		    private JButton jbCancelar = null;
	private JLabel jlDia = null;
	public JScrollPane jspDatos = null;
	private JDateChooser jDateChooserIngreso;
	public Vector localidades= new Vector();
	private JComboBox JCLocalidades=null;
	private JLabel jlNombre=null;
	private InputMap map = new InputMap();
	
	public GUIListarProductosFacturados(JFrame guiPadre){
		super(guiPadre);
		this.setSize(new java.awt.Dimension(346,240));
		this.setTitle("Calendario");
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
			jlDia = new JLabel("Ir a:");
			jlDia.setBounds(new Rectangle(10,25,60,26));
			jlDia.setHorizontalAlignment(SwingConstants.RIGHT);
			jpDatos = new TransparentPanel();
			jpDatos.setLayout(null);
			jpDatos.setSize(new java.awt.Dimension(300,130));
			jpDatos.setBorder(Utils.crearTituloYBorde("CALENDARIO"));
			jpDatos.setBounds(new Rectangle(15,15,310,135));
			jlNombre = new JLabel();
			jlNombre.setBounds(new Rectangle(10,60,60,26));
			jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
			jlNombre.setText("Localidad:");
			jpDatos.add(jlDia, null);
			jpDatos.add(getJDateChooserIngreso(), null);
			jpDatos.add(jlNombre, null);
			jpDatos.add(getJBAceptar(), null);
		}
		return jpDatos;
	}
	
	public JComboBox getJCLocalidades(){
		if (JCLocalidades == null) {
			JCLocalidades = new JComboBox();
			int sizeMax=0; 
			for(int i=0;i<localidades.size();i++){
				String lpago=(String)localidades.elementAt(i);
				if(lpago.length()>sizeMax)sizeMax=lpago.length();
				JCLocalidades.addItem(lpago);
			}
			JCLocalidades.setBackground(new Color(255,255,255));
			JCLocalidades.setForeground(java.awt.Color.black);
			JCLocalidades.setBounds(new java.awt.Rectangle(80,55,200,22));
		}
		return JCLocalidades;
	}

	 public void actualizarBusqueda(){
		 jpDatos.add(getJCLocalidades(), null);
	}
	
	public JButton getJBAceptar() {
		if (jbAceptar == null) {
			jbAceptar = new GlossyButton("VERIFICAR FACTURAS",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
			jbAceptar.setBounds(new java.awt.Rectangle(40,97,230,22));
			jbAceptar.setName("Verificar Facturas");
			jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbAceptar.setInputMap(0, map);
			jbAceptar.setMnemonic('V');
		}
		return jbAceptar;
	}
	
	
	public JButton getJBCancelar() {
		if (jbCancelar == null) {
			jbCancelar = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
			jbCancelar.setBounds(new java.awt.Rectangle(123,170,100,30));
			jbCancelar.setName("Salir");
			jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
			jbCancelar.setInputMap(0, map);
			jbCancelar.setMnemonic('S');
		}
		return jbCancelar;
	}
	
	public JDateChooser getJDateChooserIngreso() {
		if (jDateChooserIngreso == null) {
			jDateChooserIngreso = new JDateChooser("dd - MMMMM - yyyy",false);
			jDateChooserIngreso.setBounds(new java.awt.Rectangle(80,25,200,22));
			jDateChooserIngreso.getSpinner().setFont(Utils.FuenteFechas());
		}
		return jDateChooserIngreso;
	}
	
	public void setActionListeners(ActionListener lis) {
		jbAceptar.addActionListener(lis);
		jbCancelar.addActionListener(lis);
	}
	
	
	
}