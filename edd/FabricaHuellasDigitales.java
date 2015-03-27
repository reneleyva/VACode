package mx.unam.ciencias.edd;

/**
 * Clase para fabricar generadores de huellas digitales.
 */
public class FabricaHuellasDigitales {

    /**
     * Identificador para fabricar la huella digital de Bob
     * Jenkins para cadenas.
     */
    public static final int BJ_STRING   = 0;
    /**
     * Identificador para fabricar la huella digital de GLib para
     * cadenas.
     */
    public static final int GLIB_STRING = 1;
    /**
     * Identificador para fabricar la huella digital de XOR para
     * cadenas.
     */
    public static final int XOR_STRING  = 2;

    /**
     * Regresa una instancia de {@link HuellaDigital} para cadenas.
     * @param identificador el identificador del tipo de huella
     *        digital que se desea.
     * @throws IllegalArgumentException si recibe un identificador
     *         no reconocido.
     */
    public static HuellaDigital<String> getInstanciaString(int identificador) {
        
        switch (identificador){
            case BJ_STRING:
                return new gethuellaBobJenkins();
            case GLIB_STRING:
                return gethuellaGLIB();
            case XOR_STRING:
                return new gethuellaXor();
            default:
                throw new IllegalArgumentException();
        }
    }

    private static HuellaDigital<String> gethuellaGLIB(){
      	return new HuellaDigital<String>(){
			public int huellaDigital(String a){
				byte[] n = a.getBytes();
				int e = n.length;
				int l = 5381;
				for(int i = 0; i < e; i++) {
	  				char c = (char)n[i];
		  			l = l*33 + c;
				}
				return l;
			}
      	};
    }
    
	private static class gethuellaBobJenkins implements HuellaDigital<String> {
      	@Override public int huellaDigital(String s) {
            int  h = (int) hash(s.getBytes());
            return h;
        }
        long a,b,c;
        private int hash(byte [] k) {
            int l = k.length;
            a = 0x000000009e3779b9L;
            b = a;
            c = 0xffffffff;
            int i = 0;

            while (l >= 12) {
                a += (k[i] + (k[i+1] << 8) + (k[i+2] << 16) + (k[i+3] << 24));
                b += (k[i+4] + (k[i+5] << 8) + (k[i+6] << 16) + (k[i+7] << 24));
                c += (k[i+8] + (k[i+9] << 8) + (k[i+10] << 16) + (k[i+11] << 24));
                mezcla();
                i += 12;
                l -= 12;
            }
            c += k.length;

            switch(l) {
            	case 11:
                c = agrega(c, izquierda(getLongitudByte(k[i + 10]), 24));
	            case 10:
	                c = agrega(c, izquierda(getLongitudByte(k[i + 9]), 16));
	            case 9:
	                c = agrega(c, izquierda(getLongitudByte(k[i + 8]), 8));
	                // the first byte of c is reserved for the length
	            case 8:
	                b = agrega(b, izquierda(getLongitudByte(k[i + 7]), 24));
	            case 7:
	                b = agrega(b, izquierda(getLongitudByte(k[i + 6]), 16));
	            case 6:
	                b = agrega(b, izquierda(getLongitudByte(k[i + 5]), 8));
	            case 5:
	                b = agrega(b, (k[i + 4]));
	            case 4:
	                a = agrega(a, izquierda(getLongitudByte(k[i + 3]), 24));
	            case 3:
	                a = agrega(a, izquierda(getLongitudByte(k[i + 2]), 16));
	            case 2:
	                a = agrega(a, izquierda(getLongitudByte(k[i + 1]), 8));
	            case 1:
	                a = agrega(a, (k[i + 0]));
            }
    		mezcla();
    		return (int) (c&0xffffffff);
    	}

        private long getLongitudByte(byte b) {
            long val = b & 0x7F;
             return val;
        }

        private void mezcla() {
           a = resta(a, b); a = resta(a, c); a = xor(a, c >> 13);
           b = resta(b, c); b = resta(b, a); b = xor(b, izquierda(a, 8));
           c = resta(c, a); c = resta(c, b); c = xor(c, (b >> 13));
           a = resta(a, b); a = resta(a, c); a = xor(a, (c >> 12));
           b = resta(b, c); b = resta(b, a); b = xor(b, izquierda(a, 16));
           c = resta(c, a); c = resta(c, b); c = xor(c, (b >> 5));
           a = resta(a, b); a = resta(a, c); a = xor(a, (c >> 3));
           b = resta(b, c); b = resta(b, a); b = xor(b, izquierda(a, 10));
           c = resta(c, a); c = resta(c, b); c = xor(c, (b >> 15));

        }

        private long izquierda(long n, int r) {
            return (n << r) & 0x00000000ffffffffL;
        }

        private long resta(long a, long b) {
            return (a - b) & 0x00000000ffffffffL;
        }

        private long xor(long a, long b) {
            return (a ^ b) & 0x00000000ffffffffL;
        }

        private long agrega(long a, long b) {
        	return (a + b) & 0x00000000ffffffffL;
        }
    }
	
	private static class gethuellaXor implements HuellaDigital<String>{
        public int huellaDigital(String s){    
            byte[] k = s.getBytes();
            int l = k.length;
            int r= 0;
            int i = 0;
            int p = 1;
            while(l >= 4){
                int x = (k[i] << 24)|(k[i+1]<<16)|(k[i+2]<<8)| (k[i+3]);
                if(p == 1){
                    r= x; 
                    p=0;
                }else{
                    r ^= x;
                }   
                i +=4;
                l -= 4;
            }
            int t= 0;
            switch(l){
                case 3: t|= (k[i+2] << 8);
                case 2: t|= (k[i+1] << 16);
                case 1: t|= (k[i] << 24);
            }
            if(l > 0)
                r ^= t;
        	return r;
        }
    }
}
