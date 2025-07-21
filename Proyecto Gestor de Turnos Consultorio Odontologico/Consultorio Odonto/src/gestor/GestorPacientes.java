package gestor;

	import Arbol.*;
	
	import TablaRAF.*;

	public class GestorPacientes {
		private TablaRAF archivo;
		private ABB<PacienteArbol> arbol;

		public GestorPacientes(String nombreArchivo) {
			archivo = new TablaRAF(nombreArchivo);
			arbol = new ABB<>();
			cargarABBDesdeArchivo(); 
		}

		public void registrarNuevoPaciente(Paciente p) {
		    
		    if (arbol.existeDNI((int)p.getDni())) {
		       // System.out.println("El paciente ya está registrado.");
		        return;
		    }
		    p.setActivo('V');
		    long pos = archivoLength() / p.largo()+1;
		    archivo.agregar(p); 

		    Paciente prueba = new Paciente();
		    archivo.get((int)pos, prueba);
		    //System.out.println("Paciente recién grabado archivo: " + prueba);

		    arbol.insertar(new PacienteArbol((int)p.getDni(), (int)pos)); // Guarda en árbol
		    //System.out.println("Paciente registrado con éxito Arbol.");
		}


		public boolean existeDNI(int dniConsultaNuevo) {
			if (arbol.existeDNI(dniConsultaNuevo)) {
				//System.out.println("El paciente ya está registrado.");
				return true;
			}
			return false;
		}


		private long archivoLength() {
			try {
				return archivo.raf.length();
			} catch (Exception e) {
				return 0;
			}
		}

		public void cerrar() {
			archivo.cerrar();
		}

		
		private void cargarABBDesdeArchivo() {
			int i = 1;
			Paciente p = new Paciente();
			while (!archivo.eof()) {
				archivo.get(i, p);
				if (p.getActivo() == 'V') {
					arbol.insertar(new PacienteArbol((int)p.getDni(), i));
				}
				i++;
			}
		}
	
		public Paciente buscarPacientePorDNI(int dni) {
		    Nodo<PacienteArbol> nodo = buscarNodoPorDNI(arbol.getRaiz(), dni);
		    if (nodo != null) {
		        int pos = nodo.getDato().getPos(); 

		        if (pos < 1) {
		           // System.err.println("ERROR: posición inválida en el árbol (" + pos + ")");
		            return null;
		        }

		        Paciente p = new Paciente();
		        archivo.get(pos, p); 

		        if (p.getActivo() == 'V') {
		            return p; 
		        } else {
		           // System.out.println("Paciente encontrado pero inactivo.");
		        }
		    } else {
		        //System.out.println("Paciente no encontrado en el árbol.");
		    }

		    return null;
		}

		
		private Nodo<PacienteArbol> buscarNodoPorDNI(Nodo<PacienteArbol> nodo, int dni) {
		    if (nodo == null) return null;

		    int dniNodo = nodo.getDato().getDni();

		    if (dni == dniNodo) return nodo;
		    else if (dni < dniNodo)
		        return buscarNodoPorDNI(nodo.getHizq(), dni);
		    else
		        return buscarNodoPorDNI(nodo.getHder(), dni);
		}
		
		/*public void imprimirPacientesArchivo() {
		    Paciente p = new Paciente();
		    int registro = 1;
		    System.out.println("Bytes totales del archivo: " + archivoLength());
		    System.out.println("Bytes por paciente: " + p.largo());
		    System.out.println("Entrando a imprimirPacientesArchivo()");

		    while (true) {
		        try {
		            if (registro > archivoLength() / p.largo()) {
		            	System.out.println("se termino");
		            	break;  // fin de registros
		            }
		            archivo.get(registro, p);
		            if (p.getActivo() == 'V') {
		                System.out.println(p);
		            }
		            registro++;
		        } catch (Exception e) {
		            System.out.println("Error leyendo registro " + registro);
		            break;
		        }
		    }
		}*/
		
		/*public void imprimirPacientesInorden() {
	    imprimirPacientesInorden(arbol.getRaiz());
	}*/

	/*private void imprimirPacientesInorden(Nodo<PacienteArbol> nodo) {
	    if (nodo != null) {
	        imprimirPacientesInorden(nodo.getHizq());

	        PacienteArbol dato = nodo.getDato();
	        int pos = dato.getPos();
	        Paciente paciente = new Paciente();
	        archivo.get(pos, paciente);

	        if (paciente.getActivo() == 'V') {
	          System.out.println(paciente); 
	        }

	        imprimirPacientesInorden(nodo.getHder());
	    }
	}*/

	}

