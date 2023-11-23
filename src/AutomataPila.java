import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AutomataPila {
    private Set<String> estados;
    private Set<String> estadosFinales;
    private String estadoInicial;
    private Set<String> alfabetoPila;
    private Map<Transicion, String> transiciones;

    public AutomataPila() {
        this.estados = new HashSet<>();
        this.estadosFinales = new HashSet<>();
        this.alfabetoPila = new HashSet<>();
        this.transiciones = new HashMap<>();
    }

    public void agregarEstado(String estado) {
        this.estados.add(estado);
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void agregarEstadoFinal(String estadoFinal) {
        this.estadosFinales.add(estadoFinal);
    }

    public void agregarSimboloAlfabetoPila(String simbolo) {
        this.alfabetoPila.add(simbolo);
    }

    public void agregarTransicion(String estadoInicio, String simboloEntrada, String topePilaLeer,
            String estadoDestino, String topePilaEscribir) {
        Transicion transicion = new Transicion(estadoInicio, simboloEntrada, topePilaLeer);
        this.transiciones.put(transicion, estadoDestino + "," + topePilaEscribir);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("K={").append(String.join(",", estados)).append("}\n");
        sb.append("Sigma={").append(String.join(",", alfabetoPila)).append("}\n");
        sb.append("Gamma={").append("}\n");
        sb.append("Delta={\n");

        for (Map.Entry<Transicion, String> entry : transiciones.entrySet()) {
            Transicion transicion = entry.getKey();
            String[] destinoYPila = entry.getValue().split(",");

            sb.append("(").append(transicion.getEstadoInicio()).append(",")
                    .append(transicion.getSimboloEntrada()).append(",")
                    .append(transicion.getTopePilaLeer()).append(") -> (")
                    .append(destinoYPila[0]);

            // Verifica si hay al menos dos elementos en destinoYPila antes de acceder a
            // destinoYPila[1]
            if (destinoYPila.length > 1) {
                sb.append(",").append(destinoYPila[1]);
            }

            sb.append(")\n");
        }

        sb.append("}\n");
        sb.append("s=").append(estadoInicial).append("\n");
        sb.append("F={").append(String.join(",", estadosFinales)).append("}\n");

        return sb.toString();
    }

    private static class Transicion {
        private String estadoInicio;
        private String simboloEntrada;
        private String topePilaLeer;

        public Transicion(String estadoInicio, String simboloEntrada, String topePilaLeer) {
            this.estadoInicio = estadoInicio;
            this.simboloEntrada = simboloEntrada;
            this.topePilaLeer = topePilaLeer;
        }

        public String getEstadoInicio() {
            return estadoInicio;
        }

        public String getSimboloEntrada() {
            return simboloEntrada;
        }

        public String getTopePilaLeer() {
            return topePilaLeer;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Transicion transicion = (Transicion) obj;
            return estadoInicio.equals(transicion.estadoInicio) &&
                    simboloEntrada.equals(transicion.simboloEntrada) &&
                    topePilaLeer.equals(transicion.topePilaLeer);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(estadoInicio, simboloEntrada, topePilaLeer);
        }
    }
}
