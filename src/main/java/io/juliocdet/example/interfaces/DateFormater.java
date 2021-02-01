package io.juliocdet.example.interfaces;

/**
 * Funtional interface de ejemplo.
 * Una interfaz no es solo interfaz funcional por anotarla con @Funtional inteface,
 * sino por tener un solo metodo para implementar. Ya que puede tener multiples metodos default
 * o static sin nigun problema. Pero estrictamente solo un metodo para ser implementado.
 * Que sea presisamente el que se tomara si se utiliza una lambda para implementar esta interfaz.
 */
@FunctionalInterface
public interface DateFormater {

    String apply(String dia, String mes, String ano, boolean flag);

    static String getDefaultDate() {
        return "0/0/0";
    }
}
