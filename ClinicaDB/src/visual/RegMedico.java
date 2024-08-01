package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logico.Clinica;
import logico.Medico;

public class RegMedico extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCedula;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JTextField txtUser;
	private JTextField txtpassword;
	private JComboBox<Object> cmbConsultorio;
	private JSpinner spinner;
	private Medico mimedico = null;
	private JComboBox<Object> cmbEspecialidad;
	private JLabel advertenciaCedula;
	private JLabel advertenciaNombre;
	private JLabel advertenciaApellido;
	private JLabel advertenciaTel;
	private JLabel advertenciaUser;
	private JLabel advertenciaContraseña;
	private JLabel lblImportante;
	private JLabel advertenciaConsultorio;
	private JLabel AdvertenciaDireccion;
	private JLabel advertenciaEspecialidad;
	private JButton cancelButton;
	private JButton okButton;

	public static void main(String[] args) {
		try {
			RegMedico dialog = new RegMedico(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegMedico(Medico us) {
		mimedico = us;
		if (us == null) {
			setTitle("Registro del Personal Medico");
		} else {
			setTitle("Modificar Registro del Personal Medico");
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegMedico.class.getResource("/img/cruz-roja.png")));
		setBounds(100, 100, 556, 580);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Datos del Doctor:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(286, 391, 46, 14);
		panel.add(lblNewLabel);

		txtId = new JTextField();
		txtId.setBounds(286, 407, 193, 20);
		txtId.setEditable(false);
		panel.add(txtId);
		txtId.setColumns(10);

		if (us == null) {
			String nextUserCode = Clinica.getInstance().getNextUserCode();
			txtId.setText(nextUserCode);
		}

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(31, 164, 86, 14);
		panel.add(lblNewLabel_1);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				int key = evt.getKeyChar();

				boolean mayusculas = key >= 65 && key <= 90;
				boolean minusculas = key >= 97 && key <= 122;
				boolean espacio = key == 32;

				if (!(minusculas || mayusculas || espacio)) {
					evt.consume();
				}
			}
		});
		txtNombre.setBounds(31, 189, 193, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Apellido:");
		lblNewLabel_2.setBounds(31, 225, 94, 14);
		panel.add(lblNewLabel_2);

		txtApellido = new JTextField();
		txtApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				int key = evt.getKeyChar();

				boolean mayusculas = key >= 65 && key <= 90;
				boolean minusculas = key >= 97 && key <= 122;
				boolean espacio = key == 32;

				if (!(minusculas || mayusculas || espacio)) {
					evt.consume();
				}
			}
		});
		txtApellido.setBounds(31, 250, 193, 20);
		panel.add(txtApellido);
		txtApellido.setColumns(10);

		JLabel lblTipoDocumento = new JLabel("Tipo de Documento:");
		lblTipoDocumento.setBounds(31, 75, 150, 14);
		panel.add(lblTipoDocumento);

		JLabel lblNewLabel_3 = new JLabel("Número de Documento:");
		lblNewLabel_3.setBounds(31, 130, 150, 14);
		panel.add(lblNewLabel_3);

		txtCedula = new JTextField();
		txtCedula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				int key = evt.getKeyChar();

				boolean numeros = key >= 48 && key <= 57;

				if (!numeros) {
					evt.consume();
				}

				if (txtCedula.getText().trim().length() == 10) {
					evt.consume();
				}
			}
		});
		txtCedula.setBounds(31, 150, 193, 20);
		panel.add(txtCedula);
		txtCedula.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Teléfono:");
		lblNewLabel_5.setBounds(286, 162, 57, 14);
		panel.add(lblNewLabel_5);

		txtTelefono = new JTextField();
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				int key = evt.getKeyChar();

				boolean numeros = key >= 48 && key <= 57;

				if (!numeros) {
					evt.consume();
				}

				if (txtTelefono.getText().trim().length() == 10) {
					evt.consume();
				}
			}
		});
		txtTelefono.setBounds(286, 187, 193, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Dirección:");
		lblNewLabel_6.setBounds(286, 223, 57, 14);
		panel.add(lblNewLabel_6);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(286, 248, 193, 20);
		panel.add(txtDireccion);
		txtDireccion.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Nombre de Usuario:");
		lblNewLabel_7.setBounds(286, 279, 128, 14);
		panel.add(lblNewLabel_7);

		txtUser = new JTextField();
		txtUser.setBounds(286, 304, 193, 20);
		panel.add(txtUser);
		txtUser.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Contraseña:");
		lblNewLabel_8.setBounds(286, 335, 94, 14);
		panel.add(lblNewLabel_8);

		txtpassword = new JTextField();
		txtpassword.setBounds(286, 360, 193, 20);
		panel.add(txtpassword);
		txtpassword.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Especialidad:");
		lblNewLabel_9.setBounds(31, 281, 94, 14);
		panel.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("Consultorio:");
		lblNewLabel_10.setBounds(286, 106, 77, 14);
		panel.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Fecha de Nacimiento:");
		lblNewLabel_11.setBounds(31, 337, 136, 14);
		panel.add(lblNewLabel_11);

		spinner = new JSpinner();
		spinner.setBounds(31, 362, 193, 20);
		spinner.setModel(new SpinnerDateModel(new Date(1647921600000L), null, null, Calendar.YEAR));
		spinner.setValue(new Date());
		spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
		panel.add(spinner);

		cmbConsultorio = new JComboBox<Object>();
		cargarConsultorios(true);
		cmbConsultorio.setBounds(286, 131, 193, 20);
		panel.add(cmbConsultorio);

		lblImportante = new JLabel("IMPORTANTE: TODOS LOS CAMPOS  DEBEN ESTAR COMPLETOS ");
		lblImportante.setBounds(87, 457, 358, 25);
		lblImportante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblImportante.setVisible(false);
		panel.add(lblImportante);

		JLabel lblNewLabel_12 = new JLabel("");
		lblNewLabel_12.setIcon(new ImageIcon(RegMedico.class.getResource("/img/medico.png")));
		lblNewLabel_12.setBounds(10, 23, 86, 72);
		panel.add(lblNewLabel_12);

		cmbEspecialidad = new JComboBox<Object>();
		cmbEspecialidad.setBounds(31, 306, 193, 20);
		cmbEspecialidad.setModel(new DefaultComboBoxModel<Object>(
				new String[] { "<Seleccionar>", "Cirugía", "Pediatría", "Medicina Interna", "Psiquiatría",
						"Oftalmología", "Cardiología", "Neumología", "Dermatología", "Nefrología", "Neurología",
						"Radiología", "Anestesiología", "Urología", "Gastroenterología", "Gineco obstetricia" }));
		panel.add(cmbEspecialidad);

		advertenciaCedula = new JLabel("");
		advertenciaCedula.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		advertenciaCedula.setBounds(10, 150, 16, 25);
		advertenciaCedula.setVisible(false);
		panel.add(advertenciaCedula);

		advertenciaNombre = new JLabel("");
		advertenciaNombre.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		advertenciaNombre.setBounds(10, 189, 16, 25);
		advertenciaNombre.setVisible(false);
		panel.add(advertenciaNombre);

		advertenciaApellido = new JLabel("");
		advertenciaApellido.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		advertenciaApellido.setBounds(10, 252, 16, 25);
		advertenciaApellido.setVisible(false);
		panel.add(advertenciaApellido);

		advertenciaTel = new JLabel("");
		advertenciaTel.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		advertenciaTel.setBounds(265, 187, 16, 25);
		advertenciaTel.setVisible(false);
		panel.add(advertenciaTel);

		advertenciaUser = new JLabel("");
		advertenciaUser.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		advertenciaUser.setBounds(265, 306, 16, 25);
		advertenciaUser.setVisible(false);
		panel.add(advertenciaUser);

		advertenciaContraseña = new JLabel("");
		advertenciaContraseña.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		advertenciaContraseña.setBounds(265, 362, 16, 25);
		advertenciaContraseña.setVisible(false);
		panel.add(advertenciaContraseña);

		advertenciaConsultorio = new JLabel("");
		advertenciaConsultorio.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		advertenciaConsultorio.setBounds(265, 135, 16, 25);
		advertenciaConsultorio.setVisible(false);
		panel.add(advertenciaConsultorio);

		AdvertenciaDireccion = new JLabel("");
		AdvertenciaDireccion.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		AdvertenciaDireccion.setBounds(265, 245, 16, 25);
		AdvertenciaDireccion.setVisible(false);
		panel.add(AdvertenciaDireccion);

		advertenciaEspecialidad = new JLabel("");
		advertenciaEspecialidad.setIcon(new ImageIcon(RegMedico.class.getResource("/img/signo-advertencia.png")));
		advertenciaEspecialidad.setBounds(10, 306, 16, 25);
		advertenciaEspecialidad.setVisible(false);
		panel.add(advertenciaEspecialidad);

		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cancelar?",
						"Confirmación", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});

		okButton = new JButton("");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mimedico == null) {
					if (txtCedula.getText().equals("") || txtNombre.getText().equals("")
							|| txtApellido.getText().equals("") || txtTelefono.getText().equals("")
							|| txtDireccion.getText().equals("") || txtUser.getText().equals("")
							|| txtpassword.getText().equals("")
							|| cmbConsultorio.getSelectedItem().equals("<Seleccione>")
							|| cmbEspecialidad.getSelectedItem().equals("<Seleccionar>")) {
						Advertencia();
					} else {
						Medico nuevoMedico = new Medico(txtNombre.getText(), txtApellido.getText(), txtCedula.getText(),
								(Date) spinner.getValue(), txtTelefono.getText(), txtDireccion.getText(),
								txtUser.getText(), txtpassword.getText(), cmbConsultorio.getSelectedItem().toString(),
								txtId.getText(), cmbEspecialidad.getSelectedItem().toString(), txtCedula.getText());
						Clinica.getInstance().insertarMedico(nuevoMedico);
						JOptionPane.showMessageDialog(null, "Se ha registrado el doctor/a correctamente", "Información",
								JOptionPane.INFORMATION_MESSAGE);
						clean();
					}
				} else {
					mimedico.setNombre(txtNombre.getText());
					mimedico.setApellido(txtApellido.getText());
					mimedico.setID(txtId.getText()); // Asegúrate de usar el ID correcto aquí
					mimedico.setFechaNacimiento((Date) spinner.getValue());
					mimedico.setTelefono(txtTelefono.getText());
					mimedico.setDireccion(txtDireccion.getText());
					mimedico.setLoginString(txtUser.getText());
					mimedico.setPasswordString(txtpassword.getText());
					mimedico.setConsultorioString(cmbConsultorio.getSelectedItem().toString());
					mimedico.setCodigo(txtId.getText());
					mimedico.setEspecialidad(cmbEspecialidad.getSelectedItem().toString());
					Clinica.getInstance().actualizarMedico(mimedico);
					JOptionPane.showMessageDialog(null, "Operación exitosa", "Información",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		if (mimedico == null) {
			okButton.setText("Registrar");
		} else {
			okButton.setText("Modificar");
		}

		loadMedico(mimedico);
		cargarConsultorios(true);
	}

	private void loadMedico(Medico us) {
		if (us != null) {
			txtNombre.setText(us.getNombre());
			txtApellido.setText(us.getApellido());
			txtCedula.setText(us.getCedula());
			txtDireccion.setText(us.getDireccion());
			txtTelefono.setText(us.getTelefono());
			cmbEspecialidad.setSelectedItem(us.getEspecialidad());
			txtUser.setText(us.getLoginString());
			txtpassword.setText(us.getPasswordString());
			spinner.setValue(us.getFechaNacimiento());
			cmbConsultorio.setSelectedItem(us.getConsultorioString());
			txtId.setText(us.getCodigo());
		}
	}

	private void clean() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtCedula.setText("");
		txtDireccion.setText("");
		cmbEspecialidad.setSelectedIndex(0);
		txtpassword.setText("");
		txtUser.setText("");
		txtTelefono.setText("");
		txtId.setText(Clinica.getInstance().getNextUserCode());
		spinner.setValue(new Date());
		cmbConsultorio.setSelectedIndex(0);
	}

	private void Advertencia() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		advertenciaNombre.setVisible(true);
		advertenciaApellido.setVisible(true);
		advertenciaCedula.setVisible(true);
		advertenciaUser.setVisible(true);
		advertenciaContraseña.setVisible(true);
		advertenciaTel.setVisible(true);
		AdvertenciaDireccion.setVisible(true);
		advertenciaConsultorio.setVisible(true);
		advertenciaEspecialidad.setVisible(true);
		lblImportante.setVisible(true);
		tk.beep();
	}

	public void cargarConsultorios(boolean control) {
		String[] consultorioString = { "<Seleccione>", "A1-101", "A2-102", "A3-103", "B1-201", "B2-202", "B3-203",
				"C1-301", "C2-302", "C3-303" };
		cmbConsultorio.removeAllItems();
		if (control) {
			for (String string : consultorioString) {
				if (!Clinica.getInstance().encontrarConsultorio(string)) {
					cmbConsultorio.addItem(string);
				}
			}
		}
	}
}
