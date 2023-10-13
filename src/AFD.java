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
                    for (Estado aceptacion : afnd.getestadosAceptados()) {
                        if (resultado.contains(aceptacion)) {
                            automata.addestadosAceptados(nuevo);
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
