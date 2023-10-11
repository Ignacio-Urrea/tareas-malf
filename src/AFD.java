import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class AFD {
    private Automata afd;
    private final Simulador simulador;

    public AFD() {
        this.afd = new Automata();
        this.simulador = new Simulador();
    }

    public void convertirAFND_AFD(Automata afnd) {
        Automata automata = new Automata();
        Queue<HashSet<Estado>> cola = new LinkedList();

        Estado inicial = new Estado(0);
        automata.setInicial(inicial);
        automata.addEstados(inicial);
        HashSet<Estado> array_inicial = simulador.eClosure(afnd.getInicial());
        for (int i = 0; i < afnd.getEstadosAceptacion().size(); i++) {
            Estado aceptacion = afnd.getEstadosAceptacion().get(i);
            if (array_inicial.contains(aceptacion)) {
                automata.addEstadosAceptacion(inicial);
            }
        }
        cola.add(array_inicial);
        ArrayList<HashSet<Estado>> temporal = new ArrayList();
        int indexEstadoInicio = 0;
        while (!cola.isEmpty()) {
            HashSet<Estado> actual = cola.poll();
            for (Object elemento : afnd.getAlfabeto()) {
                HashSet<Estado> move_result = simulador.move(actual, (String) elemento);

                HashSet<Estado> resultado = new HashSet();
                for (Estado e : move_result) {
                    resultado.addAll(simulador.eClosure(e));
                }

                Estado anterior = (Estado) automata.getEstados().get(indexEstadoInicio);
                if (temporal.contains(resultado)) {
                    ArrayList<Estado> array_viejo = automata.getEstados();
                    Estado estado_viejo = anterior;
                    Estado estado_siguiente = array_viejo.get(temporal.indexOf(resultado) + 1);

                    estado_viejo.addTransiciones(new Transicion(estado_viejo, estado_siguiente, (String) elemento));

                } else {
                    temporal.add(resultado);
                    cola.add(resultado);

                    Estado nuevo = new Estado(temporal.indexOf(resultado) + 1);
                    anterior.addTransiciones(new Transicion(anterior, nuevo, (String) elemento));
                    automata.addEstados(nuevo);
                    for (Estado aceptacion : afnd.getEstadosAceptacion()) {
                        if (resultado.contains(aceptacion)) {
                            automata.addEstadosAceptacion(nuevo);
                        }
                    }
                }
            }
            indexEstadoInicio++;
        }

        this.afd = automata;
        definirAlfabeto(afnd);
        this.afd.setTipo("AFD");
    }

    /**
     * Método para quitar los estados de trampa de un autómata
     * 
     * @param afd
     * @return AFD con menos estados
     */
    public Automata quitarEstadosTrampa(Automata afd) {
        ArrayList<Estado> estadoAQuitar = new ArrayList();
        for (int i = 0; i < afd.getEstados().size(); i++) {
            int verificarCantidadTransiciones = afd.getEstados().get(i).getTransiciones().size();
            int contadorTransiciones = 0;
            for (Transicion t : (ArrayList<Transicion>) afd.getEstados().get(i).getTransiciones()) {
                if (afd.getEstados().get(i) == t.getFin()) {
                    contadorTransiciones++;
                }

            }
            if (verificarCantidadTransiciones == contadorTransiciones && contadorTransiciones != 0) {

                estadoAQuitar.add(afd.getEstados().get(i));
            }

        }
        for (int i = 0; i < estadoAQuitar.size(); i++) {
            for (int j = 0; j < afd.getEstados().size(); j++) {
                ArrayList<Transicion> arrayT = afd.getEstados().get(j).getTransiciones();
                int cont = 0;
                while (arrayT.size() > cont) {
                    Transicion t = arrayT.get(cont);
                    if (t.getFin() == estadoAQuitar.get(i)) {
                        afd.getEstados().get(j).getTransiciones().remove(t);
                        cont--;
                    }
                    cont++;

                }
            }
            afd.getEstados().remove(estadoAQuitar.get(i));
        }
        for (int i = 0; i < afd.getEstados().size(); i++) {
            afd.getEstados().get(i).setId(i);
        }

        return afd;
    }

    public void agregarSignoAlfabetoGeneral() {
        Stack<Transicion> transiciones = new Stack();
        for (int i = afd.getInicial().getTransiciones().size() - 1; i >= 0; i--) {
            transiciones.push(afd.getInicial().getTransiciones().remove(i));
        }

        Transicion tran = new Transicion(afd.getInicial(), afd.getInicial(), "#");
        afd.getInicial().addTransiciones(tran);

        while (!transiciones.isEmpty()) {
            afd.getInicial().addTransiciones((Transicion) transiciones.pop());
        }
    }

    /**
     * Copiar el alfabeto del AFN al AFD
     * 
     * @param afn
     */
    private void definirAlfabeto(Automata afn) {
        this.afd.setAlfabeto(afn.getAlfabeto());
    }

    public Automata getAfd() {
        return afd;
    }
}
