package cliente.ListarDeudaClientes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import common.Utils;

public class GUIFiltroLocalidadDeudaClientes extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				
	private JPanel jpBuscador=null;
	private JButton jbSalir = null;				public JButton jbContinuar;
	public JScrollPane jspDatos = null;
    public JTable jtDatos = null;			    
    public Object[][] datos;
    public Font a = new Font(Utils.tipoLetra,Font.BOLD,18);
	public JLabel leyenda=null;					private JLabel jlNombre=null;
	public Vector localidades= new Vector();
	private JComboBox JCLocalidades=null;
	private InputMap map = new InputMap();
	
    public GUIFiltroLocalidadDeudaClientes(JFrame guiPadre) {
    	super(guiPadre);
    	this.setSize(new java.awt.Dimension(340,215));
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
            jpPpal = new JPanel();
            jpPpal.setLayout(null);
            jpPpal.add(getJPBuscador(),null);
            jpPpal.add(getJBSalir(),null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }
    
    private JPanel getJPBuscador() {
        if (jpBuscador == null) {
            jpBuscador = new JPanel();
            jlNombre = new JLabel();
            jlNombre.setBounds(new Rectangle(10,30,70,15));
            jlNombre.setText("Localidad:");
            jpBuscador.setLayout(null);
            jpBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "Buscar",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
            jpBuscador.setBounds(new java.awt.Rectangle(15,15,300,110));
            jpBuscador.add(jlNombre, null);
            jpBuscador.setBackground(Utils.colorPanel);
            jpBuscador.add(getJBImprimir(),null);
        }
        return jpBuscador;
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
			JCLocalidades.setBounds(new java.awt.Rectangle(80,30,180,22));
		}
		return JCLocalidades;
	}
    
    public void actualizarBusqueda(){
    	jpBuscador.add(getJCLocalidades(), null);
	}
    
    public JButton getJBImprimir() {
        if (jbContinuar == null) {
        	jbContinuar = new JButton();
        	jbContinuar.setBounds(new java.awt.Rectangle(50,70,200,22));
        	jbContinuar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbContinuar.setText("Verificar Clientes");
        	jbContinuar.setName("Imprimir");
        	jbContinuar.setInputMap(0, map);
        }
        return jbContinuar;
    }
    
    public JButton getJBSalir() {
        if (jbSalir == null) {
            jbSalir = new JButton();
            jbSalir.setBounds(new java.awt.Rectangle(115,145,100,30));
            jbSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbSalir.setText("Salir");
            jbSalir.setName("Salir");
            jbSalir.setInputMap(0, map);
        }
        return jbSalir;
    }
        
    public void setActionListeners(ActionListener lis) {
        jbSalir.addActionListener(lis);
        jbContinuar.addActionListener(lis);
    }
	
}