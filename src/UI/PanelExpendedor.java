package UI;

import javax.swing.*;
import java.awt.*;

public class PanelExpendedor {
    public void paintComponent(Graphics graphExpendedor) {
        try {
            ImageIcon expendedor = new ImageIcon("resources/Sprites/Expendedor.png");
            Image fondo = expendedor.getImage();
            graphExpendedor.drawImage(fondo,20,20,null);
        } catch (Exception e) {
            System.out.println("No se encontró el archivo de imagen");
        }
    }
}
