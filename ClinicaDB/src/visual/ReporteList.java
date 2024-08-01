package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.ConsultaEnfermedad;
import logico.Dosis;

public class ReporteList extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableEnfermedades;
	private JTable tableVacunas;
	private JPanel pnlVacunas;
	private JPanel pnlEnfermedades;
	private DefaultTableModel modelEnfermedades;
	private Object rowEnfermedades[];
	private DefaultTableModel modelVacunas;
	private Object rowVacunas[];
	private JToggleButton tglbtnEnfermedades;
	private JToggleButton tglbtnVacuna;

	public static void main(String[] args) {
		try {
			ReporteList dialog = new ReporteList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ReporteList() {
		setTitle("Listas de Reporte");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ReporteList.class.getResource("/img/cruz-roja.png")));
		setBounds(100, 100, 600, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		pnlEnfermedades = new JPanel();
		pnlEnfermedades.setBounds(5, 5, 574, 488);
		contentPanel.add(pnlEnfermedades);
		pnlEnfermedades.setLayout(null);

		JScrollPane scrollPaneEnfermedades = new JScrollPane();
		scrollPaneEnfermedades.setBounds(5, 5, 559, 488);
		pnlEnfermedades.add(scrollPaneEnfermedades);

		String headersEnfermedades[] = { "ID", "Nombre", "Apellido", "Enfermedad" };
		modelEnfermedades = new DefaultTableModel();
		modelEnfermedades.setColumnIdentifiers(headersEnfermedades);
		tableEnfermedades = new JTable();
		tableEnfermedades.setModel(modelEnfermedades);
		scrollPaneEnfermedades.setViewportView(tableEnfermedades);

		pnlVacunas = new JPanel();
		pnlVacunas.setBounds(5, 5, 574, 488);
		contentPanel.add(pnlVacunas);
		pnlVacunas.setLayout(null);

		JScrollPane scrollPaneVacunas = new JScrollPane();
		scrollPaneVacunas.setBounds(5, 5, 559, 488);
		pnlVacunas.add(scrollPaneVacunas);

		String headersVacunas[] = { "ID", "Nombre", "Apellido", "Vacuna" };
		modelVacunas = new DefaultTableModel();
		modelVacunas.setColumnIdentifiers(headersVacunas);
		tableVacunas = new JTable();
		tableVacunas.setModel(modelVacunas);
		scrollPaneVacunas.setViewportView(tableVacunas);

		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Aceptar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		tglbtnEnfermedades = new JToggleButton("Enfermedades");
		tglbtnEnfermedades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tglbtnVacuna.setSelected(false);
				pnlVacunas.setVisible(false);
				pnlEnfermedades.setVisible(true);
				loadTableEnfermedades();
			}
		});
		menuBar.add(tglbtnEnfermedades);

		tglbtnVacuna = new JToggleButton("Vacunas");
		tglbtnVacuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tglbtnEnfermedades.setSelected(false);
				pnlVacunas.setVisible(true);
				pnlEnfermedades.setVisible(false);
				loadTableVacunas();
			}
		});
		menuBar.add(tglbtnVacuna);

		// Mostrar el panel de enfermedades y cargar los datos al iniciar
		tglbtnEnfermedades.setSelected(true);
		pnlVacunas.setVisible(false);
		pnlEnfermedades.setVisible(true);
		loadTableEnfermedades();
	}

	private void loadTableEnfermedades() {
		modelEnfermedades.setRowCount(0);
		rowEnfermedades = new Object[modelEnfermedades.getColumnCount()];
		try {
			List<ConsultaEnfermedad> consultasEnfermedades = Clinica.getInstance().getConsultasEnfermedades();
			for (ConsultaEnfermedad consultaEnfermedad : consultasEnfermedades) {
				rowEnfermedades[0] = consultaEnfermedad.getConsulta().getUsuario().getID();
				rowEnfermedades[1] = consultaEnfermedad.getConsulta().getUsuario().getNombre();
				rowEnfermedades[2] = consultaEnfermedad.getConsulta().getUsuario().getApellido();
				rowEnfermedades[3] = consultaEnfermedad.getEnfermedad().getNombreString();
				modelEnfermedades.addRow(rowEnfermedades);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadTableVacunas() {
		modelVacunas.setRowCount(0);
		rowVacunas = new Object[modelVacunas.getColumnCount()];
		try {
			List<Dosis> dosisList = Clinica.getInstance().getDosisList();
			for (Dosis dosis : dosisList) {
				rowVacunas[0] = dosis.getPaciente().getID(); // ID del paciente
				rowVacunas[1] = dosis.getPaciente().getNombre();
				rowVacunas[2] = dosis.getPaciente().getApellido();
				rowVacunas[3] = dosis.getVacinneVacuna().getNombreString();
				modelVacunas.addRow(rowVacunas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
