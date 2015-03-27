package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {
      if(elementos == 0){
	     Nodo<T> temp = new Nodo<T>(elemento);
	     cabeza = temp;
	     rabo = temp;
	    }else{
	       Nodo<T> temp = new Nodo<T>(elemento);
         rabo.siguiente = temp;
         rabo = temp;
	    } 
	    elementos++;
    }
}
