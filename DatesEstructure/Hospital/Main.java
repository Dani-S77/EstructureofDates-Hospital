import structure.Arboles.ArbolPacientes;
import structure.Matrices.Matriz;
import structure.Pilas.PilaUnidimensional;
import structure.listas.ListaEnlazada;
import structure.listas.ListaSimple;
import Modelo.Cita;
import Modelo.Doctor;
import Modelo.Paciente;
import Modelo.Tratamiento;
import Util.Validaciones;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // Estructuras de datos para almacenar la información
    private static ArbolPacientes pacientes = new ArbolPacientes();
    private static ListaSimple<Doctor> doctores = new ListaSimple<>();
    private static ListaSimple<Cita> citas = new ListaSimple<>();
    private static ListaSimple<Tratamiento> tratamientos = new ListaSimple<>();
    private static ListaEnlazada mapaPacientes = new ListaEnlazada();
    private static Map<String, Doctor> mapaDoctores = new HashMap<>();
    private static PilaUnidimensional pilaCitas = new PilaUnidimensional();
    private static Matriz habitaciones = new Matriz();

    public static void main(String[] args) {
        // Agregar algunos datos de ejemplo
        inicializarDatos();

        // Mostrar el menú principal
        mostrarMenuPrincipal();
    }

    private static void inicializarDatos() {
        // Doctores de ejemplo
        Doctor doc1 = new Doctor("D001", "Juan", "Pérez", "Cardiología", "5555551234");
        Doctor doc2 = new Doctor("D002", "María", "Gómez", "Pediatría", "5555552345");
        Doctor doc3 = new Doctor("D003", "Carlos", "Rodríguez", "Traumatología", "5555553456");

        doctores.agregar(doc1);
        doctores.agregar(doc2);
        doctores.agregar(doc3);

        mapaDoctores.put(doc1.getId(), doc1);
        mapaDoctores.put(doc2.getId(), doc2);
        mapaDoctores.put(doc3.getId(), doc3);
    }

    private static void mostrarMenuPrincipal() {
        int seleccion;
            String[] opciones = {
                    "1. Registrar paciente",
                    "2. Agendar cita",
                    "3. Registrar tratamiento",
                    "4. Buscar paciente",
                    "5. Listar pacientes",
                    "6. Habitaciones Disponibles",
                    "7. Salir"
            };

            do {
                seleccion = JOptionPane.showOptionDialog(
                        null,
                        "Seleccione una opción:",
                        "Sistema de Gestión aria",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );


                switch (seleccion) {
                    case 0://1. Registrar paciente:
                        registrarPaciente();
                        break;
                    case 1://2. Agendar cita":
                        agendarCita();
                        break;
                    case 2://"3. Registrar tratamiento":
                        registrarTratamiento();
                        break;
                    case 3://"4. Buscar paciente":
                        buscarPaciente();
                        break;
                    case 4://"5. Listar pacientes":
                        listarPacientes();
                        break;
                    case 5://"6. Salir":
                        habitacionesDisponibles();
                        break;
                    case 6://"6. Salir":
                        break;
                    default:
                        seleccion = 6;
                }
            } while (seleccion != 6);
        }


    private static void registrarPaciente() {
        String id;
        String nombre;
        String apellido;
        int edad;
        String direccion;
        String telefono;

        while (true) {
            id = JOptionPane.showInputDialog("ID del paciente (formato: alfanumérico, máx 10 caracteres):");
            if (!Validaciones.validarId(id)) {
                JOptionPane.showMessageDialog(null, "ID inválido. Debe ser alfanumérico y máximo 10 caracteres.");
            } else break;
            }


        if (mapaPacientes.buscarPorId(id) != null) {
            JOptionPane.showMessageDialog(null, "Ya existe un paciente con ese ID.");
            return;
        }

        while (true) {
            nombre = JOptionPane.showInputDialog("Nombre del paciente:");
            if (!Validaciones.validarNoVacio(nombre)) {
                JOptionPane.showMessageDialog(null, "Nombre inválido.");
            } else break;
        }

        while (true) {
            apellido = JOptionPane.showInputDialog("Apellido del paciente:");
            if (!Validaciones.validarNoVacio(apellido)) {
                JOptionPane.showMessageDialog(null, "Apellido inválido.");
            } else break;
        }


        while (true) {
            String edadStr = JOptionPane.showInputDialog("Edad del paciente:");
            if (!Validaciones.validarEntero(edadStr)) {
                JOptionPane.showMessageDialog(null, "Edad inválida. Debe ser un número entero.");
            } else {
                edad = Integer.parseInt(edadStr);
                if (!Validaciones.validarEdad(edad)) {
                    JOptionPane.showMessageDialog(null, "Edad fuera de rango (0-120).");
                } else break;
            }
        }

        String genero = (String) JOptionPane.showInputDialog(
                null,
                "Género:",
                "Registro de Paciente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Masculino", "Femenino", "Otro"},
                "Masculino"
        );
        if (genero == null) return;

        while (true) {
            direccion = JOptionPane.showInputDialog("Dirección:");
            if (!Validaciones.validarNoVacio(direccion)) {
                JOptionPane.showMessageDialog(null, "Dirección inválida.");
            } else break;
        }

        while (true) {
            telefono = JOptionPane.showInputDialog("Teléfono (10 dígitos):");
            if (!Validaciones.validarTelefono(telefono)) {
                JOptionPane.showMessageDialog(null, "Teléfono inválido. Debe tener 10 dígitos.");
            } else break;
        }

        Paciente nuevoPaciente = new Paciente(id, nombre, apellido, edad, genero, direccion, telefono);
        pacientes.insertar(nuevoPaciente);
        mapaPacientes.agregar(nuevoPaciente);

        JOptionPane.showMessageDialog(null, "Paciente registrado exitosamente:\n" + nuevoPaciente);
    }

    private static void agendarCita() {
        String fechaStr;
        LocalDateTime fecha;
        String motivo;

        if (mapaPacientes.getTamaño() == 0) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados. Debe registrar un paciente primero.");
            return;
        }

        String idPaciente = JOptionPane.showInputDialog("ID del paciente:");
        if (idPaciente == null) return;

        Paciente paciente = mapaPacientes.buscarPorId(idPaciente);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente no encontrado.");
            return;
        }

        String[] opcionesDoctor = new String[doctores.getSize()];
        for (int i = 0; i < doctores.getSize(); i++) {
            Doctor doctor = doctores.obtener(i);
            opcionesDoctor[i] = doctor.getId() + " - " + doctor.getNombre() + " " + doctor.getApellido() + " (" + doctor.getEspecialidad() + ")";
        }

        String seleccionDoctor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione un doctor:",
                "Agendar Cita",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesDoctor,
                opcionesDoctor[0]
        );
        if (seleccionDoctor == null) return;

        String idDoctor = seleccionDoctor.split(" - ")[0];
        Doctor doctor = mapaDoctores.get(idDoctor);

        while (true) {
            fechaStr = JOptionPane.showInputDialog("Fecha de la cita (formato: dd/MM/yyyy HH:mm):");
            if (fechaStr == null || !Validaciones.validarFechaHora(fechaStr)) {
                JOptionPane.showMessageDialog(null, "Formato de fecha y hora inválido. Use dd/MM/yyyy HH:mm");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                fecha = LocalDateTime.parse(fechaStr, formatter);

                if (!Validaciones.validarFechaFutura(fecha)) {
                    JOptionPane.showMessageDialog(null, "La fecha debe ser futura.");
                } else break;
            }
        }

        while (true) {
            motivo = JOptionPane.showInputDialog("Motivo de la cita:");
            if (!Validaciones.validarNoVacio(motivo)) {
                JOptionPane.showMessageDialog(null, "Motivo inválido.");
            } else break;
        }

        String idCita = "C" + (pilaCitas.espacioDisponible()+1);
        Cita nuevaCita = new Cita(idCita, paciente, doctor, fecha, motivo);
        if (pilaCitas.apilar(nuevaCita)) {
            JOptionPane.showMessageDialog(null, "Cita agendada exitosamente:\n" + nuevaCita);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agendar la cita. La pila está llena.");
        }
    }

    private static void registrarTratamiento() {
        String descripcion;
        LocalDate fechaInicio;
        LocalDate fechaFin;

        if (mapaPacientes.getTamaño() == 0) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados. Debe registrar un paciente primero.");
            return;
        }

        String idPaciente = JOptionPane.showInputDialog("ID del paciente:");
        if (idPaciente == null) return;

        Paciente paciente = mapaPacientes.buscarPorId(idPaciente);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente no encontrado.");
            return;
        }

        while (true) {
            descripcion = JOptionPane.showInputDialog("Descripción del tratamiento:");
            if (!Validaciones.validarNoVacio(descripcion)) {
                JOptionPane.showMessageDialog(null, "Descripción inválida.");
            } else break;
        }

        while (true) {
            String fechaInicioStr;
            String fechaFinStr;

            while (true) {
                fechaInicioStr = JOptionPane.showInputDialog("Fecha de inicio (formato: dd/MM/yyyy):");
                if (fechaInicioStr == null || !Validaciones.validarFecha(fechaInicioStr)) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy");
                } else break;
            }

            while (true) {
                fechaFinStr = JOptionPane.showInputDialog("Fecha de finalización (formato: dd/MM/yyyy):");
                if (fechaFinStr == null || !Validaciones.validarFecha(fechaFinStr)) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy");
                } else break;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
            fechaFin = LocalDate.parse(fechaFinStr, formatter);

            if (fechaFin.isBefore(fechaInicio)) {
                JOptionPane.showMessageDialog(null, "La fecha de finalización debe ser posterior a la fecha de inicio.");
            } else break;
        }

        String medicamentos = JOptionPane.showInputDialog("Medicamentos:");
        if (medicamentos == null) {
            medicamentos = "";
        }

        String indicaciones = JOptionPane.showInputDialog("Indicaciones:");
        if (indicaciones == null) {
            indicaciones = "";
        }

        String idTratamiento = "T" + (tratamientos.getSize() + 1);
        Tratamiento nuevoTratamiento = new Tratamiento(idTratamiento, paciente, descripcion, fechaInicio, fechaFin, medicamentos, indicaciones);
        tratamientos.agregar(nuevoTratamiento);

        // Actualizar historial médico del paciente
        paciente.setHistorialMedico("Tratamiento: " + descripcion + " - Inicio: " + fechaInicio + " - Fin: " + fechaFin);

        JOptionPane.showMessageDialog(null, "Tratamiento registrado exitosamente:\n" + nuevoTratamiento);
    }

    private static void buscarPaciente() {
        if (mapaPacientes.getTamaño() == 0) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.");
            return;
        }

        String idPaciente = JOptionPane.showInputDialog("ID del paciente a buscar:");
        if (idPaciente == null) return;

        Paciente paciente = mapaPacientes.buscarPorId(idPaciente);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente no encontrado.");
            return;
        }

        // Mostrar información detallada del paciente
        StringBuilder info = new StringBuilder();
        info.append("Información del Paciente:\n");
        info.append("ID: ").append(paciente.getId()).append("\n");
        info.append("Nombre: ").append(paciente.getNombre()).append(" ").append(paciente.getApellido()).append("\n");
        info.append("Edad: ").append(paciente.getEdad()).append("\n");
        info.append("Género: ").append(paciente.getGenero()).append("\n");
        info.append("Dirección: ").append(paciente.getDireccion()).append("\n");
        info.append("Teléfono: ").append(paciente.getTelefono()).append("\n\n");
        info.append("Historial Médico:\n").append(paciente.getHistorialMedico());

        // Buscar citas del paciente
        info.append("\nCitas:\n");
        boolean tieneCitas = false;

        for (int i = pilaCitas.cantidad() - 1; i >= 0; i--) {
            Cita cita = pilaCitas.obtenerPorIndice(i);
            if (cita != null && cita.getPaciente().getId().equals(paciente.getId())) {
                info.append("- ").append(cita.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                        .append(" con Dr. ").append(cita.getDoctor().getApellido())
                        .append(" (").append(cita.getMotivo()).append(")\n");
                tieneCitas = true;
            }
        }

        if (!tieneCitas) {
            info.append("No tiene citas programadas.\n");
        }

        // Buscar tratamientos del paciente
        info.append("\nTratamientos:\n");
        boolean tieneTratamientos = false;
        for (int i = 0; i < tratamientos.getSize(); i++) {
            Tratamiento tratamiento = tratamientos.obtener(i);
            if (tratamiento.getPaciente().getId().equals(paciente.getId())) {
                info.append("- ").append(tratamiento.getDescripcion())
                        .append(" (").append(tratamiento.getFechaInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .append(" - ").append(tratamiento.getFechaFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .append(")\n");
                tieneTratamientos = true;
            }
        }
        if (!tieneTratamientos) {
            info.append("No tiene tratamientos registrados.\n");
        }

        JOptionPane.showMessageDialog(null, info.toString(), "Información del Paciente", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void listarPacientes() {
        if (mapaPacientes.getTamaño() == 0) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.");
            return;
        }

        StringBuilder lista = new StringBuilder("Lista de Pacientes:\n\n");

        mapaPacientes.mostrarPacientes();

        JOptionPane.showMessageDialog(null, lista.toString(), "Lista de Pacientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void habitacionesDisponibles() {
        habitaciones.mostrarHabitaciones();
        int piso = 1 , habitacion = 2;
       habitaciones.ocuparHabitacion(piso, habitacion);
        //habitaciones.liberarHabitacion(1, 2);
        //habitaciones.mostrarHabitaciones();
    }
}