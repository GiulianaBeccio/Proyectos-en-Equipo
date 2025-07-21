package gestor;
import java.io.IOException;
import java.io.RandomAccessFile;
import TablaRAF.Registro;

public class Paciente implements Comparable<Paciente>, Registro{
		private long dni;
		private String nombre;
		private String telefono;
		private String obraSocial;
		private char activo = 'V'; // V = vigente, F = eliminado

		public Paciente() {} 
	    
		public Paciente(long dni, String nombre, String telefono, String obraSocial) {
			this.dni = dni;
			this.nombre = nombre;
			this.telefono = telefono;
			this.obraSocial = obraSocial;
			
		}
		@Override
		public long getClave() {
			return dni;
		}

		public long getDni() { return dni; }
		public void setDni(long dni) { this.dni = dni; }

		public String getNombre() { return nombre; }
		public void setNombre(String nombre) { this.nombre = nombre; }

		public String getTelefono() { return telefono; }
		public void setTelefono(String telefono) { this.telefono = telefono; }

		public String getObraSocial() { return obraSocial; }
		public void setObraSocial(String obraSocial) { this.obraSocial = obraSocial; }

		@Override
		public int compareTo(Paciente o) {
			return Long.compare(this.dni, o.dni);
		}

		@Override
		public String toString() {
			return "DNI: " + dni + ", Nombre: " + nombre + ", Tel: " + telefono + ", Obra: " + obraSocial;
		}
		
		private void escribirStringFijo(RandomAccessFile raf, String s, int largo) throws IOException {
		    StringBuffer sb = new StringBuffer(s);
		    if (sb.length() > largo) {
		        sb.setLength(largo);
		    } else {
		        while (sb.length() < largo) {
		            sb.append(' ');
		        }
		    }
		    raf.writeChars(sb.toString()); 
		}
		private String leerStringFijo(RandomAccessFile raf, int largo) throws IOException {
		    char[] chars = new char[largo];
		    for (int i = 0; i < largo; i++) {
		        chars[i] = raf.readChar();
		    }
		    return new String(chars).trim();
		}

		
		@Override
		public void grabar(RandomAccessFile raf) throws IOException {
		    raf.writeChar(activo);    
		    raf.writeLong(dni);      
		    escribirStringFijo(raf, nombre, 60);   
		    escribirStringFijo(raf, telefono, 30); 
		    escribirStringFijo(raf, obraSocial, 30);
		}

		@Override
		public void leer(RandomAccessFile raf) throws IOException {
		    activo = raf.readChar();
		    dni = raf.readLong();
		    nombre = leerStringFijo(raf, 60);
		    telefono = leerStringFijo(raf, 30);
		    obraSocial = leerStringFijo(raf, 30);
		}


		
		@Override
		public long largo() {
		    return 2 + 8 + 60*2 + 30*2 + 30*2; //250 
		}



		@Override
		public char getActivo() {
			return activo;
		}

		@Override
		public void setActivo(char c) {
			activo = c;
		}
		
		

		
}


