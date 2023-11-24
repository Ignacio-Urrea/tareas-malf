/**
 * 
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class ReglaProduccion {
    private String izquierda;
    private String derecha;
    private String siguiente;

    public ReglaProduccion(String izquierda, String derecha, String siguiente) {
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.siguiente = siguiente;
    }

    // Getters
    public String getIzquierda() {
        return izquierda;
    }

    public String getDerecha() {
        return derecha;
    }

    public String getSiguiente() {
        return siguiente;
    }

    // Setters
    public void setIzquierda(String izquierda) {
        this.izquierda = izquierda;
    }

    public void setDerecha(String derecha) {
        this.derecha = derecha;
    }

    public void setSiguiente(String siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public String toString() {
        return "(" + izquierda + ", " + (derecha.isEmpty() ? "ε" : derecha) + ", " + siguiente + ")";
    }
}
