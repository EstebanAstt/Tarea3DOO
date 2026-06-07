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

        Deposito<Moneda> monedasDe100  = new Deposito<>();
        Deposito<Moneda> monedasDe500  = new Deposito<>();
        Deposito<Moneda> monedasDe1000 = new Deposito<>();
        Deposito<Moneda> monedasDe1500 = new Deposito<>();

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

    public Moneda retirarMoneda(TipoMoneda tipo_moneda){
        int indice = tipo_moneda.getTipo();
        if (monedero.get(indice).getSize() > 0) {
            return monedero.get(indice).get();
        }
        return null;
    }

    public void agregarMoneda(TipoMoneda tipo_moneda){
        int indice = tipo_moneda.getTipo();
        switch (tipo_moneda){
            case MONEDA100:
                monedero.get(indice).add(new Moneda100());
                break;
            case MONEDA500:
                monedero.get(indice).add(new Moneda500());
                break;
            case MONEDA1000:
                monedero.get(indice).add(new Moneda1000());
                break;
            case MONEDA1500:
                monedero.get(indice).add(new Moneda1500());
                break;
            default:
                System.out.println("Moneda Inexistente");
        }

    }


}
