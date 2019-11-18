package estructuras;

public class Arbol {

	public nodoAVL raiz;
	nodoAVL obtenerArchivo;
	private boolean existencia;

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
			FactorEquilibrio = 0;
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
		nodoCambio.FactorEquilibrio = FE(nodoCambio);
		X.FactorEquilibrio = FE(X);
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
		nodoCambio.FactorEquilibrio = FE(nodoCambio);
		X.FactorEquilibrio = FE(X);
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
		if (raiz == null)
			return (new nodoAVL(nombreArchivo, contenido, Timestamp, nombreUsuario));
		if (nombreArchivo.compareTo(raiz.NombreArchivo) < 0) {
			raiz.l = crearArchivo(raiz.l, nombreArchivo, contenido, Timestamp, nombreUsuario);
		} else if (nombreArchivo.compareTo(raiz.NombreArchivo) > 0) {
			raiz.r = crearArchivo(raiz.r, nombreArchivo, contenido, Timestamp, nombreUsuario);
		} else {
			return raiz;
		}

		// Actualización de Alturas
		raiz.Altura = 1 + MAX(Altura(raiz.l), Altura(raiz.r));

		int balance = FE(raiz);

		// Casos de Re Balanceo
		if (balance > 1 && nombreArchivo.compareTo(raiz.l.NombreArchivo) < 0) {
			return rotacionDerecha(raiz);
		} else if (balance < -1 && nombreArchivo.compareTo(raiz.r.NombreArchivo) > 0) {
			return rotacionIzquierda(raiz);
		} else if (balance > 1 && nombreArchivo.compareTo(raiz.l.NombreArchivo) > 0) {
			raiz.l = rotacionIzquierda(raiz.l);
			return rotacionDerecha(raiz);
		} else if (balance < -1 && nombreArchivo.compareTo(raiz.r.NombreArchivo) < 0) {
			raiz.r = rotacionDerecha(raiz.r);
			return rotacionIzquierda(raiz);
		}

		return raiz;
	}

	public nodoAVL borrarArchivo(nodoAVL raiz, String nombreArchivo) {
		if (raiz == null)
			return raiz;
		if (nombreArchivo.compareTo(raiz.NombreArchivo) < 0)
			raiz.l = borrarArchivo(raiz.l, nombreArchivo);
		else if (nombreArchivo.compareTo(raiz.NombreArchivo) > 0)
			raiz.r = borrarArchivo(raiz.r, nombreArchivo);
		else {
			// Nodos con un solo hijo o vacios (hojas)
			if ((raiz.l == null) || (raiz.r == null)) {
				nodoAVL temp = null;
				if (temp == raiz.l)
					temp = raiz.r;
				else
					temp = raiz.l;

				// Caso de ningún hijo
				if (temp == null) {
					temp = raiz;
					raiz = null;
				} else {
					raiz = temp;
				}
			} else {
				nodoAVL temp = minimo(raiz.r);
				raiz.NombreArchivo = temp.NombreArchivo;
				raiz.Contenido = temp.Contenido;
				raiz.Propietario = temp.Propietario;
				raiz.Timestamp = temp.Timestamp;
				raiz.r = borrarArchivo(raiz.r, temp.NombreArchivo);
			}
		}

		if (raiz == null)
			return raiz;
		
		// Ahora se actuaizará la altura del nodo actual
		raiz.Altura = MAX(Altura(raiz.l), Altura(raiz.r)) + 1;
		// Ahora se obtendrá el balance para saber si el nodo está balanceado o no.
		
		if(FE(raiz) > 1 && FE(raiz.l) > 0) {
			return rotacionDerecha(raiz);
		}else if(FE(raiz) > 1 && FE(raiz.l) < 0) {
			raiz.l = rotacionIzquierda(raiz.l);
			return rotacionDerecha(raiz);
		}else if(FE(raiz) < -1 && FE(raiz.r) < 0) {
			return rotacionIzquierda(raiz);
		}else if(FE(raiz) < -1 && FE(raiz.r) > 0) {
			raiz.r = rotacionDerecha(raiz.r);
			return rotacionIzquierda(raiz);
		}
		
		return raiz;
	}
	
	public nodoAVL getArchivo(nodoAVL raiz, String nombreArchivo) {
		if (nombreArchivo.compareTo(raiz.NombreArchivo) > 0) {
			getArchivo(raiz.r, nombreArchivo);
		} else if (nombreArchivo.compareTo(raiz.NombreArchivo) < 0) {
			getArchivo(raiz.l, nombreArchivo);
		} else {
			obtenerArchivo = raiz;
		}
		return obtenerArchivo;
	}

	public boolean existencia(nodoAVL raiz, String nombreArchivo) {
		if(raiz == null) {
			existencia = false;
		}else {
			if (nombreArchivo.compareTo(raiz.NombreArchivo) > 0) {
				existencia = false;
				existencia(raiz.r, nombreArchivo);
			} else if (nombreArchivo.compareTo(raiz.NombreArchivo) < 0) {
				existencia = false;
				existencia(raiz.l, nombreArchivo);
			} else {
				existencia = true;
			}
		}
		return existencia;
	}

	public nodoAVL minimo(nodoAVL nodo) {
		nodoAVL temp = nodo;
		while (temp.l != null) {
			temp = temp.l;
		}
		return temp;
	}
}
