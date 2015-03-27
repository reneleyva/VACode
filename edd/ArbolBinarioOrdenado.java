package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son
 * genéricos, pero acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos
 *       sus descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos
 *       sus descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios
     * ordenados. */
    private class Iterador<T> implements Iterator<T> {

        /* Pila para emular la pila de ejecución. */
        private Pila<ArbolBinario<T>.Vertice<T>> pila;

        /* Construye un iterador con el vértice recibido. */
        public Iterador(ArbolBinario<T>.Vertice<T> vertice) {
            pila = new Pila<ArbolBinario<T>.Vertice<T>>();
            meteIzq(vertice); 
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            if(pila.esVacia())
            	return false;
            return true;
        }

  		/* Regresa el siguiente elemento del árbol en orden. */ 
  		@Override public T next() { 
    		if(hasNext()){ 
        		ArbolBinario<T>.Vertice<T> vertice = pila.saca(); 
        		meteIzq(vertice.derecho); 
        		return vertice.elemento; 
    		}else 
        return null; 
  		} 
  
  		/* Mete los vertice desde la raiz y todos sus hijos izquierdos en la pila */ 
  		private void meteIzq(ArbolBinario<T>.Vertice<T> vertice){ 
    		if(vertice == null) 
        		return; 
    		pila.mete(vertice); 
    		meteIzq(vertice.izquierdo); 
  		}  

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Constructor sin parámetros. Sencillamente ejecuta el
     * constructor sin parámetros de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de un árbol
     * binario. El árbol binario ordenado tiene los mismos elementos
     * que el árbol recibido, pero ordenados.
     * @param arbol el árbol binario a partir del cuál creamos el
     *        árbol binario ordenado.
     */
    public ArbolBinarioOrdenado(ArbolBinario<T> arbol) {
        for(T t : arbol) 
            agrega(t);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden
     * in-order.
     * @param elemento el elemento a agregar.
     * @return el vértice que contiene al nuevo elemento.
     */
    @Override public VerticeArbolBinario<T> agrega(T elemento) {
        elementos++;
        if(raiz == null){
        	Vertice<T> temp = new Vertice<T>(elemento);
        	raiz = temp;
        	return raiz;
        }
        return agrega(elemento,raiz);
    }

    //Metodo auxiliar para agrega
    private VerticeArbolBinario<T> agrega(T elemento,Vertice<T> v){
    	if(v.elemento.compareTo(elemento)>=0){
    		if(!v.hayIzquierdo()){
    			v.izquierdo = new Vertice<T>(elemento);
    			v.izquierdo.padre = v;
    			return v.izquierdo;
    		}else
    			return agrega(elemento,v.izquierdo);
    	}else{	
    		if(!v.hayDerecho()){
    			v.derecho = new Vertice<T>(elemento);
    			v.derecho.padre = v;
    			return v.derecho;
    		}else
    			return agrega(elemento,v.derecho);
    	}	
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no
     * hace nada; si está varias veces, elimina el primero que
     * encuentre (in-order). El árbol conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
    	Vertice<T> v=vertice(busca(elemento)); 
    	if(v == null)
    		return;
    	elementos--;
    	elimina(v);
    }

    //Metodo auxiliar para elimina
    private void elimina(Vertice<T> v){ 
		Vertice<T> vAnt=buscaVerticeAnterior(v); 
		if(vAnt != null){ 
			intercambia(v, vAnt); 
			elimina(vAnt); 
		}else{ 
        	if(v.derecho == null) 
            	eliminaHoja(v); 
        	else{ 
            	if(v == raiz){ 
                	raiz=v.derecho; 
                    v.derecho.padre=null; 
                }else{ 
                    subeSubArbolDerecho(v);
                } 
            }  
        } 
    } 

    //Metodo para eliminar una hoja
    private void eliminaHoja(Vertice<T> v){ 
        if(v == raiz) 
            raiz=null; 
        else 
            if(v.padre.izquierdo!=null && v.padre.izquierdo.equals(v)) 
                v.padre.izquierdo=null; 
            else 
                v.padre.derecho=null; 
    }

    //Metodo para subir sub-arbol derecho
    private void subeSubArbolDerecho(Vertice<T> v){
            v.derecho.padre=v.padre; 
            if(v.padre.izquierdo!=null && v.padre.izquierdo.equals(v)) 
                v.padre.izquierdo=v.derecho; 
            else 
                v.padre.derecho=v.derecho;
    }
    //Metodo auxiliar para intercambiar
    private void intercambia(Vertice<T> v, Vertice<T> vAnt){
        T e = v.elemento;
        v.elemento = vAnt.elemento;
        vAnt.elemento = e;        
    }

    private Vertice<T> buscaVerticeAnterior(Vertice<T> vertice) {
        if(vertice == null ){
            return null;
        }else
            if(vertice.hayIzquierdo())
                return getMaximo(vertice.izquierdo);
            return null;        
    }

    private Vertice<T> getMaximo(Vertice<T> v){
        while(v.hayDerecho()){
            v = v.derecho;
        }
        return v;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo
     * encuentra, regresa el vértice que lo contiene; si no, regresa
     * <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
       return busca(elemento,raiz);
    }

    //Metodo auxiliar para busca
    private VerticeArbolBinario<T> busca(T elemento, Vertice<T> v){
    	if(v == null)
    		return null;
    	if(v.elemento.compareTo(elemento)==0)
    		return v;
    	if(v.elemento.compareTo(elemento)<0)
    		return busca(elemento,v.derecho);
    	return busca(elemento,v.izquierdo);
    }

    /**
     * Regresa el vértice máximo en el subárbol cuya raíz es el
     * vértice que recibe.
     * @param vertice el vértice raíz del subárbol del que queremos
     *                encontrar el máximo.
     * @return el vértice máximo el subárbol cuya raíz es el vértice
     *         que recibe.
     */
    protected Vertice<T> maximoEnSubarbol(Vertice<T> vertice) {
        if(vertice == null ){
            return null;
        }else
            if(vertice.hayIzquierdo())
                return getMaximo(vertice.izquierdo);
            return null;  
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera
     * en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador<T>(raiz);
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el
     * vértice no tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice<T> v = vertice(vertice);
        giraDerecha(v);
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el
     * vértice no tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */

    private void giraDerecha(Vertice<T> vertice) {
    if(vertice.izquierdo == null)
            return;
        Vertice<T> vI = vertice.izquierdo;

            vertice.izquierdo = vI.derecho;
                if(vI.derecho != null)
                    vI.derecho.padre = vertice;
                    vI.derecho = vertice;
                    vI.padre = vertice.padre;
        if(vertice.padre != null) {
            if(vertice.padre.derecho == vertice)
                vertice.padre.derecho = vI;
            else
                vertice.padre.izquierdo = vI;
        }
        else
            raiz = vI;
        vertice.padre = vI;   
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el
     * vértice no tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice<T> v = vertice(vertice);
        giraIzquierda(v);
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el
     * vértice no tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    private void giraIzquierda(Vertice<T> vertice) {
        if(vertice.derecho == null)
            return;
        Vertice<T> vD = vertice.derecho;
            vertice.derecho = vD.izquierdo;
                if(vD.izquierdo != null)
                    vD.izquierdo.padre = vertice;
                    vD.izquierdo = vertice;
                    vD.padre = vertice.padre;
            if(vertice.padre != null) {
                if(vertice.padre.derecho == vertice)
                    vertice.padre.derecho = vD;
            else
                vertice.padre.izquierdo = vD;
        }
        else
            raiz = vD;
            vertice.padre = vD;
            
    }
}
