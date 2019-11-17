package paqueteInicio;

import estructuras.*;
import estructuras.HashTable.nodoHash;
import gui.*;

public class PPAL {

	public static Menu_App pantallaInicio;
	public static Admin administrador;
	public static Archivos sistemaArchivos;

	public static nodoHash usuarioRegistrado;
	public static Arbol archivos;
	public static Grafo carpetas;
	public static Pila bitacora;
	
	public static void main(String[] args) {
		/*Inicialización de Estructuras de Datos*/
		archivos = new Arbol();
		carpetas = new Grafo();
		bitacora = new Pila();
		// GUI
		sistemaArchivos = new Archivos();
		administrador = new Admin();
		/*Fin inicialización*/
		
		pantallaInicio = new Menu_App();
		pantallaInicio.frmEddDrive.setVisible(true);
	}

}
