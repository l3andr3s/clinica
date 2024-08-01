package logico;

import java.awt.AWTEvent;
import java.util.Date;

public class Consulta {
	private String citaCodigo;
	private String observaciones;
	private String diagnostico;
	private String receta;
	private boolean historial;
	private int estatura;
	private int peso;
	private String tipoSangre;
	private String RH;
	private Date fecha;
	private Usuario usuario;

	// Getters y Setters

	public String getCitaCodigo() {
		return citaCodigo;
	}

	public void setCitaCodigo(String citaCodigo) {
		this.citaCodigo = citaCodigo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getReceta() {
		return receta;
	}

	public void setReceta(String receta) {
		this.receta = receta;
	}

	public boolean isHistorial() {
		return historial;
	}

	public void setHistorial(boolean historial) {
		this.historial = historial;
	}

	public int getEstatura() {
		return estatura;
	}

	public void setEstatura(int estatura) {
		this.estatura = estatura;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public String getRH() {
		return RH;
	}

	public void setRH(String RH) {
		this.RH = RH;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
