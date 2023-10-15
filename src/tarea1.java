import java.util.Scanner;

/**
 * @author Juan Nuñez
 * @author Ignacio Urrea
 */
public class tarea1 {

    public static void main(String[] args) {
        System.out.println("INGRESA LA ER A CONVERTIR: ");
        Scanner sc = new Scanner(System.in);
        String ER = sc.nextLine();
        if (ER.length() > 1) {
            ExpresionRegularPostfijo convertidorER = new ExpresionRegularPostfijo();
            ER = convertidorER.convertir(ER);
            AFND thomson = new AFND(ER);
            thomson.crearAFND();
            Automata afnd = thomson.getAutomata();
            System.out.println(afnd);
            AFD afd = new AFD();
            afd.AFNDtoAFD(afnd);
            Automata afd_res = afd.getAutomataDeterministico();
            System.out.println(afd_res);
        }

        else {
            System.out.println("debes entregar una expresion regular !!");
        }
    }

}
