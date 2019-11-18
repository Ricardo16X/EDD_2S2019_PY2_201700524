package estructuras;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class Pila {
	// Esta pila servirá para el guardado de las distintas actividades
	// que se realizan en el transcurso del uso del programa...
	static nodoPila primero = null;

	public class nodoPila {
		String timestamp;
		String operacion;
		String usuario;
		nodoPila sig;

		public nodoPila(String timeStamp, String operacion, String usuario) {
			// TODO Auto-generated constructor stub
			timestamp = timeStamp;
			this.operacion = operacion;
			this.usuario = usuario;
			sig = null;
		}
	}

	// Funciones para aplicar en el nodoPila
	public void reg_bitacora(String timestamp, String operacion, String usuario) {
		nodoPila cambio = new nodoPila(timestamp, operacion, usuario);
		if (primero == null) {
			// La pila comenzará a registrar cambios...
			primero = cambio;
		} else {
			// Ahora el nodo cambio será el primero...
			cambio.sig = primero;
			primero = cambio;
		}
	}

	public void graficar() {
		// Guardaré la imagen en una carpeta, y jalaré esa carpeta desde un
		// getClass().getResource("carpeta/graficoPila.png");
		// para poder almacenarlo y mostrarlo como imagen dentro de la aplicación.
		String dot = "digraph G{\n";
		dot += "rankdir = \"TB\"\n";
		dot += "node [shape = record];\n";
		dot += "n1 [shape=record, label=\"{";

		nodoPila temp = primero;
		while (temp != null) {
			dot += temp.operacion + "___" + temp.timestamp + "___" + temp.usuario + "|";
			temp = temp.sig;
		}
		dot = dot.substring(0, dot.length() - 1);
		dot += "}\"];\n" + "}";
		try {
			BufferedWriter br = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("bitacora.dot"), "utf-8"));
			br.write(dot);
			br.close();
			Runtime.getRuntime().exec("dot -Tjpg bitacora.dot -o cambios.jpg");
			Thread.sleep(500);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
