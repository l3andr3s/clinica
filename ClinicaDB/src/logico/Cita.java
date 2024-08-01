package logico;

import java.util.Date;

public class Cita {
	private String codigo;
	private Date fecha;
	private String notas;
	private String especialidad;
	private String doctor;
	private Usuario userUsuario;
	private String estado;

	// Constructor y otros métodos

	public Cita(String codigo, Date fecha, String notas, String especialidad, String doctor, Usuario userUsuario) {
		this.codigo = codigo;
		this.fecha = fecha;
		this.notas = notas;
		this.especialidad = especialidad;
		this.doctor = doctor;
		this.userUsuario = userUsuario;
		this.estado = "pendiente"; // Estado inicial por defecto
	}

	// Getters y Setters

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public Usuario getUserUsuario() {
		return userUsuario;
	}

	public void setUserUsuario(Usuario userUsuario) {
		this.userUsuario = userUsuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
