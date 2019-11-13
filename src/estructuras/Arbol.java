package estructuras;

public class Arbol {

	public nodoAVL raiz;

	public Arbol() {
		// TODO Auto-generated constructor stub
		raiz = null;
	}

	public class nodoAVL {
		public String NombreArchivo;
		public String Contenido;
		public int FactorEquilibrio;
		public int Altura;
		public String Timestamp;
		public String Propietario;

		public nodoAVL l, r;

		public nodoAVL(String nom, String cont, String timestamp, String nomUser) {
			NombreArchivo = nom;
			Contenido = cont;
			FactorEquilibrio = 1;
			Altura = 1;
			Timestamp = timestamp;
			Propietario = nomUser;
			l = r = null;
		}
	}

	// Calculo de Altura
	int Altura(nodoAVL nodo) {
		if (nodo == null) {
			return 0;
		}
		return nodo.Altura;
	}

	// Calculo de Número Mayor para Alturas y/o Diferencias de Alturas
	int MAX(int a, int b) {
		return (a > b) ? a : b;
	}

	// Métodos de Rotación
	nodoAVL rotacionDerecha(nodoAVL nodoCambio) {
		nodoAVL X = nodoCambio.l;
		nodoAVL T2 = X.r;

		// Rotación
		X.r = nodoCambio;
		nodoCambio.l = T2;

		// Actualización de Alturas
		nodoCambio.Altura = MAX(Altura(nodoCambio.l), Altura(nodoCambio.r)) + 1;
		X.Altura = MAX(Altura(X.l), Altura(X.r)) + 1;

		// Retornar la nueva "raiz" del arbol
		return X;
	}

	nodoAVL rotacionIzquierda(nodoAVL nodoCambio) {
		nodoAVL X = nodoCambio.r;
		nodoAVL T2 = X.l;

		// Rotación
		X.l = nodoCambio;
		nodoCambio.r = T2;

		// Actualización de Alturas
		nodoCambio.Altura = MAX(Altura(nodoCambio.l), Altura(nodoCambio.r)) + 1;
		X.Altura = MAX(Altura(X.l), Altura(X.r)) + 1;

		// Retornar la nueva "raiz" del arbol
		return X;
	}

	// Recalculo de Factor de Equilibrio
	int FE(nodoAVL nodo) {
		if (nodo == null) {
			return 0;
		}
		return Altura(nodo.l) - Altura(nodo.r);
	}

	// Insertar archivo a carpeta;
	public nodoAVL crearArchivo(nodoAVL raiz, String nombreArchivo, String contenido, String Timestamp,
			String nombreUsuario) {
		nodoAVL archivo = new nodoAVL(nombreArchivo, contenido,Timestamp, nombreUsuario);
		if (raiz == null)
			return (new nodoAVL(nombreArchivo, contenido, Timestamp, nombreUsuario));
		if (nombreArchivo.compareTo(raiz.NombreArchivo) < 1) {
			raiz.l = crearArchivo(raiz.l, nombreArchivo, contenido, Timestamp, nombreUsuario);
		}else if(nombreArchivo.compareTo(raiz.NombreArchivo) > 1) {
			raiz.r = crearArchivo(raiz.r, nombreArchivo, contenido, Timestamp, nombreUsuario);
		}else {
			return raiz;
		}
		
		// Actualización de Alturas
		raiz.Altura = 1 + MAX(Altura(raiz.l), Altura(raiz.r));
		
		int balance = FE(raiz);
		
		// Casos de Re Balanceo
		if(balance > 1 && nombreArchivo.compareTo(raiz.l.NombreArchivo) < 1) {
			return rotacionDerecha(raiz);
		}else if(balance < -1 && nombreArchivo.compareTo(raiz.r.NombreArchivo) > 1) {
			return rotacionIzquierda(raiz);
		}else if(balance > 1 && nombreArchivo.compareTo(raiz.l.NombreArchivo) > 1) {
			raiz.l = rotacionIzquierda(raiz.l);
			return rotacionDerecha(raiz);
		}else if(balance < -1 && nombreArchivo.compareTo(raiz.r.NombreArchivo) < 1) {
			raiz.r = rotacionDerecha(raiz.r);
			return rotacionIzquierda(raiz);
		}
		
		return raiz;
	}
}
