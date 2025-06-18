package structure.Colas;

import Modelo.Paciente;

import java.util.Iterator;

public class Cola {
    private Nodo frente;
    private Nodo ultimo;
    private int tamaño;

    private static class Nodo {
        Paciente paciente;
        Nodo siguiente;

        Nodo( Paciente paciente) {
            this.paciente = paciente;
            this.siguiente = null;
        }
    }

    public Cola() {
        frente = null;
        ultimo = null;
        tamaño = 0;
    }

     // Encola un ID al final de la cola.

    public void encolar(Paciente paciente) {
        Nodo nuevoNodo = new Nodo(paciente);
        if (estaVacia()) {
            frente = nuevoNodo;
        } else {
            ultimo.siguiente = nuevoNodo;
        }
        ultimo = nuevoNodo;
        tamaño++;
    }

    /*
     * Desencola el ID del frente de la cola.
     * null si la cola está vacía.
     */
    public Paciente desencolar() {
        if (estaVacia()) {
            return null;
        }
        Paciente pacienteDesencolado = frente.paciente;
        frente = frente.siguiente;
        tamaño--;
        if (estaVacia()) {
            ultimo = null;
        }
        return pacienteDesencolado;
    }

    /*
     * Obtiene el ID del frente de la cola sin desencolarlo.
     * retorna El ID del frente, o null si la cola está vacía.
     */

    public Paciente obtenerFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.paciente;
    }

    /*
     * Verifica si la cola está vacía.
     *
     * retorna true si la cola está vacía, false de lo contrario.
     */
    public boolean estaVacia() {
        return frente == null;
    }

    /*
     * Obtiene el número de elementos en la cola.
     *
     * retorna El tamaño de la cola.
     */
    public int obtenerTamaño() {
        return tamaño;
    }

    /*
     * Verifica si la cola contiene el ID especificado.
     * retorna true si el ID está presente en la cola, false de lo contrario.
     */
    public boolean contiene(String idABuscar) {
        // Si la cola está vacía, no puede contener el ID.
        if (estaVacia()) {
            return false;
        }
        // Recorremos la cola desde el frente hasta el final.
        Nodo actual = frente;
        while (actual != null) {
            if (actual.paciente != null && actual.paciente.getId() != null &&
                    actual.paciente.getId().equalsIgnoreCase(idABuscar)) {
                return true; // Si encontramos el ID, devolvemos true.
            }
            actual = actual.siguiente; // Avanzamos al siguiente nodo.
        }
        // Si terminamos de recorrer la cola y no encontramos el ID, devolvemos false.
        return false;
    }
}