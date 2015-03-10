package prueba;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import cliente.LimitadorNroMax;
import cliente.ModeloTabla;

import common.Utils;

public class GUIPrueba  extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpNorte = null;			private JPanel jpSur = null;
	private JPanel jpPpal = null;	    	private JPanel jpGestion = null;
    private JPanel jpDatos = null;	    	private JPanel jpBuscador = null;
    private JButton jbCargar = null;    	private JButton jbModif = null;
    private JButton jbAceptar = null;   	private JButton jbBorrar = null;
    private JButton jbCancelar = null;		private JButton jbImprimir = null;
    private JLabel jlNombre = null;			private JLabel jlCodigo = null;
    private JTextField jtfNombre = null;	private JTextField jtfCodigo = null;
    public JScrollPane jspDatos = null;
    public JTable jtDatos = null;			private ModeloTabla modTabla = null;
    public String[] titulos = {"ID","Código","Nombre","Pr Kg?","Stock Actual","Stock Mínimo","Stock Actual Kg","Stock Mínimo Kg", "Precio Entrada","Margen Ganancia","Precio Venta Sin IVA","Precio Venta Con IVA","Proveedor"};
    public Object[][] datos;
    private InputMap map = new InputMap();
    
    public GUIPrueba(JFrame guiPadre) {
    	super(guiPadre);
    	datos = new Object[0][titulos.length];
    	this.setSize(Utils.anchoMaxVent,Utils.largoMaxVent);//this.setSize(740,540);
        this.setLocationRelativeTo(guiPadre);  
    //    this.setResizable(false); omitir
        this.setTitle("Gestión de Productos");
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal = new JPanel();
            jpPpal.setLayout(new BorderLayout());
            jpPpal.add(getJPNorte(), BorderLayout.NORTH);
            jpPpal.add(getJPSur(),BorderLayout.SOUTH);
            jpPpal.setBackground(Utils.colorFondo);
            jpPpal.add(getJPDatos(), BorderLayout.CENTER);
        }
        return jpPpal;
    }
    
    public JPanel getJPNorte() {
        if (jpNorte == null) {
        	jpNorte = new JPanel();
        	jpNorte.setPreferredSize(new Dimension(740,80));
        	GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
        	gridBagConstraints00.fill = GridBagConstraints.BOTH;
        	gridBagConstraints00.weightx = 1.0;
        	gridBagConstraints00.gridx = 0;
            gridBagConstraints00.anchor = GridBagConstraints.WEST;
            gridBagConstraints00.gridy = 0;
            
            gridBagConstraints00.insets = new Insets(0,0, 0, 10);
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.fill = GridBagConstraints.BOTH;
            gridBagConstraints01.weightx = 1.0;
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.anchor = GridBagConstraints.EAST;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(0,0, 0, 0);
        	jpNorte.setLayout(new GridBagLayout());
        	jpNorte.add(getJPGestion(),gridBagConstraints00);
        	jpNorte.add(getJPBuscador(), gridBagConstraints01);
        //	jpNorte.setBackground(Utils.colorPanel);
        }
        return jpNorte;
    }
    
    public JPanel getJPSur() {
        if (jpSur == null) {
        	jpSur = new JPanel();
        	jpSur.setPreferredSize(new Dimension(740,60));
        	GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
            gridBagConstraints00.gridx = 0;
            gridBagConstraints00.gridy = 0;
            gridBagConstraints00.insets = new Insets(0,0, 0, 40);
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(0, 0, 0, 0);
            jpSur.setLayout(new GridBagLayout());
            jpSur.add(getJBAceptar(),gridBagConstraints00);
            jpSur.add(getJBCancelar(), gridBagConstraints01);
            jpSur.setBackground(Utils.colorFondo);
        }
        return jpSur;
    }

    private JPanel getJPBuscador() {
    	if (jpBuscador == null) {
            jpBuscador = new JPanel();
            GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
            gridBagConstraints00.fill = GridBagConstraints.BOTH;
        	gridBagConstraints00.weightx = 1.0;
            gridBagConstraints00.gridx = 0;
            gridBagConstraints00.gridy = 0;
            gridBagConstraints00.insets = new Insets(5,5, 8, 5);
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.fill = GridBagConstraints.BOTH;
        	gridBagConstraints01.weightx = 1.0;
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(5,0, 8, 20);
            GridBagConstraints gridBagConstraints02 = new GridBagConstraints();
            gridBagConstraints02.fill = GridBagConstraints.BOTH;
        	gridBagConstraints02.weightx = 1.0;
            gridBagConstraints02.gridx = 2;
            gridBagConstraints02.gridy = 0;
            gridBagConstraints02.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints03 = new GridBagConstraints();
            gridBagConstraints03.fill = GridBagConstraints.BOTH;
        	gridBagConstraints03.weightx = 1.0;
            gridBagConstraints03.gridx = 3;
            gridBagConstraints03.gridy = 0;
            gridBagConstraints03.insets = new Insets(5,0, 8, 0);
            jlCodigo = new JLabel();
            jlCodigo.setPreferredSize(new Dimension(50,15));//.setBounds(new Rectangle(15,30,60,15));
            jlCodigo.setText("Código:");
            jlCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
            jlNombre = new JLabel();
            jlNombre.setPreferredSize(new Dimension(60,15));//.setBounds(new Rectangle(130,30,60,15));
            jlNombre.setText("Nombre:");
            jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
            jpBuscador.setLayout(new GridBagLayout());
            jpBuscador.setSize(new Dimension(300,65));//.setBounds(new java.awt.Rectangle(415,25,300,65));
            jpBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "Buscar",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
            
            jpBuscador.add(jlCodigo, gridBagConstraints00);
            jpBuscador.add(getJTFBuscadorCodigo(), gridBagConstraints01);
            jpBuscador.add(jlNombre, gridBagConstraints02);
            jpBuscador.add(getJTFBuscadorNombre(), gridBagConstraints03);
            jpBuscador.setBackground(Utils.colorPanel);
        }
        return jpBuscador;
    }

    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new JPanel();
            jpDatos.setSize(new Dimension(700,340));
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(0,5, 5, 5);
            jpDatos.setLayout(new GridBagLayout());
            jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "Listado de Productos",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
            jpDatos.add(getJSPDatos(), gridBagConstraints);
            jpDatos.setBackground(Utils.colorPanel);
        }
        return jpDatos;
    }

    private JPanel getJPGestion() {
        if (jpGestion == null) {
            jpGestion = new JPanel();
            jpGestion.setLayout(new GridBagLayout());//new FlowLayout());
            GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
            gridBagConstraints00.fill = GridBagConstraints.BOTH;
        	gridBagConstraints00.weightx = 1.0;
            gridBagConstraints00.gridx = 0;
            gridBagConstraints00.gridy = 0;
            gridBagConstraints00.insets = new Insets(5,5, 8, 5);
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.fill = GridBagConstraints.BOTH;
        	gridBagConstraints01.weightx = 1.0;
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints02 = new GridBagConstraints();
            gridBagConstraints02.fill = GridBagConstraints.BOTH;
        	gridBagConstraints02.weightx = 1.0;
            gridBagConstraints02.gridx = 2;
            gridBagConstraints02.gridy = 0;
            gridBagConstraints02.insets = new Insets(5,0, 8, 5);
            GridBagConstraints gridBagConstraints03 = new GridBagConstraints();
            gridBagConstraints03.fill = GridBagConstraints.BOTH;
        	gridBagConstraints03.weightx = 1.0;
            gridBagConstraints03.gridx = 3;
            gridBagConstraints03.gridy = 0;
            gridBagConstraints03.insets = new Insets(5,0, 8, 0);
            jpGestion.setSize(new Dimension(380,65));// jpGestion.setBounds(new Rectangle(10,25,380,65));
            jpGestion.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b,
                    "Gestión", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
            jpGestion.add(getJBCargar(),gridBagConstraints00);
            jpGestion.add(getJBMod(),gridBagConstraints01);
            jpGestion.add(getJBBorrar(),gridBagConstraints02);
            jpGestion.add(getJBImprimir(),gridBagConstraints03);
            jpGestion.setBackground(Utils.colorPanel);
        }
        return jpGestion;
    }

    public JButton getJBCargar() {
        if (jbCargar == null) {
            jbCargar = new JButton();
            jbCargar.setText("Ingresar");
            jbCargar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCargar.setPreferredSize(new Dimension(80,25));//.setBounds(new Rectangle(15,25,80,25));
            jbCargar.setInputMap(0, map);
        }
        return jbCargar;
    }

    public JButton getJBMod() {
        if (jbModif == null) {
            jbModif = new JButton();
            jbModif.setText("Modificar");
            jbModif.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbModif.setPreferredSize(new Dimension(80,25));//.setBounds(new Rectangle(105,25,80,25));
            jbModif.setInputMap(0, map);
        }
        return jbModif;
    }

    public JButton getJBBorrar() {
        if (jbBorrar == null) {
            jbBorrar = new JButton();
            jbBorrar.setText("Eliminar");
            jbBorrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBorrar.setPreferredSize(new Dimension(80,25));//.setBounds(new Rectangle(195,25,80,25));
            jbBorrar.setInputMap(0, map);
        }
        return jbBorrar;
    }
    
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new JButton();
        	jbImprimir.setName("Imprimir");
        	jbImprimir.setText("Imprimir");
        	jbImprimir.setPreferredSize(new Dimension(80,25));//.setBounds(new java.awt.Rectangle(285,25,80,25));
        	jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprimir.setInputMap(0, map);
        }
        return jbImprimir;
    }

    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new JButton();
            jbAceptar.setName("Aceptar");
            jbAceptar.setText("Aceptar");
            jbAceptar.setPreferredSize(new Dimension(100,30));//jbAceptar.setBounds(new Rectangle(240,470,100,30));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }

    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new JButton();
            jbCancelar.setName("Cancelar");
            jbCancelar.setText("Cancelar");
            jbCancelar.setPreferredSize(new Dimension(100,30));// jbCancelar.setBounds(new Rectangle(390,470,100,30));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setInputMap(0, map);
        }
        return jbCancelar;
    }

    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setSize(new Dimension(680,310));
              //  jspDatos.setBounds(new Rectangle(10,20,680,310));
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
					String stAct=getValueAt(row,4).toString();
					String stMin=getValueAt(row,5).toString();
					if(Integer.parseInt(stAct)<Integer.parseInt(stMin)){
						returnComp.setBackground(Color.YELLOW);
						returnComp.setForeground(Color.BLACK);
					}else{
						returnComp.setBackground(Color.WHITE);
						returnComp.setForeground(Color.BLACK);
					}
					int[] seleccion=this.getSelectedRows();
					for(int j=0;j<seleccion.length;j++){
						if(seleccion[j]==row){
							returnComp.setBackground(new Color(176,196,222));
							returnComp.setForeground(Color.BLACK);
						}
					}
					if(column==11){
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
					}
					return returnComp;
				}
			};
		    Utils.ocultarColumnaId(jtDatos);
            TableColumn columna0 = jtDatos.getColumn("Código");
			columna0.setPreferredWidth(70);
			columna0.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna2 = jtDatos.getColumn("Pr Kg?");
            columna2.setPreferredWidth(50); 
            columna2.setMaxWidth(50);
            columna2.setCellRenderer(Utils.alinearCentro());
            TableColumn columna3 = jtDatos.getColumn("Stock Actual");
            columna3.setPreferredWidth(70);
            columna3.setMaxWidth(70);
            columna3.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna4 = jtDatos.getColumn("Stock Mínimo");
            columna4.setPreferredWidth(80);
            columna4.setMaxWidth(80);
            columna4.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna5 = jtDatos.getColumn("Precio Entrada");
            columna5.setPreferredWidth(80);
            columna5.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna6 = jtDatos.getColumn("Precio Venta Sin IVA");
            columna6.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna7 = jtDatos.getColumn("Precio Venta Con IVA");
            columna7.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna8 = jtDatos.getColumn("Margen Ganancia");
            columna8.setCellRenderer(Utils.alinearDerecha());
        }
        return jtDatos;
    }

    public JTextField getJTFBuscadorCodigo() {
        if (jtfCodigo == null) {
        	jtfCodigo = new JTextField();
        	jtfCodigo.setPreferredSize(new Dimension(50, 22));//setBounds(new Rectangle(65,30,50,22));
        	jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo,6));
        }
        return jtfCodigo;
    }
    
    public JTextField getJTFBuscadorNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setPreferredSize(new Dimension(100, 22));//.setBounds(new Rectangle(190,30,100,22));
        }
        return jtfNombre;
    }
    
    public void setActionListeners(ActionListener lis) {
        jbAceptar.addActionListener(lis);
        jbModif.addActionListener(lis);
        jbCargar.addActionListener(lis);
        jbImprimir.addActionListener(lis);
        jbBorrar.addActionListener(lis);
        jbCancelar.addActionListener(lis);
    }

    public void setKeyListener(KeyListener lis) {
        jtfNombre.addKeyListener(lis);
        jtfCodigo.addKeyListener(lis);
    }

    public void setListSelectionListener(ListSelectionListener lis) {
        jtDatos.getSelectionModel().addListSelectionListener(lis);
    }

    public void actualizarTabla(){
        jpPpal.remove(getJPDatos());
        jpDatos = null;
        jtDatos = null;
        modTabla = null;
        jspDatos = null;
        jpPpal.add(getJPDatos(), null);
    }
}