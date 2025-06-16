package Modelo;

public class Paciente {
    private String id;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String direccion;
    private String telefono;
    private String historialMedico; // Se inicializa vacío y se añade con setHistorialMedico
    private int turno; // -1 significa sin turno asignado

    public Paciente(String id, String nombre, String apellido, int edad, String genero, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.direccion = direccion;
        this.telefono = telefono;
        this.historialMedico = ""; // Inicializa el historial médico
        this.turno = -1; // Por defecto, sin turno
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getHistorialMedico() {
        return historialMedico;
    }

    public int getTurno() {
        return turno;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // El metodo añade notas
    public void setHistorialMedico(String nota) {
        this.historialMedico += nota + "\n";
    }
    public void setTurno(int turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Paciente: " + nombre + " " + apellido + " (ID:" + id + ")";
    }
}