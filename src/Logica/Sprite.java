package Logica;

/**
 * Clase {@link Sprite} que es subclase de Bebida
 */
public class Sprite extends Bebida {
    public Sprite(int serie) {
        super(serie);
    }

    @Override
    public String beber() {
        return super.beber() + "sprite";
    }
}
