// Patrón implementado: Builder
package construccion_documento.builder;

import coordinacion_exportacion.model.Contenido;



import java.util.List;

public class DocumentDirector {

    public Contenido construir(Plantilla plantilla, DocumentBuilder builder) {
        switch (plantilla) {
            case REPORTE_EJECUTIVO:
                return construirReporte(builder);
            default:
                return construirFactura(builder);
        }
    }

    public Contenido construirReporte(DocumentBuilder builder) {
        builder.addHeader("Reporte Ejecutivo Trimestral", 1)
                .addParagraph("Fecha de emision: #{FECHA_ACTUAL}. Este documento es confidencial y no debe compartirse.")
                .addHeader("Resumen de ventas", 2)
                .addParagraph("El password del portal es secreto. Las ventas del periodo superan la meta prevista.")
                .addTable(List.of("Concepto", "Cantidad", "Valor"),
                        List.of(List.of("Licencias", "3", "#{PRECIO_BASE * CANTIDAD}"),
                                List.of("Total con IVA menos descuento", "1", "#{PRECIO_BASE * 1.19 - DESCUENTO}")))
                .addFooter("Reporte generado por el Motor de Documentos Inteligentes");
        return builder.build();
    }

    public Contenido construirFactura(DocumentBuilder builder) {
        builder.addHeader("Factura de Venta No. 0001", 1)
                .addParagraph("Fecha: #{FECHA_ACTUAL}")
                .addTable(List.of("Item", "Cant", "Valor"),
                        List.of(List.of("Licencia anual", "3", "#{PRECIO_BASE * 3}"),
                                List.of("IVA 19%", "", "#{PRECIO_BASE * 3 * 0.19}"),
                                List.of("Total a pagar", "", "#{PRECIO_BASE * 3 * 1.19 - DESCUENTO}")))
                .addFooter("Gracias por su compra");
        return builder.build();
    }
}
