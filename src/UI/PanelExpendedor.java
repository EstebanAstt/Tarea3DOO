package UI;
import Logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;

/**
 *<|-|>Cosas avanzadas por ahora:
 * -> Se muestra el expendedor en una ventana llamada Expendedor
 * -> Los botones del Expendedor funcionan y pueden poner un numero en forma de String (podria ser que se llame al
 * producto con un numero del 1 al 9 o bien que sea como un expendedor normal con numeracion mas compleja
 * (101,102,103,201...))
 *<|-|>Cosas que hay que añadir:
 * -> Mas comentarios
 * -> La logica detras del Expendedor
 * -> Traducir del numero ingresado a el producto que se quiera escoger (algo como un enum donde este almacenado donde
 * se ubica cada producto)
 * -> Detalles como el audio al presionar un boton
 * -> como vamos a hacer para que el expendedor reciba monedas, podria ser que se pinte un rectangulo blanco arriba de
 * los botones (esta el espacio) que indique el numero del producto y el dinero ingresado, y ya cuando se presione el
 * boton aceptar se aplique la logica de comprar producto
 * -> que el apartado de insertar moneda sea interactivo
 *
 */
public class PanelExpendedor extends JPanel {

    public static final int TAMANO_BOTON = 20;
    public static final int TAMANO_MONEDA = 30;
    public static final int INICIO_BOTONES_NUMERICOS_X = 328;
    public static final int INICIO_BOTONES_NUMERICOS_Y = 80;
    public static final int TAMANO_BOTON_INGRESAR_MONEDA_X = 20;
    public static final int TAMANO_BOTON_INGRESAR_MONEDA_Y = 38;
    public static final int INICIO_BOTON_INGESAR_MONEDA_X = 363;
    public static final int INICIO_BOTON_INGESAR_MONEDA_Y  = 179;


    private PanelComprador panelComprador;
    public PanelExpendedor(PanelComprador panelComprador) {
        this.panelComprador = panelComprador;
        PanelMaquina panel = new PanelMaquina(this.panelComprador);
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

        public PanelMaquina(PanelComprador panelComprador) {
            this.panelComprador = panelComprador;

            try {
                imagenFondo  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Expendedor.png"));
            } catch (Exception e) {
                System.err.println("No se encontró Expendedor.png: " + e.getMessage());
            }

            /*Dato: ese signo de interrogacion es un if - else pero comprimido para ahorrar lineas de codigo
                     condicion if        "?" Lo que se hace si se cumple la condicion ":" Lo que se hace si no se cumple la condicion

             */
            int w = (imagenFondo != null) ? imagenFondo.getWidth()  : 400;
            int h = (imagenFondo != null) ? imagenFondo.getHeight() : 600;
            setPreferredSize(new Dimension(w, h));


            setLayout(null);

            crearBotones();
        }

        /**
         *
         * @param g the <code>Graphics</code> object to protect se dibuja la imagen de fondo, es decir, del expendedor
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFondo != null) {
                // Dibuja la imagen ocupando todo el panel
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            } else {
                //fondo gris de respaldo
                g.setColor(new Color(0x8A8A8A));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
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

        // Esta moneda se borra después, se debe ocupar una moneda sacada
        // del monedero del comprador para realizar una compra
        // hasta ahora se ocupa una moneda auxiliar
        private Moneda1500 monedaAuxiliar = new Moneda1500();
        private Expendedor expendedorPanel = new Expendedor(2);
        private Producto productoComprado = null;

        void onTeclaPresionada(String tecla) throws PagoIncorrectoException, PagoInsuficienteException, NoHayProductoException {
            switch (tecla) {
                case "Aceptar":
                    System.out.println("Código: " + buffer);

                    // aquí se podría sacar la moneda a partir del panel comprador o compradorLocal
                    // es más seguro ir por seleccionarMoneda en PanelComprador

                    /** A partir del buffer cuando se presiona uno de los botones, se saca el producto
                     * pedido desde su respectivo depósito */
                    try {
                        switch (buffer.toString()){
                            case "1":
                            case "4":
                                expendedorPanel.comprarProducto(monedaAuxiliar, TipoProducto.COCA);
                                productoComprado = expendedorPanel.getProducto(); break;
                            case "2":
                            case "5":
                                expendedorPanel.comprarProducto(monedaAuxiliar, TipoProducto.FANTA);
                                productoComprado = expendedorPanel.getProducto(); break;
                            case "3":
                            case "6":
                                expendedorPanel.comprarProducto(monedaAuxiliar, TipoProducto.SPRITE);
                                productoComprado = expendedorPanel.getProducto(); break;
                            case "7":
                                expendedorPanel.comprarProducto(monedaAuxiliar, TipoProducto.SNICKERS);
                                productoComprado = expendedorPanel.getProducto(); break;
                            case "8":
                                expendedorPanel.comprarProducto(monedaAuxiliar, TipoProducto.SUPER8);
                                productoComprado = expendedorPanel.getProducto(); break;
                            case "9":
                                // Producto nulo opcional, puede servir para consumir y dar una excepción
                                Producto ProductoNulo = null; break;
                            default: break;
                        }
                        JOptionPane.showMessageDialog(this, "Seleccionaste: " + buffer);
                    } catch (PagoIncorrectoException e) {
                        JOptionPane.showMessageDialog(this, "Se ingresó una moneda inválida");
                    } catch (PagoInsuficienteException e) {
                        JOptionPane.showMessageDialog(this, "El valor ingresado es inferior al producto pedido");
                    } catch (NoHayProductoException e) {
                        JOptionPane.showMessageDialog(this, "No hay más productos de este tipo");
                    }
                    buffer.setLength(0);
                    break;
                case "Borrar":
                    if (buffer.length() > 0) buffer.deleteCharAt(buffer.length() - 1);
                    break;
                case "IngresarMoneda":
                    Moneda aux = panelComprador.ingresarMoneda();
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
/*
    class CoinButton extends JButton {

        private Image sprite;
        private float opacidad = 1.0f;
        private boolean usada = false;
        private final Runnable onRecoger;

        CoinButton(String rutaSprite, int size, Runnable onRecoger) {
            this.onRecoger = onRecoger;


            try {
                BufferedImage img = ImageIO.read(new File(rutaSprite));
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
    }*/
}
