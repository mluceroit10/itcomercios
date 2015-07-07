package persistencia.domain;

import java.sql.Date;

public class FacturaProveedor extends Factura{
	
	private Date fecha;
	private Proveedor proveedor=null;
	private boolean cargaParcial;
	
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isCargaParcial() {
		return cargaParcial;
	}

	public void setCargaParcial(boolean cargaParcial) {
		this.cargaParcial = cargaParcial;
	}
	
	
}
