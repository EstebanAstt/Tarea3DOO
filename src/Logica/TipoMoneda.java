package Logica;

public enum TipoMoneda {

    MONEDA100(0),
    MONEDA500(1),
    MONEDA1000(2),
    MONEDA1500(3);


    private final int tipo;


    private TipoMoneda(int tipo) {
        this.tipo = tipo;
    }


    public int getTipo() {
        return this.tipo;
    }
}
