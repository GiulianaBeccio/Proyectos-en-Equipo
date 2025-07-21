package Arbol;


public interface ArbolesBinariosBusqueda<T,W> {
	 public void insertar(T x, W clave);
	 public void inorden();
	 public T buscar(W clave);
	 public void insertarIt(T dato, W clave);
	 public boolean esVacio();
}
