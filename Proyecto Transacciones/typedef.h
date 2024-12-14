//Librerias
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <conio.h>

//Constantes
#define STR_NOMBRE_MAX 50
#define MAX_TRANSACCION 50
#define MAX_TEXTO 200
#define MAX_CANT 3
#define MAX_ENT 3
#define MAX_SUC 4
#define MAX_CUENTA 13
#define MAX_CARACTERES 23
#define TOTALTRANSFER 3

//Inicio Estructuras
typedef struct {
    int entidad;
    int sucursal;
    long long int cuenta;
}Cbu;

typedef struct {
    Cbu cbu;
    long long int saldo;
    char nombre[STR_NOMBRE_MAX];
}Cuenta;

typedef struct {
    Cbu cbu_origen;
    Cbu cbu_destino;
    long long int monto;
}Transaccion;

typedef struct {
    char entidad[MAX_ENT+1];
    char sucursal[MAX_SUC+1];
    char cuenta[MAX_CUENTA+1];
}Cbuc;

typedef struct {
    char cbu_origen[MAX_CARACTERES];
    char cbu_destino[MAX_CARACTERES];
}Cad_Transaccion;

typedef Transaccion Ttr[TOTALTRANSFER];
typedef Cad_Transaccion Ct[TOTALTRANSFER];
typedef Cuenta C[TOTALTRANSFER];

//Fin Estructuras
