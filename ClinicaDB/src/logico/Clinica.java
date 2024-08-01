package logico;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.ConexionSQL;

public class Clinica implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Clinica instance;

	public static Clinica getInstance() {
		if (instance == null) {
			instance = new Clinica();
		}
		return instance;
	}

	private Clinica() {
	}

	// Método para confirmar el login
	public boolean confirmLogin(String login, String password) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return false;
		}

		String query = "SELECT 'Medico' AS Tipo FROM Medicos WHERE Login = ? AND Password = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, login);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String tipoUsuario = rs.getString("Tipo");
				if ("Medico".equals(tipoUsuario)) {
					System.out.println("Usuario es Medico");
				}
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return false;
	}

	// Método para obtener el próximo código de usuario
	public String getNextUserCode() {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		// SQL query to extract and get the maximum numeric part of the Codigo
		String query = "SELECT MAX(CAST(SUBSTRING(Codigo, 2, LEN(Codigo) - 1) AS INT)) AS MaxCode FROM Usuarios WHERE ISNUMERIC(SUBSTRING(Codigo, 2, LEN(Codigo) - 1)) = 1";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int maxCode = rs.getInt("MaxCode");
				return "U" + String.format("%03d", maxCode + 1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return null;
	}

	// Método para insertar un médico
	public void insertarMedico(Medico medico) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryUsuarios = "INSERT INTO Usuarios (Nombre, Apellido, FechaNacimiento, Telefono, Direccion, Codigo, Cedula) VALUES (?, ?, ?, ?, ?, ?, ?)";
		String queryMedicos = "INSERT INTO Medicos (ID, Login, Password, Consultorio, Especialidad) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmtUsuarios = connection.prepareStatement(queryUsuarios,
				PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement stmtMedicos = connection.prepareStatement(queryMedicos)) {

			// Insertar en la tabla Usuarios
			stmtUsuarios.setString(1, medico.getNombre());
			stmtUsuarios.setString(2, medico.getApellido());
			stmtUsuarios.setDate(3, new java.sql.Date(medico.getFechaNacimiento().getTime()));
			stmtUsuarios.setString(4, medico.getTelefono());
			stmtUsuarios.setString(5, medico.getDireccion());
			stmtUsuarios.setString(6, medico.getCodigo());
			stmtUsuarios.setString(7, medico.getCedula());
			stmtUsuarios.executeUpdate();

			// Obtener el ID generado
			ResultSet rs = stmtUsuarios.getGeneratedKeys();
			if (rs.next()) {
				int idUsuario = rs.getInt(1);

				// Insertar en la tabla Medicos
				stmtMedicos.setInt(1, idUsuario);
				stmtMedicos.setString(2, medico.getLoginString());
				stmtMedicos.setString(3, medico.getPasswordString());
				stmtMedicos.setString(4, medico.getConsultorioString());
				stmtMedicos.setString(5, medico.getEspecialidad());
				stmtMedicos.executeUpdate();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	// Método para verificar si un consultorio está ocupado
	public boolean encontrarConsultorio(String consultorio) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return false;
		}

		String query = "SELECT COUNT(*) FROM Medicos WHERE Consultorio = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, consultorio);
			ResultSet rs = stmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return false;
	}

	// Método para obtener todos los médicos
	public List<Medico> getMedicos() {
		List<Medico> medicos = new ArrayList<>();
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return medicos;
		}

		String query = "SELECT u.ID, u.Nombre, u.Apellido, u.FechaNacimiento, u.Telefono, u.Direccion, u.Codigo, u.Cedula, m.Login, m.Password, m.Consultorio, m.Especialidad "
				+ "FROM Usuarios u JOIN Medicos m ON u.ID = m.ID";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Medico medico = new Medico(rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("ID"),
						rs.getDate("FechaNacimiento"), rs.getString("Telefono"), rs.getString("Direccion"),
						rs.getString("Login"), rs.getString("Password"), rs.getString("Consultorio"),
						rs.getString("Codigo"), rs.getString("Especialidad"), rs.getString("Cedula"));
				medicos.add(medico);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return medicos;
	}

	// Método para eliminar un médico
	public void eliminarMedico(Medico medico) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryConsultaEnfermedad = "DELETE FROM ConsultaEnfermedad WHERE ConsultaID IN (SELECT ID FROM Consultas WHERE CitaCodigo IN (SELECT Codigo FROM Citas WHERE DoctorID = ?))";
		String queryHistoriales = "DELETE FROM Historiales WHERE ConsultaID IN (SELECT ID FROM Consultas WHERE CitaCodigo IN (SELECT Codigo FROM Citas WHERE DoctorID = ?))";
		String queryConsultas = "DELETE FROM Consultas WHERE CitaCodigo IN (SELECT Codigo FROM Citas WHERE DoctorID = ?)";
		String queryCitas = "DELETE FROM Citas WHERE DoctorID = ?";
		String queryDosis = "DELETE FROM Dosis WHERE MedicoID = ?";
		String queryMedicos = "DELETE FROM Medicos WHERE ID = ?";
		String queryUsuarios = "DELETE FROM Usuarios WHERE ID = ?";

		try (PreparedStatement stmtConsultaEnfermedad = connection.prepareStatement(queryConsultaEnfermedad);
				PreparedStatement stmtHistoriales = connection.prepareStatement(queryHistoriales);
				PreparedStatement stmtConsultas = connection.prepareStatement(queryConsultas);
				PreparedStatement stmtCitas = connection.prepareStatement(queryCitas);
				PreparedStatement stmtDosis = connection.prepareStatement(queryDosis);
				PreparedStatement stmtMedicos = connection.prepareStatement(queryMedicos);
				PreparedStatement stmtUsuarios = connection.prepareStatement(queryUsuarios)) {

			// Eliminar de la tabla ConsultaEnfermedad
			stmtConsultaEnfermedad.setInt(1, Integer.parseInt(medico.getID()));
			stmtConsultaEnfermedad.executeUpdate();

			// Eliminar de la tabla Historiales
			stmtHistoriales.setInt(1, Integer.parseInt(medico.getID()));
			stmtHistoriales.executeUpdate();

			// Eliminar de la tabla Consultas
			stmtConsultas.setInt(1, Integer.parseInt(medico.getID()));
			stmtConsultas.executeUpdate();

			// Eliminar de la tabla Citas
			stmtCitas.setInt(1, Integer.parseInt(medico.getID()));
			stmtCitas.executeUpdate();

			// Eliminar de la tabla Dosis
			stmtDosis.setInt(1, Integer.parseInt(medico.getID()));
			stmtDosis.executeUpdate();

			// Eliminar de la tabla Medicos
			stmtMedicos.setInt(1, Integer.parseInt(medico.getID()));
			stmtMedicos.executeUpdate();

			// Eliminar de la tabla Usuarios
			stmtUsuarios.setInt(1, Integer.parseInt(medico.getID()));
			stmtUsuarios.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	// Método para buscar un usuario por cédula
	public Medico buscarUsuarioByCedula(String cedula) {
		Medico medico = null;
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT u.ID, u.Nombre, u.Apellido, u.FechaNacimiento, u.Telefono, u.Direccion, u.Codigo, u.Cedula, m.Login, m.Password, m.Consultorio, m.Especialidad "
				+ "FROM Usuarios u JOIN Medicos m ON u.ID = m.ID WHERE u.Cedula = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, cedula);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				medico = new Medico(rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("ID"),
						rs.getDate("FechaNacimiento"), rs.getString("Telefono"), rs.getString("Direccion"),
						rs.getString("Login"), rs.getString("Password"), rs.getString("Consultorio"),
						rs.getString("Codigo"), rs.getString("Especialidad"), rs.getString("Cedula"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return medico;
	}

	// Método para actualizar un médico
	public void actualizarMedico(Medico medico) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryUsuarios = "UPDATE Usuarios SET Nombre = ?, Apellido = ?, FechaNacimiento = ?, Telefono = ?, Direccion = ?, Codigo = ?, Cedula = ? WHERE Codigo = ?";
		String queryMedicos = "UPDATE Medicos SET Login = ?, Password = ?, Consultorio = ?, Especialidad = ? WHERE ID = (SELECT ID FROM Usuarios WHERE Codigo = ?)";

		try (PreparedStatement stmtUsuarios = connection.prepareStatement(queryUsuarios);
				PreparedStatement stmtMedicos = connection.prepareStatement(queryMedicos)) {

			// Actualizar en la tabla Usuarios
			stmtUsuarios.setString(1, medico.getNombre());
			stmtUsuarios.setString(2, medico.getApellido());
			stmtUsuarios.setDate(3, new java.sql.Date(medico.getFechaNacimiento().getTime()));
			stmtUsuarios.setString(4, medico.getTelefono());
			stmtUsuarios.setString(5, medico.getDireccion());
			stmtUsuarios.setString(6, medico.getCodigo());
			stmtUsuarios.setString(7, medico.getCedula());
			stmtUsuarios.setString(8, medico.getCodigo());
			stmtUsuarios.executeUpdate();

			// Actualizar en la tabla Medicos
			stmtMedicos.setString(1, medico.getLoginString());
			stmtMedicos.setString(2, medico.getPasswordString());
			stmtMedicos.setString(3, medico.getConsultorioString());
			stmtMedicos.setString(4, medico.getEspecialidad());
			stmtMedicos.setString(5, medico.getCodigo());
			stmtMedicos.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	// Método para obtener el próximo código de enfermedad
	public String getNextEnfermedadCode() {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT MAX(CAST(SUBSTRING(Codigo, 2, LEN(Codigo) - 1) AS INT)) AS MaxCode FROM Enfermedades";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int maxCode = rs.getInt("MaxCode");
				return "E" + String.format("%03d", maxCode + 1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return null;
	}

	// Método para insertar una nueva enfermedad
	public void insertarEnfermedad(Enfermedad enfermedad) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String query = "INSERT INTO Enfermedades (Codigo, Nombre, Tipo, Informacion, Descubierta, Transmisibilidad) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, enfermedad.getCodigoString());
			stmt.setString(2, enfermedad.getNombreString());
			stmt.setString(3, enfermedad.getTipoString());
			stmt.setString(4, enfermedad.getInformacionString());
			stmt.setDate(5, new java.sql.Date(enfermedad.getDescubierta().getTime()));
			stmt.setInt(6, enfermedad.getTransmisibilidad());

			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	// Método para insertar una vacuna
	public void insertarVacuna(Vacuna vacuna) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryVacuna = "INSERT INTO Vacunas (Codigo, Nombre, Laboratorio, Administracion) VALUES (?, ?, ?, ?)";
		String queryVacunaEnfermedad = "INSERT INTO VacunaEnfermedad (VacunaCodigo, EnfermedadCodigo) VALUES (?, ?)";

		try (PreparedStatement stmtVacuna = connection.prepareStatement(queryVacuna);
				PreparedStatement stmtVacunaEnfermedad = connection.prepareStatement(queryVacunaEnfermedad)) {

			// Insertar en la tabla Vacunas
			stmtVacuna.setString(1, vacuna.getCodigo());
			stmtVacuna.setString(2, vacuna.getNombreString());
			stmtVacuna.setString(3, vacuna.getLaboratorioString());
			stmtVacuna.setString(4, vacuna.getAdministracionString());
			stmtVacuna.executeUpdate();

			// Insertar en la tabla VacunaEnfermedad
			for (Enfermedad enfermedad : vacuna.getEnfermedades()) {
				stmtVacunaEnfermedad.setString(1, vacuna.getCodigo());
				stmtVacunaEnfermedad.setString(2, enfermedad.getCodigoString());
				stmtVacunaEnfermedad.executeUpdate();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	// Método para obtener todas las enfermedades
	public List<Enfermedad> getEnfermedades() {
		List<Enfermedad> enfermedades = new ArrayList<>();
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return enfermedades;
		}

		String query = "SELECT Codigo, Nombre, Tipo, Informacion, Descubierta, Transmisibilidad FROM Enfermedades";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Enfermedad enfermedad = new Enfermedad(rs.getString("Codigo"), rs.getString("Nombre"),
						rs.getString("Tipo"), rs.getString("Informacion"), rs.getDate("Descubierta"),
						rs.getInt("Transmisibilidad"));
				enfermedades.add(enfermedad);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return enfermedades;
	}

	// Método para buscar una enfermedad por código
	public Enfermedad buscarEnfermedadByCodigo(String codigo) {
		Enfermedad enfermedad = null;
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT Codigo, Nombre, Tipo, Informacion, Descubierta, Transmisibilidad FROM Enfermedades WHERE Codigo = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, codigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				enfermedad = new Enfermedad(rs.getString("Codigo"), rs.getString("Nombre"), rs.getString("Tipo"),
						rs.getString("Informacion"), rs.getDate("Descubierta"), rs.getInt("Transmisibilidad"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return enfermedad;
	}

	// Método para eliminar una enfermedad
	public void eliminarEnfermedad(Enfermedad enfermedad) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		try {
			connection.setAutoCommit(false);

			// Eliminar la enfermedad
			String query = "DELETE FROM Enfermedades WHERE Codigo = ?";
			try (PreparedStatement stmt = connection.prepareStatement(query)) {
				stmt.setString(1, enfermedad.getCodigoString());
				stmt.executeUpdate();
			}

			connection.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException autoCommitEx) {
				autoCommitEx.printStackTrace();
			}
			ConexionSQL.closeConnection(connection);
		}
	}

	public void actualizarEnfermedad(Enfermedad enfermedad) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String query = "UPDATE Enfermedades SET Nombre = ?, Tipo = ?, Informacion = ?, Descubierta = ?, Transmisibilidad = ? WHERE Codigo = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, enfermedad.getNombreString());
			stmt.setString(2, enfermedad.getTipoString());
			stmt.setString(3, enfermedad.getInformacionString());
			stmt.setDate(4, new java.sql.Date(enfermedad.getDescubierta().getTime()));
			stmt.setInt(5, enfermedad.getTransmisibilidad());
			stmt.setString(6, enfermedad.getCodigoString());

			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	// Método para obtener el próximo código de vacuna
	public String getNextVacunaCode() {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT MAX(CAST(SUBSTRING(Codigo, 2, LEN(Codigo) - 1) AS INT)) AS MaxCode FROM Vacunas";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int maxCode = rs.getInt("MaxCode");
				return "V" + String.format("%03d", maxCode + 1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return null;
	}

	public List<Vacuna> getVacunas() {
		List<Vacuna> vacunas = new ArrayList<>();
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return vacunas;
		}

		String query = "SELECT v.Codigo, v.Nombre, v.Laboratorio, v.Administracion, ve.EnfermedadCodigo, e.Nombre AS EnfermedadNombre "
				+ "FROM Vacunas v " + "LEFT JOIN VacunaEnfermedad ve ON v.Codigo = ve.VacunaCodigo "
				+ "LEFT JOIN Enfermedades e ON ve.EnfermedadCodigo = e.Codigo " + "ORDER BY v.Codigo DESC"; // Orden
																											// descendente
																											// por
																											// Código

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			Map<String, Vacuna> vacunaMap = new HashMap<>();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				Vacuna vacuna = vacunaMap.get(codigo);
				if (vacuna == null) {
					vacuna = new Vacuna(codigo, rs.getString("Nombre"), rs.getString("Laboratorio"),
							rs.getString("Administracion"), new ArrayList<>());
					vacunaMap.put(codigo, vacuna);
				}

				String enfermedadCodigo = rs.getString("EnfermedadCodigo");
				if (enfermedadCodigo != null) {
					Enfermedad enfermedad = new Enfermedad(enfermedadCodigo, rs.getString("EnfermedadNombre"), "", "",
							null, 0);
					vacuna.insertarEnfermedad(enfermedad);
				}
			}

			vacunas.addAll(vacunaMap.values());
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return vacunas;
	}

	public Vacuna buscarVacunaByCodigo(String codigo) {
		Vacuna vacuna = null;
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT v.Codigo, v.Nombre, v.Laboratorio, v.Administracion, ve.EnfermedadCodigo, e.Nombre AS EnfermedadNombre "
				+ "FROM Vacunas v " + "LEFT JOIN VacunaEnfermedad ve ON v.Codigo = ve.VacunaCodigo "
				+ "LEFT JOIN Enfermedades e ON ve.EnfermedadCodigo = e.Codigo " + "WHERE v.Codigo = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, codigo);
			ResultSet rs = stmt.executeQuery();
			Map<String, Vacuna> vacunaMap = new HashMap<>();

			while (rs.next()) {
				if (vacuna == null) {
					vacuna = new Vacuna(codigo, rs.getString("Nombre"), rs.getString("Laboratorio"),
							rs.getString("Administracion"), new ArrayList<>());
				}

				String enfermedadCodigo = rs.getString("EnfermedadCodigo");
				if (enfermedadCodigo != null) {
					Enfermedad enfermedad = new Enfermedad(enfermedadCodigo, rs.getString("EnfermedadNombre"), "", "",
							null, 0);
					vacuna.insertarEnfermedad(enfermedad);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return vacuna;
	}

	public void eliminarVacuna(Vacuna vacuna) {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryDosis = "DELETE FROM Dosis WHERE VacunaCodigo = ?";
		String queryVacunaEnfermedad = "DELETE FROM VacunaEnfermedad WHERE VacunaCodigo = ?";
		String queryVacunas = "DELETE FROM Vacunas WHERE Codigo = ?";

		try (PreparedStatement stmtDosis = connection.prepareStatement(queryDosis);
				PreparedStatement stmtVacunaEnfermedad = connection.prepareStatement(queryVacunaEnfermedad);
				PreparedStatement stmtVacunas = connection.prepareStatement(queryVacunas)) {

			// Eliminar de la tabla Dosis
			stmtDosis.setString(1, vacuna.getCodigo());
			stmtDosis.executeUpdate();

			// Eliminar de la tabla VacunaEnfermedad
			stmtVacunaEnfermedad.setString(1, vacuna.getCodigo());
			stmtVacunaEnfermedad.executeUpdate();

			// Eliminar de la tabla Vacunas
			stmtVacunas.setString(1, vacuna.getCodigo());
			stmtVacunas.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	// Método para insertar una cita
	public void insertarCita(Cita cita) throws SQLException {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryCitas = "INSERT INTO Citas (Codigo, Fecha, Notas, Especialidad, DoctorID, UsuarioID, Estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
		String queryUsuario = "SELECT ID FROM Usuarios WHERE Codigo = ?";
		String queryDoctor = "SELECT ID FROM Medicos WHERE Login = ?";

		try (PreparedStatement stmtCitas = connection.prepareStatement(queryCitas);
				PreparedStatement stmtUsuario = connection.prepareStatement(queryUsuario);
				PreparedStatement stmtDoctor = connection.prepareStatement(queryDoctor)) {

			// Obtener ID del usuario
			stmtUsuario.setString(1, cita.getUserUsuario().getCodigo());
			ResultSet rsUsuario = stmtUsuario.executeQuery();
			int usuarioID = 0;
			if (rsUsuario.next()) {
				usuarioID = rsUsuario.getInt("ID");
			} else {
				throw new SQLException("Usuario no encontrado en la tabla Usuarios.");
			}

			// Obtener ID del doctor
			stmtDoctor.setString(1, cita.getDoctor());
			ResultSet rsDoctor = stmtDoctor.executeQuery();
			int doctorID = 0;
			if (rsDoctor.next()) {
				doctorID = rsDoctor.getInt("ID");
			} else {
				throw new SQLException("Doctor no encontrado en la tabla Medicos.");
			}

			// Insertar en la tabla Citas
			stmtCitas.setString(1, cita.getCodigo());
			stmtCitas.setDate(2, new java.sql.Date(cita.getFecha().getTime()));
			stmtCitas.setString(3, cita.getNotas());
			stmtCitas.setString(4, cita.getEspecialidad());
			stmtCitas.setInt(5, doctorID);
			stmtCitas.setInt(6, usuarioID);
			stmtCitas.setString(7, cita.getEstado()); // Asegurar que el estado es "pendiente"
			stmtCitas.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	// Método para buscar un paciente por cédula
	public Paciente buscarPacienteByCedula(String cedula) {
		Paciente paciente = null;
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT u.Nombre, u.Apellido, u.FechaNacimiento, u.Telefono, u.Direccion, u.Codigo, u.Cedula, "
				+ "p.ID, p.TipoSangre, p.RH, p.Ocupacion, p.TelefonoOpc " + "FROM Usuarios u "
				+ "JOIN Pacientes p ON u.ID = p.ID " + "WHERE u.Cedula = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, cedula);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				paciente = new Paciente(rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("Codigo"),
						rs.getDate("FechaNacimiento"), rs.getString("Ocupacion"), rs.getString("Telefono"),
						rs.getString("Direccion"), rs.getString("Codigo"), rs.getString("TipoSangre"),
						rs.getString("RH"), rs.getString("TelefonoOpc"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return paciente;
	}

	// Método para buscar médicos por especialidad
	public List<Medico> buscarmedicosByEspecialidad(String especialidad) {
		List<Medico> medicos = new ArrayList<>();
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return medicos;
		}

		String query = "SELECT u.ID, u.Nombre, u.Apellido, u.FechaNacimiento, u.Telefono, u.Direccion, u.Codigo, u.Cedula, m.Login, m.Password, m.Consultorio, m.Especialidad "
				+ "FROM Usuarios u JOIN Medicos m ON u.ID = m.ID WHERE m.Especialidad = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, especialidad);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Medico medico = new Medico(rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("ID"),
						rs.getDate("FechaNacimiento"), rs.getString("Telefono"), rs.getString("Direccion"),
						rs.getString("Login"), rs.getString("Password"), rs.getString("Consultorio"),
						rs.getString("Codigo"), rs.getString("Especialidad"), rs.getString("Cedula"));
				medicos.add(medico);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return medicos;
	}

	public String getNextPatientCode() {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT MAX(CAST(SUBSTRING(Codigo, 2, LEN(Codigo) - 1) AS INT)) AS MaxCode FROM Usuarios";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int maxCode = rs.getInt("MaxCode");
				return "U" + String.format("%03d", maxCode + 1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return null;
	}

	// Método para insertar un usuario y vincularlo como paciente
	public void insertarUsuario(Paciente paciente) throws SQLException {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		// Verificar si el usuario ya existe
		String queryExistencia = "SELECT ID FROM Usuarios WHERE Cedula = ?";
		try (PreparedStatement stmtExistencia = connection.prepareStatement(queryExistencia)) {
			stmtExistencia.setString(1, paciente.getCedula());
			ResultSet rs = stmtExistencia.executeQuery();
			if (rs.next()) {
				throw new SQLException("Usuario con esta cédula ya existe.");
			}
		}

		String queryUsuarios = "INSERT INTO Usuarios (Nombre, Apellido, FechaNacimiento, Telefono, Direccion, Codigo, Cedula) VALUES (?, ?, ?, ?, ?, ?, ?)";
		String queryPacientes = "INSERT INTO Pacientes (ID, TipoSangre, RH, Ocupacion, TelefonoOpc) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmtUsuarios = connection.prepareStatement(queryUsuarios,
				PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement stmtPacientes = connection.prepareStatement(queryPacientes)) {

			// Insertar en la tabla Usuarios
			stmtUsuarios.setString(1, paciente.getNombre());
			stmtUsuarios.setString(2, paciente.getApellido());
			stmtUsuarios.setDate(3, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
			stmtUsuarios.setString(4, paciente.getTelefono());
			stmtUsuarios.setString(5, paciente.getDireccion());
			stmtUsuarios.setString(6, paciente.getCodigo());
			stmtUsuarios.setString(7, paciente.getCedula());
			stmtUsuarios.executeUpdate();

			// Obtener el ID generado
			ResultSet rs = stmtUsuarios.getGeneratedKeys();
			if (rs.next()) {
				int idUsuario = rs.getInt(1);

				// Insertar en la tabla Pacientes
				stmtPacientes.setInt(1, idUsuario);
				stmtPacientes.setString(2, paciente.getTipoSangre());
				stmtPacientes.setString(3, paciente.getRh());
				stmtPacientes.setString(4, paciente.getOcupacion());
				stmtPacientes.setString(5, paciente.getTelefonoOpc());
				stmtPacientes.executeUpdate();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	public String getNextCitaCode() {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT MAX(CAST(SUBSTRING(Codigo, 2, LEN(Codigo) - 1) AS INT)) AS MaxCode FROM Citas";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int maxCode = rs.getInt("MaxCode");
				return "C" + String.format("%03d", maxCode + 1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return null;
	}

	public Usuario buscarUsuarioByCodigo(String codigo) {
		Usuario usuario = null;
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT * FROM Usuarios WHERE Codigo = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, codigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				usuario = new Usuario(rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("ID"),
						rs.getDate("FechaNacimiento"), rs.getString("Telefono"), rs.getString("Direccion"),
						rs.getString("Codigo"), rs.getString("Cedula"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return usuario;
	}

	// Método para insertar una consulta
	public void insertarConsulta(Consulta consulta) throws SQLException {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryConsultas = "INSERT INTO Consultas (CitaCodigo, Observaciones, Diagnostico, Receta, Historial, Estatura, Peso, TipoSangre, RH, Fecha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmtConsultas = connection.prepareStatement(queryConsultas)) {
			stmtConsultas.setString(1, consulta.getCitaCodigo());
			stmtConsultas.setString(2, consulta.getObservaciones());
			stmtConsultas.setString(3, consulta.getDiagnostico());
			stmtConsultas.setString(4, consulta.getReceta());
			stmtConsultas.setBoolean(5, consulta.isHistorial());
			stmtConsultas.setInt(6, consulta.getEstatura());
			stmtConsultas.setInt(7, consulta.getPeso());
			stmtConsultas.setString(8, consulta.getTipoSangre());
			stmtConsultas.setString(9, consulta.getRH());
			stmtConsultas.setDate(10, new java.sql.Date(consulta.getFecha().getTime()));

			stmtConsultas.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	public List<Cita> getCitasPendientes() throws SQLException {
		List<Cita> citasPendientes = new ArrayList<>();
		String sql = "SELECT c.Codigo, c.Fecha, c.Notas, c.Especialidad, m.Login AS Doctor, u.ID, u.Nombre, u.Apellido, c.estado "
				+ "FROM Citas c " + "JOIN Usuarios u ON c.UsuarioID = u.ID " + "JOIN Medicos m ON c.DoctorID = m.ID "
				+ "WHERE c.estado = 'pendiente'";

		try (Connection con = ConexionSQL.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				Date fecha = rs.getDate("Fecha");
				String notas = rs.getString("Notas");
				String especialidad = rs.getString("Especialidad");
				String doctor = rs.getString("Doctor");
				int usuarioId = rs.getInt("ID");
				String nombreUsuario = rs.getString("Nombre");
				String apellidoUsuario = rs.getString("Apellido");

				Usuario usuario = new Usuario(String.valueOf(usuarioId), nombreUsuario, apellidoUsuario);
				Cita cita = new Cita(codigo, fecha, notas, especialidad, doctor, usuario);
				cita.setEstado(rs.getString("estado"));
				citasPendientes.add(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return citasPendientes;
	}

	public Cita buscarCitaByCodigo(String codigo) {
		Cita cita = null;
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return null;
		}

		String query = "SELECT c.Codigo, c.Fecha, c.Notas, c.Especialidad, m.Login AS DoctorLogin, c.UsuarioID, "
				+ "u.Nombre AS UsuarioNombre, u.Apellido AS UsuarioApellido, u.Cedula, u.FechaNacimiento, "
				+ "p.TipoSangre, p.RH " + "FROM Citas c " + "JOIN Usuarios u ON c.UsuarioID = u.ID "
				+ "JOIN Pacientes p ON u.ID = p.ID " + "JOIN Medicos m ON c.DoctorID = m.ID " + "WHERE c.Codigo = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, codigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("UsuarioID"), rs.getString("UsuarioNombre"),
						rs.getString("UsuarioApellido"));
				usuario.setCedula(rs.getString("Cedula"));
				usuario.setFechaNacimiento(rs.getDate("FechaNacimiento"));

				Paciente paciente = new Paciente(rs.getString("UsuarioNombre"), rs.getString("UsuarioApellido"),
						rs.getString("Cedula"), rs.getDate("FechaNacimiento"), rs.getString("Especialidad"), "", "", "",
						rs.getString("TipoSangre"), rs.getString("RH"), "");

				cita = new Cita(rs.getString("Codigo"), rs.getDate("Fecha"), rs.getString("Notas"),
						rs.getString("Especialidad"), rs.getString("DoctorLogin"), usuario);
				cita.setUserUsuario(paciente);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return cita;
	}

	public Paciente buscarPacienteByCodigo(String codigo) {
		Connection con = ConexionSQL.getConnection();
		Paciente paciente = null;
		try {
			String query = "SELECT u.ID, u.Nombre, u.Apellido, u.FechaNacimiento, u.Telefono, u.Direccion, u.Codigo, u.Cedula, "
					+ "p.TipoSangre, p.RH, p.Ocupacion, p.TelefonoOpc " + "FROM Usuarios u "
					+ "JOIN Pacientes p ON u.ID = p.ID " + "WHERE u.Codigo = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, codigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				paciente = new Paciente(rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("Cedula"),
						rs.getDate("FechaNacimiento"), rs.getString("Ocupacion"), rs.getString("Telefono"),
						rs.getString("Direccion"), rs.getString("Codigo"), rs.getString("TipoSangre"),
						rs.getString("RH"), rs.getString("TelefonoOpc"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(con);
		}
		return paciente;
	}

	public void actualizarEstadoCita(String codigoCita, String nuevoEstado) throws SQLException {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String query = "UPDATE Citas SET estado = ? WHERE Codigo = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, nuevoEstado);
			stmt.setString(2, codigoCita);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	public List<Consulta> getConsultasByPaciente(String cedula) {
		List<Consulta> consultas = new ArrayList<>();
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return consultas;
		}

		String query = "SELECT co.CitaCodigo, co.Observaciones, co.Diagnostico, co.Receta, co.Historial, co.Estatura, co.Peso, co.TipoSangre, co.RH, co.Fecha "
				+ "FROM Consultas co " + "JOIN Citas ci ON co.CitaCodigo = ci.Codigo "
				+ "JOIN Usuarios u ON ci.UsuarioID = u.ID " + "WHERE u.Cedula = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, cedula);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Consulta consulta = new Consulta();
				consulta.setCitaCodigo(rs.getString("CitaCodigo"));
				consulta.setObservaciones(rs.getString("Observaciones"));
				consulta.setDiagnostico(rs.getString("Diagnostico"));
				consulta.setReceta(rs.getString("Receta"));
				consulta.setHistorial(rs.getBoolean("Historial"));
				consulta.setEstatura(rs.getInt("Estatura"));
				consulta.setPeso(rs.getInt("Peso"));
				consulta.setTipoSangre(rs.getString("TipoSangre"));
				consulta.setRH(rs.getString("RH"));
				consulta.setFecha(rs.getDate("Fecha"));
				consultas.add(consulta);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return consultas;
	}

	public void insertarHistorial(String citaCodigo) throws SQLException {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryHistorial = "INSERT INTO Historiales (ConsultaID) VALUES ((SELECT TOP 1 ID FROM Consultas WHERE CitaCodigo = ? ORDER BY ID))";

		try (PreparedStatement stmtHistorial = connection.prepareStatement(queryHistorial)) {
			stmtHistorial.setString(1, citaCodigo);
			stmtHistorial.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	public void insertarConsultaEnfermedad(int consultaID, String codigoEnfermedad) throws SQLException {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String query = "INSERT INTO ConsultaEnfermedad (ConsultaID, EnfermedadCodigo) VALUES (?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, consultaID);
			stmt.setString(2, codigoEnfermedad);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	public int obtenerIDConsulta(String citaCodigo) throws SQLException {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return -1;
		}

		String query = "SELECT ID FROM Consultas WHERE CitaCodigo = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, citaCodigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("ID");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return -1;
	}

	public List<Vacuna> getVacunasByPaciente(String cedulaPaciente) {
		List<Vacuna> vacunas = new ArrayList<>();
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return vacunas;
		}

		String query = "SELECT v.Nombre, d.Fecha, m.Login AS Doctor " + "FROM Dosis d "
				+ "JOIN Vacunas v ON d.VacunaCodigo = v.Codigo " + "JOIN Pacientes p ON d.PacienteID = p.ID "
				+ "JOIN Usuarios u ON p.ID = u.ID " + "JOIN Medicos m ON d.MedicoID = m.ID " + "WHERE u.Cedula = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, cedulaPaciente);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Vacuna vacuna = new Vacuna(rs.getString("Nombre"), rs.getDate("Fecha"), rs.getString("Doctor"));
				vacunas.add(vacuna);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return vacunas;
	}

	public void aplicarVacuna(String cedulaPaciente, Date fecha, Vacuna vacuna, String doctorLogin)
			throws SQLException {
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return;
		}

		String queryPaciente = "SELECT p.ID FROM Pacientes p JOIN Usuarios u ON p.ID = u.ID WHERE u.Cedula = ?";
		String queryVacuna = "SELECT Codigo FROM Vacunas WHERE Nombre = ?";
		String queryDoctor = "SELECT ID FROM Medicos WHERE Login = ?";
		String queryDosis = "INSERT INTO Dosis (VacunaCodigo, PacienteID, MedicoID, Fecha) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmtPaciente = connection.prepareStatement(queryPaciente);
				PreparedStatement stmtVacuna = connection.prepareStatement(queryVacuna);
				PreparedStatement stmtDoctor = connection.prepareStatement(queryDoctor);
				PreparedStatement stmtDosis = connection.prepareStatement(queryDosis)) {

			// Obtener ID del paciente
			stmtPaciente.setString(1, cedulaPaciente);
			ResultSet rsPaciente = stmtPaciente.executeQuery();
			int pacienteID = 0;
			if (rsPaciente.next()) {
				pacienteID = rsPaciente.getInt("ID");
			} else {
				throw new SQLException("Paciente no encontrado.");
			}

			// Obtener código de la vacuna
			stmtVacuna.setString(1, vacuna.getNombreString());
			ResultSet rsVacuna = stmtVacuna.executeQuery();
			String vacunaCodigo = "";
			if (rsVacuna.next()) {
				vacunaCodigo = rsVacuna.getString("Codigo");
			} else {
				throw new SQLException("Vacuna no encontrada.");
			}

			// Obtener ID del doctor
			stmtDoctor.setString(1, doctorLogin);
			ResultSet rsDoctor = stmtDoctor.executeQuery();
			int doctorID = 0;
			if (rsDoctor.next()) {
				doctorID = rsDoctor.getInt("ID");
			} else {
				throw new SQLException("Doctor no encontrado.");
			}

			// Insertar en la tabla Dosis
			stmtDosis.setString(1, vacunaCodigo);
			stmtDosis.setInt(2, pacienteID);
			stmtDosis.setInt(3, doctorID);
			stmtDosis.setDate(4, new java.sql.Date(fecha.getTime()));
			stmtDosis.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
	}

	public List<ConsultaEnfermedad> getConsultasEnfermedades() throws SQLException {
		List<ConsultaEnfermedad> consultasEnfermedades = new ArrayList<>();
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return consultasEnfermedades;
		}

		String query = "SELECT ce.ConsultaID, ce.EnfermedadCodigo, co.CitaCodigo, ci.UsuarioID, u.ID AS UsuarioID, u.Nombre, u.Apellido, e.Nombre AS EnfermedadNombre "
				+ "FROM ConsultaEnfermedad ce " + "JOIN Consultas co ON ce.ConsultaID = co.ID "
				+ "JOIN Citas ci ON co.CitaCodigo = ci.Codigo " + "JOIN Usuarios u ON ci.UsuarioID = u.ID "
				+ "JOIN Enfermedades e ON ce.EnfermedadCodigo = e.Codigo";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("UsuarioID"), rs.getString("Nombre"),
						rs.getString("Apellido"));
				Consulta consulta = new Consulta();
				consulta.setUsuario(usuario);
				consulta.setCitaCodigo(rs.getString("CitaCodigo"));

				Enfermedad enfermedad = new Enfermedad(rs.getString("EnfermedadCodigo"),
						rs.getString("EnfermedadNombre"), "", "", null, 0);
				ConsultaEnfermedad consultaEnfermedad = new ConsultaEnfermedad(consulta, enfermedad);
				consultasEnfermedades.add(consultaEnfermedad);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return consultasEnfermedades;
	}

	public List<Dosis> getDosisList() throws SQLException {
		List<Dosis> dosisList = new ArrayList<>();
		Connection connection = ConexionSQL.getConnection();
		if (connection == null) {
			return dosisList;
		}

		String query = "SELECT d.ID, d.Fecha, d.VacunaCodigo, v.Nombre AS VacunaNombre, d.PacienteID, u.ID AS UsuarioID, u.Nombre, u.Apellido "
				+ "FROM Dosis d " + "JOIN Vacunas v ON d.VacunaCodigo = v.Codigo "
				+ "JOIN Usuarios u ON d.PacienteID = u.ID";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Paciente paciente = new Paciente(rs.getString("Nombre"), rs.getString("Apellido"),
						rs.getString("UsuarioID"), null, "", "", "", "", "", "", "");
				Vacuna vacuna = new Vacuna(rs.getString("VacunaCodigo"), rs.getString("VacunaNombre"), "", "",
						new ArrayList<>());
				Dosis dosis = new Dosis(rs.getInt("ID"), rs.getDate("Fecha"), vacuna, paciente);
				dosisList.add(dosis);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ConexionSQL.closeConnection(connection);
		}
		return dosisList;
	}

}
