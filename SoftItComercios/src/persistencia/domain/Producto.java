package persistencia.domain;

import persistencia.OidGenerator;

  
public class Producto {    
    
	private Long id;
	private String nombre;
	private Long codigo;
	private int stockActual;
	private int stockMinimo;
	private double precioEntrada;
	private boolean precioEntCIva;
	private double precioVentaSinIva;
	private double precioVentaConIva;
	private boolean precioKilos;
	private int ganancia; //porcentaje
	private Proveedor proveedor= null;
	private double stockKilosAct;
	private double stockKilosMin;

	public Producto(){    
		id=OidGenerator.getNewId();	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getStockActual() {
		return stockActual;
	}
	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}
	public int getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public boolean isPrecioKilos() {
		return precioKilos;
	}
	public void setPrecioKilos(boolean precioKilos) {
		this.precioKilos = precioKilos;
	}
	public double getPrecioEntrada() {
		return precioEntrada;
	}
	public void setPrecioEntrada(double precioEntrada) {
		this.precioEntrada = precioEntrada;
	}
	public double getPrecioVentaSinIva() {
		return precioVentaSinIva;
	}
	public void setPrecioVentaSinIva(double precioVenta) {
		this.precioVentaSinIva = precioVenta;
	}
	public int getGanancia() {
		return ganancia;
	}
	public void setGanancia(int ganancia) {
		this.ganancia = ganancia;
	}
	public boolean isPrecioEntCIva() {
		return precioEntCIva;
	}
	public void setPrecioEntCIva(boolean precioEntCIva) {
		this.precioEntCIva = precioEntCIva;
	}
	public double getPrecioVentaConIva() {
		return precioVentaConIva;
	}
	public void setPrecioVentaConIva(double precioVentaConIva) {
		this.precioVentaConIva = precioVentaConIva;
	}
	public double getStockKilosAct() {
		return stockKilosAct;
	}
	public void setStockKilosAct(double stockKilosAct) {
		this.stockKilosAct = stockKilosAct;
	}
	public double getStockKilosMin() {
		return stockKilosMin;
	}
	public void setStockKilosMin(double stockKilosMin) {
		this.stockKilosMin = stockKilosMin;
	}
	
	
}
