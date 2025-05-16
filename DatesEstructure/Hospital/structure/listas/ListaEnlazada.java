package structure.listas;

import Modelo.Paciente;

import javax.swing.*;

public class ListaEnlazada {
    private NodoPaciente cabeza;
    private int tamaño = 0;

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
        tamaño++;
    }

    // Retorna la cantidad de pacientes registrados
    public int getTamaño() {
        return tamaño;
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
                    .append(", Nombre: ").append(p.getNombre()).append(" ").append(p.getApellido())
                    .append(", Edad: ").append(p.getEdad())
                    .append(", Teléfono: ").append(p.getTelefono())
                    .append("\n");
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
}
