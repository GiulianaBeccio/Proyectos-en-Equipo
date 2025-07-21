
# Proyecto Transacciones

Un archivo de transacciones, transacciones.txt, contiene líneas con campos de ancho fijo. Cada
línea contiene:
- 22 dígitos que corresponden a un CBU de origen
- 22 dígitos que corresponden al CBU destino
- 10 dígitos que corresponden al monto transferido en centavos. 
El monto no puede ser negativo ni cero.

### Ejemplo
La siguiente linea:
285059094009041813520128505909400904181352020000052395

Se lee como:
desde el cbu 2850590940090418135201 al cbu 2850590940090418135202 se transfirieron $523.95

#
Un CBU está compuesto por:
- 3 dígitos que corresponden a la entidad bancaria
- 4 dígitos que corresponden a la sucursal
- 1 dígito verificador (que ignoraremos)
- 13 dígitos del número de cuenta
- 1 dígito verificador de cuenta (que ignoraremos).

### Archivo Binario
Otro archivo de tipo binario, cuentas.dat, contiene datos en el formato Cuenta que representan una
cuenta bancaria.
#
El programa lee el archivo transacciones.txt, usando el tipoTransaccion y actualiza
los registros almacenados en el archivo cuentas.dat. La cantidad a transferir es descontada de
la cuenta de origen y acumulada en la cuenta destino. Al finalizar el proceso de actualización, se 
muestra el valor total de las transferencias realizadas.



