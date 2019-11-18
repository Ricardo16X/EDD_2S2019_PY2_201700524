package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import estructuras.Arbol;
import estructuras.Arbol.nodoAVL;
import estructuras.Grafo;
import estructuras.HashTable.nodoHash;
import metodos.Hash;
import paqueteInicio.PPAL;

public class Archivos extends JFrame {

	private static final long serialVersionUID = 9180214042948194919L;
	// Vista
	private JPanel contentPane;
	private JPanel panelArchivos;
	private JTextField textField;
	// JPopMenu para archivos y carpetas...
	JPopupMenu archivos; // Este pop menu solamente se activará en las carpetas y archivos...
	JMenuItem modificar;
	JMenuItem cambiarNombre;
	JMenuItem eliminar;
	JMenuItem compartir;
	JMenuItem descargar;
	JPopupMenu carpetas;

	int alto = 10, ancho = 10;

	// Esta variable me ayudará a saber en que carpeta estoy actualmente
	public static Grafo carpetaActual;
	public static Grafo carpetaPadre;
	public static Arbol archivoActual;
	// Variable que me dirá que usuario está logueado...
	public nodoHash usuarioActual;
	private BufferedReader lectorArchivo;
	private nodoAVL accionesArchivo;

	private static String dot;
	/* Visualización de Reportes */
	private JPanel panelReportes;
	private JLabel imagenReporte = new JLabel();
	private JScrollPane reporte = new JScrollPane();
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Archivos archivos = new Archivos();
					archivos.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Archivos() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				panelArchivos.removeAll();
				panelArchivos.setVisible(false);
				usuarioActual = PPAL.usuarioRegistrado;
				carpetaActual = usuarioActual.carpetaRaiz;
				archivoActual = carpetaActual.archivos;
				textField.setText(carpetaActual.nombreCarpeta);
				// Método para actualizar todas las carpetas y archivos de la raiz del usuario
				// registrado...
				alto = 10;
				ancho = 10;
				actualizar(carpetaActual);
				actualizar(archivoActual.raiz);
				panelArchivos.repaint();
				panelArchivos.setVisible(true);
			}
		});
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		setTitle("Archivos - EDD Drive");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PPAL.carpetas.graphic(usuarioActual.carpetaRaiz);
				carpetaActual = null;
				usuarioActual = null;
				PPAL.gestorCarpetas.EliminarTodo();
				Menu_App.frmEddDrive.setVisible(true);
				setVisible(false);
			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 500);
		setResizable(false);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAcciones = new JMenu("Acciones");
		menuBar.add(mnAcciones);

		JMenuItem mntmArchivos = new JMenuItem("Archivos");
		mntmArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelReportes.setVisible(false);
				panelArchivos.setVisible(true);
			}
		});
		mnAcciones.add(mntmArchivos);

		JMenu mnReportes = new JMenu("Reportes");
		mnAcciones.add(mnReportes);

		JMenuItem mntmArchivosarbolAvl = new JMenuItem("Archivos (Arbol AVL)");
		mntmArchivosarbolAvl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelArchivos.setVisible(false);
				graphic(carpetaActual.archivos.raiz);
				try {
					Thread.sleep(500);
					imagenReporte.setIcon(new ImageIcon(ImageIO.read(new File("archivosAVL.jpg"))));
					reporte.setLocation(0, 0);
					reporte.setSize(panelReportes.getWidth(), panelReportes.getHeight());
					panelReportes.add(reporte);
					reporte.setViewportView(imagenReporte);
					panelReportes.setVisible(true);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mnReportes.add(mntmArchivosarbolAvl);

		JMenuItem mntmCerrarSesin = new JMenuItem("Cerrar Sesi\u00F3n");
		mntmCerrarSesin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PPAL.carpetas.graphic(usuarioActual.carpetaRaiz);
				setVisible(false);
				Menu_App.frmEddDrive.setVisible(true);
			}
		});
		mnAcciones.add(mntmCerrarSesin);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDirectorio = new JLabel("Directorio:");
		lblDirectorio.setBounds(10, 17, 76, 14);
		contentPane.add(lblDirectorio);
		lblDirectorio.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblDirectorio.setForeground(Color.BLACK);

		textField = new JTextField("");
		textField.setBounds(96, 11, 508, 27);
		contentPane.add(textField);
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textField.setEditable(false);

		panelArchivos = new JPanel();
		panelArchivos.setLayout(null);
		panelArchivos.setBounds(10, 49, 684, 391);
		panelArchivos.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelArchivos.setBackground(new Color(255, 255, 255));
		contentPane.add(panelArchivos);

		JPopupMenu popInicio = new JPopupMenu();
		popInicio.setBounds(-10068, -10031, 105, 72);
		addPopup(panelArchivos, popInicio);

		JMenu menu = new JMenu("Nuevo");
		popInicio.add(menu);

		JMenuItem cargaArchivos = new JMenuItem("Carga Archivos");
		cargaArchivos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser cargaMasiva = new JFileChooser();
				File fl = null;
				String[] datos;
				String lineaLeida;
				String mensajeNegativo = "";

				cargaMasiva.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileFilter filtro = new FileNameExtensionFilter("Archivos CSV", "csv");
				cargaMasiva.setFileFilter(filtro);
				if (cargaMasiva.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						fl = cargaMasiva.getSelectedFile();
					} catch (Exception e) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Error al leer el archivo .csv", "Error de Lectura",
								JOptionPane.ERROR_MESSAGE);
					}
					if (fl.canRead()) {
						try {
							lectorArchivo = new BufferedReader(
									new InputStreamReader(new FileInputStream(fl.getAbsolutePath()), "utf-8"));
							lineaLeida = lectorArchivo.readLine();
							lineaLeida = lectorArchivo.readLine();
							while (lineaLeida != null) {
								datos = lineaLeida.split(",");
								/*
								 * datos[0] = nombre Archivo datos[1] = contenido archivo
								 */
								/* Proceso de Inserción */
								if (archivoActual.raiz == null) {
									carpetaActual.archivos.raiz = PPAL.archivos.crearArchivo(archivoActual.raiz,
											datos[0], datos[1], Hash.timestamp(), usuarioActual.nom);
								} else {
									if (!PPAL.archivos.existencia(carpetaActual.archivos.raiz, datos[0])) {
										carpetaActual.archivos.raiz = PPAL.archivos.crearArchivo(archivoActual.raiz,
												datos[0], datos[1], Hash.timestamp(), usuarioActual.nom);
									} else {
										mensajeNegativo += "FALLO CON ARCHIVO: " + datos[0] + " nombre repetido.\n";
									}
								}
								/* Fin Inserción */
								lineaLeida = lectorArchivo.readLine();
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					if (mensajeNegativo.length() > 1) {
						JOptionPane.showMessageDialog(null, mensajeNegativo);
					}
					panelArchivos.removeAll();
					panelArchivos.setVisible(false);
					actualizar(carpetaActual.archivos.raiz);
					actualizar(carpetaActual);
					panelArchivos.setVisible(true);
					PPAL.bitacora.reg_bitacora(Hash.timestamp(), "Carga Masiva de Archivos", usuarioActual.nom);
				}
			}
		});
		popInicio.add(cargaArchivos);

		JMenuItem itemArchivo = new JMenuItem("Archivo");
		itemArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String nom = "";
					String cont = "";
					while (nom.length() == 0 || cont.length() == 0) {
						System.out.println(nom.length());
						System.out.println(cont.length());
						if (nom.length() == 0) {
							nom = JOptionPane.showInputDialog("Ingrese el nombre del Archivo");
						} else if (cont.length() == 0) {
							cont = JOptionPane.showInputDialog("Ingrese el contenido del Archivo");
						}
					}

					if (archivoActual.raiz == null) {
						archivoActual.raiz = PPAL.archivos.crearArchivo(archivoActual.raiz, nom, cont, Hash.timestamp(),
								usuarioActual.nom);
						cargaArchivo(nom);
						PPAL.bitacora.reg_bitacora(Hash.timestamp(), "Creación de Archivo", usuarioActual.nom);
					} else {
						if (!PPAL.archivos.existencia(archivoActual.raiz, nom)) {
							archivoActual.raiz = PPAL.archivos.crearArchivo(archivoActual.raiz, nom, cont,
									Hash.timestamp(), usuarioActual.nom);
							cargaArchivo(nom);
							PPAL.bitacora.reg_bitacora(Hash.timestamp(), "Creación de Archivo", usuarioActual.nom);
						} else {
							JOptionPane.showMessageDialog(null,
									"Fallo al crear Archivo: Archivo con mismo nombre no permitido.");
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
			}
		});

		menu.add(itemArchivo);

		JMenuItem itemCarpeta = new JMenuItem("Carpeta");
		itemCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String nom = "";
					while (nom.length() == 0) {
						nom = JOptionPane.showInputDialog("Ingrese el nombre de la Carpeta");
					}
					if (!PPAL.carpetas.existeCarpeta(carpetaActual, nom)) {
						cargaCarpeta(nom);
						PPAL.carpetas.agregarCarpeta(carpetaActual, nom);
						PPAL.bitacora.reg_bitacora(Hash.timestamp(), "Creación de Carpeta", usuarioActual.nom);
					} else {
						JOptionPane.showMessageDialog(null, "ERROR: Carpeta ya existente");
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
			}
		});
		menu.add(itemCarpeta);

		panelReportes = new JPanel();
		panelReportes.setBounds(10, 49, 684, 391);
		contentPane.add(panelReportes);
		panelReportes.setLayout(null);
		
		JLabel lblLeftdir = new JLabel();
		lblLeftdir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(carpetaActual != usuarioActual.carpetaRaiz) {
					carpetaActual = PPAL.gestorCarpetas.pop();
					archivoActual = carpetaActual.archivos;
					/*Actualización de Carpetas y Vista de Archivos xdxd*/
					panelArchivos.removeAll();
					panelArchivos.setVisible(false);
					alto = 10; ancho = 10;
					actualizar(carpetaActual.archivos.raiz);
					actualizar(carpetaActual);
					panelArchivos.setVisible(true);
					/*Fin de Actualización*/
				}
			}
		});
		Image ldir = new ImageIcon(getClass().getResource("/imagenes/left.png")).getImage();
		lblLeftdir.setIcon(new ImageIcon(ldir.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		lblLeftdir.setBounds(614, 11, 25, 25);
		lblLeftdir.setVisible(true);
		contentPane.add(lblLeftdir);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/* Gráfico de Árbol AVL */
	public void graphic(nodoAVL raizArbol) {
		dot = "digraph G{\n";
		int id_ = 1;
		System.out.println(dot);
		aux_graphic(raizArbol, id_);
		dot += "}";
		System.out.println(dot);
		try {
			BufferedWriter escritor = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("arbolAVL.dot"), "utf-8"));
			escritor.write(dot);
			escritor.close();
			Runtime.getRuntime().exec("dot -Tjpg arbolAVL.dot -o archivosAVL.jpg");
			Thread.sleep(500);
			System.out.println("He finalizado el proceso");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public void aux_graphic(nodoAVL raizArbol, int id) {
		if (raizArbol != null) {
			dot += "\"" + raizArbol.NombreArchivo + "\" " + "[label = \" " + "*Nombre: " + raizArbol.NombreArchivo
					+ "\\n*Contenido: " + raizArbol.Contenido + "\\n*FE: " + String.valueOf(raizArbol.FactorEquilibrio)
					+ "\\n*Altura: " + String.valueOf(raizArbol.Altura - 1) + "\\n*TimeStamp: " + raizArbol.Timestamp
					+ "\\n*Propietario: " + raizArbol.Propietario + " \"];\n";
			id += 1;
			aux_graphic(raizArbol.l, id);
			aux_graphic(raizArbol.r, id);

			if (raizArbol.l != null) {
				dot += "\"" + raizArbol.NombreArchivo + "\" " + " -> \"" + raizArbol.l.NombreArchivo + "\";\n";
			}
			if (raizArbol.r != null) {
				dot += "\"" + raizArbol.NombreArchivo + "\" -> \"" + raizArbol.r.NombreArchivo + "\";\n";
			}
		}
	}
	/* Fin Gráfico de Árbol AVL */

	public void cargaArchivo(String nombreArchivo) {
		/*
		 * Antes de agregarlo a la maya de archivos primero deberé de cargar ese archivo
		 * al arbol avl, verificando que no exista y luego mostrarlos.
		 */
		JButton nuevoArchivo = new JButton();
		nuevoArchivo.setText(nombreArchivo);
		nuevoArchivo.setContentAreaFilled(false);
		nuevoArchivo.setOpaque(true);
		nuevoArchivo.setBackground(Color.WHITE);
		nuevoArchivo.setHorizontalAlignment(JButton.LEFT);
		Image archivo = new ImageIcon(getClass().getResource("/imagenes/archivo.png")).getImage();
		nuevoArchivo.setIcon(new ImageIcon(archivo.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		nuevoArchivo.setBounds(ancho, alto, 100, 30);
		ancho += 100;

		if (ancho >= 610) {
			alto += 30;
			ancho = 10;
		}
		// Acciones
		nuevoArchivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				nuevoArchivo.setToolTipText(nuevoArchivo.getText());
				nuevoArchivo.setBackground(new Color(30, 144, 255));
				nuevoArchivo.setForeground(Color.white);
				panelArchivos.repaint();
				nuevoArchivo.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nuevoArchivo.setBackground(Color.WHITE);
				nuevoArchivo.setForeground(Color.BLACK);
				nuevoArchivo.repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					// Abrir contenido de archivo en bloc de notas.
					JOptionPane.showMessageDialog(null, "CONTENIDO: \n" + PPAL.archivos.getArchivo(archivoActual.raiz,
							((JButton) e.getSource()).getText()).Contenido);
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					accionesArchivo = PPAL.archivos.getArchivo(archivoActual.raiz,
							((JButton) (e.getSource())).getText());
					System.out.println(accionesArchivo.NombreArchivo);
				}
			}
		});
		// Le agrego los menu emergentes
		modificar = new JMenuItem("Modificar"); // Modificar Terminado xdxd
		modificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nuevoContenido = "";
				int respuesta = JOptionPane.showConfirmDialog(null, "Desea Continuar?", "Modificar Contenido",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (respuesta == JOptionPane.YES_OPTION) {
					while (true) {
						nuevoContenido = JOptionPane.showInputDialog("Contenido Anterior: " + accionesArchivo.Contenido
								+ "\n" + "Ingrese el nuevo contenido del archivo.");
						if (nuevoContenido.length() > 0) {
							accionesArchivo.Contenido = nuevoContenido;
							JOptionPane.showMessageDialog(null, "Cambio Realizado Satisfactoriamente.");
							PPAL.bitacora.reg_bitacora(Hash.timestamp(),
									"Archivo " + accionesArchivo.NombreArchivo + " Modificado", usuarioActual.nom);
							break;
						}
					}
				}
			}
		});

		eliminar = new JMenuItem("Eliminar"); // Eliminar Terminado
		eliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int respuesta = JOptionPane.showConfirmDialog(null, "Desea Continuar?", "Eliminar Archivo",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (respuesta == JOptionPane.YES_OPTION) {
					try {
						archivoActual.raiz = PPAL.archivos.borrarArchivo(archivoActual.raiz,
								accionesArchivo.NombreArchivo);
						PPAL.bitacora.reg_bitacora(Hash.timestamp(), "Borrado de Archivo", usuarioActual.nom);
						JOptionPane.showMessageDialog(null, "Archivo Borrado Satisfactoriamente.");

						/* Actualización de Ventana para que se vea el borrado del archivo */
						panelArchivos.removeAll();
						panelArchivos.setVisible(false);
						actualizar(archivoActual.raiz);
						actualizar(carpetaActual);
						panelArchivos.setVisible(true);
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
				}
			}
		});

		compartir = new JMenuItem("Compartir"); // Compartir Terminado
		compartir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int respuesta = JOptionPane.showConfirmDialog(null, "Desea Compartir el Archivo?", "Compartir",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (respuesta == JOptionPane.YES_OPTION) {
					String usuario = JOptionPane.showInputDialog("Ingrese el nombre del usuario a compartir...");
					try {
						nodoHash usuarioFinal = Menu_App.usuarios.getUsuario(usuario);
						if (usuarioFinal != null) {
							if (usuarioFinal.carpetaRaiz.archivos.raiz == null) {
								usuarioFinal.carpetaRaiz.archivos.raiz = PPAL.archivos.crearArchivo(
										usuarioFinal.carpetaRaiz.archivos.raiz, accionesArchivo.NombreArchivo,
										accionesArchivo.Contenido, Hash.timestamp(), usuarioActual.nom);
								PPAL.bitacora.reg_bitacora(Hash.timestamp(), "Se ha compartido un archivo a " + usuario,
										usuarioActual.nom);
								JOptionPane.showMessageDialog(null, "Se ha compartido el archivo exitosamente...");
							} else {
								if (!PPAL.archivos.existencia(usuarioFinal.carpetaRaiz.archivos.raiz,
										accionesArchivo.NombreArchivo)) {
									PPAL.archivos.crearArchivo(usuarioFinal.carpetaRaiz.archivos.raiz,
											accionesArchivo.NombreArchivo, accionesArchivo.Contenido, Hash.timestamp(),
											usuarioActual.nom);
									PPAL.bitacora.reg_bitacora(Hash.timestamp(),
											"Se ha compartido un archivo a " + usuario, usuarioActual.nom);
								} else {
									JOptionPane.showMessageDialog(null,
											"El Usuario contiene un archivo con el mismo nombre en la carpeta raiz...");
								}
							}

						} else {
							JOptionPane.showMessageDialog(null,
									"No se ha encontrado ningún usuario con este nombre...");
						}
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
				}
			}
		});

		cambiarNombre = new JMenuItem("Cambiar Nombre");	
		cambiarNombre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int resultado = JOptionPane.showConfirmDialog(null, "Desea Cambiar Nombre?", "Cambio de Nombre",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (resultado == JOptionPane.YES_OPTION) {
					String nuevoNombre = "";
					/*
					 * Commo primer paso eliminaré el archivo del arbol
					 * 
					 * Pediré el nombre nuevo
					 * 
					 * y lugo lo insertaré con los datos que he recopilado anteriormente...
					 */
					try {
						archivoActual.raiz = PPAL.archivos.borrarArchivo(archivoActual.raiz,
								accionesArchivo.NombreArchivo); // Eliminación
						while (true) {
							nuevoNombre = JOptionPane.showInputDialog("Ingresa el nuevo nombre del archivo: ");
							if (archivoActual.raiz == null) {
								archivoActual.raiz = PPAL.archivos.crearArchivo(archivoActual.raiz, nuevoNombre,
										accionesArchivo.Contenido, Hash.timestamp(), usuarioActual.nom);
								break;
							} else {
								if (!(PPAL.archivos.existencia(archivoActual.raiz, nuevoNombre))) {
									archivoActual.raiz = PPAL.archivos.crearArchivo(archivoActual.raiz, nuevoNombre,
											accionesArchivo.Contenido, Hash.timestamp(), usuarioActual.nom);
									PPAL.bitacora.reg_bitacora(Hash.timestamp(),
											"Cambio de Nombre de " + accionesArchivo.NombreArchivo + " -> " + nuevoNombre,
											usuarioActual.nom);
									break;
								}
							}
						}
						JOptionPane.showMessageDialog(null, "Los cambios se verán al iniciar sesión nuevamente!");
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
		});

		descargar = new JMenuItem("Descargar Archivo");		// Descarga de Archivos Terminado
		descargar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				File carpetaAlmacenadora = new File("Carpeta Descargas");
				if(!carpetaAlmacenadora.exists()) {
					carpetaAlmacenadora.mkdir();
				}
				try {
					BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Carpeta Descargas/" + accionesArchivo.NombreArchivo),"utf-8"));
					escritor.write(accionesArchivo.Contenido);
					escritor.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		archivos = new JPopupMenu();
		
		archivos.add(descargar);
		archivos.add(modificar);
		archivos.add(eliminar);
		archivos.add(compartir);
		archivos.add(cambiarNombre);

		addPopup(nuevoArchivo, archivos);
		panelArchivos.add(nuevoArchivo);
		panelArchivos.repaint();
	}

	public void cargaCarpeta(String nom) {
		// Primero creamos el Label
		JButton nuevoLabel = new JButton();
		nuevoLabel.setText(nom);
		nuevoLabel.setContentAreaFilled(false);
		nuevoLabel.setOpaque(true);
		nuevoLabel.setBackground(Color.WHITE);
		Image carpeta = new ImageIcon(getClass().getResource("/imagenes/carpeta.png")).getImage();
		nuevoLabel.setIcon(new ImageIcon(carpeta.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		nuevoLabel.setBounds(ancho, alto, 100, 30);
		nuevoLabel.setHorizontalAlignment(JButton.LEFT);
		ancho += 100;
		if (ancho >= 610) {
			alto += 30;
			ancho = 10;
		}
		// Le agrego acciones
		nuevoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				nuevoLabel.setToolTipText(nuevoLabel.getText());
				nuevoLabel.setBackground(new Color(30, 144, 255));
				nuevoLabel.setForeground(Color.white);
				panelArchivos.repaint();
				nuevoLabel.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nuevoLabel.setBackground(Color.WHITE);
				nuevoLabel.setForeground(Color.BLACK);
				nuevoLabel.repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					JButton temp = (JButton) e.getSource();
					System.out.println("Entrando a carpeta llamada " + temp.getText());
					PPAL.gestorCarpetas.guardarPadre(carpetaActual);
					carpetaActual = PPAL.carpetas.getCarpeta(carpetaActual, temp.getText());
					archivoActual = carpetaActual.archivos;
					/*Actualización de Vista de Carpetas*/
					panelArchivos.removeAll();
					panelArchivos.setVisible(false);
					alto = 10; ancho = 10;
					actualizar(carpetaActual.archivos.raiz);
					actualizar(carpetaActual);
					panelArchivos.setVisible(true);
				}
			}
		});
		// Le agrego los menu emergentes
		modificar = new JMenuItem("Modificar");
		eliminar = new JMenuItem("Eliminar");
		carpetas = new JPopupMenu();
		carpetas.add(modificar);
		carpetas.add(eliminar);
		addPopup(nuevoLabel, carpetas);
		panelArchivos.add(nuevoLabel);
		panelArchivos.repaint();
	}

	private void actualizar(Grafo carpetaPadre) {
		Grafo temp = carpetaPadre.primero;
		while (temp != null) {
			cargaCarpeta(temp.nombreCarpeta);
			temp = temp.siguiente;
		}
	}

	private void actualizar(nodoAVL archivosCarpeta) {
		if (archivosCarpeta != null) {
			actualizar(archivosCarpeta.l);
			cargaArchivo(archivosCarpeta.NombreArchivo);
			actualizar(archivosCarpeta.r);
		}
	}
}
