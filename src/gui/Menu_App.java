package gui;

/*Imports from local packages*/
import metodos.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import estructuras.HashTable;
import estructuras.HashTable.nodoHash;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class Menu_App {
	// Displays
	protected JFrame frmEddDrive;
	private JPanel PanelRegistro;
	private JPanel PanelSesion;
	// Texto
	private JTextField txtUser_Log;
	private JPasswordField txtPassword_Log;
	private JTextField txtUser_Reg;
	private JPasswordField txtPassword_Reg;
	// Botones
	private JButton btnRegistro;
	private JButton btnIngresar;
	private JButton btnRegistrar;
	// Variable control de Usuario
	public static nodoHash nodoUsuario;
	public static HashTable usuarios;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu_App window = new Menu_App();
					window.frmEddDrive.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu_App() {
		initialize();
		usuarios = new HashTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEddDrive = new JFrame();
		frmEddDrive.setTitle("EDD DRIVE");
		frmEddDrive.getContentPane().setBackground(new Color(32, 178, 170));
		frmEddDrive.setResizable(false);
		frmEddDrive.setBounds(100, 100, 350, 260);
		frmEddDrive.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEddDrive.setLocationRelativeTo(null);
		frmEddDrive.getContentPane().setLayout(null);
		
		PanelRegistro = new JPanel();
		PanelRegistro.setBackground(new Color(30, 144, 255));
		PanelRegistro.setBounds(0, 0, 344, 231);
		PanelRegistro.setVisible(false);
		frmEddDrive.getContentPane().add(PanelRegistro);
		PanelRegistro.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nuevo Registro");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(93, 11, 164, 34);
		lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 25));
		PanelRegistro.add(lblNewLabel);
		
		JLabel lblUsuario_1 = new JLabel("Usuario:");
		lblUsuario_1.setForeground(new Color(255, 255, 255));
		lblUsuario_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblUsuario_1.setBounds(44, 81, 57, 14);
		PanelRegistro.add(lblUsuario_1);
		
		txtUser_Reg = new JTextField();
		txtUser_Reg.setBounds(155, 81, 145, 20);
		PanelRegistro.add(txtUser_Reg);
		
		JLabel lblContrasea_1 = new JLabel("Contrase\u00F1a: ");
		lblContrasea_1.setForeground(new Color(255, 255, 255));
		lblContrasea_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblContrasea_1.setBounds(44, 124, 101, 14);
		PanelRegistro.add(lblContrasea_1);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (txtPassword_Reg.getText().length() >= 8 && !txtUser_Reg.getText().isEmpty()) {
					// Hacer el proceso de Inserción y su respectivo ingreso a la tabla hash
					if(!txtUser_Reg.getText().equals("Admin") && !usuarios.existe(txtUser_Reg.getText())) {
						try {
							usuarios.insertar(txtUser_Reg.getText(), txtPassword_Reg.getText(), true);
							JOptionPane.showMessageDialog(null, "El Usuario " + txtUser_Reg.getText() + " ha sido registrado...");
							limpiar();
							PanelSesion.setVisible(true);
							PanelRegistro.setVisible(false);
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}else {
						JOptionPane.showMessageDialog(null, "No se ha podido completar la operación\n"
								+ "Hay un usuario registrado con el mismo nombre de usuario...");
					}
				}else if(txtPassword_Reg.getText().length() < 8 && !txtPassword_Reg.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "La contraseña, debe contener al menos 8 caracteres...");
				}else {
					JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios...");
				}
			}
		});
		btnRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnRegistrar.setBounds(123, 180, 101, 34);
		PanelRegistro.add(btnRegistrar);
		
		txtPassword_Reg = new JPasswordField();
		txtPassword_Reg.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		txtPassword_Reg.setBounds(155, 124, 145, 20);
		PanelRegistro.add(txtPassword_Reg);
		
		PanelSesion = new JPanel();
		PanelSesion.setBackground(new Color(32, 178, 170));
		PanelSesion.setBounds(0, 0, 344, 231);
		frmEddDrive.getContentPane().add(PanelSesion);
		PanelSesion.setLayout(null);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (!txtUser_Log.getText().isEmpty() && !txtPassword_Log.getText().isEmpty()) {
					/* Esto se activará solamente cuando el sha-256(contraseña) == sha-256(contraseña) almacenada en la tabla hash.
					 * De igual forma, solamente entrará a este if si encuentra al usuario ingresado existe en la tabla.*/
					try {
						if(txtUser_Log.getText().equals("Admin") && txtPassword_Log.getText().equals("Admin")) {
							frmEddDrive.setVisible(false);
							Admin nd = new Admin();
							nd.setVisible(true);
						}else if (usuarios.existe(txtUser_Log.getText()) && usuarios.getPassHash(txtUser_Log.getText()).equals(Hash.get_sha256(Hash.sha256(txtPassword_Log.getText())))) {
							JOptionPane.showMessageDialog(null, "Bienvenido " + txtUser_Log.getText());
							nodoUsuario = usuarios.getUsuario(txtUser_Log.getText());
							Archivos nuevaVentana = new Archivos();
							nuevaVentana.setVisible(true);
						}
						else {
							JOptionPane.showMessageDialog(null, "El usuario o contraseña son incorrectos!!!");
						}
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnIngresar.setBounds(248, 175, 75, 34);
		PanelSesion.add(btnIngresar);
		btnIngresar.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnIngresar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		btnRegistro = new JButton("Registro");
		btnRegistro.setBounds(25, 175, 77, 34);
		PanelSesion.add(btnRegistro);
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelSesion.setVisible(false);
				PanelRegistro.setVisible(true);
			}
		});
		btnRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRegistro.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnRegistro.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(40, 123, 77, 21);
		PanelSesion.add(lblContrasea);
		lblContrasea.setForeground(new Color(255, 255, 255));
		lblContrasea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(40, 80, 53, 21);
		PanelSesion.add(lblUsuario);
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		txtPassword_Log = new JPasswordField();
		txtPassword_Log.setBounds(156, 121, 145, 25);
		PanelSesion.add(txtPassword_Log);
		txtPassword_Log.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtPassword_Log.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtPassword_Log.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		
		txtUser_Log = new JTextField();
		txtUser_Log.setBounds(156, 82, 145, 20);
		PanelSesion.add(txtUser_Log);
		txtUser_Log.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		txtUser_Log.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblInicioDeSesin = new JLabel("Inicio de Sesi\u00F3n");
		lblInicioDeSesin.setBounds(90, 11, 164, 34);
		PanelSesion.add(lblInicioDeSesin);
		lblInicioDeSesin.setForeground(new Color(255, 255, 255));
		lblInicioDeSesin.setFont(new Font("Segoe UI", Font.ITALIC, 25));
	}
	
	private void limpiar() {
		txtUser_Log.setText(null);
		txtUser_Reg.setText(null);
		txtPassword_Log.setText(null);
		txtPassword_Reg.setText(null);
	}
}
