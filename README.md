# Información Bancaria
El Banco que lo contrato la semana pasada ha decidido mejorar sus procesos para lo cual
tiene en mente un sistema de administración con interfaz de clientes basado en **listas
simples enlazadas**.

## Equipo de trabajo
- [Pablo José Hernández meléndez](https://github.com/pablohernandezm)
- [Jhoy Luis Castro Casanova](https://www.linkedin.com/in/jhoy-luis-castro-casanova-061142249/)

## Entorno de desarrollo
El proyecto fue realizado utilizando en conjunto las herramientas:
- Apache Netbeans IDE 19.0.0
- IntelliJ IDEA 2023.2.2 (Ultimate Edition)
- 
Además de la versión 21 de Java (openjdk version "21" 2023-09-19 LTS).

## Requerimientos
- Calcular el número de clientes.
- Total de capital depositado.
- Total de intereses a pagar.
- Adicionar nuevos usuarios (dos usuarios no pueden tener el mismo ID).
- Dar de baja a usuarios (el usuario se retira de banco, así como su capital).
- Actualizar usuarios (el usuario quiere aumentar el capital).
- Buscar usuarios (por ID).
- Salvar la lista en un archivo.

### Datos del cliente
- Cédula.
- Capital.
- El día del calendario (año de 360 días) en que el capital fue depositado al banco.

### Consideraciones
El interés se calcula mediante la ecuación: $\frac{K*i}{100}+\frac{360-M}{360}$.
Donde:
- $K$ es el capital.
- $i$ es la tasa de interés.
- $M$ es el día del calendario en que el capital fue depositado al banco.

## Entrada
- El archivo de entrada contiene una lista de clientes, cada uno de ellos en una línea
separada.
- Cada cliente tiene una cédula, capital, día en el cual el capital fue depositado en
el banco, y el interés a aplicar.
- Una línea cuyo carácter es un punto (.) significa el final del archivo (esta última línea no se
procesa).
- El archivo de entrada debe ser leído en el archivo clientes.in.
- El archivo de salida debe ser escrito en el archivo clientes.in.

## El menú
INFORMACIÓN BANCARIA
1. Número de clientes ✅
2. Total de capital depositado ✅
3. Total intereses a pagar ✅
4. Adicionar nuevo usuario ✅
5. Dar de baja a usuarios ✅
6. Actualizar usuarios ✅
7. Buscar usuarios (por ID) ✅
8. Listar los usuarios ✅
9. Guardar lista en un archivo ✅
10. Cargar lista desde un archivo ✅
11. Salir ✅

Seleccione una opción (1 - 11):

##  Salida
La salida debe imprimir los datos correspondientes en consola. ✅

## Adicional
Modele el conjunto de clases que representa la solución lógica del requerimiento del usuario.