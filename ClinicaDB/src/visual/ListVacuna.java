package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.Clinica;
import logico.Vacuna;

public class ListVacuna extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Object row[];
	private JButton ButtonDelete;
	private Vacuna vacunaselect = null;

	public static void main(String[] args) {
		try {
			ListVacuna dialog = new ListVacuna();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListVacuna() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListVacuna.class.getResource("/img/cruz-roja.png")));
		setTitle("Lista de Vacunas");
		setBounds(100, 100, 668, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int row = 0;
							row = table.getSelectedRow();
							if (row > -1) {
								ButtonDelete.setEnabled(true);
								vacunaselect = Clinica.getInstance()
										.buscarVacunaByCodigo(table.getValueAt(row, 0).toString());
							}
						}
					});
					String headers[] = { "Código", "Nombre", "Laboratorio", "Administracion", "Enfermedades" };
					model = new DefaultTableModel();
					model.setColumnIdentifiers(headers);
					scrollPane.setViewportView(table);
				}
				table.setModel(model);
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				ButtonDelete = new JButton("Eliminar");
				ButtonDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (vacunaselect != null) {
							int option = JOptionPane.showConfirmDialog(null,
									"¿Está seguro de eliminar la vacuna: " + vacunaselect.getCodigo() + "?",
									"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (option == JOptionPane.YES_OPTION) {
								Clinica.getInstance().eliminarVacuna(vacunaselect);
								loadTable();
							}
						}
					}
				});
				ButtonDelete.setEnabled(false);
				ButtonDelete.setActionCommand("Cancel");
				buttonPane.add(ButtonDelete);
			}
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		loadTable();
	}

	private void loadTable() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		for (Vacuna object : Clinica.getInstance().getVacunas()) {
			row[0] = object.getCodigo();
			row[1] = object.getNombreString();
			row[2] = object.getLaboratorioString();
			row[3] = object.getAdministracionString();
			row[4] = retornaString(object);
			model.addRow(row);
		}
	}

	private String retornaString(Vacuna object) {
		StringBuilder listaefString = new StringBuilder();
		for (int i = 0; i < object.getEnfermedades().size(); i++) {
			if (i != 0) {
				listaefString.append(", ");
			}
			listaefString.append(object.getEnfermedades().get(i).getNombreString());
		}
		return listaefString.toString();
	}
}
