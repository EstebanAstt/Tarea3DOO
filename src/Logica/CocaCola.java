package Logica;

/**
 * Clase {@link CocaCola} que es subclase de Bebida
 */
public class CocaCola extends Bebida {
    public CocaCola(int serie) {
        super(serie);
    }

    @Override
    public String beber() {
        return "cocacola";
    }
}
