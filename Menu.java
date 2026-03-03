import ProcesosO.LoteriaScheduler;
import ProcesosO.PlanificacionGarantizadaGio;
import ProcesosO.PrioridadesGio;
import ProcesosO.menu;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        int opcion;
        Scanner sc = new Scanner(System.in);

        // Displaying the menu
        System.out.println("Elije el proceso a realizar:");
        System.out.println("1. Round Robin y Proceso más corto primero");
        System.out.println("2. Prioridades");
        System.out.println("3. Múltiples colas de prioridades");
        System.out.println("4. Planificación garantizada");
        System.out.println("5. Boletos de Lotería");
        System.out.println("6. Planificación equitativa");
        System.out.println("7. Salir");
        opcion = sc.nextInt();

   
        do {
            switch (opcion) {
                case 1:
                    menu.inicio(args);
                    break;

                case 2:
                    PrioridadesGio.inicio(args);
                    break;

                case 3:
                    
                    System.out.println("Múltiples colas de prioridades:");
                    break;

                case 4:
                    PlanificacionGarantizadaGio.inicio(args);
                    break;

                case 5:
                    LoteriaScheduler.main(args);
                    break;

                case 6:
                    
                    System.out.println("Planificación equitativa:");
                    break;

                case 7:
                    System.out.println("Bye, BYE");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
                    break;
            }

            
            if (opcion != 7) {
                System.out.println("Elije el proceso a realizar:");
                System.out.println("1. Round Robin y Proceso más corto primero");
                System.out.println("2. Prioridades");
                System.out.println("3. Múltiples colas de prioridades");
                System.out.println("4. Planificación garantizada");
                System.out.println("5. Boletos de Lotería");
                System.out.println("6. Planificación equitativa");
                System.out.println("7. Salir");
                opcion = sc.nextInt();
            }

        } while (opcion != 7);

        
        sc.close();
    }
}