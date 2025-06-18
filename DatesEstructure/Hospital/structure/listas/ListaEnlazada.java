package structure.listas;

import Modelo.Paciente;

import javax.swing.*;

public class ListaEnlazada {
    private NodoPaciente cabeza;
    private int tamano = 0;

    // Clase interna Nodo
    private static class NodoPaciente {
        Paciente paciente;
        NodoPaciente siguiente;

        NodoPaciente(Paciente paciente) {
            this.paciente = paciente;
            this.siguiente = null;
        }
    }

    // Agrega un paciente al final de la lista
    public void agregar(Paciente paciente) {
        NodoPaciente nuevo = new NodoPaciente(paciente);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoPaciente actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamano++;
    }

    // Retorna la cantidad de pacientes registrados
    public int getTamano() {
        return tamano;
    }

    // Muestra los pacientes registrados (ID, nombre, edad, teléfono)
    public void mostrarPacientes() {
        if (cabeza == null) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.", "Pacientes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        NodoPaciente actual = cabeza;

        StringBuilder listaPacientes = new StringBuilder("Pacientes registrados:\n");
        while (actual != null) {
            Paciente p = actual.paciente;
            listaPacientes.append("ID: ").append(p.getId())
            .append(p.toString()).append("\n")
            .append("  Edad: ").append(p.getEdad()).append(", Género: ").append(p.getGenero()).append("\n")
            .append("  Teléfono: ").append(p.getTelefono()).append(", Dirección: ").append(p.getDireccion()).append("\n")
            .append("------------------------------------------\n");
            actual = actual.siguiente;
        }
        JOptionPane.showMessageDialog(null, listaPacientes.toString(), "Pacientes", JOptionPane.INFORMATION_MESSAGE);
    }

    // Buscar paciente por ID
    public Paciente buscarPorId(String id) {
        NodoPaciente actual = cabeza;
        while (actual != null) {
            if (actual.paciente.getId().equals(id)) {
                return actual.paciente;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Elimina un paciente por su ID de la lista enlazada
     * retorna true si el paciente fue eliminado, false si no se encontró.
     */
    public boolean eliminarPorId(String id) {
        if (cabeza == null) {
            return false; // Lista vacía
        }

        // Si el paciente a eliminar es la cabeza
        if (cabeza.paciente.getId().equals(id)) {
            cabeza = cabeza.siguiente; // La nueva cabeza es el siguiente nodo
            tamano--;
            return true;
        }

        // Buscar el nodo anterior al que queremos eliminar
        NodoPaciente actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.paciente.getId().equals(id)) {
            actual = actual.siguiente;
        }

        // Si encontramos el nodo anterior al que queremos eliminar
        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente; // Saltamos el nodo a eliminar
            tamano--;
            return true;
        }

        return false; // Paciente no encontrado
    }
}
