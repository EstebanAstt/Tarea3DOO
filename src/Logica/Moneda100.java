package Logica;

/**
 * Clase {@link Moneda100} que es subclase de Moneda
 */
public class Moneda100 extends Moneda {
    public Moneda100(){
        super();
    }

    @Override
    public int getValor(){
        return 100;
    }

    public String toString(){
        return getValor() + " " + getSerie().hashCode();
    }
}
