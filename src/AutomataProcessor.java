import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class AutomataProcessor {
    Estado inicial;
    ArrayList<Estado> aceptacion;
    HashSet<Estado> conjunto;

    public AutomataProcessor() {
    }

    public HashSet<Estado> eClosure(Estado eClosureEstado) {
        Stack<Estado> pilaClosure = new Stack();
        Estado actual = eClosureEstado;
        actual.getTransiciones();
        HashSet<Estado> resultado = new HashSet();

        pilaClosure.push(actual);
        while (!pilaClosure.isEmpty()) {
            actual = pilaClosure.pop();

            for (Transicion t : (ArrayList<Transicion>) actual.getTransiciones()) {
                if (t.getElemento() == "_" && !resultado.contains(t.getFin())) {
                    resultado.add(t.getFin());
                    pilaClosure.push(t.getFin());
                }
            }
        }
        resultado.add(eClosureEstado);
        return resultado;
    }

    public HashSet<Estado> move(HashSet<Estado> estados, String simbolo) {
        HashSet<Estado> alcanzados = new HashSet();
        Iterator<Estado> iterador = estados.iterator();
        while (iterador.hasNext()) {
            for (Transicion tranAux : (ArrayList<Transicion>) iterador.next().getTransiciones()) {
                Estado siguiente = tranAux.getFin();
                if (tranAux.getElemento().equals(simbolo)) {
                    alcanzados.add(siguiente);
                }
            }
        }
        return alcanzados;

    }

    public Estado move(Estado estado, String simbolo) {
        ArrayList<Estado> alcanzados = new ArrayList();

        for (Transicion t : (ArrayList<Transicion>) estado.getTransiciones()) {
            Estado siguiente = t.getFin();
            String simb = (String) t.getElemento();

            if (simb.equals(simbolo) && !alcanzados.contains(siguiente)) {
                alcanzados.add(siguiente);
            }

        }

        return alcanzados.get(0);
    }

    // mettodo para simular un automata sin importar si es afd o afnd

    public boolean simular(String regex, Automata automata) {
        inicial = automata.getInicial();
        aceptacion = new ArrayList(automata.getEstadosAceptacion());
        conjunto = eClosure(inicial);
        int inicioCalce = 0, finCalce = 0, cont = 0;
        boolean anterior = false;

        for (Character ch : regex.toCharArray()) {
            if (!automata.getAlfabeto().contains(ch.toString())) {
                if (evaluarAceptacion(aceptacion, conjunto) && anterior) {
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
            if (anterior && !evaluarAceptacion(aceptacion, conjunto) && conjunto.size() == 0) {
                finCalce = cont - 1;
                if (inicioCalce <= finCalce)
                    System.out.println(inicioCalce + " " + finCalce);
                inicioCalce = cont;
                regresarInicioAutomata(automata);
                obtenerEstadosAlcanzados(ch);
                anterior = false;
            }
            if (conjunto.isEmpty()) {
                inicioCalce = cont;
                regresarInicioAutomata(automata);
                obtenerEstadosAlcanzados(ch);
            }
            if (evaluarAceptacion(aceptacion, conjunto)) {
                anterior = true;
            }

            cont++;
        }

        if (evaluarAceptacion(aceptacion, conjunto)) {
            finCalce = cont - 1;
            if (inicioCalce <= finCalce)
                System.out.println(inicioCalce + " " + finCalce);
        }

        return true;
    }

    public void obtenerEstadosAlcanzados(Character ch) {
        conjunto = move(conjunto, ch.toString());
        HashSet<Estado> temp = new HashSet();
        Iterator<Estado> iter = conjunto.iterator();

        while (iter.hasNext()) {
            Estado siguiente = iter.next();
            temp.addAll(eClosure(siguiente));
        }
        conjunto = temp;
    }

    public void regresarInicioAutomata(Automata automata) {

        inicial = automata.getInicial();
        conjunto = eClosure(inicial);
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
