package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logico.Cita;
import logico.Clinica;
import logico.Medico;
import logico.Paciente;

public class CrearCitas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnCancelar;
	private JButton btnRegistrar;
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCelular;
	private JTextField txtTelefonoOpc;
	private JTextField txtNid;
	private JSpinner spnFechaNacimiento;
	private JComboBox<Object> cbxTipoDocumento;
	private JTextField txtIDPaciente;
	private JTextPane textNotas;
	private Paciente paciente = null;
	private JTextField txtDireccion;
	private JComboBox<Object> cbxTipoBlood;
	private JComboBox<Object> cbxOcupacion;
	private JComboBox<Object> cbxRh;
	private JComboBox<Object> cbxDoctor;
	private JComboBox<Object> cbxEspecialidad;
	private JLabel lblAdvertencia;
	private JLabel signoAvisoDocId;
	private JLabel mensaje;
	private JLabel signoAvisoTipoBlood;
	private JLabel signoAvisoOcupacion;
	private JLabel signoAvisoApellido;
	private JLabel signoAvisoNombre;
	private JLabel signoAvisoCelular;
	private JLabel signoAvisoTelefono;
	private JLabel signoAvisoDireccion;
	private JLabel signoAvisoRH;
	private JLabel signoAviso7;
	private JSpinner spnFechaCita;

	public static void main(String[] args) {
		try {
			CrearCitas dialog = new CrearCitas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CrearCitas() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(CrearCitas.class.getResource("/img/cruz-roja.png")));
		setTitle("Registro De Citas");
		setBounds(100, 100, 666, 719);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 630, 370);
		panel.setBorder(
				new TitledBorder(null, "Datos del Paciente:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblNombre = new JLabel("Nombres:");
		lblNombre.setBounds(31, 191, 70, 25);
		panel.add(lblNombre);

		JLabel lblApellido = new JLabel("Apellidos:");
		lblApellido.setBounds(31, 227, 70, 25);
		panel.add(lblApellido);

		JButton button = new JButton("Buscar    ");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paciente = Clinica.getInstance().buscarPacienteByCedula(txtNid.getText());

				if (paciente == null) {
					JOptionPane.showMessageDialog(null, "No Existen Pacientes Registrados Con Esta Identificacion",
							"PACIENTE NO ENCONTRADO", JOptionPane.ERROR_MESSAGE);
					activationKey(true);
					// Permitir la edición de los campos
					txtNid.setEditable(true);
					txtNombre.setEditable(true);
					txtApellido.setEditable(true);
					txtCelular.setEditable(true);
					txtTelefonoOpc.setEditable(true);
					txtDireccion.setEditable(true);
					cbxOcupacion.setEnabled(true);
					cbxTipoBlood.setEnabled(true);
					cbxRh.setEnabled(true);
				} else {
					// Si el paciente es encontrado, no permitir la edición de los campos
					txtNid.setText(paciente.getCedula()); // Solo se coloca la cédula
					txtNid.setEditable(false);
					txtIDPaciente.setText(paciente.getCodigo()); // El código se coloca en txtIDPaciente
					txtNombre.setText(paciente.getNombre());
					txtNombre.setEditable(false);
					txtApellido.setText(paciente.getApellido());
					txtApellido.setEditable(false);
					txtCelular.setText(paciente.getTelefono());
					txtCelular.setEditable(false);
					txtTelefonoOpc.setText(paciente.getTelefonoOpc());
					txtTelefonoOpc.setEditable(false);
					cbxOcupacion.setSelectedItem(paciente.getOcupacion());
					cbxOcupacion.setEnabled(false);
					txtDireccion.setText(paciente.getDireccion());
					txtDireccion.setEditable(false);
					cbxTipoBlood.setSelectedItem(paciente.getTipoSangre());
					cbxTipoBlood.setEnabled(false);
					cbxRh.setSelectedItem(paciente.getRh());
					cbxRh.setEnabled(false);
				}
			}
		});

		button.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/lupa.png")));
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setBounds(283, 111, 132, 34);
		panel.add(button);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				int key = evt.getKeyChar();
				boolean mayusculas = key >= 65 && key <= 90;
				boolean minusculas = key >= 97 && key <= 122;
				boolean espacio = key == 32;
				if (!(minusculas || mayusculas || espacio)) {
					evt.consume();
				}
			}
		});
		txtNombre.setEditable(false);
		txtNombre.setBounds(98, 191, 155, 25);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		txtApellido = new JTextField();
		txtApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				int key = evt.getKeyChar();
				boolean mayusculas = key >= 65 && key <= 90;
				boolean minusculas = key >= 97 && key <= 122;
				boolean espacio = key == 32;
				if (!(minusculas || mayusculas || espacio)) {
					evt.consume();
				}
			}
		});
		txtApellido.setEditable(false);
		txtApellido.setColumns(10);
		txtApellido.setBounds(98, 227, 155, 25);
		panel.add(txtApellido);

		JLabel lblDocIdentidad = new JLabel("Cédula:");
		lblDocIdentidad.setBounds(31, 117, 70, 25);
		panel.add(lblDocIdentidad);

		JLabel lblNoIdenticacion = new JLabel("NO.");
		lblNoIdenticacion.setBounds(98, 116, 34, 25);
		panel.add(lblNoIdenticacion);

		txtNid = new JTextField();
		txtNid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				int key = evt.getKeyChar();
				boolean numeros = key >= 48 && key <= 57;
				if (!numeros) {
					evt.consume();
				}
				if (txtNid.getText().trim().length() == 10) {
					evt.consume();
				}
			}
		});
		txtNid.setBounds(135, 116, 110, 25);
		panel.add(txtNid);
		txtNid.setColumns(10);

		JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaNacimiento.setBounds(31, 153, 130, 25);
		panel.add(lblFechaNacimiento);

		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setBounds(291, 190, 46, 25);
		panel.add(lblCelular);

		JLabel lblTelefonoOpcional = new JLabel("Teléfono:");
		lblTelefonoOpcional.setBounds(284, 227, 53, 25);
		panel.add(lblTelefonoOpcional);

		txtCelular = new JTextField();
		txtCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				int key = evt.getKeyChar();
				boolean numeros = key >= 48 && key <= 57;
				if (!numeros) {
					evt.consume();
				}
				if (txtCelular.getText().trim().length() == 10) {
					evt.consume();
				}
			}
		});
		txtCelular.setEditable(false);
		txtCelular.setBounds(347, 191, 110, 25);
		panel.add(txtCelular);
		txtCelular.setColumns(10);

		txtTelefonoOpc = new JTextField();
		txtTelefonoOpc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				int key = evt.getKeyChar();
				boolean numeros = key >= 48 && key <= 57;
				if (!numeros) {
					evt.consume();
				}
				if (txtTelefonoOpc.getText().trim().length() == 10) {
					evt.consume();
				}
			}
		});
		txtTelefonoOpc.setEditable(false);
		txtTelefonoOpc.setBounds(347, 228, 110, 25);
		panel.add(txtTelefonoOpc);
		txtTelefonoOpc.setColumns(10);

		signoAvisoDocId = new JLabel("");
		signoAvisoDocId.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoDocId.setBounds(10, 117, 16, 25);
		signoAvisoDocId.setVisible(false);
		panel.add(signoAvisoDocId);

		signoAvisoCelular = new JLabel("");
		signoAvisoCelular.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoCelular.setBounds(271, 190, 16, 25);
		signoAvisoCelular.setVisible(false);
		panel.add(signoAvisoCelular);

		signoAvisoNombre = new JLabel("");
		signoAvisoNombre.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoNombre.setBounds(10, 191, 16, 25);
		signoAvisoNombre.setVisible(false);
		panel.add(signoAvisoNombre);

		signoAvisoApellido = new JLabel("");
		signoAvisoApellido.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoApellido.setBounds(10, 227, 16, 25);
		signoAvisoApellido.setVisible(false);
		panel.add(signoAvisoApellido);

		signoAvisoTelefono = new JLabel("");
		signoAvisoTelefono.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoTelefono.setBounds(271, 227, 16, 25);
		signoAvisoTelefono.setVisible(false);
		panel.add(signoAvisoTelefono);

		spnFechaNacimiento = new JSpinner();
		spnFechaNacimiento.setEnabled(false);
		spnFechaNacimiento.setModel(new SpinnerDateModel(new Date(1650181417873L), null, null, Calendar.YEAR));
		spnFechaNacimiento.setEditor(new JSpinner.DateEditor(spnFechaNacimiento, "dd/MM/yyyy"));
		spnFechaNacimiento.setValue(new Date());
		spnFechaNacimiento.setBounds(165, 155, 110, 25);
		panel.add(spnFechaNacimiento);

		JLabel signoAviso6 = new JLabel("");
		signoAviso6.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/prescripcion-medica.png")));
		signoAviso6.setBounds(27, 23, 86, 72);
		panel.add(signoAviso6);

		JLabel lblIdPaciente = new JLabel("ID:");
		lblIdPaciente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIdPaciente.setBounds(488, 30, 23, 25);
		panel.add(lblIdPaciente);

		txtIDPaciente = new JTextField();
		txtIDPaciente.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtIDPaciente.setEditable(false);
		txtIDPaciente.setColumns(10);
		txtIDPaciente.setText(Clinica.getInstance().getNextUserCode()); // Cambio aquí
		txtIDPaciente.setBounds(513, 30, 93, 25);
		panel.add(txtIDPaciente);

		JLabel lblOcupacion = new JLabel("Ocupación:");
		lblOcupacion.setBounds(31, 263, 70, 25);
		panel.add(lblOcupacion);

		signoAvisoOcupacion = new JLabel("");
		signoAvisoOcupacion.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoOcupacion.setBounds(10, 263, 16, 25);
		signoAvisoOcupacion.setVisible(false);
		panel.add(signoAvisoOcupacion);

		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(347, 262, 110, 25);
		panel.add(txtDireccion);

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(284, 263, 70, 25);
		panel.add(lblDireccion);

		signoAvisoDireccion = new JLabel("");
		signoAvisoDireccion.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoDireccion.setBounds(271, 263, 16, 25);
		signoAvisoDireccion.setVisible(false);
		panel.add(signoAvisoDireccion);

		cbxOcupacion = new JComboBox<Object>();
		cbxOcupacion.setEnabled(false);
		cbxOcupacion.setModel(new DefaultComboBoxModel<Object>(new String[] { "<Seleccionar>", "Profesor", "Abogado",
				"Ingeniero", "Medico", "Empleado Privado", "Empleado Publico", "Independiente" }));
		cbxOcupacion.setBounds(98, 263, 155, 25);
		panel.add(cbxOcupacion);

		JLabel label_2 = new JLabel("Tipo de Sangre:");
		label_2.setBounds(31, 310, 94, 25);
		panel.add(label_2);

		cbxTipoBlood = new JComboBox<Object>();
		cbxTipoBlood.setEnabled(false);
		cbxTipoBlood.setModel(new DefaultComboBoxModel<Object>(new String[] { "", "A", "B", "AB", "O" }));
		cbxTipoBlood.setBounds(126, 310, 55, 25);
		panel.add(cbxTipoBlood);

		JLabel label_3 = new JLabel("RH:");
		label_3.setBounds(284, 310, 33, 25);
		panel.add(label_3);

		cbxRh = new JComboBox<Object>();
		cbxRh.setEnabled(false);
		cbxRh.setModel(new DefaultComboBoxModel<Object>(new String[] { "", "+", "-", "Nulo" }));
		cbxRh.setBounds(347, 310, 77, 25);
		panel.add(cbxRh);

		signoAvisoRH = new JLabel("");
		signoAvisoRH.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoRH.setBounds(271, 310, 16, 25);
		signoAvisoRH.setVisible(false);
		panel.add(signoAvisoRH);

		signoAvisoTipoBlood = new JLabel("");
		signoAvisoTipoBlood.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));
		signoAvisoTipoBlood.setBounds(10, 310, 16, 25);
		signoAvisoTipoBlood.setVisible(false);
		panel.add(signoAvisoTipoBlood);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnRegistrar = new JButton("Registrar");
				btnRegistrar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (paciente == null) {
								if (txtNid.getText().equals("") || txtNombre.getText().equals("")
										|| txtApellido.getText().equals("") || txtTelefonoOpc.getText().equals("")
										|| txtDireccion.getText().equals("") || txtCelular.getText().equals("")
										|| cbxOcupacion.getSelectedItem().equals("")
										|| cbxTipoBlood.getSelectedItem().equals("")
										|| cbxRh.getSelectedItem().equals("")
										|| cbxEspecialidad.getSelectedItem().equals("<Seleccionar>")
										|| cbxDoctor.getSelectedItem().equals("") || textNotas.getText().equals("")) {
									advertencia();
									return;
								}

								// Crear nuevo paciente y registrarlo
								paciente = new Paciente(txtNombre.getText(), txtApellido.getText(), txtNid.getText(),
										(Date) spnFechaNacimiento.getValue(), cbxOcupacion.getSelectedItem().toString(),
										txtCelular.getText(), txtDireccion.getText(), txtIDPaciente.getText(),
										cbxTipoBlood.getSelectedItem().toString(), cbxRh.getSelectedItem().toString(),
										txtTelefonoOpc.getText());

								Clinica.getInstance().insertarUsuario(paciente);
							}

							String nextCitaCode = Clinica.getInstance().getNextCitaCode();
							Cita cita = new Cita(nextCitaCode, (Date) spnFechaCita.getValue(), textNotas.getText(),
									cbxEspecialidad.getSelectedItem().toString(),
									cbxDoctor.getSelectedItem().toString(), paciente);
							Clinica.getInstance().insertarCita(cita);

							JOptionPane.showMessageDialog(null, "Cita registrada exitosamente", "Información",
									JOptionPane.INFORMATION_MESSAGE);
							clean();
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				btnRegistrar.setActionCommand("Cancel");
				buttonPane.add(btnRegistrar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea cancelar?",
								"Confirmación", JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							dispose();
						}
					}
				});
				btnCancelar.setActionCommand("OK");
				buttonPane.add(btnCancelar);
				getRootPane().setDefaultButton(btnCancelar);
			}
		}

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(
				new TitledBorder(null, "Datos de la Cita", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 395, 630, 184);
		contentPanel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("Especialista:");
		lblNewLabel_5.setBounds(10, 42, 84, 25);
		panel_2.add(lblNewLabel_5);

		cbxEspecialidad = new JComboBox<Object>();
		cbxEspecialidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbxEspecialidad.getSelectedIndex() > 0) {
					listarMedicosByEspecialidad(cbxEspecialidad.getSelectedItem().toString());
				}
			}
		});
		cbxEspecialidad.setModel(new DefaultComboBoxModel<Object>(
				new String[] { "<Seleccionar>", "Cirugía", "Pediatría", "Medicina Interna", "Psiquiatría",
						"Oftalmología", "Cardiología", "Neumología", "Dermatología", "Nefrología", "Neurología",
						"Radiología", "Anestesiología", "Urología", "Gastroenterología", "Gineco obstetricia" }));
		cbxEspecialidad.setBounds(90, 42, 147, 25);
		panel_2.add(cbxEspecialidad);

		spnFechaCita = new JSpinner();
		spnFechaCita.setModel(new SpinnerDateModel(new Date(1650181583304L), null, null, Calendar.DAY_OF_YEAR));
		spnFechaCita.setEditor(new JSpinner.DateEditor(spnFechaCita, "dd/MM/yyyy"));
		spnFechaCita.setValue(new Date());
		spnFechaCita.setBounds(90, 118, 110, 25);
		panel_2.add(spnFechaCita);

		JLabel lblNewLabel_7 = new JLabel("Fecha Cita:");
		lblNewLabel_7.setBounds(10, 118, 84, 25);
		panel_2.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Notas:");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_8.setBounds(324, 18, 70, 25);
		panel_2.add(lblNewLabel_8);

		JLabel lblNewLabel_2 = new JLabel("Doctor:");
		lblNewLabel_2.setBounds(10, 78, 46, 25);
		panel_2.add(lblNewLabel_2);

		cbxDoctor = new JComboBox<Object>();
		cbxDoctor.setEnabled(false);
		cbxDoctor.setBounds(90, 78, 147, 25);
		panel_2.add(cbxDoctor);

		textNotas = new JTextPane();
		textNotas.setBounds(324, 42, 283, 108);
		panel_2.add(textNotas);

		lblAdvertencia = new JLabel("No hay Doctores Registrados ");
		lblAdvertencia.setForeground(Color.RED);
		lblAdvertencia.setBounds(90, 78, 172, 25);
		lblAdvertencia.setVisible(false);
		panel_2.add(lblAdvertencia);

		mensaje = new JLabel(
				"AVISO: LOS CAMPOS MARCADOS      SON NECESARIOS PARA PROCESAR CORRETACTAMENTE SU SOLICITUD");
		mensaje.setBounds(22, 599, 598, 25);
		contentPanel.add(mensaje);
		mensaje.setVisible(false);
		mensaje.setFont(new Font("Tahoma", Font.BOLD, 11));

		signoAviso7 = new JLabel("");
		signoAviso7.setBounds(204, 599, 16, 25);
		contentPanel.add(signoAviso7);
		signoAviso7.setVisible(false);
		signoAviso7.setIcon(new ImageIcon(CrearCitas.class.getResource("/img/signo-advertencia.png")));

	}

	private void listarMedicosByEspecialidad(String especialidad) {
		cbxDoctor.removeAllItems();
		cbxDoctor.setEnabled(true);
		cbxDoctor.addItem("<Seleccionar>");
		int doctores = 0;
		for (Medico medico : Clinica.getInstance().buscarmedicosByEspecialidad(especialidad)) {
			cbxDoctor.addItem(medico.getLoginString());
			doctores++;
		}
		if (doctores == 0) {
			cbxDoctor.removeAllItems();
			cbxDoctor.setVisible(false);
			lblAdvertencia.setVisible(true);
		} else {
			lblAdvertencia.setVisible(false);
			cbxDoctor.setVisible(true);
		}
	}

	public void activationKey(boolean value) {
		txtNombre.setEditable(true);
		txtApellido.setEditable(true);
		spnFechaNacimiento.setEnabled(true);
		txtCelular.setEditable(true);
		txtTelefonoOpc.setEditable(true);
		txtDireccion.setEditable(true);
		cbxOcupacion.setEnabled(true);
		cbxTipoBlood.setEnabled(true);
		cbxRh.setEnabled(true);
	}

	public void desactivationKey(boolean value) {
		txtNombre.setEditable(false);
		txtApellido.setEditable(false);
		spnFechaNacimiento.setEnabled(false);
		txtCelular.setEditable(false);
		txtTelefonoOpc.setEditable(false);
		txtDireccion.setEditable(false);
		cbxOcupacion.setEnabled(false);
		cbxTipoBlood.setEnabled(false);
		cbxRh.setEnabled(false);
	}

	private void advertencia() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		signoAvisoNombre.setVisible(true);
		signoAvisoApellido.setVisible(true);
		signoAvisoDocId.setVisible(true);
		signoAvisoOcupacion.setVisible(true);
		signoAvisoCelular.setVisible(true);
		signoAvisoTelefono.setVisible(true);
		signoAvisoDireccion.setVisible(true);
		signoAvisoTipoBlood.setVisible(true);
		signoAvisoRH.setVisible(true);
		signoAviso7.setVisible(true);
		mensaje.setVisible(true);
		tk.beep();
	}

	private void clean() {
		txtNid.setText("");
		txtNid.setEditable(true); // Asegurarse de habilitar el campo para la próxima búsqueda
		txtNombre.setText("");
		txtNombre.setEditable(true);
		txtApellido.setText("");
		txtApellido.setEditable(true);
		txtCelular.setText("");
		txtCelular.setEditable(true);
		txtTelefonoOpc.setText("");
		txtTelefonoOpc.setEditable(true);
		txtDireccion.setText("");
		txtDireccion.setEditable(true);
		cbxOcupacion.setSelectedIndex(0);
		cbxOcupacion.setEnabled(true);
		cbxTipoBlood.setSelectedIndex(0);
		cbxTipoBlood.setEnabled(true);
		cbxRh.setSelectedIndex(0);
		cbxRh.setEnabled(true);
		cbxEspecialidad.setSelectedIndex(0);
		cbxDoctor.removeAllItems(); // Limpiar la lista de doctores
		textNotas.setText("");
		spnFechaNacimiento.setValue(new Date());
		spnFechaCita.setValue(new Date());
		txtIDPaciente.setText(Clinica.getInstance().getNextUserCode());
	}

}
