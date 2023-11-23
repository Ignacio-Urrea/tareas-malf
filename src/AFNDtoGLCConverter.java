import java.util.HashMap;
import java.util.Map;

class AFNDtoGLCConverter {
    private static int estadoCounter = 1;

    public GramaticaLibreContexto convertirAFNDtoGLC(Automata afnd) {
        GramaticaLibreContexto glc = new GramaticaLibreContexto();

        // Asigna un símbolo no terminal para cada estado
        Map<Estado, String> estadoToNoTerminal = asignarNoTerminales(afnd);

        // Agrega reglas para transiciones en el AFND
        for (Estado estado : afnd.getEstados()) {
            for (Arista arista : estado.getAristas()) {
                String simboloEntrada = arista.getCaracter();
                Estado estadoDestino = arista.getFin();
                String noTerminalInicio = estadoToNoTerminal.get(estado);
                String noTerminalDestino = estadoToNoTerminal.get(estadoDestino);

                // Elimina el "_" si existe en el símbolo de entrada
                if (simboloEntrada.equals("_")) {
                    glc.agregarRegla(noTerminalInicio, "", noTerminalDestino);
                } else if (simboloEntrada.equals("?")) {
                    // Cambia la regla "A3 -> ? A6" por "A3 -> A6"
                    glc.agregarRegla(noTerminalInicio, "", noTerminalDestino);
                } else {
                    glc.agregarRegla(noTerminalInicio, simboloEntrada, noTerminalDestino);
                }
            }
        }

        // Agrega reglas para estados iniciales y finales
        String simboloInicial = estadoToNoTerminal.get(afnd.getInicial());
        glc.setSimboloInicial(simboloInicial);

        for (Estado estadoAceptado : afnd.getestadosAceptados()) {
            String noTerminalAceptado = estadoToNoTerminal.get(estadoAceptado);
            // Elimina el "_" si existe en el símbolo de aceptación
            glc.agregarRegla(noTerminalAceptado, "", "");
        }

        return glc;
    }

    private Map<Estado, String> asignarNoTerminales(Automata afnd) {
        Map<Estado, String> estadoToNoTerminal = new HashMap<>();

        for (Estado estado : afnd.getEstados()) {
            String noTerminal = "A" + estadoCounter++;
            estadoToNoTerminal.put(estado, noTerminal);
        }

        return estadoToNoTerminal;
    }
}
