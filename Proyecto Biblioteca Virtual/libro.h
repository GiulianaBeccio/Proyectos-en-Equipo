//Librerias
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

//Constantes
#define MAXSTR 50
#define MAXAUTOR 3

//Inicio Estructuras

typedef char String[MAXSTR];
typedef struct {
    String Nombre;
    String Apellido;
    String NomCompleto;
} Autor;

typedef struct {
    String titulo;
    Autor autor[MAXAUTOR];
    int cantPaginas;
    int ISBN;
    int anio;
    String editorial;
    String genero;
}Libro;

typedef Libro T;

//Fin Estructuras


//Inicio Funciones de Libro

//Para Mostrar Strings en pantalla con Formato
char * toString(Libro a, int op)
{
    char * aux = (char *)malloc(2048);
    int i;
    int c=196;

     char autores[512] = "";
    for (i = 0; i < MAXAUTOR; i++) {
        if (strlen(a.autor[i].NomCompleto) > 0) {
            strcat(autores, a.autor[i].NomCompleto);
            if (i < MAXAUTOR - 1 && strlen(a.autor[i + 1].NomCompleto) > 1) {
                strcat(autores, ", ");
            }
        }
    }


    switch (op) {
        case 1:
            sprintf(aux, "| %-30s | %-20s | %-8d | %-10d | %-8d | %-25s | %-15s |\n",
                    a.titulo, autores, a.cantPaginas, a.ISBN, a.anio, a.editorial, a.genero);
            break;

        case 2:
                printf("\n");
                for (i = 0; i < MAXAUTOR; i++){
                    if(strcmp(a.autor[i].NomCompleto,"\0"))
                        sprintf(aux,"|%s", a.autor[i].NomCompleto);
                    else
                        sprintf(aux," ");
                    printf("%s", aux);
                }
                printf("\n");
                break;

        case 3: for (i = 1; i <13;i++)
                    printf("%c",c);
                sprintf(aux,"| %-20s |\n", a.genero);
                for (i = 1; i <13;i++)
                    printf("%c",c);
                printf("\n");
                break;


    }

    return aux;
}


//Para Mostrar solo UN Libro
void mostrarLibro(Libro l)
{
    int i;
    int c=196; //para el ascii
    for (i = 1; i < 139; i++)
        printf("%c",c);
    printf("\n| %-30s | %-20s | %-8s | %-10s | %-8s | %-25s | %-15s |\n",
           "Titulo", "Autor(es)", "C. Pag", "ISBN", "Anio", "Editorial", "Genero");
    for (i = 1; i < 139; i++)
        printf("%c",c);
    printf("\n");
    printf("%s", toString(l, 1));
    for (i = 1; i < 139; i++)
        printf("%c",c);
    printf("\n");
}

//Encabezado del Listado (Cuadro)
void mostrarEncabezado()
{

    int i;
    int c=196; //para el ascii
    for (i = 1; i <139;i++)
	    printf("%c",c);
	printf("\n| %-30s | %-20s | %-8s | %-10s | %-8s | %-25s | %-15s |\n", "Titulo", "Autor", "C. Pag", "ISBN", "Anio", "Editorial", "Genero");
	for (i = 1; i <139;i++)
	    printf("%c",c);
	printf("\n");

}

//Funcion de cuadro (Lineas Punteadas)
void mostrarPiso()
{
    int i;
    int c=196; //para el ascii
    for (i = 1; i <139;i++)
	    printf("%c",c);
	printf("\n");
}

//Comparacion entre dos Libros
//El numero de operacion determina que se compara del libro
int compara(Libro a, Libro b, int operacion)
{
    switch(operacion)
    {
        case 1: return a.ISBN - b.ISBN;
                break;

        case 2: return strcmp(a.autor[0].NomCompleto,b.autor[0].NomCompleto);
                break;

        case 3: return strcmp(a.titulo,b.titulo);
                break;

        case 4: return strcmp(a.editorial,b.editorial);
                break;

        case 5: return strcmp(a.genero,b.genero);
                break;

        case 6: return a.anio - b.anio;
                break;
    }
}

//Ingresar Nuevo Libro
T ingresarLibro()
{
    T lib;
    int band = 1;
    int i = 0;
	printf("\n Ingrese titulo: ");
	fflush(stdin);
	gets(lib.titulo);
	while(band == 1 && i < MAXAUTOR)
    {
        printf("\n Ingrese Apellido del autor: ");
        fflush(stdin);
        gets(lib.autor[i].Apellido);
         printf("\n Ingrese Nombre del autor: ");
        fflush(stdin);
        gets(lib.autor[i].Nombre);
        snprintf(lib.autor[i].NomCompleto, sizeof(lib.autor[i].NomCompleto), "%s %s", lib.autor[i].Nombre, lib.autor[i].Apellido);
        printf("\n Desea ingresar otro autor? (1 para Si / 0 para No): ");
        scanf("%d",&band);
        i++;
    }
    printf("\n Ingresar cantidad de paginas: ");
    scanf("%d",&lib.cantPaginas);

    printf("\n Ingresar ISBN: ");
    scanf("%d",&lib.ISBN);

    printf("\n Ingresar anio de edicion: ");
    scanf("%d",&lib.anio);

	printf("\n Ingrese editorial: ");
	fflush(stdin);
	gets(lib.editorial);

	printf("\n Ingrese genero: ");
	fflush(stdin);
	gets(lib.genero);

	return lib;
}

//Fin Funciones de Libro
