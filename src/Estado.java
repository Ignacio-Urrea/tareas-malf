import java.util.ArrayList;

//representa un estado en un autómata
public class Estado {
    private int id;
    private ArrayList<Transicion> transiciones = new ArrayList();

    public Estado(int id) {
        this.id = id;
    }

    // para inicializar con las trnasiciones
    public Estado(int id, ArrayList<Transicion> transiciones) {
        this.id = id;
        this.transiciones = transiciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public boolean addTransiciones(Transicion e) {
        return transiciones.add(e);
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}
