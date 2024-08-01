package logico;

import java.io.Serializable;
import java.util.Date;

public class Medico extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String loginString;
	private String passwordString;
	private String consultorioString;
	private String especialidad;

	public Medico(String nombre, String apellido, String id, Date fechaNacimiento, String telefono, String direccion,
			String loginString, String passwordString, String consultorioString, String codigo, String especialidad,
			String cedula) {
		super(nombre, apellido, id, fechaNacimiento, telefono, direccion, codigo, cedula);
		this.loginString = loginString;
		this.passwordString = passwordString;
		this.consultorioString = consultorioString;
		this.especialidad = especialidad;
	}

	public String getLoginString() {
		return loginString;
	}

	public void setLoginString(String loginString) {
		this.loginString = loginString;
	}

	public String getPasswordString() {
		return passwordString;
	}

	public void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
	}

	public String getConsultorioString() {
		return consultorioString;
	}

	public void setConsultorioString(String consultorioString) {
		this.consultorioString = consultorioString;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
}
