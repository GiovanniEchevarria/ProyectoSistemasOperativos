package ProcesosO;
import java.util.Random;
import java.util.Scanner;

public class menu{

    public static void inicio(String[] args) {

        Scanner leer = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=================================");
            System.out.println("   SIMULADOR PLANIFICADOR CPU");
            System.out.println("=================================");
            System.out.println("1. Round Robin");
            System.out.println("2. SJF");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leer.nextInt();

            switch (opcion) {

                case 1:
                    ejecutarRobin();
                    break;

                case 2:
                    ejecutarSJF();
                    break;

                case 3:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 3);
    }

    // ===============================
    // ROUND ROBIN (TU LÓGICA)
    // ===============================
    public static void ejecutarRobin(){

        Random ram = new Random();

        int n = ram.nextInt(1, 25);
        int quantum = ram.nextInt(1, 10);

        int [] procesos = new int[n];  
        int [] datos = new int[n];
        int [] tiempoEspera = new int[n];
        int [] tiempoRetorno = new int[n];

        System.out.println("\n-*-*-*- Round Robin -*-*-*-");
        System.out.println("Quantum: " + quantum);

        // Generar procesos
        for (int i = 0; i < n; i++) {
            procesos[i] = ram.nextInt(1, 20);
            datos[i] = procesos[i];

            System.out.println("Proceso " + (i + 1) + ": " + procesos[i] + " unidades de tiempo");
        }

        int tiempo = 0;
        boolean procesosCompletados;

        do {
            procesosCompletados = true;

            for (int i = 0; i < n; i++) {

                if (datos[i] > 0) {
                    procesosCompletados = false;

                    if (datos[i] > quantum) {
                        tiempo += quantum;
                        datos[i] -= quantum;
                    } else {
                        tiempo += datos[i];
                        tiempoEspera[i] = tiempo - procesos[i];
                        tiempoRetorno[i] = tiempo;
                        datos[i] = 0;
                    }
                }
            }

        } while (!procesosCompletados);

        double calcularPromedio = 0;

        System.out.println("\nProceso\tTiempo de Espera\tTiempo de Retorno");

        for(int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + tiempoEspera[i] + 
                                       "\t\t\t" + tiempoRetorno[i]);
            calcularPromedio += tiempoEspera[i];
        }

        System.out.println("\nTiempo de Espera Promedio: " + (calcularPromedio / n));
    }


    // ===============================
    // SJF (TU LÓGICA)
    // ===============================
    public static void ejecutarSJF(){

        Random ram = new Random();

        int n = ram.nextInt(1, 25);

        int [] procesos = new int[n];  
        int [] datos = new int[n];
        int [] tiempoEspera = new int[n];
        int [] tiempoRetorno = new int[n];

        System.out.println("\n-*-*-*- SJF -*-*-*-");

        // Generar procesos
        for (int i = 0; i < n; i++) {
            procesos[i] = i + 1;
            datos[i] = ram.nextInt(1, 20);

            System.out.println("Proceso " + procesos[i] + ": " 
                              + datos[i] + " unidades de tiempo");
        }

        // Ordenar por tiempo (burbuja)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (datos[j] > datos[j + 1]) {

                    int tempProceso = procesos[j];
                    procesos[j] = procesos[j + 1];
                    procesos[j + 1] = tempProceso;

                    int tempDato = datos[j];
                    datos[j] = datos[j + 1];
                    datos[j + 1] = tempDato;
                }
            }
        }

        int tiempo = 0;
        double calcularPromedio = 0;

        System.out.println("\nProceso\tTiempo de Espera\tTiempo de Retorno");

        for(int i = 0; i < n; i++) {

            tiempoEspera[i] = tiempo;
            tiempo += datos[i];
            tiempoRetorno[i] = tiempo;

            System.out.println(procesos[i] + "\t\t" + tiempoEspera[i] + 
                                       "\t\t\t" + tiempoRetorno[i]);

            calcularPromedio += tiempoEspera[i];
        }

        System.out.println("\nTiempo de Espera Promedio: " + (calcularPromedio / n));
    }
}
