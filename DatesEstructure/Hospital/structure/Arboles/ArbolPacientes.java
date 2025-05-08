package structure.Arboles;

import Modelo.Paciente;

public class ArbolPacientes {
    private Nodo raiz;

    public ArbolPacientes() {
        this.raiz = null;
    }

    public void insertar(Paciente paciente) {
        raiz = insertarRecursivo(raiz, paciente);
    }

    private Nodo insertarRecursivo(Nodo actual, Paciente paciente) {
        if (actual == null) {
            return new Nodo(paciente);
        }

        int comparacion = paciente.getId().compareTo(actual.paciente.getId());

        if (comparacion < 0) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, paciente);
        } else if (comparacion > 0) {
            actual.derecho = insertarRecursivo(actual.derecho, paciente);
        }

        return actual;
    }

    public Paciente buscar(String id) {
        return buscarRecursivo(raiz, id);
    }

    private Paciente buscarRecursivo(Nodo actual, String id) {
        if (actual == null) {
            return null;
        }

        int comparacion = id.compareTo(actual.paciente.getId());

        if (comparacion == 0) {
            return actual.paciente;
        } else if (comparacion < 0) {
            return buscarRecursivo(actual.izquierdo, id);
        } else {
            return buscarRecursivo(actual.derecho, id);
        }
    }

    public void inOrden() {
        inOrdenRecursivo(raiz);
    }

    private void inOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.izquierdo);
            System.out.println(nodo.paciente);
            inOrdenRecursivo(nodo.derecho);
        }
    }

    private class Nodo {
        Paciente paciente;
        Nodo izquierdo;
        Nodo derecho;

        public Nodo(Paciente paciente) {
            this.paciente = paciente;
            this.izquierdo = null;
            this.derecho = null;
        }
    }
}
