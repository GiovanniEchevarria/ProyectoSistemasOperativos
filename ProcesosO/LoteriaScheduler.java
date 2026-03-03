package ProcesosO;
import java.util.*;

class Proceso {
    int id;
    int restante;
    int estado; // 1 listo 2 bloqueado
    int prioridad;
    int boletos;
    String usuario;

    boolean iniciado = false;
    boolean finalizado = false;
    int cpuUsado = 0;
    int cambios = 0;

    public Proceso(int id, int restante, int estado, int prioridad, int boletos, String usuario) {
        this.id = id;
        this.restante = restante;
        this.estado = estado;
        this.prioridad = prioridad;
        this.boletos = boletos;
        this.usuario = usuario;
    }

    public void imprimir() {
        String est = (estado == 1) ? "Listo" : "Bloqueado";
        System.out.println("P" + id + " | restante=" + restante + " | " + est +
                " | prioridad=" + prioridad + " | boleto=" + boletos + " | usuario=" + usuario);
    }
}

public class LoteriaScheduler {

    static Random rand = new Random();

    static boolean intentarDesbloquear(Proceso p) {
        for (int i = 0; i < 3; i++) {
            if (rand.nextInt(2) == 1) {
                p.estado = 1;
                return true;
            }
        }
        return false;
    }

    static void imprimirPCB(List<Proceso> procesos, String titulo) {
        System.out.println("\n=== " + titulo + " ===");
        for (Proceso p : procesos) {
            p.imprimir();
        }
        System.out.println("-----------------------");
    }

    static void ejecutarLoteria(List<Proceso> procesosOriginal, int tiempoMonitor, boolean apropiativo) {

        List<Proceso> procesos = new ArrayList<>();
        for (Proceso p : procesosOriginal) {
            procesos.add(new Proceso(p.id, p.restante, p.estado, p.prioridad, p.boletos, p.usuario));
        }

        int tiempo = 0;

        imprimirPCB(procesos, "Inicio Lotería " + (apropiativo ? "Apropiativo" : "No Apropiativo"));

        while (tiempo < tiempoMonitor) {

            List<Proceso> listos = new ArrayList<>();
            for (Proceso p : procesos) {
                if (!p.finalizado && p.estado == 1) {
                    listos.add(p);
                }
            }

            if (listos.isEmpty()) {
                System.out.println("No hay procesos listos.");
                break;
            }

            List<Proceso> pool = new ArrayList<>();
            for (Proceso p : listos) {
                for (int i = 0; i < p.boletos; i++) {
                    pool.add(p);
                }
            }

            Proceso seleccionado = pool.get(rand.nextInt(pool.size()));

            int ejecutar = apropiativo ? 1 : seleccionado.restante;
            ejecutar = Math.min(ejecutar, seleccionado.restante);

            seleccionado.iniciado = true;
            seleccionado.restante -= ejecutar;
            seleccionado.cpuUsado += ejecutar;
            seleccionado.cambios++;
            tiempo += ejecutar;

            System.out.println("t=" + tiempo + " -> Ejecutó P" + seleccionado.id +
                    " por " + ejecutar + " (resta " + seleccionado.restante + ")");

            if (seleccionado.restante == 0) {
                seleccionado.finalizado = true;
                System.out.println("P" + seleccionado.id + " finalizó.");
            }

            imprimirPCB(procesos, "Estado en t=" + tiempo);
        }

        System.out.println("\n===== REPORTE FINAL =====");
        for (Proceso p : procesos) {
            System.out.println("P" + p.id +
                    " | Finalizado: " + p.finalizado +
                    " | Iniciado: " + p.iniciado +
                    " | Cambios: " + p.cambios);
        }
    }

    public static void main(String[] args) {

        int numProcesos = 5;
        int tiempoMonitor = rand.nextInt(16) + 20;

        String[] usuarios = {"u1", "u2", "u3"};
        List<Proceso> procesos = new ArrayList<>();

        for (int i = 1; i <= numProcesos; i++) {
            procesos.add(new Proceso(
                    i,
                    rand.nextInt(8) + 3,
                    rand.nextInt(2) + 1,
                    rand.nextInt(5) + 1,
                    rand.nextInt(10) + 1,
                    usuarios[rand.nextInt(3)]
            ));
        }

        imprimirPCB(procesos, "PCB Inicial");

        ejecutarLoteria(procesos, tiempoMonitor, true);   // Apropiativo
        ejecutarLoteria(procesos, tiempoMonitor, false);  // No Apropiativo
    }
}