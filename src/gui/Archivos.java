package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import estructuras.Arbol;
import estructuras.Arbol.nodoAVL;
import estructuras.Grafo;
import estructuras.HashTable.nodoHash;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Archivos extends JFrame {
	
	private static final long serialVersionUID = 9180214042948194919L;
	// Vista
	private JPanel contentPane;
	private JPanel panelArchivos;
	private JTextField textField;
	// JPopMenu para archivos y carpetas...
	JPopupMenu archivos; // Este pop menu solamente se activará en las carpetas y archivos...
	JMenuItem modificar;
	JMenuItem eliminar;
	JMenuItem compartir;
	JPopupMenu carpetas;
	
	int alto = 10, ancho = 10;

	// Esta variable me ayudará a saber en que carpeta estoy actualmente
	public static Grafo carpetaActual;
	public static Arbol archivoActual;
	// Variable que me dirá que usuario está logueado...
	public static nodoHash usuarioActual;

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
		// carpetaActual = Linker.sistemaArchivos.getCarpeta();
		// archivoActual = carpetaActual.archivos;
		usuarioActual = Menu_App.nodoUsuario;
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
				Linker.app.frmEddDrive.setVisible(true);
				setVisible(false);
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 460);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelArchivos = new JPanel();
		panelArchivos.setLayout(null);
		panelArchivos.setBounds(10, 29, 684, 391);
		panelArchivos.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelArchivos.setBackground(new Color(255, 255, 255));
		contentPane.add(panelArchivos);
		
		JPopupMenu popInicio = new JPopupMenu();
		popInicio.setBounds(-10068, -10031, 105, 72);
		addPopup(panelArchivos, popInicio);

		JMenu menu = new JMenu("Nuevo");
		popInicio.add(menu);

		JMenuItem itemArchivo = new JMenuItem("Archivo");
		itemArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton nuevoArchivo = new JButton();
				nuevoArchivo.setText(JOptionPane.showInputDialog("Ingrese el nombre del nuevo archivo"));
				nuevoArchivo.setContentAreaFilled(false);
				nuevoArchivo.setOpaque(true);
				nuevoArchivo.setBackground(Color.WHITE);
				nuevoArchivo.setHorizontalAlignment(JButton.LEFT);
				Image archivo = new ImageIcon(getClass().getResource("/imagenes/archivo.png")).getImage();
				nuevoArchivo.setIcon(new ImageIcon(archivo.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
				nuevoArchivo.setBounds(ancho, alto, 100, 30);
				ancho += 100;
				
				if(ancho >= 610) {
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
						if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
							// Abrir contenido de archivo en bloc de notas.
						}
					}
				});
				// Le agrego los menu emergentes
				modificar = new JMenuItem("Modificar");
				eliminar = new JMenuItem("Eliminar");
				compartir = new JMenuItem("Compartir");
				archivos = new JPopupMenu();
				archivos.add(modificar);
				archivos.add(eliminar);
				archivos.add(compartir);
				addPopup(nuevoArchivo, archivos);
				panelArchivos.add(nuevoArchivo);
				panelArchivos.repaint();
			}
		});
		
		menu.add(itemArchivo);

		JMenuItem itemCarpeta = new JMenuItem("Carpeta");
		itemCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*Linker.sistemaArchivos.agregarCarpeta(carpetaActual,
						JOptionPane.showInputDialog("Ingresa el nombre de la Nueva Carpeta"));*/
				// Primero creamos el Label
				JButton nuevoLabel = new JButton();
				nuevoLabel.setText(JOptionPane.showInputDialog("Ingresa el nombre de la nueva Carpeta"));
				String contenido = JOptionPane.showInputDialog("Ingrese el contenido del archivo...\nDejar vacio archivo en blanco.");
				nuevoLabel.setContentAreaFilled(false);
				nuevoLabel.setOpaque(true);
				nuevoLabel.setBackground(Color.WHITE);
				Image carpeta = new ImageIcon(getClass().getResource("/imagenes/carpeta.png")).getImage();
				nuevoLabel.setIcon(new ImageIcon(carpeta.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
				nuevoLabel.setBounds(ancho, alto, 100, 30);
				nuevoLabel.setHorizontalAlignment(JButton.LEFT);
				ancho += 100;
				if(ancho >= 610){
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
						if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
							JButton temp = (JButton) e.getSource();
							System.out.println("Entrando a carpeta llamada " + temp.getText());
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
		});
		menu.add(itemCarpeta);

		JLabel lblDirectorio = new JLabel("Directorio:");
		lblDirectorio.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblDirectorio.setForeground(Color.BLACK);
		lblDirectorio.setBounds(10, 6, 76, 14);
		contentPane.add(lblDirectorio);

		textField = new JTextField("&");
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textField.setEditable(false);
		textField.setBounds(96, 0, 508, 27);
		contentPane.add(textField);

		JLabel lblLeft = new JLabel();
		Image left = new ImageIcon(getClass().getResource("/imagenes/left.png")).getImage();
		lblLeft.setIcon(new ImageIcon(left.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		lblLeft.setBounds(614, 2, 25, 25);
		contentPane.add(lblLeft);

		JLabel lblRight = new JLabel();
		Image right = new ImageIcon(getClass().getResource("/imagenes/right.png")).getImage();
		lblRight.setIcon(new ImageIcon(right.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		lblRight.setBounds(649, 2, 25, 25);
		contentPane.add(lblRight);
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
}
