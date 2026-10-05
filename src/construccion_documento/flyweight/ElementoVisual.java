// Patrón implementado: Flyweight
package construccion_documento.flyweight;


public class ElementoVisual {
    private final Glifo glifo;
    private final double x;
    private final double y;
    private final String color;
    private final double escala;

    public ElementoVisual(Glifo glifo, double x, double y, String color, double escala) {
        this.glifo = glifo;
        this.x = x;
        this.y = y;
        this.color = color;
        this.escala = escala;
    }

    public Glifo getGlifo() {
        return glifo;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public double getEscala() {
        return escala;
    }
}
