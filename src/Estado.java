import java.util.ArrayList;

//representa un estado en un autómata
public class Estado {
    private int id;
    private ArrayList<Arista> aristas = new ArrayList();

    public Estado(int id) {
        this.id = id;
    }

    // para inicializar con las trnasiciones
    public Estado(int id, ArrayList<Arista> transiciones) {
        this.id = id;
        this.aristas = transiciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Arista> getTransiciones() {
        return aristas;
    }

    public boolean addTransiciones(Arista e) {
        return aristas.add(e);
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}
