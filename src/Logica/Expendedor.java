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
    private Deposito<CocaCola> cocacola1 = new Deposito<>();
    private Deposito<Sprite> sprite = new Deposito<>();
    private Deposito<Sprite> sprite1 = new Deposito<>();
    private Deposito<Fanta> fanta = new Deposito<>();
    private Deposito<Fanta> fanta1 = new Deposito<>();
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
            cocacola1.add(new CocaCola(cont+1));
            sprite.add(new Sprite(cont+2));
            sprite1.add(new Sprite(cont+3));
            fanta.add(new Fanta(cont+4));
            fanta1.add(new Fanta(cont+5));
            super8.add(new Super8(cont+6));
            snickers.add(new Snickers(cont+7));
            cont+=8;
        }
    }

    /**
     * productoComprado guarda el producto comprado en {@link #comprarProducto(TipoProducto)}
     * después se retornará en el método {@link #getProducto()}
     */
    private Producto productoComprado;

    /**
     * Se compra un producto y se saca de su respectivo depósito
     * también se guardan las monedas de vuelto en el depósito de monedas
     * @param cual TipoProducto ingresado
     */
    public void comprarProducto(TipoProducto cual) throws PagoIncorrectoException, PagoInsuficienteException, NoHayProductoException{
        while (monVu.get() != null);

        if (getCantidadIngresada() == 0) {
            /**
             * @throws PagoIncorrectoException se lanza si se intenta pagar con una moneda null
             */
            throw new PagoIncorrectoException();
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
            case COCA1:
                this.precioProductos = TipoProducto.COCA1.getValor();
                break;
            case SPRITE:
                this.precioProductos = TipoProducto.SPRITE.getValor();
                break;
            case SPRITE1:
                this.precioProductos = TipoProducto.SPRITE1.getValor();
                break;
            case FANTA:
                this.precioProductos = TipoProducto.FANTA.getValor();
                break;
            case FANTA1:
                this.precioProductos = TipoProducto.FANTA1.getValor();
                break;
            case SUPER8:
                this.precioProductos = TipoProducto.SUPER8.getValor();
                break;
            case SNICKERS:
                this.precioProductos = TipoProducto.SNICKERS.getValor();
                break;
            default:
                vaciarMonTemporal(false);
        }

        if (getCantidadIngresada() < precioProductos) {
            /**
             * @throws PagoInsuficienteException se lanza si el dinero no alcanza para comprar el producto solicitado
             */
            vaciarMonTemporal(false);
            throw new PagoInsuficienteException();
        }

        /** Se inicializa un producto local nulo */
        Producto p = null;
        switch (cual) {
            case COCA:
                p = cocacola.get();
                break;
            case COCA1:
                p = cocacola1.get();
                break;
            case SPRITE:
                p = sprite.get();
                break;
            case SPRITE1:
                p = sprite1.get();
                break;
            case FANTA:
                p = fanta.get();
                break;
            case FANTA1:
                p = fanta1.get();
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
            vaciarMonTemporal(false);
            throw new NoHayProductoException();
        }

        /** Se guarda el producto seleccionado por el segundo switch */
        productoComprado = p;

        /**
         * @param diff es el vuelto, el cual no se retorna (porque lo único que se retorna es el producto p)
         * si no que se almacena en un deposito el cual se rellena con monedas de 100 hasta completar el vuelto
         */
        int diff = getCantidadIngresada() - precioProductos; //con esto se crea el vuelto y se almacena en monedas de 100
        for(int i = 0; i < diff; i+=100){
            monVu.add(new Moneda100());
        }
        vaciarMonTemporal(true);
    }

    /**
     * Se encarga de recibir una moneda del depósito de monedas
     * @return se retorna las monedas de 100 de una en una
     */
    public Moneda getVuelto() {
        return monVu.get();
    }

    /**
     * Agrega una moneda temporal al depósito de monedas temporales
     * @param moneda variable Moneda ingresada
     */
    public void agregarMoneda(Moneda moneda){
        if(moneda != null){monTemporal.add(moneda);}
    }

    /**
     * Método encargado de retornar el producto comprado
     * @return se retorna el producto inicializado en {@link #comprarProducto(TipoProducto)}
     */
    public Producto getProducto() {
        Producto aux = this.productoComprado;
        this.productoComprado = null;
        return aux;
    }

    /**
     * Se toma una moneda de monTemporal y se reparte entre monGuardadas o monVu
     * dependiendo si la compra fue exitosa o no
     * @param verifica_compra variable booleana que verifica si se realizó la compra
     */
    private void vaciarMonTemporal(boolean verifica_compra){
        while(monTemporal.getSize() > 0){
            if(verifica_compra) {
                Moneda moneda = monTemporal.get();
                monGuardadas.add(moneda);
            } else {
                Moneda moneda = monTemporal.get();
                monVu.add(moneda);
            }
        }
    }

    /**
     * Calcula suma el valor de las monedas de monTemporal y retorna ese mismo valor
     * @return suma total de las monedas
     */
    private int getCantidadIngresada(){
        int suma = 0;
        for(int i = 0 ; i < monTemporal.getSize() ; i++){
            Moneda m = monTemporal.peek(i);
            suma += m.getValor();
        }
        return suma;
    }

    public int getCantidadEnSlot(TipoProducto tipoProducto){
        if (tipoProducto == null) {
            return 0;
        }

        switch (tipoProducto) {
            case COCA:
                return cocacola.getSize();
            case COCA1:
                return cocacola1.getSize();
            case SPRITE:
                return sprite.getSize();
            case SPRITE1:
                return sprite1.getSize();
            case FANTA:
                return fanta.getSize();
            case FANTA1:
                return fanta1.getSize();
            case SUPER8:
                return super8.getSize();
            case SNICKERS:
                return snickers.getSize();
            default:
                return 0;
        }
    }
}
