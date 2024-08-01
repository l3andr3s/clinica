package visual;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Clinica;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class Principal extends JFrame {
	/**
	 * 
	 */
	private Image img_login = new ImageIcon(Login.class.getResource("/img/cerrar-sesion.png")).getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	@SuppressWarnings("unused")
	private Image img_logo = new ImageIcon(Principal.class.getResource("/img/LogoClinica.png")).getImage()
			.getScaledInstance(1894, 985, Image.SCALE_SMOOTH);

	private static final long serialVersionUID = 1L;

	private Dimension dim;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("Medicine Dominican Global");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FileOutputStream clinica2;
				ObjectOutputStream clinicaWrite;
				try {
					clinica2 = new FileOutputStream("Clinica.dat");
					clinicaWrite = new ObjectOutputStream(clinica2);
					clinicaWrite.writeObject(Clinica.getInstance());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/cruz-roja.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dim = getToolkit().getScreenSize();
		setBounds(100, 100, 450, 300);
		setSize(dim.width, dim.height - 25);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Medico");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegMedico reg = new RegMedico(null);
				reg.setModal(true);
				reg.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Listado");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListMedico list = new ListMedico();
				list.setModal(true);
				list.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenu mnNewMenu_1 = new JMenu("Consulta");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Crear Consulta");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					CrearConsulta consulta = new CrearConsulta();
					consulta.setModal(true); // Si deseas que sea modal
					consulta.setVisible(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_2 = new JMenu("Vacuna");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Registrar");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegVacuna vacuna = new RegVacuna();
				vacuna.setModal(true);
				vacuna.setVisible(true);
			}
		});

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Listar");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListVacuna listVacuna = new ListVacuna();
				listVacuna.setModal(true);
				listVacuna.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_7);
		mnNewMenu_2.add(mntmNewMenuItem_3);

		JMenu mnNewMenu_3 = new JMenu("Cita");
		menuBar.add(mnNewMenu_3);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Crear Citas");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CrearCitas cita = new CrearCitas();
				cita.setVisible(true);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_4);

		JMenu mnNewMenu_4 = new JMenu("Enfermedad");
		menuBar.add(mnNewMenu_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Listado");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListEnfermedad list = new ListEnfermedad();
				list.setModal(true);
				list.setVisible(true);
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_5);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Registrar");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegEnfermedad consulta = new RegEnfermedad(null);
				consulta.setModal(true);
				consulta.setVisible(true);
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_6);

		JMenu mnNewMenu_5 = new JMenu("Reporte");
		menuBar.add(mnNewMenu_5);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Control");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReporteList listrepList = new ReporteList();
				listrepList.setModal(true);
				listrepList.setVisible(true);
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_8);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 1894, 985);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("Cerrar Sesión");
		btnNewButton.setVisible(false);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro/a que desea cerrar sesion?",
						"Confirmacion", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					dispose(); // Aca se debe poner el metodo que cierre sesion correctamente.
				}
			}
		});
		btnNewButton.setIcon(new ImageIcon(img_login));
		btnNewButton.setBounds(1728, 13, 152, 62);
		panel.add(btnNewButton);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(0, 0, 1894, 985);
		panel.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/LogoClinica (1).png")));

	}
}
