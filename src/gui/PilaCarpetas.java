package gui;

import estructuras.Grafo;

public class PilaCarpetas {
	/*Esta clase solo me servirá para hacer un recorrido al revés en las carpetas
	 * util cuando quiero regresar a la carpeta padre de un usuario al pulsar la flecha de retorno.*/
	carpetaPadre primero;
	class carpetaPadre{
		Grafo carpeta;
		carpetaPadre sig;
		public carpetaPadre() {
			// TODO Auto-generated constructor stub
			sig = null;
		}
	}
	
	public PilaCarpetas() {
		// TODO Auto-generated constructor stub
		primero = null;
	}
	
	public void guardarPadre(Grafo carpeta) {
		if(primero == null) {
			primero = new carpetaPadre();
			primero.carpeta = carpeta;
		}else {
			carpetaPadre actual = new carpetaPadre();
			actual.carpeta = carpeta;
			
			actual.sig = primero;
			primero = actual;
		}
	}
	
	public Grafo pop() {
		/*Elimino la cabeza*/
		Grafo temp = primero.carpeta;
		primero = primero.sig;
		return temp;
	}
	
	public void EliminarTodo() {
		primero = null;
	}
}
