package logico;

import java.io.Serializable;

public class Historial implements Serializable {

	private static final long serialVersionUID = 1L;
	private Consulta consultas;

	public Historial(Consulta consultas) {
		super();
		this.consultas = consultas;
	}

	public Consulta getConsultas() {
		return consultas;
	}

	public void setConsultas(Consulta consultas) {
		this.consultas = consultas;
	}

}
