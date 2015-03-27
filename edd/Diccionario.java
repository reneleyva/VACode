package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario
 * generaliza el concepto de arreglo, permitiendo (en general,
 * dependiendo de qué tan buena sea su método para generar huellas
 * digitales) agregar, eliminar, y buscar valores en <i>O</i>(1) en
 * cada uno de estos casos.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Clase privada para iteradores de diccionarios. */
    private class Iterador<V> implements Iterator<V> {

        /* En qué lista estamos. */
        private int indice;
        /* Diccionario. */
        private Diccionario<K,V> diccionario;
        /* Iterador auxiliar. */
        private Iterator<Diccionario<K,V>.Entrada<K,V>> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas
         * del diccionario. */
        public Iterador(Diccionario<K,V> diccionario) {
            this.diccionario = diccionario;
            while(indice < diccionario.entradas.length && diccionario.entradas[indice] == null)
                indice++;
            if(diccionario.entradas.length > indice)
                iterador = diccionario.entradas[indice].iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        public boolean hasNext() {
            if(iterador == null)
                return false;
            if(iterador.hasNext()) 
                return true;
            indice++;
            iterador = null;
            while(indice < diccionario.entradas.length && diccionario.entradas[indice] == null) 
                indice++;
            if(diccionario.entradas.length > indice)
                iterador = diccionario.entradas[indice].iterator();
            return hasNext();
        }

        /* Regresa el siguiente elemento. */
        public V next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return iterador.next().valor;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* Tamaño mínimo; decidido arbitrariamente a 2^6. */
    private static final int MIN_N = 64;

    /* Máscara para no usar módulo. */
    private int mascara;
    /* Huella digital. */
    private HuellaDigital<K> huella;
    /* Nuestro diccionario. */
    private Lista<Entrada<K, V>>[] entradas;
    /* Número de valores*/
    private int total;

    /* Clase para las entradas del diccionario. */
    private class Entrada<K, V> {
        public K llave;
        public V valor;
        public Entrada(K llave, V valor){
        	this.llave = llave;
        	this.valor = valor;
        }
    }

    /* Truco para crear un arreglo genérico. Es necesario hacerlo
       así por cómo Java implementa sus genéricos; de otra forma
       obtenemos advertencias del compilador. */
    @SuppressWarnings("unchecked") private Lista<Entrada<K, V>>[] nuevoArreglo(int n) {
        Lista[] arreglo = new Lista[n];
        return (Lista<Entrada<K, V>>[])arreglo;
    }

    /**
     * Construye un diccionario con un tamaño inicial y huella
     * digital predeterminados.
     */
    public Diccionario() {
        this(MIN_N, new HuellaDigital<K>(){
            public int huellaDigital(K llave) {
                return llave.hashCode();
            }
        });
    }

    /**
     * Construye un diccionario con un tamaño inicial definido por
     * el usuario, y una huella digital predeterminada.
     * @param tam el tamaño a utilizar.
     */
    public Diccionario(int tam) {
        this(tam, new HuellaDigital<K>(){
            public int huellaDigital(K llave) {
                return llave.hashCode();
            }
        });
    }

    /**
     * Construye un diccionario con un tamaño inicial
     * predeterminado, y una huella digital definida por el usuario.
     * @param huella la huella digital a utilizar.
     */
    public Diccionario(HuellaDigital<K> huella) {
        this(MIN_N, huella);
    }

    /**
     * Construye un diccionario con un tamaño inicial, y un método
     * de huella digital definidos por el usuario.
     * @param tam el tamaño del diccionario.
     * @param huella la huella digital a utilizar.
     */
    public Diccionario(int tam, HuellaDigital<K> huella) {
        mascara = mascara(tam);
        entradas = nuevoArreglo(mascara+1);
        this.huella = huella;
        total = 0;
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave
     * proporcionada. Si la llave ya había sido utilizada antes para
     * agregar un valor, el diccionario reemplaza ese valor con el
     * recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     */
    public void agrega(K llave, V valor) {
        int i = indice(llave);
        Lista<Entrada<K,V>> lista = getLista(i,true);
        Entrada<K,V> entrada = buscaEntrada(lista,llave);
        if(entrada != null) 
            entrada.valor = valor;
        else {
            entrada = new Entrada<K,V>(llave,valor);
            lista.agregaFinal(entrada);
            total++;
        }
        if(carga() > MAXIMA_CARGA)
        creceArreglo();
    }

    private Lista<Entrada<K,V>> getLista(int indice, boolean caso) {
        if(indice < entradas.length && entradas[indice] != null)
            return entradas[indice];
        if(indice < entradas.length && entradas[indice] == null && caso == true) {
            entradas[indice] = new Lista<Entrada<K,V>>();
            return entradas[indice];
        }
        return null;
    }

    private Entrada<K,V> buscaEntrada(Lista<Entrada<K,V>> lista, K llave) {
        for(Entrada<K,V> entrada : lista)
            if(entrada.llave.equals(llave))
                return entrada;
        return null;
    }

    private int indice(K llave) {
           return mascara & huella.huellaDigital(llave);
    }
    
    private void creceArreglo() {
        mascara = (mascara<<1)|1;//Revisar
        total = 0;
        Lista<V> valores = valores();
        Lista<K> llaves = llaves();
        entradas = nuevoArreglo(mascara+1);
        for(K llave : llaves)
            agrega(llave,valores.eliminaPrimero());
    }

    /**
     * Regresa el valor del diccionario asociado a la llave
     * proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws <tt>NoSuchElementException</tt> si la llave no está
     *         en el diccionario.
     */
    public V get(K llave) {
        Lista<Entrada<K,V>> lista = getLista(indice(llave),false);
        if(lista != null)
            for(Entrada<K,V> e : lista)
                if(e.llave.equals(llave))
                    return e.valor;
        throw new NoSuchElementException();
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <tt>true</tt> si la llave está en el diccionario,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(K llave) {
        int i = indice(llave);
        if(i < entradas.length && entradas[i] != null) {
            if(buscaEntrada(entradas[i],llave) != null)
                return true;
        }
        return false;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave
     * proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        if(!contiene(llave))
            throw new NoSuchElementException();
        int i = indice(llave);
        if(entradas[i].getLongitud() > 1)
            entradas[i].elimina(buscaEntrada(entradas[i],llave));
        else
            entradas[i] = null; 
        total--;
    }

    /**
     * Regresa una lista con todas las llaves con valores asociados
     * en el diccionario. La lista no tiene ningún tipo de orden.
     * @return una lista con todas las llaves.
     */
    public Lista<K> llaves() {
        Lista<K> llaves = new Lista<K>();
        for(int i = 0; i < entradas.length; i++)
            if(entradas[i] != null)
                for(Entrada<K,V> e : entradas[i])
                    llaves.agregaFinal(e.llave);
        return llaves;
    }

    /**
     * Regresa una lista con todos los valores en el diccionario. La
     * lista no tiene ningún tipo de orden.
     * @return una lista con todos los valores.
     */
    public Lista<V> valores() {
        Lista<V> valores = new Lista<V>();
        for(int i = 0; i < entradas.length; i++)
            if(entradas[i] != null)
                for(Entrada<K,V> e : entradas[i])
                    valores.agregaFinal(e.valor);
        return valores;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave
     * que tenemos en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        int max = 0;
        for(Lista<Entrada<K,V>> lista: entradas)
            if(lista != null)
                max = (max < lista.getLongitud())? lista.getLongitud() : max;
        return max-1;
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        int colisiones = 0;
        for(Lista<Entrada<K,V>> lista: entradas)
            if(lista != null)
                colisiones += lista.getLongitud()-1;
        return colisiones;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        return total/(mascara + 1.0);
    }

    /**
     * Regresa el número de valores en el diccionario.
     * @return el número de valores en el diccionario.
     */
    public int getTotal() {
        return total;
    }

    private int mascara(int n) {
        int m = 1;
        while (m < n)
            m = (m << 1) | 1;
         m = (m << 1) | 1;
        return m;
    }

    /**
     * Regresa un iterador para iterar los valores del
     * diccionario. El diccionario se itera sin ningún orden
     * específico.
     * @return un iterador para iterar el diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new Iterador<V>(this);
    }
}
