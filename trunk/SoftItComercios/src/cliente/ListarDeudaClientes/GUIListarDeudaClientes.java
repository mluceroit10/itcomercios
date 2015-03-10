package cliente.ListarDeudaClientes;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import cliente.ModeloTabla;

import common.Utils;

public class GUIListarDeudaClientes extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDeudas = null;
	private JButton jbSalir = null;				public JButton jbImprimir;
	public JScrollPane jspDatos = null;
    public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
    public String[] titulos = {"Cliente","Saldo a Favor","Adeudado"};
    public Object[][] datos;
    public Font a = new Font(Utils.tipoLetra,Font.BOLD,18);
	public JLabel leyenda=null;					
	private JCheckBox jCheckSelectAll = null;
	private String nombreLoc="";
	private InputMap map = new InputMap();
	
    public GUIListarDeudaClientes(String loc,JDialog guiPadre) {
    	super(guiPadre);
    	nombreLoc = loc;
    	this.setSize(new java.awt.Dimension(700,525));
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
        	jpDeudas.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, " Estado de Cuenta de los clientes de "+nombreLoc,
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,a, null));
        	jpDeudas.add(getJSPDatos(), null);
        	jpDeudas.setBackground(Utils.colorPanel);
        	jpDeudas.add(getJCheckSelectAll(), null);
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
        	jtDatos = new JTable(modTabla){
				private static final long serialVersionUID = 1L;
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
					Component returnComp = super.prepareRenderer(renderer, row,column);
					String deuda=getValueAt(row,2).toString();
					String favor=getValueAt(row,1).toString();
					if(deuda.compareTo("")!=0){
						float impRestante= Float.parseFloat(deuda);
						if(impRestante>0){
							returnComp.setBackground(Color.WHITE);
							returnComp.setForeground(Color.RED);
							returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
						}
					}else{
						if(favor.compareTo("")!=0){
							float impRestante= Float.parseFloat(favor);
							if(impRestante>0){
								returnComp.setBackground(Color.WHITE);
								returnComp.setForeground(Color.BLUE);
								returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
							}
						}else{
							returnComp.setBackground(Color.WHITE);
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
        	TableColumn columna4 = jtDatos.getColumn("Saldo a Favor");
            columna4.setMaxWidth(150); 
            columna4.setPreferredWidth(150);
            columna4.setCellRenderer(Utils.alinearDerecha());	
            TableColumn columna5 = jtDatos.getColumn("Adeudado");
            columna5.setPreferredWidth(100);
            columna5.setMaxWidth(100);
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