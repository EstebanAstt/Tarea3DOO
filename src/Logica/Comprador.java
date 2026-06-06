package Logica;

public class Comprador {
    private String sonido;
    private int vuelto;

    // agregar documentación a ésta clase

    public Comprador(Moneda m, int tipo, Expendedor exp) throws PagoIncorrectoException, PagoInsuficienteException, NoHayProductoException {
        this.vuelto = 0;
        
        exp.comprarProducto(m, tipo);
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
}
