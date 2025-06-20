package GUI;

import Modelo.Paciente;
import Util.LectorArchivo;
import structure.listas.ListaEnlazada;

import javax.swing.*;

public class MenuPrincipalArchivo {
    private static final LectorArchivo lectorArchivo = new LectorArchivo() ;


    public void ejecutarMenuPrincipal() {
        lectorArchivo.cargarPacientes();
        String[] opciones = {"Ingresar Paciente", "Mostrar Pacientes", "Buscar Paciente", "Modificar Paciente", "Eliminar Paciente", "Salir"};
        int opcionSeleccionada;

        do {
            opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una opción:",
                    "Gestión de Pacientes",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcionSeleccionada) {
                case 0: // Ingresar Paciente
                    solicitarDatosEIngresarPaciente();
                    break;
                case 1: // Mostrar Pacientes
                    mostrarPacientes();
                    break;
                case 2: // Buscar Paciente
                    solicitarIdYBuscarPaciente();
                    break;
                case 3: // Modificar Paciente
                    solicitarIdYModificarPaciente();
                    break;
                case 4: // Eliminar Paciente
                    solicitarIdYEliminarPaciente();
                    break;
                case 5: // Salir
                    //JOptionPane.showMessageDialog(null, "Saliendo de la aplicación.", "Adiós", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default: // Cerrar ventana (X)
                    opcionSeleccionada = 5;
                    break;
            }
        } while (opcionSeleccionada != 5);
    }

    // --- Métodos que interactúan con LectorArchivo y JOptionPane ---

    public void solicitarDatosEIngresarPaciente() {
        String id = JOptionPane.showInputDialog("Ingrese ID del paciente:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = JOptionPane.showInputDialog("Ingrese Nombre:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        String apellido = JOptionPane.showInputDialog("Ingrese Apellido:");
        if (apellido == null || apellido.trim().isEmpty()) return;

        int edad = -1;
        try {
            String edadStr = JOptionPane.showInputDialog("Ingrese Edad:");
            if (edadStr == null) return;
            edad = Integer.parseInt(edadStr.trim());
            if (edad < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Edad inválida. Debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String genero = JOptionPane.showInputDialog("Ingrese Género:");
        if (genero == null || genero.trim().isEmpty()) return;
        String direccion = JOptionPane.showInputDialog("Ingrese Dirección:");
        if (direccion == null || direccion.trim().isEmpty()) return;
        String telefono = JOptionPane.showInputDialog("Ingrese Teléfono:");
        if (telefono == null || telefono.trim().isEmpty()) return;

        Paciente nuevoPaciente = new Paciente(id.trim(), nombre.trim(), apellido.trim(), edad, genero.trim(), direccion.trim(), telefono.trim());
        lectorArchivo.ingresarPaciente(nuevoPaciente);
    }

    public void mostrarPacientes() {

        ListaEnlazada listaActual = lectorArchivo.mostrarPacientes(); // Obtiene la lista de LectorArchivo
        if (listaActual.estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.", "Lista Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de Pacientes:\n\n");
        int i = 0;
        for (Paciente p : listaActual) {
            sb.append(i + 1).append(". ")
                    .append("ID: ").append(p.getId())
                    .append(", Nombre: ").append(p.getNombre()).append(" ").append(p.getApellido())
                    .append(", Edad: ").append(p.getEdad())
                    .append(", Género: ").append(p.getGenero())
                    .append(", Dirección: ").append(p.getDireccion())
                    .append(", Teléfono: ").append(p.getTelefono())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Pacientes Registrados", JOptionPane.PLAIN_MESSAGE);
    }

    public void solicitarIdYBuscarPaciente() {
        String idABuscar = JOptionPane.showInputDialog("Ingrese el ID del paciente a buscar:");
        if (idABuscar == null || idABuscar.trim().isEmpty()) {
            return;
        }

        ListaEnlazada paciente = lectorArchivo.mostrarPacientes();
        Paciente pacienteEncontrado = paciente.buscarPorId(idABuscar);

        if (pacienteEncontrado != null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Paciente Encontrado:\n" +
                            "ID: " + pacienteEncontrado.getId() + "\n" +
                            "Nombre: " + pacienteEncontrado.getNombre() + " " + pacienteEncontrado.getApellido() + "\n" +
                            "Edad: " + pacienteEncontrado.getEdad() + "\n" +
                            "Género: " + pacienteEncontrado.getGenero() + "\n" +
                            "Dirección: " + pacienteEncontrado.getDireccion() + "\n" +
                            "Teléfono: " + pacienteEncontrado.getTelefono(),
                    "Resultado de Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID '" + idABuscar + "' no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void solicitarIdYModificarPaciente() {
        String idAModificar = JOptionPane.showInputDialog("Ingrese el ID del paciente a modificar:");
        if (idAModificar == null || idAModificar.trim().isEmpty()) {
            return;
        }

        ListaEnlazada paciente = lectorArchivo.mostrarPacientes();
        Paciente pacienteActual = paciente.buscarPorId(idAModificar);

        if (pacienteActual != null) {

            // Simular la captura de nuevos datos, puedes hacer esto más interactivo si quieres
            String nuevoNombre = JOptionPane.showInputDialog("Nuevo Nombre (Dejar vacío para no cambiar):", pacienteActual.getNombre());
            String nuevoApellido = JOptionPane.showInputDialog("Nuevo Apellido (Dejar vacío para no cambiar):", pacienteActual.getApellido());
            String nuevaEdadStr = JOptionPane.showInputDialog("Nueva Edad (Dejar vacío para no cambiar):", String.valueOf(pacienteActual.getEdad()));
            String nuevoGenero = JOptionPane.showInputDialog("Nuevo Género (Dejar vacío para no cambiar):", pacienteActual.getGenero());
            String nuevaDireccion = JOptionPane.showInputDialog("Nueva Dirección (Dejar vacío para no cambiar):", pacienteActual.getDireccion());
            String nuevoTelefono = JOptionPane.showInputDialog("Nuevo Teléfono (Dejar vacío para no cambiar):", pacienteActual.getTelefono());

            int nuevaEdad = pacienteActual.getEdad(); // Mantener la edad actual por defecto
            if (nuevaEdadStr != null && !nuevaEdadStr.trim().isEmpty()) {
                try {
                    int parsedEdad = Integer.parseInt(nuevaEdadStr.trim());
                    if (parsedEdad >= 0) {
                        nuevaEdad = parsedEdad;
                    } else {
                        JOptionPane.showMessageDialog(null, "Edad inválida. Se mantendrá la edad actual.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Formato de edad inválido. Se mantendrá la edad actual.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }

            // Crea un nuevo objeto Paciente con los datos actualizados
            // El ID se mantiene el original para la búsqueda, los otros datos son los nuevos
            Paciente datosParaModificar = new Paciente(
                    pacienteActual.getId(), // El ID no cambia
                    (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) ? nuevoNombre.trim() : pacienteActual.getNombre(),
                    (nuevoApellido != null && !nuevoApellido.trim().isEmpty()) ? nuevoApellido.trim() : pacienteActual.getApellido(),
                    nuevaEdad,
                    (nuevoGenero != null && !nuevoGenero.trim().isEmpty()) ? nuevoGenero.trim() : pacienteActual.getGenero(),
                    (nuevaDireccion != null && !nuevaDireccion.trim().isEmpty()) ? nuevaDireccion.trim() : pacienteActual.getDireccion(),
                    (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) ? nuevoTelefono.trim() : pacienteActual.getTelefono()
            );

            lectorArchivo.modificarPaciente(idAModificar.trim(), datosParaModificar); 

        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID '" + idAModificar + "' no encontrado para modificar.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void solicitarIdYEliminarPaciente() {
        String idAEliminar = JOptionPane.showInputDialog("Ingrese el ID del paciente a eliminar:");
        if (idAEliminar == null || idAEliminar.trim().isEmpty()) {
            return;
        }
        lectorArchivo.eliminarPaciente(idAEliminar.trim()); 
    }
}