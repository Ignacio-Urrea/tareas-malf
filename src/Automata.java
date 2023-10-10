import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Gerardo Estrada
 * @author Pedro González
 */
public class Automata
{
    //compuesto por un estado inicial
    private Estado inicial;
    private ArrayList<Estado> aceptacion;
    private ArrayList<Estado> estados;
    
    private HashSet alfabeto;
    private String tipo;
   
    
    public Automata()
    {
        this.estados = new ArrayList();
        this.aceptacion = new ArrayList();
        this.alfabeto = new HashSet();
    }

    public Estado getInicial()
    {
        return inicial;
    }

    public void setInicial(Estado inicial)
    {
        this.inicial = inicial;
    }

    public ArrayList<Estado> getEstadosAceptacion()
    {
        return aceptacion;
    }

     public void addEstadosAceptacion(Estado fin) 
     {
        this.aceptacion.add(fin);
    }
     
    public ArrayList<Estado> getEstados()
    {
        return estados;
    }

    public void addEstados(Estado estado) 
    {
        this.estados.add(estado);
    }

    public HashSet getAlfabeto()
    {
        return alfabeto;
    }
    
    /**
     * Metodo para definir el alfabeto del automata a partir 
     * de la expresion regular
     * @param regex 
     */
    public void crearAlfabeto(String regex) 
    {
        for (Character ch: regex.toCharArray())
        {           
            if (ch != '|' && ch != '.' && ch != '*' && ch != '_' && ch!='~')
            {
                this.alfabeto.add(Character.toString(ch));
            }
        }
    }

    public void setAlfabeto(HashSet alfabeto)
    {
        this.alfabeto = alfabeto;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        String res = new String();
        res += ""+this.tipo+":\r\n"; 
        res += "K={";
        for(int i= 0; i<this.estados.size();i++)
        {
            if(i==this.estados.size()-1)
            {
                res += "q"+this.estados.get(i);
            }
            else
            {
                res += "q"+this.estados.get(i)+",";
            }
            
        }
        res += "}\r\n";
        
        res += "Sigma={";
        for (int i = 0; i < this.alfabeto.toArray().length; i++)
        {
            if(i == this.alfabeto.toArray().length-1)
            {
                res+=this.alfabeto.toArray()[i];
            }
            else
            {
                res+=this.alfabeto.toArray()[i]+",";
            }
        }
        res += "}\r\n";
        
        res += "Delta: \r\n";
        for (int i =0 ; i<this.estados.size();i++)
        {
            Estado est = estados.get(i);
            for (int j = 0; j < est.getTransiciones().size(); j++)
            {
                res += est.getTransiciones().get(j)+"\r\n";
            }
             
        }
        res += "s={q" + this.inicial +"}\r\n";     
        res += "F={";
        for(int i= 0; i<this.aceptacion.size();i++)
        {
            if(i==this.aceptacion.size()-1)
            {
                res += "q"+this.aceptacion.get(i);
            }
            else
            {
                res += "q"+this.aceptacion.get(i)+",";
            }
            
        }
        res += "}\r\n";
        res += "\r\n";
        return res;
    }
    
}
