//Librerias
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

//Abstraccion de Estructuras y Funciones
#include "libro.h"
//Definicion de "Libro"
typedef Libro T;
#include "listaSimple.h"
#include "biblioteca.h"
#include "menu.h"


//Al correr el programa es recomendable usar pantalla completa para mejor visualizacion de los Datos
//Recuerde que para que se guarden los cambios se debe terminar la ejecucion, en caso contrario las modificaciones se perderan

int main()

{
    FILE *fp;
    Biblioteca bibli;

    //Crea una Lista (Biblioteca)
    bibli = crearBibli(&bibli);

    //Coloca en la lista todos los valores guardados en el archivo
    levantarBibli(&bibli);

    //Seccion de Menu
    selecccionMenu(&bibli);

    //Guarda en el archivo los cambios del programa
    guardarBibli(bibli);


    return 0;
}
