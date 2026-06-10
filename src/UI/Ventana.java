package UI;

import Logica.Expendedor;

import java.awt.*;
import javax.swing.*;

/**
 * Ventana principal que se representa en {@link Main.Main}
 */
public class Ventana extends JFrame {

    public Ventana() {
        setTitle("Máquina Expendedora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(1,2));

        /** Añade los paneles por separado */
        PanelComprador panelComprador = new PanelComprador();
        PanelExpendedor panelExpendedor = new PanelExpendedor(panelComprador);
        add(panelComprador);
        add(panelExpendedor);
        pack();
        setLocationRelativeTo(null);
    }
}

