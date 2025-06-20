package Util;
import Modelo.Paciente;
import structure.listas.ListaEnlazada;

import javax.swing.*;
import java.io.*;

public class LectorArchivo {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\pheg9\\IdeaProjects\\EstructureofDates-Hospital\\DatesEstructure\\Hospital\\Pacientes.txt"; // Para Windows
    private ListaEnlazada listaPacientes;

    public LectorArchivo() {
        //this.pacientes = new ArrayList<>();
        this.listaPacientes = new ListaEnlazada();
    }

    public void cargarPacientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 7) {
                    try {
                        String id = datos[0];
                        String nombre = datos[1];
                        String apellido = datos[2];
                        int edad = Integer.parseInt(datos[3]);
                        String genero = datos[4];
                        String direccion = datos[5];
                        String telefono = datos[6];
                        //pacientes.add(new Paciente(id, nombre, apellido, edad, genero, direccion, telefono));
                        listaPacientes.agregar(new Paciente(id, nombre, apellido, edad, genero, direccion, telefono));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error al parsear edad en la línea: " + linea, "Error de Lectura", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Pacientes cargados exitosamente.", "Carga de Datos", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "El archivo '" + NOMBRE_ARCHIVO + "' no fue encontrado. Se creará uno nuevo al guardar.", "Archivo no encontrado", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage(), "Error de Lectura", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarPacientes() {
        // Asegúrate de que 'pacientes' contenga al menos un elemento
        if (listaPacientes == null || listaPacientes.estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes para guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, false))) { // ¡Aquí está el cambio clave!
            // Obtén el último paciente de la lista
            for (Paciente p : listaPacientes) {
                // Escribe solo la información del último paciente
                String lineaPaciente = p.getId() + "," +
                        p.getNombre() + "," +
                        p.getApellido() + "," +
                        p.getEdad() + "," +
                        p.getGenero() + "," +
                        p.getDireccion() + "," +
                        p.getTelefono();

                bw.write(lineaPaciente);
                bw.newLine(); // Añade un salto de línea después del paciente
            }
            //JOptionPane.showMessageDialog(null, "Datos guardados exitosamente en el archivo.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage(), "Error de Escritura", JOptionPane.ERROR_MESSAGE);
        }
    }


    public boolean ingresarPaciente(Paciente nuevoPaciente) {

        if (nuevoPaciente == null || nuevoPaciente.getId() == null) {
            JOptionPane.showMessageDialog(null, "Paciente o ID no pueden ser nulos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (listaPacientes.buscarPorId(nuevoPaciente.getId()) != null) {
            JOptionPane.showMessageDialog(null, "Ya existe un paciente con ese ID.", "ID Duplicado", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        //pacientes.add(nuevoPaciente);
        listaPacientes.agregar(nuevoPaciente);
        guardarPacientes();
        JOptionPane.showMessageDialog(null, "Paciente ingresado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }


    public ListaEnlazada mostrarPacientes() {
        return listaPacientes; // Retorna una copia para evitar modificación externa directa
    }

    public boolean modificarPaciente(String idAModificar, Paciente pacienteAModificar) {
        if (idAModificar == null || idAModificar.trim().isEmpty() || pacienteAModificar == null) {
            JOptionPane.showMessageDialog(null, "ID o datos del paciente inválidos para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }



        Paciente pacienteOriginal = listaPacientes.buscarPorId(idAModificar);
        if (pacienteOriginal != null) {
            // Actualiza los campos del paciente original con los nuevos datos
            pacienteOriginal.setNombre(pacienteAModificar.getNombre());
            pacienteOriginal.setApellido(pacienteAModificar.getApellido());
            pacienteOriginal.setEdad(pacienteAModificar.getEdad());
            pacienteOriginal.setGenero(pacienteAModificar.getGenero());
            pacienteOriginal.setDireccion(pacienteAModificar.getDireccion());
            pacienteOriginal.setTelefono(pacienteAModificar.getTelefono());

            guardarPacientes();
            JOptionPane.showMessageDialog(null, "Paciente modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID '" + idAModificar + "' no encontrado para modificar.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public boolean eliminarPaciente(String idAEliminar) {
        if (idAEliminar == null || idAEliminar.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID no puede estar vacío para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean eliminado = listaPacientes.eliminarPorId(idAEliminar);

        if (eliminado) {
            guardarPacientes();
            JOptionPane.showMessageDialog(null, "Paciente con ID '" + idAEliminar + "' eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID '" + idAEliminar + "' no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}