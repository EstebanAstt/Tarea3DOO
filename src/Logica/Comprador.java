package Logica;
import java.util.ArrayList;

//en agregarMoneda creo que hay que hacer una exception si se ingresa una moneda inexistente, no creo que se ocupe pero para que este ahi

public class Comprador {
    private String sonido;
    private int vuelto;
    private ArrayList<Deposito<Moneda>> monedero;

    // agregar documentación a ésta clase

    public Comprador(int tipo, Expendedor exp) throws PagoIncorrectoException, PagoInsuficienteException, NoHayProductoException {
        this.vuelto = 0;
        this.monedero = new ArrayList<>();

        for (TipoMoneda t : TipoMoneda.values()) {
            Deposito<Moneda> nuevoDeposito = new Deposito<>();
            for(int i = 0; i <= 5; i++){
                nuevoDeposito.add(t.crearMoneda());

            }
            this.monedero.add(nuevoDeposito);
        }

        Producto producto = exp.getProducto();
        
        // si se pudo comprar, se consume
        if (producto instanceof Bebida bebida) {
            this.sonido = bebida.beber();
        } else if (producto instanceof Dulce dulce){
            this.sonido = dulce.comer();
        } else {
            this.sonido = null;
        }

        // saca el vuelto moneda a moneda hasta que quede vacio
        Moneda monedaVuelto;
        while ((monedaVuelto = exp.getVuelto()) != null) {
            this.vuelto += monedaVuelto.getValor();
        }
    }

    public int cuantoVuelto() {
        return this.vuelto;
    }

    public String queConsumiste() {
        return this.sonido;
    }

    public Moneda retirarMoneda(TipoMoneda tipo_moneda){
        int indice = tipo_moneda.getTipo();
        if (monedero.get(indice).getSize() > 0) {
            return monedero.get(indice).get();
        }
        return null;
    }

    public void agregarMoneda(TipoMoneda tipo_moneda){
        if (tipo_moneda == null) {
            throw new IllegalArgumentException("El tipo de moneda no puede ser nulo.");
        }

        monedero.get(tipo_moneda.getTipo()).add(tipo_moneda.crearMoneda());

    }
}
