package Logica;

/**
 * Clase {@link Fanta} que es subclase de Bebida
 */
public class Fanta extends Bebida {
    public Fanta(int serie) {
        super(serie);
    }

    @Override
    public String beber() {
        return super.beber() + "fanta";
    }
}
