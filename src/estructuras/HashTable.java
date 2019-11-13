package estructuras;

import java.security.NoSuchAlgorithmException;

import estructuras.Grafo;
import metodos.*;

public class HashTable {
	// esta variable solo me dirá la posición
	// de el array de primos en dónde me encuentro actualmente
	// para posterior poder aumentarlo, cuando el factor de utilización
	// sea mayor al establecido...
	static int indicePrimo = 0;
	/* Tabla Hash general */
	static int primos[] = { 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
			103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211,
			223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337,
			347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461,
			463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601,
			607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739,
			743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881,
			883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997 };
	// array de usuarios de tamaño 7
	public static nodoHash users[] = new nodoHash[primos[indicePrimo]];
	// esta variable me ayudará a guardar el numero de datos que podré guardar
	// dependiendo de que factor de utilización se encuentra en mi tabla hash...
	static int numeroPrimo = primos[indicePrimo];
	// Esta variable solo me ayudará para saber, cuantos usuarios han sido
	// ingresados
	static int usuarios_registrados = 0;
	static String hex_hash = "";
	static int nuevo_hash_index = 0;
	static int intentos = 1;
	
	public static Grafo carpetaRaiz = null;

	public static class nodoHash {
		/* Nodo de la tabla Hash */
		public String nom;
		String pass;
		String hex_pass;
		int orden = 0;

		public nodoHash(String _nom, String _pass, String hex) {
			// TODO Auto-generated constructor stub
			nom = _nom;
			hex_pass = hex;
			carpetaRaiz = new Grafo("/");
		}
	}

	public void insertar(String _nom, String _pass, boolean nuevo) throws NoSuchAlgorithmException {
		// Calculo de indice;
		int indice = 0;
		int hash_index = 0;
		// Suma de todos los ascci del nombre.
		for (char i : _nom.toCharArray()) {
			indice += i;
		}
		hash_index = indice % primos[indicePrimo];
		// Calculo de codigo hash de la contraseña...
		if(nuevo) {
			hex_hash = Hash.get_sha256(Hash.sha256(_pass));
		}
		if (users[hash_index] == null) {
			users[hash_index] = new nodoHash(_nom, _pass, hex_hash);
			users[hash_index].orden = usuarios_registrados;
			usuarios_registrados = usuarios_registrados + 1;
		} else {
			nuevo_hash_index = 0;
			intentos = 1;
			while (true) {
				nuevo_hash_index = (int) (hash_index + Math.pow(intentos, 2)) % primos[indicePrimo];
				if (users[nuevo_hash_index] == null) {
					users[nuevo_hash_index] = new nodoHash(_nom, _pass, hex_hash);
					users[nuevo_hash_index].orden = usuarios_registrados;
					usuarios_registrados = usuarios_registrados + 1;
					break;
				}
				intentos++;
			}
		}
		// Después de insertar, verificaré que no sea necesario hacer un rehashing a la
		// tabla...
		if (((usuarios_registrados * 100) / numeroPrimo) > 75) { // Rehashing solamente si el factor de uso
																	// solamente sobrepase el 75% del tamaño de
																	// la tabla actual.
			reHashing();
		}
	}

	public void reHashing() throws NoSuchAlgorithmException {
		// Calculo del siguiente primo...
		indicePrimo = indicePrimo + 1;
		numeroPrimo = primos[indicePrimo];

		// Reestructuración de elementos...

		// En esta variable guardaré todos los datos almacenados anteriormente
		// antes de proceder a realizar el rehashing.

		// Ordeno la tabla por medio del numero de insercion.
		// Para que la "pureza" de la tabla hash no se pierda por completo.
		nodoHash temp[] = ordenar();
		// Creo y almaceno nuevamente la tabla de users...
		users = new nodoHash[numeroPrimo];

		// Aqui realizo el rehashing de todos los elementos creados en user...
		// reinicializo el contador de usuarios registrados para que no haya problema al
		// copiar y reinsertar
		// los valores anteriores
		usuarios_registrados = 0;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] != null) {
				insertar(temp[i].nom, temp[i].pass, false);
			}
		}
		System.out.println("Después de Aplicar Rehashing");
		mostrar(users);
	}

	public void mostrar(nodoHash[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				System.out.println(array[i].nom + "\n-----");
			} else {
				System.out.println("\n---null---");
			}
		}
	}

	public nodoHash[] ordenar() {
		int contador = 0;
		nodoHash[] temporal = new nodoHash[getSize()];
		nodoHash temp = null;
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null) {
				temporal[contador] = users[i];
				contador++;
			}
		}
		// Ordenar mediante bubble sort, ya que necesito ingresar los datos
		// conforme hayan sido ingresados anteriormente
		// por lo que el "orden" de entrada, influira en su posición
		// a la hora de hacer rehashing a la tabla...
		for (int i = 0; i < temporal.length - 1; i++) {
			for (int j = i + 1; j < temporal.length; j++) {
				if (temporal[j].orden < temporal[i].orden) {
					temp = temporal[i];
					temporal[i] = temporal[j];
					temporal[j] = temp;
				}
			}
		}
		return temporal;
	}

	public int getSize() {
		int contador = 0;
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null) {
				contador++;
			}
		}
		return contador;
	}

	public boolean existe(String nom) {
		int indice = 0;
		for (char i : nom.toCharArray()) {
			indice += i;
		}
		int search_index = indice % primos[indicePrimo];
		if (users[search_index] != null) {
			if (users[search_index].nom.equals(nom)) {
				return true;
			} else {
				int contador = 0;
				nuevo_hash_index = 0;
				intentos = 1;
				while (contador < primos[primos.length - 1]) {
					nuevo_hash_index = (int) (search_index + Math.pow(intentos, 2)) % primos[indicePrimo];
					if (users[nuevo_hash_index] != null) {
						if (users[nuevo_hash_index].nom.equals(nom)) {
							return true;
						}
					}
					intentos++;
					contador++;
				}
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String getPassHash(String nom) {
		int indice = 0;
		for (char i : nom.toCharArray()) {
			indice += i;
		}
		int search_index = indice % primos[indicePrimo];
		if (users[search_index] != null) {
			if (users[search_index].nom.equals(nom)) {
				return users[search_index].hex_pass;
			} else {
				int contador = 0;
				nuevo_hash_index = 0;
				intentos = 1;
				while (contador < primos[primos.length - 1]) {
					nuevo_hash_index = (int) (search_index + Math.pow(intentos, 2)) % primos[indicePrimo];
					if (users[nuevo_hash_index] != null) {
						if (users[nuevo_hash_index].nom.equals(nom)) {
							return users[nuevo_hash_index].hex_pass;
						}
					}
					intentos++;
					contador++;
				}
				return null;
			}
		} else {
			return null;
		}
	}
	
	public nodoHash getUsuario(String nom) {
		int indice = 0;
		for (char i : nom.toCharArray()) {
			indice += i;
		}
		int search_index = indice % primos[indicePrimo];
		if (users[search_index] != null) {
			if (users[search_index].nom.equals(nom)) {
				return users[search_index];
			} else {
				int contador = 0;
				nuevo_hash_index = 0;
				intentos = 1;
				while (contador < primos[primos.length - 1]) {
					nuevo_hash_index = (int) (search_index + Math.pow(intentos, 2)) % primos[indicePrimo];
					if (users[nuevo_hash_index] != null) {
						if (users[nuevo_hash_index].nom.equals(nom)) {
							return users[nuevo_hash_index];
						}
					}
					intentos++;
					contador++;
				}
				return null;
			}
		} else {
			return null;
		}
	}
}
