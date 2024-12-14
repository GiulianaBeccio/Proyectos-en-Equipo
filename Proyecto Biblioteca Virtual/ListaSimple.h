//Este archivo continene las Funciones y Estructuras para usar Listas

//Librerias
#include <stdio.h>
#include <stdlib.h>


//Inicio Estructuras
typedef int boolean;

typedef struct nodo {
	T dato;
	struct nodo * prox;
} Nodo;

typedef struct {
	Nodo * com;
	Nodo * actual;
	int cantidad;
}Lista;
//Fin Estructuras


//Funciones de Listas

Lista crearLista()
{
	Lista * l = (Lista *) malloc(sizeof(Lista));
	Nodo * cab = (Nodo *)malloc(sizeof(Nodo));
	Nodo * cen = (Nodo *) malloc (sizeof(Nodo));
	l->com = cab;
	l->actual = cab;
	cab->prox = cen;
	cen->prox = NULL;
	l->cantidad = 0;
	return *l;
}
void setPrimero(Lista * l)
{
	 l->actual = l->com->prox;
}
boolean esListaVacia(Lista l)
{
	return l.cantidad == 0;
}
void siguiente (Lista * l)
{
	l->actual = l->actual->prox;
}
boolean esUltimo(Lista l)
{
	return l.actual->prox == NULL;
}

T verActual(Lista l)
{
	return l.actual->dato;
}
void anterior (Lista * l)
{
	Nodo * p = l->com;
	while (p->prox != l->actual)
	{
		p = p->prox;
	}
	l->actual = p;
}

void mostrarLista(Lista l, int op)
{
    Nodo * p = l.com->prox;
    while (p->prox != NULL)
    {
        printf("%s", toString((p->dato), op));
        p = p->prox;
    }
}
void insertarDespues(Lista * l, T x)
{
	Nodo * aux = (Nodo*)malloc(sizeof(Nodo));
	aux->dato = x;
	aux->prox = l->actual->prox;
	l->actual->prox = aux;
	l->actual = aux;
	(l->cantidad)++;
}

void insertarEnOrden(Lista * l, T x)
{
	T y;
    Nodo * aux;
	aux->dato = x;
	Nodo * ant = l->com;
	l->actual = l->com->prox;
	while (l->actual->prox != NULL)
	{
		y = l->actual->dato;
		if (compara(x, y,1) > 0)
		{
		  	   ant = l->actual;
		  	   l->actual = l->actual->prox;
		}
		else
		   break;
	}
	aux->prox = l->actual;
	ant->prox = aux;
	l->actual = aux;
	(l->cantidad)++;
}
Nodo * getActual(Lista l)
{
	return l.actual;
}
void setActual (Lista * l, Nodo * p)
{
	l->actual = p;
}
void vaciarLista (Lista * l)
{
	Nodo * p = l->com;
	Nodo * aux;

	while ( l->cantidad > 0)
	{
		aux = p->prox;
		(l->cantidad)--;
		p->prox = aux->prox;
		free(aux);
	}
}
void listaAarchivo(char * arch, Lista l)
{
	FILE * fp = fopen(arch,"wb");
	Nodo * p = l.com->prox;
	T  dato;
	while (p->prox != NULL)
	{
		dato =  p->dato;
		fwrite(&dato,sizeof(T),1,fp);
		p = p->prox;
	}
	fclose(fp);
}
void eliminar(Lista *l, T x, int op)
{
	Nodo * ant = l->com;
	l->actual = l->com->prox;
	while (l->actual->prox != NULL)
	{
		if (compara(x, l->actual->dato,op)!= 0)
		{
			ant = l->actual;
			l->actual = l->actual->prox;
		}
        else
		   break;
	}
	ant->prox = l->actual->prox;
	free(l->actual);
	l->actual = ant;
	(l->cantidad)--;
}

void archivoAlista(char * arch, Lista * l)
{
	FILE * farch = fopen(arch, "rb");

	if (farch == NULL)
	{
		printf("error, archivo no existe");
	} else {
	//printf("Leyendo informacion...");
	T dato;
	rewind(farch);
	l->actual = l->com;
	while (fread(&dato,sizeof(T), 1, farch)== 1)
	{
		insertarDespues(l, dato);
	}
	//printf("fin\n");
	fclose(farch);
    }
}
boolean buscar(Lista *l, T x, int operacion)
{
	int encontrado = 0;
	Nodo * p = l->actual;
	l->actual = l->com->prox;
	while (l->actual->prox != NULL)
	{
		if (compara(l->actual->dato, x,operacion) == 0)
		{
			encontrado = 1;
			break;
		}
		l->actual = l->actual->prox;
	}
	if (!encontrado)
	    l->actual = p;
	return encontrado;
}
int getCantidad(Lista l)
{
	return l.cantidad;
}

//Funciones Propias

Lista copiarLista(Lista original) {
    Lista copia = crearLista();
    Nodo *p = original.com->prox;

    while (p->prox != NULL) {
        insertarDespues(&copia, p->dato);
        p = p->prox;
    }
    return copia;
}



