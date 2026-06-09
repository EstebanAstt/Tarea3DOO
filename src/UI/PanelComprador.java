package UI;

import Logica.Comprador;
import Logica.Moneda;
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

    private static final TipoMoneda[] DENOMINACIONES = {
            TipoMoneda.MONEDA100,
            TipoMoneda.MONEDA500,
            TipoMoneda.MONEDA1000,
            TipoMoneda.MONEDA1500
    };

    private MonedaSeleccionada monedaSeleccionada = null;
    private int indiceMonedaSeleccionada = -1;

    private final int[] contadores = new int[4];
    private final JLabel[] labelContadores = new JLabel[4];

    private BufferedImage fondo;
    private Comprador comprador = new Comprador();


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
            TipoMoneda aux = TipoMoneda.buscarPorTipo(i);
            final int idx = comprador.getCantidadMoneda(aux);
            final int pos = i;
            int[] f = FILAS_MONEDA[i];

            MonedaSeleccionada monedaSeleccionada = new MonedaSeleccionada(
                    "PressedButtons/MonedaSeleccionada.png",
                    f[2], f[3], pos
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
                try {
                    // Java te obliga a envolver esto porque puede lanzar PagoIncorrectoException
                    comprador.agregarMoneda(aux);

                    // Si la moneda se agrega con éxito, actualizas la interfaz gráfica
                    int nuevaCantidad = comprador.getCantidadMoneda(aux);
                    labelContadores[pos].setText(String.valueOf(nuevaCantidad));

                } catch (Logica.PagoIncorrectoException ex) {
                    System.err.println("Error al procesar la moneda: " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, "No se pudo agregar la moneda: " + ex.getMessage());
                }
            });
            add(botonGenerar);

            JLabel label = new JLabel(String.valueOf(idx), SwingConstants.CENTER);
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

    public TipoMoneda getDenominacionSeleccionada() {
        if (indiceMonedaSeleccionada < 0) return null;
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

    public Moneda ingresarMoneda(){
        TipoMoneda tipoMoneda = getDenominacionSeleccionada();
        if(tipoMoneda != null){
            return comprador.retirarMoneda(tipoMoneda);
        }
        return null;
    }

    public void actualizarContadores() {
        for (int i = 0; i < FILAS_MONEDA.length; i++) {
            // Buscamos el tipo de moneda correspondiente a esta fila
            TipoMoneda aux = TipoMoneda.buscarPorTipo(i);

            // Pedimos al comprador la cantidad real y actualizada de monedas
            int nuevaCantidad = comprador.getCantidadMoneda(aux);

            // Actualizamos el JLabel de la pantalla si el array ya está inicializado
            if (labelContadores[i] != null) {
                labelContadores[i].setText(String.valueOf(nuevaCantidad));
            }
        }
        // Forzamos a Swing a redibujar visualmente el panel por si acaso
        this.repaint();
    }
}
