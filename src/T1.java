/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class T1 {
    public static void main(String[] args) {

        if (args.length > 0) {
            String expresion = args[0];

            Convertidor convertidor = new Convertidor();
            expresion = convertidor.convertir(expresion);

            AFND algoritmoThomson = new AFND(expresion);

            algoritmoThomson.crearAFND();

            // afnd
            algoritmoThomson.agregarSignoAlfabetoGeneral();
            Automata resultado_afnd = algoritmoThomson.getAutomata();

            System.out.println(resultado_afnd);

            AFD afd = new AFD();

            afd.convertirAFND_AFD(resultado_afnd);
            afd.agregarSignoAlfabetoGeneral();

            Automata resultado_afd = afd.getAutomataDeterministico();
            System.out.println(resultado_afd);

        } else {
            System.out.println("debes entregar una expresion regular en los argumentos (EJEMPLO: java T1 a.b.c)");
        }
    }

}
