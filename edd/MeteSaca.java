package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * Clase abtracta para estructuras lineales restringidas a
 * operaciones mete/saca/mira, todas ocupando una lista subyaciente.
 */
public abstract class MeteSaca<T> {

    /* Clase Nodo protegida para uso interno de sus clases
     * herederas. */
    protected class Nodo<T> {
        public T elemento;
        public Nodo<T> siguiente;

        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Primer elemento de la estructura. */
    protected Nodo<T> cabeza;
    /* Último elemento de la lista. */
    protected Nodo<T> rabo;
    /* Número de elementos en la estructura. */
    protected int elementos;

    /**
     * Agrega un elemento al extremo de la estructura.
     * @param elemento el elemento a agregar.
     */
    public abstract void mete(T elemento);

    /**
     * Elimina el elemento en un extremo de la estructura y lo
     * regresa.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T saca() {
        if(this.elementos == 0){
        	throw new NoSuchElementException();
        }
        T regresa = cabeza.elemento;
        cabeza = cabeza.siguiente;
        elementos--;
        return regresa;
    }

    /**
     * Nos permite ver el elemento en un extremo de la estructura,
     * sin sacarlo de la misma.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T mira() {
    	if(elementos==0)
    		throw new NoSuchElementException();
    	return cabeza.elemento;
    }

    /**
     * Nos dice si la estructura está vacía.
     * @return <tt>true</tt> si la estructura no tiene elementos,
     *         <tt>false</tt> en otro caso.
     */
    public boolean esVacia() {
        if(elementos == 0){
        	return true;
        }
        	return false;
    }
}
