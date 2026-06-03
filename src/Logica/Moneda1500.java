package Logica;

/**
 * Clase {@link Moneda1500} que es subclase de Moneda
 */
public class Moneda1500 extends Moneda{
    public Moneda1500(){
        super();
    }

    @Override
    public int getValor(){
        return 1500;
    }

    public String toString(){
        return getValor() + " " + getSerie().hashCode();
    }
}
