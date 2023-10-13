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
                kleeneAutomata = kleene(pilaAFND.pop());
                pilaAFND.push(kleeneAutomata);
                this.automata = kleeneAutomata;
            } else if (c == '.') {
                primerAutomata = pilaAFND.pop();
                segundoAutomata = pilaAFND.pop();
                Automata concatenacionAutomata = AFND_CONCATENACION(primerAutomata, segundoAutomata);
                pilaAFND.push(concatenacionAutomata);
                this.automata = concatenacionAutomata;
            } else if (c == '|') {
                primerAutomata = pilaAFND.pop();
                segundoAutomata = pilaAFND.pop();
                Automata unionAutomata = AFND_UNION(primerAutomata, segundoAutomata);
                pilaAFND.push(unionAutomata);
                this.automata = unionAutomata;
            } else if (c == '~') {
                Automata afndVacio = E_AFND();
                pilaAFND.push(afndVacio);
                this.automata = afndVacio;
            } else {
                Automata afndSimple = AFND1(c);
                pilaAFND.push(afndSimple);
                this.automata = afndSimple;
            }
        }

        this.automata.crearAlfabeto(expresionRegular);
        this.automata.setTipo("AFND");
    }

    public Automata AFND1(char elemento) {
        Automata automataAux = new Automata();
        Estado inicio = new Estado(0);
        Estado fin = new Estado(1);
        Arista tran = new Arista(inicio, fin, String.valueOf(elemento));
        inicio.addAristas(tran);
        automataAux.addEstados(inicio);
        automataAux.addEstados(fin);

        automataAux.setInicial(inicio);
        automataAux.addestadosAceptados(fin);
        return automataAux;
    }

    public Automata E_AFND() {
        Automata automataAux = new Automata();
        Estado inicio = new Estado(0);
        Estado fin = new Estado(1);
        automataAux.addEstados(inicio);
        automataAux.addEstados(fin);
        automataAux.setInicial(inicio);
        automataAux.addestadosAceptados(fin);
        return automataAux;
    }

    public Automata AFND_UNION(Automata primerAutomata, Automata segundoAutomata) {
        Automata unionAutomata = new Automata();
        Estado inicio = new Estado(0);

        Arista tran = new Arista(inicio, segundoAutomata.getInicial(), "_");

        inicio.addAristas(tran);

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
        unionAutomata.addestadosAceptados(fin);

        Estado anteriorInicio = primerAutomata.getInicial();
        ArrayList<Estado> anteriorFin = primerAutomata.getestadosAceptados();
        ArrayList<Estado> anteriorFin2 = segundoAutomata.getestadosAceptados();

        Arista tranAux = new Arista(inicio, anteriorInicio, "_");
        inicio.getAristas().add(tranAux);

        for (int k = 0; k < anteriorFin.size(); k++) {
            tranAux = new Arista(anteriorFin.get(k), fin, "_");
            anteriorFin.get(k).getAristas().add(tranAux);
        }

        for (int k = 0; k < anteriorFin2.size(); k++) {
            tranAux = new Arista(anteriorFin2.get(k), fin, "_");
            anteriorFin2.get(k).getAristas().add(tranAux);
        }

        HashSet<String> alfabeto = new HashSet<>();
        alfabeto.addAll(primerAutomata.getAlfabeto());
        alfabeto.addAll(segundoAutomata.getAlfabeto());
        unionAutomata.setAlfabeto(alfabeto);

        return unionAutomata;
    }

    public Automata AFND_CONCATENACION(Automata primerAutomata, Automata segundoAutomata) {
        Automata concatenacionAutomata = new Automata();
        int i;
        for (i = 0; i < segundoAutomata.getEstados().size(); i++) {
            Estado estadoAux = segundoAutomata.getEstados().get(i);
            estadoAux.setId(i);

            if (i == 0) {
                concatenacionAutomata.setInicial(estadoAux);
            }

            if (i == segundoAutomata.getEstados().size() - 1) {
                for (int j = 0; j < segundoAutomata.getestadosAceptados().size(); j++) {
                    Arista tranAux = new Arista(segundoAutomata.getestadosAceptados().get(j),
                            primerAutomata.getInicial(), "_");
                    estadoAux.addAristas(tranAux);
                }
            }
            concatenacionAutomata.addEstados(estadoAux);
        }

        for (int j = 0; j < primerAutomata.getEstados().size(); j++) {
            Estado estadoAux = primerAutomata.getEstados().get(j);
            estadoAux.setId(i);

            if (primerAutomata.getEstados().size() - 1 == j) {
                concatenacionAutomata.addestadosAceptados(estadoAux);
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

    private Automata kleene(Automata afnd) {
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
        kleeneAutomata.addestadosAceptados(fin);

        Estado inicioAnterior = afnd.getInicial();
        ArrayList<Estado> finAnterior = afnd.getestadosAceptados();

        inicio.getAristas().add(new Arista(inicio, inicioAnterior, "_"));
        inicio.getAristas().add(new Arista(inicio, fin, "_"));

        for (int i = 0; i < finAnterior.size(); i++) {
            finAnterior.get(i).getAristas().add(new Arista(finAnterior.get(i), inicioAnterior, "_"));
            finAnterior.get(i).getAristas().add(new Arista(finAnterior.get(i), fin, "_"));
        }
        kleeneAutomata.setAlfabeto(afnd.getAlfabeto());
        return kleeneAutomata;
    }

    public void agregarSignoAlfabetoGeneral() {
        Stack<Arista> transiciones = new Stack<>();
        for (int i = automata.getInicial().getAristas().size() - 1; i >= 0; i--) {
            transiciones.push(automata.getInicial().getAristas().remove(i));
        }

        Arista tran = new Arista(automata.getInicial(), automata.getInicial(), "#");
        automata.getInicial().addAristas(tran);

        while (!transiciones.isEmpty()) {
            automata.getInicial().addAristas(transiciones.pop());
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
