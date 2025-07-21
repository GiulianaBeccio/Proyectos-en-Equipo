package TablaRAF;

import java.io.RandomAccessFile;

public class TablaRAF {
	public RandomAccessFile raf;

	public TablaRAF() {
	}

	public TablaRAF(String nombre) {
		try {
			raf = new RandomAccessFile(nombre, "rw");
			raf.seek(0);
		} catch (Exception e) {
			//System.err.println("ERROR: " + nombre + " no se pudo abrir");
		}
	}

	public void agregar(Registro r) {
		try {
			raf.seek(raf.length());
			//System.out.println("Posición antes de grabar: " + raf.getFilePointer());
			r.grabar(raf);
			//System.out.println("Posición después de grabar: " + raf.getFilePointer());
		} catch (Exception e) {
			//System.out.println("ERROR: al grabar ");
			 e.printStackTrace();
		}
	
	}


	public void actualizar(int n, Registro r) {
		try {
			raf.seek((n - 1) * r.largo());
			r.grabar(raf);
		} catch (Exception e) {
			//System.out.println("ERROR: al actualizar ");
		}
	}

	public void eliminar(int n, Registro r) {
		try {
			raf.seek((n-1)*r.largo());
			r.setActivo('F'); 
			r.grabar(raf);
		}catch ( Exception e ) {
			//System.out.println("ERROR: al eliminar "); 
			}
	}

	public void get(int n, Registro r) {
		try {
			raf.seek((n - 1) * r.largo());
			r.leer(raf);
		} catch (Exception e) {
			//System.out.println("ERROR: en el get ");
			  e.printStackTrace();
		}
	}

	public boolean eof() {
		boolean r = false;
		try {
			r = (raf.getFilePointer() >= raf.length());
		} catch (Exception e) {
			//System.out.println("ERROR: en eof");
		}
		return r;
	}

	public void cerrar() {
		try {
			raf.close();
		} catch (Exception e) {
			//System.out.println("ERROR: al cerrar");
		}
	}
}
