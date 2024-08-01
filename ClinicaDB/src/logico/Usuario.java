package logico;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String nombre;
	protected String apellido;
	protected String tipoDocumento;
	protected String id;
	protected String codigo;
	protected Date fechaNacimiento;
	protected String telefono;
	protected String direccion;
	protected String cedula;

	// New constructor to match the usage in getCitasPendientes
	public Usuario(String id, String nombre, String apellido) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	// Existing constructor
	public Usuario(String nombre, String apellido, String id, Date fechaNacimiento, String telefono, String direccion,
			String codigo, String cedula) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.id = id;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.direccion = direccion;
		this.codigo = codigo;
		this.cedula = cedula;
	}

	// Getter and Setter methods
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
}