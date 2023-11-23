// ReglaProduccion.java
class ReglaProduccion {
    private String izquierda;
    private String derecha;
    private String siguiente;

    public ReglaProduccion(String izquierda, String derecha, String siguiente) {
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.siguiente = siguiente;
    }

    public String getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(String izquierda) {
        this.izquierda = izquierda;
    }

    public String getDerecha() {
        return derecha;
    }

    public void setDerecha(String derecha) {
        this.derecha = derecha;
    }

    public String getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(String siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public String toString() {
        return "(" + izquierda + ", " + (derecha.isEmpty() ? "ε" : derecha) + ", " + siguiente + ")";
    }
}
