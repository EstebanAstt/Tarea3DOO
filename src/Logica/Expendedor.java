package Logica;

/**
 * Clase {@link Expendedor} es la encargada de crear los depósitos de cada producto
 * como también guardar las diferentes monedas de vuelto y el producto comprado
 */
public class Expendedor {
    private int numProductos;
    private int precioProductos;

    /** Se crean los depósitos de todos los productos definidos */
    private Deposito<CocaCola> cocacola = new Deposito<>();
    private Deposito<Sprite> sprite = new Deposito<>();
    private Deposito<Fanta> fanta = new Deposito<>();
    private Deposito<Super8> super8 = new Deposito<>();
    private Deposito<Snickers> snickers = new Deposito<>();

    /**
     * El depósito monVu sirve exclusivamente para entregar el vuelto en monedas de 100,
     * o devolverle el dinero al comprador si ocurre una excepción
     */
    private Deposito<Moneda> monVu = new Deposito<>();
    private Deposito<Moneda> monTemporal = new Deposito<>();
    private Deposito<Moneda> monGuardadas = new Deposito<>();

    /**
     * Constructor que se encarga de crear los productos en sus respectivos depósitos
     * @param numProductos que determina el número total de productos
     */
    public Expendedor(int numProductos){
        this.numProductos = numProductos;

        /**
         * @param cont sirve para dar un N° de serie único para cada producto en el for,
         * el cual crea productos según la cantidad definida al inicializar el expendedor
         */
        int cont = 1;
        for(int i = 0; i < this.numProductos; i++){
            cocacola.add(new CocaCola(cont));
            sprite.add(new Sprite(cont+1));
            fanta.add(new Fanta(cont+2));
            super8.add(new Super8(cont+3));
            snickers.add(new Snickers(cont+4));
            cont+=5;
        }
    }

    /**
     * productoComprado guarda el producto comprado en {@link #comprarProducto(Moneda, TipoProducto)}
     * después se retornará en el método {@link #getProducto()}
     */
    private Producto productoComprado;

    /**
     * Se compra un producto y se saca de su respectivo depósito
     * también se guardan las monedas de vuelto en el depósito de monedas
     * @param m Moneda ingresada
     * @param cual TipoProducto ingresado
     */
    public void comprarProducto(Moneda m, TipoProducto cual) throws PagoIncorrectoException, PagoInsuficienteException, NoHayProductoException{
        while (monVu.get() != null);

        if (m == null) {
            /**
             * @throws PagoIncorrectoException se lanza si se intenta pagar con una moneda null
             */
            throw new PagoIncorrectoException();
        }

        if (m.getValor() < precioProductos) {
            /**
             * @throws PagoInsuficienteException se lanza si el dinero no alcanza para comprar el producto solicitado
             */
            monVu.add(m);
            throw new PagoInsuficienteException();
        }

        /**
         * Aquí una aclaración importante y es porque se hace uso de dos switch
         * en principio es innecesario, porque en un switch se define el precio del producto y en el otro
         * se crea el producto, no se hacen ambas cosas juntas porque se tendría que extraer el producto
         * de su depósito para poder verificar si al comprador le alcanza, y en caso de que no le alcanze,
         * como el producto ya se sacó del depósito desaparecería, lo cual no tiene sentido, se podría
         * vaciar un depósito sin haber comprado un solo producto
         */

        switch (cual) {
            case COCA:
                this.precioProductos = TipoProducto.COCA.getValor();
                break;
            case SPRITE:
                this.precioProductos = TipoProducto.SPRITE.getValor();
                break;
            case FANTA:
                this.precioProductos = TipoProducto.FANTA.getValor();
                break;
            case SUPER8:
                this.precioProductos = TipoProducto.SUPER8.getValor();
                break;
            case SNICKERS:
                this.precioProductos = TipoProducto.SNICKERS.getValor();
                break;
            default:
                monVu.add(m);
        }

        /** Se inicializa un producto local nulo */
        Producto p = null;
        switch (cual) {
            case COCA:
                p = cocacola.get();
                break;
            case SPRITE:
                p = sprite.get();
                break;
            case FANTA:
                p = fanta.get();
                break;
            case SUPER8:
                p = super8.get();
                break;
            case SNICKERS:
                p = snickers.get();
                break;
        }

        if (p == null) {
            /**
             * @throws NoHayProductoException se lanza si no queda producto
             */
            monVu.add(m);
            throw new NoHayProductoException();
        }

        /** Se guarda el producto seleccionado por el segundo switch */
        productoComprado = p;

        /**
         * @param diff es el vuelto, el cual no se retorna (porque lo único que se retorna es el producto p)
         * si no que se almacena en un deposito el cual se rellena con monedas de 100 hasta completar el vuelto
         */
        int diff = m.getValor() - precioProductos; //con esto se crea el vuelto y se almacena en monedas de 100
        for(int i = 0; i < diff; i+=100){
            monVu.add(new Moneda100());
        }
    }

    /**
     * Se encarga de recibir una moneda del depósito de monedas
     * @return se retorna las monedas de 100 de una en una
     */
    public Moneda getVuelto() {
        return monVu.get();
    }

    public void agregarMoneda(Moneda moneda){
        if(moneda != null){monTemporal.add(moneda);}
    }

    /**
     * Método encargado de retornar el producto comprado
     * @return se retorna el producto inicializado en {@link #comprarProducto(Moneda, TipoProducto)}
     */
    public Producto getProducto() {
        return productoComprado;
    }
}
