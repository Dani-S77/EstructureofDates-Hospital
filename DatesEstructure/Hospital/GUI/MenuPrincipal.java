package GUI;
import Modelo.Cita;
import Modelo.Doctor;
import Modelo.Paciente;
import Util.LectorArchivo;
import structure.Colas.Cola;
import structure.Pilas.PilaUnidimensional;
import structure.listas.ListaEnlazada;

import javax.swing.JOptionPane;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Stack;

public class MenuPrincipal {

    public String[][] habitacionesHospital;
    // --- Estructuras de Datos ---
    // Pilas: Manejo del historial de consultas médicas.
    public final PilaUnidimensional historialConsultas = new PilaUnidimensional();
    // Colas: Organización de turnos para atención.
    // LinkedList implementa la interfaz Queue, por lo que puede usarse para colas.
    public final Cola colaTurnos = new Cola();

    // Listas Enlazadas: Gestión de pacientes registrados.
    public final ListaEnlazada listaPacientes = new ListaEnlazada();

    // Matriz: Representación de habitaciones disponibles en el hospital.
    // 'Disponible' o 'Ocupada'. Podríamos almacenar el ID del paciente si está ocupada.
    public final int NUM_PISOS = 3;
    public final int HABITACIONES_POR_PISO = 5;

    private final lector2 cargar = new lector2();

    // --- Métodos de Gestión de Habitaciones (Matriz) ---
    public void inicializarHabitaciones() {
        cargar.cargarPacientes(listaPacientes);
        habitacionesHospital = new String[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                habitacionesHospital[i][j] = "Disponible"; // Todas las habitaciones están disponibles al inicio
            }
        }
        // Para propósitos de demostración, ocupamos algunas habitaciones
        if (3 > 0 && 5 > 0) {
            if (habitacionesHospital.length > 0 && habitacionesHospital[0].length > 0) {
                habitacionesHospital[0][0] = "Ocupada (Paciente P001)";
            }
            if (habitacionesHospital.length > 1 && habitacionesHospital[1].length > 2) {
                habitacionesHospital[1][2] = "Ocupada (Paciente P002)";
            }
        }
    }

    public void gestionarHabitaciones() {
        int opcion;
        do {
            String menuHabitaciones =
                    """
                            1. Ver Estado de Habitaciones
                            2. Asignar Habitación
                            3. Liberar Habitación
                            4. Volver al Menú Principal
                            
                            Seleccione una opción:""";
            String input = JOptionPane.showInputDialog(null, menuHabitaciones, "Gestión de Habitaciones", JOptionPane.PLAIN_MESSAGE);

            if (input == null) {
                opcion = 4;
            } else {
                try {
                    opcion = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    opcion = 0;
                }
            }

            switch (opcion) {
                case 1:
                    mostrarEstadoHabitaciones();
                    break;
                case 2:
                    asignarHabitacion();
                    break;
                case 3:
                    liberarHabitacion();
                    break;
                case 4:
                    break; // Volver al menú principal
                default:
                    if (opcion != 0) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (opcion != 4);
    }

    public void mostrarEstadoHabitaciones() {
        StringBuilder sb = new StringBuilder("ESTADO DE HABITACIONES\n\n");
        sb.append("Piso \\ Hab |");
        for (int j = 0; j < HABITACIONES_POR_PISO; j++) {
            sb.append(String.format(" %-13s |", "Hab " + (j + 1)));
        }
        sb.append("\n------------");
        for (int j = 0; j < HABITACIONES_POR_PISO; j++) {
            sb.append("------------");
        }
        sb.append("\n");

        for (int i = 0; i < NUM_PISOS; i++) {
            sb.append(String.format("Piso %-4d |", (i + 1)));
            for (int j = 0; j < HABITACIONES_POR_PISO; j++) {
                String estado = habitacionesHospital[i][j];
                String display = estado.equals("Disponible") ? "Disponible" : "Ocupada";
                sb.append(String.format(" %-10s |", display));
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Estado de Habitaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    public void asignarHabitacion() {
        try {
            int piso = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de piso (1-" + NUM_PISOS + "):")) - 1;
            int habitacion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de habitación (1-" + HABITACIONES_POR_PISO + "):")) - 1;
            String idPaciente = JOptionPane.showInputDialog("Ingrese el ID del paciente a asignar:");

            if (piso >= 0 && piso < NUM_PISOS && habitacion >= 0 && habitacion < HABITACIONES_POR_PISO) {
                if (habitacionesHospital[piso][habitacion].equals("Disponible")) {

                    // Opcional: Verificar si el paciente existe en listaPacientes
                    Paciente pEncontrado = listaPacientes.buscarPorId(idPaciente);

                    if (pEncontrado != null) {
                        habitacionesHospital[piso][habitacion] = "Ocupada (Paciente " + idPaciente + ")";
                        JOptionPane.showMessageDialog(null, "Habitación " + (piso + 1) + "-" + (habitacion + 1) + " asignada al paciente " + pEncontrado.getNombre() + " " + pEncontrado.getApellido() + ".", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Paciente con ID " + idPaciente + " no encontrado en el registro. No se asignó la habitación.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La habitación " + (piso + 1) + "-" + (habitacion + 1) + " ya está ocupada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Números de piso o habitación fuera de rango.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void liberarHabitacion() {
        try {
            int piso = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de piso (1-" + NUM_PISOS + "):")) - 1;
            int habitacion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de habitación (1-" + HABITACIONES_POR_PISO + "):")) - 1;

            if (piso >= 0 && piso < NUM_PISOS && habitacion >= 0 && habitacion < HABITACIONES_POR_PISO) {
                if (!habitacionesHospital[piso][habitacion].equals("Disponible")) {
                    String infoOcupante = habitacionesHospital[piso][habitacion];
                    habitacionesHospital[piso][habitacion] = "Disponible";
                    JOptionPane.showMessageDialog(null, "Habitación " + (piso + 1) + "-" + (habitacion + 1) + " liberada. Anteriormente: " + infoOcupante, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "La habitación " + (piso + 1) + "-" + (habitacion + 1) + " ya está disponible.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Números de piso o habitación fuera de rango.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // --- Métodos de Gestión de Pacientes (Listas Enlazadas) ---
    public void gestionarPacientes() {

        int opcion;
        do {
            String menuPacientes = """
                    GESTIÓN DE PACIENTES (Lista Enlazada)
                    
                    1. Registrar Nuevo Paciente
                    2. Ver Lista de Pacientes
                    3. Buscar Paciente por ID
                    4. Actualizar Datos de Paciente
                    5. Eliminar Paciente
                    6. Volver al Menú Principal
                    
                    Seleccione una opción:""";
            String input = JOptionPane.showInputDialog(null, menuPacientes, "Gestión de Pacientes", JOptionPane.PLAIN_MESSAGE);

            if (input == null) {
                opcion = 6;
            } else {
                try {
                    opcion = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    opcion = 0;
                }
            }

            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    mostrarPacientes();
                    break;
                case 3:
                    buscarPaciente();
                    break;
                case 4:
                    actualizarPaciente();
                    break;
                case 5:
                    eliminarPaciente();
                    break;
                case 6:
                    cargar.guardarPacientes(listaPacientes);
                    break;
                default:
                    if (opcion != 0) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (opcion != 6);
    }

    public void registrarPaciente() {
        String id = JOptionPane.showInputDialog("Ingrese ID del paciente:");
        if (id == null || id.trim().isEmpty()) return; // Cancelar

        // Verificar si el ID ya existe
        if (listaPacientes.buscarPorId(id) != null) {
            JOptionPane.showMessageDialog(null, "Ya existe un paciente con ese ID.");
            return;
        }

        String nombre = JOptionPane.showInputDialog("Ingrese Nombre del paciente:");
        if (nombre == null) return;
        String apellido = JOptionPane.showInputDialog("Ingrese Apellido del paciente:");
        if (apellido == null) return;
        int edad;
        try {
            String edadStr = JOptionPane.showInputDialog("Ingrese Edad del paciente:");
            if (edadStr == null) return;
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Edad inválida. El paciente no fue registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String genero = JOptionPane.showInputDialog("Ingrese Género del paciente:");
        if (genero == null) return;
        String direccion = JOptionPane.showInputDialog("Ingrese Dirección del paciente:");
        if (direccion == null) return;
        String telefono = JOptionPane.showInputDialog("Ingrese Teléfono del paciente:");
        if (telefono == null) return;

        Paciente nuevoPaciente = new Paciente(id, nombre, apellido, edad, genero, direccion, telefono);
        listaPacientes.agregar(nuevoPaciente);
        JOptionPane.showMessageDialog(null, "Paciente " + nombre + " " + apellido + " registrado con éxito.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarPacientes() {
        if (listaPacientes.estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.", "Lista Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        listaPacientes.mostrarPacientes();
    }

    public void buscarPaciente() {
        String idBuscar = JOptionPane.showInputDialog("Ingrese el ID del paciente a buscar:");
        if (idBuscar == null || idBuscar.trim().isEmpty()) return;

        Paciente pacienteEncontrado = listaPacientes.buscarPorId(idBuscar);

        if (pacienteEncontrado != null) {
            String sb = "PACIENTE ENCONTRADO:\n\n" + pacienteEncontrado + "\n" +
                    "  Edad: " + pacienteEncontrado.getEdad() + ", Género: " + pacienteEncontrado.getGenero() + "\n" +
                    "  Dirección: " + pacienteEncontrado.getDireccion() + "\n" +
                    "  Teléfono: " + pacienteEncontrado.getTelefono() + "\n" +
                    "  Historial Médico:\n" + (pacienteEncontrado.getHistorialMedico().isEmpty() ? "    (Vacío)" : pacienteEncontrado.getHistorialMedico());
            JOptionPane.showMessageDialog(null, sb, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID " + idBuscar + " no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void actualizarPaciente() {
        String idBuscar = JOptionPane.showInputDialog("Ingrese el ID del paciente a actualizar:");
        if (idBuscar == null || idBuscar.trim().isEmpty()) return;

        Paciente pacienteEncontrado = listaPacientes.buscarPorId(idBuscar);
        if (pacienteEncontrado != null) {
            String nuevoNombre = JOptionPane.showInputDialog("Nuevo Nombre (" + pacienteEncontrado.getNombre() + "):", pacienteEncontrado.getNombre());
            if (nuevoNombre != null) pacienteEncontrado.setNombre(nuevoNombre);

            String nuevoApellido = JOptionPane.showInputDialog("Nuevo Apellido (" + pacienteEncontrado.getApellido() + "):", pacienteEncontrado.getApellido());
            if (nuevoApellido != null) pacienteEncontrado.setApellido(nuevoApellido);

            try {
                String nuevaEdadStr = JOptionPane.showInputDialog("Nueva Edad (" + pacienteEncontrado.getEdad() + "):", String.valueOf(pacienteEncontrado.getEdad()));
                if (nuevaEdadStr != null && !nuevaEdadStr.trim().isEmpty()) {
                    pacienteEncontrado.setEdad(Integer.parseInt(nuevaEdadStr));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Edad inválida. No se actualizó la edad.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            String nuevoGenero = JOptionPane.showInputDialog("Nuevo Género (" + pacienteEncontrado.getGenero() + "):", pacienteEncontrado.getGenero());
            if (nuevoGenero != null) pacienteEncontrado.setGenero(nuevoGenero);

            String nuevaDireccion = JOptionPane.showInputDialog("Nueva Dirección (" + pacienteEncontrado.getDireccion() + "):", pacienteEncontrado.getDireccion());
            if (nuevaDireccion != null) pacienteEncontrado.setDireccion(nuevaDireccion);

            String nuevoTelefono = JOptionPane.showInputDialog("Nuevo Teléfono (" + pacienteEncontrado.getTelefono() + "):", pacienteEncontrado.getTelefono());
            if (nuevoTelefono != null) pacienteEncontrado.setTelefono(nuevoTelefono);

            JOptionPane.showMessageDialog(null, "Datos del paciente " + pacienteEncontrado.getNombre() + " " + pacienteEncontrado.getApellido() + " actualizados con éxito.", "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID " + idBuscar + " no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void eliminarPaciente() {
        String idEliminar = JOptionPane.showInputDialog("Ingrese el ID del paciente a eliminar:");
        if (idEliminar == null || idEliminar.trim().isEmpty()) return;

        Paciente pacienteAEliminar = listaPacientes.buscarPorId(idEliminar);

        if (pacienteAEliminar != null) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar a " + pacienteAEliminar.getNombre() + " " + pacienteAEliminar.getApellido() + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                listaPacientes.eliminarPorId(idEliminar);
                JOptionPane.showMessageDialog(null, "Paciente " + pacienteAEliminar.getNombre() + " " + pacienteAEliminar.getApellido() + " eliminado con éxito.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID " + idEliminar + " no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    // --- Métodos de Gestión de Turnos (Colas) ---
    public void gestionarTurnos() {
        int opcion;
        do {
            String menuTurnos = """
                    GESTIÓN DE TURNOS (Cola)
                    
                    1. Asignar Turno (Encolar Paciente)
                    2. Atender Siguiente Paciente (Desencolar)
                    3. Ver Próximo Turno
                    4. Ver Todos los Turnos en Cola
                    5. Volver al Menú Principal
                    
                    Seleccione una opción:""";
            String input = JOptionPane.showInputDialog(null, menuTurnos, "Gestión de Turnos", JOptionPane.PLAIN_MESSAGE);

            if (input == null) {
                opcion = 5;
            } else {
                try {
                    opcion = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    opcion = 0;
                }
            }

            switch (opcion) {
                case 1:
                    asignarTurno();
                    break;
                case 2:
                    atenderSiguienteTurno();
                    break;
                case 3:
                    verProximoTurno();
                    break;
                case 4:
                    verTodosLosTurnos();
                    break;
                case 5:
                    break;
                default:
                    if (opcion != 0) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (opcion != 5);
    }

    public void asignarTurno() {
        String idPaciente = JOptionPane.showInputDialog("Ingrese el ID del paciente para asignar turno:");
        if (idPaciente == null || idPaciente.trim().isEmpty()) return;

        Paciente pacienteParaTurno = listaPacientes.buscarPorId(idPaciente);

        if (pacienteParaTurno != null) {
            if (colaTurnos.contiene(idPaciente)) {
                JOptionPane.showMessageDialog(null, "El paciente " + pacienteParaTurno.getNombre() + " ya tiene un turno asignado y está en la cola.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            colaTurnos.encolar(pacienteParaTurno); // Añade a la cola
            pacienteParaTurno.setTurno(colaTurnos.obtenerTamaño()); // Asigna un número de turno simple
            JOptionPane.showMessageDialog(null, "Paciente " + pacienteParaTurno.getNombre() + " añadido a la cola de turnos. Su turno es el #" + pacienteParaTurno.getTurno() + ".", "Turno Asignado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID " + idPaciente + " no encontrado. Regístrelo primero.", "Paciente No Encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atenderSiguienteTurno() {
        if (colaTurnos.estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes en la cola de turnos.", "Cola Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Paciente pacienteAtendido = colaTurnos.desencolar(); // Saca al primer paciente de la cola
        if (pacienteAtendido != null) {
            pacienteAtendido.setTurno(-1); // Resetea su turno
            JOptionPane.showMessageDialog(null, "Paciente " + pacienteAtendido.getNombre() + " " + pacienteAtendido.getApellido() + " ha sido atendido.", "Atención de Turno", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void verProximoTurno() {
        if (colaTurnos.estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes en la cola de turnos.", "Cola Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Paciente proximoPaciente = colaTurnos.obtenerFrente(); // Mira al primer paciente sin removerlo
        JOptionPane.showMessageDialog(null, "Próximo paciente en turno: " + proximoPaciente.getNombre() + " " + proximoPaciente.getApellido() + " (ID: " + proximoPaciente.getId() + ")", "Próximo Turno", JOptionPane.INFORMATION_MESSAGE);
    }

    public void verTodosLosTurnos() {
        if (colaTurnos.estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes en la cola de turnos.", "Cola Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("PACIENTES EN COLA DE TURNOS:\n\n");
        int i = 1;
        Cola colaTemporal = new Cola(); // Necesitas una instancia de tu cola personalizada
        while (!colaTurnos.estaVacia()) {
            Paciente p = colaTurnos.desencolar();
            sb.append(i++).append(". ").append(p.getNombre()).append(" ").append(p.getApellido()).append(" (ID: ").append(p.getId()).append(")\n");
            colaTemporal.encolar(p); // Vuelve a encolar en la temporal
        }
        // Ahora la cola original está vacía, pasa los elementos de la temporal a la original
        while (!colaTemporal.estaVacia()) {
            colaTurnos.encolar(colaTemporal.desencolar());
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Cola de Turnos", JOptionPane.INFORMATION_MESSAGE);
    }


    // --- Métodos de Gestión de Historial de Consultas (Pilas) ---
    public void gestionarHistorialConsultas() {
        int opcion;
        do {
            String menuHistorial = """
                    GESTIÓN DE HISTORIAL DE CONSULTAS (Pila)
                    
                    1. Registrar Nueva Consulta (Push)
                    2. Ver Última Consulta (Peek)
                    3. Deshacer Última Consulta (Pop)
                    4. Ver Historial Completo
                    5. Volver al Menú Principal
                    
                    Seleccione una opción:""";
            String input = JOptionPane.showInputDialog(null, menuHistorial, "Historial de Consultas", JOptionPane.PLAIN_MESSAGE);

            if (input == null) {
                opcion = 5;
            } else {
                try {
                    opcion = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    opcion = 0;
                }
            }

            switch (opcion) {
                case 1:
                    registrarConsulta();
                    break;
                case 2:
                    verUltimaConsulta();
                    break;
                case 3:
                    deshacerUltimaConsulta();
                    break;
                case 4:
                    verHistorialCompleto();
                    break;
                case 5:
                    break;
                default:
                    if (opcion != 0) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (opcion != 5);
    }

    public void registrarConsulta() {
        String idCita = JOptionPane.showInputDialog("Ingrese ID de la cita:");
        if (idCita == null || idCita.trim().isEmpty()) return;

        String idPaciente = JOptionPane.showInputDialog("Ingrese ID del paciente para la consulta:");
        if (idPaciente == null || idPaciente.trim().isEmpty()) return;

        Paciente pacienteCita = listaPacientes.buscarPorId(idPaciente);

        if (pacienteCita == null) {
            JOptionPane.showMessageDialog(null, "Paciente con ID " + idPaciente + " no encontrado. Registre al paciente primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simulación de Doctores disponibles (podría ser otra LinkedList o array)
        Doctor doctorCita = new Doctor("D001", "Dr. Juan", "Pérez", "General");
        String nombreDoctor = JOptionPane.showInputDialog("Ingrese Nombre del Doctor (Ej. Dr. Juan Pérez):");
        if (nombreDoctor == null) return;
        String apellidoDoctor = JOptionPane.showInputDialog("Ingrese Nombre del Doctor (Ej. Dr. Juan Pérez):");
        if (nombreDoctor == null) return;
        String especialidadDoctor = JOptionPane.showInputDialog("Ingrese Especialidad del Doctor (Ej. General):");
        if (especialidadDoctor == null) return;
        doctorCita = new Doctor("D" + (int) (Math.random() * 1000), nombreDoctor, apellidoDoctor, especialidadDoctor); // ID aleatorio simple

        LocalDateTime fechaConsulta;
        String fechaStr = JOptionPane.showInputDialog("Ingrese Fecha y Hora de la consulta (YYYY-MM-DD HH:MM):");
        if (fechaStr == null) return;
        try {
            fechaConsulta = LocalDateTime.parse(fechaStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha/hora inválido. Use YYYY-MM-DD HH:MM. Cita no registrada.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String motivo = JOptionPane.showInputDialog("Ingrese Motivo de la consulta:");
        if (motivo == null) return;

        Cita nuevaCita = new Cita(idCita, pacienteCita, doctorCita, fechaConsulta, motivo);
        historialConsultas.apilar(nuevaCita);// Añade la cita a la pila
        pacienteCita.setHistorialMedico("Consulta con " + doctorCita.getNombre() + " (" + fechaConsulta.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "): " + motivo);
        JOptionPane.showMessageDialog(null, "Consulta registrada con éxito para " + pacienteCita.getNombre() + ".", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void verUltimaConsulta() {
        if (historialConsultas.estaVacia()) {
            JOptionPane.showMessageDialog(null, "El historial de consultas está vacío.", "Historial Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Cita ultimaCita = historialConsultas.cima(); // Mira la cima de la pila sin remover
        JOptionPane.showMessageDialog(null, "ÚLTIMA CONSULTA (Cima de la pila):\n\n" + ultimaCita.toString(), "Última Consulta", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deshacerUltimaConsulta() {
        if (historialConsultas.estaVacia()) {
            JOptionPane.showMessageDialog(null, "El historial de consultas está vacío, no hay nada que deshacer.", "Historial Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Cita citaDeshecha = historialConsultas.desapilar(); // Saca la cima de la pila
        JOptionPane.showMessageDialog(null, "Última consulta (ID: " + citaDeshecha.getId() + ") ha sido deshecha/eliminada del historial.", "Consulta Deshecha", JOptionPane.INFORMATION_MESSAGE);
    }

    public void verHistorialCompleto() {
        if (historialConsultas.estaVacia()) {
            JOptionPane.showMessageDialog(null, "El historial de consultas está vacío.", "Historial Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("HISTORIAL COMPLETO DE CONSULTAS (De la más reciente a la más antigua):\n\n");
        // Iterar la pila sin vaciarla
        Stack<Cita> tempStack = new Stack<>();
        while (!historialConsultas.estaVacia()) {
            Cita cita = historialConsultas.cima();
            sb.append(cita.toString()).append("\n------------------------------------------\n");
            tempStack.push(cita); // Mueve al stack temporal
        }
        // Restaurar la pila original
        while (!tempStack.isEmpty()) {
            historialConsultas.apilar(tempStack.pop());
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Historial de Consultas", JOptionPane.INFORMATION_MESSAGE);
    }
}

class lector2 extends LectorArchivo {
    private static final String NOMBRE_ARCHIVO = "C:\\Users\\pheg9\\IdeaProjects\\EstructureofDates-Hospital\\DatesEstructure\\Hospital\\Pacientes.txt"; // Para Windows

    protected void cargarPacientes(ListaEnlazada listaPacientes) {
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Dividir la línea por las comas para obtener los datos de cada paciente
                String[] datos = linea.split(",");

                // Verificar que tengamos la cantidad de datos esperada (7 en este caso: id, nombre, apellido, edad, genero, direccion, telefono)
                if (datos.length == 7) {
                    try {
                        // Parsear los datos y crear un nuevo objeto Paciente
                        String id = datos[0];
                        String nombre = datos[1];
                        String apellido = datos[2];
                        int edad = Integer.parseInt(datos[3]); // Convertir la edad a int
                        String genero = datos[4];
                        String direccion = datos[5];
                        String telefono = datos[6];

                        Paciente paciente = new Paciente(id, nombre, apellido, edad, genero, direccion, telefono);

                        // Agregar el paciente a tu ListaEnlazada
                        listaPacientes.agregar(paciente);

                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear un número en la línea: " + linea + " - " + e.getMessage());
                        // Puedes mostrar un JOptionPane aquí si lo deseas, pero podría ser molesto para muchas líneas erróneas.
                    }
                } else {
                    System.err.println("Línea con formato incorrecto, se esperaban 7 campos: " + linea);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de pacientes: " + e.getMessage(), "Error de Lectura", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void guardarPacientes(ListaEnlazada listaPacientes) {
        // Asegúrate de que 'listaPacientes' no esté vacía
        if (listaPacientes == null || listaPacientes.estaVacia()) { // Usa el método de tu ListaEnlazada
            JOptionPane.showMessageDialog(null, "No hay pacientes para guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, false))) {
            // Itera directamente sobre tu ListaEnlazada gracias a que implementa Iterable
            for (Paciente p : listaPacientes) { // ¡Aquí está el cambio clave!
                String lineaPaciente = p.getId() + "," +
                        p.getNombre() + "," +
                        p.getApellido() + "," +
                        p.getEdad() + "," +
                        p.getGenero() + "," +
                        p.getDireccion() + "," +
                        p.getTelefono();

                bw.write(lineaPaciente);
                bw.newLine();
            }
            //JOptionPane.showMessageDialog(null, "Datos guardados exitosamente en el archivo.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage(), "Error de Escritura", JOptionPane.ERROR_MESSAGE);
        }
    }

}