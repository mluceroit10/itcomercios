package persistencia.domain;

import java.sql.Date;

import persistencia.OidGenerator;

public class Vencimiento {    
    private Long id;
	private Producto producto = null;
	private int stock;
	private double stockKilos;
	private Date fechaVto;

	public Vencimiento() {
		id=OidGenerator.getNewId();
	}
	 
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaVto() {
		return fechaVto;
	}

	public void setFechaVto(Date fechaVto) {
		this.fechaVto = fechaVto;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getStockKilos() {
		return stockKilos;
	}

	public void setStockKilos(double stockKilos) {
		this.stockKilos = stockKilos;
	}
		
}
