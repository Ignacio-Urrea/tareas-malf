import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

// en base a esta clase crearemos los afd y afnd
public class Automata {
    HashSet alfabeto;
    String type;
    ArrayList<Estado> estadosAceptados;
    ArrayList<Estado> estados;
    Estado inicio;

    public Automata() {
        this.estadosAceptados = new ArrayList();
        this.alfabeto = new HashSet();
        this.estados = new ArrayList();

    }

    public void setAlfabeto(HashSet alfabeto) {
        this.alfabeto = alfabeto;
    }

    public String getTipo() {
        return type;
    }

    public void setTipo(String tipo) {
        this.type = tipo;
    }

    public ArrayList<Estado> getestadosAceptados() {
        return estadosAceptados;
    }

    public void addestadosAceptados(Estado fin) {
        this.estadosAceptados.add(fin);
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void addEstados(Estado estado) {
        this.estados.add(estado);
    }

    @Override
    public String toString() {
        String res = new String();
        res += "" + this.type + ":\r\n";
        res += "K = {";
        for (int i = 0; i < this.estados.size(); i++) {
            if (i == this.estados.size() - 1) {
                res += "q" + this.estados.get(i);
            } else {
                res += "q" + this.estados.get(i) + ",";
            }

        }
        res += "}\r\n";

        res += "sigma={";
        for (int i = 0; i < this.alfabeto.toArray().length; i++) {
            if (i == this.alfabeto.toArray().length - 1) {
                res += this.alfabeto.toArray()[i];
            } else {
                res += this.alfabeto.toArray()[i] + ",";
            }
        }
        res += "}\r\n";

        res += "Delta: \r\n";
        for (int i = 0; i < this.estados.size(); i++) {
            Estado est = estados.get(i);
            for (int j = 0; j < est.getAristas().size(); j++) {
                res += est.getAristas().get(j) + "\r\n";
            }

        }
        res += "s={q" + this.inicio + "}\r\n";
        res += "F={";
        for (int i = 0; i < this.estadosAceptados.size(); i++) {
            if (i == this.estadosAceptados.size() - 1) {
                res += "q" + this.estadosAceptados.get(i);
            } else {
                res += "q" + this.estadosAceptados.get(i) + ",";
            }

        }
        res += "}\r\n";
        res += "\r\n";
        return res;
    }

    public Estado getInicial() {
        return inicio;
    }

    public void setInicial(Estado inicio) {
        this.inicio = inicio;
    }

    public HashSet getAlfabeto() {
        return alfabeto;
    }

    public void crearAlfabeto(String regex) {
        for (Character ch : regex.toCharArray()) {
            if (ch != '|' && ch != '.' && ch != '*' && ch != '_' && ch != '~') {
                this.alfabeto.add(Character.toString(ch));
            }
        }
    }

}
