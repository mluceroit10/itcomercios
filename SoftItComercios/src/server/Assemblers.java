package server;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.FacturaProveedor;
import persistencia.domain.ItemFactura;
import persistencia.domain.Localidad;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.PlanillaES;
import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import persistencia.domain.Provincia;

public class Assemblers {

	public Assemblers() {
	}

	public static Cliente crearCliente(Cliente objD ) {
		Cliente cte = new Cliente();
		cte.setId(objD.getId());
		cte.setCuit(objD.getCuit());
		cte.setNombre(objD.getNombre());
		cte.setTelefono(objD.getTelefono());
		cte.setDireccion(objD.getDireccion());
		cte.setIvaCl(objD.getIvaCl());
		cte.setIngBrutosCl(objD.getIngBrutosCl());
		cte.setDeuda(objD.getDeuda());
		cte.setFechaUF(objD.getFechaUF());
		return cte;
	}
	
	public static Comercio crearComercio(Comercio objD ) {
		Comercio dist = new Comercio();
		dist.setId(objD.getId());
		dist.setCuit(objD.getCuit());
		dist.setNombre(objD.getNombre());
		dist.setTelefono(objD.getTelefono());
		dist.setDireccion(objD.getDireccion());
		dist.setCategoria(objD.getCategoria());
		dist.setInicioAct(objD.getInicioAct());
		dist.setNroFactA(objD.getNroFactA());
		dist.setNroFactB(objD.getNroFactB());
		dist.setNroRemito(objD.getNroRemito());
		return dist;
	}
	
	public static FacturaCliente crearFacturaCliente(FacturaCliente objD ) {
		FacturaCliente fc = new FacturaCliente();
		fc.setId(objD.getId());
		fc.setNroFactura(objD.getNroFactura());
		fc.setTipoFactura(objD.getTipoFactura());
		fc.setImporteTotal(objD.getImporteTotal());
		fc.setImporteAuxIva(objD.getImporteAuxIva());
		fc.setLugar(objD.getLugar());
		fc.setAnulada(objD.isAnulada());
		fc.setFechaImpresion(objD.getFechaImpresion());
		fc.setFechaPago(objD.getFechaPago());
		fc.setImporteAbonado(objD.getImporteAbonado());
		fc.setCondVenta(objD.getCondVenta());
		fc.setIva(objD.getIva());
		fc.setRemitoNro(objD.getRemitoNro());
		fc.setIngrBrutos(objD.getIngrBrutos());
		fc.setDiaBuscar(objD.getDiaBuscar());
		return fc;
	}
	
	public static FacturaProveedor crearFacturaProveedor(FacturaProveedor objD ) {
		FacturaProveedor fp = new FacturaProveedor();
		fp.setId(objD.getId());
		fp.setNroFactura(objD.getNroFactura());
		fp.setTipoFactura(objD.getTipoFactura());
		fp.setImporteTotal(objD.getImporteTotal());
		fp.setImporteAuxIva(objD.getImporteAuxIva());
		fp.setLugar(objD.getLugar());
		fp.setAnulada(objD.isAnulada());
		fp.setFecha(objD.getFecha());
		fp.setDiaBuscar(objD.getDiaBuscar());
		return fp;
	}
	
	public static ItemFactura crearItemFactura(ItemFactura objD ) {
		ItemFactura itF = new ItemFactura();
		itF.setId(objD.getId());
		itF.setCantidad(objD.getCantidad());
		itF.setKilos(objD.getKilos());
		itF.setPrUnit(objD.getPrUnit());
		itF.setDescuento(objD.getDescuento());
		itF.setPrTotal(objD.getPrTotal());
		return itF;
	}
	
	public static Localidad crearLocalidad(Localidad objD ) {
		Localidad loc = new Localidad();
		loc.setId(objD.getId());
		loc.setNombre(objD.getNombre());
		loc.setCodPostal(objD.getCodPostal());
		return loc;
	}
	
	public static MovimientoCaja crearMovimientoCaja(MovimientoCaja objD ) {
		MovimientoCaja mc = new MovimientoCaja();
		mc.setId(objD.getId());
		mc.setFecha(objD.getFecha());
		mc.setCodigo(objD.getCodigo());
		mc.setDescripcion(objD.getDescripcion());
		mc.setTipoMovimiento(objD.getTipoMovimiento());
		mc.setFormaPago(objD.getFormaPago());
		mc.setNroCheque(objD.getNroCheque());
		mc.setConFactura(objD.isConFactura());
		mc.setTipoFactura(objD.getTipoFactura());
		mc.setImporte(objD.getImporte());
		return mc;
	}
	
	public static PlanillaES crearPlanillaES(PlanillaES objD ) {
		PlanillaES pl = new PlanillaES();
		pl.setId(objD.getId());
		pl.setFecha(objD.getFecha());
		pl.setNroPlanilla(objD.getNroPlanilla());
		pl.setSaldo(objD.getSaldo());
		return pl;
	}
	
	public static Producto crearProducto(Producto objD ) {
		Producto mc = new Producto();
		mc.setId(objD.getId());
		mc.setNombre(objD.getNombre());
		mc.setCodigo(objD.getCodigo());
		mc.setStockActual(objD.getStockActual());
		mc.setStockMinimo(objD.getStockMinimo());
		mc.setStockKilosAct(objD.getStockKilosAct());
		mc.setStockKilosMin(objD.getStockKilosMin());
		mc.setPrecioEntrada(objD.getPrecioEntrada());
		mc.setPrecioVentaSinIva(objD.getPrecioVentaSinIva());
		mc.setPrecioVentaConIva(objD.getPrecioVentaConIva());
		mc.setPrecioEntCIva(objD.isPrecioEntCIva());
		mc.setPrecioKilos(objD.isPrecioKilos());
		mc.setGanancia(objD.getGanancia());
		return mc;
	}
	
	public static Proveedor crearProveedor(Proveedor objD ) {
		Proveedor pr = new Proveedor();
		pr.setId(objD.getId());
		pr.setCodigo(objD.getCodigo());
		pr.setNombre(objD.getNombre());
		pr.setTelefono(objD.getTelefono());
		pr.setDireccion(objD.getDireccion());
		pr.setDeuda(objD.getDeuda());
		pr.setFechaUF(objD.getFechaUF());
		return pr;
	}
	
	public static Provincia crearProvincia(Provincia objD ) {
		Provincia prov = new Provincia();
		prov.setId(objD.getId());
		prov.setNombre(objD.getNombre());
		return prov;
	}
}