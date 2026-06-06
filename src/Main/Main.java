package Main;
import UI.PanelExpendedor;
import UI.Ventana;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Swing requiere que las interfaces se ejecuten en su propio hilo seguro
        SwingUtilities.invokeLater(() -> {
            PanelExpendedor ventana = new PanelExpendedor();
            ventana.setVisible(true);
        });
    }
}