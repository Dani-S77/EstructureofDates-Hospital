package Modelo;

public class Doctor {
    private String id;
    private String nombre;
    private String apellido;
    private String especialidad;

    public Doctor(String id, String nombre, String apellido, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String toString() {
        return "Dr." + nombre + " " + apellido + "-" + especialidad + "(ID:" + id + ")";
    }
}

