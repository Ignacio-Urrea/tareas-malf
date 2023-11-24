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

        // Inicializar Gamma con <S0> y los caracteres de la GLC
        HashSet<String> gammaInicial = new HashSet<>();
        gammaInicial.add("<S0>");
        for (ReglaProduccion regla : glc.getReglasProduccion()) {
            if (regla.getDerecha().length() == 1) {
                gammaInicial.add(regla.getDerecha());
            }
        }
        ap.setGamma(gammaInicial);

        // Agregar estados
        ap.setEstados(new HashSet<>(glc.getNoTerminales()));

        // Establecer estado inicial y estados de aceptación
        ap.setEstadoInicial(glc.getSimboloInicial());
        ap.setEstadosAceptacion(obtenerEstadosFinales(glc));

        // Construir transiciones
        ArrayList<TransicionPila> transiciones = new ArrayList<>();

        // Transición para el estado inicial
        transiciones.add(new TransicionPila("A1", "", "<S0>", "A2", new String[] { "<S0>" }));

        for (ReglaProduccion regla : glc.getReglasProduccion()) {
            String estadoOrigen = regla.getIzquierda();
            String simboloEntrada = regla.getDerecha();
            String simboloPilaLeer = "<S0>"; // Cambiado para leer de la pila
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
        System.out.println("AUTOMATA DE PILA:");
        System.out.println("K=" + ap.getEstados());
        System.out.println("Sigma=" + ap.getAlfabeto());
        System.out.println("Gamma=" + ap.getGamma());
        System.out.println("Delta=" + formatTransiciones(ap.getTransiciones()));
        System.out.println("s=" + ap.getEstadoInicial());
        System.out.println("F=" + ap.getEstadosAceptacion());

        return ap;
    }

    private HashSet<String> obtenerEstadosFinales(GramaticaLibreContexto glc) {
        HashSet<String> estadosFinales = new HashSet<>();
        int maxEstado = 0;

        for (ReglaProduccion regla : glc.getReglasProduccion()) {
            if (regla.getSiguiente() != null && !regla.getSiguiente().isEmpty()) {
                try {
                    int estado = Integer.parseInt(regla.getSiguiente().substring(1)); // Obtener el número del estado
                    maxEstado = Math.max(maxEstado, estado);
                } catch (NumberFormatException e) {
                    // Manejar el caso en el que el formato del estado no sea el esperado
                    e.printStackTrace();
                }
            }
        }

        estadosFinales.add("A" + maxEstado); // Agregar el estado más alto
        return estadosFinales;
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
