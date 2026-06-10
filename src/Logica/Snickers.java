package Logica;

/**
 * Clase {@link Snickers} que es subclase de Dulce
 */
public class Snickers extends Dulce {
    public Snickers(int serie) {
        super(serie);
    }

    @Override
    public String comer(){
        return super.comer() + "snickers";
    }
}
