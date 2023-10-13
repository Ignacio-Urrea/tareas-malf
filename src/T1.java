/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */

public class T1 {

    public static void main(String[] args) {
        String ER_recibida = args[0];
        if (args.length == 1) {
            ConvertidorExpresionRegular convertidorER = new ConvertidorExpresionRegular();
            ER_recibida = convertidorER.convertir(ER_recibida);
            AFND algoritmoThomson = new AFND(ER_recibida);
            algoritmoThomson.crearAFND();
            // afnd
            algoritmoThomson.agregarSignoAlfabetoGeneral();
            Automata afnd = algoritmoThomson.getAutomata();
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
