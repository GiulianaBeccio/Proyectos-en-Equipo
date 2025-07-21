package Arbol;

public class ABB<T extends Comparable <T>> {
	private Nodo<T> raiz;
	public ABB(){
		raiz = null;
	}
	public void insertar(T dato){
		raiz=insertar(raiz, dato);
	}
	private Nodo<T> insertar(Nodo <T> raiz, T dato){ 
		if (raiz == null )
			raiz = new Nodo<T>(null, dato, null);
	else{
		if (dato.compareTo((raiz.getDato()))<0)
	
		raiz.setHizq(insertar(raiz.getHizq(),dato));
	    else
		raiz.setHder(insertar(raiz.getHder(), dato));
	}
	
		return raiz;
	}
	public void inorden()
	{
		inorden(raiz);
	}
	private void inorden(Nodo<T> raiz) {
	     if ( raiz != null)
	     {
	    	 inorden(raiz.getHizq());
	    	 System.out.print (raiz.getDato()+" ");
	    	 inorden(raiz.getHder());
	     }
		
	}
	public void insertarIt(T dato) {
		Nodo <T> ant= null; // ant siempre queda como padre de p 
		Nodo<T> p = raiz;
		if (p==null)
			raiz= new Nodo<T>(null,dato,null);
		else
		{
			while(p != null)
			{
				ant =p;
				if(dato.compareTo(p.getDato())<0) 
					p=p.getHizq();
				else 
					p = p.getHder();
				
			}
			p = new Nodo<T>(null,dato,null);
			if(dato.compareTo(ant.getDato())<0)
				ant.setHizq(p);
			else
				ant.setHder(p);
		}
		
	}
	public boolean existeDNI(int dniBuscado) {
	    return existeDNI(raiz, dniBuscado);
	}

	private boolean existeDNI(Nodo<T> nodo, int dniBuscado) {
	    if (nodo == null) return false;

	    T dato = nodo.getDato();
	    if (dato instanceof PacienteArbol) {
	        int dniNodo = ((PacienteArbol) dato).getDni();
	        if (dniBuscado == dniNodo) return true;
	        else if (dniBuscado < dniNodo)
	            return existeDNI(nodo.getHizq(), dniBuscado);
	        else
	            return existeDNI(nodo.getHder(), dniBuscado);
	    } else {
	        return false;
	    }
	}
	public Nodo<T> getRaiz() {
	    return raiz;
	}
	
	
	

}



