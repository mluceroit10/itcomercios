package prueba;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import common.RootAndIp;
import common.Utils;

public class GUIPrincipalP extends JFrame{

	private static final long serialVersionUID = 1L;
	//PANEL PRINCIPAL
	private JPanel jContentPane = null;  
	private JButton jButtonSalir = null;
	//BARRA MENU
	private JMenuBar menuPrinc=null;
	private JMenu archivo=null;
	private JMenu gestionar=null;
	private JMenu baseDatos=null;
	
	private JMenuItem archivoSalir=null;
	private JMenuItem infoProg=null;
	private JMenuItem gestionarProvincia=null;  
	private JMenuItem gestionarLocalidad=null;
	private JMenuItem backupBD=null;
	private JMenuItem infoComercio=null;
	
	private Border b= javax.swing.BorderFactory.createLineBorder(new Color(104,34,139),2);
	//PANEL ACCESOS RAPIDOS
	private JPanel jPanelAccesosProductos=null;
	private JPanel jPanelAccesosProveedores=null;
	private JPanel jPanelAccesosCliente=null;
	private JPanel jPanelAccesosFactProv=null;
	private JPanel jPanelAccesosContables=null;
	private JPanel jPanelAccesosRemitoCte=null; 
	private JPanel jPanelAccesosFactCte=null;
	private JPanel jPanelAccesosPedidos=null;
	private JPanel jPanelLibroIva=null;
	
	private JButton jBProductos=null;
	private JButton jBControlStock=null;
	private JButton jBGestionProveedores=null;
	private JButton jBGestionClientes=null;
	private JButton jBFacturaCliente=null;
	private JButton jBRemitoCliente=null;
	private JButton jBGestionarMC=null;
	private JButton jBPlanillaES=null;
	private JButton jBTodasFactProv=null;
	private JButton jBTodosRemitosCliente=null;
	private JButton jBFacturaProveedor=null;
	private JButton jBTodasFactCliente=null;
	private JButton jBListadoDeudaClientes=null;
	private JButton jBListadoProdsFacturados=null;
	private JButton jBLibroIva=null;
	Color colorFuenteMenu = new Color(0,0,128);
	private InputMap map = new InputMap();
	private JPanel jpPpal;
	private JPanel jpUlt;
	
	public GUIPrincipalP() throws Exception{
		super();
		this.setSize(new java.awt.Dimension(800,570));
		this.setLocationRelativeTo(null);
	//	this.setResizable(false);
		this.setTitle("Sistema Informático ''itComercios''");
		this.setContentPane(getJPPpal());
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(RootAndIp.getExtras()+"/iconos/logo2.png"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}

	public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal = new JPanel();
            jpPpal.setLayout(new BorderLayout());
            jpPpal.setBackground(Utils.colorFondo);
            jpPpal.add(getJContentPane(), BorderLayout.CENTER);
        }
        return jpPpal;
    }
	
	private JPanel getJContentPane() {
		if(jContentPane==null){
			jContentPane = new JPanel();
			jContentPane.setBackground(Utils.colorFondo);
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.setSize(800,570);
			GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
        	gridBagConstraints00.fill = GridBagConstraints.BOTH;
        	gridBagConstraints00.weightx = 1.0;
        	gridBagConstraints00.gridx = 0;
            gridBagConstraints00.anchor = GridBagConstraints.WEST;
            gridBagConstraints00.gridy = 0;
            gridBagConstraints00.insets = new Insets(0,10, 10, 10);
           
            GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.fill = GridBagConstraints.BOTH;
            gridBagConstraints01.weightx = 1.0;
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.anchor = GridBagConstraints.EAST;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(0,0, 10, 10);
            
            jContentPane.add(getJPanelAccesosContables(), gridBagConstraints00);
			jContentPane.add(getJPanelAccesosFactProveedor(), gridBagConstraints01);
			
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
        	gridBagConstraints10.fill = GridBagConstraints.BOTH;
        	gridBagConstraints10.weightx = 1.0;
        	gridBagConstraints10.gridx = 0;
            gridBagConstraints10.anchor = GridBagConstraints.WEST;
            gridBagConstraints10.gridy = 1;
            gridBagConstraints10.insets = new Insets(0,10, 10, 10);
            
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = GridBagConstraints.BOTH;
            gridBagConstraints11.weightx = 1.0;
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.anchor = GridBagConstraints.EAST;
            gridBagConstraints11.gridy = 1;
            gridBagConstraints11.insets = new Insets(0,0,10, 10);
            
            jContentPane.add(getJPanelAccesosClientes(), gridBagConstraints10);
		//	jContentPane.add(getJPanelAccesosProveedores(), gridBagConstraints10);
			jContentPane.add(getJPanelAccesosFactCliente(), gridBagConstraints11);
			
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.fill = GridBagConstraints.BOTH;
            gridBagConstraints20.weightx = 1.0;
            gridBagConstraints20.gridx = 0;
            gridBagConstraints20.anchor = GridBagConstraints.WEST;
            gridBagConstraints20.gridy = 2;
            gridBagConstraints20.insets = new Insets(0,10, 10, 10);
            
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = GridBagConstraints.BOTH;
            gridBagConstraints21.weightx = 1.0;
            gridBagConstraints21.gridx = 1;
            gridBagConstraints21.anchor = GridBagConstraints.EAST;
            gridBagConstraints21.gridy = 2;
            gridBagConstraints21.insets = new Insets(0,0,10, 10);
            
			jContentPane.add(getJPanelAccesosProductos(), gridBagConstraints20);
			jContentPane.add(getJPanelAccesosRemitoCliente(), gridBagConstraints21);
			
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.fill = GridBagConstraints.BOTH;
			gridBagConstraints30.weightx = 1.0;
			gridBagConstraints30.gridx = 0;
			gridBagConstraints30.anchor = GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.insets = new Insets(0,10, 10, 10);
            
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.fill = GridBagConstraints.BOTH;
            gridBagConstraints31.weightx = 1.0;
            gridBagConstraints31.gridx = 1;
            gridBagConstraints31.anchor = GridBagConstraints.EAST;
            gridBagConstraints31.gridy = 3;
            gridBagConstraints31.insets = new Insets(0,0,10, 10);
		//0	jContentPane.add(getJPanelAccesosPedidos(), gridBagConstraints30);
			//jContentPane.add(getJPanelUltimo(),gridBagConstraints31);
			
			
			this.setJMenuBar(getJBarraMenu());
		}
		return jContentPane;
	}
	
	public JPanel getJPanelUltimo() {
        if (jpUlt == null) {
        	jpUlt = new JPanel();
        	jpUlt.setLayout(new GridBagLayout());
        	jpUlt.setBackground(Utils.colorFondo);
        	jpUlt.add(getJPanelLibroIva(),null);
			JLabel salir = new JLabel("SALIR");
			salir.setBounds(new java.awt.Rectangle(675,485,44,20));
			salir.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			//jContentPane.add(salir,null);
			jpUlt.add(getJButtonSalir(),null);
            jpUlt.add(getJContentPane(),null);
        }
        return jpUlt;
    }

	private JMenuBar getJBarraMenu(){
		if (menuPrinc==null){
			menuPrinc = new JMenuBar();
			archivo = new JMenu("Información        ");
			archivo.setForeground(colorFuenteMenu);
			archivo.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			gestionar = new JMenu("Gestionar             ");
	        gestionar.setForeground(colorFuenteMenu);
	        gestionar.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
	        baseDatos = new JMenu("Datos         ");
	        baseDatos.setForeground(colorFuenteMenu);
	        baseDatos.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
	        menuPrinc.add(archivo);
	        menuPrinc.add(gestionar);
	        menuPrinc.add(baseDatos);
	        infoComercio= new JMenuItem("Del Comercio");
	        infoComercio.setForeground(colorFuenteMenu);
	        infoComercio.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
	        infoProg = new JMenuItem("Del Programa        ");
			infoProg.setForeground(colorFuenteMenu);
			infoProg.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
	        archivoSalir= new JMenuItem("Salir        ");
	        archivoSalir.setForeground(colorFuenteMenu);
	        archivoSalir.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
	        gestionarProvincia= new JMenuItem("Provincia");
	        gestionarProvincia.setForeground(colorFuenteMenu);
	        gestionarProvincia.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
	        gestionarLocalidad= new JMenuItem("Localidad");
	        gestionarLocalidad.setForeground(colorFuenteMenu);
	        gestionarLocalidad.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
	        backupBD= new JMenuItem("BackUP de Base de Datos");
	        backupBD.setForeground(colorFuenteMenu);
	        backupBD.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
	        archivo.add(infoComercio);
	        archivo.add(infoProg);
	        archivo.add(archivoSalir);
	        gestionar.add(gestionarProvincia);
	        gestionar.add(gestionarLocalidad);
	        baseDatos.add(backupBD);
		}
		return menuPrinc;
	}

	private JPanel getJPanelAccesosProductos(){
		if (jPanelAccesosProductos==null){
			jPanelAccesosProductos = new JPanel();
			jPanelAccesosProductos.setLayout(new GridBagLayout());
			jPanelAccesosProductos.setBackground(Utils.colorPanel);
			jPanelAccesosProductos.setBorder(b);
			jPanelAccesosProductos.setSize(400,115);
			GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
        	gridBagConstraints00.fill = GridBagConstraints.BOTH;
        	gridBagConstraints00.weightx = 1.1;
        	gridBagConstraints00.gridx = 0;
            gridBagConstraints00.anchor = GridBagConstraints.CENTER;
            gridBagConstraints00.gridy = 0;
            gridBagConstraints00.insets = new Insets(0,0, 0, 0);
           
          /*  GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
            gridBagConstraints01.fill = GridBagConstraints.BOTH;
            gridBagConstraints01.weightx = 1.0;
            gridBagConstraints01.gridx = 1;
            gridBagConstraints01.anchor = GridBagConstraints.EAST;
            gridBagConstraints01.gridy = 0;
            gridBagConstraints01.insets = new Insets(0,0, 10, 10);*/
            
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
        //	gridBagConstraints10.fill = GridBagConstraints.BOTH;
        //	gridBagConstraints10.weightx = 1.0;
        	gridBagConstraints10.gridx = 0;
       //     gridBagConstraints10.anchor = GridBagConstraints.WEST;
            gridBagConstraints10.gridy = 1;
            gridBagConstraints10.insets = new Insets(0,0, 0, 0);
           
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
        //    gridBagConstraints11.fill = GridBagConstraints.BOTH;
        //    gridBagConstraints11.weightx = 1.0;
            gridBagConstraints11.gridx = 1;
        //    gridBagConstraints11.anchor = GridBagConstraints.EAST;
            gridBagConstraints11.gridy = 1;
            gridBagConstraints11.insets = new Insets(0,0, 0,0);
            
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
      //  	gridBagConstraints20.fill = GridBagConstraints.BOTH;
      //  	gridBagConstraints20.weightx = 1.0;
        	gridBagConstraints20.gridx = 0;
      //      gridBagConstraints20.anchor = GridBagConstraints.WEST;
            gridBagConstraints20.gridy = 2;
            gridBagConstraints20.insets = new Insets(0,0, 0, 0);
           
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
        //    gridBagConstraints21.fill = GridBagConstraints.BOTH;
          //  gridBagConstraints21.weightx = 1.0;
            gridBagConstraints21.gridx = 1;
            //gridBagConstraints21.anchor = GridBagConstraints.EAST;
            gridBagConstraints21.gridy = 2;
            gridBagConstraints21.insets = new Insets(0,0,0, 0);

			JLabel titulosoc= new JLabel("PRODUCTOS");
		//	titulosoc.setPreferredSize(new Dimension(109,16));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			titulosoc.setHorizontalAlignment(SwingConstants.RIGHT);
			jPanelAccesosProductos.add(titulosoc,gridBagConstraints00);
			jPanelAccesosProductos.add(getJButtonProductos(), gridBagConstraints10);
			JLabel gestion= new JLabel("GESTION");
		//	gestion.setBounds(new java.awt.Rectangle(65,95,55,12));
			gestion.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			jPanelAccesosProductos.add(gestion, gridBagConstraints20);
			jPanelAccesosProductos.add(getJButtonControlStock(), gridBagConstraints11);
			JLabel stock= new JLabel("CONTROL STOCK");
		//	stock.setBounds(new java.awt.Rectangle(250,95,100,12));
			stock.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			jPanelAccesosProductos.add(stock,gridBagConstraints21);
		}
		return jPanelAccesosProductos;
	}
	
	private JPanel getJPanelAccesosPedidos(){
		if (jPanelAccesosPedidos==null){
			jPanelAccesosPedidos = new JPanel();
			jPanelAccesosPedidos.setLayout(new GridBagLayout());
			jPanelAccesosPedidos.setBackground(Utils.colorPanel);
			jPanelAccesosPedidos.setBorder(b);
			jPanelAccesosPedidos.setSize(400,115);
			JLabel titulosoc= new JLabel("PEDIDOS");
			titulosoc.setBounds(new java.awt.Rectangle(170,5,80,16));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			jPanelAccesosPedidos.add(titulosoc, null);
			jPanelAccesosPedidos.add(getJButtonDeudasClientes(), null);
			JLabel gestion= new JLabel("DEUDAS");
			gestion.setBounds(new java.awt.Rectangle(65,95,55,12));
			gestion.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			jPanelAccesosPedidos.add(gestion, null);
			jPanelAccesosPedidos.add(getJButtonProductosFacts(), null);
			JLabel stock= new JLabel("PRODUCTOS FACTURADOS");
			stock.setBounds(new java.awt.Rectangle(225,95,160,12));
			stock.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			jPanelAccesosPedidos.add(stock, null);
		}
		return jPanelAccesosPedidos;
	}

	public JButton getJButtonDeudasClientes() {
		if (jBListadoDeudaClientes == null) {
			jBListadoDeudaClientes= new JButton();
			jBListadoDeudaClientes.setPreferredSize(new Dimension(65,65));
			jBListadoDeudaClientes.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/deuda.png"));
			jBListadoDeudaClientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBListadoDeudaClientes.setInputMap(0, map);
		}
		return jBListadoDeudaClientes;
	}

	public JButton getJButtonProductosFacts() {
		if (jBListadoProdsFacturados == null) {
			jBListadoProdsFacturados= new JButton();
			jBListadoProdsFacturados.setPreferredSize(new Dimension(65,65));
			jBListadoProdsFacturados.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/productosFacturados.png"));
			jBListadoProdsFacturados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBListadoProdsFacturados.setInputMap(0, map);
		} 
		return jBListadoProdsFacturados;
	}
	
	private JPanel getJPanelLibroIva(){
		if (jPanelLibroIva==null){
			jPanelLibroIva = new JPanel();
			jPanelLibroIva.setLayout(new GridBagLayout());
			jPanelLibroIva.setBackground(Utils.colorPanel);
			jPanelLibroIva.setBorder(b);
			jPanelLibroIva.setBounds(new java.awt.Rectangle(425,385,150,115));
			jPanelLibroIva.add(getJButtonLibroIva(), null);
			JLabel stock= new JLabel("LIBRO IVA");
			stock.setBounds(new java.awt.Rectangle(45,95,70,12));
			stock.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			jPanelLibroIva.add(stock, null);
		}
		return jPanelLibroIva;
	}
	
	public JButton getJButtonLibroIva() {
		if (jBLibroIva == null) {
			jBLibroIva= new JButton();
			jBLibroIva.setPreferredSize(new Dimension(65,65));
			jBLibroIva.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/libroIva.png"));
			jBLibroIva.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBLibroIva.setInputMap(0, map);
		}
		return jBLibroIva;
	}
	
	private JPanel getJPanelAccesosProveedores(){
		if (jPanelAccesosProveedores==null){
			jPanelAccesosProveedores = new JPanel();
			jPanelAccesosProveedores.setLayout(new GridBagLayout());
			jPanelAccesosProveedores.setBackground(Utils.colorPanel);
			jPanelAccesosProveedores.setBorder(b);
			jPanelAccesosProveedores.setSize(150,115);
			JLabel titulov= new JLabel("PROVEEDORES");
			titulov.setBounds(new java.awt.Rectangle(20,5,108,20));
			titulov.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			jPanelAccesosProveedores.add(titulov, null);
			jPanelAccesosProveedores.add(getJButtonProveedores(), null);
			JLabel gestion= new JLabel("GESTION");
			gestion.setBounds(new java.awt.Rectangle(48,95,55,15));
			gestion.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			jPanelAccesosProveedores.add(gestion, null);
		}
		return jPanelAccesosProveedores;
	}
	
	public JButton getJButtonProductos() {
		if (jBProductos == null) {
			jBProductos= new JButton();
			jBProductos.setPreferredSize(new Dimension(65,85));
			jBProductos.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/productos.png"));
			jBProductos.setText("Gpo");
			jBProductos.setText("<html><image src=\"SoftItComercios/extras/iconos/productos.png\"><br/GESTION</html>");
			jBProductos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBProductos.setInputMap(0, map);
		}
		return jBProductos;
	}

	public JButton getJButtonControlStock() {
		if (jBControlStock == null) {
			jBControlStock= new JButton();
			jBControlStock.setPreferredSize(new Dimension(65,65));
			jBControlStock.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/stock.PNG"));
			jBControlStock.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBControlStock.setInputMap(0, map);
		} 
		return jBControlStock;
	}
	
	public JButton getJButtonSalir() {
		if (jButtonSalir == null) {
			jButtonSalir = new JButton();
			jButtonSalir.setPreferredSize(new Dimension(100,100));
			jButtonSalir.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/salir.png"));
			jButtonSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jButtonSalir.setInputMap(0, map);
		}
		return jButtonSalir;
	}

	public JButton getJButtonProveedores() {
		if (jBGestionProveedores == null) {
			jBGestionProveedores= new JButton();
			jBGestionProveedores.setPreferredSize(new Dimension(65,65)); 
			jBGestionProveedores.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/proveedores.png"));
			
			jBGestionProveedores.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBGestionProveedores.setInputMap(0, map);
		}
		return jBGestionProveedores;
	}
	
	private JPanel getJPanelAccesosClientes(){
		if (jPanelAccesosCliente==null){
			jPanelAccesosCliente = new JPanel();
			jPanelAccesosCliente.setLayout(new GridBagLayout());
			jPanelAccesosCliente.setBackground(Utils.colorPanel);
			jPanelAccesosCliente.setBorder(b);
			jPanelAccesosCliente.setSize(150,115);
			JLabel tituloc= new JLabel("CLIENTES");
			tituloc.setBounds(new java.awt.Rectangle(40,5,80,20));
			tituloc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			jPanelAccesosCliente.add(tituloc, null);
			jPanelAccesosCliente.add(getJButtonClientes(), null);
			JLabel gestion= new JLabel("GESTION");
			gestion.setBounds(new java.awt.Rectangle(48,95,55,15));
			gestion.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			jPanelAccesosCliente.add(gestion, null);
		}
		return jPanelAccesosCliente;
	}
	
	public JButton getJButtonClientes() {
		if (jBGestionClientes == null) {
			jBGestionClientes= new JButton();
			jBGestionClientes.setPreferredSize(new Dimension(65,65));
			jBGestionClientes.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/socios.png"));
			jBGestionClientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBGestionClientes.setInputMap(0, map);
		}
		return jBGestionClientes;
	}
	
	private JPanel getJPanelAccesosContables(){
		if (jPanelAccesosContables==null){
			jPanelAccesosContables = new JPanel();
			jPanelAccesosContables.setLayout(new GridBagLayout());
			jPanelAccesosContables.setBackground(Utils.colorPanel);
			jPanelAccesosContables.setBorder(b);
			jPanelAccesosContables.setPreferredSize(new Dimension(400,115));
			JLabel titulosoc= new JLabel("GESTION CONTABLE");
			titulosoc.setBounds(new java.awt.Rectangle(120,5,150,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			jPanelAccesosContables.add(titulosoc, null);
			jPanelAccesosContables.add(getJButtonGestionarMC(), null);
				JLabel movCaja= new JLabel("MOVIMIENTO DE CAJA");
				movCaja.setBounds(new java.awt.Rectangle(25,95,135,15));
				movCaja.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
				jPanelAccesosContables.add(movCaja, null);
				jPanelAccesosContables.add(getJButtonPlanillaES(), null);
				JLabel plES= new JLabel("PLANILLA DE E/S");
				plES.setBounds(new java.awt.Rectangle(259,95,93,15));
				plES.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
				jPanelAccesosContables.add(plES, null);
		}
		return jPanelAccesosContables;
	}

	public JButton getJButtonGestionarMC() {
		if (jBGestionarMC == null) {
			jBGestionarMC= new JButton();
			jBGestionarMC.setPreferredSize(new Dimension(65,65));
			jBGestionarMC.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/movCaja.png"));
			jBGestionarMC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBGestionarMC.setInputMap(0, map);
		}
		return jBGestionarMC;
	}

	public JButton getJButtonPlanillaES() {
		if (jBPlanillaES == null) {
			jBPlanillaES= new JButton();
			jBPlanillaES.setPreferredSize(new Dimension(65,65));
			jBPlanillaES.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/planillaES.png"));
			jBPlanillaES.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBPlanillaES.setInputMap(0, map);
		}
		return jBPlanillaES;
	}
	
	private JPanel getJPanelAccesosFactProveedor(){
		if (jPanelAccesosFactProv==null){
			jPanelAccesosFactProv = new JPanel();
			jPanelAccesosFactProv.setLayout(new GridBagLayout());
			jPanelAccesosFactProv.setBackground(Utils.colorPanel);
			jPanelAccesosFactProv.setBorder(b);
			jPanelAccesosFactProv.setSize(350,115);
			JLabel titulosoc= new JLabel("FACTURACION - PROVEEDOR");
			titulosoc.setBounds(new java.awt.Rectangle(80,5,220,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			jPanelAccesosFactProv.add(titulosoc, null);
			jPanelAccesosFactProv.add(getJButtonFacturaProveedor(), null);
				JLabel nFact= new JLabel("NUEVA FACTURA");
				nFact.setBounds(new java.awt.Rectangle(50,95,103,15));
				nFact.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
				jPanelAccesosFactProv.add(nFact, null);
				jPanelAccesosFactProv.add(getJButtonTodasFactProveedor(), null);
				JLabel factGen= new JLabel("FACTURAS GENERADAS");
				factGen.setBounds(new java.awt.Rectangle(210,95,140,15));
				factGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
				jPanelAccesosFactProv.add(factGen, null);
		}
		return jPanelAccesosFactProv;
	}

	public JButton getJButtonFacturaCliente() {
		if (jBFacturaCliente == null) {
			jBFacturaCliente= new JButton();
			jBFacturaCliente.setPreferredSize(new Dimension(65,65));
			jBFacturaCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/facturaC.PNG"));
			jBFacturaCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBFacturaCliente.setInputMap(0, map);
		}
		return jBFacturaCliente;
	}

	public JButton getJButtonRemitoCliente() {
		if (jBRemitoCliente == null) {
			jBRemitoCliente= new JButton();
			jBRemitoCliente.setPreferredSize(new Dimension(65,65));
			jBRemitoCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/remito.png"));
			jBRemitoCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBRemitoCliente.setInputMap(0, map);
		}
		return jBRemitoCliente;
	}
	
	private JPanel getJPanelAccesosFactCliente(){
		if (jPanelAccesosFactCte==null){
			jPanelAccesosFactCte = new JPanel();
			jPanelAccesosFactCte.setLayout(new GridBagLayout());
			jPanelAccesosFactCte.setBackground(Utils.colorPanel);
			jPanelAccesosFactCte.setBorder(b);
			jPanelAccesosFactCte.setSize(350,115);
			JLabel titulosoc= new JLabel("FACTURACION - CLIENTE");
			titulosoc.setBounds(new java.awt.Rectangle(90,5,190,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			jPanelAccesosFactCte.add(titulosoc, null);
			jPanelAccesosFactCte.add(getJButtonFacturaCliente(), null);
				JLabel nFact= new JLabel("NUEVA FACTURA");
				nFact.setBounds(new java.awt.Rectangle(50,95,105,15));
				nFact.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
				jPanelAccesosFactCte.add(nFact, null);
				jPanelAccesosFactCte.add(getJButtonTodasFactCliente(), null);
				JLabel factGen= new JLabel("FACTURAS GENERADAS");
				factGen.setBounds(new java.awt.Rectangle(210,95,140,15));
				factGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
				jPanelAccesosFactCte.add(factGen, null);
		}
		return jPanelAccesosFactCte;
	}

	public JButton getJButtonFacturaProveedor() {
		if (jBFacturaProveedor== null) {
			jBFacturaProveedor= new JButton();
			jBFacturaProveedor.setPreferredSize(new Dimension(65,65));
			jBFacturaProveedor.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/facturaP.png"));
			jBFacturaProveedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBFacturaProveedor.setInputMap(0, map);
		}
		return jBFacturaProveedor;
	}

	public JButton getJButtonTodasFactCliente() {
		if (jBTodasFactCliente == null) {
			jBTodasFactCliente= new JButton();
			jBTodasFactCliente.setPreferredSize(new Dimension(65,65));
			jBTodasFactCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/tFactCte.png"));
			jBTodasFactCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBTodasFactCliente.setInputMap(0, map);
		}
		return jBTodasFactCliente;
	}
	
	private JPanel getJPanelAccesosRemitoCliente(){
		if (jPanelAccesosRemitoCte==null){
			jPanelAccesosRemitoCte = new JPanel();
			jPanelAccesosRemitoCte.setLayout(new GridBagLayout());
			jPanelAccesosRemitoCte.setBackground(Utils.colorPanel);
			jPanelAccesosRemitoCte.setBorder(b);
			jPanelAccesosRemitoCte.setSize(350,115);
			JLabel titulosoc= new JLabel("REMITOS - CLIENTE");
			titulosoc.setBounds(new java.awt.Rectangle(110,5,140,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			jPanelAccesosRemitoCte.add(titulosoc, null);
			jPanelAccesosRemitoCte.add(getJButtonRemitoCliente(), null);
				JLabel nRem= new JLabel("NUEVO REMITO");
				nRem.setBounds(new java.awt.Rectangle(50,95,90,15));
				nRem.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
				jPanelAccesosRemitoCte.add(nRem, null);
				jPanelAccesosRemitoCte.add(getJButtonTodosRemitosCliente(), null);
				JLabel remGen= new JLabel("REMITOS GENERADOS");
				remGen.setBounds(new java.awt.Rectangle(215,95,130,15));
				remGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
				jPanelAccesosRemitoCte.add(remGen, null);
		}
		return jPanelAccesosRemitoCte;
	}

	public JButton getJButtonTodasFactProveedor() {
		if (jBTodasFactProv== null) {
			jBTodasFactProv= new JButton();
			jBTodasFactProv.setPreferredSize(new Dimension(65,65));
			jBTodasFactProv.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/tFactProv.png"));
			jBTodasFactProv.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBTodasFactProv.setInputMap(0, map);
		}
		return jBTodasFactProv;
	}

	public JButton getJButtonTodosRemitosCliente() {
		if (jBTodosRemitosCliente == null) {
			jBTodosRemitosCliente= new JButton();
			jBTodosRemitosCliente.setPreferredSize(new Dimension(65,65));
			jBTodosRemitosCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/tRemitosCte.png"));
			jBTodosRemitosCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBTodosRemitosCliente.setInputMap(0, map);
		}
		return jBTodosRemitosCliente;
	}
	
	public void setActionListeners(ActionListener lis) {
		this.jButtonSalir.addActionListener(lis);
		this.infoComercio.addActionListener(lis);
		this.infoProg.addActionListener(lis);
		this.archivoSalir.addActionListener(lis);
		this.gestionarProvincia.addActionListener(lis);
		this.gestionarLocalidad.addActionListener(lis);
		this.jBGestionarMC.addActionListener(lis);
		this.jBPlanillaES.addActionListener(lis);
		this.jBGestionClientes.addActionListener(lis);
		this.jBGestionProveedores.addActionListener(lis);
		this.jBProductos.addActionListener(lis);
		this.jBControlStock.addActionListener(lis);
		this.jBFacturaProveedor.addActionListener(lis);
		this.jBTodasFactProv.addActionListener(lis);
		this.jBFacturaCliente.addActionListener(lis);
		this.jBTodasFactCliente.addActionListener(lis);
		this.jBRemitoCliente.addActionListener(lis);
		this.jBTodosRemitosCliente.addActionListener(lis);
		this.jBListadoDeudaClientes.addActionListener(lis);
		this.jBListadoProdsFacturados.addActionListener(lis);
		this.jBLibroIva.addActionListener(lis);
		this.backupBD.addActionListener(lis);
	}

	public JMenuItem getArchivoInfoComercio() {
		return infoComercio;
	}
	
	public JMenuItem getArchivoSalir() {
		return archivoSalir;
	}
		
	public JMenuItem getGestionarProvincia() {
		return gestionarProvincia;
	}

	public JMenuItem getGestionarLocalidad() {
		return gestionarLocalidad;
	}
	
	public JMenuItem getBaseDatos() {
		return backupBD;
	}

	public JMenuItem getInfoProg() {
		return infoProg;
	}
	
}
