package logico;

import java.io.Serializable;

public class ConsultaEnfermedad implements Serializable {

	private static final long serialVersionUID = 1L;
	private Consulta consulta;
	private Enfermedad enfermedad;

	public ConsultaEnfermedad(Consulta consulta, Enfermedad enfermedad) {
		this.consulta = consulta;
		this.enfermedad = enfermedad;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Enfermedad getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(Enfermedad enfermedad) {
		this.enfermedad = enfermedad;
	}
}
