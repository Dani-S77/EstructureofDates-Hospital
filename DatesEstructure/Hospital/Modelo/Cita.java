package Modelo;

import java.time.LocalDateTime;

public class Cita {
    private String id;
    private Paciente paciente;
    private Doctor doctor;
    private LocalDateTime fecha;
    private String motivo;

    public Cita(String id, Paciente paciente, Doctor doctor, LocalDateTime fecha, String motivo) {
        this.id = id;
        this.paciente = paciente;
        this.doctor = doctor;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getMotivo() {
        return motivo;
    }


    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Cita ID: " + id + "\n" +
                "  Paciente: " + (paciente != null ? paciente.getNombre() + " " + paciente.getApellido() : "N/A") + "\n" +
                "  Doctor: " + (doctor != null ? doctor.getNombre() : "N/A") + "\n" +
                "  Fecha: " + fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + "\n" +
                "  Motivo: " + motivo;
    }
}

