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

    // yeahh
    @Override
    public String toString() {
        return "(" + izquierda + ", " + (derecha.isEmpty() ? "ε" : derecha) + ", " + siguiente + ")";
    }
}
