package fr.polytechtours.javaperformance.tp.tp4;

/**
 * Cette exercice met en place un algorithme permettant de calculer le 43eme facteur de la suite de Fibonacci.
 */
public class Exercice2 {

    public static int fibonacci(final Integer i) {
        if(i < 0) {
            throw new IllegalArgumentException("Invalid input value");
        }

        return (i < 3) ? i : fibonacci(i - 1) + fibonacci(i - 2);
    }
}
