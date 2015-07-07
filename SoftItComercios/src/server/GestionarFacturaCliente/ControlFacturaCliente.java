package server.GestionarFacturaCliente;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.ItemFactura;
import persistencia.domain.Localidad;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import persistencia.domain.Provincia;
import persistencia.domain.Vencimiento;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarCliente.ControlCliente;
import server.GestionarComercio.ControlComercio;
import server.GestionarProducto.ControlProducto;

import common.Utils;
import common.GestionarFacturaCliente.IControlFacturaCliente;
public class ControlFacturaCliente implements IControlFacturaCliente{
		
	public ControlFacturaCliente() throws RemoteException{   }
	 																// A - B - Remito     //loc Rio Cuarto - Moldes	
	public double agregarFacturaClienteTotal(FacturaCliente fc,String tipo,String loc,int nroMC,Vector items)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlCliente cCte = new ControlCliente();
		ControlComercio cDist=new ControlComercio();
		FacturaCliente lnew= Assemblers.crearFacturaCliente(fc);
		ControlProducto cProd = new ControlProducto();
		double result=0;
		FacturaCliente rem=null;
		if(tipo.compareTo("A")==0 || tipo.compareTo("B")==0){
			if(fc.getRemitoNro()!=null && fc.getRemitoNro().compareTo("")!=0){
				if(existeFacturaClienteNroTipo(new Long(fc.getRemitoNro()),"RemitoCliente")){
					rem=buscarFacturaClientePersistencia(new Long(fc.getRemitoNro()),"RemitoCliente");
				}
			}
		}
		try{
			mp.initPersistencia();
			Cliente cte = cCte.buscarClientePersistentePorId(mp,fc.getCliente().getId());
			mp.hacerPersistente(lnew);
			lnew.setPeriodo(Utils.getMes(fc.getFechaImpresion())+"-"+Utils.getAnio(fc.getFechaImpresion()));
			if(cte.getFechaUF()==null){
				cte.setFechaUF(fc.getFechaImpresion());
			}else{
				if(cte.getFechaUF().before(fc.getFechaImpresion()))
						cte.setFechaUF(fc.getFechaImpresion());
			}
			for(int i=0;i<items.size();i++){
				ItemFactura itF = (ItemFactura) items.elementAt(i);
				ItemFactura itnew= Assemblers.crearItemFactura(itF);
				Producto pr= cProd.buscarProductoPersistentePorId(mp,itF.getProducto().getId());
				if(rem==null){
					// decremento de stock por cada producto de la factura cuando no estoy fact un remito
					pr.setStockActual(pr.getStockActual()-itF.getCantidad());
					pr.setStockKilosAct(Utils.redondear(pr.getStockKilosAct()-itF.getKilos(),2));
//					Debo agregar el ctrl del Vto
					if(itF.getFechaVto()!=null){
						actualizarStockDeVencimiento(mp,pr,itF.getFechaVto(),itF.getCantidad(),itF.getKilos());
					}	
				}
				mp.hacerPersistente(itnew);
				itnew.setProducto(pr);
				itnew.setFactura(lnew);
			}
			Comercio dist=cDist.buscarUnicaComercioPersistencia(mp);
			Long nroFacturaObt= new Long(0);
			long cod=0;
			if(tipo.compareTo("A")==0){
				nroFacturaObt=dist.getNroFactA();
				cod=nroFacturaObt.longValue()+1;
				dist.setNroFactA(new Long(String.valueOf(cod)));
			}
			if(tipo.compareTo("B")==0){
				nroFacturaObt=dist.getNroFactB();
				cod=nroFacturaObt.longValue()+1;
				dist.setNroFactB(new Long(String.valueOf(cod)));
			}
			if(tipo.compareTo("Remito")==0){
				nroFacturaObt=dist.getNroRemito();
				cod=nroFacturaObt.longValue()+1;
				dist.setNroRemito(new Long(String.valueOf(cod)));
			}
			if(rem!=null){
				Set compr = rem.getComprobantesPago();
				for (Iterator itMC=compr.iterator();itMC.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) itMC.next();
					mc.setFactura(lnew);
				}
				if(rem.getFechaPago()!=null){
					Date fpago = Utils.crearFecha2(rem.getFechaPago());
					lnew.setFechaPago(fpago);
					lnew.setImporteAbonado(rem.getImporteAbonado());
				}
			}
			lnew.setCliente(cte);
			if(fc.getCondVenta().compareTo("CONTADO")!=0){
				// SALDO CLIENTE CASO 1 y 2 y la omision del 5  ( + ) 
				cte.setDeuda(Utils.redondear(cte.getDeuda() + fc.getImporteTotal(),2));
			}
			if(rem!=null){		//Estoy facturando un remito
				rem.setRemitoNro("Facturado");
			} 
			mp.commit();
		} finally {
			mp.rollback();
		}
		return result;
	}
			
	
	private void actualizarStockDeVencimiento(ManipuladorPersistencia mp,Producto pr, Date feVto, int stockUn, double stockKg) throws Exception {
		try {
			String filtro = "producto.id == "+pr.getId();
			Vector VencimientoCol= mp.getObjects(Vencimiento.class,filtro);
			boolean existeVencimiento=false;
			Vencimiento venc=null;
			if (VencimientoCol.size()>=1){
				for(int i=0; i<VencimientoCol.size() && !existeVencimiento;i++){
					Vencimiento b = (Vencimiento)VencimientoCol.elementAt(i);
					if(	common.Utils.getStrUtilDate(b.getFechaVto()).compareTo(common.Utils.getStrUtilDate(feVto))==0 ){
						existeVencimiento=true;
						venc=b;
					}
				}	
			}
			if(existeVencimiento){
				//solo incremento el stock
				venc.setStock(venc.getStock()-stockUn);
				venc.setStockKilos(Utils.redondear(venc.getStockKilos()-stockKg,2));
			}else{
				//debo crear el objeto Vto
				Vencimiento vencim = new Vencimiento();
				vencim.setStock(-stockUn);
				vencim.setStockKilos(-stockKg);
				vencim.setFechaVto(Utils.crearFecha(feVto));
				mp.hacerPersistente(vencim);
				ControlProducto cProd = new ControlProducto();
				Producto prod = cProd.buscarProductoPersistentePorId(mp,pr.getId());
				vencim.setProducto(prod);
			}
			//No analizo el stock 0
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public void anularFacturaCliente(Long idF)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlProducto cProd = new ControlProducto();
		try {
			mp.initPersistencia();
			String filtro = "id == "+idF;
			Vector FacturaClienteCol=mp.getObjects(FacturaCliente.class,filtro);
				FacturaCliente fc = (FacturaCliente) FacturaClienteCol.elementAt(0);
				fc.setAnulada(true);
//				 SALDO CLIENTE CASO 3 y 4 ( - ) 
				if(fc.getCondVenta().compareTo("CONTADO")!=0){
					fc.getCliente().setDeuda(Utils.redondear(fc.getCliente().getDeuda()-fc.getImporteTotal(),2));
				}
				for(Iterator items=fc.getItems().iterator();items.hasNext();){
					ItemFactura itF = (ItemFactura) items.next();
					Producto pr= cProd.buscarProductoPersistentePorId(mp,itF.getProducto().getId());
					// incremento el stock por cada producto de la factura que haya sido anulado
						pr.setStockActual(pr.getStockActual()+itF.getCantidad());
						pr.setStockKilosAct(Utils.redondear(pr.getStockKilosAct()+itF.getKilos(),2));
				}
				if(fc.getFechaImpresion().equals(fc.getCliente().getFechaUF())){
					//Tengo que actualizar la fecha de la ultima facturacion
					String filtroFC = " cliente.id=="+fc.getCliente().getId() +" && anulada==false && id!="+idF;
	    			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtroFC,"fechaImpresion descending");
	    			if(FacturaClientes.size()>0){
	    				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(0);
	    				fc.getCliente().setFechaUF(Utils.crearFecha( b.getFechaImpresion()));
	    			}else{
	    				fc.getCliente().setFechaUF(null);
	    			}
				}
				mp.commit();
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
	}
		
	public boolean existenFacturasDeCliente(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existen = false;
		try {
			mp.initPersistencia();
			String filtro = "cliente.id=="+id;
			Vector facturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			if(facturaClientes.size()>0)
				existen=true;
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return existen;
	}
		
	public Vector obtenerFacturaClientesPeriodo(boolean listarRemitosSinFact,String tipoF,int diaLI,int mesLI,int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\""+tipoF+"\" && periodo==\""+mesLI+"-"+anioLI+"\"";
			if(diaLI!=0)
				filtro +=" && diaBuscar=="+diaLI;
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				boolean agregar=true;
				if(listarRemitosSinFact){
					filtro +=" && remitoNro==\"\" && anulada==false";
				}	
				if(agregar){
					FacturaCliente a= Assemblers.crearFacturaCliente(b);
					Cliente cte= Assemblers.crearCliente(b.getCliente());
					Localidad loc= Assemblers.crearLocalidad(b.getCliente().getLocalidad());
					Provincia prv= Assemblers.crearProvincia(b.getCliente().getLocalidad().getProvincia());
					loc.setProvincia(prv);
					cte.setLocalidad(loc);
					// ver como retornar el conjunto de elementos
					a.setCliente(cte);
					Set movs = b.getComprobantesPago();
					Set nvmovs= new HashSet();
					for(Iterator it= movs.iterator(); it.hasNext();){
						MovimientoCaja mc = (MovimientoCaja) it.next();
						MovimientoCaja mov= Assemblers.crearMovimientoCaja(mc);
						nvmovs.add(mov);
					}
					a.setComprobantesPago(nvmovs);
					FacturaClientes2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}
	
	public Vector obtenerFacturaClientesPeriodoFiltros(boolean listarRemitosSinFact,String tipoF,int diaLI,int mesLI,int anioLI,String fecha,String numero,String cliente)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\""+tipoF+"\"  && cliente.nombre.toLowerCase().indexOf(\""+cliente.toLowerCase()+"\")>= 0 && periodo==\""+mesLI+"-"+anioLI+"\"";
			if(diaLI!=0)
				filtro +=" && diaBuscar=="+diaLI;
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				boolean agregar=true;
				if(listarRemitosSinFact){
					filtro +=" && remitoNro==\"\" && anulada==false";
				}	
				if(agregar && (Utils.comienza(String.valueOf(b.getNroFactura()),numero) && 
						 Utils.comienza(common.Utils.getStrUtilDate(b.getFechaImpresion()), fecha))){
					FacturaCliente a= Assemblers.crearFacturaCliente(b);
					Cliente cte= Assemblers.crearCliente(b.getCliente());
					Localidad loc= Assemblers.crearLocalidad(b.getCliente().getLocalidad());
					Provincia prv= Assemblers.crearProvincia(b.getCliente().getLocalidad().getProvincia());
					loc.setProvincia(prv);
					cte.setLocalidad(loc);
					a.setCliente(cte);
					Set movs = b.getComprobantesPago();
					Set nvmovs= new HashSet();
					for(Iterator it= movs.iterator(); it.hasNext();){
						MovimientoCaja mc = (MovimientoCaja) it.next();
						MovimientoCaja mov= Assemblers.crearMovimientoCaja(mc);
						nvmovs.add(mov);
					}
					a.setComprobantesPago(nvmovs);
					FacturaClientes2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}
	
	
	public Vector obtenerFacturasClienteFechaLoc(Date fecha,Long idLoc)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "anulada==false && cliente.localidad.id=="+idLoc;
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"tipoFactura ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				if(b.getFechaImpresion().equals(fecha)){
					if(b.getRemitoNro().compareTo("")==0){
						FacturaCliente a= Assemblers.crearFacturaCliente(b);
						Cliente cte= Assemblers.crearCliente(b.getCliente());
						a.setCliente(cte);
						FacturaClientes2.add(a);
					}
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}
	
	public boolean existeFacturaClienteNroTipo(Long nroF, String tipoF)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nroFactura == "+nroF+" && tipoFactura==\""+tipoF+"\"";
			Vector FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()>=1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public boolean existeFacturaDeRemito(String nroRemito)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "remitoNro == \"" +nroRemito+ "\"";
			Vector FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public FacturaCliente buscarFacturaDeRemito(String nroRemito) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		FacturaCliente a = new FacturaCliente();
		try {
			mp.initPersistencia();
			String filtro = "remitoNro == \"" +nroRemito+ "\"";
			Vector FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()>=1){
				FacturaCliente b = (FacturaCliente)(FacturaClienteCol.toArray())[0];
				a = Assemblers.crearFacturaCliente(b);
				Cliente cte= Assemblers.crearCliente(b.getCliente());
				Localidad loc= Assemblers.crearLocalidad(b.getCliente().getLocalidad());
				Provincia prv= Assemblers.crearProvincia(b.getCliente().getLocalidad().getProvincia());
				loc.setProvincia(prv);
				cte.setLocalidad(loc);
				Set elem = b.getItems();
				Set aelem = new HashSet();
				for(Iterator j=elem.iterator();j.hasNext();){
					ItemFactura itv= (ItemFactura) j.next();
					ItemFactura it= Assemblers.crearItemFactura(itv);
					Producto p= Assemblers.crearProducto(itv.getProducto());
					Proveedor pr = Assemblers.crearProveedor(itv.getProducto().getProveedor());
					p.setProveedor(pr);
					it.setProducto(p);
					aelem.add(it);
				}
				a.setItems(aelem);
				a.setCliente(cte);
				//comprobantes
				Set movs = b.getComprobantesPago();
				Set nvmovs= new HashSet();
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) it.next();
					MovimientoCaja mov= Assemblers.crearMovimientoCaja(mc);
					nvmovs.add(mov);
				}
				a.setComprobantesPago(nvmovs);
				
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public FacturaCliente buscarFacturaClientePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		FacturaCliente obj = new FacturaCliente();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(FacturaCliente.class,filtro);
		if (col.size()>=1){
			obj = (FacturaCliente)(col.toArray())[0];
		}
		return obj;
	}
	
	public FacturaCliente buscarFacturaCliente(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		FacturaCliente a = new FacturaCliente();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Vector FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()>=1){
				FacturaCliente b = (FacturaCliente)(FacturaClienteCol.toArray())[0];
				a = Assemblers.crearFacturaCliente(b);
				Cliente cte= Assemblers.crearCliente(b.getCliente());
				Localidad loc= Assemblers.crearLocalidad(b.getCliente().getLocalidad());
				Provincia prv= Assemblers.crearProvincia(b.getCliente().getLocalidad().getProvincia());
				loc.setProvincia(prv);
				cte.setLocalidad(loc);
				Set elem = b.getItems();
				Set aelem = new HashSet();
				for(Iterator j=elem.iterator();j.hasNext();){
					ItemFactura itv= (ItemFactura) j.next();
					ItemFactura it= Assemblers.crearItemFactura(itv);
					Producto p= Assemblers.crearProducto(itv.getProducto());
					Proveedor pr = Assemblers.crearProveedor(itv.getProducto().getProveedor());
					p.setProveedor(pr);
					it.setProducto(p);
					aelem.add(it);
				}
				a.setItems(aelem);
				a.setCliente(cte);
				//comprobantes
				Set movs = b.getComprobantesPago();
				Set nvmovs= new HashSet();
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) it.next();
					MovimientoCaja mov= Assemblers.crearMovimientoCaja(mc);
					nvmovs.add(mov);
				}
				a.setComprobantesPago(nvmovs);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public FacturaCliente buscarFacturaClientePersistencia(Long nroF, String tipoF) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		FacturaCliente b = new FacturaCliente();
		try {
			mp.initPersistencia();
			String filtro = "nroFactura == "+nroF+" && tipoFactura==\""+tipoF+"\"";
			Collection FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()>=1){
				b = (FacturaCliente)(FacturaClienteCol.toArray())[0];
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return b;
	}
	
	public Vector obtenerListadoProductosFacturados(Vector ids)throws Exception {
		Vector rta = new Vector();
//		elem 1 vector de productos Codigo elem 2 vector + nombre producto vector 3 cantidad.
		Long [] codigos= new Long [20*ids.size()];
		String [] productos= new String [20*ids.size()];
		String [] proveedores= new String [20*ids.size()];
		int [] cantidades= new int [20*ids.size()];
		double [] kilos= new double [20*ids.size()];
		int elems = 0;
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro=" ";
			for(int i=0;i<ids.size();i++){
				Long idFactSel=(Long)ids.elementAt(i);
				if(i==0)
					filtro = "id == " +idFactSel;
				else
					filtro += " || id == " +idFactSel;
			}
			Vector facturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			for(int i=0;i<facturaClienteCol.size();i++){
				FacturaCliente fc= (FacturaCliente)facturaClienteCol.elementAt(i);
				Set prodFact = fc.getItems();
				for(Iterator it=prodFact.iterator(); it.hasNext(); ){
					ItemFactura item = (ItemFactura) it.next();
					boolean encontrado=false;
					int posEncontrado=0;
					for(int k=0; k<elems; k++){
						if(codigos[k]==item.getProducto().getCodigo()){
							encontrado=true;
							posEncontrado=k;
						}
					}
					if(encontrado){
						cantidades[posEncontrado]=cantidades[posEncontrado]+item.getCantidad();
						kilos[posEncontrado]=kilos[posEncontrado]+item.getKilos();
					}else{
						codigos[elems]=item.getProducto().getCodigo();
						productos[elems]=item.getProducto().getNombre();
						proveedores[elems]=item.getProducto().getProveedor().getNombre();
						cantidades[elems]=item.getCantidad();
						kilos[elems]=item.getKilos();
						elems++;
					}
				}
			}
			
			mp.commit();
		} finally {
			mp.rollback();
		}
		rta.add(String.valueOf(elems));
		rta.add(codigos);
		rta.add(productos);
		rta.add(proveedores);
		rta.add(cantidades);
		rta.add(kilos);
		return rta;
	}
	
	public Vector obtenerFacturasLibroIva(int mesLI, int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\"FacturaCliente-A\" || tipoFactura==\"FacturaCliente-B\"";
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				if(Utils.getMes(b.getFechaImpresion())==mesLI && Utils.getAnio(b.getFechaImpresion())==anioLI){
					FacturaCliente a= Assemblers.crearFacturaCliente(b);
					Cliente cte= Assemblers.crearCliente(b.getCliente());
					a.setCliente(cte);
					FacturaClientes2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}
	
	public boolean facturaAsociada(Long id) throws Exception {
		boolean estaAsoc = false;
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "conFactura==true && factura.id=="+id;
			Vector movsDeFactura= mp.getObjects(MovimientoCaja.class,filtro);
			if (movsDeFactura.size()!=0){
				estaAsoc = true;
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return estaAsoc;
    }
	
}
