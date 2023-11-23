import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Stack;

public class GLCtoAPConverter {

    public AutomataPila convertirGLCaAP(GramaticaLibreContexto glc) {
        AutomataPila ap = new AutomataPila();
        ap.setPila(new Stack<>()); // Inicializa la pila

        // Establecer alfabeto de entrada y pila
        ap.setAlfabeto(new HashSet<>(glc.getTerminales()));
        ap.setGamma(new HashSet<>(Collections.singletonList("<S0>"))); // Pila inicializada con epsilon

        // Agregar estados
        ap.setEstados(new HashSet<>(glc.getNoTerminales()));

        // Establecer estado inicial y estados de aceptación
        ap.setEstadoInicial(glc.getSimboloInicial());
        ap.setEstadosAceptacion(new HashSet<>(Collections.singletonList("A2"))); // Cambiado el estado de aceptación

        // Construir transiciones
        ArrayList<TransicionPila> transiciones = new ArrayList<>();

        // Transición para el estado inicial
        transiciones.add(new TransicionPila("A1", "", "_", "A2", new String[] { "<S0>" })); // Cambiada la transición
                                                                                            // inicial

        for (ReglaProduccion regla : glc.getReglasProduccion()) {
            String estadoOrigen = regla.getIzquierda();
            String simboloEntrada = regla.getDerecha();
            String simboloPilaLeer = "_"; // Para esta implementación básica, siempre leemos de la pila epsilon
            String estadoDestino = regla.getSiguiente();

            // Para escribir en la pila, consideraremos cada símbolo de la cadena de salida
            // de la regla
            String[] simbolosPilaEscribir = new String[simboloEntrada.length() + 1]; // Ajuste aquí
            for (int i = 0; i < simboloEntrada.length(); i++) {
                simbolosPilaEscribir[i] = String.valueOf(simboloEntrada.charAt(i));
            }
            simbolosPilaEscribir[simboloEntrada.length()] = "<S0>"; // Cambiado el nuevo símbolo agregado a la pila

            transiciones.add(new TransicionPila(estadoOrigen, simboloEntrada, simboloPilaLeer, estadoDestino,
                    simbolosPilaEscribir));
        }

        ap.setTransiciones(transiciones);

        // Mostrar la estructura deseada
        System.out.println("AP M:");
        System.out.println("K=" + ap.getEstados());
        System.out.println("Sigma=" + ap.getAlfabeto());
        System.out.println("Gamma=" + ap.getGamma());
        System.out.println("Delta=" + formatTransiciones(ap.getTransiciones()));
        System.out.println("s=" + ap.getEstadoInicial());
        System.out.println("F=" + ap.getEstadosAceptacion());

        return ap;
    }

    private String formatTransiciones(ArrayList<TransicionPila> transiciones) {
        StringBuilder delta = new StringBuilder();
        for (TransicionPila transicion : transiciones) {
            delta.append("(")
                    .append("(").append(transicion.getEstadoOrigen()).append(",").append(transicion.getSimboloEntrada())
                    .append(",")
                    .append(transicion.getSimboloPilaLeer()).append("),(")
                    .append(transicion.getEstadoDestino()).append(",")
                    .append(String.join("", transicion.getSimbolosPilaEscribir()))
                    .append(")),");
        }
        // Eliminar la coma extra al final
        delta.deleteCharAt(delta.length() - 1);
        return delta.toString();
    }
}
