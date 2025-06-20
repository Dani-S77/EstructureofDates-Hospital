
import GUI.MenuArbol;
import GUI.MenuPrincipal;
import GUI.MenuPrincipalArchivo;

import javax.swing.JOptionPane;

public class Main2 {

    private static MenuPrincipal menu = new MenuPrincipal();
    private static MenuPrincipalArchivo Archivo = new MenuPrincipalArchivo();
    private static MenuArbol menuArbol = new MenuArbol();

    public static void main(String[] args) {
        Archivo.ejecutarMenuPrincipal();
        // Inicializar la matriz de habitaciones al inicio del programa
        menu.inicializarHabitaciones();




        int opcionPrincipal;
        do {
            String menuPrincipal = """
                    SISTEMA DE GESTIÓN HOSPITALARIA
                    
                    1. Gestión de Pacientes (Listas Enlazadas)
                    2. Gestión de Turnos (Colas)
                    3. Historial de Consultas (Pilas)
                    4. Gestión de Habitaciones (Matriz)
                    5. Salir
                    
                    Seleccione una opción:""";
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
                    menu.gestionarPacientes();
                    break;
                case 2:
                    menu.gestionarTurnos();
                    break;
                case 3:
                    menu.gestionarHistorialConsultas();
                    break;
                case 4:
                    menu.gestionarHabitaciones();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema. ¡Gracias!", "Adiós", JOptionPane.INFORMATION_MESSAGE);
                    menuArbol.rMenu();
                    break;
                default:
                    if (opcionPrincipal != 0) { // Evitar mensaje si ya hubo error de formato
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (opcionPrincipal != 5);
    }
}
