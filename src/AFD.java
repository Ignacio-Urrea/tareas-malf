import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class AFD {
    Automata automata;

    public AFD() {
        this.automata = new Automata();
    }

    public void AFNDtoAFD(Automata afnd) {
        Estado first = new Estado(0);

        Automata automata = new Automata();
        Queue<HashSet<Estado>> cola = new LinkedList();
        automata.setInicial(first);
        automata.addEstados(first);
        HashSet<Estado> array_inicial = this.eClosure(afnd.getInicial());
        for (int i = 0; i < afnd.getestadosAceptados().size(); i++) {
            Estado aceptacion = afnd.getestadosAceptados().get(i);
            if (array_inicial.contains(aceptacion)) {
                automata.addestadosAceptados(first);
            }
        }
        cola.add(array_inicial);
        ArrayList<HashSet<Estado>> temporal = new ArrayList();
        int indexEstadoInicio = 0;
        while (!cola.isEmpty()) {
            HashSet<Estado> actual = cola.poll();
            for (Object elemento : afnd.getAlfabeto()) {
                HashSet<Estado> move_result = this.move(actual, (String) elemento);

                HashSet<Estado> resultado = new HashSet();
                for (Estado e : move_result) {
                    resultado.addAll(this.eClosure(e));
                }

                Estado anterior = (Estado) automata.getEstados().get(indexEstadoInicio);
                if (temporal.contains(resultado)) {
                    ArrayList<Estado> array_viejo = automata.getEstados();
                    Estado estado_viejo = anterior;
                    Estado estado_siguiente = array_viejo.get(temporal.indexOf(resultado) + 1);

                    estado_viejo.addAristas(new Arista(estado_viejo, estado_siguiente, (String) elemento));

                } else {
                    temporal.add(resultado);
                    cola.add(resultado);

                    Estado nuevo = new Estado(temporal.indexOf(resultado) + 1);
                    anterior.addAristas(new Arista(anterior, nuevo, (String) elemento));
                    automata.addEstados(nuevo);
                    for (Estado aceptacion : afnd.getestadosAceptados()) {
                        if (resultado.contains(aceptacion)) {
                            automata.addestadosAceptados(nuevo);
                        }
                    }
                }
            }
            indexEstadoInicio++;
        }

        this.automata = automata;
        definirAlfabeto(afnd);
        this.automata.setTipo("AFD");
    }

    public HashSet<Estado> move(HashSet<Estado> estados, String simbolo) {
        HashSet<Estado> alcanzados = new HashSet();
        Iterator<Estado> iterator = estados.iterator();
        while (iterator.hasNext()) {
            for (Arista tranAux : (ArrayList<Arista>) iterator.next().getAristas()) {
                Estado siguiente = tranAux.getFin();
                if (tranAux.getCaracter().equals(simbolo)) {
                    alcanzados.add(siguiente);
                }
            }
        }
        return alcanzados;
    }

    public HashSet<Estado> eClosure(Estado eClosureEstado) {
        Stack<Estado> stackClosure = new Stack();
        Estado current = eClosureEstado;
        current.getAristas();
        HashSet<Estado> result = new HashSet();

        stackClosure.push(current);
        while (!stackClosure.isEmpty()) {
            current = stackClosure.pop();

            for (Arista t : (ArrayList<Arista>) current.getAristas()) {
                if (t.getCaracter() == "_" && !result.contains(t.getFin())) {
                    result.add(t.getFin());
                    stackClosure.push(t.getFin());
                }
            }
        }
        result.add(eClosureEstado);
        return result;
    }

    private void definirAlfabeto(Automata afn) {
        this.automata.setAlfabeto(afn.getAlfabeto());
    }

    public Automata getAutomataDeterministico() {
        return automata;
    }
}
