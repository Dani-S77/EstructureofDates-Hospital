package structure.listas;

import Modelo.Paciente;

import javax.swing.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaEnlazada implements Iterable<Paciente> {
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
    }

    public int getTamano() {
        return tamano;
    }

    // Método para verificar si está vacía (ejemplo)
    public boolean estaVacia() {
        return cabeza == null;
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
                    .append(" " + p.toString()).append("\n")
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

    /*
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
            return true;
        }

        return false; // Paciente no encontrado
    }

    @Override
    public Iterator<Paciente> iterator() {
        return new ListaEnlazadaIterator();
    }

    // --- Clase interna para el Iterador ---
    private class ListaEnlazadaIterator implements Iterator<Paciente> {
        private NodoPaciente actual = cabeza; // El iterador comienza desde la cabeza de la lista

        @Override
        public boolean hasNext() { // Pregunta si hay más elementos para recorrer.
            return actual != null; // Hay un siguiente elemento si el nodo actual no es nulo
        }

        @Override
        public Paciente next() {//Devuelve el siguiente elemento de la colección.
            if (!hasNext()) {
                // Lanza esta excepción si se intenta acceder a un elemento que no existe
                throw new NoSuchElementException("No more elements in the list.");
            }
            Paciente paciente = actual.paciente; // Obtiene el paciente del nodo actual
            actual = actual.siguiente; // Avanza al siguiente nodo para la próxima llamada a next()
            return paciente;
        }

        @Override
        public void remove() { //Elimina el último elemento devuelto por next()
            throw new UnsupportedOperationException("Remove operation is not supported by this iterator.");
        }
    }
}
