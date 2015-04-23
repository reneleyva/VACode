import time
"""Multiplica matrices"""
def multplica(M1, M2):
	a11 = M1[0][0]*M2[0][0] + M1[0][1]*M2[1][0]
	a12 = M1[0][0]*M2[0][1] + M1[0][1]*M2[1][1]
	a21 = M1[1][0]*M2[0][0] + M1[1][1]*M2[1][0]
	a22 = M1[1][0]*M2[0][1] + M1[1][1]*M2[1][1]
	r = [[a11, a12], [a21, a22]]
	return r 

"""Nos da la matriz a la p potencia, memo es un diccionario"""
def matrizPotencia(M, p, memo):
	if p == 1:
		return M 
	if p in memo:
		return memo[p]
	K = matrizPotencia(M, p//2, memo)
	R = multplica(K, K)
	memo[p] = R 
	return R 

"""Clacula el enesimo numero de fibonacci en tiempo lineal"""
def fibo(n, dicc):
	if (n in dicc):
		return dicc[n]
	if (n < 2):
		dicc[n] = 1
		return dicc[n]
	else:
		f = fibo(n-1, dicc) + fibo(n-2, dicc)
		dicc[n] = f
		return f 

"""Fiibonacci logaritmico"""
def fiboLog(n):
	if (n < 0):
		print("Bale Berga la Bida")
		return
	if (n < 2):
		return 1 
	 #i.e. 62 = 2^5 + 2^4 + 2^3 + 2^2 + 2^0 = 32 + 16 + 8 + 4 + 1.
	powers = [int(pow(2, b)) for (b, d) in enumerate(reversed(bin(n-1)[2:])) if d == '1'] #Lo encontre en internet
	Q = [[1, 1],
         [1, 0]]
    
	memo = dict()
	matrices = [matrizPotencia(Q, p, memo) for p in powers]

	while len(matrices) > 1:
		M1 = matrices.pop()
		M2 = matrices.pop()
		R = multplica(M1, M2)
		matrices.append(R)
	return matrices[0][0][0]

if __name__ == '__main__':
	start = time.time()
	f = fiboLog(1000000)
	print ("Mumero de didgitos de fibonacci de 1000000: %s " % len(str(f)))
	print ("Tiempo de ejecucion: %s" % (time.time() - start))
