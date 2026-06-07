package Logica;

public enum TipoProducto {
    COCA(1000, 1),
    SPRITE(1000, 2),
    FANTA(1000, 3),
    SUPER8(500, 4),
    SNICKERS(1100, 5);

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
}
