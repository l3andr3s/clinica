package logico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vacuna implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String nombre;
	private String laboratorio;
	private String administracion;
	private List<Enfermedad> enfermedades;
	private Date fecha;
	private String doctor;

	public Vacuna(String codigo, String nombre, String laboratorio, String administracion,
			List<Enfermedad> enfermedades) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.laboratorio = laboratorio;
		this.administracion = administracion;
		this.enfermedades = enfermedades;
	}

	// Nuevo constructor para la tabla de vacunación
	public Vacuna(String nombre, Date fecha, String doctor) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.doctor = doctor;
	}

	// Getters y setters para los nuevos atributos
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getNombreString() {
		return nombre;
	}

	public void setNombreString(String nombreString) {
		this.nombre = nombreString;
	}

	public String getLaboratorioString() {
		return laboratorio;
	}

	public void setLaboratorioString(String laboratorioString) {
		this.laboratorio = laboratorioString;
	}

	public String getAdministracionString() {
		return administracion;
	}

	public void setAdministracionString(String administracionString) {
		this.administracion = administracionString;
	}

	public ArrayList<Enfermedad> getEnfermedades() {
		return (ArrayList<Enfermedad>) enfermedades;
	}

	public void setEnfermedades(ArrayList<Enfermedad> enfermedades) {
		this.enfermedades = enfermedades;
	}

	public String getCodigo() {
		return codigo;
	}

	public void insertarEnfermedad(Enfermedad ef) {
		enfermedades.add(ef);
	}

	@Override
	public String toString() {
		return this.nombre; // Devuelve el nombre de la vacuna
	}
}