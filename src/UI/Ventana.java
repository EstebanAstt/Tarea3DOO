package UI;

import java.awt.*;
import javax.swing.*;
public class Ventana extends JFrame {

    public Ventana() {

        setTitle("Máquina Expendedora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(1,2));

        PanelComprador panelComprador = new PanelComprador();
        PanelExpendedor panelExpendedor = new PanelExpendedor(panelComprador);
        add(panelComprador);
        add(panelExpendedor);

        pack();
        setLocationRelativeTo(null);
    }
}

