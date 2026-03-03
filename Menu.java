import Procesos.PlanificacionGarantizadaGio;
import Procesos.PrioridadesGio;
import java.util.Scanner;
public class Menu {

    public static void main (String[] args){

        int opcion;
        Scanner sc = new Scanner (System.in);

        System.out.println("Elije el proceso a realizar");
        System.out.println("1.Round Robin");
        System.out.println("2.Prioridades");
        System.out.println("3.Múltiples colas de prioridades");
        System.out.println("4.Proceso más corto primero");
        System.out.println("5.Planificación garantizada");
        System.out.println("6.Boletos de Lotería");
        System.out.println("7.Planificación equitativa");
        System.out.println("8. salir");
        opcion = sc.nextInt();

        do { 
             switch (opcion) {
                case 1:
                
                
                break;

                 case 2:
                  
                 PrioridadesGio.inicio(args);
                
                break;

                 case 3:
                
                
                break;

                 case 4:
               
                
                break;

                 case 5:

                 PlanificacionGarantizadaGio.inicio(args);
                
                
                break;

                 case 6:
             
                
                break;

                 case 7:
               
                
                break;
                case 8:
               
                
                break;
        
            default:
                break;
        }

        } while (opcion != 10);

        
    }


    
}
