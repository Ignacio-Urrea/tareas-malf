import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class Convertidor {

    public Map<Character, Integer> precedencia_operadores;

    public Convertidor() {
        // operadores y precedencias
        Map<Character, Integer> mapa = new HashMap<>();
        mapa.put('(', 1);
        mapa.put('|', 2);
        mapa.put('.', 3);
        mapa.put('*', 4);
        precedencia_operadores = Collections.unmodifiableMap(mapa);
    }

    // Convierte er a su posfijo
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

    // precedencia de cada caracter
    private Integer obtenerPrecedencia(Character c) {
        Integer precedencia = precedencia_operadores.get(c);
        if (precedencia == null) {
            precedencia = 6; // Precedencia por defecto
        }
        return precedencia;
    }

}
