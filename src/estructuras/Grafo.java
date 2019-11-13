package estructuras;

import javax.swing.JOptionPane;

public class Grafo {
	
	public String nombreCarpeta;
	Grafo primero, ultimo;
	Grafo siguiente;
	public Arbol archivos;
	
	
	public Grafo(String nombreCarpeta) {
		// TODO Auto-generated constructor stub
		archivos = new Arbol();
		// Estas variables me ayudarán para ver si tengo una carpeta vacia o no...
		primero = ultimo = null;
		this.nombreCarpeta = nombreCarpeta;
	}
	
	public Grafo() {
		System.out.println("Instancia de Grafo");
	}
	
	public void agregarCarpeta(Grafo estaCarpeta,String subCarpeta) {
		if(!existeCarpeta(estaCarpeta, subCarpeta)) {
			Grafo nuevaCarpeta = new Grafo(subCarpeta);
			if(estaCarpeta.primero == null) {
				estaCarpeta.primero = estaCarpeta.ultimo = nuevaCarpeta;
			}else {
				// Se irá por medio de Cola...
				estaCarpeta.ultimo.siguiente = nuevaCarpeta;
				estaCarpeta.ultimo = nuevaCarpeta;
			}
		}else {
			JOptionPane.showMessageDialog(null, "Nombre Inválido:\nNo se permite carpetas con el mismo nombre", "Error - Creación de Carpeta", JOptionPane.ERROR_MESSAGE, null);
		}
	}
	
	public boolean existeCarpeta(Grafo estaCarpeta, String nombreProhibido) {
		boolean siExiste = false;
		Grafo temporal = estaCarpeta.primero;
		while(temporal != null) {
			if(temporal.nombreCarpeta.equals(nombreProhibido)) {
				siExiste = true;
				break;
			}
			temporal = temporal.siguiente;
		}
		return siExiste;
	}
	
	public Grafo getCarpeta() {
		return HashTable.carpetaRaiz;
	}
	
	public Grafo getCarpeta(Grafo carpetaPadre, String carpetaHijo) {
		Grafo temporal = carpetaPadre;
		while(temporal != null) {
			if(temporal.nombreCarpeta.equals(carpetaHijo)) {
				return temporal;
			}
		}
		return null;
	}
}
