package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
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

import estructuras.Grafo;
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
	
	int alto = 10;
	int limAlto = 644;

	// Esta variable me ayudará a saber en que carpeta estoy actualmente
	public static Grafo carpetaActual;

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
		//carpetaActual = Linker.sistemaArchivos.getCarpeta();
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
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 460);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelDriveOp = new JPanel();
		panelDriveOp.setBackground(new Color(255, 255, 255));
		panelDriveOp.setBounds(0, 0, 70, 411);
		contentPane.add(panelDriveOp);
		panelDriveOp.setLayout(null);

		JLabel Compartir = new JLabel();
		Compartir.setBounds(10, 11, 50, 50);
		Compartir.setIcon(new ImageIcon(getClass().getResource("/imagenes/share.png")));
		panelDriveOp.add(Compartir);

		JLabel Salir = new JLabel();
		Salir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON1) {
					Linker.app.frmEddDrive.setVisible(true);
					setVisible(false);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				Salir.setToolTipText("Cerrar Sesión");
			}
		});
		Salir.setBounds(10, 350, 50, 50);
		Salir.setIcon(new ImageIcon(getClass().getResource("/imagenes/salir.png")));
		panelDriveOp.add(Salir);
		
		panelArchivos = new JPanel();
		panelArchivos.setBounds(70, 29, 614, 382);
		panelArchivos.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelArchivos.setBackground(new Color(255, 255, 255));
		panelArchivos.setAutoscrolls(true);
		
		JPopupMenu popInicio = new JPopupMenu();
		popInicio.setBounds(-10068, -10031, 105, 72);
		addPopup(panelArchivos, popInicio);

		JMenu menu = new JMenu("Nuevo");
		popInicio.add(menu);

		JMenuItem itemArchivo = new JMenuItem("Archivo");
		menu.add(itemArchivo);

		JMenuItem itemCarpeta = new JMenuItem("Carpeta");
		itemCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*Linker.sistemaArchivos.agregarCarpeta(carpetaActual,
						JOptionPane.showInputDialog("Ingresa el nombre de la Nueva Carpeta"));*/
				// Primero creamos el Label
				JLabel nuevoLabel = new JLabel();
				nuevoLabel.setText("     " + JOptionPane.showInputDialog("Ingresa el nombre de la nueva Carpeta"));
				nuevoLabel.setOpaque(true);
				nuevoLabel.setBackground(Color.WHITE);
				nuevoLabel.setBounds(10, alto, panelArchivos.getWidth() - 20, 30);
				alto += 30;
				if(alto > limAlto) {
					panelArchivos.setPreferredSize(new Dimension(580, limAlto));
					limAlto += 30;
				}
				// Le agrego acciones
				nuevoLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						nuevoLabel.setBackground(new Color(30, 144, 255));
						nuevoLabel.setForeground(Color.WHITE);
						panelArchivos.repaint();
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						nuevoLabel.setBackground(Color.WHITE);
						nuevoLabel.setForeground(Color.BLACK);
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
							JLabel temp = (JLabel) e.getSource();
							System.out.println("Entrando a carpeta llamada " + temp.getText());
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
				addPopup(nuevoLabel, archivos);
				panelArchivos.add(nuevoLabel);
				panelArchivos.repaint();
			}
		});
		menu.add(itemCarpeta);

		JLabel lblDirectorio = new JLabel("Directorio:");
		lblDirectorio.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblDirectorio.setForeground(Color.BLACK);
		lblDirectorio.setBounds(80, 7, 90, 14);
		contentPane.add(lblDirectorio);

		textField = new JTextField("&");
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textField.setEditable(false);
		textField.setBounds(159, 0, 445, 27);
		contentPane.add(textField);

		JLabel lblLeft = new JLabel();
		Image left = new ImageIcon(getClass().getResource("/imagenes/left.png")).getImage();
		lblLeft.setIcon(new ImageIcon(left.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		lblLeft.setBounds(614, 2, 25, 25);
		contentPane.add(lblLeft);

		JLabel lblRight = new JLabel("");
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
