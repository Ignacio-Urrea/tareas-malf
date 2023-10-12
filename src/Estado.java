import java.util.ArrayList;

//representa un estado en un autómata con su identificador y su lista de aristas
public class Estado {
    private int id;
    private ArrayList<Arista> aristas = new ArrayList();

    public Estado(int id) {
        this.id = id;
    }

    public Estado(int id, ArrayList<Arista> aristas) {
        this.id = id;
        this.aristas = aristas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Arista> getAristas() {
        return aristas;
    }

    public boolean addAristas(Arista e) {
        return aristas.add(e);
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}
