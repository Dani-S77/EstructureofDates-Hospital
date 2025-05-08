package structure.Pilas;



public class PilaUnidimensional<T> {
    private Object[] datos;
    private int top;
    private int capacidad;

    public PilaUnidimensional(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new Object[capacidad];
        this.top = -1;
    }

    public void push(T elemento) {
        if (top >= capacidad - 1) {
            throw new RuntimeException("Pila llena");
        }
        datos[++top] = elemento;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (estaVacia()) {
            throw new RuntimeException("Pila vacía");
        }
        return (T) datos[top--];
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (estaVacia()) {
            throw new RuntimeException("Pila vacía");
        }
        return (T) datos[top];
    }

    public boolean estaVacia() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public void vaciar() {
        top = -1;
    }
}
