import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

// er a su postfijo
public class ConvertidorExpresionRegular {

    // Mapa que almacena los operadores y sus precedencias
    private final Map<Character, Integer> precedenciaOperadores;

    public ConvertidorExpresionRegular() {
        Map<Character, Integer> mapa = new HashMap<>();
        mapa.put('(', 1);
        mapa.put('|', 2);
        mapa.put('.', 3);
        mapa.put('*', 4);
        precedenciaOperadores = Collections.unmodifiableMap(mapa);
    }

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

    private Integer obtenerPrecedencia(Character c) {
        Integer precedencia = precedenciaOperadores.get(c);
        if (precedencia == null) {
            precedencia = 6; // Precedencia por defecto
        }
        return precedencia;
    }
}
