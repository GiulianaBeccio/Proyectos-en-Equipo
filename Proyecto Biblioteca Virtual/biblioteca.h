//Librerias
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <conio.h>
#include <unistd.h>
#include <time.h>

//Constante
#define MAXAUTOR 3


//Inicio Estructura

typedef struct {
	Lista lista;
}Biblioteca;

//Fin Estructura


//Inicio Funciones de Biblioteca

//Crea la biblioteca
Biblioteca crearBibli(Biblioteca * bibli)
{
	bibli->lista = crearLista();
	return *bibli;
}

//Para parar la ejecucion hasta que el usuario presione una tecla
void pausa()
{
    printf("\n\n Presione enter para continuar... ");
    getch();
    system("cls");
}

void delay_print(char *str) //para el efecto del mensaje de bienvenida
{
    struct timespec ts = {0, 1000000};
    for (char *p = str; *p; p++)
    {
        putchar(*p);
        fflush(stdout);
        nanosleep(&ts, NULL);
    }
    putchar('\n');
}

void bienvenidaMsj()
{

    printf("\n");
    delay_print("****************************************************\n");
    delay_print("*                                                  *\n");
    delay_print("*      >>>>>  Bienvenidos a la Biblioteca  <<<<<   *\n");
    delay_print("*                                                  *\n");
    delay_print("****************************************************\n");

    printf("\n");
    pausa();
}

void mostrarMensaje(char * msg)
{
	printf("\n*** %s ***\n",msg);
}


//Muestra Biblioteca COMPLETA
void mostrarBiblio(Biblioteca b)
{
    const char *rosaPastel = "\033[38;2;255;182;193m";
    const char *normal = "\033[0m";
	int i;
	int c=196; //para el ascii
	printf("\n");
	for (i = 1; i <139;i++)
	    printf("%c",c);
	printf("\n%s| %-30s | %-20s | %-8s | %-10s | %-8s | %-25s | %-15s |%s\n",rosaPastel, "Titulo", "Autor", "C. Pag", "ISBN", "Anio", "Editorial", "Genero",normal);
	for (i = 1; i <139;i++)
	    printf("%c",c);
	printf("\n");
	mostrarLista(b.lista,1);
	for (i = 1; i <139;i++)
	    printf("%c",c);
	printf("\n");
}


//Inicio Ingreso de Campos (Para busqueda)

T getAutor()
{
    T lib;
    printf("\n Ingrese el Autor a buscar: ");
    fflush(stdin);
    gets(lib.autor[0].NomCompleto);

    return lib;
}

T getEditorial()
{
    T lib;
    printf("\n Ingrese la Editorial a buscar: ");
    fflush(stdin);
    gets(lib.editorial);

    return lib;
}

T getAnio()
{
    T lib;
    printf("\n Ingrese el Anio a buscar: ");
    scanf("%d",&lib.anio);

    return lib;
}

T getGenero()
{
    T lib;
    printf("\n Ingrese el Genero a buscar: ");
    fflush(stdin);
    gets(lib.genero);

    return lib;
}

//Fin Ingreso de Campos (para busqueda)


//Inicio de Funciones del Apartado 5: Listados
void mostrarListaEditoriales(Biblioteca bibli)
{
    Biblioteca aux;
    int encontrado;
    T libAux = getEditorial(bibli);
    aux.lista = copiarLista(bibli.lista);

    encontrado = buscar(&(aux.lista),libAux,4);
    if(encontrado)
    {
        mostrarEncabezado();
        while(encontrado)
        {
            printf(toString((verActual(aux.lista)),1));
            eliminar(&(aux.lista),libAux,4);
            encontrado = buscar(&(aux.lista),libAux,4);
        }
        mostrarPiso();
    }else{
        printf("Editorial no encontrada");
        pausa();
    }
}

void mostrarListaEdiPorAnio(Biblioteca bibli)
{
    Biblioteca aux;
    int encontrado;
    int a1,a2;
    int c =175;
    int libEncontrado=0;
    int anio_actual;
    T libAux = getEditorial(bibli);
    aux.lista = copiarLista(bibli.lista);
    encontrado = buscar(&(aux.lista),libAux,4);
    do {
        printf("\t%c Ingrese rango de anios en formato: aaaa-aaaa: ",c);
        if (scanf("%d-%d", &a1, &a2) != 2 || a1 > a2) {
            printf("\n Rango invalido. Asegurese de que el formato sea correcto y que el primer anio sea menor o igual al segundo.\n");
            while (getchar() != '\n');
        } else {
            break;
        }
    } while (1);
    if(encontrado)
    {
        mostrarEncabezado();
        while(encontrado)
        {
            int anio_actual = aux.lista.actual->dato.anio;
            if ((a1 <= anio_actual) && (anio_actual <= a2))
            {

                printf(toString((verActual(aux.lista)),1));
                libEncontrado=1;
            }
            eliminar(&(aux.lista),libAux,4);
            encontrado = buscar(&(aux.lista),libAux,4);
        }
        mostrarPiso();
         if (!libEncontrado)
            {
                 mostrarMensaje("No se encontraron libros en el rango de años especificado.");
            }
    }else{
        mostrarMensaje("Editorial no encontrada");

    }

}

void mostrarListaGenero(Biblioteca bibli)
{
    Biblioteca aux;
    int encontrado;
    T libAux = getGenero(bibli);
    aux.lista = copiarLista(bibli.lista);

    encontrado = buscar(&(aux.lista),libAux,5);
    if(encontrado)
    {
        mostrarEncabezado();
        while(encontrado)
        {
             printf(toString((verActual(aux.lista)),1));
            eliminar(&(aux.lista),libAux,5);
            encontrado = buscar(&(aux.lista),libAux,5);
        }
        mostrarPiso();
    }else{
        mostrarMensaje("Genero no encontrado");
        pausa();
    }
}

void mostrarListaAutores(Biblioteca bibli)
{
    Biblioteca aux;
    int encontrado;
    T libAux = getAutor(bibli);
    aux.lista = copiarLista(bibli.lista);

    encontrado = buscar(&(aux.lista),libAux,2);
    if(encontrado)
    {
        mostrarEncabezado();
        while(encontrado)
        {
            printf(toString((verActual(aux.lista)),1));
            eliminar(&(aux.lista),libAux,2);
            encontrado = buscar(&(aux.lista),libAux,2);
        }
        mostrarPiso();
    }else{
        mostrarMensaje("Autor no encontrado");
        pausa();
    }
}

void mostrarListaAnio(Biblioteca bibli)
{
    Biblioteca aux;
    int encontrado;
    T libAux = getAnio(bibli);
    aux.lista = copiarLista(bibli.lista);

    encontrado = buscar(&(aux.lista),libAux,6);
    if(encontrado)
    {
        mostrarEncabezado();
        while(encontrado)
        {
            printf(toString((verActual(aux.lista)),1));
            eliminar(&(aux.lista),libAux,6);
            encontrado = buscar(&(aux.lista),libAux,6);
        }
        mostrarPiso();
    }else{
        mostrarMensaje("Anio no encontrado");
        pausa();
    }
}

//Listar Autores Por una determinada Editorial
void autoresEditorial(Biblioteca bibli)
{
    Biblioteca aux;
    int encontrado;
    T libAux = getEditorial(bibli);
    aux.lista = copiarLista(bibli.lista);

    encontrado = buscar(&(aux.lista),libAux,4);
    if(encontrado)
    {
        printf("\nAutor/es: ");
        while(encontrado)
        {
            printf("\n\t %s",aux.lista.actual->dato.autor[0].NomCompleto);
            printf("\n");
            eliminar(&(aux.lista),libAux,4);
            encontrado = buscar(&(aux.lista),libAux,4);
        }
    }else{
        mostrarMensaje("Editorial no encontrada");
        pausa();
    }

}

//Ingresar una palabra y buscarla en el titulo
void mostrarLibroPorPalabra(Biblioteca bibli)
{
    Biblioteca aux;
    char palabra[50];
    int encontrado=0;
    printf("Ingrese la palabra a buscar en los titulos (tenga en cuenta las mayusculas y minusculas): ");
    fflush(stdin);
    gets(palabra);
    aux.lista = copiarLista(bibli.lista);
    setPrimero(&(aux.lista));
    mostrarEncabezado();
    while (!esListaVacia(aux.lista) && !esUltimo(aux.lista))
    {
        T libro_actual = verActual(aux.lista);
        if (strstr(libro_actual.titulo, palabra) != NULL)
        {
            printf(toString((verActual(aux.lista)),1));
            encontrado = 1;
        }

        siguiente(&(aux.lista));
    }
    mostrarPiso();
    if (!encontrado)
            {
                 mostrarMensaje("No se encontraron libros con la palabra en el titulo");
            }

}

//Mostrar Todos los autores que comienzan por una determinada Letra
void mostrarAutoresLetraInicial(Biblioteca bibli)
{
    Biblioteca aux;
    char l;

    int encontrado = 0;
    printf("Ingrese letra a buscar (mayusculas y minusculas se distinguen):");
    fflush(stdin);
    scanf(" %c", &l);
    aux.lista = copiarLista(bibli.lista);
    setPrimero(&(aux.lista));

    while (!esListaVacia(aux.lista) && !esUltimo(aux.lista))
    {
        T libro_actual = verActual(aux.lista);
        for (int i = 0; i < MAXAUTOR; i++)
        {
            if (libro_actual.autor[i].Apellido[0] == l)
            {
                if (!encontrado)
                {
                    mostrarEncabezado();
                    encontrado = 1;
                }
                printf("| %-30s | %-20s | %-8d | %-10d | %-8d | %-25s | %-15s |\n", libro_actual.titulo, libro_actual.autor[i].NomCompleto,
                       libro_actual.cantPaginas, libro_actual.ISBN, libro_actual.anio, libro_actual.editorial, libro_actual.genero);
                break;
            }
        }

        siguiente(&(aux.lista));
    }

    if (encontrado)
    {
        mostrarPiso();
    }
    else
    {
        mostrarMensaje("No se encontraron autores que comiencen con esa letra.");
    }

    pausa();
}

//Fin Funciones del Apartado 5: Listados

//Coloca en la lista todos los valores guardados en el archivo
void levantarBibli(Biblioteca * bibli)
{
	bibli->lista = crearLista();
	archivoAlista("biblioteca.dat", &(bibli->lista));
}

//Guarda en el archivo los cambios del programa
void guardarBibli(Biblioteca bibli)
{
    listaAarchivo("biblioteca.dat",bibli.lista);
}

//Fin Funciones de Biblioteca
