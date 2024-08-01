package logico;

import java.io.Serializable;
import java.util.Date;

public class Vigilancia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Enfermedad enfermedadEnfermedad;
	private int transmisibilidad;
	private int poblacionAfectada;
	private Date primerbroteDate;
	private Date fechaActual;
	private String anotacioneString;

	public Vigilancia(Enfermedad enfermedadEnfermedad, int transmisibilidad, int poblacionAfectada,
			Date primerbroteDate, Date fechaActual, String anotacioneString) {
		super();
		this.enfermedadEnfermedad = enfermedadEnfermedad;
		this.transmisibilidad = transmisibilidad;
		this.poblacionAfectada = poblacionAfectada;
		this.primerbroteDate = primerbroteDate;
		this.fechaActual = fechaActual;
		this.anotacioneString = anotacioneString;
	}

	public Enfermedad getEnfermedadEnfermedad() {
		return enfermedadEnfermedad;
	}

	public void setEnfermedadEnfermedad(Enfermedad enfermedadEnfermedad) {
		this.enfermedadEnfermedad = enfermedadEnfermedad;
	}

	public int getTransmisibilidad() {
		return transmisibilidad;
	}

	public void setTransmisibilidad(int transmisibilidad) {
		this.transmisibilidad = transmisibilidad;
	}

	public int getPoblacionAfectada() {
		return poblacionAfectada;
	}

	public void setPoblacionAfectada(int poblacionAfectada) {
		this.poblacionAfectada = poblacionAfectada;
	}

	public Date getPrimerbroteDate() {
		return primerbroteDate;
	}

	public void setPrimerbroteDate(Date primerbroteDate) {
		this.primerbroteDate = primerbroteDate;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getAnotacioneString() {
		return anotacioneString;
	}

	public void setAnotacioneString(String anotacioneString) {
		this.anotacioneString = anotacioneString;
	}
}
