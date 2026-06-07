package Logica;

/**
 * La clase {@link Moneda} que es clase padre de Moneda1500, Moneda1000, Moneda500 y Moneda100
 * Las clases de Moneda tienen un número de serie que corresponde a su hashCode
 * Además cada subclase de Moneda tiene un valor distinto al retornar getValor
 */
abstract class Moneda {
    private static int NUM_SERIE = 1;
    private int serie;
    public Moneda(){
        this.serie = NUM_SERIE;
        NUM_SERIE++;
    }

    public int getSerie(){
        return this.serie;
    }
    public abstract int getValor();
}
