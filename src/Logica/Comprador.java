package Logica;
import java.util.ArrayList;

public class Comprador {
    private String sonido;
    private int vuelto;
    private ArrayList<ArrayList<Moneda>> monedero;

    // agregar documentación a ésta clase

    public Comprador(int tipo, Expendedor exp) throws PagoIncorrectoException, PagoInsuficienteException, NoHayProductoException {
        this.vuelto = 0;
        this.monedero = new ArrayList<>();

        ArrayList<Moneda> monedasDe100  = new ArrayList<>();
        ArrayList<Moneda> monedasDe500  = new ArrayList<>();
        ArrayList<Moneda> monedasDe1000 = new ArrayList<>();
        ArrayList<Moneda> monedasDe1500 = new ArrayList<>();

        for(int i = 0; i <= 5; i++){
            monedasDe100.add(new Moneda100());
            monedasDe500.add(new Moneda500());
            monedasDe1000.add(new Moneda1000());
            monedasDe1500.add(new Moneda1500());

        }

        monedero.add(monedasDe100);
        monedero.add(monedasDe500);
        monedero.add(monedasDe1000);
        monedero.add(monedasDe1500);


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
