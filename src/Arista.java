/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class Arista {
    String caracter;
    Estado inicio;
    Estado finall;

    public Arista(Estado inicio, Estado finall, String caracter) {
        this.inicio = inicio;
        this.finall = finall;
        this.caracter = caracter;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String elemento) {
        this.caracter = caracter;
    }

    public Estado getInicio() {
        return inicio;
    }

    public void setInicio(Estado inicio) {
        this.inicio = inicio;
    }

    public Estado getFin() {
        return finall;
    }

    public void setFin(Estado fin) {
        this.finall = fin;
    }

    @Override
    public String toString() {
        return "(q" + inicio.getId() + "," + caracter + ",q" + finall.getId() + ")";
    }

}
