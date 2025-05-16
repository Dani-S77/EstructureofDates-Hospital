package structure.Pilas;
import Modelo.Cita;

import javax.swing.*;

public class PilaUnidimensional {
    private static final int MAX = 100; // Tamaño máximo de la pila
    private Cita[] pila;
    private int top;

    public PilaUnidimensional() {
        this.pila = new Cita[MAX];
        this.top = -1;
    }


    // Verifica si la pila está llena
    public boolean estaLlena() {
        return top == MAX - 1;
    }

    public int espacioDisponible() {
        return MAX - (top + 1);
    }

    // Verifica si la pila está vacía
    public boolean estaVacia() {
        return top == -1;
    }

    // Agrega una cita a la pila
    public boolean apilar(Cita cita) {
        if (estaLlena()) {
            JOptionPane.showMessageDialog(null, "La pila está llena. No se puede agregar la cita.", "Pila Llena", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        pila[++top] = cita;
        return true;
    }

    // Elimina y retorna la cita en la cima de la pila
    public Cita desapilar() {
        if (estaVacia()) {
            JOptionPane.showMessageDialog(null, "La pila está vacía. No se puede desapilar.", "Pila Vacía", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return pila[top--];
    }

    // Retorna la cita en la cima sin eliminarla
    public Cita cima() {
        if (estaVacia()) {
            JOptionPane.showMessageDialog(null, "La pila está vacía.", "Pila Vacía", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return pila[top];
    }

    // Muestra todas las citas en la pila
    public void mostrarHistorial() {
        if (estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay citas en el historial.", "Historial de Citas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder historial = new StringBuilder("Historial de Citas:\n");
        for (int i = top; i >= 0; i--) {
            historial.append(pila[i]).append("\n");
        }
        JOptionPane.showMessageDialog(null, historial.toString(), "Historial de Citas", JOptionPane.INFORMATION_MESSAGE);
    }

    public Cita obtenerPorIndice(int index) {
        if (index < 0 || index > top) return null;
        return pila[index];
    }

    public int cantidad() {
        return top + 1;
    }


}
