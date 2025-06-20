package GUI;

import Modelo.Paciente;
import Util.LectorArchivo;
import structure.Arboles.ArbolPacientes;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MenuArbol {
    private Scanner scanner;
    private final lector3 cargar = new lector3();
    private final ArbolPacientes arbol = new ArbolPacientes();

    public void rMenu() {
        // Cargar pacientes al inicio, como lo tenías.
        // Asegúrate de que 'cargarPacientes' se adapte a recibir 'ArbolPacientes'
        // como se explicó en una respuesta anterior.
        cargar.cargarPacientes(arbol);

        int opcion;
        do {
            String input = JOptionPane.showInputDialog(
                    null,
                    "--- Menú de Gestión de Pacientes (Árbol AVL) ---\n" +
                            "1. Buscar paciente por ID\n" +
                            "2. Mostrar pacientes (In-Orden)\n" +
                            "3. Mostrar pacientes (Pre-Orden)\n" +
                            "4. Mostrar pacientes (Post-Orden)\n" +
                            "0. Salir\n\n" +
                            "Ingrese su opción:",
                    "Menú Principal AVL",
                    JOptionPane.PLAIN_MESSAGE
            );

            // Si el usuario presiona Cancelar o cierra la ventana
            if (input == null) {
                opcion = 0; // Se considera como salir
            } else {
                try {
                    opcion = Integer.parseInt(input);

                    switch (opcion) {
                        case 1:
                            buscarPaciente();
                            break;
                        case 2:
                            mostrarPacientesOrdenados("In-Orden", arbol.obtenerInOrdenString());
                            break;
                        case 3: // Nueva opción para Pre-Orden
                            mostrarPacientesOrdenados("Pre-Orden", arbol.obtenerPreOrdenString());
                            break;
                        case 4: // Nueva opción para Post-Orden
                            mostrarPacientesOrdenados("Post-Orden", arbol.obtenerPostOrdenString());
                            break;
                        case 0:
                            JOptionPane.showMessageDialog(null, "Saliendo del programa. ¡Hasta pronto!", "Adiós", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, intente de nuevo.", "Error de Opción", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                    opcion = -1; // Para que el bucle continúe
                }
            }
        } while (opcion != 0);
    }

    private void buscarPaciente() {
        String idBuscar = JOptionPane.showInputDialog(
                null,
                "--- Buscar Paciente ---\nIngrese el ID del paciente a buscar:",
                "Buscar Paciente",
                JOptionPane.QUESTION_MESSAGE
        );

        if (idBuscar == null || idBuscar.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Búsqueda cancelada o ID vacío.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Paciente encontrado = arbol.buscar(idBuscar.trim()); // Usar trim() para limpiar espacios

        if (encontrado != null) {
            JOptionPane.showMessageDialog(null, "Paciente encontrado:\n" + encontrado.toString(), "Paciente Encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Paciente con ID '" + idBuscar + "' no encontrado.", "Paciente No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarPacientesOrdenados(String tipoOrden, String contenido) {
        if (arbol.estaVacia()) {
            JOptionPane.showMessageDialog(null, "El árbol de pacientes está vacío.", "Lista de Pacientes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (contenido != null && !contenido.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "--- Lista de Pacientes (" + tipoOrden + ") ---\n\n" + contenido,
                        "Lista de Pacientes",
                        JOptionPane.PLAIN_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(null, "No hay pacientes para mostrar o un error al generar la lista en " + tipoOrden + ".", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}


class lector3 extends LectorArchivo {
    private static final String NOMBRE_ARCHIVO = "C:\\Users\\pheg9\\IdeaProjects\\EstructureofDates-Hospital\\DatesEstructure\\Hospital\\Pacientes.txt"; // Para Windows

    protected void cargarPacientes(ArbolPacientes arbolPacientes) {
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
                        arbolPacientes.insertar(paciente);

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
}
