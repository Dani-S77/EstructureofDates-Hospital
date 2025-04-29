package Modelo;

public class Doctor {
    private String id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;

    public Doctor (String id,String nombre,String apellido,String especialidad, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

        public String getId() {return id;}
        public void setId(String id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getEspecialidad() {return especialidad;}
    public void setEspecialidad(String especialidad) {this.especialidad = especialidad;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    @Override
    public String toString(){
        return "Dr."+nombre+" "+apellido+"-"+especialidad+"(ID:"+id+")";
    }
}

