package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import logico.Clinica;
import logico.Enfermedad;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Calendar;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;

public class RegEnfermedad extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JComboBox<Object> cbxTipo;
	private JSpinner spnFecha;
	private JTextPane textPane;
	private Enfermedad miEnfermedad = null;
	private JSlider sliderPorcentaje;
	private JLabel TxtValor;
	private JLabel advertencia2;
	private JLabel advertencia1;
	private JLabel advertencia3;
	private JLabel MensajeAdvertencia;
	private JLabel advertencia4;
	private JButton okButton;
	private JButton cancelButton;
	private boolean updated = false;

	public static void main(String[] args) {
		try {
			RegEnfermedad dialog = new RegEnfermedad(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegEnfermedad(Enfermedad ef) {
		miEnfermedad = ef;
		if (ef == null) {
			setTitle("Registro de Enfermedad");
		} else {
			setTitle("Modificar Registro de Enfermedad");
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegEnfermedad.class.getResource("/img/cruz-roja.png")));
		setBounds(100, 100, 681, 616);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Código:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 32, 59, 50);
		contentPanel.add(lblNewLabel);

		txtCodigo = new JTextField();
		txtCodigo.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(73, 47, 96, 21);
		contentPanel.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(20, 94, 59, 14);
		contentPanel.add(lblNewLabel_1);

		txtNombre = new JTextField();
		txtNombre.setBounds(20, 117, 238, 21);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Tipo:");
		lblNewLabel_2.setBounds(20, 161, 59, 14);
		contentPanel.add(lblNewLabel_2);

		cbxTipo = new JComboBox<>();
		cbxTipo.setBounds(20, 184, 238, 21);
		cbxTipo.setModel(new DefaultComboBoxModel<>(
				new String[] { "<Seleccione>", "Virus", "Bacteriana", "Hongos", "Nutricional", "Traumatica", "Genética",
						"Congenita", "Mental", "Degenerativa", "Autoinmune", "Cardiovascular", "Alérgica" }));
		contentPanel.add(cbxTipo);

		JLabel lblNewLabel_3 = new JLabel("Descubierta en:");
		lblNewLabel_3.setBounds(20, 236, 91, 14);
		contentPanel.add(lblNewLabel_3);

		spnFecha = new JSpinner();
		spnFecha.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.YEAR));
		spnFecha.setValue(new Date());
		spnFecha.setEditor(new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy"));
		spnFecha.setBounds(20, 261, 238, 21);
		contentPanel.add(spnFecha);

		JLabel lblNewLabel_4 = new JLabel("Descripción:");
		lblNewLabel_4.setBounds(20, 319, 71, 14);
		contentPanel.add(lblNewLabel_4);

		textPane = new JTextPane();
		textPane.setBounds(10, 344, 325, 148);
		contentPanel.add(textPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Tasa de Transmisibilidad:", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel_1.setBounds(391, 244, 250, 248);
		contentPanel.add(panel_1);

		TxtValor = new JLabel("");
		TxtValor.setBounds(430, 501, 173, 22);
		contentPanel.add(TxtValor);

		sliderPorcentaje = new JSlider();
		sliderPorcentaje.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				TxtValor.setText("Tasa de transmisibilidad: " + sliderPorcentaje.getValue());
			}
		});
		panel_1.add(sliderPorcentaje);
		sliderPorcentaje.setPaintTicks(true);
		sliderPorcentaje.setPaintLabels(true);
		sliderPorcentaje.setValue(0);
		sliderPorcentaje.setOrientation(SwingConstants.VERTICAL);
		sliderPorcentaje.setMajorTickSpacing(20);
		sliderPorcentaje.setMinorTickSpacing(20);

		JLabel LblLogo = new JLabel("");
		LblLogo.setBounds(414, 31, 205, 200);
		LblLogo.setIcon(new ImageIcon(new ImageIcon(RegEnfermedad.class.getResource("/img/TablaEnfermedades.jpg"))
				.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		contentPanel.add(LblLogo);

		advertencia1 = new JLabel("");
		advertencia1.setIcon(new ImageIcon(RegEnfermedad.class.getResource("/img/signo-advertencia.png")));
		advertencia1.setBounds(5, 94, 16, 14);
		advertencia1.setVisible(false);
		contentPanel.add(advertencia1);

		advertencia2 = new JLabel("");
		advertencia2.setIcon(new ImageIcon(RegEnfermedad.class.getResource("/img/signo-advertencia.png")));
		advertencia2.setBounds(5, 161, 16, 14);
		advertencia2.setVisible(false);
		contentPanel.add(advertencia2);

		advertencia3 = new JLabel("");
		advertencia3.setIcon(new ImageIcon(RegEnfermedad.class.getResource("/img/signo-advertencia.png")));
		advertencia3.setBounds(5, 319, 16, 14);
		advertencia3.setVisible(false);
		contentPanel.add(advertencia3);

		MensajeAdvertencia = new JLabel("Los Campos con     son Obligatorios");
		MensajeAdvertencia.setFont(new Font("Tahoma", Font.BOLD, 14));
		MensajeAdvertencia.setBounds(41, 503, 263, 22);
		MensajeAdvertencia.setVisible(false);
		contentPanel.add(MensajeAdvertencia);

		advertencia4 = new JLabel("");
		advertencia4.setIcon(new ImageIcon(RegEnfermedad.class.getResource("/img/signo-advertencia.png")));
		advertencia4.setBounds(159, 509, 16, 14);
		advertencia4.setVisible(false);
		contentPanel.add(advertencia4);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (miEnfermedad == null) {
					if (txtNombre.getText().equals("") || cbxTipo.getSelectedItem().equals("<Seleccione>")
							|| textPane.getText().equals("")) {
						advertencia();
					} else {
						Enfermedad nuevaEnfermedad = new Enfermedad(txtCodigo.getText(), txtNombre.getText(),
								cbxTipo.getSelectedItem().toString(), textPane.getText(), (Date) spnFecha.getValue(),
								sliderPorcentaje.getValue());
						Clinica.getInstance().insertarEnfermedad(nuevaEnfermedad);
						JOptionPane.showMessageDialog(null, "Operación exitosa", "Información",
								JOptionPane.INFORMATION_MESSAGE);
						clean();
					}
				} else {
					miEnfermedad.setCodigoString(txtCodigo.getText());
					miEnfermedad.setNombreString(txtNombre.getText());
					miEnfermedad.setTipoString(cbxTipo.getSelectedItem().toString());
					miEnfermedad.setInformacionString(textPane.getText());
					miEnfermedad.setDescubierta((Date) spnFecha.getValue());
					miEnfermedad.setTransmisibilidad(sliderPorcentaje.getValue());
					Clinica.getInstance().actualizarEnfermedad(miEnfermedad);
					JOptionPane.showMessageDialog(null, "Operación exitosa", "Información",
							JOptionPane.INFORMATION_MESSAGE);
					updated = true;
					dispose();
				}
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cancelar?",
						"Confirmación", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		if (miEnfermedad == null) {
			okButton.setText("Registrar");
			txtCodigo.setText(Clinica.getInstance().getNextEnfermedadCode()); // Asignar el próximo código de enfermedad
		} else {
			okButton.setText("Modificar");
			loadEnfermedad(miEnfermedad);
		}
	}

	private void loadEnfermedad(Enfermedad ef) {
		if (ef != null) {
			txtCodigo.setText(ef.getCodigoString());
			txtNombre.setText(ef.getNombreString());
			cbxTipo.setSelectedItem(ef.getTipoString());
			textPane.setText(ef.getInformacionString());
			spnFecha.setValue(ef.getDescubierta());
			sliderPorcentaje.setValue(ef.getTransmisibilidad());
		}
	}

	private void clean() {
		txtNombre.setText("");
		textPane.setText("");
		cbxTipo.setSelectedIndex(0);
		spnFecha.setValue(new Date());
		txtCodigo.setText(Clinica.getInstance().getNextEnfermedadCode()); // Asignar el próximo código de enfermedad
		sliderPorcentaje.setValue(0);
	}

	private void advertencia() {
		advertencia1.setVisible(true);
		advertencia2.setVisible(true);
		advertencia3.setVisible(true);
		advertencia4.setVisible(true);
		MensajeAdvertencia.setVisible(true);
		Toolkit.getDefaultToolkit().beep();
	}

	public boolean isUpdated() {
		return updated;
	}

	public Enfermedad getEnfermedad() {
		return miEnfermedad;
	}
}
