//Archivo de Estructuras
#include "typedef.h"

//insertar una linea horizontal en codigo ASCII
void lineaHorizontal()
{
    printf("\n");
    char linea_horizontal = 196;
    for (int i = 0; i < 50; i++) printf("%c", linea_horizontal);
    printf("\n");
}

// Inicio Funciones de Archivos (Reutilizables)
int aperturaArch (FILE **archivo, char *nombre, char *modo)
{
    if((*archivo=fopen(nombre,modo))==NULL)
    {
        printf("ERROR: no se puede abrir el archivo");
        return 0;
    }
    return 1;
}


void verificarApertura(FILE **fp, FILE **fp2)
{
    if(!(aperturaArch(fp,"transacciones.txt","rt")))
        exit(0);
    if(!(aperturaArch(fp2,"cuenta.dat","w+b"))){
        fclose(*fp);
        exit(0);
    }
}


void EscribirArchivoB(FILE *fp, Cuenta *c)
{
    for (int i = 0; i < MAX_CANT; i++)
    {
        fwrite(&c[i], sizeof(Cuenta), 1, fp); // Escribir cada cuenta de manera individual
    }
}


void leerArchivoB(FILE *fp, Cuenta *c) {
    fseek(fp, 0L, SEEK_SET);
    lineaHorizontal();
    printf("\t      *** Datos de cuenta ***\t");
    lineaHorizontal();
    for (int i = 0; i < MAX_CANT; i++) {
        fread(&c[i], sizeof(Cuenta), 1, fp);
        printf("\nNombre: %s", c[i].nombre);
        printf("\nCBU: %d%d%lld", c[i].cbu.entidad, c[i].cbu.sucursal, c[i].cbu.cuenta);
        printf("\nSaldo: $%.2llf", (double)c[i].saldo/100);
        lineaHorizontal();
    }
}

// Fin Funciones de Archivos


//Setear las cuentas en esta Funcion
Cuenta cuentaValores (Cuenta *c)
{
    c[0] = (Cuenta){{280, 4930, 4903859102953}, 99999, "Milagros Ruz"};
    c[1] = (Cuenta){{281, 4859, 4892015700125}, 591800, "Federico Amor"};
    c[2] = (Cuenta){{285, 8590, 4009041813520}, 598760, "Giuliana Beccio"};

}

//-------------------------------------------------------------------------------------------------


//Verifica que se puede extraer el monto del saldo (sin ser negativo)
int verSaldo(double saldo, double monto)
{
    if(saldo >= monto)
    {
        return 1;
    }
    return 0;
}

// Compara para comprobar si coinciden los datos de Transferencia y si hay saldo disponible
int compara(FILE *fp, Transaccion Tr[], Cuenta c[], int iTr)
{
    long pos;
    int band=0;
    int i=0;
    fseek(fp, 0L, SEEK_SET);
    while (fread(&c[i], sizeof(Cuenta), 1, fp) == 1)
    {
        if ((c[i].cbu.entidad == Tr[iTr].cbu_origen.entidad) && (c[i].cbu.sucursal == Tr[iTr].cbu_origen.sucursal) && (c[i].cbu.cuenta == Tr[iTr].cbu_origen.cuenta) && (band == 0))
        {
            double saldo = (double) c[i].saldo;
            double monto = (double) Tr[iTr].monto;

            if(verSaldo(saldo,monto))
            {
                c[i].saldo = c[i].saldo-Tr[iTr].monto;
                fseek(fp, -(long long)sizeof(Cuenta), SEEK_CUR);
                fwrite(&c[i], sizeof(Cuenta), 1, fp);
                band = 1;
            }
        }
        i++;
        if (band==1)
            {
                break;
            }
    }
    int k=0;
    fseek(fp, 0L, SEEK_SET);
    while (fread(&c[k], sizeof(Cuenta), 1, fp) == 1)
    {

        if((c[k].cbu.entidad == Tr[iTr].cbu_destino.entidad) && (c[k].cbu.sucursal == Tr[iTr].cbu_destino.sucursal) && (c[k].cbu.cuenta == Tr[iTr].cbu_destino.cuenta) && (band == 1))
        {
            c[k].saldo = c[k].saldo+Tr[iTr].monto;
            fseek(fp, -(long long)sizeof(Cuenta), SEEK_CUR);
            fwrite(&c[k], sizeof(Cuenta), 1, fp);
            return 1;
        }
        k++;
    }
    return 0;
}

// "Accion" de Transferir
void transferir (FILE *fp, Transaccion Tr[], Cuenta c[])
{
    int i;
    for (i=0;i<MAX_CANT;i++)
    {
        if (compara(fp,Tr, c,i))
        {
            printf("\nTransferencia %d Exitosa\n  ", i+1);
        }else {
            printf("Error: No se pudo realizar la transferencia\n");
        }
    }

    leerArchivoB(fp,c);
}

//Trata los datos para pasarlos de Char[] (String) a Entero
//Luego imprime por pantalla los valores de cada campo para luego pasar a transferir definitivamente
void tratar(FILE *fp,Cuenta c[], Transaccion Tr[],Cad_Transaccion ct[], FILE * fp2)
{

    char flecha=175; // flecha en codigo ASCII
    char linea[MAX_TRANSACCION + 5];
    int i = 0;
    char buffer[15];
    fseek(fp,0L,SEEK_SET);
    while (fgets(linea, sizeof(linea), fp) != NULL) {

        // CBU Origen
        strncpy(buffer, linea, 3);
        buffer[MAX_ENT] = '\0';
        strcpy(ct[i].cbu_origen,buffer);
        Tr[i].cbu_origen.entidad = atoi(buffer);
        strncpy(buffer, linea + 3, 4);
        buffer[MAX_SUC] = '\0';
        strcat(ct[i].cbu_origen,buffer);
        Tr[i].cbu_origen.sucursal = atoi(buffer);
        strncpy(buffer, linea + 7, 13);
        buffer[MAX_CUENTA] = '\0';
        strcat(ct[i].cbu_origen,buffer);
        Tr[i].cbu_origen.cuenta = atoll(buffer);



        // CBU Destino
        strncpy(buffer, linea + 20, 3);
        buffer[MAX_ENT] = '\0';
        strcpy(ct[i].cbu_destino,buffer);
        Tr[i].cbu_destino.entidad = atoi(buffer);
        strncpy(buffer, linea + 23, 4);
        buffer[MAX_SUC] = '\0';
        strcat(ct[i].cbu_destino,buffer);
        Tr[i].cbu_destino.sucursal = atoi(buffer);
        strncpy(buffer, linea + 27, 13);
        buffer[MAX_CUENTA] = '\0';
        strcat(ct[i].cbu_destino,buffer);
        Tr[i].cbu_destino.cuenta = atoll(buffer);


        // Monto
        strncpy(buffer, linea + 40, 10);
        buffer[10] = '\0';
        Tr[i].monto = (long long)(atof(buffer) / 100); // Convertir a centavos
        i++;


    }
    int k;
    for (int k = 0; k < MAX_CANT; k++) {
        //  El \t es para tabular un mensaje en consola
        lineaHorizontal();
        printf("\t       *** Transaccion %d ***\t", k + 1);
        lineaHorizontal();
        // Imprimir CBU Origen
        printf("CBU Origen: %s\n\n",ct[k].cbu_origen);
        printf("Datos CBU:\n\t   %c Entidad: %d\n\t   %c Sucursal: %d\n\t   %c Cuenta: %lld\n",
               flecha, Tr[k].cbu_origen.entidad, flecha, Tr[k].cbu_origen.sucursal,flecha, Tr[k].cbu_origen.cuenta);


        // Imprimir CBU Destino
        printf("\nCBU Destino: %s\n\n",ct[k].cbu_destino);
        printf("Datos CBU:\n\t   %c Entidad: %d\n\t   %c Sucursal: %d\n\t   %c Cuenta: %lld\n",
               flecha, Tr[k].cbu_destino.entidad,flecha, Tr[k].cbu_destino.sucursal, flecha,Tr[k].cbu_destino.cuenta);

        // Imprimir Monto
        printf("\nMonto: $%.2llf\n", (double) (Tr[k].monto/100));


    }
    printf("\n\n %c%c%c Presione enter para realizar las transacciones: ", flecha,flecha,flecha);
    getch(); //parar ejecucion del programa hasta que se presione una tecla
    system("cls");

    transferir(fp2, Tr, c);
}


// Funcion Principal de Transferencia
void transferencia(FILE *fp,FILE *fp2,Cuenta c[],Transaccion Tr[],Cad_Transaccion ct[])
{
    char flecha=175;
    EscribirArchivoB(fp2, c);
    leerArchivoB(fp2, c);       //leer binario

    printf("\n\n %c%c%c Presione enter para mostrar detalles de transacciones: ", flecha,flecha,flecha);
    getch(); //parar ejecucion del programa hasta que se presione una tecla
    system("cls"); //limpia pantalla

    tratar(fp,c,Tr,ct,fp2);
}

//Cuanto se transfirio en total
void cantTransferido(FILE *fp, Transaccion Tr[]) {
    fseek(fp, 0L, SEEK_SET);
    double total;
    printf("\t*** Cantidad Transferida En Total ***\n");

    for (int i = 0; i < MAX_CANT; i++) {
      total =  total + (double) Tr[i].monto/100;

    }
    printf("\nEl monto total de las transferencias es: $%.2f", total);
    lineaHorizontal();
}
