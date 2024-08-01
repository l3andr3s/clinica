package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;

import logico.Clinica;
import logico.Enfermedad;
import logico.Vacuna;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class RegVacuna extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private Random rand = new Random();
	private JTextField txtNombre;
	private JList<String> list;
	private DefaultListModel<String> lista1;
	private JList<String> list_1;
	private DefaultListModel<String> lista2;
	private JComboBox<Object> cbxAd;
	private JComboBox<Object> cbxLab;
	private ArrayList<Enfermedad> enfermedads;
	private JLabel advertenciaNombre;
	private JLabel advertenciaLab;
	private JLabel advertenciaAdmin;
	private JLabel Mensaje;
	private JLabel advertencia;

	public static void main(String[] args) {
		try {
			RegVacuna dialog = new RegVacuna();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegVacuna() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegVacuna.class.getResource("/img/cruz-roja.png")));
		setTitle("Registrar Vacuna");
		setBounds(100, 100, 456, 493);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Código:");
		lblNewLabel.setBounds(26, 11, 62, 14);
		panel.add(lblNewLabel);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(26, 26, 157, 20);
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);
		String nextVacunaCode = Clinica.getInstance().getNextVacunaCode();
		if (nextVacunaCode != null) {
			txtCodigo.setText(nextVacunaCode);
		} else {
			txtCodigo.setText("V001");
		}

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(26, 57, 62, 14);
		panel.add(lblNewLabel_1);

		txtNombre = new JTextField();
		txtNombre.setBounds(26, 71, 157, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Laboratorio:");
		lblNewLabel_2.setBounds(238, 11, 91, 14);
		panel.add(lblNewLabel_2);

		cbxLab = new JComboBox<Object>();
		cbxLab.setBounds(236, 26, 157, 20);
		cbxLab.setModel(new DefaultComboBoxModel<Object>(new String[] { "<Seleccione>", "Johnson & Johnson", "Roche",
				"AbbVie", "Bayer", "Pfizer", "Bristol-Myers Squibb", "Merck & Co", "Novartis", "Sanofi" }));
		panel.add(cbxLab);

		JLabel lblNewLabel_3 = new JLabel("Via de Administración:");
		lblNewLabel_3.setBounds(238, 57, 129, 16);
		panel.add(lblNewLabel_3);

		cbxAd = new JComboBox<Object>();
		cbxAd.setBounds(236, 71, 157, 20);
		cbxAd.setModel(new DefaultComboBoxModel<Object>(
				new String[] { "<Seleccione>", "Intravenosa", "Intramuscular", "Subcutanea" }));
		panel.add(cbxAd);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(12, 121, 401, 233);
		panel.add(panel_3);
		panel_3.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 35, 157, 187);
		panel_3.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		lista1 = new DefaultListModel<>();
		list = new JList<>(lista1);
		scrollPane.setViewportView(list);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(234, 35, 157, 187);
		panel_3.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		lista2 = new DefaultListModel<>();
		list_1 = new JList<>(lista2);
		scrollPane_1.setViewportView(list_1);

		JLabel lblNewLabel_4 = new JLabel("Listado de enfermedades:");
		lblNewLabel_4.setBounds(10, 10, 157, 14);
		panel_3.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("La vacuna protege contra:");
		lblNewLabel_5.setBounds(234, 10, 157, 14);
		panel_3.add(lblNewLabel_5);

		JButton btnNewButton = new JButton(">");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() > -1) {
					lista2.addElement(list.getSelectedValue());
					lista1.remove(list.getSelectedIndex());
				}
			}
		});
		btnNewButton.setBounds(176, 51, 48, 23);
		panel_3.add(btnNewButton);

		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list_1.getSelectedIndex() > -1) {
					lista1.addElement(list_1.getSelectedValue());
					lista2.remove(list_1.getSelectedIndex());
				}
			}
		});
		button.setBounds(175, 86, 49, 23);
		panel_3.add(button);

		advertenciaNombre = new JLabel("");
		advertenciaNombre.setIcon(new ImageIcon(RegVacuna.class.getResource("/img/signo-advertencia.png")));
		advertenciaNombre.setBounds(12, 71, 18, 20);
		advertenciaNombre.setVisible(false);
		panel.add(advertenciaNombre);

		advertenciaLab = new JLabel("");
		advertenciaLab.setIcon(new ImageIcon(RegVacuna.class.getResource("/img/signo-advertencia.png")));
		advertenciaLab.setBounds(218, 28, 18, 20);
		advertenciaLab.setVisible(false);
		panel.add(advertenciaLab);

		advertenciaAdmin = new JLabel("");
		advertenciaAdmin.setIcon(new ImageIcon(RegVacuna.class.getResource("/img/signo-advertencia.png")));
		advertenciaAdmin.setBounds(218, 73, 18, 20);
		advertenciaAdmin.setVisible(false);
		panel.add(advertenciaAdmin);

		Mensaje = new JLabel("Los Campos con     son Obligatorios");
		Mensaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		Mensaje.setBounds(94, 367, 250, 14);
		Mensaje.setVisible(false);
		panel.add(Mensaje);

		advertencia = new JLabel("");
		advertencia.setIcon(new ImageIcon(RegVacuna.class.getResource("/img/signo-advertencia.png")));
		advertencia.setBounds(212, 367, 18, 16);
		advertencia.setVisible(false);
		panel.add(advertencia);

		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cancelar?",
						"Confirmación", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});

		JButton okButton = new JButton("Registrar");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enfermedads = new ArrayList<>();
				for (int i = 0; i < list_1.getModel().getSize(); i++) {
					enfermedads.add(Clinica.getInstance()
							.buscarEnfermedadByCodigo(list_1.getModel().getElementAt(i).substring(0, 4)));
				}
				Vacuna auxVacuna = null;
				if (txtNombre.getText().equals("") || cbxLab.getSelectedItem().equals("<Seleccione>")
						|| cbxAd.getSelectedItem().equals("<Seleccione>")) {
					Advertencia();
				} else {
					auxVacuna = new Vacuna(txtCodigo.getText(), txtNombre.getText(),
							cbxLab.getSelectedItem().toString(), cbxAd.getSelectedItem().toString(), enfermedads);
					Clinica.getInstance().insertarVacuna(auxVacuna);
					JOptionPane.showMessageDialog(null, "Operación exitosa", "Información",
							JOptionPane.INFORMATION_MESSAGE);
					clean();
				}
			}
		});

		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		generarList();
	}

	private void generarList() {
		for (Enfermedad ef : Clinica.getInstance().getEnfermedades()) {
			lista1.addElement(ef.getCodigoString() + "-" + ef.getNombreString());
		}
	}

	private void clean() {
		String nextVacunaCode = Clinica.getInstance().getNextVacunaCode();
		if (nextVacunaCode != null) {
			txtCodigo.setText(nextVacunaCode);
		} else {
			txtCodigo.setText("V001");
		}
		txtNombre.setText("");
		cbxAd.setSelectedIndex(0);
		cbxLab.setSelectedIndex(0);
		lista2.removeAllElements();
		lista1.removeAllElements();
		generarList();
	}

	private void Advertencia() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		advertenciaAdmin.setVisible(true);
		advertenciaLab.setVisible(true);
		advertenciaNombre.setVisible(true);
		advertencia.setVisible(true);
		Mensaje.setVisible(true);
		tk.beep();
	}
}
