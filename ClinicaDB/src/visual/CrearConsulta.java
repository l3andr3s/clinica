package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cita;
import logico.Clinica;
import logico.Consulta;
import logico.Enfermedad;
import logico.Paciente;
import logico.Usuario;
import logico.Vacuna;

public class CrearConsulta extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel modelCitas;
	private JTable tableCitas;
	private Cita citaSelect = null;
	private JButton btnConsultarCita;
	private JTextField txtCedula;
	private JSpinner spnFechaNacimiento;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JTextField txtTipoSangre;
	private JTextField txtRH;
	private JSpinner spnEstatura;
	private JSpinner spnPeso;
	private JTextArea txtObservaciones;
	private JTextArea txtReceta;
	private JTextArea txtDiagnostico;
	private DefaultTableModel modelEnfermedades;
	private JTable tableEnfermedades;
	private DefaultTableModel modelEnfermedadesPaciente;
	private JTable tableEnfermedadesPaciente;
	private JRadioButton rdbtnHistorialSi;
	private JRadioButton rdbtnHistorialNo;
	private ButtonGroup historialGroup;
	private DefaultTableModel modelHistorial;
	private JTable tableHistorial;
	private DefaultTableModel modelVacunas;
	private JTable tableVacunas;
	private JTextField txtNombresVac;
	private JTextField txtApellidosVac;
	private JTextField txtDoctor;
	private JSpinner spnFecha;
	private JComboBox<Vacuna> cbVacuna;

	public static void main(String[] args) {
		try {
			CrearConsulta dialog = new CrearConsulta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CrearConsulta() throws SQLException {
		setTitle("Listado de Citas y Consultas");
		setBounds(100, 100, 950, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout());

		initializeComponents();

		JPanel panelCitas = new JPanel();
		panelCitas.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelCitas, "Citas");

		JPanel panelConsultas = new JPanel();
		panelConsultas.setLayout(null);
		contentPanel.add(panelConsultas, "Consultas");

		JPanel panelHistorialMedico = new JPanel();
		panelHistorialMedico.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelHistorialMedico, "Historial Médico");

		JPanel panelVacunas = new JPanel();
		panelVacunas.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelVacunas, "Vacuna");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JToggleButton tglbtnCitas = new JToggleButton("Citas");
		tglbtnCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (contentPanel.getLayout());
				cl.show(contentPanel, "Citas");
				btnConsultarCita.setEnabled(false);
			}
		});
		menuBar.add(tglbtnCitas);

		JToggleButton tglbtnConsultas = new JToggleButton("Consultas");
		tglbtnConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (contentPanel.getLayout());
				cl.show(contentPanel, "Consultas");
				cargarDatosPaciente();
			}
		});
		menuBar.add(tglbtnConsultas);

		JToggleButton tglbtnHistorialMedico = new JToggleButton("Historial Médico");
		tglbtnHistorialMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (contentPanel.getLayout());
				cl.show(contentPanel, "Historial Médico");
				cargarHistorialMedico();
			}
		});
		menuBar.add(tglbtnHistorialMedico);

		JToggleButton tglbtnVacunas = new JToggleButton("Vacuna");
		tglbtnVacunas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (contentPanel.getLayout());
				cl.show(contentPanel, "Vacuna");
				cargarDatosVacunacion();
			}
		});
		menuBar.add(tglbtnVacunas);

		JScrollPane scrollPaneCitas = new JScrollPane();
		panelCitas.add(scrollPaneCitas, BorderLayout.CENTER);

		String headersCitas[] = { "Código", "Fecha", "Notas", "Especialidad", "Doctor", "Paciente" };
		modelCitas = new DefaultTableModel();
		modelCitas.setColumnIdentifiers(headersCitas);
		tableCitas = new JTable();
		tableCitas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableCitas.getSelectedRow();
				if (row > -1) {
					btnConsultarCita.setEnabled(true);
					String codigoCita = tableCitas.getValueAt(row, 0).toString();
					citaSelect = Clinica.getInstance().buscarCitaByCodigo(codigoCita);
					cargarDatosVacunacion();
				}
			}
		});

		scrollPaneCitas.setViewportView(tableCitas);
		tableCitas.setModel(modelCitas);

		JPanel buttonPaneCitas = new JPanel();
		buttonPaneCitas.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelCitas.add(buttonPaneCitas, BorderLayout.SOUTH);

		btnConsultarCita = new JButton("Consultar");
		btnConsultarCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (citaSelect != null) {
					int option = JOptionPane.showConfirmDialog(null,
							"Está seguro de consultar la cita: " + citaSelect.getCodigo(), "Confirmación",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (option == JOptionPane.YES_OPTION) {
						CardLayout cl = (CardLayout) (contentPanel.getLayout());
						cl.show(contentPanel, "Consultas");
						cargarDatosPaciente();
						cargarHistorialMedico();
					}
				}
			}
		});
		btnConsultarCita.setEnabled(false);
		buttonPaneCitas.add(btnConsultarCita);

		JButton okButtonCitas = new JButton("Aceptar");
		okButtonCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		okButtonCitas.setActionCommand("OK");
		buttonPaneCitas.add(okButtonCitas);
		getRootPane().setDefaultButton(okButtonCitas);

		loadTableCitas();

		JPanel pnlDatosPaciente = new JPanel();
		pnlDatosPaciente.setBorder(
				new TitledBorder(null, "Datos del Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDatosPaciente.setBounds(10, 11, 517, 233);
		panelConsultas.add(pnlDatosPaciente);
		pnlDatosPaciente.setLayout(null);

		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setBounds(10, 20, 70, 25);
		pnlDatosPaciente.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setEditable(false);
		txtCedula.setBounds(100, 20, 155, 25);
		pnlDatosPaciente.add(txtCedula);

		JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaNacimiento.setBounds(10, 60, 130, 25);
		pnlDatosPaciente.add(lblFechaNacimiento);

		spnFechaNacimiento = new JSpinner();
		spnFechaNacimiento.setEnabled(false);
		spnFechaNacimiento.setModel(new SpinnerDateModel());
		spnFechaNacimiento.setEditor(new JSpinner.DateEditor(spnFechaNacimiento, "dd/MM/yyyy"));
		spnFechaNacimiento.setBounds(150, 60, 110, 25);
		pnlDatosPaciente.add(spnFechaNacimiento);

		JLabel lblNombres = new JLabel("Nombres:");
		lblNombres.setBounds(10, 100, 70, 25);
		pnlDatosPaciente.add(lblNombres);

		txtNombres = new JTextField();
		txtNombres.setEditable(false);
		txtNombres.setBounds(100, 100, 155, 25);
		pnlDatosPaciente.add(txtNombres);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(10, 140, 70, 25);
		pnlDatosPaciente.add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setEditable(false);
		txtApellidos.setBounds(100, 140, 155, 25);
		pnlDatosPaciente.add(txtApellidos);

		JLabel lblTipoSangre = new JLabel("Tipo de Sangre:");
		lblTipoSangre.setBounds(300, 20, 100, 25);
		pnlDatosPaciente.add(lblTipoSangre);

		txtTipoSangre = new JTextField();
		txtTipoSangre.setEditable(false);
		txtTipoSangre.setBounds(400, 20, 100, 25);
		pnlDatosPaciente.add(txtTipoSangre);

		JLabel lblRH = new JLabel("RH:");
		lblRH.setBounds(300, 60, 100, 25);
		pnlDatosPaciente.add(lblRH);

		txtRH = new JTextField();
		txtRH.setEditable(false);
		txtRH.setBounds(400, 60, 100, 25);
		pnlDatosPaciente.add(txtRH);

		JLabel lblEstatura = new JLabel("Estatura:");
		lblEstatura.setBounds(300, 100, 100, 25);
		pnlDatosPaciente.add(lblEstatura);

		spnEstatura = new JSpinner(new SpinnerNumberModel(0, 0, 300, 1));
		spnEstatura.setBounds(400, 100, 100, 25);
		pnlDatosPaciente.add(spnEstatura);

		JLabel lblPeso = new JLabel("Peso:");
		lblPeso.setBounds(300, 140, 100, 25);
		pnlDatosPaciente.add(lblPeso);

		spnPeso = new JSpinner(new SpinnerNumberModel(0, 0, 300, 1));
		spnPeso.setBounds(400, 140, 100, 25);
		pnlDatosPaciente.add(spnPeso);

		JPanel pnlDatosClinicos = new JPanel();
		pnlDatosClinicos.setBorder(
				new TitledBorder(null, "Datos Clínicos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDatosClinicos.setBounds(10, 255, 517, 200);
		panelConsultas.add(pnlDatosClinicos);
		pnlDatosClinicos.setLayout(null);

		JLabel lblObservaciones = new JLabel("Observaciones:");
		lblObservaciones.setBounds(10, 20, 100, 25);
		pnlDatosClinicos.add(lblObservaciones);

		txtObservaciones = new JTextArea();
		txtObservaciones.setBounds(120, 20, 380, 50);
		pnlDatosClinicos.add(txtObservaciones);

		JLabel lblReceta = new JLabel("Receta:");
		lblReceta.setBounds(10, 80, 100, 25);
		pnlDatosClinicos.add(lblReceta);

		txtReceta = new JTextArea();
		txtReceta.setBounds(120, 80, 380, 50);
		pnlDatosClinicos.add(txtReceta);

		JLabel lblDiagnostico = new JLabel("Diagnóstico:");
		lblDiagnostico.setBounds(10, 140, 100, 25);
		pnlDatosClinicos.add(lblDiagnostico);

		txtDiagnostico = new JTextArea();
		txtDiagnostico.setBounds(120, 140, 380, 50);
		pnlDatosClinicos.add(txtDiagnostico);

		JPanel pnlEnfermedades = new JPanel();
		pnlEnfermedades
				.setBorder(new TitledBorder(null, "Enfermedades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlEnfermedades.setBounds(537, 11, 387, 233);
		panelConsultas.add(pnlEnfermedades);
		pnlEnfermedades.setLayout(new BorderLayout());

		String headersEnfermedades[] = { "Código", "Nombre", "Tipo" };
		modelEnfermedades = new DefaultTableModel();
		modelEnfermedades.setColumnIdentifiers(headersEnfermedades);
		tableEnfermedades = new JTable(modelEnfermedades);
		JScrollPane scrollPaneEnfermedades = new JScrollPane(tableEnfermedades);
		pnlEnfermedades.add(scrollPaneEnfermedades, BorderLayout.CENTER);

		JPanel pnlEnfermedadesPaciente = new JPanel();
		pnlEnfermedadesPaciente.setBorder(new TitledBorder(null, "Enfermedades del Paciente", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnlEnfermedadesPaciente.setBounds(537, 255, 387, 200);
		panelConsultas.add(pnlEnfermedadesPaciente);
		pnlEnfermedadesPaciente.setLayout(new BorderLayout());

		modelEnfermedadesPaciente = new DefaultTableModel();
		modelEnfermedadesPaciente.setColumnIdentifiers(headersEnfermedades);
		tableEnfermedadesPaciente = new JTable(modelEnfermedadesPaciente);
		JScrollPane scrollPaneEnfermedadesPaciente = new JScrollPane(tableEnfermedadesPaciente);
		pnlEnfermedadesPaciente.add(scrollPaneEnfermedadesPaciente, BorderLayout.CENTER);

		JPanel pnlBotonesEnfermedadesPaciente = new JPanel();
		pnlBotonesEnfermedadesPaciente.setLayout(new FlowLayout());
		pnlEnfermedadesPaciente.add(pnlBotonesEnfermedadesPaciente, BorderLayout.SOUTH);

		JButton btnAgregarEnfermedad = new JButton("Agregar");
		btnAgregarEnfermedad.setPreferredSize(new Dimension(100, 25));
		btnAgregarEnfermedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableEnfermedades.getSelectedRow();
				if (row > -1) {
					// Verificar si la enfermedad ya está en la lista de enfermedades del paciente
					String codigoEnfermedad = modelEnfermedades.getValueAt(row, 0).toString();
					boolean enfermedadYaAgregada = false;
					for (int i = 0; i < modelEnfermedadesPaciente.getRowCount(); i++) {
						if (modelEnfermedadesPaciente.getValueAt(i, 0).toString().equals(codigoEnfermedad)) {
							enfermedadYaAgregada = true;
							break;
						}
					}
					if (!enfermedadYaAgregada) {
						Object[] rowData = new Object[3];
						rowData[0] = modelEnfermedades.getValueAt(row, 0);
						rowData[1] = modelEnfermedades.getValueAt(row, 1);
						rowData[2] = modelEnfermedades.getValueAt(row, 2);
						modelEnfermedadesPaciente.addRow(rowData);
					} else {
						JOptionPane.showMessageDialog(null, "Esta enfermedad ya ha sido agregada.");
					}
				}
			}
		});
		pnlBotonesEnfermedadesPaciente.add(btnAgregarEnfermedad);

		JButton btnQuitarEnfermedad = new JButton("Quitar");
		btnQuitarEnfermedad.setPreferredSize(new Dimension(100, 25));
		btnQuitarEnfermedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableEnfermedadesPaciente.getSelectedRow();
				if (row > -1) {
					modelEnfermedadesPaciente.removeRow(row);
				}
			}
		});
		pnlBotonesEnfermedadesPaciente.add(btnQuitarEnfermedad);

		JLabel lblHistorial = new JLabel("¿Agregar a Historial Médico?");
		lblHistorial.setBounds(10, 460, 180, 25);
		panelConsultas.add(lblHistorial);

		rdbtnHistorialSi = new JRadioButton("Sí");
		rdbtnHistorialSi.setBounds(190, 460, 50, 25);
		panelConsultas.add(rdbtnHistorialSi);

		rdbtnHistorialNo = new JRadioButton("No");
		rdbtnHistorialNo.setBounds(240, 460, 50, 25);
		panelConsultas.add(rdbtnHistorialNo);

		historialGroup = new ButtonGroup();
		historialGroup.add(rdbtnHistorialSi);
		historialGroup.add(rdbtnHistorialNo);
		rdbtnHistorialNo.setSelected(true);

		JPanel buttonPaneConsultas = new JPanel();
		buttonPaneConsultas.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPaneConsultas.setBounds(10, 500, 900, 35);
		panelConsultas.add(buttonPaneConsultas);

		JButton okButtonConsultas = new JButton("Guardar Consulta");
		okButtonConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardarConsulta();
			}
		});
		okButtonConsultas.setActionCommand("OK");
		buttonPaneConsultas.add(okButtonConsultas);
		getRootPane().setDefaultButton(okButtonConsultas);

		JButton cancelButtonConsultas = new JButton("Cancelar");
		cancelButtonConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButtonConsultas.setActionCommand("Cancel");
		buttonPaneConsultas.add(cancelButtonConsultas);

		loadEnfermedadesTable();

		String headersHistorial[] = { "Código Cita", "Fecha", "Observaciones", "Diagnóstico", "Receta" };
		modelHistorial = new DefaultTableModel();
		modelHistorial.setColumnIdentifiers(headersHistorial);
		tableHistorial = new JTable();
		JScrollPane scrollPaneHistorial = new JScrollPane(tableHistorial);
		panelHistorialMedico.add(scrollPaneHistorial, BorderLayout.CENTER);
		tableHistorial.setModel(modelHistorial);

		String headersVacunas[] = { "Vacuna", "Fecha de Vacunacion", "Doctor" };
		modelVacunas = new DefaultTableModel();
		modelVacunas.setColumnIdentifiers(headersVacunas);
		tableVacunas = new JTable();
		JScrollPane scrollPaneVacunas = new JScrollPane(tableVacunas);
		panelVacunas.add(scrollPaneVacunas, BorderLayout.CENTER);
		tableVacunas.setModel(modelVacunas);

		JPanel pnlVacunacion = new JPanel();
		pnlVacunacion
				.setBorder(new TitledBorder(null, "Vacunación", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlVacunacion.setLayout(new GridBagLayout());
		panelVacunas.add(pnlVacunacion, BorderLayout.SOUTH);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;

		JLabel lblCedula1 = new JLabel("Cédula:");
		pnlVacunacion.add(lblCedula1, gbc);

		gbc.gridx = 1;
		txtCedula = new JTextField();
		txtCedula.setEditable(false);
		txtCedula.setColumns(10);
		pnlVacunacion.add(txtCedula, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel lblFecha = new JLabel("Fecha:");
		pnlVacunacion.add(lblFecha, gbc);

		gbc.gridx = 1;
		spnFecha = new JSpinner();
		spnFecha.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		spnFecha.setEditor(new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy"));
		spnFecha.setEnabled(false);
		pnlVacunacion.add(spnFecha, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel lblNombresVac = new JLabel("Nombres:");
		pnlVacunacion.add(lblNombresVac, gbc);

		gbc.gridx = 1;
		txtNombresVac = new JTextField();
		txtNombresVac.setEditable(false);
		txtNombresVac.setColumns(10);
		pnlVacunacion.add(txtNombresVac, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel lblApellidosVac = new JLabel("Apellidos:");
		pnlVacunacion.add(lblApellidosVac, gbc);

		gbc.gridx = 1;
		txtApellidosVac = new JTextField();
		txtApellidosVac.setEditable(false);
		txtApellidosVac.setColumns(10);
		pnlVacunacion.add(txtApellidosVac, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		JLabel lblVacuna = new JLabel("Vacuna:");
		pnlVacunacion.add(lblVacuna, gbc);

		gbc.gridx = 1;
		cbVacuna = new JComboBox<>();
		pnlVacunacion.add(cbVacuna, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		JLabel lblDoctor = new JLabel("Doctor:");
		pnlVacunacion.add(lblDoctor, gbc);

		gbc.gridx = 1;
		txtDoctor = new JTextField();
		txtDoctor.setEditable(false);
		txtDoctor.setColumns(10);
		pnlVacunacion.add(txtDoctor, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JButton btnAplicarVacuna = new JButton("Aplicar");
		btnAplicarVacuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarVacuna(txtCedula.getText(), (Date) spnFecha.getValue(), (Vacuna) cbVacuna.getSelectedItem(),
						txtDoctor.getText());
			}
		});
		pnlVacunacion.add(btnAplicarVacuna, gbc);

		gbc.gridy = 7;
		JButton btnCancelarVacuna = new JButton("Cancelar");
		btnCancelarVacuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		pnlVacunacion.add(btnCancelarVacuna, gbc);

		cargarVacunasDisponibles();
	}

	private void loadTableCitas() throws SQLException {
		modelCitas.setRowCount(0);
		List<Cita> citas = Clinica.getInstance().getCitasPendientes();
		for (Cita cita : citas) {
			Object[] row = new Object[6];
			row[0] = cita.getCodigo();
			row[1] = new SimpleDateFormat("dd/MM/yyyy").format(cita.getFecha());
			row[2] = cita.getNotas();
			row[3] = cita.getEspecialidad();
			row[4] = cita.getDoctor();
			row[5] = cita.getUserUsuario().getNombre() + " " + cita.getUserUsuario().getApellido();
			modelCitas.addRow(row);
		}
	}

	private void loadEnfermedadesTable() throws SQLException {
		modelEnfermedades.setRowCount(0);
		List<Enfermedad> enfermedades = Clinica.getInstance().getEnfermedades();
		for (Enfermedad enfermedad : enfermedades) {
			Object[] row = new Object[3];
			row[0] = enfermedad.getCodigoString();
			row[1] = enfermedad.getNombreString();
			row[2] = enfermedad.getTipoString();
			modelEnfermedades.addRow(row);
		}
	}

	private void cargarDatosPaciente() {
		if (citaSelect != null) {
			Usuario usuario = citaSelect.getUserUsuario();
			txtCedula.setText(usuario.getCedula());
			spnFechaNacimiento.setValue(usuario.getFechaNacimiento());
			txtNombres.setText(usuario.getNombre());
			txtApellidos.setText(usuario.getApellido());

			if (usuario instanceof Paciente) {
				Paciente paciente = (Paciente) usuario;
				txtTipoSangre.setText(paciente.getTipoSangre());
				txtRH.setText(paciente.getRh());
			} else {
				txtTipoSangre.setText("");
				txtRH.setText("");
			}
		} else {
			txtCedula.setText("");
			spnFechaNacimiento.setValue(new java.util.Date());
			txtNombres.setText("");
			txtApellidos.setText("");
			txtTipoSangre.setText("");
			txtRH.setText("");
		}
	}

	private void cargarHistorialMedico() {
		if (citaSelect != null) {
			modelHistorial.setRowCount(0);
			String cedulaPaciente = citaSelect.getUserUsuario().getCedula();
			List<Consulta> consultas = Clinica.getInstance().getConsultasByPaciente(cedulaPaciente);
			for (Consulta consulta : consultas) {
				Object[] row = new Object[5];
				row[0] = consulta.getCitaCodigo();
				row[1] = new SimpleDateFormat("dd/MM/yyyy").format(consulta.getFecha());
				row[2] = consulta.getObservaciones();
				row[3] = consulta.getDiagnostico();
				row[4] = consulta.getReceta();
				modelHistorial.addRow(row);
			}
		}
	}

	private void guardarConsulta() {
		if (citaSelect != null) {
			Consulta consulta = new Consulta();
			consulta.setCitaCodigo(citaSelect.getCodigo());
			consulta.setObservaciones(txtObservaciones.getText());
			consulta.setDiagnostico(txtDiagnostico.getText());
			consulta.setReceta(txtReceta.getText());
			consulta.setHistorial(rdbtnHistorialSi.isSelected());
			consulta.setEstatura((Integer) spnEstatura.getValue());
			consulta.setPeso((Integer) spnPeso.getValue());
			consulta.setTipoSangre(txtTipoSangre.getText());
			consulta.setRH(txtRH.getText());
			consulta.setFecha(new java.util.Date());

			try {
				Clinica.getInstance().insertarConsulta(consulta);
				Clinica.getInstance().actualizarEstadoCita(citaSelect.getCodigo(), "realizada");

				if (consulta.isHistorial()) {
					Clinica.getInstance().insertarHistorial(consulta.getCitaCodigo());
				}

				int consultaID = Clinica.getInstance().obtenerIDConsulta(consulta.getCitaCodigo());
				for (int i = 0; i < modelEnfermedadesPaciente.getRowCount(); i++) {
					String codigoEnfermedad = modelEnfermedadesPaciente.getValueAt(i, 0).toString();
					Clinica.getInstance().insertarConsultaEnfermedad(consultaID, codigoEnfermedad);
				}

				JOptionPane.showMessageDialog(null, "Consulta guardada correctamente.");
				dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error al guardar la consulta: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void cargarDatosVacunacion() {
		if (citaSelect != null) {
			Paciente paciente = (Paciente) citaSelect.getUserUsuario();
			txtCedula.setText(paciente.getCedula());
			txtNombresVac.setText(paciente.getNombre());
			txtApellidosVac.setText(paciente.getApellido());
			txtDoctor.setText(citaSelect.getDoctor());
			cargarVacunas();
		}
	}

	private void cargarVacunas() {
		if (citaSelect != null) {
			modelVacunas.setRowCount(0);
			String cedulaPaciente = citaSelect.getUserUsuario().getCedula();
			List<Vacuna> vacunas = Clinica.getInstance().getVacunasByPaciente(cedulaPaciente);
			for (Vacuna vacuna : vacunas) {
				Object[] row = new Object[3];
				row[0] = vacuna.getNombreString();
				row[1] = new SimpleDateFormat("dd/MM/yyyy").format(vacuna.getFecha());
				row[2] = vacuna.getDoctor();
				modelVacunas.addRow(row);
			}
		}
	}

	private void cargarVacunasDisponibles() {
		try {
			cbVacuna.removeAllItems();
			List<Vacuna> vacunas = Clinica.getInstance().getVacunas();
			for (Vacuna vacuna : vacunas) {
				cbVacuna.addItem(vacuna);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar las vacunas: " + e.getMessage());
		}
	}

	private void aplicarVacuna(String cedulaPaciente, Date fecha, Vacuna vacuna, String doctor) {
		if (cedulaPaciente != null && !cedulaPaciente.isEmpty() && vacuna != null && doctor != null
				&& !doctor.isEmpty()) {
			try {
				Clinica.getInstance().aplicarVacuna(cedulaPaciente, fecha, vacuna, doctor);
				JOptionPane.showMessageDialog(null, "Vacuna aplicada correctamente.");
				cargarVacunas();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error al aplicar la vacuna: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.");
		}
	}

	private void initializeComponents() {
		txtCedula = new JTextField();
		txtNombres = new JTextField();
		txtApellidos = new JTextField();
		txtDoctor = new JTextField();
	}

	private void limpiarCamposVacunacion() {
		txtCedula.setText("");
		txtNombresVac.setText("");
		txtApellidosVac.setText("");
		txtDoctor.setText("");
		spnFecha.setValue(new Date());
		cbVacuna.setSelectedIndex(-1);
	}
}
