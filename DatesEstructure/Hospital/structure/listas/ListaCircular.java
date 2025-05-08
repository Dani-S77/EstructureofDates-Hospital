package structure.listas;

public class ListaCircular<T> {
    private Nodo<T> cabeza;
    private Nodo<T> ultimo;
    private int size;
    
    public ListaCircular() {
        this.cabeza = null;
        this.ultimo = null;
        this.size = 0;
    }
    
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo;
            ultimo = nuevoNodo;
            nuevoNodo.setSiguiente(nuevoNodo); // Apunta a sí mismo para formar el círculo
        } else {
            nuevoNodo.setSiguiente(cabeza);
            ultimo.setSiguiente(nuevoNodo);
            ultimo = nuevoNodo;
        }
        size++;
    }
    
    public T obtener(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }
    
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }
        
        if (size == 1 && cabeza.getDato().equals(dato)) {
            cabeza = null;
            ultimo = null;
            size--;
            return true;
        }
        
        Nodo<T> actual = cabeza;
        Nodo<T> anterior = ultimo;
        
        do {
            if (actual.getDato().equals(dato)) {
                if (actual == cabeza) {
                    cabeza = cabeza.getSiguiente();
                }
                if (actual == ultimo) {
                    ultimo = anterior;
                }
                anterior.setSiguiente(actual.getSiguiente());
                size--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        } while (actual != cabeza);
        
        return false;
    }
    
    public int getSize() {
        return size;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    public void recorrer() {
        if (estaVacia()) {
            System.out.println("Lista vacía");
            return;
        }
        
        Nodo<T> actual = cabeza;
        do {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        } while (actual != cabeza);
    }
    
    private static class Nodo<T> {
        private T dato;
        private Nodo<T> siguiente;
        
        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
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
    }
}
