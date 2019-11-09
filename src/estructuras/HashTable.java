package estructuras;

import java.security.NoSuchAlgorithmException;

import estructuras.Grafo;
import metodos.*;

public class HashTable {
	// esta variable solo me dir� la posici�n
	// de el array de primos en d�nde me encuentro actualmente
	// para posterior poder aumentarlo, cuando el factor de utilizaci�n
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
	// array de usuarios de tama�o 7
	nodoHash users[] = new nodoHash[primos[indicePrimo]];
	// esta variable me ayudar� a guardar el numero de datos que podr� guardar
	// dependiendo de que factor de utilizaci�n se encuentra en mi tabla hash...
	static int numeroPrimo = primos[indicePrimo];
	// Esta variable solo me ayudar� para saber, cuantos usuarios han sido
	// ingresados
	static int usuarios_registrados = 0;
	static String hex_hash = "";
	static int nuevo_hash_index = 0;
	static int intentos = 1;

	public class nodoHash {
		/* Nodo de la tabla Hash */
		String nom;
		String pass;
		String hex_pass;
		int orden = 0;
		Grafo archivos = null;

		public nodoHash(String _nom, String _pass, String hex) {
			// TODO Auto-generated constructor stub
			nom = _nom;
			pass = _pass;
			hex_pass = hex;
			archivos = new Grafo();
		}
	}

	public void insertar(String _nom, String _pass) throws NoSuchAlgorithmException {
		// Calculo de indice;
		int indice = 0;
		int hash_index = 0;
		// Suma de todos los ascci del nombre.
		for (char i : _nom.toCharArray()) {
			indice += i;
		}
		hash_index = indice % primos[indicePrimo];
		// Calculo de codigo hash de la contrase�a...
		hex_hash = Hash.get_sha256(Hash.sha256(_pass));
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
		// Despu�s de insertar, verificar� que no sea necesario hacer un rehashing a la
		// tabla...
		if (((usuarios_registrados * 100) / numeroPrimo) > 75) { // Rehashing solamente si el factor de uso
																	// solamente sobrepase el 75% del tama�o de
																	// la tabla actual.
			reHashing();
		}
	}

	public void reHashing() throws NoSuchAlgorithmException {
		// Calculo del siguiente primo...
		System.out.println("Antes del Rehashing");
		mostrar();
		indicePrimo = indicePrimo + 1;
		numeroPrimo = primos[indicePrimo];

		// Reestructuraci�n de elementos...

		// En esta variable guardar� todos los datos almacenados anteriormente
		// antes de proceder a realizar el rehashing.
		
		// Ordeno la tabla por medio del numero de insercion.
		// Para que la "pureza" de la tabla hash no se pierda por completo.
		nodoHash temp[] = ordenar_mergesort();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp.length; j++) {

			}
		}
		// Creo y almaceno nuevamente la tabla de users...
		users = new nodoHash[numeroPrimo];
		for (int usuario = 0; usuario < users.length; usuario++) {
			users[usuario] = null;
		}

		// Aqui realizo el rehashing de todos los elementos creados en user...
		// reinicializo el contador de usuarios registrados para que no haya problema al
		// copiar y reinsertar
		// los valores anteriores
		usuarios_registrados = 0;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] != null) {
				insertar(temp[i].nom, temp[i].pass);
			}
		}
		System.out.println("Despu�s del Rehashing");
		mostrar();
	}

	public void mostrar() {
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null) {
				System.out.println(users[i].nom + "\n-----");
			} else {
				System.out.println("\n---null---");
			}
		}
	}

	public nodoHash[] ordenar_mergesort() {
		nodoHash[] temporal = users;
		for (int i = 0; i < temporal.length - 1; i++) {
			for (int j = i; j < temporal.length; j++) {
				
			}
		}
		return temporal;
	}
	
	public int getSize() {
		int contador = 0;
		for (int i = 0; i < users.length; i++) {
			if(users[i] != null) {
				contador++;
			}
		}
		return contador;
	}
}