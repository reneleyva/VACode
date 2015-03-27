package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal
 * forma que el árbol siempre es lo más cercano posible a estar
 * lleno.<p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios
     * completos. */
    private class Iterador<T> implements Iterator<T> {

        private Cola<ArbolBinario<T>.Vertice<T>> cola;

        /* Constructor que recibe la raíz del árbol. */
        public Iterador(ArbolBinario<T>.Vertice<T> raiz) {
            	cola = new Cola<ArbolBinario<T>.Vertice<T>>();
            	cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            if(cola.esVacia())
				return false;
			return true;
        }

        /* Regresa el elemento siguiente. */
        @Override public T next() {
            if(hasNext()){
            	ArbolBinario<T>.Vertice<T> temp = cola.saca();
            	if(temp.hayIzquierdo())
            		cola.mete(temp.izquierdo);
            	if(temp.hayDerecho())
            		cola.mete(temp.derecho);
        	return temp.get();
        	}else
        	throw new NoSuchElementException();
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
    public ArbolBinarioCompleto() { super(); }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo
     * elemento se coloca a la derecha del último nivel, o a la
     * izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @return un iterador que apunta al vértice del árbol que
     *         contiene el elemento.
     */
    @Override public VerticeArbolBinario<T> agrega(T elemento) {
    	elementos++;
    	ArbolBinario<T>.Vertice<T> temp = new ArbolBinario<T>.Vertice<T>(elemento);
    	if(raiz == null){
    		raiz = temp;
    		return temp;
    	}
    	Cola<ArbolBinario<T>.Vertice<T>> cola = new Cola<ArbolBinario<T>.Vertice<T>>();	
    		cola.mete(raiz);
       	return agrega(elemento,cola);
    }

    //Metodo auxiliar para agrega
    private VerticeArbolBinario<T> agrega (T elemento,Cola<ArbolBinario<T>.Vertice<T>> cola){
    	if(cola.esVacia()){
    		return null;
    	}
    	ArbolBinario<T>.Vertice<T> temp = cola.saca();
    	if(temp.izquierdo == null){
    		temp.izquierdo = new ArbolBinario<T>.Vertice<T>(elemento);
    		temp.izquierdo.padre = temp;
    		return temp;
    	}
    	if(temp.derecho == null){
    		temp.derecho = new ArbolBinario<T>.Vertice<T>(elemento);
    		temp.derecho.padre = temp;
    		return temp;
       	}
       	cola.mete(temp.izquierdo);
       	cola.mete(temp.derecho);
       	agrega(elemento,cola);
       	return temp;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia
     * lugares con el último elemento del árbol al recorrerlo por
     * BFS, y entonces es eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if(raiz == null)
            return;
        Vertice<T> vertice = null;
        VerticeArbolBinario<T> v = busca(elemento);
        Vertice<T> vertice1 = vertice(v);
        Cola<Vertice<T>> cola = new Cola<Vertice<T>>();
        cola.mete(raiz);
        while(!cola.esVacia()){
          vertice = cola.saca();
          if(vertice.izquierdo != null)
             cola.mete(vertice.izquierdo);
          if(vertice.derecho != null)
              cola.mete(vertice.derecho);
        }
        if(vertice1 == null)
            return;
        if(vertice == raiz)
            raiz = null;
        else {
            vertice1.elemento = vertice.elemento;
            if(vertice.padre.izquierdo.equals(vertice))
                vertice.padre.izquierdo = null;
            else
                vertice.padre.derecho = null;
                vertice.padre = null;
          }
        elementos--;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera
     * en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador<T>(raiz);
    }
}

