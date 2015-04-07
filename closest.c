#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include <math.h>
#include <float.h>

typedef struct{
	long double x;
	long double y;
}point;

typedef struct 
{
	point* p1;
	point* p2;
	long double dist;
}pairs;

point* newpoint(long double x, long double y)
{
	point* p = (point*) malloc(sizeof(point));
	p->x = x;
	p->y = y;
	return p;
}

pairs* newpairs(point p1, point p2, int dist)
{
	pairs* close = (pairs*)malloc(sizeof(pairs));
	close->p1 = p1;
	close->p2 = p2;
	close->dist = dist;
	return close;
}


int compareX(void* x1, void* x2)
{
	point *p1 = (point*)x1;
	point *p2 = (point*)x2;
	return p1->x - p2->x;
}
int compareY(void* y1, void* y2)
{
	point* p1 = (point*)y1;
	point* p2 = (point*)y2;
	return p1->x - p2->y;
}
long double square(long double x)
{
	return x * x;
}
long double distance(point* p1, point* p2)
{
	return sqrt(square(p1->x - p2->x) + square(p1->y - p2->y));
}
void printpoint(point p1)
{
	printf("( %llf, %llf) ",p1.x,p1.y);
}
void printpairs(pairs* par)
{
	printpoint(par->p1);
	printf("&&");
	printpoint(par->p2);
	printf("\nSu distancia es %d",par->dist);
}

pairs* bforce(point P[], int size)
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
		for(j= 0; j < size; j++)
		{
			long double dist = distance(P[i],P[j]);
			if(distance(dist < delta)
			{
				min1 =  *(P+i);
				min2 = *(P+j);
				delta = dist;
			}
		}
	}
	pairs* closest = newpairs(min1,min2,delta);
	return closest;
}


pairs* closest(point* P[],int size)
{
	if(size <= 3)
		return bforce(P, size);

	int lvertical = (int) size/2;
	pairs* CL = closest(P,Y,lvertical);
	pairs* CR = closest(P+lvertical,Y+lvertical,size -lvertical);
	if(CL->dist < CR->dist)
		return CL;
	else
		return CR;
}

point* copia(point* arr[], int size) {
	point* nuevo[size];
	int i;
	for (i = 0; i < size; i++) {
		nuevo[i] = arr[i];
	}
	return nuevo;
}
int main(int argc, char const *argv[])
{	
	point* pares[100];
	int size = 100 
	srand(time(NULL));
	int i;
	for (i = 0; i < size; i++) {
		int x = rand();
		int y = rand();
		pares[i] = newpoint(x, y);
	}
	otro_y = copia(pares, size)
	qsort(pares,size,sizeof(point), compareX);
	qsort(pares,size,sizeof(point), compareY);
	pairs* min_x = closest(pares, size);
	pairs* min_y = closest(pares, size);
	pairs* min_abs =  min_x->dist < min_y->dist ? min_x : min_y;
	printpairs (min_abs);
	return 0;
}
