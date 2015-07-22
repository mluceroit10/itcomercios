package server.GestionarFacturaProveedor;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.FacturaProveedor;
import persistencia.domain.ItemFactura;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import persistencia.domain.Vencimiento;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarProducto.ControlProducto;
import server.GestionarProveedor.ControlProveedor;

import common.Utils;
import common.GestionarFacturaProveedor.IControlFacturaProveedor;
public class ControlFacturaProveedor implements IControlFacturaProveedor{
	
	
	public ControlFacturaProveedor() throws RemoteException{   }
	
	public double agregarFacturaProveedorTotal(FacturaProveedor p,Vector items,Vector cambioPrecio,Vector prEntrConIva, Vector ganancias,Vector nuevoPrecioVtaSinIva,Vector nuevoPrecioVtaConIva,Long idFGuardada)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlProveedor cProv = new ControlProveedor();
		//creo objeto y seteo datos basicos
		FacturaProveedor lnew= Assemblers.crearFacturaProveedor(p);
		ControlProducto cProd = new ControlProducto();
		double result=0;
		try{
			mp.initPersistencia();
			if(idFGuardada!=null){
				FacturaProveedor felim=buscarFProveedorPersistentePorId(mp,idFGuardada);
				mp.borrarTodos(felim.getItems());
				mp.borrar(felim);
			}	
			
			Proveedor prov = cProv.buscarProveedorPersistentePorId(mp,p.getProveedor().getId());
			mp.hacerPersistente(lnew);
			lnew.setPeriodo(Utils.getMes(p.getFecha())+"-"+Utils.getAnio(p.getFecha()));
			if(prov.getFechaUF()==null){
				prov.setFechaUF(p.getFecha());
			}else{
				if(prov.getFechaUF().before(p.getFecha()))
					prov.setFechaUF(p.getFecha());
			}
//			 SALDO PROVEEDOR CASO 1 ( + ) 
			prov.setDeuda(Utils.redondear(prov.getDeuda() + p.getImporteTotal(),2));
			for(int i=0;i<items.size();i++){
				ItemFactura itF = (ItemFactura) items.elementAt(i);
				ItemFactura itnew= Assemblers.crearItemFactura(itF);
				Producto pr= cProd.buscarProductoPersistentePorId(mp,itF.getProducto().getId());
				// decremento de stock por cada producto de la factura
				pr.setStockActual(pr.getStockActual()+itF.getCantidad());
				pr.setStockKilosAct(Utils.redondear(pr.getStockKilosAct()+itF.getKilos(),2));
				pr.setPrecioEntrada(itF.getPrUnit());
				if(((String)cambioPrecio.elementAt(i)).compareTo("SI")==0){
					pr.setGanancia(Integer.parseInt((String)ganancias.elementAt(i)));
				//	if(prEntrConIva.elementAt(i).toString().compareTo("CON IVA")==0)
						pr.setPrecioEntCIva(true);
				//	else
				//		pr.setPrecioEntCIva(false);
					pr.setPrecioVentaSinIva(Double.parseDouble((String)nuevoPrecioVtaSinIva.elementAt(i)));
					pr.setPrecioVentaConIva(Double.parseDouble((String)nuevoPrecioVtaConIva.elementAt(i)));
				}	
				
				//Debo agregar el ctrl del Vto
				if(itF.getFechaVto()!=null){
					actualizarVencimientoDelProducto(mp,pr,itF.getFechaVto(),itF.getCantidad(),itF.getKilos());
				//
				}	
				
				mp.hacerPersistente(itnew);
				itnew.setProducto(pr);
				itnew.setFactura(lnew);
			}
			lnew.setProveedor(prov);
			mp.commit();
		} finally {
			mp.rollback();
		}
		return result;
	}
	
	
	private void actualizarVencimientoDelProducto(ManipuladorPersistencia mp,Producto pr, Date feVto, int stockUn, double stockKg) throws Exception {
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
				venc.setStock(venc.getStock()+stockUn);
				venc.setStockKilos(Utils.redondear(venc.getStockKilos()+stockKg,2));
			}else{
				//debo crear el objeto Vto
				Vencimiento vencim = new Vencimiento();
				vencim.setStock(stockUn);
				vencim.setStockKilos(stockKg);
				vencim.setFechaVto(Utils.crearFecha(feVto));
				mp.hacerPersistente(vencim);
				ControlProducto cProd = new ControlProducto();
				Producto prod = cProd.buscarProductoPersistentePorId(mp,pr.getId());
				vencim.setProducto(prod);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void anularFacturaProveedor(Long idF)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlProducto cProd = new ControlProducto();
		try {
			mp.initPersistencia();
			String filtro = "id == "+idF;
			Vector facturaProvCol=mp.getObjects(FacturaProveedor.class,filtro);
			FacturaProveedor fp = (FacturaProveedor) facturaProvCol.elementAt(0);
			fp.setAnulada(true);
//			 SALDO PROVEEDOR CASO 2 ( - ) 
			fp.getProveedor().setDeuda(Utils.redondear(fp.getProveedor().getDeuda()-fp.getImporteTotal(),2));
			for(Iterator items=fp.getItems().iterator();items.hasNext();){
				ItemFactura itF = (ItemFactura) items.next();
				Producto pr= cProd.buscarProductoPersistentePorId(mp,itF.getProducto().getId());
				// incremento el stock por cada producto de la factura que haya sido anulado
				pr.setStockActual(pr.getStockActual()-itF.getCantidad());
				pr.setStockKilosAct(Utils.redondear(pr.getStockKilosAct()-itF.getKilos(),2));
			}

			if(fp.getFecha().equals(fp.getProveedor().getFechaUF())){
				//Tengo que actualizar la fecha de la ultima facturacion
				String filtroFP = " proveedor.id=="+fp.getProveedor().getId() +" && anulada==false && id!="+idF;
    			Vector facturasProveedor= mp.getObjectsOrdered(FacturaProveedor.class,filtroFP,"fecha descending");
    			if(facturasProveedor.size()>0){
    				FacturaProveedor b = (FacturaProveedor)facturasProveedor.elementAt(0);
    				fp.getProveedor().setFechaUF(Utils.crearFecha(b.getFecha()));
    			}else{
    				fp.getProveedor().setFechaUF(null);
    			}
			}
			
			mp.commit();
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
	}
		
	
	public void eliminarFacturaProveedor(FacturaProveedor p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "nroFactura == \""+p.getNroFactura()+"\" && tipoFactura==\""+p.getTipoFactura()+"\"";
			Vector FacturaProveedorCol=mp.getObjects(FacturaProveedor.class,filtro);
			if (FacturaProveedorCol.size()==1){
				FacturaProveedor factProveedor = (FacturaProveedor) FacturaProveedorCol.elementAt(0);
				mp.borrar(factProveedor);
				mp.commit();
			}
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
	}
		
	public Vector obtenerFacturaProveedores(String tipoF,int mesLI,int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaProveedors2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\""+tipoF+"\" && periodo==\""+mesLI+"-"+anioLI+"\" ";
			Vector FacturaProveedors= mp.getObjectsOrdered(FacturaProveedor.class,filtro,"fecha ascending");
			for(int i=0; i<FacturaProveedors.size();i++){
				FacturaProveedor b = (FacturaProveedor)FacturaProveedors.elementAt(i);
				FacturaProveedor a= Assemblers.crearFacturaProveedor(b);
				Proveedor prov= Assemblers.crearProveedor(b.getProveedor());
				a.setProveedor(prov);
				Set movs = b.getComprobantesPago();
				Set nvmovs= new HashSet();
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) it.next();
					MovimientoCaja mov= Assemblers.crearMovimientoCaja(mc);
					nvmovs.add(mov);
				}
				a.setComprobantesPago(nvmovs);
				FacturaProveedors2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaProveedors2;
	}
	
	public Vector obtenerFacturaProveedoresFiltros(String tipoF,int mesLI,int anioLI,String fecha,String numero,String proveedor)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaProveedors2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\""+tipoF+"\" && proveedor.nombre.toLowerCase().indexOf(\""+proveedor.toLowerCase()+"\")>= 0 && periodo==\""+mesLI+"-"+anioLI+"\"";
			Vector FacturaProveedors= mp.getObjectsOrdered(FacturaProveedor.class,filtro,"fecha ascending");
			for(int i=0; i<FacturaProveedors.size();i++){
				FacturaProveedor b = (FacturaProveedor)FacturaProveedors.elementAt(i);
				if(	(Utils.comienza(String.valueOf(b.getNroFactura()),numero) && 
					 Utils.comienza(common.Utils.getStrUtilDate(b.getFecha()), fecha))){
					FacturaProveedor a= Assemblers.crearFacturaProveedor(b); 
					Proveedor prov= Assemblers.crearProveedor(b.getProveedor());
					a.setProveedor(prov);
					Set movs = b.getComprobantesPago();
					Set nvmovs= new HashSet();
					for(Iterator it= movs.iterator(); it.hasNext();){
						MovimientoCaja mc = (MovimientoCaja) it.next();
						MovimientoCaja mov= Assemblers.crearMovimientoCaja(mc);
						nvmovs.add(mov);
					}
					a.setComprobantesPago(nvmovs);
					FacturaProveedors2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaProveedors2;
	}
		
	public boolean existeFacturaProveedorNroTipo(Long nroF, String tipoF, int cod, Long idFPGuardada)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nroFactura == "+nroF+" && tipoFactura==\""+tipoF+"\" && proveedor.codigo=="+cod;
			Vector FacturaProveedorCol= mp.getObjects(FacturaProveedor.class,filtro);
			if (FacturaProveedorCol.size()==1){
			    FacturaProveedor fp=(FacturaProveedor)FacturaProveedorCol.elementAt(0);
			    if(fp.getId().equals(idFPGuardada))
			    	existe=false;
			    else existe=true;	
			}
				
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public FacturaProveedor buscarFacturaProveedor(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		FacturaProveedor a = new FacturaProveedor();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Vector FacturaProveedorCol= mp.getObjects(FacturaProveedor.class,filtro);
			if (FacturaProveedorCol.size()>=1){
				FacturaProveedor b = (FacturaProveedor)(FacturaProveedorCol.toArray())[0];
				a = Assemblers.crearFacturaProveedor(b);
				Proveedor cte= Assemblers.crearProveedor(b.getProveedor());
				a.setProveedor(cte);
				Set elem = b.getItems();
				Set aelem = new HashSet();
				for(Iterator j=elem.iterator();j.hasNext();){
					ItemFactura itv= (ItemFactura) j.next();
					ItemFactura it= Assemblers.crearItemFactura(itv);
					Producto p2= itv.getProducto();
					Producto p= Assemblers.crearProducto(p2);
					Proveedor pr2 = p2.getProveedor();
					Proveedor pr = Assemblers.crearProveedor(pr2);
					p.setProveedor(pr);
					it.setProducto(p);
					aelem.add(it);
				}
				a.setItems(aelem);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
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
	
	public FacturaProveedor buscarFProveedorPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		FacturaProveedor obj = new FacturaProveedor();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(FacturaProveedor.class,filtro);
		if (col.size()>=1){
			obj = (FacturaProveedor)(col.toArray())[0];
		}
		return obj;
	}
	
	public double guardarFacturaProveedor(FacturaProveedor p,Vector items,Vector cambioPrecio,Vector prEntrConIva, Vector ganancias,Vector nuevoPrecioVtaSinIva,Vector nuevoPrecioVtaConIva,Long idFGuardada)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlProveedor cProv = new ControlProveedor();
		//creo objeto y seteo datos basicos
		FacturaProveedor lnew =Assemblers.crearFacturaProveedor(p);
		ControlProducto cProd = new ControlProducto();
		double result=0;
		try{
			mp.initPersistencia();
			if(idFGuardada!=null){
				FacturaProveedor felim=buscarFProveedorPersistentePorId(mp,idFGuardada);
				mp.borrarTodos(felim.getItems());
				mp.borrar(felim);
			}	
			
			Proveedor prov = cProv.buscarProveedorPersistentePorId(mp,p.getProveedor().getId());
			if(idFGuardada==null)
				mp.hacerPersistente(lnew);
			lnew.setPeriodo(Utils.getMes(p.getFecha())+"-"+Utils.getAnio(p.getFecha()));

			// No guardo la fecha de UF ni el saldo enla deuda del proveedor

			for(int i=0;i<items.size();i++){
				ItemFactura itF = (ItemFactura) items.elementAt(i);
				ItemFactura itnew= Assemblers.crearItemFactura(itF);
				Producto pr= cProd.buscarProductoPersistentePorId(mp,itF.getProducto().getId());
				
				// No guardo decrementos de stock por cada producto de la factura

				if(((String)cambioPrecio.elementAt(i)).compareTo("SI")==0){
					pr.setGanancia(Integer.parseInt((String)ganancias.elementAt(i)));
					pr.setPrecioEntrada(itF.getPrUnit());					
					pr.setPrecioEntCIva(true);
					pr.setPrecioVentaSinIva(Double.parseDouble((String)nuevoPrecioVtaSinIva.elementAt(i)));
					pr.setPrecioVentaConIva(Double.parseDouble((String)nuevoPrecioVtaConIva.elementAt(i)));
				}	
				
				// No guardo el ctrl del Vto
								
				mp.hacerPersistente(itnew);
				itnew.setProducto(pr);
				itnew.setFactura(lnew);
			}
			lnew.setProveedor(prov);
			mp.commit();
		} finally {
			mp.rollback();
		}
		return result;
	}
}
