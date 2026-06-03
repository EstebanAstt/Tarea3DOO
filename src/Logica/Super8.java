package Logica;

/**
 * Clase {@link Super8} que es subclase de Dulce
 */
public class Super8 extends Dulce{
    public Super8 (int serie) {
        super(serie);
    }

    @Override
    public String comer(){
        return "super8";
    }
}
