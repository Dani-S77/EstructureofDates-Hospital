package Modelo;


import java.time.LocalDate;

public class Tratamiento {
    private String id;
    private Paciente paciente;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String medicamentos;
    private String indicaciones;

    public Tratamiento(String id, Paciente paciente,String descripcion, LocalDate fechaInicio,LocalDate fechaFin,
                       String medicamentos,String indicaciones) {
        this.id=id;
        this.paciente=paciente;
        this.descripcion=descripcion;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.medicamentos=medicamentos;
        this.indicaciones=indicaciones;
    }

    public String getId(){return id;}
    public void setId(String id) {this.id = id;}

    public Paciente getPaciente() {return paciente;}
    public void setPaciente(Paciente paciente) {this.paciente = paciente;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public LocalDate getFechaInicio(){return  fechaInicio;}
    public void setFechaInicio(LocalDate fechaInicio) {this.fechaInicio = fechaInicio;}

    public LocalDate getFechaFin() {return fechaFin;}
    public void setFechaFin(LocalDate fechaFin) {this.fechaFin = fechaFin;}

    public String getIndicaciones(){return indicaciones;}
    public void setIndicaciones(String indicaciones){this.indicaciones=indicaciones;}

    public String getMedicamentos() {return medicamentos;}
    public void setMedicamentos(String medicamentos) {this.medicamentos = medicamentos;}

    @Override
    public String toString(){
        return "Tratamiento: "+id+ "-Paciente:"+paciente.getNombre()+"Duracion:"+fechaInicio+"a"+fechaFin;
    }

}

