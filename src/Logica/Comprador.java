package Logica;
import java.util.ArrayList;

/**
 * Clase {@link Comprador} la cual es encargada de consumir un producto
 * entregando el valor de vuelto total y disponer un monedero para guardar monedas de distinto valor
 */
public class Comprador {
    private String sonido;
    private int vuelto;
    private ArrayList<Deposito<Moneda>> monedero;

    /**
     * Constructor que determina el producto consumido y el dinero de vuelto total
     * además también se inicializa un monedero para guardar distintos tipos de monedas
     */
    public Comprador() {
        this.monedero = new ArrayList<>();

        for (TipoMoneda t : TipoMoneda.values()) {
            Deposito<Moneda> nuevoDeposito = new Deposito<>();
            for(int i = 0; i <= 5; i++){
                nuevoDeposito.add(t.crearMoneda());

            }
            this.monedero.add(nuevoDeposito);
        }


        


    }

    /**
     * Método que retorna el vuelto calculado en {@link #Comprador()}
     * @return el vuelto total
     */
    public int cuantoVuelto() {
        return this.vuelto;
    }

    /**
     * Método que retorna el producto consumido en {@link #Comprador()}
     * @return producto consumido como variable String
     */
    public String queConsumiste(Producto producto) {
        /** Si pudo realizar una compra, se consume el producto */
        if (producto instanceof Bebida bebida) {
            this.sonido = bebida.beber();
        } else if (producto instanceof Dulce dulce){
            this.sonido = dulce.comer();
        } else {
            this.sonido = null;
        }
        return null;
    }

    /**
     * Método que se encarga de retirar una moneda del monedero
     * @param tipo_moneda TipoMoneda que se quiere retirar
     * @return se retorna moneda retirada
     */
    public Moneda retirarMoneda(TipoMoneda tipo_moneda){
        int indice = tipo_moneda.getTipo();
        if (monedero.get(indice).getSize() > 0) {
            return monedero.get(indice).get();
        }
        return null;
    }

    public int getCantidadMoneda(TipoMoneda tipo_moneda){
        int indice = tipo_moneda.getTipo();
        return monedero.get(indice).getSize();
    }

    /**
     * Método encargado de agregar un tipo de moneda seleccionada al monedero
     * @param tipo_moneda la cual corresponde a una variable enum TipoMoneda
     * @throws PagoIncorrectoException se lanza cuando TipoMoneda es nulo
     */
    public void agregarMoneda(TipoMoneda tipo_moneda) throws PagoIncorrectoException{
        if (tipo_moneda == null) {
            throw new PagoIncorrectoException();
        }

        monedero.get(tipo_moneda.getTipo()).add(tipo_moneda.crearMoneda());
    }
}
