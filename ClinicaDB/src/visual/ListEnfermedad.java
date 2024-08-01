package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.Clinica;
import logico.Enfermedad;
import java.awt.Toolkit;

public class ListEnfermedad extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel model;
	private Object row[];
	private JTable table;
	private Enfermedad enfermedaSelect = null;
	private JButton btnMod;
	private JButton btnDelete;

	public static void main(String[] args) {
		try {
			ListEnfermedad dialog = new ListEnfermedad();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListEnfermedad() {
		setTitle("Listado de Enfermedades");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ListEnfermedad.class.getResource("/img/senal-de-peligro-biologico (1).png")));
		setBounds(100, 100, 950, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		String headers[] = { "Código", "Nombre", "Tipo", "Informacion", "Fecha de Aparición",
				"Tasa de Transmisibilidad" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(headers);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row > -1) {
					btnDelete.setEnabled(true);
					btnMod.setEnabled(true);
					enfermedaSelect = Clinica.getInstance()
							.buscarEnfermedadByCodigo(table.getValueAt(row, 0).toString());
				}
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(model);
		scrollPane.setViewportView(table);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnMod = new JButton("Modificar");
		btnMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (enfermedaSelect != null) {
					RegEnfermedad ef = new RegEnfermedad(enfermedaSelect);
					ef.setModal(true);
					ef.setVisible(true);
					if (ef.isUpdated()) {
						Clinica.getInstance().actualizarEnfermedad(ef.getEnfermedad());
						loadTable();
					}
				}
			}
		});
		btnMod.setEnabled(false);
		buttonPane.add(btnMod);

		btnDelete = new JButton("Eliminar");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (enfermedaSelect != null) {
					int option = JOptionPane.showConfirmDialog(null,
							"Está seguro de eliminar la enfermedad: " + enfermedaSelect.getCodigoString(),
							"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (option == JOptionPane.YES_OPTION) {
						Clinica.getInstance().eliminarEnfermedad(enfermedaSelect);
						loadTable();
					}
				}
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setActionCommand("Cancel");
		buttonPane.add(btnDelete);

		JButton okButton = new JButton("Aceptar");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		loadTable();
	}

	private void loadTable() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		List<Enfermedad> enfermedades = Clinica.getInstance().getEnfermedades();
		for (Enfermedad enfermedad : enfermedades) {
			row[0] = enfermedad.getCodigoString();
			row[1] = enfermedad.getNombreString();
			row[2] = enfermedad.getTipoString();
			row[3] = enfermedad.getInformacionString();
			row[4] = new SimpleDateFormat("dd/MM/yyyy").format(enfermedad.getDescubierta());
			row[5] = enfermedad.getTransmisibilidad() + "%";
			model.addRow(row);
		}
	}

}
