import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */
public class AutomataProcessor {
    Estado estadoInicial;
    ArrayList<Estado> estadosAceptacion;
    HashSet<Estado> conjuntoEstados;

    public AutomataProcessor() {
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

    public boolean simular(String regex, Automata automata) {
        estadoInicial = automata.getInicial();
        estadosAceptacion = new ArrayList(automata.getestadosAceptados());
        conjuntoEstados = eClosure(estadoInicial);
        int inicioCalce = 0, finCalce = 0, cont = 0;
        boolean anterior = false;

        for (Character ch : regex.toCharArray()) {
            if (!automata.getAlfabeto().contains(ch.toString())) {
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
            if (anterior && !evaluarAceptacion(estadosAceptacion, conjuntoEstados) && conjuntoEstados.size() == 0) {
                finCalce = cont - 1;
                if (inicioCalce <= finCalce)
                    System.out.println(inicioCalce + " " + finCalce);
                inicioCalce = cont;
                regresarInicioAutomata(automata);
                obtenerEstadosAlcanzados(ch);
                anterior = false;
            }
            if (conjuntoEstados.isEmpty()) {
                inicioCalce = cont;
                regresarInicioAutomata(automata);
                obtenerEstadosAlcanzados(ch);
            }
            if (evaluarAceptacion(estadosAceptacion, conjuntoEstados)) {
                anterior = true;
            }

            cont++;
        }

        if (evaluarAceptacion(estadosAceptacion, conjuntoEstados)) {
            finCalce = cont - 1;
            if (inicioCalce <= finCalce)
                System.out.println(inicioCalce + " " + finCalce);
        }

        return true;
    }

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

    public void regresarInicioAutomata(Automata automata) {
        estadoInicial = automata.getInicial();
        conjuntoEstados = eClosure(estadoInicial);
    }

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
