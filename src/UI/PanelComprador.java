package UI;

import Logica.TipoMoneda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;

public class PanelComprador extends JPanel {
    public static final int TAMANO_MONEDA = 90;
    public static final int INICIO_MONEDAS_X = 45;
    public static final int INICIO_MONEDAS_Y = 9;
    public static final int MARGEN_MONEDAS = 4;
    public static final int MARGEN_BOTON_AGREGAR = 60;
    public static final int TAMANO_BOTON_AGREGAR = 34;
    public static final int INICIO_BOTONES_AGREGAR_X = 247;
    public static final int INICIO_BOTONES_AGREGAR_Y = 30;

    private static final int[][] FILAS_MONEDA = {
            { INICIO_MONEDAS_X, INICIO_MONEDAS_Y, TAMANO_MONEDA, TAMANO_MONEDA, INICIO_BOTONES_AGREGAR_X, INICIO_BOTONES_AGREGAR_Y, TAMANO_BOTON_AGREGAR, TAMANO_BOTON_AGREGAR },
            { INICIO_MONEDAS_X, INICIO_MONEDAS_Y + (TAMANO_MONEDA + MARGEN_MONEDAS), TAMANO_MONEDA, TAMANO_MONEDA, INICIO_BOTONES_AGREGAR_X, INICIO_BOTONES_AGREGAR_Y + (TAMANO_BOTON_AGREGAR + MARGEN_BOTON_AGREGAR), TAMANO_BOTON_AGREGAR, TAMANO_BOTON_AGREGAR },
            { INICIO_MONEDAS_X, INICIO_MONEDAS_Y + 2 * (TAMANO_MONEDA + MARGEN_MONEDAS), TAMANO_MONEDA, TAMANO_MONEDA, INICIO_BOTONES_AGREGAR_X, INICIO_BOTONES_AGREGAR_Y + 2 * (TAMANO_BOTON_AGREGAR + MARGEN_BOTON_AGREGAR), TAMANO_BOTON_AGREGAR, TAMANO_BOTON_AGREGAR },
            { INICIO_MONEDAS_X, INICIO_MONEDAS_Y + 3 * (TAMANO_MONEDA + MARGEN_MONEDAS), TAMANO_MONEDA, TAMANO_MONEDA, INICIO_BOTONES_AGREGAR_X, INICIO_BOTONES_AGREGAR_Y + 3 * (TAMANO_BOTON_AGREGAR + MARGEN_BOTON_AGREGAR), TAMANO_BOTON_AGREGAR, TAMANO_BOTON_AGREGAR },
    };

    private static final int[] DENOMINACIONES = {
            TipoMoneda.MONEDA100.getTipo(),
            TipoMoneda.MONEDA500.getTipo(),
            TipoMoneda.MONEDA1000.getTipo(),
            TipoMoneda.MONEDA1500.getTipo()
    };

    private MonedaSeleccionada monedaSeleccionada = null;
    private int indiceMonedaSeleccionada = -1;

    private final int[] contadores = new int[4];
    private final JLabel[] labelContadores = new JLabel[4];

    private BufferedImage fondo;

    public PanelComprador() {
        try {
            URL fondoUrl = getClass().getClassLoader().getResource("Sprites/Comprador.png");
            if (fondoUrl != null) {
                fondo = ImageIO.read(fondoUrl);
            } else {
                System.err.println("No se encontró Sprites/Comprador.png");
            }
        } catch (Exception e) {
            System.err.println("Error cargando fondo: " + e.getMessage());
        }

        int w = fondo != null ? fondo.getWidth() : 400;
        int h = fondo != null ? fondo.getHeight() : 600;
        setPreferredSize(new Dimension(w, h));
        setLayout(null);

        crearFilas();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(0x8A8A8A));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void crearFilas() {
        for (int i = 0; i < FILAS_MONEDA.length; i++) {
            final int idx = i;
            int[] f = FILAS_MONEDA[i];

            MonedaSeleccionada monedaSeleccionada = new MonedaSeleccionada(
                    "PressedButtons/MonedaSeleccionada.png",
                    f[2], f[3], idx
            );
            monedaSeleccionada.setBounds(f[0], f[1], f[2], f[3]);
            monedaSeleccionada.addActionListener(e ->
                    seleccionarMoneda(monedaSeleccionada)
            );
            add(monedaSeleccionada);

            SpriteButton botonGenerar = new SpriteButton(
                    "PressedButtons/CursorSobreBotonGenerar.png",
                    f[6], f[7]
            );
            botonGenerar.setBounds(f[4], f[5], f[6], f[7]);
            //-> aca hay que poner la logica de generar una moneda para el comprador. Se puso un contador de enteros pero la idea es que realmente se genere una moneda)
            botonGenerar.addActionListener(e -> {
                contadores[idx]++;
                labelContadores[idx].setText(String.valueOf(contadores[idx]));
            });
            add(botonGenerar);

            JLabel label = new JLabel("0", SwingConstants.CENTER);
            label.setFont(new Font("Monospaced", Font.BOLD, 13));
            label.setForeground(new Color(0x333333));

            int slotX = f[0] + f[2] + 5;
            int slotW = f[4] - slotX - 5;
            label.setBounds(slotX, f[5], slotW, f[7]);
            add(label);

            labelContadores[i] = label;
        }
    }

    private void seleccionarMoneda(MonedaSeleccionada nueva) {
        if (monedaSeleccionada != null && monedaSeleccionada != nueva) {
            monedaSeleccionada.setSeleccionada(false);
        }
        monedaSeleccionada = nueva;
        monedaSeleccionada.setSeleccionada(true);
        indiceMonedaSeleccionada = nueva.getIndice();

        System.out.println("Moneda seleccionada: $" + getDenominacionSeleccionada());
    }

    public int getDenominacionSeleccionada() {
        if (indiceMonedaSeleccionada < 0) return -1;
        return DENOMINACIONES[indiceMonedaSeleccionada];
    }

    public int getContador(int fila) {
        return contadores[fila];
    }

    static class MonedaSeleccionada extends JButton {
        private final ImageIcon spriteSeleccionado;
        private boolean seleccionada = false;
        private final int indice;

        MonedaSeleccionada(String rutaSel, int w, int h, int indice) {
            this.indice = indice;
            spriteSeleccionado = cargarSprite(rutaSel, w, h);

            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setText("");
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        int getIndice() {
            return indice;
        }

        void setSeleccionada(boolean sel) {
            this.seleccionada = sel;
            repaint();
        }

        private ImageIcon cargarSprite(String ruta, int w, int h) {
            try {
                URL url = getClass().getClassLoader().getResource(ruta);
                if (url == null) {
                    System.err.println("Sprite no encontrado: " + ruta);
                    return null;
                }
                BufferedImage img = ImageIO.read(url);
                Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } catch (Exception e) {
                System.err.println("Error cargando sprite: " + ruta);
                return null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (seleccionada && spriteSeleccionado != null) {
                g.drawImage(spriteSeleccionado.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    static class SpriteButton extends JButton {
        private final ImageIcon spritePresionado;
        private boolean presionado = false;

        SpriteButton(String rutaPressed, int w, int h) {
            spritePresionado = cargarSprite(rutaPressed, w, h);

            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setText("");
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    presionado = true;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    presionado = false;
                    repaint();
                }
            });
        }

        private ImageIcon cargarSprite(String ruta, int w, int h) {
            try {
                URL url = getClass().getClassLoader().getResource(ruta);
                if (url == null) {
                    System.err.println("Sprite no encontrado: " + ruta);
                    return null;
                }
                BufferedImage img = ImageIO.read(url);
                Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } catch (Exception e) {
                System.err.println("Error cargando sprite: " + ruta);
                return null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (presionado && spritePresionado != null) {
                g.drawImage(spritePresionado.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
