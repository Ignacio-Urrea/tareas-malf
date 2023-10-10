import java.util.Scanner;

public class T1 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

    if (args.length > 0) {
        // Obtener el primer argumento proporcionado en la lÃ­nea de comandos
        String primerArgumento = args[0];
        
        Scanner sc = new Scanner(System.in);
        String expresion=sc.nextLine();

        Simulador simulador = new Simulador();

        Convertidor convertidor = new Convertidor();
        expresion = convertidor.conversor(expresion);

        AFND algoritmoThomson = new AFND(expresion);
        
        algoritmoThomson.construirAFND();
        //AFND resultante
        algoritmoThomson.agregarSignoAlfabetoGeneral();
        Automata resultado_afnd = algoritmoThomson.getAfnd();

        System.out.println(resultado_afnd);

        AFD afd= new AFD();

        afd.convertirAFND_AFD(resultado_afnd);
        afd.agregarSignoAlfabetoGeneral();

        Automata resultado_afd = afd.getAfd();        
        System.out.println(resultado_afd);
        
        // Resto de tu cÃ³digo...
    } else {
        // Si no se proporcionan argumentos, imprimir un mensaje de error
        System.out.println("No se proporcionaron argumentos.");
    }
    }
    
}
