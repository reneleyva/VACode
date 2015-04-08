#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include <math.h>
#include <float.h>
#define SIZE 10
typedef int (*compare)(void* a, void* b);
typedef struct{
	long long int x;
	long long int y;
}point;

typedef struct 
{
	point* p1;
	point* p2;
	long double dist;
}pairs;

point* newpoint(long long int x, long long int y)
{
	point* p = (point*) malloc(sizeof(point));
	p->x = x;
	p->y = y;
	return p;
}

pairs* newpairs(point* p1, point* p2, int dist)
{
	pairs* close = (pairs*)malloc(sizeof(pairs));
	close->p1 = p1;
	close->p2 = p2;
	close->dist = dist;
	return close;
}

int cmp(const void* a, const void* b)
{
	return (*(int*)a) - (*(int*)b);
}
int compareX(const void* x1,const  void* x2)
{
	point* p1 = (point*)x1;
	point* p2 = (point*)x2;
	if(p1->x < p2->x)
		return -1;
	if(p2->x < p1->x)
		return 1;
	return 0;
}
int compareY(const void* y1, const void* y2)
{
	point p1 = (*(point*)y1);
	point p2 = (*(point*)y2);
	return p1.y - p2.y;
}
long double square(long long int x)
{
	return x * x;
}
long double distance(point* p1, point* p2)
{
	return sqrt(square(p1->x - p2->x) + square(p1->y - p2->y));
}
void printpoint(point* p1)
{
	printf("( %.2Ld, %.2Ld ) ",p1->x,p1->y);
}
void printpairs(pairs* par)
{
	printpoint(par->p1);
	printf("&&");
	printpoint(par->p2);
	printf("\nSu distancia es %.2Lf\n",par->dist);
}

pairs* bforce(point* P[], int size)
{
	if(!P)
		return NULL;
	pairs* closest = NULL;
	long double delta = DBL_MAX;
	point* min1 = newpoint(0.0,0.0);
	point* min2 = newpoint(0.0,0.0);
	int i;
	int j;
	for(i=0; i < size; i++)
	{
		for(j= i+1; j < size; j++)
		{
			long double dist = distance(P[i],P[j]);
			if(dist < delta)
			{
				min1 =  *(P+i);
				min2 = *(P+j);
				delta = dist;
			}
		}
	}
	closest = newpairs(min1,min2,delta);
	return closest;
}


pairs* closest(point* P[],int size)
{
	if(size <= 3)
		return bforce(P, size);

	int lvertical = (int) size/2;
	pairs* CL = closest(P,lvertical);
	pairs* CR = closest(P+lvertical,size -lvertical);
	if(CL->dist < CR->dist)
		return CL;
	else
		return CR;
}

void copia(point* arr[], int size, point* cpy[]) {
	int i;
	for (i = 0; i < size; i++) {
		cpy[i] = arr[i];
	}
}
/*
void merge(void * A, int a,int e_size, void* B, int b, void *O, compare comp){
	int i=0;
	int j = 0;
	int k = 0;
	while(i<a && j<b){
		if(comp() < 0)
			O[k++]= A[i++];
		else 
			O[k++]=B[j++];
	}
	while (j < b)
		O[k++] = B[j++];
	while(i < a)
		O[k++]= A[i++];
}
void msort(void * L, int n, int e_size, compare comp ){
	if(n<2)
    	return;
  	int a = (n/2);
  	int b = n - a;
  	void* A; 
  	void* B;  //BBB
  	A= malloc((sizeof(e_size))*a);
  	B= malloc((sizeof(e_size))*b);
  	int i,j;
 	for(i=0; i<a; i++)
    	A[i]=L[i];
  	for(j=0; j<b;j++)
    	B [j]=L[j+a];
  	msort(A,a, e_size,comp);
  	msort(B,b,e_size,comp);
  	merge(A,a,B,b,L, comp);
  	free(A);
  	free(B);
}

*/
void quicksor(void *A, int n, int e_size, compare comp)
{
	int pivot;

}
int main()
{	
	int size = 10;
	point* pares[SIZE] = {newpoint(0,0), newpoint(1, 5), newpoint(0, 10), 
		newpoint(-20, 30), newpoint(-10097, 656), newpoint(123, 876), newpoint(1, 1),
		newpoint(0, 89), newpoint(127, 987), newpoint(5, 5)};
	point* otro_y[SIZE];
	copia(pares,size,otro_y);
	qsort(pares,size,sizeof(point),compareX);
	int i;
	int arrr[] = {-1,-5,11,23,322,0,12,13,14,15,67,121};
	int s = 12;
	qsort(arrr,sizeof(arrr)/sizeof(*arrr),sizeof(int),cmp);
	for(i=0;i<size;i++)
		printf("%d , ", arrr[i]);
	putchar('\n');
	pairs* min_x = closest(pares, size);
	pairs* min_y = closest(pares, size);
	//pairs* min_abs =  min_x->dist < min_y->dist ? min_x : min_y;
	//printpairs (min_abs);
	puts("\n");
	return 0;
}
