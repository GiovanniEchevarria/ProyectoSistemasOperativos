package ProcesosO;
import java.util.*;

//PCB (Bloque de Control de Proceso)
class ProcesosP {
    int id;
    int tiempoRestante;
    int estado; // 1=Listo 2=Bloqueado 3=Ejecutando 4=Terminado
    int prioridad;
    int intentos = 0;
    boolean entro = false;

    ProcesosP(int id, int tiempo, int estado, int prioridad) {
        this.id = id;
        this.tiempoRestante = tiempo;
        this.estado = estado;
        this.prioridad = prioridad;
    }
}

public class PrioridadesGio {

    static Random r = new Random();
    static int cambios = 0;
    static int quantum = r.nextInt(4) + 2;

    public static void inicio(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Apropiativo");
        System.out.println("2. No Apropiativo");
        int tipo = sc.nextInt();

        boolean apropiativo = (tipo == 1);

        int tiempoSim = r.nextInt(16) + 20;
        int n = r.nextInt(10) + 1;

        ArrayList<ProcesosP> lista = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            lista.add(new ProcesosP(
                    i,
                    r.nextInt(8) + 3,
                    r.nextInt(2) + 1,
                    r.nextInt(5) + 1));
        }

        System.out.println("\nTiempo simulación: " + tiempoSim);
        System.out.println("Quantum: " + quantum);

        mostrarPCB(lista);

        simular(lista, tiempoSim, apropiativo);

        reporte(lista);
    }

    //SIMULACION

    static void simular(ArrayList<ProcesosP> lista, int tiempoMax, boolean apropiativo) {

        for (int t = 0; t < tiempoMax; t++) {

            ProcesosP actual = mayorPrioridad(lista);
            if (actual == null) break;

            ejecutar(actual, apropiativo);
        }
    }

    //EJECUCION

    static void ejecutar(ProcesosP p, boolean apropiativo) {

        if (p.estado == 2) {
            desbloquear(p);
            return;
        }

        p.estado = 3;
        p.entro = true;
        cambios++;

        System.out.println("\nProceso " + p.id + " entra a ejecución");

        int bloqueo = r.nextInt(2);

        if (bloqueo == 1) {

            System.out.println("Proceso BLOQUEADO");

            int interrupcion = apropiativo ?
                    r.nextInt(quantum) + 1 :
                    r.nextInt(p.tiempoRestante) + 1;

            interrupcion = Math.min(interrupcion, p.tiempoRestante);

            p.tiempoRestante -= interrupcion;

            System.out.println("Interrupción en: " + interrupcion);
            System.out.println("Tiempo restante: " + p.tiempoRestante);

            p.estado = (p.tiempoRestante <= 0) ? 4 : 2;

        } else {

            int ejecutar = apropiativo ?
                    Math.min(quantum, p.tiempoRestante) :
                    p.tiempoRestante;

            p.tiempoRestante -= ejecutar;

            System.out.println("Ejecuta " + ejecutar + " unidades");

            if (p.tiempoRestante <= 0) {
                p.estado = 4;
                System.out.println("Proceso TERMINADO");
            } else {
                p.estado = 1;
            }
        }
    }

    // DESBLOQUEO

    static void desbloquear(ProcesosP p) {

        int v = r.nextInt(2);

        if (v == 1) {
            p.estado = 1;
            p.intentos = 0;
            System.out.println("Proceso " + p.id + " desbloqueado");
        } else {
            p.intentos++;
            System.out.println("Intento desbloqueo fallido");

            if (p.intentos >= 3) {
                p.estado = 4;
                System.out.println("MUERTE POR INANICIÓN proceso " + p.id);
            }
        }
    }

    //  AUX

    static ProcesosP mayorPrioridad(ArrayList<ProcesosP> lista) {

        ProcesosP mejor = null;

        for (ProcesosP p : lista)
            if (p.estado == 1 && p.tiempoRestante > 0)
                if (mejor == null || p.prioridad > mejor.prioridad)
                    mejor = p;

        return mejor;
    }

    static void mostrarPCB(ArrayList<ProcesosP> lista) {
        System.out.println("\nID | Tiempo | Estado | Prioridad");
        for (ProcesosP p : lista)
            System.out.println(p.id + " | " + p.tiempoRestante +
                    " | " + p.estado +
                    " | " + p.prioridad);
    }

    static void reporte(ArrayList<ProcesosP> lista) {

        System.out.println("\nREPORTE FINAL");

        System.out.print("Terminados: ");
        for (ProcesosP p : lista)
            if (p.estado == 4) System.out.print(p.id + " ");

        System.out.print("\nNunca ejecutados: ");
        for (ProcesosP p : lista)
            if (!p.entro) System.out.print(p.id + " ");

        System.out.println("\nCambios de proceso: " + cambios);
    }
}