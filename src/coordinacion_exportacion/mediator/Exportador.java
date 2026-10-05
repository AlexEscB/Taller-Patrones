// Patrón implementado: Mediator
package coordinacion_exportacion.mediator;

import construccion_documento.builder.DocumentBuilder;
import construccion_documento.builder.Plantilla;
import renderizado.Formato;
import renderizado.RenderizadorEngine;



public interface Exportador {
    void exportar(Plantilla plantilla, Formato formato, DocumentBuilder builder, RenderizadorEngine engine);
}
