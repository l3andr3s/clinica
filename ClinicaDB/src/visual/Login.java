package visual;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.border.MatteBorder;
import logico.Clinica;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.SwingConstants;

public class Login extends JDialog {

	private Image img_logo = new ImageIcon(Login.class.getResource("/img/hospital.png")).getImage()
			.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
	private Image img_user = new ImageIcon(Login.class.getResource("/img/profile.png")).getImage().getScaledInstance(40,
			40, Image.SCALE_SMOOTH);
	private Image img_pass = new ImageIcon(Login.class.getResource("/img/padlock.png")).getImage().getScaledInstance(30,
			30, Image.SCALE_SMOOTH);
	private Image img_login = new ImageIcon(Login.class.getResource("/img/cerrar-sesion.png")).getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsuario;
	private JLabel lblLoginMsj;
	private JPasswordField pwdPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login dialog = new Login();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/cruz-roja.png")));
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setUndecorated(true);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 139));
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 255, 0)));
		panel_1.setBackground(new Color(30, 144, 255));
		panel_1.setBounds(10, 11, 570, 418);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel LblSalir = new JLabel("X");
		LblSalir.setForeground(new Color(255, 255, 255));
		LblSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cerrar el programa?",
						"Confirmación", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					dispose();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				LblSalir.setForeground(Color.red);
			}

			public void mouseExited(MouseEvent e) {
				LblSalir.setForeground(Color.white);
			}
		});
		LblSalir.setFont(new Font("Tahoma", Font.BOLD, 20));
		LblSalir.setBounds(535, 11, 25, 14);
		panel_1.add(LblSalir);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(185, 179, 200, 40);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		txtUsuario = new JTextField();
		txtUsuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtUsuario.getText().equals("Usuario")) {
					txtUsuario.setText("");
				} else {
					txtUsuario.selectAll();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtUsuario.getText().equals("")) {
					txtUsuario.setText("Usuario");
				}
			}
		});
		txtUsuario.setBorder(null);
		txtUsuario.setText("Usuario");
		txtUsuario.setBounds(0, 0, 150, 40);
		panel_2.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblUserLogo = new JLabel("");
		lblUserLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserLogo.setBounds(131, 0, 88, 40);
		panel_2.add(lblUserLogo);
		lblUserLogo.setIcon(new ImageIcon(img_user));

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(185, 248, 200, 40);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(0, 0, 150, 40);
		pwdPassword.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				if (pwdPassword.getText().equals("Contraseña")) {
					pwdPassword.setEchoChar('●');
					pwdPassword.setText("");
				} else {
					pwdPassword.selectAll();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent e) {
				if (pwdPassword.getText().equals("")) {
					pwdPassword.setText("Contraseña");
					pwdPassword.setEchoChar((char) 0);
				}
			}
		});
		pwdPassword.setBorder(null);
		pwdPassword.setEchoChar((char) 0);
		panel_3.add(pwdPassword);
		pwdPassword.setText("Contraseña");

		JLabel lblPassLogo = new JLabel("");
		lblPassLogo.setBounds(151, -12, 49, 64);
		lblPassLogo.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblPassLogo);
		lblPassLogo.setIcon(new ImageIcon(img_pass));

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				Toolkit tk = Toolkit.getDefaultToolkit();
				System.out.println("Usuario: " + txtUsuario.getText());
				System.out.println("Password: " + pwdPassword.getText());
				if (Clinica.getInstance().confirmLogin(txtUsuario.getText(), pwdPassword.getText())) {
					Principal frame = new Principal();
					dispose();
					frame.setVisible(true);
				} else if (txtUsuario.getText().equals("") || txtUsuario.getText().equals("Usuario")
						|| pwdPassword.getText().equals("") || pwdPassword.getText().equals("Contraseña")) {
					lblLoginMsj.setText("¡Favor llenar los campos!");
					tk.beep();
				} else {
					lblLoginMsj.setText("¡Usuario o Contraseña incorrecta!");
					tk.beep();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel_4.setBackground(Color.lightGray);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel_4.setBackground(Color.white);
			}
		});
		panel_4.setBounds(212, 324, 145, 47);
		panel_1.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblNewLabel = new JLabel("Iniciar Sesión");
		lblNewLabel.setBounds(41, 13, 95, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_4.add(lblNewLabel);

		JLabel lblLoginLogo = new JLabel("");
		lblLoginLogo.setBounds(0, 8, 30, 30);
		panel_4.add(lblLoginLogo);
		lblLoginLogo.setIcon(new ImageIcon(img_login));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(196, 61, 177, 108);
		panel_1.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(img_logo));

		lblLoginMsj = new JLabel("");
		lblLoginMsj.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginMsj.setBounds(185, 295, 200, 16);
		panel_1.add(lblLoginMsj);
	}
}
