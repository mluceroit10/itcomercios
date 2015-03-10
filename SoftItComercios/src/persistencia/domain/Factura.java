package persistencia.domain;

import java.util.HashSet;
import java.util.Set;

import persistencia.OidGenerator;
  
public class Factura {
	
	private Long id;
	private Long nroFactura;
	private Set items=new HashSet();
	private String tipoFactura; 
	private double importeTotal; 
	private double importeAuxIva;
	private String lugar;
	private boolean anulada;
	private String periodo;
	
	private Set comprobantesPago=new HashSet();
	
	public Factura(){
		id=OidGenerator.getNewId();	
	}

	public Set getComprobantesPago() {
		return comprobantesPago;
	}

	public void setComprobantesPago(Set comprobantesPago) {
		this.comprobantesPago = comprobantesPago;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public Set getItems() {
		return items;
	}

	public void setItems(Set items) {
		this.items = items;
	}

	public Long getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(Long nroFactura) {
		this.nroFactura = nroFactura;
	}

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public double getImporteAuxIva() {
		return importeAuxIva;
	}

	public void setImporteAuxIva(double importeAuxIva) {
		this.importeAuxIva = importeAuxIva;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public boolean isAnulada() {
		return anulada;
	}

	public void setAnulada(boolean anulada) {
		this.anulada = anulada;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
}
