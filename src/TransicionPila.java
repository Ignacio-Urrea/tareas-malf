/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class TransicionPila {
    private String estadoOrigen;
    private String simboloEntrada;
    private String simboloPilaLeer;
    private String estadoDestino;
    private String[] simbolosPilaEscribir;

    public TransicionPila(String estadoOrigen, String simboloEntrada, String simboloPilaLeer,
            String estadoDestino, String[] simbolosPilaEscribir) {
        this.estadoOrigen = estadoOrigen;
        this.simboloEntrada = simboloEntrada;
        this.simboloPilaLeer = simboloPilaLeer;
        this.estadoDestino = estadoDestino;
        this.simbolosPilaEscribir = simbolosPilaEscribir;
    }

    // Getters
    public String getEstadoOrigen() {
        return estadoOrigen;
    }

    public String getSimboloEntrada() {
        return simboloEntrada;
    }

    public String getSimboloPilaLeer() {
        return simboloPilaLeer;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public String[] getSimbolosPilaEscribir() {
        return simbolosPilaEscribir;
    }

    // Setters
    public void setEstadoOrigen(String estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public void setSimboloEntrada(String simboloEntrada) {
        this.simboloEntrada = simboloEntrada;
    }

    public void setSimboloPilaLeer(String simboloPilaLeer) {
        this.simboloPilaLeer = simboloPilaLeer;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public void setSimbolosPilaEscribir(String[] simbolosPilaEscribir) {
        this.simbolosPilaEscribir = simbolosPilaEscribir;
    }

    @Override
    public String toString() {
        // Implementa la impresión de la transición
        // Puedes seguir el formato que desees
        // Ejemplo:
        return "(" + estadoOrigen + ", " + simboloEntrada + ", " + simboloPilaLeer +
                "; " + estadoDestino + ", " + String.join("", simbolosPilaEscribir) + ")";
    }
}
