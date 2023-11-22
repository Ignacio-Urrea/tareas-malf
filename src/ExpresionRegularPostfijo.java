import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

// ER a su postfijo
public class ExpresionRegularPostfijo {

    // Mapa que almacena los operadores y sus precedencias
    private final Map<Character, Integer> precedenciaOperadores;

    public ExpresionRegularPostfijo() {
        Map<Character, Integer> op = new HashMap<>();
        op.put('(', 1);
        op.put('|', 2);
        op.put('.', 3);
        op.put('*', 4);
        precedenciaOperadores = Collections.unmodifiableMap(op);
    }

    public String convertir(String expresion) {
        String posfija = new String();
        Stack<Character> pila = new Stack<>();

        for (Character c : expresion.toCharArray()) {
            if (c.equals('(')) {
                pila.push(c);
            } else if (c.equals(')')) {
                while (!pila.peek().equals('(')) {
                    posfija += pila.pop();
                }
                pila.pop();
            } else {
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
