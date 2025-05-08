package Util;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validaciones {

    // Validar que un string no esté vacío
    public static boolean validarNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    // Validar formato de ID (alfanumérico)
    public static boolean validarId(String id) {
        return id != null && Pattern.matches("^[A-Z0-9]{1,10}$", id);
    }

    // Validar que sea un número entero
    public static boolean validarEntero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validar que sea un número positivo
    public static boolean validarPositivo(int numero) {
        return numero > 0;
    }

    // Validar formato de teléfono
    public static boolean validarTelefono(String telefono) {
        return telefono != null && Pattern.matches("^[0-9]{10}$", telefono);
    }

    // Validar formato de fecha
    public static boolean validarFecha(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Validar formato de fecha y hora
    public static boolean validarFechaHora(String fechaHora) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime.parse(fechaHora, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Validar que una fecha sea futura
    public static boolean validarFechaFutura(LocalDateTime fecha) {
        return fecha.isAfter(LocalDateTime.now());
    }

    // Validar rango de edad
    public static boolean validarEdad(int edad) {
        return edad >= 0 && edad <= 120;
    }
}
