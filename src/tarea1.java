/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */
public class tarea1 {

    public static void main(String[] args) {
        if (args.length == 1) {
            String ER_recibida = args[0];
            ExpresionRegularPostfijo convertidorER = new ExpresionRegularPostfijo();
            ER_recibida = convertidorER.convertir(ER_recibida);
            AFND thomson = new AFND(ER_recibida);
            thomson.crearAFND();
            // afnd
            thomson.agregarSignoAlfabetoGeneral();
            Automata afnd = thomson.getAutomata();
            System.out.println(afnd);
            AFD afd = new AFD();
            afd.AFNDtoAFD(afnd);
            afd.agregarSignoAlfabetoGeneral();
            Automata afd_res = afd.getAutomataDeterministico();
            System.out.println(afd_res);
        }

        else {
            System.out.println("debes entregar una expresion regular en los argumentos");
        }
    }

}
