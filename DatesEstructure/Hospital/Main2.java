

// SistemaHospital.java
import Modelo.Cita;
import Modelo.Doctor;
import Modelo.Paciente;
import structure.Pilas.PilaUnidimensional;

import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList; // Implementa Queue y List
import java.util.Queue;
import java.util.Stack;

public class Main2 {

    // --- Estructuras de Datos ---
    // Pilas: Manejo del historial de consultas médicas.
    private static PilaUnidimensional historialConsultas = new PilaUnidimensional();
    // Colas: Organización de turnos para atención.
    // LinkedList implementa la interfaz Queue, por lo que puede usarse para colas.
    private static Queue<Paciente> colaTurnos = new LinkedList<>();

    // Listas Enlazadas: Gestión de pacientes registrados.
    // LinkedList también implementa List y es una lista enlazada.
    private static LinkedList<Paciente> listaPacientes = new LinkedList<>();

    // Matriz: Representación de habitaciones disponibles en el hospital.
    // 'Disponible' o 'Ocupada'. Podríamos almacenar el ID del paciente si está ocupada.
    private static String[][] habitacionesHospital;
    private static final int NUM_PISOS = 3; // Ejemplo: 3 pisos
    private static final int HABITACIONES_POR_PISO = 5; // Ejemplo: 5 habitaciones por piso

    public static void main(String[] args) {
        // Inicializar la matriz de habitaciones al inicio del programa
        inicializarHabitaciones(3, 5);

        int opcionPrincipal;
        do {
            String menuPrincipal = "SISTEMA DE GESTIÓN HOSPITALARIA\n\n" +
                    "1. Gestión de Pacientes (Listas Enlazadas)\n" +
                    "2. Gestión de Turnos (Colas)\n" +
                    "3. Historial de Consultas (Pilas)\n" +
                    "4. Gestión de Habitaciones (Matriz)\n" +
                    "5. Salir\n\n" +
                    "Seleccione una opción:";
            String input = JOptionPane.showInputDialog(null, menuPrincipal, "Menú Principal", JOptionPane.PLAIN_MESSAGE);

            if (input == null) { // Si el usuario presiona Cancelar o cierra el diálogo
                opcionPrincipal = 5;
            } else {
                try {
                    opcionPrincipal = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    opcionPrincipal = 0; // Para que el bucle continúe
                }
            }

            switch (opcionPrincipal) {
                case 1:
                    gestionarPacientes();
                    break;
                case 2:
                    gestionarTurnos();
                    break;
                case 3:
                    gestionarHistorialConsultas();
                    break;
                case 4:
                    gestionarHabitaciones();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema. ¡Gracias!", "Adiós", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    if (opcionPrincipal != 0) { // Evitar mensaje si ya hubo error de formato
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (opcionPrincipal != 5);
    }

    // --- Métodos de Gestión de Habitaciones (Matriz) ---
    private static void inicializarHabitaciones(int pisos, int habitacionesPorPiso) {
        habitacionesHospital = new String[pisos][habitacionesPorPiso];
        for (int i = 0; i < pisos; i++) {
            for (int j = 0; j < habitacionesPorPiso; j++) {
                habitacionesHospital[i][j] = "Disponible"; // Todas las habitaciones están disponibles al inicio
            }
        }
        // Para propósitos de demostración, ocupamos algunas habitaciones
        if (pisos > 0 && habitacionesPorPiso > 0) {
            if (habitacionesHospital.length > 0 && habitacionesHospital[0].length > 0) {
                habitacionesHospital[0][0] = "Ocupada (Paciente P001)";
            }
            if (habitacionesHospital.length > 1 && habitacionesHospital[1].length > 2) {
                habitacionesHospital[1][2] = "Ocupada (Paciente P002)";
            }
        }
    }

    private static void gestionarHabitaciones() {
        int opcion;
        do {
            String menuHabitaciones =
                    "1. Ver Estado de Habitaciones\n" +
                    "2. Asignar Habitación\n" +
                    "3. Liberar Habitación\n" +
                    "4. Volver al Menú Principal\n\n" +
                    "Seleccione una opción:";
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

    private static void mostrarEstadoHabitaciones() {
        StringBuilder sb = new StringBuilder("ESTADO DE HABITACIONES\n\n");
        sb.append("Piso \\ Hab |");
        for (int j = 0; j < HABITACIONES_POR_PISO; j++) {
            sb.append(String.format(" %-10s |", "Hab " + (j + 1)));
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

    private static void asignarHabitacion() {
        try {
            int piso = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de piso (1-" + NUM_PISOS + "):")) - 1;
            int habitacion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de habitación (1-" + HABITACIONES_POR_PISO + "):")) - 1;
            String idPaciente = JOptionPane.showInputDialog("Ingrese el ID del paciente a asignar:");

            if (piso >= 0 && piso < NUM_PISOS && habitacion >= 0 && habitacion < HABITACIONES_POR_PISO) {
                if (habitacionesHospital[piso][habitacion].equals("Disponible")) {
                    // Opcional: Verificar si el paciente existe en listaPacientes
                    Paciente pEncontrado = null;
                    for (Paciente p : listaPacientes) {
                        if (p.getId().equalsIgnoreCase(idPaciente)) {
                            pEncontrado = p;
                            break;
                        }
                    }

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

    private static void liberarHabitacion() {
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
    private static void gestionarPacientes() {
        int opcion;
        do {
            String menuPacientes = "GESTIÓN DE PACIENTES (Lista Enlazada)\n\n" +
                    "1. Registrar Nuevo Paciente\n" +
                    "2. Ver Lista de Pacientes\n" +
                    "3. Buscar Paciente por ID\n" +
                    "4. Actualizar Datos de Paciente\n" +
                    "5. Eliminar Paciente\n" +
                    "6. Volver al Menú Principal\n\n" +
                    "Seleccione una opción:";
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
                    break;
                default:
                    if (opcion != 0) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (opcion != 6);
    }

    private static void registrarPaciente() {
        String id = JOptionPane.showInputDialog("Ingrese ID del paciente:");
        if (id == null || id.trim().isEmpty()) return; // Cancelar

        // Verificar si el ID ya existe
        for (Paciente p : listaPacientes) {
            if (p.getId().equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(null, "Error: Ya existe un paciente con ese ID.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String nombre = JOptionPane.showInputDialog("Ingrese Nombre del paciente:");
        if (nombre == null) return;
        String apellido = JOptionPane.showInputDialog("Ingrese Apellido del paciente:");
        if (apellido == null) return;
        int edad = 0;
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
        listaPacientes.add(nuevoPaciente);
        JOptionPane.showMessageDialog(null, "Paciente " + nombre + " " + apellido + " registrado con éxito.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarPacientes() {
        if (listaPacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.", "Lista Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("LISTA DE PACIENTES REGISTRADOS:\n\n");
        for (Paciente p : listaPacientes) {
            sb.append(p.toString()).append("\n");
            sb.append("  Edad: ").append(p.getEdad()).append(", Género: ").append(p.getGenero()).append("\n");
            sb.append("  Teléfono: ").append(p.getTelefono()).append(", Dirección: ").append(p.getDireccion()).append("\n");
            sb.append("------------------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Pacientes Registrados", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void buscarPaciente() {
        String idBuscar = JOptionPane.showInputDialog("Ingrese el ID del paciente a buscar:");
        if (idBuscar == null || idBuscar.trim().isEmpty()) return;

        Paciente pacienteEncontrado = null;
        for (Paciente p : listaPacientes) {
            if (p.getId().equalsIgnoreCase(idBuscar)) {
                pacienteEncontrado = p;
                break;
            }
        }

        if (pacienteEncontrado != null) {
            StringBuilder sb = new StringBuilder("PACIENTE ENCONTRADO:\n\n");
            sb.append(pacienteEncontrado.toString()).append("\n");
            sb.append("  Edad: ").append(pacienteEncontrado.getEdad()).append(", Género: ").append(pacienteEncontrado.getGenero()).append("\n");
            sb.append("  Dirección: ").append(pacienteEncontrado.getDireccion()).append("\n");
            sb.append("  Teléfono: ").append(pacienteEncontrado.getTelefono()).append("\n");
            sb.append("  Historial Médico:\n").append(pacienteEncontrado.getHistorialMedico().isEmpty() ? "    (Vacío)" : pacienteEncontrado.getHistorialMedico());
            JOptionPane.showMessageDialog(null, sb.toString(), "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID " + idBuscar + " no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void actualizarPaciente() {
        String idBuscar = JOptionPane.showInputDialog("Ingrese el ID del paciente a actualizar:");
        if (idBuscar == null || idBuscar.trim().isEmpty()) return;

        Paciente pacienteEncontrado = null;
        for (Paciente p : listaPacientes) {
            if (p.getId().equalsIgnoreCase(idBuscar)) {
                pacienteEncontrado = p;
                break;
            }
        }

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

    private static void eliminarPaciente() {
        String idEliminar = JOptionPane.showInputDialog("Ingrese el ID del paciente a eliminar:");
        if (idEliminar == null || idEliminar.trim().isEmpty()) return;

        Paciente pacienteAEliminar = null;
        for (Paciente p : listaPacientes) {
            if (p.getId().equalsIgnoreCase(idEliminar)) {
                pacienteAEliminar = p;
                break;
            }
        }

        if (pacienteAEliminar != null) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar a " + pacienteAEliminar.getNombre() + " " + pacienteAEliminar.getApellido() + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                listaPacientes.remove(pacienteAEliminar);
                JOptionPane.showMessageDialog(null, "Paciente " + pacienteAEliminar.getNombre() + " " + pacienteAEliminar.getApellido() + " eliminado con éxito.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID " + idEliminar + " no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    // --- Métodos de Gestión de Turnos (Colas) ---
    private static void gestionarTurnos() {
        int opcion;
        do {
            String menuTurnos = "GESTIÓN DE TURNOS (Cola)\n\n" +
                    "1. Asignar Turno (Encolar Paciente)\n" +
                    "2. Atender Siguiente Paciente (Desencolar)\n" +
                    "3. Ver Próximo Turno\n" +
                    "4. Ver Todos los Turnos en Cola\n" +
                    "5. Volver al Menú Principal\n\n" +
                    "Seleccione una opción:";
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

    private static void asignarTurno() {
        String idPaciente = JOptionPane.showInputDialog("Ingrese el ID del paciente para asignar turno:");
        if (idPaciente == null || idPaciente.trim().isEmpty()) return;

        Paciente pacienteParaTurno = null;
        for (Paciente p : listaPacientes) {
            if (p.getId().equalsIgnoreCase(idPaciente)) {
                pacienteParaTurno = p;
                break;
            }
        }

        if (pacienteParaTurno != null) {
            if (colaTurnos.contains(pacienteParaTurno)) {
                JOptionPane.showMessageDialog(null, "El paciente " + pacienteParaTurno.getNombre() + " ya tiene un turno asignado y está en la cola.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            colaTurnos.offer(pacienteParaTurno); // Añade a la cola
            pacienteParaTurno.setTurno(colaTurnos.size()); // Asigna un número de turno simple
            JOptionPane.showMessageDialog(null, "Paciente " + pacienteParaTurno.getNombre() + " añadido a la cola de turnos. Su turno es el #" + pacienteParaTurno.getTurno() + ".", "Turno Asignado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID " + idPaciente + " no encontrado. Regístrelo primero.", "Paciente No Encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void atenderSiguienteTurno() {
        if (colaTurnos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes en la cola de turnos.", "Cola Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Paciente pacienteAtendido = colaTurnos.poll(); // Saca al primer paciente de la cola
        if (pacienteAtendido != null) {
            pacienteAtendido.setTurno(-1); // Resetea su turno
            JOptionPane.showMessageDialog(null, "Paciente " + pacienteAtendido.getNombre() + " " + pacienteAtendido.getApellido() + " ha sido atendido.", "Atención de Turno", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void verProximoTurno() {
        if (colaTurnos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes en la cola de turnos.", "Cola Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Paciente proximoPaciente = colaTurnos.peek(); // Mira al primer paciente sin removerlo
        JOptionPane.showMessageDialog(null, "Próximo paciente en turno: " + proximoPaciente.getNombre() + " " + proximoPaciente.getApellido() + " (ID: " + proximoPaciente.getId() + ")", "Próximo Turno", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void verTodosLosTurnos() {
        if (colaTurnos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes en la cola de turnos.", "Cola Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("PACIENTES EN COLA DE TURNOS:\n\n");
        int i = 1;
        for (Paciente p : colaTurnos) {
            sb.append(i++).append(". ").append(p.getNombre()).append(" ").append(p.getApellido()).append(" (ID: ").append(p.getId()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Cola de Turnos", JOptionPane.INFORMATION_MESSAGE);
    }


    // --- Métodos de Gestión de Historial de Consultas (Pilas) ---
    private static void gestionarHistorialConsultas() {
        int opcion;
        do {
            String menuHistorial = "GESTIÓN DE HISTORIAL DE CONSULTAS (Pila)\n\n" +
                    "1. Registrar Nueva Consulta (Push)\n" +
                    "2. Ver Última Consulta (Peek)\n" +
                    "3. Deshacer Última Consulta (Pop)\n" +
                    "4. Ver Historial Completo\n" +
                    "5. Volver al Menú Principal\n\n" +
                    "Seleccione una opción:";
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

    private static void registrarConsulta() {
        String idCita = JOptionPane.showInputDialog("Ingrese ID de la cita:");
        if (idCita == null || idCita.trim().isEmpty()) return;

        String idPaciente = JOptionPane.showInputDialog("Ingrese ID del paciente para la consulta:");
        if (idPaciente == null || idPaciente.trim().isEmpty()) return;

        Paciente pacienteCita = null;
        for (Paciente p : listaPacientes) {
            if (p.getId().equalsIgnoreCase(idPaciente)) {
                pacienteCita = p;
                break;
            }
        }

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
        doctorCita = new Doctor("D" + (int)(Math.random()*1000), nombreDoctor, apellidoDoctor, especialidadDoctor); // ID aleatorio simple

        LocalDateTime fechaConsulta = null;
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

    private static void verUltimaConsulta() {
        if (historialConsultas.estaVacia()) {
            JOptionPane.showMessageDialog(null, "El historial de consultas está vacío.", "Historial Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Cita ultimaCita = historialConsultas.cima(); // Mira la cima de la pila sin remover
        JOptionPane.showMessageDialog(null, "ÚLTIMA CONSULTA (Cima de la pila):\n\n" + ultimaCita.toString(), "Última Consulta", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void deshacerUltimaConsulta() {
        if (historialConsultas.estaVacia()) {
            JOptionPane.showMessageDialog(null, "El historial de consultas está vacío, no hay nada que deshacer.", "Historial Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Cita citaDeshecha = historialConsultas.desapilar(); // Saca la cima de la pila
        JOptionPane.showMessageDialog(null, "Última consulta (ID: " + citaDeshecha.getId() + ") ha sido deshecha/eliminada del historial.", "Consulta Deshecha", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void verHistorialCompleto() {
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