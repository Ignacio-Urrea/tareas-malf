import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/**
 * Clase que representa un procesador para un autómata.
 */
public class AutomataProcessor {
    // Variables de instancia para el estado inicial y los estados de aceptación.
    Estado estadoInicial;
    ArrayList<Estado> estadosAceptacion;
    HashSet<Estado> conjuntoEstados;

    /**
     * Constructor por defecto.
     */
    public AutomataProcessor() {
    }

    /**
     * Método para realizar la operación move en un conjunto de estados dado un
     * símbolo.
     * 
     * @param estados Conjunto de estados.
     * @param simbolo Símbolo para realizar la operación move.
     * @return Conjunto de estados alcanzados por el símbolo.
     */
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

    /**
     * Método para realizar la operación move en un estado dado un símbolo.
     * 
     * @param estado  Estado.
     * @param simbolo Símbolo para realizar la operación move.
     * @return Estado alcanzado por el símbolo.
     */
    public Estado move(Estado estado, String simbolo) {
        ArrayList<Estado> alcanzados = new ArrayList();

        for (Arista t : (ArrayList<Arista>) estado.getAristas()) {
            Estado siguiente = t.getFin();
            String simb = (String) t.getCaracter();

            if (simb.equals(simbolo) && !alcanzados.contains(siguiente)) {
                alcanzados.add(siguiente);
            }
        }

        return alcanzados.get(0);
    }

    /**
     * Método para calcular eClosure de un estado.
     * 
     * @param eClosureEstado Estado para calcular eClosure.
     * @return Conjunto de estados alcanzados por eClosure.
     */
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

    /**
     * Método para simular el autómata dado una expresión regular.
     * 
     * @param regex    Expresión regular a ser simulada en el autómata.
     * @param automata Autómata a ser simulado.
     * @return true si la simulación es exitosa, false de lo contrario.
     */
    public boolean simular(String regex, Automata automata) {
        estadoInicial = automata.getInicial();
        estadosAceptacion = new ArrayList(automata.getestadosAceptados());
        conjuntoEstados = eClosure(estadoInicial);
        int inicioCalce = 0, finCalce = 0, cont = 0;
        boolean anterior = false;

        // Iterar a través de los caracteres de la expresión regular.
        for (Character ch : regex.toCharArray()) {
            // Verificar si el carácter está en el alfabeto del autómata.
            if (!automata.getAlfabeto().contains(ch.toString())) {
                // Verificar si los estados actuales son aceptados y si el estado anterior
                // también lo fue.
                if (evaluarAceptacion(estadosAceptacion, conjuntoEstados) && anterior) {
                    finCalce = cont - 1;
                    if (inicioCalce <= finCalce)
                        System.out.println(inicioCalce + " " + finCalce);
                }
                ch = '#';
                inicioCalce = cont + 1;
                regresarInicioAutomata(automata);
                anterior = false;
            }
            obtenerEstadosAlcanzados(ch);
            // Verificar si el estado anterior fue aceptado, pero los estados actuales no lo
            // son y el conjunto está vacío.
            if (anterior && !evaluarAceptacion(estadosAceptacion, conjuntoEstados) && conjuntoEstados.size() == 0) {
                finCalce = cont - 1;
                if (inicioCalce <= finCalce)
                    System.out.println(inicioCalce + " " + finCalce);
                inicioCalce = cont;
                regresarInicioAutomata(automata);
                obtenerEstadosAlcanzados(ch);
                anterior = false;
            }
            // Verificar si el conjunto de estados está vacío y reiniciar el automata.
            if (conjuntoEstados.isEmpty()) {
                inicioCalce = cont;
                regresarInicioAutomata(automata);
                obtenerEstadosAlcanzados(ch);
            }
            // Verificar si los estados actuales son aceptados.
            if (evaluarAceptacion(estadosAceptacion, conjuntoEstados)) {
                anterior = true;
            }

            cont++;
        }

        // Verificar si el último conjunto de estados es aceptado.
        if (evaluarAceptacion(estadosAceptacion, conjuntoEstados)) {
            finCalce = cont - 1;
            if (inicioCalce <= finCalce)
                System.out.println(inicioCalce + " " + finCalce);
        }

        return true;
    }

    /**
     * Método para obtener los estados alcanzados por un carácter y calcular
     * eClosure.
     * 
     * @param ch Carácter para realizar la operación move.
     */
    public void obtenerEstadosAlcanzados(Character ch) {
        conjuntoEstados = move(conjuntoEstados, ch.toString());
        HashSet<Estado> temp = new HashSet();
        Iterator<Estado> iter = conjuntoEstados.iterator();

        while (iter.hasNext()) {
            Estado siguiente = iter.next();
            temp.addAll(eClosure(siguiente));
        }
        conjuntoEstados = temp;
    }

    /**
     * Método para regresar al estado inicial del autómata y calcular su eClosure.
     * 
     * @param automata Autómata a ser reiniciado.
     */
    public void regresarInicioAutomata(Automata automata) {
        estadoInicial = automata.getInicial();
        conjuntoEstados = eClosure(estadoInicial);
    }

    /**
     * Método para evaluar si algún estado de aceptación está en el conjunto de
     * estados dados.
     * 
     * @param aceptacion Lista de estados de aceptación.
     * @param conjunto   Conjunto de estados dados.
     * @return true si algún estado de aceptación está presente en el conjunto,
     *         false de lo contrario.
     */
    public boolean evaluarAceptacion(ArrayList<Estado> aceptacion, HashSet<Estado> conjunto) {
        boolean res = false;

        for (Estado estado_aceptacion : aceptacion) {
            if (conjunto.contains(estado_aceptacion)) {
                res = true;
            }
        }
        return res;
    }
}
