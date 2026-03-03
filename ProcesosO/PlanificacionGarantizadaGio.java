package ProcesosO;
import java.util.*;

class ProcesoG {

    int id;
    int tiempoRestante;
    int estado; //1 listo,2 bloqueado,4 terminado
    int cpuUsado = 0;
    int intentos = 0;
    boolean entro = false;

    ProcesoG(int id, int tiempo, int estado) {
        this.id = id;
        this.tiempoRestante = tiempo;
        this.estado = estado;
    }
}

public class PlanificacionGarantizadaGio {

    static Random r = new Random();
    static int cambios = 0;

    public static void inicio(String[] args) {

        int tiempoSim = r.nextInt(16) + 20;
        int n = r.nextInt(10) + 1;

        ArrayList<ProcesoG> lista = new ArrayList<>();

        for (int i = 1; i <= n; i++)
            lista.add(new ProcesoG(
                    i,
                    r.nextInt(8) + 3,
                    r.nextInt(2) + 1));

        System.out.println("Tiempo simulación: " + tiempoSim);

        mostrarPCB(lista);

        simular(lista, tiempoSim);

        reporte(lista);
    }

    //SIMULACION

    static void simular(ArrayList<ProcesoG> lista, int tiempoMax) {

        for (int t = 0; t < tiempoMax; t++) {

            ProcesoG p = menorUsoCPU(lista);
            if (p == null) break;

            ejecutar(p);
        }
    }

    static void ejecutar(ProcesoG p) {

        if (p.estado == 2) {
            desbloquear(p);
            return;
        }

        cambios++;
        p.entro = true;

        System.out.println("\nEjecutando proceso " + p.id);

        int bloqueo = r.nextInt(2);

        if (bloqueo == 1) {

            int interrupcion = r.nextInt(p.tiempoRestante) + 1;

            p.tiempoRestante -= interrupcion;
            p.cpuUsado += interrupcion;

            System.out.println("Se bloqueó en " + interrupcion);

            p.estado = (p.tiempoRestante <= 0) ? 4 : 2;

        } else {

            p.tiempoRestante--;
            p.cpuUsado++;

            if (p.tiempoRestante <= 0) {
                p.estado = 4;
                System.out.println("Proceso terminado");
            }
        }
    }

    static void desbloquear(ProcesoG p) {

        if (r.nextInt(2) == 1) {
            p.estado = 1;
            p.intentos = 0;
            System.out.println("Proceso desbloqueado");
        } else {
            p.intentos++;
            if (p.intentos >= 3) {
                p.estado = 4;
                System.out.println("MUERTE POR INANICIÓN " + p.id);
            }
        }
    }

    static ProcesoG menorUsoCPU(ArrayList<ProcesoG> lista) {

        ProcesoG menor = null;

        for (ProcesoG p : lista)
            if (p.estado == 1 && p.tiempoRestante > 0)
                if (menor == null || p.cpuUsado < menor.cpuUsado)
                    menor = p;

        return menor;
    }

    static void mostrarPCB(ArrayList<ProcesoG> lista) {
        System.out.println("\nID | Tiempo | Estado");
        for (ProcesoG p : lista)
            System.out.println(p.id + " | " + p.tiempoRestante + " | " + p.estado);
    }

    static void reporte(ArrayList<ProcesoG> lista) {

        System.out.println("\n--- REPORTE FINAL ---");

        System.out.print("Terminados: ");
        for (ProcesoG p : lista)
            if (p.estado == 4) System.out.print(p.id + " ");

        System.out.print("\nNunca ejecutados: ");
        for (ProcesoG p : lista)
            if (!p.entro) System.out.print(p.id + " ");

        System.out.println("\nCambios de proceso: " + cambios);
    }
}