import java.util.HashMap;
import java.util.Map;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

class AFNDtoGLC {
    private static int estadoCounter = 1;

    public GramaticaLibreContexto convertirAFNDtoGLC(Automata afnd) {
        GramaticaLibreContexto glc = new GramaticaLibreContexto();

        Map<Estado, String> estadoToNoTerminal = asignarNoTerminales(afnd);

        for (Estado estado : afnd.getEstados()) {
            for (Arista arista : estado.getAristas()) {
                String simboloEntrada = arista.getCaracter();
                Estado estadoDestino = arista.getFin();
                String noTerminalInicio = estadoToNoTerminal.get(estado);
                String noTerminalDestino = estadoToNoTerminal.get(estadoDestino);

                if (simboloEntrada.equals("_")) {
                    glc.agregarRegla(noTerminalInicio, "", noTerminalDestino);
                } else if (simboloEntrada.equals("?")) {
                    glc.agregarRegla(noTerminalInicio, "", noTerminalDestino);
                } else {
                    glc.agregarRegla(noTerminalInicio, simboloEntrada, noTerminalDestino);
                }
            }
        }

        String simboloInicial = estadoToNoTerminal.get(afnd.getInicial());
        glc.setSimboloInicial(simboloInicial);

        for (Estado estadoAceptado : afnd.getestadosAceptados()) {
            String noTerminalAceptado = estadoToNoTerminal.get(estadoAceptado);
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
