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

    public AutomataProcessor processor;

    Automata automataDeterministico;

    public AFD() {
        this.automataDeterministico = new Automata();
        this.processor = new AutomataProcessor();
    }

    public void AFNDtoAFD(Automata afnd) {
        Estado first = new Estado(0);

        Automata automata = new Automata();
        Queue<HashSet<Estado>> cola = new LinkedList();
        automata.setInicial(first);
        automata.addEstados(first);
        HashSet<Estado> array_inicial = processor.eClosure(afnd.getInicial());
        for (int i = 0; i < afnd.getEstadosAceptacion().size(); i++) {
            Estado aceptacion = afnd.getEstadosAceptacion().get(i);
            if (array_inicial.contains(aceptacion)) {
                automata.addEstadosAceptacion(first);
            }
        }
        cola.add(array_inicial);
        ArrayList<HashSet<Estado>> temporal = new ArrayList();
        int indexEstadoInicio = 0;
        while (!cola.isEmpty()) {
            HashSet<Estado> actual = cola.poll();
            for (Object elemento : afnd.getAlfabeto()) {
                HashSet<Estado> move_result = processor.move(actual, (String) elemento);

                HashSet<Estado> resultado = new HashSet();
                for (Estado e : move_result) {
                    resultado.addAll(processor.eClosure(e));
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
                    for (Estado aceptacion : afnd.getEstadosAceptacion()) {
                        if (resultado.contains(aceptacion)) {
                            automata.addEstadosAceptacion(nuevo);
                        }
                    }
                }
            }
            indexEstadoInicio++;
        }

        this.automataDeterministico = automata;
        definirAlfabeto(afnd);
        this.automataDeterministico.setTipo("AFD");
    }

    public Automata quitarEstadosTrampa(Automata afd) {
        ArrayList<Estado> estadosAQuitar = new ArrayList();
        for (int i = 0; i < afd.getEstados().size(); i++) {
            int verificarCantidadTransiciones = afd.getEstados().get(i).getAristas().size();
            int contadorTransiciones = 0;
            for (Arista t : (ArrayList<Arista>) afd.getEstados().get(i).getAristas()) {
                if (afd.getEstados().get(i) == t.getFin()) {
                    contadorTransiciones++;
                }

            }
            if (verificarCantidadTransiciones == contadorTransiciones && contadorTransiciones != 0) {

                estadosAQuitar.add(afd.getEstados().get(i));
            }

        }
        for (int i = 0; i < estadosAQuitar.size(); i++) {
            for (int j = 0; j < afd.getEstados().size(); j++) {
                ArrayList<Arista> arrayT = afd.getEstados().get(j).getAristas();
                int cont = 0;
                while (arrayT.size() > cont) {
                    Arista t = arrayT.get(cont);
                    if (t.getFin() == estadosAQuitar.get(i)) {
                        afd.getEstados().get(j).getAristas().remove(t);
                        cont--;
                    }
                    cont++;

                }
            }
            afd.getEstados().remove(estadosAQuitar.get(i));
        }
        for (int i = 0; i < afd.getEstados().size(); i++) {
            afd.getEstados().get(i).setId(i);
        }

        return afd;
    }

    public void agregarSignoAlfabetoGeneral() {
        Stack<Arista> transiciones = new Stack();
        for (int i = automataDeterministico.getInicial().getAristas().size() - 1; i >= 0; i--) {
            transiciones.push(automataDeterministico.getInicial().getAristas().remove(i));
        }

        Arista tran = new Arista(automataDeterministico.getInicial(), automataDeterministico.getInicial(), "#");
        automataDeterministico.getInicial().addAristas(tran);

        while (!transiciones.isEmpty()) {
            automataDeterministico.getInicial().addAristas((Arista) transiciones.pop());
        }
    }

    private void definirAlfabeto(Automata afn) {
        this.automataDeterministico.setAlfabeto(afn.getAlfabeto());
    }

    public Automata getAutomataDeterministico() {
        return automataDeterministico;
    }
}
