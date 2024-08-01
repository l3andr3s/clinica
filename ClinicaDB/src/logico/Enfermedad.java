package logico;

import java.io.Serializable;
import java.util.Date;

public class Enfermedad implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoString;
	private String nombreString;
	private String tipoString;
	private String informacionString;
	private Date descubierta;
	private int transmisibilidad;
	/*
	 * private int poblacionAfectada; private Date primerbroteDate; private Date
	 * fechaActual;
	 */

	public Enfermedad(String codigo, String nombreString, String tipoString, String informacion, Date descubrimiento,
			int transmisibilidad) {
		super();
		this.codigoString = codigo;
		this.nombreString = nombreString;
		this.tipoString = tipoString;
		this.informacionString = informacion;
		this.descubierta = descubrimiento;
		this.transmisibilidad = transmisibilidad;
	}

	public String getNombreString() {
		return nombreString;
	}

	public void setNombreString(String nombreString) {
		this.nombreString = nombreString;
	}

	public String getTipoString() {
		return tipoString;
	}

	public void setTipoString(String tipoString) {
		this.tipoString = tipoString;
	}

	public String getCodigoString() {
		return codigoString;
	}

	public void setCodigoString(String codigoString) {
		this.codigoString = codigoString;
	}

	public String getInformacionString() {
		return informacionString;
	}

	public void setInformacionString(String informacionString) {
		this.informacionString = informacionString;
	}

	public Date getDescubierta() {
		return descubierta;
	}

	public void setDescubierta(Date descubierta) {
		this.descubierta = descubierta;
	}

	public int getTransmisibilidad() {
		return transmisibilidad;
	}

	public void setTransmisibilidad(int transmisibilidad) {
		this.transmisibilidad = transmisibilidad;
	}

}