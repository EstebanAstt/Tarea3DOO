package Logica;

/**
 * Clase {@link Moneda1000} que es subclase de Moneda
 */
public class Moneda1000 extends Moneda {
    public Moneda1000(){
        super();
    }

    @Override
    public int getValor(){
        return 1000;
    }

    public String toString(){
        return getValor() + " " + getSerie().hashCode();
    }
}
