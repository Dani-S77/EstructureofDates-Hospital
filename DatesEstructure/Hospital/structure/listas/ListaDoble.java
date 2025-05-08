package structure.listas;

public class ListaDoble<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int size;
    
    public ListaDoble() {
        this.cabeza = null;
        this.cola = null;
        this.size = 0;
    }
    
    public void agregarAlFinal(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.setAnterior(cola);
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }
        size++;
    }
    
    public void agregarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
            cabeza = nuevoNodo;
        }
        size++;
    }
    
    public T obtener(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        
        Nodo<T> actual;
        if (indice < size / 2) {
            // Empezar desde la cabeza
            actual = cabeza;
            for (int i = 0; i < indice; i++) {
                actual = actual.getSiguiente();
            }
        } else {
            // Empezar desde la cola
            actual = cola;
            for (int i = size - 1; i > indice; i--) {
                actual = actual.getAnterior();
            }
        }
        return actual.getDato();
    }
    
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }
        
        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            if (cabeza != null) {
                cabeza.setAnterior(null);
            } else {
                cola = null;
            }
            size--;
            return true;
        }
        
        if (cola.getDato().equals(dato)) {
            cola = cola.getAnterior();
            cola.setSiguiente(null);
            size--;
            return true;
        }
        
        Nodo<T> actual = cabeza;
        while (actual != null && !actual.getDato().equals(dato)) {
            actual = actual.getSiguiente();
        }
        
        if (actual != null) {
            actual.getAnterior().setSiguiente(actual.getSiguiente());
            actual.getSiguiente().setAnterior(actual.getAnterior());
            size--;
            return true;
        }
        return false;
    }
    
    public int getSize() {
        return size;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    private static class Nodo<T> {
        private T dato;
        private Nodo<T> siguiente;
        private Nodo<T> anterior;
        
        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
            this.anterior = null;
        }
        
        public T getDato() {
            return dato;
        }
        
        public Nodo<T> getSiguiente() {
            return siguiente;
        }
        
        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }
        
        public Nodo<T> getAnterior() {
            return anterior;
        }
        
        public void setAnterior(Nodo<T> anterior) {
            this.anterior = anterior;
        }
    }
}
