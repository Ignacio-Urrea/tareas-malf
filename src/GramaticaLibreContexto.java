import java.util.HashSet;
import java.util.Set;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

class GramaticaLibreContexto {
    private String simboloInicial;
    private Set<String> noTerminales;
    private Set<String> terminales;
    private Set<ReglaProduccion> reglasProduccion;

    public GramaticaLibreContexto() {
        this.noTerminales = new HashSet<>();
        this.terminales = new HashSet<>();
        this.reglasProduccion = new HashSet<>();
    }

    // Getters
    public String getSimboloInicial() {
        return simboloInicial;
    }

    public Set<String> getNoTerminales() {
        return noTerminales;
    }

    public Set<String> getTerminales() {
        return terminales;
    }

    public Set<ReglaProduccion> getReglasProduccion() {
        return reglasProduccion;
    }

    // Setters
    public void setSimboloInicial(String simboloInicial) {
        this.simboloInicial = simboloInicial;
        this.noTerminales.add(simboloInicial);
    }

    public void setNoTerminales(Set<String> noTerminales) {
        this.noTerminales = noTerminales;
    }

    public void setTerminales(Set<String> terminales) {
        this.terminales = terminales;
    }

    public void setReglasProduccion(Set<ReglaProduccion> reglasProduccion) {
        this.reglasProduccion = reglasProduccion;
    }

    // Otros métodos
    public void agregarRegla(String izquierda, String derecha, String siguiente) {
        this.noTerminales.add(izquierda);
        this.noTerminales.add(siguiente);

        if (!derecha.isEmpty()) {
            this.terminales.add(derecha);
        }

        this.reglasProduccion.add(new ReglaProduccion(izquierda, derecha, siguiente));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("S= ").append(simboloInicial).append("\n");
        stringBuilder.append("V= ").append(noTerminales).append("\n");
        stringBuilder.append("Sigma= ").append(terminales).append("\n");
        stringBuilder.append("R =\n");

        for (ReglaProduccion regla : reglasProduccion) {
            stringBuilder.append(regla).append("\n");
        }

        return stringBuilder.toString();
    }
}
