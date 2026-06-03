package Logica;

/**
 * La clase {@link Moneda} que es clase padre de Moneda1500, Moneda1000, Moneda500 y Moneda100
 * Las clases de Moneda tienen un número de serie que corresponde a su hashCode
 * Además cada subclase de Moneda tiene un valor distinto al retornar getValor
 */
abstract class Moneda {
    public Moneda(){
    }

    public Moneda getSerie(){
        return this;
    }
    public abstract int getValor();
}
