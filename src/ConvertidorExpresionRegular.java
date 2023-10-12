import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Clase que realiza la conversión de una expresión regular de notación infix a
 * postfix (posfijo).
 * 
 * Esta clase contiene métodos para convertir una expresión regular en su forma
 * posfija.
 * La clase utiliza una pila para realizar la conversión y tiene en cuenta la
 * precedencia de los operadores.
 * 
 * Los operadores permitidos son: '(', '|', '.', '*'.
 * 
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */
public class ConvertidorExpresionRegular {

    // Mapa que almacena los operadores y sus precedencias
    private final Map<Character, Integer> precedenciaOperadores;

    /**
     * Constructor de la clase ConvertidorExpresionRegular.
     * Inicializa el mapa de precedencias de los operadores.
     */
    public ConvertidorExpresionRegular() {
        Map<Character, Integer> mapa = new HashMap<>();
        mapa.put('(', 1);
        mapa.put('|', 2);
        mapa.put('.', 3);
        mapa.put('*', 4);
        precedenciaOperadores = Collections.unmodifiableMap(mapa);
    }

    /**
     * Convierte una expresión regular de notación infix a postfix (posfijo).
     *
     * @param expresion La expresión regular en notación infix.
     * @return La expresión regular convertida a postfix (posfijo).
     */
    public String convertir(String expresion) {
        String posfija = new String();
        Stack<Character> pila = new Stack<>();

        for (Character c : expresion.toCharArray()) {
            switch (c) {
                case '(':
                    pila.push(c);
                    break;
                case ')':
                    while (!pila.peek().equals('(')) {
                        posfija += pila.pop();
                    }
                    pila.pop();
                    break;
                default:
                    while (!pila.isEmpty()) {
                        Character caracterEnCima = pila.peek();
                        Integer precedenciaCaracterEnCima = obtenerPrecedencia(caracterEnCima);
                        Integer precedenciaCaracterActual = obtenerPrecedencia(c);
                        if (precedenciaCaracterEnCima >= precedenciaCaracterActual) {
                            posfija += pila.pop();
                        } else {
                            break;
                        }
                    }
                    pila.push(c);
                    break;
            }
        }

        while (!pila.isEmpty()) {
            posfija += pila.pop();
        }
        return posfija;
    }

    /**
     * Obtiene la precedencia de un operador dado.
     *
     * @param c El operador cuya precedencia se debe determinar.
     * @return La precedencia del operador. Si el operador no está en el mapa,
     *         devuelve 6 (predefinido).
     */
    private Integer obtenerPrecedencia(Character c) {
        Integer precedencia = precedenciaOperadores.get(c);
        if (precedencia == null) {
            precedencia = 6; // Precedencia por defecto
        }
        return precedencia;
    }
}
