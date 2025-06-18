import structure.Arboles.ArbolPacientes;
import structure.Colas.Cola;
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
    private static final ArbolPacientes pacientes = new ArbolPacientes();
    private static final ListaSimple<Doctor> doctores = new ListaSimple<>();
    private static final ListaSimple<Cita> citas = new ListaSimple<>();
    private static final ListaSimple<Tratamiento> tratamientos = new ListaSimple<>();
    private static final ListaEnlazada mapaPacientes = new ListaEnlazada();
    private static final Map<String, Doctor> mapaDoctores = new HashMap<>();
    private static final PilaUnidimensional pilaCitas = new PilaUnidimensional();
    private static final Matriz habitaciones = new Matriz();
    private static final Cola turno = new Cola();

    public static void main(String[] args) {
        // Agregar algunos datos de ejemplo
        inicializarDatos();

        // Mostrar el menú principal
        mostrarMenuPrincipal();
    }

    private static void inicializarDatos() {
        // Doctores de ejemplo
        Doctor doc1 = new Doctor("D001", "Juan", "Pérez", "Cardiología");
        Doctor doc2 = new Doctor("D002", "María", "Gómez", "Pediatría");
        Doctor doc3 = new Doctor("D003", "Carlos", "Rodríguez", "Traumatología");

        doctores.agregar(doc1);
        doctores.agregar(doc2);
        doctores.agregar(doc3);

        mapaDoctores.put(doc1.getId(), doc1);
        mapaDoctores.put(doc2.getId(), doc2);
        mapaDoctores.put(doc3.getId(), doc3);
    }

    private static void mostrarMenuPrincipal() {
        int seleccion;
           /* String[] opciones = {
                    "1. Registrar paciente",
                    "2. Agendar cita",
                    "3. Registrar tratamiento",
                    "4. Buscar paciente",
                    "5. Siguiente paciente",
                    "6. Habitaciones Disponibles",
                    "7. Salir"
            };*/

        String[] opciones = {
                "1. Gestion de pacientes registrados (Lista enlazada)",//Registrar pacientes, tratamientos y buscar paciente
                "2. Agendar cita (Pilas)",
                "3. Turno de atención (Colas)",
                "4. Habitaciones disponibles (Matriz)",
                "5. Salir"
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
                case 0://1. Gestion de pacientes registrados (Lista enlazada)":
                    registrarPaciente();
                    break;
                case 1://2. Agendar cita (Pilas)":
                    agendarCita();
                    break;
                case 2://"Turno de atención (Colas)":
                    registrarTratamiento();
                    break;
                case 3://" Habitaciones disponibles (Matriz):"
                    buscarPaciente();
                    break;
                case 4://"5. Salir":
                    break;
                default:
                    seleccion = 4;
            }
        } while (seleccion != 4);


            /*
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
                    case 4: // Lista de espera
                        turnoEspera();
                        break;
                    case 5://"6. habitaciones disponibles":
                        habitacionesDisponibles();
                        break;
                    case 6://"7. Salir":
                        break;
                    default:
                        seleccion = 6;
                }
            } while (seleccion != 6);

            */
    }

    private static void menuPaciente() {
        String[] opcionesMenu = {
                "Registrar nuevo paciente",
                "Registrar tratamiento",
                "Buscar paciente",
                "Listar pacientes",
                "Salir" // La última opción será para salir
        };

        int seleccion; // Para almacenar la opción seleccionada por el usuario

        do {
            // Mostrar el menú con botones
            seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una opción:",
                    "Menú Principal del Consultorio",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcionesMenu,                     // Array de Strings con el texto de los botones
                    opcionesMenu[0]                   // Opción preseleccionada
            );

            switch (seleccion) {
                case 0: // Corresponde a "Registrar nuevo paciente"
                    registrarPaciente();
                    break;
                case 1: // Corresponde a "Registrar tratamiento"
                    registrarTratamiento();
                    break;
                case 2: // Corresponde a "Buscar paciente"
                    buscarPaciente();
                    break;
                case 3: // Corresponde a "Listar pacientes"
                    listarPacientes();
                    break;
                case 4: // Corresponde a "Salir"
                case JOptionPane.CLOSED_OPTION: // También si el usuario cierra el diálogo (-1)
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, intente de nuevo.", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        } while (seleccion != 4 && seleccion != JOptionPane.CLOSED_OPTION); // El bucle continúa hasta que se seleccione 'Salir' o se cierre el diálogo
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

        turno.encolar(id);
        nuevoPaciente.setTurno(turno.obtenerTamaño());
        JOptionPane.showMessageDialog(null, "Su turno en la cola es: " + nuevoPaciente.getTurno());

        pacientes.insertar(nuevoPaciente);
        mapaPacientes.agregar(nuevoPaciente);
        JOptionPane.showMessageDialog(null, "Paciente registrado exitosamente:\n" + nuevoPaciente);
    }

    private static void agendarCita() {
        String fechaStr;
        LocalDateTime fecha;
        String motivo;

        if (mapaPacientes.getTamano() == 0) {
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

        String idCita = "C" + (pilaCitas.espacioDisponible() + 1);
        Cita nuevaCita = new Cita(idCita, paciente, doctor, fecha, motivo);
        if (pilaCitas.apilar(nuevaCita)) {
            JOptionPane.showMessageDialog(null, "Cita agendada exitosamente:\n");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agendar la cita. La pila está llena.");
        }
    }

    private static void registrarTratamiento() {
        String descripcion;
        LocalDate fechaInicio;
        LocalDate fechaFin;

        if (mapaPacientes.getTamano() == 0) {
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
        Paciente paciente;
        boolean tieneCitas;
        Tratamiento tratamiento;

        if (mapaPacientes.getTamano() == 0) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.");
            return;
        }

        String idPaciente = JOptionPane.showInputDialog("ID del paciente a buscar:");
        if (idPaciente == null) return;

        paciente = mapaPacientes.buscarPorId(idPaciente);
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
        tieneCitas = false;

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
            tratamiento = tratamientos.obtener(i);
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

        // Definimos las opciones de los botones
        String[] opciones = {
                "Editar informacion paciente",
                "Editar tratamiento",
                "Eliminar paciente",
                "Salir"
        };
        int seleccion; // Para almacenar la opción seleccionada por el usuario

        do {
            // Mostrar el menú con botones
            seleccion = JOptionPane.showOptionDialog(
                    null,                             // Componente padre (null para que esté centrado en la pantalla)
                    "Que quiere hacer?",         // Mensaje a mostrar
                    "Menú Paciente",
                    JOptionPane.DEFAULT_OPTION,       // Tipo de opción (los botones se manejarán por el array)
                    JOptionPane.QUESTION_MESSAGE,     // Tipo de mensaje (icono de pregunta)
                    null,                             // Icono personalizado (null para usar el predeterminado)
                    opciones,                     // Array de Strings con el texto de los botones
                    opciones[0]                   // Opción preseleccionada (el primer botón por defecto)
            );

            switch (seleccion) {
                case 0: // "Editar informacion paciente"
                    editarPaciente(idPaciente);
                    break;
                case 1: // "Editar tratamiento"
                    editarTratamiento(paciente);
                    break;
                case 2: // "Eliminar paciente"
                    break;
                case 3: // Corresponde a "Salir"
                case JOptionPane.CLOSED_OPTION: // También si el usuario cierra el diálogo (-1)
                    JOptionPane.showMessageDialog(null, "Saliendo del programa. ¡Hasta pronto!", "Adiós", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    // Esto rara vez debería ocurrir con showOptionDialog si los botones están bien definidos
                    JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, intente de nuevo.", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        } while (seleccion != 3 && seleccion != JOptionPane.CLOSED_OPTION); // El bucle continúa hasta que se seleccione 'Salir' o se cierre el diálogo
    }


    private static void editarPaciente(String idPaciente) {

        Paciente pacienteExistente = mapaPacientes.buscarPorId(idPaciente);
        JOptionPane.showMessageDialog(null, "Información actual del paciente:\n" + pacienteExistente.toString());

        String[] opcionesEdicion = {"Nombre", "Apellido", "Edad", "Género", "Dirección", "Teléfono", "Cancelar"};
        int opcionSeleccionada;

        do {
            opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione el dato a editar para el paciente " + pacienteExistente.getNombre() + " " + pacienteExistente.getApellido() + ":",
                    "Editar Información del Paciente",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcionesEdicion,
                    opcionesEdicion[0]
            );

            switch (opcionSeleccionada) {
                case 0: // Nombre
                    String nuevoNombre;
                    while (true) {
                        nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
                        if (!Validaciones.validarNoVacio(nuevoNombre)) {
                            JOptionPane.showMessageDialog(null, "Nombre inválido.");
                        } else break;
                    }
                    pacienteExistente.setNombre(nuevoNombre);
                    JOptionPane.showMessageDialog(null, "Nombre actualizado exitosamente.");
                    break;
                case 1: // Apellido
                    String nuevoApellido;
                    while (true) {
                        nuevoApellido = JOptionPane.showInputDialog("Ingrese el nuevo apellido:");
                        if (!Validaciones.validarNoVacio(nuevoApellido)) {
                            JOptionPane.showMessageDialog(null, "Apellido inválido.");
                        } else break;
                    }
                    pacienteExistente.setApellido(nuevoApellido);
                    JOptionPane.showMessageDialog(null, "Apellido actualizado exitosamente.");
                    break;
                case 2: // Edad
                    int nuevaEdad;
                    while (true) {
                        String edadStr = JOptionPane.showInputDialog("Ingrese la nueva edad:");
                        if (!Validaciones.validarEntero(edadStr)) {
                            JOptionPane.showMessageDialog(null, "Edad inválida. Debe ser un número entero.");
                        } else {
                            nuevaEdad = Integer.parseInt(edadStr);
                            if (!Validaciones.validarEdad(nuevaEdad)) {
                                JOptionPane.showMessageDialog(null, "Edad fuera de rango (0-120).");
                            } else break;
                        }
                    }
                    pacienteExistente.setEdad(nuevaEdad);
                    JOptionPane.showMessageDialog(null, "Edad actualizada exitosamente.");
                    break;
                case 3: // Género
                    String nuevoGenero = (String) JOptionPane.showInputDialog(
                            null,
                            "Seleccione el nuevo género:",
                            "Editar Género",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"Masculino", "Femenino", "Otro"},
                            pacienteExistente.getGenero()
                    );
                    if (nuevoGenero != null) {
                        pacienteExistente.setGenero(nuevoGenero);
                        JOptionPane.showMessageDialog(null, "Género actualizado exitosamente.");
                    }
                    break;
                case 4: // Dirección
                    String nuevaDireccion;
                    while (true) {
                        nuevaDireccion = JOptionPane.showInputDialog("Ingrese la nueva dirección:");
                        if (!Validaciones.validarNoVacio(nuevaDireccion)) {
                            JOptionPane.showMessageDialog(null, "Dirección inválida.");
                        } else break;
                    }
                    pacienteExistente.setDireccion(nuevaDireccion);
                    JOptionPane.showMessageDialog(null, "Dirección actualizada exitosamente.");
                    break;
                case 5: // Teléfono
                    String nuevoTelefono;
                    while (true) {
                        nuevoTelefono = JOptionPane.showInputDialog("Ingrese el nuevo teléfono (10 dígitos):");
                        if (!Validaciones.validarTelefono(nuevoTelefono)) {
                            JOptionPane.showMessageDialog(null, "Teléfono inválido. Debe tener 10 dígitos.");
                        } else break;
                    }
                    pacienteExistente.setTelefono(nuevoTelefono);
                    JOptionPane.showMessageDialog(null, "Teléfono actualizado exitosamente.");
                    break;
                case 6: // Cancelar
                    JOptionPane.showMessageDialog(null, "Edición cancelada.");
                    break;
                default:
                    break;
            }
        } while (opcionSeleccionada != 6 && opcionSeleccionada != JOptionPane.CLOSED_OPTION); // Bucle hasta cerrar la ventana

        if (opcionSeleccionada != 6 && opcionSeleccionada != JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(null, "Información del paciente actualizada:\n" + pacienteExistente);
        }
    }

    private static void editarTratamiento(Paciente paciente) {
        Tratamiento temp = null;
        Tratamiento tratamiento;
        boolean tieneTratamientos = false;
        for (int i = 0; i < tratamientos.getSize(); i++) {
            tratamiento = tratamientos.obtener(i);
            tieneTratamientos = true;
            }

        for (int i = 0; i < tratamientos.getSize(); i++) {
            tratamiento = tratamientos.obtener(i);
            if (tratamiento.getPaciente().getId().equals(paciente.getId())){
                temp = tratamientos.obtener(i);
                break;}
            }

        tratamiento = temp;
        // Modificar descripción
        while (tieneTratamientos =! false) {
            String nuevaDescripcion = JOptionPane.showInputDialog("Nueva descripción del tratamiento (anterior: " + tratamiento.getDescripcion() + "):");
            if (nuevaDescripcion == null) break; // Si cancela, mantiene el valor anterior
            if (!Validaciones.validarNoVacio(nuevaDescripcion)) {
                JOptionPane.showMessageDialog(null, "Descripción inválida.");
            } else {
                tratamiento.setDescripcion(nuevaDescripcion);
                break;
            }
        }

        // Modificar fechas de inicio y fin
        LocalDate nuevaFechaInicio;
        LocalDate nuevaFechaFin;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            String fechaInicioStr;
            String fechaFinStr;

            while (true) {
                fechaInicioStr = JOptionPane.showInputDialog("Nueva fecha de inicio (formato: dd/MM/yyyy, anterior: " + tratamiento.getFechaInicio().format(formatter) + "):");
                if (fechaInicioStr == null) { // Si cancela, mantiene el valor anterior
                    nuevaFechaInicio = tratamiento.getFechaInicio();
                    break;
                }
                if (!Validaciones.validarFecha(fechaInicioStr)) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy");
                } else {
                    nuevaFechaInicio = LocalDate.parse(fechaInicioStr, formatter);
                    break;
                }
            }

            while (true) {
                fechaFinStr = JOptionPane.showInputDialog("Nueva fecha de finalización (formato: dd/MM/yyyy, anterior: " + tratamiento.getFechaFin().format(formatter) + "):");
                if (fechaFinStr == null) { // Si cancela, mantiene el valor anterior
                    nuevaFechaFin = tratamiento.getFechaFin();
                    break;
                }
                if (!Validaciones.validarFecha(fechaFinStr)) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy");
                } else {
                    nuevaFechaFin = LocalDate.parse(fechaFinStr, formatter);
                    break;
                }
            }

            if (nuevaFechaFin.isBefore(nuevaFechaInicio)) {
                JOptionPane.showMessageDialog(null, "La fecha de finalización debe ser posterior a la fecha de inicio.");
                // Si las fechas son inválidas, se vuelve a pedir ambas
                if (fechaInicioStr != null && fechaFinStr != null)
                    continue; // Si no se canceló, se vuelve a intentar
                else break; // Si alguna se canceló, se sale del bucle de fechas manteniendo las anteriores
            } else {
                tratamiento.setFechaInicio(nuevaFechaInicio);
                tratamiento.setFechaFin(nuevaFechaFin);
                break;
            }
        }

        // Modificar medicamentos
        String nuevosMedicamentos = JOptionPane.showInputDialog("Nuevos medicamentos (anterior: " + tratamiento.getMedicamentos() + "):");
        if (nuevosMedicamentos != null) {
            tratamiento.setMedicamentos(nuevosMedicamentos);
        }

        // Modificar indicaciones
        String nuevasIndicaciones = JOptionPane.showInputDialog("Nuevas indicaciones (anterior: " + tratamiento.getIndicaciones() + "):");
        if (nuevasIndicaciones != null) {
            tratamiento.setIndicaciones(nuevasIndicaciones);
        }

        // Opcional: Actualizar el historial médico del paciente con la información modificada
        tratamiento.getPaciente().setHistorialMedico(
                "Tratamiento: " + tratamiento.getDescripcion() +
                        " - Inicio: " + tratamiento.getFechaInicio() +
                        " - Fin: " + tratamiento.getFechaFin()
        );

        JOptionPane.showMessageDialog(null, "Tratamiento modificado exitosamente:\n" + tratamiento);
    }

    private static void eliminarPaciente() {
    }

    private static void listarPacientes() {
        if (mapaPacientes.getTamano() == 0) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.");
            return;
        }

        StringBuilder lista = new StringBuilder("Lista de Pacientes:\n\n");

        mapaPacientes.mostrarPacientes();

        JOptionPane.showMessageDialog(null, lista.toString(), "Lista de Pacientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void habitacionesDisponibles() {
        habitaciones.mostrarHabitaciones();
        int piso = 1, habitacion = 2;
        habitaciones.ocuparHabitacion(piso, habitacion);
        //habitaciones.liberarHabitacion(1, 2);
        //habitaciones.mostrarHabitaciones();
    }

    private static void turnoEspera() {
        Paciente pT = mapaPacientes.buscarPorId(turno.desencolar());

        JOptionPane.showMessageDialog(null, "Siguiente Turno:" + pT.getTurno() + " " + pT.getId());
    }
}