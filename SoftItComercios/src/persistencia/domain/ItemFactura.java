package persistencia.domain;
  
import java.sql.Date;

import persistencia.OidGenerator;

public class ItemFactura {
	
	private Long id;
	private int cantidad;
	private double kilos;
	private Producto producto = null;
	private Factura factura = null;
	private double prUnit;
	private int descuento;//es en porcentaje
	private double prTotal;
	private Date fechaVto;
	
	public ItemFactura(){
		id=OidGenerator.getNewId();	
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public double getPrUnit() {
		return prUnit;
	}

	public void setPrUnit(double prUnit) {
		this.prUnit = prUnit;
	}

	public double getKilos() {
		return kilos;
	}

	public void setKilos(double kilos) {
		this.kilos = kilos;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public double getPrTotal() {
		return prTotal;
	}

	public void setPrTotal(double prTotal) {
		this.prTotal = prTotal;
	}

	public Date getFechaVto() {
		return fechaVto;
	}

	public void setFechaVto(Date fechaVto) {
		this.fechaVto = fechaVto;
	}

	
	
	
}
