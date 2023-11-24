// Modifica la clase tarea1 para incluir la conversión de AFND a GLC

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
        if (ER.length() > 0) {
            ExpresionRegularPostfijo convertidorER = new ExpresionRegularPostfijo();
            ER = convertidorER.convertir(ER);
            AFND thomson = new AFND(ER);
            thomson.crearAFND();
            Automata afnd = thomson.getAutomata();
            // System.out.println("AFND:");
            // System.out.println(afnd);

            // Conversión de AFND a GLC
            AFNDtoGLCConverter afndToGLCConverter = new AFNDtoGLCConverter();
            GramaticaLibreContexto glc = afndToGLCConverter.convertirAFNDtoGLC(afnd);
            System.out.println("GRAMATICA LIBRE DE CONTEXTO");

            System.out.println(glc);
            // Conversión de GLC a AP

            GLCtoAPConverter glcToAPConverter = new GLCtoAPConverter();
            AutomataPila ap = glcToAPConverter.convertirGLCaAP(glc);
            // System.out.println("Autómata de Pila resultante:");
            // System.out.println(ap);

        } else {
            System.out.println("Debes entregar una expresión regular!!");
        }
    }
}
