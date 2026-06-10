package Logica;
import java.util.function.Supplier;

/**
 * Enum que determina el tipo de moneda, como también crear un tipo de moneda
 */
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

    /**
     * Se crea una moneda sacándola de creador
     * @return Moneda creada
     */
    public Moneda crearMoneda() {
        return this.creador.get();
    }

    /**
     * Busca el tipo de moneda a partir de su número
     * @param id variable int de identificación
     * @return retorna TipoMoneda al tener su número
     */
    public static TipoMoneda buscarPorTipo(int id) {
        for (TipoMoneda m : TipoMoneda.values()) {
            if (m.getTipo() == id) {
                return m;
            }
        }
        return null;
    }
}
