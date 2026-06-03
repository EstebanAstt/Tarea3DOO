package Logica;

/**
 * la clase {@link Dulce} es subclase de producto y a su vez es la clase padre de Super8 y Snickers respectivamente
 * Para hacer que clase Dulce y Bebida tuvieran otras diferencias además de del nombre fue poner dos métodos distintos,
 * en bebida es String beber y en Dulce es String comer
 */
public abstract class Dulce extends Producto {
    private int serie;

    /**
     *
     * @param serie está definida en producto, por lo que para definir la serie se debe llamar a la clase super
     */
    public Dulce(int serie){
        super(serie);
        this.serie = serie;
    }

    public abstract String comer();
}
