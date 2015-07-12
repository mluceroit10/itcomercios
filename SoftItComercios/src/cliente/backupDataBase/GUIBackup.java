package cliente.backupDataBase;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;

import common.RootAndIp;
import common.Utils;

public class GUIBackup extends JDialog{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton JButtonS = null;
	private JLabel icono = null;
	private JTextPane advertencia = null;
	private JTextPane resultado = null;
	private JTextPane jTextPaneBackup = null;
	private RootAndIp rip= new RootAndIp();
	private InputMap map = new InputMap();
	
	public GUIBackup(JFrame guiPadre){
		super(guiPadre);
		this.setSize(1000, 455);
		this.setResizable(false);
		this.setLocationRelativeTo(guiPadre);
		this.setTitle("Backup Base de Datos");
		this.setContentPane(getJContentPane());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			advertencia = new JTextPane();
			advertencia.setText("");
			advertencia.setAlignmentX(CENTER_ALIGNMENT);
			advertencia.setForeground(new Color(0,0,205));
			advertencia.setBounds(new java.awt.Rectangle(30,10,940,40));
			advertencia.setDisabledTextColor(Utils.colorTexto);
			advertencia.setForeground(Utils.colorTexto);
			advertencia.setFont(Utils.FuenteBasica());
			advertencia.setOpaque(false);
			advertencia.setBorder(Utils.b);
			advertencia.setEnabled(false);
			icono= new JLabel();
			icono.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/guardar.png")));
			icono.setBounds(new java.awt.Rectangle(30,140,50,50));
			icono.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
			resultado = new JTextPane();
			resultado.setText("");
			resultado.setForeground(new Color(0,0,205)); 
			resultado.setDisabledTextColor(Utils.colorTexto);
			resultado.setBounds(new java.awt.Rectangle(30,280,940,60));
			resultado.setForeground(Utils.colorTexto);
			resultado.setFont(Utils.FuenteBasica());
			resultado.setOpaque(false);
			resultado.setBorder(Utils.b);
			resultado.setEnabled(false);
			jContentPane = new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
			jContentPane.setLayout(null);
			jContentPane.setBackground(Utils.colorFondo);
			jContentPane.add(advertencia, null);
			jContentPane.add(resultado, null);
			jContentPane.add(icono, null);
			jContentPane.add(getJButtonSalir(), null);
			jContentPane.add(getJTextPaneBackup(), null);
		}
		return jContentPane;
	}

	public JButton getJButtonSalir() {
		if (JButtonS == null) {
			JButtonS = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
			JButtonS.setBounds(new java.awt.Rectangle(350,375,200,40));
			JButtonS.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			JButtonS.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salirv.png")));
			JButtonS.setInputMap(0, map);
			JButtonS.setFont(Utils.FuenteBotonesGrandes());
			JButtonS.setMnemonic('S');
		}
		return JButtonS;
	}

	public void setActionListeners(ActionListener lis){
		this.JButtonS.addActionListener(lis);
	}

	public JTextPane getAdvertencia() {
		return advertencia;
	}

	public JTextPane getResultado() {
		return resultado;
	}

	public JTextPane getJTextPaneBackup() {
		if (jTextPaneBackup == null) {
			jTextPaneBackup = new JTextPane();
			jTextPaneBackup.setBounds(new java.awt.Rectangle(100,60,870,210));
			jTextPaneBackup.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sabia Ud. que...?", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", java.awt.Font.BOLD, 12), java.awt.Color.black));
			//jTextPaneBackup.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			jTextPaneBackup.setText("El Backup es una copia de seguridad de su Base de Datos; es decir al realizar un Backup se crea autom�ticamente un archivo con la informaci�n almacenada en su Base de Datos en el momento de la ejecuci�n. Este archivo le servir� como respaldo, copia o recuerdo de la informaci�n que usted tenga almacenada en el sistema. Pueden realizarse la cantidad de Backups que desee y cada uno de los archivos creados quedar� almacenado en un directorio comun y con un nombre que distingue a cada uno.");
			jTextPaneBackup.setDisabledTextColor(Utils.colorNegro);
			jTextPaneBackup.setEnabled(false);
			jTextPaneBackup.setFont(Utils.FuenteBasica());

		}
		return jTextPaneBackup;
	}

}
