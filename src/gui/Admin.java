package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;

public class Admin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtDirFile;
	private JTextPane txtProblemas;
	private JScrollPane scrollProblemas;
	private JPanel panelCargaMasiva;

	/* Botones dentro del men� de Administrador */
	private JMenuItem mntmAcciones;
	private JButton btnExaminar;

	private BufferedReader lectorArchivo;
	private String lineaLeida;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin set = new Admin();
					set.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Admin() {
		getContentPane().setBackground(Color.DARK_GRAY);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				Linker.app.frmEddDrive.setVisible(true);
				Linker.admin.setVisible(false);
			}
		});
		setTitle("Men\u00FA Administrador");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuAcciones = new JMenu("Acciones");
		menuBar.add(menuAcciones);

		mntmAcciones = new JMenuItem("Carga Masiva");
		mntmAcciones.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent arg0) {
				// Cuando la carga masiva se activa
				// procederemos a pedir los datos del archivo de entrada
				// que en este caso es un archivo .csv
				// que contendr� los distintos usuarios que ingresaran en el sistema...
				// del mismo modo, se proceder� a realizar un metodo con el cual
				// ir guardando todos los usuarios del archivo .csv
				// que no lograron ingresar...

				/*
				 * Por el momento, vamos a crear la interfaz de la carga masiva de usuarios...
				 */
				panelCargaMasiva.setVisible(true);
			}
		});
		menuAcciones.add(mntmAcciones);
		
		JMenuItem mntmCerrarSesin = new JMenuItem("Cerrar Sesi\u00F3n");
		mntmCerrarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Linker.app.frmEddDrive.setVisible(true);
				Linker.admin.setVisible(false);
			}
		});
		menuAcciones.add(mntmCerrarSesin);
		getContentPane().setLayout(null);

		panelCargaMasiva = new JPanel();
		panelCargaMasiva.setBackground(new Color(0, 51, 204));
		panelCargaMasiva.setBounds(0, 0, 444, 250);
		panelCargaMasiva.setVisible(false);
		getContentPane().add(panelCargaMasiva);
		panelCargaMasiva.setLayout(null);

		btnExaminar = new JButton("Examinar...");
		btnExaminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Con el bot�n examinar, procederemos a abrir el explorador de archivos
				// para poder con el, elegir el archivo para leerlo.
				JFileChooser jf = new JFileChooser();
				File fl = null;
				String[] datos;
				
				jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileFilter filtro = new FileNameExtensionFilter("Archivos CSV", "csv");
				jf.setFileFilter(filtro);
				if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// Si seleccionamos el archivo, lo leeremos.
					try {
						fl = jf.getSelectedFile();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
					if (fl.canRead()) {
						txtDirFile.setText(fl.getName());
						// Lectura de Archivo
						try {
							lectorArchivo = new BufferedReader(new InputStreamReader(new FileInputStream(fl.getAbsolutePath()), "utf-8"));
							try {
								lineaLeida = lectorArchivo.readLine();
								String errores = "Errores Registrados:\n"
										+ "Usuario:\t\tMotivo:\n";
								int registrados = 0;
								while(lineaLeida != null) {
									datos = lineaLeida.split(",");
									/*
									 * datos[0] = usuario
									 * datos[1] = contrase�a
									 * */
									if(Linker.usuarios.existe(datos[0]) || datos[0].equals("Admin")) {
										errores += datos[0] + "\t\tya se encuentra Registrado:\n";
									}else {
										// Si no existe, entonces
										// comprobar� que su contrase�a, tenga por lo menos 8 caracteres.
										if(datos[1].length() >= 8) {
											// Proceso de Inserci�n
											Linker.usuarios.insertar(datos[0], datos[1]);
											registrados++;
										}else {
											errores += datos[0] + "\t\tLa contrase�a no cumple con los 8 caracteres m�nimos";
										}
									}
									lineaLeida = lectorArchivo.readLine();
								}
								
								String resultado = "Usuarios Registrados: " + registrados + "\n\n";
								resultado += errores;
								
								txtProblemas.setText(resultado);
							} catch (IOException | NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						} catch (FileNotFoundException | UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				}
				// Intentamos resguardar los datos y los insertamos...
				
			}
		});
		btnExaminar.setBounds(322, 40, 112, 25);
		panelCargaMasiva.add(btnExaminar);

		JLabel lblCargaMasiva = new JLabel("CARGA MASIVA");
		lblCargaMasiva.setForeground(new Color(255, 255, 255));
		lblCargaMasiva.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		lblCargaMasiva.setHorizontalAlignment(SwingConstants.CENTER);
		lblCargaMasiva.setBounds(10, 11, 115, 14);
		panelCargaMasiva.add(lblCargaMasiva);

		txtDirFile = new JTextField();
		txtDirFile.setBounds(10, 40, 300, 25);
		panelCargaMasiva.add(txtDirFile);
		txtDirFile.setColumns(10);

		JLabel lblProblemas = new JLabel("Resultados:");
		lblProblemas.setForeground(new Color(255, 255, 255));
		lblProblemas.setBounds(10, 80, 89, 14);
		panelCargaMasiva.add(lblProblemas);

		txtProblemas = new JTextPane();
		txtProblemas.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		scrollProblemas = new JScrollPane(txtProblemas);
		scrollProblemas.setBounds(10, 95, 424, 144);
		scrollProblemas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollProblemas.setVisible(true);
		panelCargaMasiva.add(scrollProblemas);
	}
}
