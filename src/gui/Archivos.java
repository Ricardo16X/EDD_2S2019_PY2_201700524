package gui;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import estructuras.Grafo;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JList;

public class Archivos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9180214042948194919L;
	private JPanel contentPane;
	private JPanel graficos;
	private JPanel panelArchivos;
	private JTextField textField;
	// Estas variables me ayudarán a recalcular el nuevo espacio donde se quedará la nueva carpeta o archivo
	// y asi posicionarlo de manera correcta...
	int x = 0,y = 0;
	// JPopMenu para archivos y carpetas...
	JPopupMenu archivos;	// Este pop menu solamente se activará en las carpetas y archivos...
	
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
		carpetaActual = Linker.sistemaArchivos.getCarpeta();
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
				if(arg0.getButton() == MouseEvent.BUTTON1) {
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
		panelArchivos.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelArchivos.setBackground(new Color(255, 255, 255));
		panelArchivos.setBounds(70, 29, 614, 382);
		contentPane.add(panelArchivos);
		panelArchivos.setLayout(null);

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBounds(-10068, -10031, 105, 72);
		addPopup(panelArchivos, popupMenu);

		JMenu menu = new JMenu("Nuevo");
		popupMenu.add(menu);

		JMenuItem itemArchivo = new JMenuItem("Archivo");
		menu.add(itemArchivo);

		JMenuItem itemCarpeta = new JMenuItem("Carpeta");
		itemCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Linker.sistemaArchivos.agregarCarpeta(carpetaActual, JOptionPane.showInputDialog("Ingresa el nombre de la Nueva Carpeta"));
				
			}
		});
		menu.add(itemCarpeta);
		
		JButton btnCarpeta = new JButton("Carpeta");
		btnCarpeta.setIcon(new ImageIcon(Archivos.class.getResource("/imagenes/share.png")));
		btnCarpeta.setBounds(10, 11, 150, 65);
		panelArchivos.add(btnCarpeta);
		
		JList list = new JList();
		list.setBounds(156, 174, 163, 50);
		panelArchivos.add(list);

		JLabel lblDirectorio = new JLabel("Directorio:");
		lblDirectorio.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblDirectorio.setForeground(Color.BLACK);
		lblDirectorio.setBounds(80, 7, 90, 14);
		contentPane.add(lblDirectorio);

		textField = new JTextField(carpetaActual.nombreCarpeta);
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textField.setEditable(false);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
		});
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
