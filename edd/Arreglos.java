package mx.unam.ciencias.edd;

/**
 * Clase para manipular arreglos genéricos.
 */
public class Arreglos {

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>>
                     void selectionSort(T[] a) {
        for (int i = 0;i < a.length;i++){
        	int m = i;
        	for(int j = i + 1; j < a.length;j++){
        		if (a[j].compareTo(a[m]) < 0)
        			m = j;
        	}
        	intercambia(a,i,m);
        }
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>>
                     void quickSort(T[] a) {
        quickSort(a,0,a.length-1);
    }

    //Metodo Auxiliar
    private static <T extends Comparable<T>>
                     void quickSort(T[] a,int ini,int fin) {
        if (fin - ini < 1)
        	return;
        int i = ini + 1, j = fin;
        while(i < j){
        	if(a[i].compareTo(a[ini]) > 0 && a[j].compareTo(a[ini]) <= 0)
        		intercambia(a, i++, j--);
        	else if (a[i].compareTo(a[ini]) <= 0)
        		i++;
        	else 
     		j--;
        }

        if (a[i].compareTo(a[ini]) > 0)
        	i--;
        intercambia(a,i,ini);
        quickSort(a,ini,i-1);
        quickSort(a,i+1,fin);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa
     * el índice del elemento en el arreglo, o -1 si no se
     * encuentra.
     * @param a el arreglo dónde buscar.
     * @param e el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se
     * encuentra.
     */
    public static <T extends Comparable<T>>
                     int busquedaBinaria(T[] a, T e) {
        int i = a.length;
        int c,
        inf = 0,
        sup = i-1;
        while (inf <= sup){
        	c = (sup+inf)/2;
        	if(a[c] == e)
        		return c;
        	else if (e.compareTo(a[c]) < 0)
        		sup = c-1;
        	else
        		inf = c+1;
        }
        return -1;
    }

    /**
    * Metodo Auxiliar
    * Intercambia los elementos en los indices del arreglo.
    */

    private static <T extends Comparable<T>>
    				 void intercambia(T[] a, int i, int j){
    				 	if(i == j)
    				 		return;
    				 	T t = a[j];
    				 	a[j] = a[i];
    				 	a[i] = t;
    }
}
