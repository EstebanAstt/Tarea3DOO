package Logica;

/**
 * la clase {@link Bebida} es subclase de producto y a su vez es la clase padre de Cocacola, Fanta y Sprite respectivamente
 * Para hacer que clase Dulce y Bebida tuvieran otras diferencias además de del nombre fue poner dos métodos distintos,
 * en bebida es String beber y en Dulce es String comer
 */
public abstract class Bebida extends Producto {
    private int serie;

    /**
     *
     * @param serie está definida en producto, por lo que para definir la serie se debe llamar a la clase super
     */
    public Bebida(int serie) {
        super(serie);
        this.serie = serie;
    }

    public int getSerie() {
        return this.serie;
    }

    public String beber(){
        return "Bebiste: ";
    }
}
