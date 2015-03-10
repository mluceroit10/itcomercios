package persistencia.domain;

import java.util.HashSet;
import java.util.Set;

import persistencia.OidGenerator;

public class Proveedor {
	
	private Long id;
	private int codigo;
	private String nombre;
	private String telefono;
	private String direccion; 
	private Localidad localidad;
	private Set productos = new HashSet();  

	public Proveedor(){
		id=OidGenerator.getNewId();	
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getProductos() {
		return productos;
	}

	public void setProductos(Set productos) {
		this.productos = productos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
	
}
