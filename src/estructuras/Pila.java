package estructuras;

public class Pila {
	// Esta pila servirá para el guardado de las distintas actividades
	// que se realizan en el transcurso del uso del programa...
	static nodoPila primero = null;
	
	public class nodoPila{
		String hora;
		String fecha;
		String operacion;
		String usuario;
		nodoPila sig;
		
		public nodoPila(String hora, String fecha, String operacion, String usuario) {
			// TODO Auto-generated constructor stub
			this.hora = hora;
			this.fecha = fecha;
			this.operacion = operacion;
			this.usuario = usuario;
			sig = null;
		}
	}
	
	// Funciones para aplicar en el nodoPila
	public void reg_bitacora(String hora, String fecha, String operacion, String usuario) {
		nodoPila cambio = new nodoPila(hora, fecha, operacion, usuario);
		if(primero == null) {
			// La pila comenzará a registrar cambios...
			primero = cambio;
		}else {
			// Ahora el nodo cambio será el primero...
			cambio.sig = primero;
			primero = cambio;
		}
	}
	
	public void graficar() {
		// Guardaré la imagen en una carpeta, y jalaré esa carpeta desde un getClass().getResource("carpeta/graficoPila.png");
		// para poder almacenarlo y mostrarlo como imagen dentro de la aplicación.
	}
}
