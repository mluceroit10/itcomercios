package cliente;

import java.util.Vector;

import persistencia.domain.FacturaCliente;
import persistencia.domain.FacturaProveedor;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.PlanillaES;
import server.ManipuladorPersistencia;

import common.Utils;

public class ModificarDatos {
    public ModificarDatos() {
    }
  
  
    public static void main(String[] args) throws Exception {
    	ManipuladorPersistencia mp = new ManipuladorPersistencia();
    	try {
			mp.initPersistencia();
				Vector fClientes = mp.getAll(FacturaCliente.class);
				for(int i=0; i<fClientes.size();i++){
					FacturaCliente b = (FacturaCliente)fClientes.elementAt(i);
					System.out.println("pos "+(i+1)+" nro factC " +b.getNroFactura());
					b.setPeriodo(Utils.getMes(b.getFechaImpresion())+"-"+Utils.getAnio(b.getFechaImpresion()));
				}	
				
				Vector fProv = mp.getAll(FacturaProveedor.class);
				for(int i=0; i<fProv.size();i++){
					FacturaProveedor fp = (FacturaProveedor)fProv.elementAt(i);
					System.out.println("pos "+(i+1)+" nro factP " +fp.getNroFactura());
					fp.setPeriodo(Utils.getMes(fp.getFecha())+"-"+Utils.getAnio(fp.getFecha()));
				}	
				
				Vector movsC = mp.getAll(MovimientoCaja.class);
				for(int i=0; i<movsC.size();i++){
					MovimientoCaja mc = (MovimientoCaja)movsC.elementAt(i);
					System.out.println("pos "+(i+1)+" nro factMC " +mc.getCodigo());
					mc.setPeriodo(Utils.getMes(mc.getFecha())+"-"+Utils.getAnio(mc.getFecha()));
				}	
				
				Vector planillas = mp.getAll(PlanillaES.class);
				for(int i=0; i<planillas.size();i++){
					PlanillaES pl = (PlanillaES)planillas.elementAt(i);
					System.out.println("pos "+(i+1)+" nro factP " +pl.getNroPlanilla());
					pl.setPeriodo(Utils.getMes(pl.getFecha())+"-"+Utils.getAnio(pl.getFecha()));
				}	
			mp.commit();
		}  finally{
			mp.rollback();
		}
    }
}
