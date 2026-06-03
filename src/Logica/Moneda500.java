package Logica;

/**
 * Clase {@link Moneda500} que es subclase de Moneda
 */
public class Moneda500 extends Moneda {
    public Moneda500(){
        super();
    }

    @Override
    public int getValor(){
        return 500;
    }

    public String toString(){
        return getValor() + " " + getSerie().hashCode();
    }
}
