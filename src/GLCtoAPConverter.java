import java.util.Stack;

public class GLCtoAPConverter {
    public static AutomataPila convertirGLCaAP(GramaticaLibreContexto glc) {
        AutomataPila ap = new AutomataPila();

        // Inicializar el conjunto de estados y el alfabeto de la pila
        ap.agregarEstado("q0");
        ap.agregarEstado("q1");
        ap.agregarSimboloAlfabetoPila("a");
        ap.agregarSimboloAlfabetoPila("<S0>");

        // Configurar la transición inicial
        ap.agregarTransicion("q0", "", "", "q1", "<S0>");

        // Configurar las transiciones basadas en las reglas de producción
        for (ReglaProduccion regla : glc.getReglasProduccion()) {
            String estadoInicio = "q" + regla.getIzquierda();
            String simboloEntrada = regla.getDerecha().isEmpty() ? "" : regla.getDerecha();
            String estadoDestino = "q" + regla.getSiguiente();

            String topePilaLeer = regla.getDerecha().isEmpty() ? "<S0>" : regla.getDerecha();
            String topePilaEscribir = regla.getSiguiente().equals("") ? "" : regla.getSiguiente();

            ap.agregarTransicion(estadoInicio, simboloEntrada, topePilaLeer, estadoDestino, topePilaEscribir);
        }

        // Configurar el estado inicial y los estados finales
        ap.setEstadoInicial("q0");
        ap.agregarEstadoFinal("q1");

        return ap;
    }
}