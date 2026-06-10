package Main;
import UI.Ventana;

import javax.swing.SwingUtilities;

/**
 * Clase principal que crea la ventana
 */
public class Main {
    public static void main(String[] args) {
        /** Swing requiere que las interfaces se ejecuten en su propio hilo seguro */
        SwingUtilities.invokeLater(() -> new Ventana().setVisible(true));
    }
}