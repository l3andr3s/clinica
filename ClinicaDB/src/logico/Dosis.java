package logico;

import java.io.Serializable;
import java.util.Date;

public class Dosis implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date fecha;
	private Vacuna vacuna;
	private Paciente paciente;

	public Dosis(int id, Date fecha, Vacuna vacuna, Paciente paciente) {
		this.id = id;
		this.fecha = fecha;
		this.vacuna = vacuna;
		this.paciente = paciente;
	}

	// Getters and setters...

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Vacuna getVacinneVacuna() {
		return vacuna;
	}

	public void setVacinneVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
}
