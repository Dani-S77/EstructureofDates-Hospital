package structure.Arboles;
import Modelo.Paciente;

public class ArbolPacientesAVL {
    private Nodo raiz;

    public ArbolPacientesAVL() {
        this.raiz = null;
    }

    // Clase interna Nodo con el campo 'altura'
    private class Nodo {
        Paciente paciente;
        Nodo izquierdo;
        Nodo derecho;
        int altura; // Altura de este nodo en el subárbol

        public Nodo(Paciente paciente) {
            this.paciente = paciente;
            this.izquierdo = null;
            this.derecho = null;
            this.altura = 1;
        }
    }


    // Obtiene la altura de un nodo
    private int altura(Nodo N) {
        if (N == null) {
            return 0;
        }
        return N.altura;
    }

    // Obtiene el máximo de dos enteros
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Obtiene el factor de balance de un nodo (altura del subárbol izquierdo - altura del subárbol derecho)
    private int getBalance(Nodo N) {
        if (N == null) {
            return 0;
        }
        return altura(N.izquierdo) - altura(N.derecho);
    }

    // Rotación simple a la derecha
    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izquierdo;
        Nodo T2 = x.derecho;

        // Realizar rotación
        x.derecho = y;
        y.izquierdo = T2;

        // Actualizar alturas
        y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;

        return x; // Nueva raíz del subárbol rotado
    }

    // Rotación simple a la izquierda
    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecho;
        Nodo T2 = y.izquierdo;

        // Realizar rotación
        y.izquierdo = x;
        x.derecho = T2;

        // Actualizar alturas
        x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;

        return y; // Nueva raíz del subárbol rotado
    }


    public boolean estaVacia() {
        return raiz == null;
    }

    public void insertar(Paciente paciente) {
        raiz = insertarRecursivo(raiz, paciente);
    }

    private Nodo insertarRecursivo(Nodo actual, Paciente paciente) {
        // 1. Realizar la inserción
        if (actual == null) {
            return new Nodo(paciente);
        }

        int comparacion = paciente.getId().compareTo(actual.paciente.getId());

        if (comparacion < 0) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, paciente);
        } else if (comparacion > 0) {
            actual.derecho = insertarRecursivo(actual.derecho, paciente);
        } else {
            // En caso de ID duplicado simplemente se ignora la inserción
            return actual;
        }

        // 2. Actualizar la altura del nodo actual
        actual.altura = 1 + max(altura(actual.izquierdo), altura(actual.derecho));

        // 3. Revisar si está desbalanceado
        int balance = getBalance(actual);

        // 4. Si el nodo está desbalanceado, hay 4 casos:

        // Caso Izquierda-Izquierda
        if (balance > 1 && paciente.getId().compareTo(actual.izquierdo.paciente.getId()) < 0) {
            return rotarDerecha(actual);
        }

        // Caso Derecha-Derecha
        if (balance < -1 && paciente.getId().compareTo(actual.derecho.paciente.getId()) > 0) {
            return rotarIzquierda(actual);
        }

        // Caso Izquierda-Derecha
        if (balance > 1 && paciente.getId().compareTo(actual.izquierdo.paciente.getId()) > 0) {
            actual.izquierdo = rotarIzquierda(actual.izquierdo);
            return rotarDerecha(actual);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && paciente.getId().compareTo(actual.derecho.paciente.getId()) < 0) {
            actual.derecho = rotarDerecha(actual.derecho);
            return rotarIzquierda(actual);
        }

        // Si el nodo ya está balanceado, retornar el nodo actual
        return actual;
    }

    public Paciente buscar(String id) {
        return buscarRecursivo(raiz, id);
    }

    private Paciente buscarRecursivo(Nodo actual, String id) {
        if (actual == null) {
            return null; // El paciente no se encontró
        }

        int comparacion = id.compareTo(actual.paciente.getId());

        if (comparacion == 0) {
            return actual.paciente; // Se encontró el paciente
        } else if (comparacion < 0) {
            return buscarRecursivo(actual.izquierdo, id);
        } else {
            return buscarRecursivo(actual.derecho, id);
        }
    }

    // --- Métodos de Recorrido para obtener una cadena de texto (para JOptionPane) ---

    // Recorrido In-Orden que devuelve una cadena (datos ordenados por ID)
    public String obtenerInOrdenString() {
        StringBuilder sb = new StringBuilder();
        obtenerInOrdenRecursivo(raiz, sb);
        return sb.toString();
    }

    private void obtenerInOrdenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            obtenerInOrdenRecursivo(nodo.izquierdo, sb);
            sb.append(nodo.paciente.toString()).append("\n"); // Asume que Paciente.toString() es adecuado
            obtenerInOrdenRecursivo(nodo.derecho, sb);
        }
    }

    // Recorrido Pre-Orden que devuelve una cadena (Raíz -> Izquierda -> Derecha)
    public String obtenerPreOrdenString() {
        StringBuilder sb = new StringBuilder();
        obtenerPreOrdenRecursivo(raiz, sb);
        return sb.toString();
    }

    private void obtenerPreOrdenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.paciente.toString()).append("\n");
            obtenerPreOrdenRecursivo(nodo.izquierdo, sb);
            obtenerPreOrdenRecursivo(nodo.derecho, sb);
        }
    }

    // Recorrido Post-Orden que devuelve una cadena (Izquierda -> Derecha -> Raíz)
    public String obtenerPostOrdenString() {
        StringBuilder sb = new StringBuilder();
        obtenerPostOrdenRecursivo(raiz, sb);
        return sb.toString();
    }

    private void obtenerPostOrdenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            obtenerPostOrdenRecursivo(nodo.izquierdo, sb);
            obtenerPostOrdenRecursivo(nodo.derecho, sb);
            sb.append(nodo.paciente.toString()).append("\n");
        }
    }
}



