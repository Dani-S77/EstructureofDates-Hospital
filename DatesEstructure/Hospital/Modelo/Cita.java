package Modelo;

import java.time.LocalDateTime;

public class Cita {
    private String id;
    private Paciente paciente;
    private Doctor doctor;
    private LocalDateTime fecha;
    private String motivo;
    private boolean atendida;

    public Cita(String id, Paciente paciente, Doctor doctor, LocalDateTime fecha, String motivo, boolean atendida) {
        this.id = id;
        this.paciente = paciente;
        this.doctor = doctor;
        this.fecha = fecha;
        this.motivo = motivo;
        this.atendida = atendida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente() {
        this.paciente = paciente;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isAtendida() {return atendida;}
    public void setAtendida(boolean atendida) {this.atendida = atendida;}

    @Override
    public String toString(){
        return "Cita: "+id+"-Paciente: "+paciente.getNombre()+
                "-Doctor: "+doctor.getNombre()+ "-Fecha: "+fecha;
        //prueba
    }
}





