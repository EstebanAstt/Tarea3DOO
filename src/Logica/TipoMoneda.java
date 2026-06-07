package Logica;
import java.util.function.Supplier;

public enum TipoMoneda {

    MONEDA100(0, Moneda100::new),
    MONEDA500(1, Moneda500::new),
    MONEDA1000(2, Moneda1000::new),
    MONEDA1500(3, Moneda1500::new);


    private final int tipo;
    private final Supplier<Moneda> creador;

    private TipoMoneda(int tipo, Supplier<Moneda> creador) {
        this.tipo = tipo;
        this.creador = creador;
    }


    public int getTipo() {
        return this.tipo;
    }

    public Moneda crearMoneda() {
        return this.creador.get();
    }
}
