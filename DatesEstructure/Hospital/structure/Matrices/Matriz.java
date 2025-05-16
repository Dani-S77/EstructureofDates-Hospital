package structure.Matrices;


import javax.swing.*;

public class Matriz {

    private boolean[][] habitaciones;
    private int pisos;
    private int habitacionesPorPiso;

    public Matriz() {
        this.pisos = pisos;
        this.habitacionesPorPiso = habitacionesPorPiso;
        habitaciones = new boolean[pisos=2][habitacionesPorPiso=5]; // false = disponible
    }

    // Ocupar una habitación
    public boolean ocuparHabitacion(int piso, int numero) {
        if (esValida(piso, numero) && !habitaciones[piso][numero]) {
            habitaciones[piso][numero] = true;
            return true;
        }
        return false;
    }

    // Liberar una habitación
    public boolean liberarHabitacion(int piso, int numero) {
        if (esValida(piso, numero) && habitaciones[piso][numero]) {
            habitaciones[piso][numero] = false;
            return true;
        }
        return false;
    }

    // Verifica si una habitación está ocupada
    public boolean estaOcupada(int piso, int numero) {
        if (esValida(piso, numero)) {
            return habitaciones[piso][numero];
        }
        return false;
    }

    // Mostrar todas las habitaciones
    public void mostrarHabitaciones() {
        StringBuilder mensaje = new StringBuilder();
        for (int i = 0; i < pisos; i++) {
            mensaje.append("Piso ").append(i + 1).append(": ");
            for (int j = 0; j < habitacionesPorPiso; j++) {
                mensaje.append(habitaciones[i][j] ? "[X] " : "[ ] ");
            }
            mensaje.append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Estado de las Habitaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    // Devuelve cuántas habitaciones están disponibles
    public int contarDisponibles() {
        int disponibles = 0;
        for (int i = 0; i < pisos; i++) {
            for (int j = 0; j < habitacionesPorPiso; j++) {
                if (!habitaciones[i][j]) disponibles++;
            }
        }
        return disponibles;
    }

    // Verifica si una posición es válida
    private boolean esValida(int piso, int numero) {
        return piso >= 0 && piso < pisos && numero >= 0 && numero < habitacionesPorPiso;
    }
}
