package Logica;

public enum TipoProducto {
    COCA(1000, 1),
    FANTA(1000, 2),
    SPRITE(1000, 3),
    COCA1(1000,4),
    FANTA1(1000,5),
    SPRITE1(1000,6),
    SUPER8(500, 7),
    SNICKERS(1100, 8);

    private int valor;
    private int tipo;

    TipoProducto(int valor, int tipo){
        this.valor = valor;
        this.tipo = tipo;
    }

    public int getValor(){
        return valor;
    }

    public int getTipo(){
        return tipo;
    }

    public static TipoProducto buscarPorTipo(int id) {
        for (TipoProducto p : TipoProducto.values()) {
            if (p.getTipo() == id) {
                return p;
            }
        }

        return null;
    }
}
