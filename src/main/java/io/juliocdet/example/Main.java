package io.juliocdet.example;

import io.juliocdet.example.interfaces.DateFormater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Main {

    private static List<Integer> numeros = Arrays.asList(1, 5, 7, 4, 1, 20, 6, 9, 8, 4, 1, 3, 29, 32, 51, 6, 99);

    public static void main(String[] args) {

        // Ejemplo sin uso de lambdas
        System.out.println("Array original: " + numeros);
        System.out.print("Ordenar numeros de menor a mayor solo los pares: ");
        // Creamos un nuevo objeto para no modificar el origina, por buena practica pero que tenga de inicio los mismos valores
        List<Integer> numerosOrdenados = new ArrayList<>(numeros);
        // Ordenamos los numero de menor a mayor
        Collections.sort(numerosOrdenados);
        List<Integer> numerosParesOrdenador = new ArrayList<>();
        for (int num : numerosOrdenados) {
            if (num % 2 == 0) {
                numerosParesOrdenador.add(num);
            }
        }
        // Mostramos todos los numeros
        for (int num : numerosParesOrdenador) {
            System.out.print(num + ", ");
        }
        System.out.println(); // Salto de linea

        /*--------------------------------------------------------------------------------------------------------------*/

        // Ejemplo de uso como se pueden declarar las intefacez para luego utilizarlo
        System.out.println("Array original: " + numeros);

        Predicate<Integer> filtrarIntPares = (num) -> (num % 2) == 0; // Declaramos un predicate el cual filtra los numero enteros por pares
        Consumer<Integer> mostrarNumIntegers = (num) -> System.out.print(num + ","); // Muestra separado por coma todos los numero enteros

        System.out.print("Ordenar numeros de menor a mayor solo los pares: ");
        numeros.stream() // Initia el flujo de Stream
                .sorted() // Ordenamos de menor a mayor los elementos
                .filter(filtrarIntPares) // solo dejamos pasar a los numero que sean pares
                .forEach(mostrarNumIntegers); // Imprime cada numero concadenado con ", "

        System.out.println(); // Salto de linea

        /*--------------------------------------------------------------------------------------------------------------*/

        // Tambien es posible pasar funciones que cumplan con la firma de la lambda requerida (Pasar Method references)
        // Ejemplo, si se necesita un Predicate, entonces nesitamos una funcion que reciba dos argumento del mismo tipo
        // que es el predicate necesario, y que retorne boolean. Siguiendo asi la implementacion de la Funtional Interface

        System.out.println("Array original: " + numeros);
        System.out.print("Ordenar numeros de mayor a menor solo impares: ");
        numeros.stream() // Initia el flujo de Stream
                .sorted((numAnterior, numActual) -> numActual - numAnterior) // Ordenamos de mayor a menor los elementos
                .filter(Main::sortIntImPares)
                .forEach(Main::imprimirNumeros);

        System.out.println(); // Salto de linea
        /*--------------------------------------------------------------------------------------------------------------*/

        // Ejemplo de uso como se pueden pasar lambdas sin necesidad de delcararla antes
        System.out.println("Array original: " + numeros);
        System.out.print("Ordenar numeros de mayor a menor: ");
        numeros.stream() // Initia el flujo de Stream
                .sorted((numAnterior, numActual) -> numActual - numAnterior) // Ordenamos de mayor a menor los elementos
                .forEach(num -> System.out.print(num + ", "));

        System.out.println(); // Salto de linea

        /*--------------------------------------------------------------------------------------------------------------*/

        // Funtional interfaces, lo que esta detras de las lambdas en Java
        // En este ejemplo en lugar de usar una de las interfaces, Predicate, Funtion, Consumer, y Supplier.
        // Voy a usar una interfaz funtional creada por mi, la cual para ser valida necesita recibir tres Strings
        // y un cuarto parametro de tipo boolean. Con el objetivo de ejemplificar el trasfondo de las Funtional Interfaces

        DateFormater format = (dia, mes, ano, flag) -> {
            if (Integer.parseInt(dia) > 31 || Integer.parseInt(dia) < 0) {
                return "Error en el dia";
            }

            if (Integer.parseInt(mes) > 12 || Integer.parseInt(mes) < 0) {
                return "Error en el mes";
            }

            if (Integer.parseInt(ano) < 0) {
                return "Error en el ano";
            }

            StringBuilder stringBuilder = new StringBuilder();
            if (flag) {
                stringBuilder.append("Informacion extra sobre la hora y fecha ");
            }
            stringBuilder.append(dia).append("/").append(mes).append("/").append(ano);
            return stringBuilder.toString();
        };

        System.out.println("El dia y mes es: " + format.apply("2", "3", "2021", false));
        System.out.println("El dia y mes es: " + format.apply("2", "3", "2021", true));
        System.out.println("El dia y mes es: " + format.apply("-1", "3", "2021", true));
        System.out.println("El dia y mes es: " + format.apply("2", "22", "2021", true));
        System.out.println("El dia y mes es: " + format.apply("2", "3", "-6", true));
        System.out.println("El dia y mes es: " + DateFormater.getDefaultDate());

        System.out.println(); // Salto de linea

        /*--------------------------------------------------------------------------------------------------------------*/

        // Ejemplo del uso de la clase Optional

        Optional<Integer> optionalPrimerNumeroEnteroDivisibleEntre3 = numeros.stream()
                .filter(num -> num % 3 == 0)
                .findFirst();

        // Podemos verificar atravez de un if si es que el optional tiene un valor y asi saber que hacer en ese caso
        if (optionalPrimerNumeroEnteroDivisibleEntre3.isPresent()) {
            // Con el metodo .get() podemos obtenre el valor almacenado por el Optional, pero primero asegurate de tener un valor
            // ya que en caso de que no tenga nada, lanzara una exception NoSuchElementException.
            System.out.println("El primer valor divisible entre 3 encontrado fue: " + optionalPrimerNumeroEnteroDivisibleEntre3.get());
        }

        // Tambien podemos verificar atravez de un if si es que el optional no tiene un valor y que hacer entonces
        if (optionalPrimerNumeroEnteroDivisibleEntre3.isEmpty()) {
            System.out.println("No encontro un valor divisible entre 3");
        }

        // Tambien es posible verificar si el optional tiene un valor atravez del metodo ifPresent que espera una lambda consummer
        // y de esa forma es mucho mas sensillo y corto.
        optionalPrimerNumeroEnteroDivisibleEntre3.ifPresent(numero -> System.out.println("El numero encontrado fue: " + numero));

        // Y tambien existe una alternavia en la cual es como un if else ya que en una sola expresion se puede tomar accion segun
        // si es que el valor esta presente o no. El primer parametro es un Consummer el cual se ejecutara en caso de que si este presente,
        // y el segundo es un runnable el cual puede ser una lambda que no espera parametros () pero si tiene cuerpo y ese correspondo a cuando
        // el valor no se encuentra presente.
        optionalPrimerNumeroEnteroDivisibleEntre3
                .ifPresentOrElse(
                        numero -> System.out.println("El numero encontrado fue: " + numero), // Esta lambda se ejecuta cuando hay valor
                        () -> System.out.println("No encontro un valor divisible entre 3")); // Esta lambda se ejecuta cuando no valor
    }

    // Metodos auxiliares usador para pasarlos por referencia
    public static boolean sortIntImPares(int num) {
        return num % 2 == 1;
    }

    public static void imprimirNumeros(int num) {
        System.out.print(num + ", ");
    }
}
