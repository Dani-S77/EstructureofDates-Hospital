//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Modelo.Paciente;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        String[] opciones = {"Registrar paciente", "ver pacientes", "Salir"};
        int OpcSelec;
        Paciente nuevoPaciente = null;

        do {
            OpcSelec = JOptionPane.showOptionDialog(
                    null,
                    "Que desea hacer?",
                    "Registro Hospital X",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            switch (OpcSelec) { ///registrar paciente
                case 0:
                    String id = JOptionPane.showInputDialog(null, "Ingrese el ID del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                    if (id != null && !id.trim().isEmpty()) {
                        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (nombre != null && !nombre.trim().isEmpty()) {
                            String apellido = JOptionPane.showInputDialog(null, "Ingrese el apellido del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                            if (apellido != null && !apellido.trim().isEmpty()) {
                                String edadStr = JOptionPane.showInputDialog(null, "Ingrese la edad del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                                try {
                                    int edad = Integer.parseInt(edadStr);
                                    String genero = JOptionPane.showInputDialog(null, "Ingrese el género del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                                    if (genero != null) {
                                        String direccion = JOptionPane.showInputDialog(null, "Ingrese la dirección del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                                        if (direccion != null) {
                                            String telefono = JOptionPane.showInputDialog(null, "Ingrese el teléfono del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                                            if (telefono != null) {
                                                String historialMedico = JOptionPane.showInputDialog(null, "Ingrese el historial médico del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                                                if (historialMedico != null) {
                                                    nuevoPaciente = new Paciente(id, nombre, apellido, edad, genero, direccion, telefono);
                                                    JOptionPane.showMessageDialog(null, "Paciente con ID " + id + " registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

                                                }
                                            }
                                        }
                                    }

                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(null, "La edad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else if (apellido != null) {
                                JOptionPane.showMessageDialog(null, "El apellido del paciente no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (nombre != null) {
                            JOptionPane.showMessageDialog(null, "El nombre del paciente no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (id != null) {
                        JOptionPane.showMessageDialog(null, "La ID del paciente no puede estar vacio", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case 1: //ver paciente
                    JOptionPane.showMessageDialog(null,nuevoPaciente.toString());
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion no valida");
            }
        } while (OpcSelec != 2);


        }
    }

