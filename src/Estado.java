import java.util.ArrayList;

/**
 * @author Gerardo Estrada
 * @author Pedro González
 */
public class Estado
{
    private int id;
    private ArrayList<Transicion> transiciones = new ArrayList();

    public Estado(int id)
    {
        this.id = id;
    }

    public Estado(int id, ArrayList<Transicion> transiciones)
    {
        this.id = id;
        this.transiciones = transiciones;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public ArrayList<Transicion> getTransiciones()
    {
        return transiciones;
    }

    public boolean addTransiciones(Transicion e)
    {
        return transiciones.add(e);
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.id);
    }
    
    
}
