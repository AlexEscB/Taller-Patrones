// Patrón implementado: Builder
package construccion_documento.builder;

import construccion_documento.flyweight.GlifoFactory;



public enum Plantilla {
    REPORTE_EJECUTIVO("Reporte ejecutivo"),
    FACTURA_SIMPLE("Factura simple");

    private final String nombre;

    Plantilla(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public DocumentBuilder crearBuilder(GlifoFactory fabrica) {
        switch (this) {
            case REPORTE_EJECUTIVO:
                return new ReporteEjecutivoBuilder(fabrica);
            default:
                return new FacturaSimpleBuilder(fabrica);
        }
    }
}
