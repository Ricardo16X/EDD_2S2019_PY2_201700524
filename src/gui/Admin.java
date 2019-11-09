package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
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

public class Admin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtDirFile;
	private JTextPane txtProblemas;
	private JScrollPane scrollProblemas;

	/* Botones dentro del menú de Administrador */
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
					Admin frame = new Admin();
					frame.setVisible(true);
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
		setTitle("Men\u00FA Administrador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuAcciones = new JMenu("Acciones");
		menuBar.add(menuAcciones);

		mntmAcciones = new JMenuItem("Carga Masiva");
		mntmAcciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Cuando la carga masiva se activa
				// procederemos a pedir los datos del archivo de entrada
				// que en este caso es un archivo .csv
				// que contendrá los distintos usuarios que ingresaran en el sistema...
				// del mismo modo, se procederá a realizar un metodo con el cual
				// ir guardando todos los usuarios del archivo .csv
				// que no lograron ingresar...

				/*
				 * Por el momento, vamos a crear la interfaz de la carga masiva de usuarios...
				 */
			}
		});
		menuAcciones.add(mntmAcciones);
		getContentPane().setLayout(null);

		JPanel panelCargaMasiva = new JPanel();
		panelCargaMasiva.setBounds(0, 0, 434, 240);
		getContentPane().add(panelCargaMasiva);
		panelCargaMasiva.setLayout(null);

		btnExaminar = new JButton("Examinar...");
		btnExaminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Con el botón examinar, procederemos a abrir el explorador de archivos
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
							lectorArchivo = new BufferedReader(new FileReader(fl.getAbsolutePath()));
							try {
								lineaLeida = lectorArchivo.readLine();
								while(lineaLeida != null) {
									datos = lineaLeida.split(",");
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				// Intentamos resguardar los datos y los insertamos...
				
			}
		});
		btnExaminar.setBounds(322, 40, 100, 25);
		panelCargaMasiva.add(btnExaminar);

		JLabel lblCargaMasiva = new JLabel("CARGA MASIVA");
		lblCargaMasiva.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		lblCargaMasiva.setHorizontalAlignment(SwingConstants.CENTER);
		lblCargaMasiva.setBounds(10, 11, 115, 14);
		panelCargaMasiva.add(lblCargaMasiva);

		txtDirFile = new JTextField();
		txtDirFile.setBounds(10, 40, 300, 25);
		panelCargaMasiva.add(txtDirFile);
		txtDirFile.setColumns(10);

		JLabel lblProblemas = new JLabel("Resultados:");
		lblProblemas.setBounds(10, 80, 89, 14);
		panelCargaMasiva.add(lblProblemas);

		txtProblemas = new JTextPane();
		txtProblemas.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		scrollProblemas = new JScrollPane(txtProblemas);
		scrollProblemas.setBounds(10, 95, 414, 134);
		scrollProblemas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollProblemas.setVisible(true);
		panelCargaMasiva.add(scrollProblemas);
	}
}
