/**
 * @author Gerardo Estrada
 * @author Pedro González
 */
public class Transicion {
    public String elemento;
    public Estado inicio;
    public Estado fin;

    public Transicion(Estado inicio, Estado fin, String elemento) {
        this.inicio = inicio;
        this.fin = fin;
        this.elemento = elemento;
    }

    public Estado getInicio() {
        return inicio;
    }

    public void setInicio(Estado inicio) {
        this.inicio = inicio;
    }

    public Estado getFin() {
        return fin;
    }

    public void setFin(Estado fin) {
        this.fin = fin;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    /**
     * Mostrar la transicion
     * 
     * @return String toString
     */
    @Override
    public String toString() {
        return "(q" + inicio.getId() + "," + elemento + ",q" + fin.getId() + ")";
    }

}
