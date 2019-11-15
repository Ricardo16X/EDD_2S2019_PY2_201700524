package gui;
import estructuras.*;

public class Linker {
	protected static Pila bitacora = new Pila();
	protected static Arbol archivos = new Arbol();
	protected static Grafo sistemaArchivos = new Grafo();
	protected static Admin admin = new Admin();
	protected static Menu_App app = new Menu_App();
	protected static Archivos file = new Archivos();
	
	public Linker(){
		file.setVisible(false);
	}
}
