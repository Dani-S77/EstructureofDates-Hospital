//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Modelo.Doctor;
import Modelo.Paciente;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        String[] opciones = {"Registrar paciente", "Ver pacientes","Salir"};
        int OpcSelec;
        Paciente nuevoPaciente = null;
        Doctor doc = null;

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

                    String idDStr = null ;
                    int idDoc = -1;
                    while (idDoc < 0) {
                        idDStr = JOptionPane.showInputDialog(null, "Ingrese el ID del doctor:", "Registro de doctor", JOptionPane.PLAIN_MESSAGE);
                        if (idDStr == null || idDStr.trim().isEmpty() ) JOptionPane.showMessageDialog(null, "El ID del doctor es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        try {
                            idDoc = Integer.parseInt(idDStr);
                            if (idDoc < 0) {
                                JOptionPane.showMessageDialog(null, "El ID no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    String nombreD = null;
                    while (nombreD == null || nombreD.trim().isEmpty()) {
                        nombreD = JOptionPane.showInputDialog(null, "Ingrese el nombre del doctor:", "Registro de Doctor", JOptionPane.PLAIN_MESSAGE);
                        if (nombreD == null || nombreD.trim().isEmpty()) JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    String apellidoD = null;
                    while (apellidoD == null || apellidoD.trim().isEmpty()) {
                        apellidoD = JOptionPane.showInputDialog(null, "Ingrese el apellido del doctor:", "Registro de Doctor", JOptionPane.PLAIN_MESSAGE);
                        if (apellidoD.trim().isEmpty() || apellidoD == null) {
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    String especialidad = null;
                    while (especialidad == null || especialidad.trim().isEmpty()) {
                        especialidad = JOptionPane.showInputDialog(null, "Ingrese la especialidad del doctor:", "Registro de Doctor", JOptionPane.PLAIN_MESSAGE);
                        if (especialidad.trim().isEmpty() || especialidad == null) {
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    String telefonoDStr = null;
                    int telefonoD = -1;
                    while (telefonoD < 0) {
                        telefonoDStr = JOptionPane.showInputDialog(null, "Ingrese la edad del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (telefonoDStr.trim().isEmpty() || telefonoDStr == null) {
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        try {
                            telefonoD = Integer.parseInt(telefonoDStr);
                            if (telefonoD < 0) {
                                JOptionPane.showMessageDialog(null, "La edad no puede ser negativa.", "Error", JOptionPane.ERROR_MESSAGE);
                                telefonoD = -1; // Reset para continuar el bucle
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "La edad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    int id = -1;
                    String idStr = null;

                    while (id < 0) {
                        idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (idStr == null || idStr.trim().isEmpty() ) JOptionPane.showMessageDialog(null, "El ID del paciente es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        try {
                            id = Integer.parseInt(idStr);
                            if (id < 0) {
                                JOptionPane.showMessageDialog(null, "El ID no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }


                    String nombre = null;
                    while (nombre == null || nombre.trim().isEmpty()) {
                        nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (nombre == null || nombre.trim().isEmpty()) JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    String apellido = null;
                    while (apellido == null || apellido.trim().isEmpty()) {
                        apellido = JOptionPane.showInputDialog(null, "Ingrese el apellido del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (apellido.trim().isEmpty() || apellido == null) {
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    String edadStr = null;
                    int edad = -1;
                    while (edad < 0) {
                        edadStr = JOptionPane.showInputDialog(null, "Ingrese la edad del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (edadStr.trim().isEmpty() || edadStr == null) {
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        try {
                            edad = Integer.parseInt(edadStr);
                            if (edad < 0) {
                                JOptionPane.showMessageDialog(null, "La edad no puede ser negativa.", "Error", JOptionPane.ERROR_MESSAGE);
                                edad = -1; // Reset para continuar el bucle
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "La edad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    String genero = null;
                    while (genero == null || genero.trim().isEmpty()) {
                        genero = JOptionPane.showInputDialog(null, "Ingrese el género del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (genero.trim().isEmpty() || genero == null)
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    String direccion = null;
                    while (direccion == null || direccion.trim().isEmpty()) {
                        direccion = JOptionPane.showInputDialog(null, "Ingrese la dirección del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (direccion.trim().isEmpty() || direccion == null)
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    String telefono = null;
                    while (telefono == null || telefono.trim().isEmpty()) {
                        telefono = JOptionPane.showInputDialog(null, "Ingrese el teléfono del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (telefono.trim().isEmpty() || telefono == null)
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    String historialMedico = null;
                    while (historialMedico == null || historialMedico.trim().isEmpty()) {
                        historialMedico = JOptionPane.showInputDialog(null, "Ingrese el historial médico del paciente:", "Registro de Paciente", JOptionPane.PLAIN_MESSAGE);
                        if (historialMedico.trim().isEmpty() || historialMedico == null)
                            JOptionPane.showMessageDialog(null, "Dato ingresado incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    nuevoPaciente = new Paciente(idStr,nombre,apellido,edad,genero,direccion,telefono);



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

