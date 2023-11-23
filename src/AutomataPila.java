import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class AutomataPila {
    private HashSet<String> estados;
    private HashSet<String> alfabeto;
    private HashSet<String> gamma; // Alfabeto de la pila
    private HashSet<String> estadosAceptacion;
    private String estadoInicial;
    private Stack<String> pila;
    private ArrayList<TransicionPila> transiciones;

    public AutomataPila() {
        // Inicializa las estructuras de datos
    }

    // Getters
    public HashSet<String> getEstados() {
        return estados;
    }

    public HashSet<String> getAlfabeto() {
        return alfabeto;
    }

    public HashSet<String> getGamma() {
        return gamma;
    }

    public HashSet<String> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public Stack<String> getPila() {
        return pila;
    }

    public ArrayList<TransicionPila> getTransiciones() {
        return transiciones;
    }

    // Setters
    public void setEstados(HashSet<String> estados) {
        this.estados = estados;
    }

    public void setAlfabeto(HashSet<String> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public void setGamma(HashSet<String> gamma) {
        this.gamma = gamma;
    }

    public void setEstadosAceptacion(HashSet<String> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void setPila(Stack<String> pila) {
        this.pila = pila;
    }

    public void setTransiciones(ArrayList<TransicionPila> transiciones) {
        this.transiciones = transiciones;
    }

    @Override
    public String toString() {
        // Implementa la impresión del autómata de pila
        // Puedes seguir el formato que desees
        // Ejemplo:
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Estados: ").append(estados).append("\n");
        stringBuilder.append("Alfabeto: ").append(alfabeto).append("\n");
        stringBuilder.append("Gamma (alfabeto de la pila): ").append(gamma).append("\n");
        stringBuilder.append("Estado Inicial: ").append(estadoInicial).append("\n");
        stringBuilder.append("Estados de Aceptación: ").append(estadosAceptacion).append("\n");
        stringBuilder.append("Transiciones:\n");

        for (TransicionPila transicion : transiciones) {
            stringBuilder.append(transicion).append("\n");
        }

        return stringBuilder.toString();
    }
}
