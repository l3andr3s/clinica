package logico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Paciente extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tipoSangre;
	private String rh;
	private String ocupacion;
	private String telefonoOpc;
	private ArrayList<Dosis> misDosis;
	private ArrayList<Cita> misCitas;

	public Paciente(String nombre, String apellido, String cedula, Date fechaNacimiento, String ocupacion,
			String telefono, String direccion, String codigo, String tipoSangre, String rh, String telefonoOpc) {
		super(nombre, apellido, cedula, fechaNacimiento, telefono, direccion, codigo, cedula);
		this.tipoSangre = tipoSangre;
		this.rh = rh;
		this.ocupacion = ocupacion;
		this.misDosis = new ArrayList<>();
		this.misCitas = new ArrayList<>();
		this.telefonoOpc = telefonoOpc;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public ArrayList<Dosis> getMisDosis() {
		return misDosis;
	}

	public void setMisDosis(ArrayList<Dosis> misDosis) {
		this.misDosis = misDosis;
	}

	public void ingresarDosis(Dosis vaccine) {
		misDosis.add(vaccine);
	}

	public ArrayList<Cita> getMisCitas() {
		return misCitas;
	}

	public void setMisCitas(ArrayList<Cita> misCitas) {
		this.misCitas = misCitas;
	}

	public void insertarCita(Cita nueva) {
		misCitas.add(nueva);
	}

	public String getTelefonoOpc() {
		return telefonoOpc;
	}

	public void setTelefonoOpc(String telefonoOpc) {
		this.telefonoOpc = telefonoOpc;
	}

	public String getRh() {
		return rh;
	}

	public void setRh(String rh) {
		this.rh = rh;
	}
}