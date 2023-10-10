import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * @author Gerardo Estrada
 * @author Pedro González
 */
public class AFND
{
    private Automata afnd;
    private String expresionRegular;

    public AFND(String expresionRegular)
    {
        this.expresionRegular = expresionRegular;
    }
    
    public void construirAFND()
    {
        Stack pilaAFND = new Stack();
        Automata afnd1,afnd2, kleene;
        
        for(int i=0; i<this.expresionRegular.length();i++)
        {
            char c = this.expresionRegular.charAt(i);
            switch(c)
            {
                case '*':
                    kleene = clausuraKleene((Automata) pilaAFND.pop());
                    pilaAFND.push(kleene);
                    this.afnd=kleene;
                    break;
                case  '.':
                    afnd1 = (Automata) pilaAFND.pop();
                    afnd2 = (Automata) pilaAFND.pop();
                    Automata concat_result = afndConcatenacion(afnd1,afnd2);
                   
                    pilaAFND.push(concat_result);
                    this.afnd=concat_result;
                    break;
                case '|':
                    afnd1 = (Automata) pilaAFND.pop();
                    afnd2 = (Automata) pilaAFND.pop();
                    Automata union = afndUnion(afnd1, afnd2);
                    pilaAFND.push(union);
                    this.afnd = union;                    
                    break;
                case '~':
                    Automata afndVacio = afndVacio();
                    pilaAFND.push(afndVacio);
                    this.afnd = afndVacio;
                    break;
                    
                default: 
                    Automata afndSimple = afndSimple(c);
                    pilaAFND.push(afndSimple);
                    this.afnd = afndSimple;
            }
        }
        
        this.afnd.crearAlfabeto(expresionRegular);
        this.afnd.setTipo("AFND");
    }
    
    public Automata afndSimple(char elemento)
    {
        
        Automata afndAux = new Automata();
        Estado inicio = new Estado(0);
        Estado fin = new Estado(1);
        Transicion tran= new Transicion(inicio, fin, String.valueOf(elemento));
        inicio.addTransiciones(tran); 
        afndAux.addEstados(inicio);
        afndAux.addEstados(fin);
   
        afndAux.setInicial(inicio);
        afndAux.addEstadosAceptacion(fin);
        return afndAux;

    }
    
    public Automata afndVacio()
    {
        Automata afndAux = new Automata();
        Estado inicio = new Estado(0);
        Estado fin = new Estado(1);
        afndAux.addEstados(inicio);
        afndAux.addEstados(fin);   
        afndAux.setInicial(inicio);
        afndAux.addEstadosAceptacion(fin);
        return afndAux;
    }
    
    public Automata afndUnion(Automata afnd1, Automata afnd2)
    {
        Automata union = new Automata();
        Estado inicio = new Estado(0);
        
        Transicion tran = new Transicion(inicio, afnd2.getInicial(), "_");
        
        inicio.addTransiciones(tran);
        
        union.addEstados(inicio);
        union.setInicial(inicio);
        
        int i;

        for(i=0;i<afnd1.getEstados().size();i++)
        {
            Estado estadoAux = afnd1.getEstados().get(i);
            estadoAux.setId(i+1);
            union.addEstados(estadoAux);
        }
        
        for(int j=0;j<afnd2.getEstados().size();j++)
        {
            Estado estadoAux = afnd2.getEstados().get(j);
            estadoAux.setId(i+1);
            union.addEstados(estadoAux);
            i++;
        }
        
        Estado fin = new Estado(afnd1.getEstados().size() + afnd2.getEstados().size() + 1);
        union.addEstados(fin);
        union.addEstadosAceptacion(fin);
        
        Estado anteriorInicio = afnd1.getInicial();
        ArrayList<Estado> anteriorFin = afnd1.getEstadosAceptacion();
        ArrayList<Estado> anteriorFin2 = afnd2.getEstadosAceptacion();
        
        Transicion tranAux = new Transicion(inicio, anteriorInicio, "_");
        inicio.getTransiciones().add(tranAux);
        
        for (int k =0; k<anteriorFin.size();k++)
        {
            tranAux = new Transicion(anteriorFin.get(k), fin, "_");
            anteriorFin.get(k).getTransiciones().add(tranAux);
        }
        
        for (int k =0; k<anteriorFin2.size();k++)
        {
            tranAux = new Transicion(anteriorFin2.get(k),fin,"_");
            anteriorFin2.get(k).getTransiciones().add(tranAux);
        }
        
        HashSet alfabeto = new HashSet();
        alfabeto.addAll(afnd1.getAlfabeto());
        alfabeto.addAll(afnd2.getAlfabeto());
        union.setAlfabeto(alfabeto);
        
        return union;
    }
    
    public Automata afndConcatenacion(Automata afnd1, Automata afnd2)
    {
        Automata concatenacion = new Automata();        
        int i;
        for (i = 0;  i<afnd2.getEstados().size() ; i++)
        {
            Estado estadoAux = afnd2.getEstados().get(i);
            estadoAux.setId(i);
            
            if(i==0)
            {
                concatenacion.setInicial(estadoAux);
            }
            
            if (i == afnd2.getEstados().size()-1)
            {
                for (int j = 0; j<afnd2.getEstadosAceptacion().size();j++)
                {
                    Transicion tranAux = new Transicion(afnd2.getEstadosAceptacion().get(j), afnd1.getInicial(), "_");
                    estadoAux.addTransiciones(tranAux);
                }
            }
            concatenacion.addEstados(estadoAux);
        }
        
        for (int j =0;j<afnd1.getEstados().size();j++)
        {
            Estado estadoAux = (Estado) afnd1.getEstados().get(j);
            
            estadoAux.setId(i);

            if (afnd1.getEstados().size()-1==j)
            {
                concatenacion.addEstadosAceptacion(estadoAux);
            }
            concatenacion.addEstados(estadoAux);
            i++;
        }
       
        HashSet alfabeto = new HashSet();
        alfabeto.addAll(afnd1.getAlfabeto());
        alfabeto.addAll(afnd2.getAlfabeto());
        concatenacion.setAlfabeto(alfabeto);
        
        return concatenacion;
    }

    private Automata clausuraKleene(Automata afnd)
    {
        Automata kleene = new Automata();
        
        Estado inicio = new Estado(0);
        kleene.addEstados(inicio);
        kleene.setInicial(inicio);
        
        for (int i = 0; i < afnd.getEstados().size(); i++)
        {
            Estado estadoAux = afnd.getEstados().get(i);           
            estadoAux.setId(i+1);
            kleene.addEstados(estadoAux);
        }
        
        Estado fin = new Estado(afnd.getEstados().size()+1);
        kleene.addEstados(fin);
        kleene.addEstadosAceptacion(fin);
        
        Estado inicioAnterior = afnd.getInicial();        
        ArrayList<Estado> finAnterior = afnd.getEstadosAceptacion();
        
        inicio.getTransiciones().add(new Transicion(inicio, inicioAnterior, "_"));
        inicio.getTransiciones().add(new Transicion(inicio, fin, "_"));
        
        for (int i =0; i<finAnterior.size();i++){
            finAnterior.get(i).getTransiciones().add(new Transicion(finAnterior.get(i), inicioAnterior, "_"));
            finAnterior.get(i).getTransiciones().add(new Transicion(finAnterior.get(i), fin, "_"));
        }
        kleene.setAlfabeto(afnd.getAlfabeto());
        return kleene;
    }

    public void agregarSignoAlfabetoGeneral()
    {     
        Stack<Transicion> transiciones = new Stack();
        for(int i=afnd.getInicial().getTransiciones().size()-1; i>=0;i--)
        {
            transiciones.push(afnd.getInicial().getTransiciones().remove(i));
        }
        
        Transicion tran = new Transicion(afnd.getInicial(), afnd.getInicial(), "#");
        afnd.getInicial().addTransiciones(tran);
        
        while(!transiciones.isEmpty())
        {
            afnd.getInicial().addTransiciones((Transicion) transiciones.pop());
        }
    }
    
    public Automata getAfnd()
    {
        return afnd;
    }

    public void setAfnd(Automata afnd)
    {
        this.afnd = afnd;
    }

    public String getExpresionRegular()
    {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular)
    {
        this.expresionRegular = expresionRegular;
    }
    
    
}
