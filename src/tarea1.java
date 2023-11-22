// Modifica la clase tarea1 para incluir la conversión de AFND a GLC

import java.util.Scanner;

public class tarea1 {

    public static void main(String[] args) {
        System.out.println("INGRESA LA ER A CONVERTIR: ");
        Scanner sc = new Scanner(System.in);
        String ER = sc.nextLine();
        if (ER.length() > 0) {
            ExpresionRegularPostfijo convertidorER = new ExpresionRegularPostfijo();
            ER = convertidorER.convertir(ER);
            AFND thomson = new AFND(ER);
            thomson.crearAFND();
            Automata afnd = thomson.getAutomata();
            System.out.println("AFND:");
            System.out.println(afnd);

            // Conversión de AFND a GLC
            AFNDtoGLCConverter afndToGLCConverter = new AFNDtoGLCConverter();
            GramaticaLibreContexto glc = afndToGLCConverter.convertirAFNDtoGLC(afnd);
            System.out.println("Gramática Libre de Contexto resultante:");
            System.out.println(glc);
        } else {
            System.out.println("Debes entregar una expresión regular!!");
        }
    }
}
