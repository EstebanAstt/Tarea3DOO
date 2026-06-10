package UI;

import java.awt.*;
import java.io.File;
import javax.sound.sampled.*;
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

        /** Se agrega música de fondo */
        reproducirFondo("MusicaFondo.wav");
    }

    private static void reproducirFondo(String ruta){
        try {
            File archivo = new File(ruta);

            /** Si el archivo existe, se reproduce como variable Clip y se repite indefinidamente */
            if (archivo.exists()){
                AudioInputStream audioFondo = AudioSystem.getAudioInputStream(archivo);
                Clip clip = AudioSystem.getClip();
                clip.open(audioFondo);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
            else {
                System.out.println("No se encontró el archivo de audio");
            }
        } catch (Exception e) {}
    }
}