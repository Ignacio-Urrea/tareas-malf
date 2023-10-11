import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */
public class AFND {
    private Automata automata;
    private String expresionRegular;

    public AFND(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public void crearAFND() {
        Stack<Automata> pilaAFND = new Stack<>();

        Automata primerAutomata, kleeneAutomata;

        Automata segundoAutomata;
        for (int i = 0; i < this.expresionRegular.length(); i++) {
            char c = this.expresionRegular.charAt(i);
            if (c == '*') {
                kleeneAutomata = clausuraKleene(pilaAFND.pop());
                pilaAFND.push(kleeneAutomata);
                this.automata = kleeneAutomata;
            } else if (c == '.') {
                primerAutomata = pilaAFND.pop();
                segundoAutomata = pilaAFND.pop();
                Automata concatenacionAutomata = afndConcatenacion(primerAutomata, segundoAutomata);
                pilaAFND.push(concatenacionAutomata);
                this.automata = concatenacionAutomata;
            } else if (c == '|') {
                primerAutomata = pilaAFND.pop();
                segundoAutomata = pilaAFND.pop();
                Automata unionAutomata = afndUnion(primerAutomata, segundoAutomata);
                pilaAFND.push(unionAutomata);
                this.automata = unionAutomata;
            } else if (c == '~') {
                Automata afndVacio = afndVacio();
                pilaAFND.push(afndVacio);
                this.automata = afndVacio;
            } else {
                Automata afndSimple = afndSimple(c);
                pilaAFND.push(afndSimple);
                this.automata = afndSimple;
            }
        }

        this.automata.crearAlfabeto(expresionRegular);
        this.automata.setTipo("AFND");
    }

    public Automata afndSimple(char elemento) {
        Automata automataAux = new Automata();
        Estado inicio = new Estado(0);
        Estado fin = new Estado(1);
        Transicion tran = new Transicion(inicio, fin, String.valueOf(elemento));
        inicio.addTransiciones(tran);
        automataAux.addEstados(inicio);
        automataAux.addEstados(fin);

        automataAux.setInicial(inicio);
        automataAux.addEstadosAceptacion(fin);
        return automataAux;
    }

    public Automata afndVacio() {
        Automata automataAux = new Automata();
        Estado inicio = new Estado(0);
        Estado fin = new Estado(1);
        automataAux.addEstados(inicio);
        automataAux.addEstados(fin);
        automataAux.setInicial(inicio);
        automataAux.addEstadosAceptacion(fin);
        return automataAux;
    }

    public Automata afndUnion(Automata primerAutomata, Automata segundoAutomata) {
        Automata unionAutomata = new Automata();
        Estado inicio = new Estado(0);

        Transicion tran = new Transicion(inicio, segundoAutomata.getInicial(), "_");

        inicio.addTransiciones(tran);

        unionAutomata.addEstados(inicio);
        unionAutomata.setInicial(inicio);

        int i;

        for (i = 0; i < primerAutomata.getEstados().size(); i++) {
            Estado estadoAux = primerAutomata.getEstados().get(i);
            estadoAux.setId(i + 1);
            unionAutomata.addEstados(estadoAux);
        }

        for (int j = 0; j < segundoAutomata.getEstados().size(); j++) {
            Estado estadoAux = segundoAutomata.getEstados().get(j);
            estadoAux.setId(i + 1);
            unionAutomata.addEstados(estadoAux);
            i++;
        }

        Estado fin = new Estado(primerAutomata.getEstados().size() + segundoAutomata.getEstados().size() + 1);
        unionAutomata.addEstados(fin);
        unionAutomata.addEstadosAceptacion(fin);

        Estado anteriorInicio = primerAutomata.getInicial();
        ArrayList<Estado> anteriorFin = primerAutomata.getEstadosAceptacion();
        ArrayList<Estado> anteriorFin2 = segundoAutomata.getEstadosAceptacion();

        Transicion tranAux = new Transicion(inicio, anteriorInicio, "_");
        inicio.getTransiciones().add(tranAux);

        for (int k = 0; k < anteriorFin.size(); k++) {
            tranAux = new Transicion(anteriorFin.get(k), fin, "_");
            anteriorFin.get(k).getTransiciones().add(tranAux);
        }

        for (int k = 0; k < anteriorFin2.size(); k++) {
            tranAux = new Transicion(anteriorFin2.get(k), fin, "_");
            anteriorFin2.get(k).getTransiciones().add(tranAux);
        }

        HashSet<String> alfabeto = new HashSet<>();
        alfabeto.addAll(primerAutomata.getAlfabeto());
        alfabeto.addAll(segundoAutomata.getAlfabeto());
        unionAutomata.setAlfabeto(alfabeto);

        return unionAutomata;
    }

    public Automata afndConcatenacion(Automata primerAutomata, Automata segundoAutomata) {
        Automata concatenacionAutomata = new Automata();
        int i;
        for (i = 0; i < segundoAutomata.getEstados().size(); i++) {
            Estado estadoAux = segundoAutomata.getEstados().get(i);
            estadoAux.setId(i);

            if (i == 0) {
                concatenacionAutomata.setInicial(estadoAux);
            }

            if (i == segundoAutomata.getEstados().size() - 1) {
                for (int j = 0; j < segundoAutomata.getEstadosAceptacion().size(); j++) {
                    Transicion tranAux = new Transicion(segundoAutomata.getEstadosAceptacion().get(j),
                            primerAutomata.getInicial(), "_");
                    estadoAux.addTransiciones(tranAux);
                }
            }
            concatenacionAutomata.addEstados(estadoAux);
        }

        for (int j = 0; j < primerAutomata.getEstados().size(); j++) {
            Estado estadoAux = primerAutomata.getEstados().get(j);
            estadoAux.setId(i);

            if (primerAutomata.getEstados().size() - 1 == j) {
                concatenacionAutomata.addEstadosAceptacion(estadoAux);
            }
            concatenacionAutomata.addEstados(estadoAux);
            i++;
        }

        HashSet<String> alfabeto = new HashSet<>();
        alfabeto.addAll(primerAutomata.getAlfabeto());
        alfabeto.addAll(segundoAutomata.getAlfabeto());
        concatenacionAutomata.setAlfabeto(alfabeto);

        return concatenacionAutomata;
    }

    private Automata clausuraKleene(Automata afnd) {
        Automata kleeneAutomata = new Automata();

        Estado inicio = new Estado(0);
        kleeneAutomata.addEstados(inicio);
        kleeneAutomata.setInicial(inicio);

        for (int i = 0; i < afnd.getEstados().size(); i++) {
            Estado estadoAux = afnd.getEstados().get(i);
            estadoAux.setId(i + 1);
            kleeneAutomata.addEstados(estadoAux);
        }

        Estado fin = new Estado(afnd.getEstados().size() + 1);
        kleeneAutomata.addEstados(fin);
        kleeneAutomata.addEstadosAceptacion(fin);

        Estado inicioAnterior = afnd.getInicial();
        ArrayList<Estado> finAnterior = afnd.getEstadosAceptacion();

        inicio.getTransiciones().add(new Transicion(inicio, inicioAnterior, "_"));
        inicio.getTransiciones().add(new Transicion(inicio, fin, "_"));

        for (int i = 0; i < finAnterior.size(); i++) {
            finAnterior.get(i).getTransiciones().add(new Transicion(finAnterior.get(i), inicioAnterior, "_"));
            finAnterior.get(i).getTransiciones().add(new Transicion(finAnterior.get(i), fin, "_"));
        }
        kleeneAutomata.setAlfabeto(afnd.getAlfabeto());
        return kleeneAutomata;
    }

    public void agregarSignoAlfabetoGeneral() {
        Stack<Transicion> transiciones = new Stack<>();
        for (int i = automata.getInicial().getTransiciones().size() - 1; i >= 0; i--) {
            transiciones.push(automata.getInicial().getTransiciones().remove(i));
        }

        Transicion tran = new Transicion(automata.getInicial(), automata.getInicial(), "#");
        automata.getInicial().addTransiciones(tran);

        while (!transiciones.isEmpty()) {
            automata.getInicial().addTransiciones(transiciones.pop());
        }
    }

    public Automata getAutomata() {
        return automata;
    }

    public void setAutomata(Automata automata) {
        this.automata = automata;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }
}
