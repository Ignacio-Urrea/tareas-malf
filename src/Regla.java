import java.util.Objects;

public class Regla {
    private String noTerminalInicio;
    private String simboloEntrada;
    private String noTerminalDestino;

    public Regla(String noTerminalInicio, String simboloEntrada, String noTerminalDestino) {
        this.noTerminalInicio = noTerminalInicio;
        this.simboloEntrada = simboloEntrada;
        this.noTerminalDestino = noTerminalDestino;
    }

    // Agrega getters si es necesario

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Regla regla = (Regla) obj;
        return Objects.equals(noTerminalInicio, regla.noTerminalInicio) &&
                Objects.equals(simboloEntrada, regla.simboloEntrada) &&
                Objects.equals(noTerminalDestino, regla.noTerminalDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noTerminalInicio, simboloEntrada, noTerminalDestino);
    }
}
