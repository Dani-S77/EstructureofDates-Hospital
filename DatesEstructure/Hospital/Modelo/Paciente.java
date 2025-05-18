package Modelo;

public class Paciente {
    private String id;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String direccion;
    private String telefono;
    private String historialMedico;
    private int turno;

    public Paciente(String id, String nombre, String apellido,int edad,String genero, String direccion, String telefono){
      this.id=id ;
      this.nombre=nombre;
      this.apellido=apellido;
      this.edad=edad;
      this.genero=genero;
      this.direccion=direccion;
      this.telefono=telefono;
      this.historialMedico="";
      this.turno=-1;
    }

    public String getId() { return id; }
    public void setId(String id){this.id=id; }

    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}

    public String getApellido(){return apellido;}
    public void  setApellido(String apellido){this.apellido=apellido;}

    public int getEdad(){return edad;}
    public void setEdad(int edad){this.edad=edad;}

    public String getGenero() {return genero;}
    public void setGenero(String genero) {this.genero = genero;}

    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getHistorialMedico() {return historialMedico;}
    public void setHistorialMedico(String nota) { this.historialMedico += nota + "\n";}

    public int getTurno(){return turno;}
    public void setTurno(int turno){this.turno=turno;}

   @Override
    public String toString(){
        return "Paciente: "+nombre+ " "+apellido+"(ID:"+id+")";
    }
}
