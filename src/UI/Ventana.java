package UI;

import java.awt.*;
import javax.swing.*;
public class Ventana extends JFrame {
    public Ventana(){
        super();
        this.setTitle("esta Ventana");
        // se cambia Layout por defecto a BorderLayout
        this.setLayout(new BorderLayout());
        // se agregan botones no útiles para demostrar las zonas del Layout
        this.add(new JButton("sur"),BorderLayout.SOUTH);
        this.add(new JButton("centro"),BorderLayout.CENTER);
        this.add(new JButton("norte"),BorderLayout.NORTH);
        this.add(new JButton("este"),BorderLayout.EAST);
        this.add(new JButton("oeste"),BorderLayout.WEST);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400,300);
        this.setVisible(true);
    }
}
