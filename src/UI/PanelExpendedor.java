package UI;
import Logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;


public class PanelExpendedor extends JPanel {
    public static final int CANTIDAD_PRODUCTOS = 8;
    public static final int TAMANO_MONEDA = 30;


    public static final int TAMANO_LATA_X = 33;
    public static final int TAMANO_LATA_Y = 65;


    public static final int TAMANO_SNICKERS_X = 49;
    public static final int TAMANO_SNICKERS_Y = 15;
    public static final int POSICION_SNICKERS_X = 133;
    public static final int POSICION_SNICKERS_Y = 330;
    public static final int TAMANO_SUPER8 = 50;
    public static final int POSICION_SUPER8_X = 56;
    public static final int POSICION_SUPER8_Y = 300;


    public static final int INICIO_SPRITES_PRODUCTOS_X = 63;
    public static final int INICIO_SPRITES_PRODUCTOS_Y = 53;
    public static final int MARGEN_SPRITES_PRODUCTOS_X = 77;
    public static final int MARGEN_SPRITES_PRODUCTOS_Y = 117;

    public static final int OFFSET_X = 3;
    public static final int OFFSET_Y = -6;

    public static final int BANDEJA_X = 26;
    public static final int BANDEJA_Y = 492;
    public static final int BANDEJA_TAMANO_X = 272;
    public static final int BANDEJA_TAMANO_Y = 87;

    public static final int BANDEJA_VUELTO_X = 326;
    public static final int BANDEJA_VUELTO_Y = 226;
    public static final int BANDEJA_VUELTO_TAMANO_X = 64;
    public static final int BANDEJA_VUELTO_TAMANO_Y = 180;

    public static final int[][] SPRITES_PRODUCTOS = {
            {INICIO_SPRITES_PRODUCTOS_X                                 , INICIO_SPRITES_PRODUCTOS_Y                             , TAMANO_LATA_X    , TAMANO_LATA_Y    },
            {INICIO_SPRITES_PRODUCTOS_X + MARGEN_SPRITES_PRODUCTOS_X    , INICIO_SPRITES_PRODUCTOS_Y                             , TAMANO_LATA_X    , TAMANO_LATA_Y    },
            {INICIO_SPRITES_PRODUCTOS_X + 2*(MARGEN_SPRITES_PRODUCTOS_X), INICIO_SPRITES_PRODUCTOS_Y                             , TAMANO_LATA_X    , TAMANO_LATA_Y    },
            {INICIO_SPRITES_PRODUCTOS_X                                 , INICIO_SPRITES_PRODUCTOS_Y + MARGEN_SPRITES_PRODUCTOS_Y, TAMANO_LATA_X    , TAMANO_LATA_Y    },
            {INICIO_SPRITES_PRODUCTOS_X + MARGEN_SPRITES_PRODUCTOS_X    , INICIO_SPRITES_PRODUCTOS_Y + MARGEN_SPRITES_PRODUCTOS_Y, TAMANO_LATA_X    , TAMANO_LATA_Y    },
            {INICIO_SPRITES_PRODUCTOS_X + 2*(MARGEN_SPRITES_PRODUCTOS_X), INICIO_SPRITES_PRODUCTOS_Y + MARGEN_SPRITES_PRODUCTOS_Y, TAMANO_LATA_X    , TAMANO_LATA_Y    },
            {POSICION_SUPER8_X                                          , POSICION_SUPER8_Y                                      , TAMANO_SUPER8    , TAMANO_SUPER8    },
            {POSICION_SNICKERS_X                                        , POSICION_SNICKERS_Y                                    , TAMANO_SNICKERS_X, TAMANO_SNICKERS_Y}

    };

    public static final int TAMANO_BOTON = 20;
    public static final int INICIO_BOTONES_NUMERICOS_X = 328;
    public static final int INICIO_BOTONES_NUMERICOS_Y = 80;

    public static final int TAMANO_BOTON_INGRESAR_MONEDA_X = 20;
    public static final int TAMANO_BOTON_INGRESAR_MONEDA_Y = 38;
    public static final int INICIO_BOTON_INGESAR_MONEDA_X = 363;
    public static final int INICIO_BOTON_INGESAR_MONEDA_Y  = 179;


    private PanelComprador panelComprador;
    private Expendedor expendedor = new Expendedor(5);

    public PanelExpendedor(PanelComprador panelComprador) {
        this.panelComprador = panelComprador;
        PanelMaquina panel = new PanelMaquina(this.panelComprador,this.expendedor);
        add(panel);

    }

    /**
     * imagenFondo es el expendedor base
     * buffer es el StringBuilder que apila cada label del teclado numerico para poder escoger el producto
     */
    static class PanelMaquina extends JPanel {

        private BufferedImage imagenFondo;
        private final StringBuilder buffer = new StringBuilder();

        private final PanelComprador panelComprador;
        private final Expendedor expendedor;
        private final Comprador comprador;


        private JButton botonBandeja = null;
        private JPanel bandejaVuelto;

        private BufferedImage cocaColaSprite;
        private BufferedImage fantaSprite;
        private BufferedImage spriteSprite;
        private BufferedImage snickersSprite;
        private BufferedImage super8Sprite;

        public PanelMaquina(PanelComprador panelComprador, Expendedor expendedor) {
            this.panelComprador = panelComprador;
            this.expendedor = expendedor;
            this.comprador = panelComprador.getComprador();



            bandejaVuelto = new JPanel();
            bandejaVuelto.setBounds(BANDEJA_VUELTO_X, BANDEJA_VUELTO_Y, BANDEJA_VUELTO_TAMANO_X, BANDEJA_VUELTO_TAMANO_Y);
            bandejaVuelto.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Alinea al centro y da 5px de espacio
            bandejaVuelto.setOpaque(false);
            add(bandejaVuelto);

            try {
                imagenFondo  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Expendedor.png"));
                cocaColaSprite = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Cocacola.png"));
                fantaSprite = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Fanta.png"));
                spriteSprite = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Sprite.png"));
                snickersSprite = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Snickers.png"));
                super8Sprite = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Super8.png"));
            } catch (Exception e) {
                System.err.println("No se encontró el png: " + e.getMessage());
            }


            int w = (imagenFondo != null) ? imagenFondo.getWidth()  : 400;
            int h = (imagenFondo != null) ? imagenFondo.getHeight() : 600;
            setPreferredSize(new Dimension(w, h));


            setLayout(null);

            crearBotones();
        }
        private BufferedImage obtenerSprite(int slot) {
            switch (slot) {
                case 1:
                case 4:
                    return cocaColaSprite;

                case 2:
                case 5:
                    return fantaSprite;

                case 3:
                case 6:
                    return spriteSprite;

                case 7:
                    return super8Sprite;

                case 8:
                    return snickersSprite;

                default:
                    return null;
            }
        }
        private void dibujarProductos(Graphics g) {

            for (int slot = 1; slot <= CANTIDAD_PRODUCTOS; slot++) {

                int cantidad = expendedor.getCantidadEnSlot(TipoProducto.buscarPorTipo(slot));

                BufferedImage sprite = obtenerSprite(slot);

                if (sprite == null) continue;

                int baseX = SPRITES_PRODUCTOS[slot-1][0];
                int baseY = SPRITES_PRODUCTOS[slot-1][1];

                for (int i = cantidad - 1; i >= 0; i--) {
                    int x = baseX + OFFSET_X * i;
                    int y = baseY + OFFSET_Y * i;

                    g.drawImage(sprite, x, y, this);
                }
            }
        }
        /**
         *
         * @param g the <code>Graphics</code> object to protect se dibuja la imagen de fondo, es decir, del expendedor
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagenFondo != null ) {
                // Dibuja la imagen ocupando todo el panel
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            } else {
                //fondo gris de respaldo
                g.setColor(new Color(0x8A8A8A));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            dibujarProductos(g);
        }

        /**
         * se crean los botones para poder elegir el producto, estos son inicialmente invisibles y ya luego cuando se
         * les hace click se añade el sprite de boton presionado
         */
        private void crearBotones() {

            Object[][] teclas = {
                    //  label   x    y    w    h
                    {   "1", INICIO_BOTONES_NUMERICOS_X, INICIO_BOTONES_NUMERICOS_Y , TAMANO_BOTON, TAMANO_BOTON},
                    {   "2", INICIO_BOTONES_NUMERICOS_X + TAMANO_BOTON, INICIO_BOTONES_NUMERICOS_Y, TAMANO_BOTON, TAMANO_BOTON},
                    {   "3", INICIO_BOTONES_NUMERICOS_X + 2 * TAMANO_BOTON, INICIO_BOTONES_NUMERICOS_Y, TAMANO_BOTON, TAMANO_BOTON},
                    {   "4", INICIO_BOTONES_NUMERICOS_X,  INICIO_BOTONES_NUMERICOS_Y + TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON},
                    {   "5", INICIO_BOTONES_NUMERICOS_X + TAMANO_BOTON, INICIO_BOTONES_NUMERICOS_Y + TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON},
                    {   "6", INICIO_BOTONES_NUMERICOS_X + 2 * TAMANO_BOTON, INICIO_BOTONES_NUMERICOS_Y + TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON},
                    {   "7", INICIO_BOTONES_NUMERICOS_X, INICIO_BOTONES_NUMERICOS_Y + 2 * TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON },
                    {   "8", INICIO_BOTONES_NUMERICOS_X + TAMANO_BOTON, INICIO_BOTONES_NUMERICOS_Y + 2 * TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON },
                    {   "9", INICIO_BOTONES_NUMERICOS_X + 2 * TAMANO_BOTON, INICIO_BOTONES_NUMERICOS_Y + 2 * TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON },
                    {   "0", INICIO_BOTONES_NUMERICOS_X, INICIO_BOTONES_NUMERICOS_Y + 3 * TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON },
                    {   "Borrar", INICIO_BOTONES_NUMERICOS_X + TAMANO_BOTON, INICIO_BOTONES_NUMERICOS_Y + 3 * TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON },
                    {  "Aceptar", INICIO_BOTONES_NUMERICOS_X + 2 * TAMANO_BOTON, INICIO_BOTONES_NUMERICOS_Y + 3 * TAMANO_BOTON, TAMANO_BOTON, TAMANO_BOTON },
                    {  "IngresarMoneda", INICIO_BOTON_INGESAR_MONEDA_X, INICIO_BOTON_INGESAR_MONEDA_Y, TAMANO_BOTON_INGRESAR_MONEDA_X, TAMANO_BOTON_INGRESAR_MONEDA_Y}
            };

            for (Object[] t : teclas) {
                String label = (String) t[0];
                int x = (int) t[1], y = (int) t[2];
                int w = (int) t[3], h = (int) t[4];

                ImageIcon iconPresionado = cargarSprite("PressedButtons/CursorSobreBoton" + label + ".png", w, h);

                BotonInvisible boton = new BotonInvisible(label, iconPresionado);
                boton.setBounds(x, y, w, h);
                boton.addActionListener(e -> {

                    // Por onTeclaPresionada, se crea excepción para no dar error
                    // Opcional, luego borrar o cambiar por otra cosa
                    try {
                        onTeclaPresionada(label);
                    } catch (Exception ex) {
                        System.out.print("texto de prueba");
                    }
                });
                add(boton);
            }
        }

        /**
         *
         * @param tecla
         */

        private Producto productoComprado = null;
        private Moneda aux = null;
        /**
         * Método que se encarga de dar una función a cada botón del expendedor
         * @param tecla Tecla presionada por el usuario
         * @throws PagoIncorrectoException
         * @throws PagoInsuficienteException
         * @throws NoHayProductoException
         */
        void onTeclaPresionada(String tecla) throws PagoIncorrectoException, PagoInsuficienteException, NoHayProductoException {
            /** Moneda que cambia de valor cuando se ingresa al expendedor*/

            // arreglar problema de "Moneda invalida al presionar Aceptar después de IngresarMoneda"
            switch (tecla) {
                case "Aceptar":
                    System.out.println("Código: " + buffer);


                    if (buffer.length() == 0) {
                        JOptionPane.showMessageDialog(this, "Por favor ingrese un código numérico.");
                        break;
                    }


                    try {
                        if (botonBandeja != null) {
                            JOptionPane.showMessageDialog(this,
                                    "¡Por favor, retire el producto anterior de la bandeja antes de realizar otra compra!",
                                    "Bandeja Ocupada",
                                    JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        int productoSeleccionadoNum = Integer.parseInt(buffer.toString());
                        TipoProducto tipoProducto = TipoProducto.buscarPorTipo(productoSeleccionadoNum);

                        expendedor.comprarProducto(tipoProducto);
                        productoComprado = expendedor.getProducto();

                        if (productoComprado != null) {


                            //Obtener la imagen correspondiente del producto comprado
                            BufferedImage spriteProducto = obtenerSprite(productoSeleccionadoNum);

                            //Crear un botón común y corriente configurado con la imagen
                            botonBandeja = new JButton();
                            botonBandeja.setBounds(BANDEJA_X, BANDEJA_Y, BANDEJA_TAMANO_X, BANDEJA_TAMANO_Y);

                            // Configuración para que el botón sea invisible y solo muestre el Sprite
                            botonBandeja.setContentAreaFilled(false);
                            botonBandeja.setBorderPainted(false);
                            botonBandeja.setFocusPainted(false);
                            botonBandeja.setOpaque(false);
                            botonBandeja.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                            // Ajustamos la imagen al tamaño estándar de la bandeja
                            Image imagenEscalada = spriteProducto.getScaledInstance(BANDEJA_TAMANO_X, BANDEJA_TAMANO_Y, Image.SCALE_SMOOTH);
                            botonBandeja.setIcon(new ImageIcon(spriteProducto));


                            botonBandeja.addActionListener(ev -> {
                                JOptionPane.showMessageDialog(this, "Has retirado tu producto exitosamente.");

                                panelComprador.recibirProductoEnSlot(productoComprado, spriteProducto);
                                remove(botonBandeja); // Se remueve a sí mismo del contenedor principal
                                botonBandeja = null;  // Limpiamos la referencia
                                revalidate();         // Avisa a Swing del cambio estructural
                                repaint();            // Redibuja la pantalla de inmediato
                            });

                            // Añadir el botón directamente al panel de la máquina
                            add(botonBandeja);
                            revalidate();
                            repaint();
                        }

                        JOptionPane.showMessageDialog(this, "Seleccionaste: " + tipoProducto);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Código numérico inválido.");
                    } catch (PagoIncorrectoException e) {
                        JOptionPane.showMessageDialog(this, "Se ingresó una moneda inválida");
                    } catch (PagoInsuficienteException e) {
                        JOptionPane.showMessageDialog(this, "El valor ingresado es inferior al producto pedido");
                    } catch (NoHayProductoException e) {
                        JOptionPane.showMessageDialog(this, "No hay más productos de este tipo");
                    }
                    actualizarBandejaVuelto();
                    // Limpiamos el buffer tras intentar la operación
                    buffer.setLength(0);
                    break;

                case "Borrar":
                    if (buffer.length() > 0) buffer.deleteCharAt(buffer.length() - 1);
                    break;
                case "IngresarMoneda":
                    aux = panelComprador.ingresarMoneda();
                    expendedor.agregarMoneda(aux);
                    JOptionPane.showMessageDialog(this, "Ingresaste una: " + aux.getTipoMoneda());
                    panelComprador.actualizarContadores();
                    break;
                default:
                    if (buffer.length() < 1) buffer.append(tecla);
            }
            System.out.println("Buffer actual: " + buffer);

            /** Cuando existe display, se actualiza el estado del buffer */
            repaint();
        }

        /**
         *
         * @param ruta la ubicacion del sprite que esta en resources
         * @param w ancho del sprite
         * @param h altura del sprite
         * @return
         */
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

        public void actualizarBandejaVuelto() {
            Moneda m;

            // Hacemos el while extrayendo monedas de una en una hasta que getVuelto() retorne null
            while ((m = expendedor.getVuelto()) != null) {
                final Moneda monedaExtraida = m; // Variable final requerida para la expresión lambda

                // 1. Identificamos el tipo de moneda para elegir su Sprite correspondiente
                String rutaSprite = "Sprites/Moneda100.png"; // Ruta por defecto

                if (monedaExtraida instanceof Logica.Moneda500) {
                    rutaSprite = "Sprites/Moneda500.png";
                } else if (monedaExtraida instanceof Logica.Moneda1000) {
                    rutaSprite = "Sprites/Moneda1000.png";
                } else if (monedaExtraida instanceof Logica.Moneda1500) {
                    rutaSprite = "Sprites/Moneda1500.png";
                }

                // 2. Creamos tu CoinButton pasando la ruta, el tamaño (ej: 30x30) y la acción al recoger
                CoinButton botonMoneda = new CoinButton(rutaSprite, TAMANO_MONEDA, () -> {
                    try {

                        this.comprador.agregarMoneda(monedaExtraida.getTipoMoneda());

                        panelComprador.actualizarContadores();

                    } catch (Logica.PagoIncorrectoException e) {
                        System.err.println("Error al recoger la moneda: " + e.getMessage());
                    }
                });

                // 3. Hacemos visible el botón y lo agregamos a nuestra bandeja con FlowLayout
                botonMoneda.setPreferredSize(new Dimension(TAMANO_MONEDA, TAMANO_MONEDA)); // Le damos el tamaño estándar para el Layout
                botonMoneda.setVisible(true);

                bandejaVuelto.add(botonMoneda);
            }

            // 4. Refrescamos la bandeja para que Swing dibuje todas las monedas que cayeron juntas
            bandejaVuelto.revalidate();
            bandejaVuelto.repaint();
        }
    }

    /**
     *
     */
    static class BotonInvisible extends JButton {

        private final ImageIcon iconPresionado;
        private boolean presionado = false;

        BotonInvisible(String label, ImageIcon iconPresionado) {
            super(label);
            this.iconPresionado = iconPresionado;

            //seteamos todo para que el boton real sea invisible
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setText("");

            // Cursor de manita para indicar que es clickeable
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Aca se puede añadir el audio de presionar un boton
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e)  { presionado = true;  repaint(); }
                @Override
                public void mouseReleased(MouseEvent e) { presionado = false; repaint(); }
            });
        }

        /**
         *
         * @param g the <code>Graphics</code> object to protect se dibujan los botones presionados en caso de que lo
         *          esten
         */
        @Override
        protected void paintComponent(Graphics g) {
            if (presionado && iconPresionado != null) {
                // Dibuja el sprite del estado presionado
                g.drawImage(iconPresionado.getImage(), 0, 0, getWidth(), getHeight(), this);
            }

        }
    }
    static class CoinButton extends JButton {

        private Image sprite;
        private float opacidad = 1.0f;
        private boolean usada = false;
        private final Runnable onRecoger;

        CoinButton(String rutaSprite, int size, Runnable onRecoger) {
            this.onRecoger = onRecoger;


            try {
                BufferedImage img = ImageIO.read(getClass().getClassLoader().getResource(rutaSprite));
                sprite = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            } catch (Exception e) {
                System.err.println("No se encontró el sprite: " + rutaSprite);
            }

            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setText("");
            setVisible(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addActionListener(e -> recoger());
        }

        private void recoger() {
            if (usada) return;

            usada = true;
            onRecoger.run();
            animarDesvanecimiento();
        }

        //Una animacion para bajar la opacidad y se vea mas limpio
        private void animarDesvanecimiento() {
            Timer fade = new Timer(30, null);
            fade.addActionListener(e -> {
                opacidad -= 0.12f;
                if (opacidad <= 0) {
                    opacidad = 0;
                    fade.stop();
                    Container padre = getParent();
                    if (padre != null) {
                        padre.remove(this);
                        padre.repaint();
                    }
                } else {
                    repaint();
                }
            });
            fade.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (opacidad <= 0 || sprite == null) return;
            Graphics2D g2 = (Graphics2D) g.create();
            // Aplica opacidad para el fade al desaparecer
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidad));
            g2.drawImage(sprite, 0, 0, getWidth(), getHeight(), this);
            g2.dispose();
        }


    }


}
