//Librerias
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <conio.h>
//Archivo de Funciones
#include "funciones.h"

int main()
{
    //Estructuras definidas en typedef.h
    C c;
    Ct ct;
    Ttr Tr;

    //Archivos
    FILE *fp;   //Archivo de tipo texto (.txt)
    FILE *fp2;  //Archivo binario (.dat)

    //Set valor de cuentas
    cuentaValores(c);

    //Apertura de archivos
    verificarApertura(&fp,&fp2);

    //Inicio Transferencia
    transferencia(fp,fp2,c,Tr,ct);

    //Cuanto se transfirio en total
    cantTransferido(fp,Tr);
    fclose(fp);
    fclose(fp2);
    return 0;
}
