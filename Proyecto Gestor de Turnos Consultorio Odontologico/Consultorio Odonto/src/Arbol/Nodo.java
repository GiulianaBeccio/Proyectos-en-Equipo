package Arbol;



public class Nodo<T extends Comparable<T>> {
	private T d;
	private Nodo<T> hizq, hder; 

	public T getDato() {
		return d;
	}
	public void setDato(T dato) {
		this.d = dato;
	}
	
	
	public Nodo(T d) {
		this.d = d;
	}
	
	public Nodo(Nodo<T> hi, T dato, Nodo<T> hd){
		this.d = dato;
		hizq = hi;
		hder = hd;
	}
	public T getD() {
		return d;
	}
	public void setD(T d) {
		this.d = d;
	}
	public Nodo<T> getHizq() {
		return hizq;
	}
	public void setHizq(Nodo<T> hizq) {
		this.hizq = hizq;
	}
	public Nodo<T> getHder() {
		return hder;
	}
	public void setHder(Nodo<T> hder) {
		this.hder = hder;
	}
	
	
}
