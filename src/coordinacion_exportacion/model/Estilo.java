package coordinacion_exportacion.model;


public class Estilo {
    private final String tipografia;
    private final String color;
    private final double escala;

    public Estilo(String tipografia, String color, double escala) {
        this.tipografia = tipografia;
        this.color = color;
        this.escala = escala;
    }

    public String getTipografia() {
        return tipografia;
    }

    public String getColor() {
        return color;
    }

    public double getEscala() {
        return escala;
    }
}
