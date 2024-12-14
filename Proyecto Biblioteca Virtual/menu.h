//Librerias
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <conio.h>


//Inicio Funciones de Menu

void alta(Biblioteca *bibli, int op)
{
    T nuevoLib;
    nuevoLib = ingresarLibro();
    if (buscar(&(bibli->lista),nuevoLib,op))
        mostrarMensaje("Error: alta existente.");
    else
    {
        insertarDespues(&(bibli->lista),nuevoLib);
        mostrarMensaje("Alta exitosa.");
    }
    pausa();
}


void baja(Biblioteca *bibli)
{
    int resp, baja2,op,confirma;
    T bajaLib;
    do{
        printf("\n Desea ingresar el libro por Titulo (1) o por ISBN (0)?");
        printf("\n Ingrese respuesta: ");
        scanf("%d",&resp);
    }while((resp != 1) && (resp != 0));

    if(resp)
    {
        op = 3;
        printf("\n Ingrese el Titulo: ");
        fflush(stdin);
	    gets(bajaLib.titulo);
    }else{
        op = 1;
        printf("\n Ingrese el ISBN: ");
        scanf("%d",&bajaLib.ISBN);
    }

    if (buscar(&(bibli->lista),bajaLib,op))
    {
        printf("\n\n Esta seguro que quiere eliminar este libro?\n");
        mostrarLibro(verActual(bibli->lista));
        printf("\n En caso afirmativo ingrese 1, de lo contrario ingrese 0: ");
        scanf("%d",&confirma);
        if(confirma){
            eliminar(&(bibli->lista),bajaLib,op);
            mostrarMensaje("Baja exitosa.");
        }
        pausa();
    }else
    {
        printf("***Error: No se encontro el Libro.***");
        printf("\n\n Desea reiniciar la baja?");
        printf("\n En caso afirmativo ingrese 1 y sera derivado a Baja, de lo contrario ingrese 0.");
        printf("\n\n Respuesta: ");
        scanf("%d",&baja2);
        if(baja2)
        {
            system("cls");
            baja(bibli);
        }
        system("cls");
    }

}

//Consultar la existencia de un Libro
void consulta(Biblioteca *bibli, int op)
{

    T consulta;
    int resp;
    printf("\n Este apartado es para consultar la existencia de un libro en la Biblioteca.\n");
    mostrarMensaje("IMPORTANTE");
    printf("\n Recuerde que el sistema discrimina si usted no pone las mayusculas correspondientes.");
    printf("\n\n Ingrese el Titulo a buscar: ");
    fflush(stdin);
	gets(consulta.titulo);
	system("cls");
	printf("\n\n Titulo: %s\n",consulta.titulo);
	if (buscar(&(bibli->lista),consulta,op))
    {
        mostrarMensaje("Titulo Encontrado");
        mostrarLibro(verActual(bibli->lista));
    }else{
        mostrarMensaje("Titulo no encontrado");
        printf("\n\n Desea ingresar el libro?");
        printf("\n En caso afirmativo ingrese 1 y sera derivado a Alta, de lo contrario ingrese 0.");
        printf("\n\n Respuesta: ");
        scanf("%d",&resp);
        if(resp){
            pausa();
            alta(bibli,op);
        }
    }
    pausa();
}

//Modificar libro POR CAMPO (ingresado por el usuario)
void modificar(Biblioteca *bibli, int op)
{
    int resp,confirma;
    T modLib;
    do{
        printf("\n Desea buscar el libro por Titulo (1) o por ISBN (0)?");
        printf("\n Ingrese respuesta: ");
        scanf("%d",&resp);
    }while((resp != 1) && (resp != 0));

    if(resp)
    {
        op = 3;
        printf("\n Ingrese el Titulo: ");
        fflush(stdin);
	    gets(modLib.titulo);
    }else{
        op = 1;
        printf("\n Ingrese el ISBN: ");
        scanf("%d",&modLib.ISBN);
    }
    if (buscar(&(bibli->lista),modLib,op))
    {
        printf("\n\n Esta seguro que quiere modificar este libro?\n");
        mostrarLibro(verActual(bibli->lista));
        printf("\n En caso afirmativo ingrese 1, de lo contrario ingrese 0: ");
        scanf("%d",&confirma);
        if(confirma){
            do{
                printf("\n-(1) Titulo\n-(2) Autor\n-(3) Cant. Paginas\n-(4) ISBN\n-(5) Anio\n-(6) Editorial\n-(7) Genero");
                printf("\n Ingrese el numero del campo a modificar: ");
                scanf("%d",&confirma);
            }while((confirma < 1) && (confirma > 7));
            switch(confirma)
            {
                case 1: printf("\n Ingrese el nuevo Titulo: ");
                        fflush(stdin);
                        gets(modLib.titulo);
                        strcpy((bibli->lista.actual->dato.titulo),(modLib.titulo));
                        break;

                case 2: printf("\n Ingrese Apellido del autor: ");
                        fflush(stdin);
                        gets(modLib.autor[0].Apellido);
                        printf("\n Ingrese Nombre del autor: ");
                        fflush(stdin);
                        gets(modLib.autor[0].Nombre);
                        snprintf(modLib.autor[0].NomCompleto, sizeof(modLib.autor[0].NomCompleto), "%s %s", modLib.autor[0].Nombre, modLib.autor[0].Apellido);
                        strcpy((bibli->lista.actual->dato.autor[0].NomCompleto),(modLib.autor[0].NomCompleto));
                        break;

                case 3: printf("\n Ingrese la nueva Cant. de Paginas: ");
                        scanf("%d",&modLib.cantPaginas);
                        bibli->lista.actual->dato.cantPaginas = modLib.cantPaginas;
                        break;

                case 4: printf("\n Ingrese el nuevo ISBN: ");
                        scanf("%d",&modLib.ISBN);
                        bibli->lista.actual->dato.ISBN = modLib.ISBN;
                        break;

                case 5: printf("\n Ingrese el nuevo Anio: ");
                        scanf("%d",&modLib.anio);
                        bibli->lista.actual->dato.anio = modLib.anio;
                        break;

                case 6: printf("\n Ingrese la nueva Editorial: ");
                        fflush(stdin);
                        gets(modLib.editorial);
                        strcpy((bibli->lista.actual->dato.editorial),(modLib.editorial));
                        break;

                case 7: printf("\n Ingrese el nuevo Genero: ");
                        fflush(stdin);
                        gets(modLib.genero);
                        strcpy((bibli->lista.actual->dato.genero),(modLib.genero));
                        break;

            }
            printf("***Modificacion Exitosa***");
        }
        pausa();

    }else{
        mostrarMensaje("No se encontro el Libro");
        pausa();
    }
}


//Funcion que ofrece diferentes variedades de formas de Listar las categorias de la Biblioteca
void listados(Biblioteca *bibli, int op)
{
    int cambia = 1,resp,i;
    int c=196; //para el ascii
    int c1=175;
    Nodo *p;
    do{
        do{
            printf("\n(1) Listar Libros\n(2) Listar Autores\n(3) Listar libros por Genero\n(4) Listar libros por Autor\n(5) Listar libros por Editorial\n(6) Listar libros por Editorial y rango de Anios");
            printf("\n(7) Listar autores por Editorial\n(8) Listar libros por Anio\n(9) Listar libros por primera letra de apellido de Autor\n(10) Listar libros por palabra");
            printf("\n Elija el listado a mostrar: ");
            scanf("%d",&resp);
            if((resp < 1) || (resp > 10))
            {
                mostrarMensaje("Valor Invalido");
                resp = 0;
                pausa();
            }
        }while(resp == 0);
        switch(resp)
        {
            case 1: for (i = 1; i <139;i++)
	                    printf("%c",c);
                    printf("\n| %-30s | %-20s | %-8s | %-10s | %-8s | %-25s | %-15s |\n", "Titulo", "Autor", "C. Pag", "ISBN", "Anio", "Editorial", "Genero");
                    for (i = 1; i <139;i++)
	                    printf("%c",c);
                    printf("\n");
                    mostrarLista((bibli->lista),resp);
                    for (i = 1; i <139;i++)
                        printf("%c",c);
                    printf("\n");
                    break;

            case 2: mostrarLista((bibli->lista),resp);
                    int i;
                    for (i = 1; i <25;i++)
                        printf("%c",c);
                    printf("\n");
                    break;

            case 3: mostrarListaGenero(*bibli);
                    break;

            case 4: mostrarListaAutores(*bibli);
                    break;

            case 5: mostrarListaEditoriales(*bibli);
                    break;

            case 6: mostrarListaEdiPorAnio(*bibli);
                    break;

            case 7: autoresEditorial(*bibli);
                    break;

            case 8: mostrarListaAnio(*bibli);
                    break;

            case 9: mostrarAutoresLetraInicial(*bibli);
                    break;

            case 10: mostrarLibroPorPalabra(*bibli);
                     break;
        }

        printf("\n Desea realizar otro listado?");
        printf("\n Ingrese 1 para Si y 0 para No: ");
        scanf("%d",&cambia);
        system("cls");

    }while(cambia);
    pausa();
}

//Seleccion de numero del Menu
int menu()
{
	int op;
    int c=175;//ascii
	do {
	   printf("\n 1.Alta\n 2.Baja\n 3.Consultar\n 4.Modificacion\n 5.Listados\n 6.Mostrar Biblioteca\n 7.Fin");
       printf("\n Ingrese su opcion %c ",c);
       scanf("%d",&op);
	}while (op <1 || op > 8);
	fflush(stdin);
    return op;
}

//Menu principal con Funciones Principales de Biblioteca
void selecccionMenu (Biblioteca *bibli){
    int op;
    bienvenidaMsj();
    do {
	    op = menu();
	    switch(op)
	    {
	    	case 1: alta(bibli,op);
	    	        break;

	    	case 2: baja(bibli);
	    	        break;

	    	case 3: consulta(bibli,op);
	    	        break;

	    	case 4: modificar(bibli,op);
	    	        break;

            case 5: listados(bibli,op);
                    break;

	    	case 6: mostrarBiblio(*bibli);
                    pausa();
	    	        break;
		}
    } while (op != 7);

}

//Fin Funciones de Menu
