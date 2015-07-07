package cliente.backupDataBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.Date;

import javax.swing.JFrame;

import common.Utils;

public class MediadorBackup implements ActionListener{

	private GUIBackup guiBackup=null;
	private String pathBackup = common.RootAndIp.getArchivos()+ "backup/";
	private String date;
	
	public MediadorBackup(JFrame guiPadre){
    	this.guiBackup = new GUIBackup(guiPadre);
		this.guiBackup.setActionListeners(this);
		this.backUpDataBase();
	}

	public void backUpDataBase(){
		boolean seHizo=false;
		try{
			this.backup();
			seHizo=true;
		}catch(Exception e){
			e.printStackTrace();
			seHizo=false;
		}
		if(seHizo){
			try{
				this.guiBackup.getAdvertencia().setText("SE REALIZO  CORRECTAMENTE EL BACKUP DE SU BASE DE DATOS");
				this.guiBackup.getResultado().setText("EL ARCHIVO SE GUARDO EN EL DIRECTORIO "+pathBackup+"\nCON EL NOMBRE BACKUP_BD_COMERCIO_"+date+ ".SQL");
			}catch(Exception e){
				e.printStackTrace();
				seHizo=false;
				Utils.advertenciaUsr(null, "Ocurrió un error en el sistema, mientras se intentaba\nrealizar el Backup de su Base de Datos. Intente nuevamente.");
			}
		}else{
			this.guiBackup.getAdvertencia().setText("OCURRIO UN ERROR AL REALIZAR EL BACKUP DE SU BASE DE DATOS. INTENTE NUEVAMENTE.");
			this.guiBackup.getResultado().setText("NO SE CREO EL ARCHIVO CORRESPONDIENTE AL BACKUP.");
		}
		Utils.show(guiBackup);
	}

	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if(this.guiBackup.getJButtonSalir()==source){
			this.guiBackup.dispose();
		}
	}
	
	public void backup()throws Exception{
		Runtime rt= Runtime.getRuntime();
		try{
			FileOutputStream archivo= new FileOutputStream(pathBackup+"backup.bat");
			Date fecha= new Date();
			int p = fecha.toLocaleString().indexOf(" ");
			String primerParte = fecha.toLocaleString().substring(0,p);
			boolean esConGuion = false; 
			int barra1 = primerParte.indexOf("-");
			if(barra1 >= 0) {
				esConGuion = true;
			}
			String diaMesAnio ="";
			if(!esConGuion){
				barra1 = primerParte.indexOf("/");
				if(barra1 < 0 ){
					barra1 = primerParte.indexOf(".");
				}
				String parte1Barra = primerParte.substring(0,barra1);
				diaMesAnio = parte1Barra +"-"+ primerParte.substring(barra1+1,barra1+3)+"-"+primerParte.substring(barra1+4,primerParte.length());
			}else{
				diaMesAnio  = primerParte.substring(0,11);
			}
			String segundaParte = fecha.toLocaleString().substring(p+1,fecha.toLocaleString().length());
			int punto1 = segundaParte.indexOf(":");
			String parte1Punto = segundaParte.substring(0,punto1);
			String horaMinSeg = parte1Punto +"-"+ segundaParte.substring(punto1+1,punto1+3)+"-"+segundaParte.substring(punto1+4,segundaParte.length());
			date = diaMesAnio+"_hr_"+horaMinSeg;
			String linea= new String("C:/mysql/bin/MYSQLDUMP.EXE softcomerciodb jdo_table oid provincia localidad cliente proveedor " +
					"producto factura  factura_cliente factura_proveedor planilla_es movimiento_caja item_factura comercio>"+pathBackup+"BACKUP_BD_COMERCIO_"+date+".SQL");
			archivo.write(linea.getBytes());
			archivo.close();
			rt.exec(pathBackup+"backup.bat");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
