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

    // Método para verificar si está vacía (ejemplo)
    public boolean estaVacia() {
        return raiz == null;
    }

// --- Métodos de Recorrido para imprimir en consola (como los tenías) ---

    // Recorrido In-Orden: Izquierda -> Raíz -> Derecha (muestra los datos ordenados por ID)
    public void inOrden() {
        inOrdenRecursivo(raiz);
    }

    private void inOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.izquierdo);
            System.out.println(nodo.paciente); // Imprime el paciente
            inOrdenRecursivo(nodo.derecho);
        }
    }

    // Recorrido Pre-Orden: Raíz -> Izquierda -> Derecha (útil para copiar el árbol)
    public void preOrden() {
        preOrdenRecursivo(raiz);
    }

    private void preOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.println(nodo.paciente); // Imprime el paciente
            preOrdenRecursivo(nodo.izquierdo);
            preOrdenRecursivo(nodo.derecho);
        }
    }

    // Recorrido Post-Orden: Izquierda -> Derecha -> Raíz (útil para eliminar el árbol)
    public void postOrden() {
        postOrdenRecursivo(raiz);
    }

    private void postOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            postOrdenRecursivo(nodo.izquierdo);
            postOrdenRecursivo(nodo.derecho);
            System.out.println(nodo.paciente); // Imprime el paciente
        }
    }

    // --- Métodos de Recorrido para obtener una cadena de texto (para JOptionPane) ---

    // Recorrido In-Orden que devuelve una cadena
    public String obtenerInOrdenString() {
        StringBuilder sb = new StringBuilder();
        obtenerInOrdenRecursivo(raiz, sb);
        return sb.toString();
    }

    private void obtenerInOrdenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            obtenerInOrdenRecursivo(nodo.izquierdo, sb);
            sb.append(nodo.paciente.toString()).append("\n"); // Añade el paciente y un salto de línea
            obtenerInOrdenRecursivo(nodo.derecho, sb);
        }
    }

    // Recorrido Pre-Orden que devuelve una cadena
    public String obtenerPreOrdenString() {
        StringBuilder sb = new StringBuilder();
        obtenerPreOrdenRecursivo(raiz, sb);
        return sb.toString();
    }

    private void obtenerPreOrdenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.paciente.toString()).append("\n"); // Añade el paciente y un salto de línea
            obtenerPreOrdenRecursivo(nodo.izquierdo, sb);
            obtenerPreOrdenRecursivo(nodo.derecho, sb);
        }
    }

    // Recorrido Post-Orden que devuelve una cadena
    public String obtenerPostOrdenString() {
        StringBuilder sb = new StringBuilder();
        obtenerPostOrdenRecursivo(raiz, sb);
        return sb.toString();
    }

    private void obtenerPostOrdenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            obtenerPostOrdenRecursivo(nodo.izquierdo, sb);
            obtenerPostOrdenRecursivo(nodo.derecho, sb);
            sb.append(nodo.paciente.toString()).append("\n"); // Añade el paciente y un salto de línea
        }
    }
}
