package UI;

import java.awt.*;
import javax.swing.*;
public class Ventana extends JFrame {

    public Ventana() {

        setTitle("Máquina Expendedora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(1,2));

        add(new PanelComprador());
        add(new PanelExpendedor());

        pack();
        setLocationRelativeTo(null);
    }
}

