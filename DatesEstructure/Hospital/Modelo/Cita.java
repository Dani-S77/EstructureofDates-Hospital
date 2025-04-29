package Modelo;

import java.time.LocalDateTime;

public class Cita {
    private String id;
    private Paciente paciente;
    private Doctor doctor;
    private LocalDateTime fecha;
    private String motivo;
    private boolean atendida;

    public Cita(String id, Paciente paciente, Doctor doctor, LocalDateTime fecha,String motivo,boolean atendida) {
        this.id = id;
        this.paciente = paciente;
        this.doctor = doctor;
        this.fecha=fecha;
        this.motivo=motivo;
        this.atendida=atendida;
    }

    public String getId() {return id;}

}

