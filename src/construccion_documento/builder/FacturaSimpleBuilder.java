// Patrón implementado: Builder
package construccion_documento.builder;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Encabezado;
import coordinacion_exportacion.model.Estilo;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Tabla;
import coordinacion_exportacion.model.Texto;
import construccion_documento.flyweight.ElementoVisual;
import construccion_documento.flyweight.Glifo;
import construccion_documento.flyweight.GlifoFactory;



public class FacturaSimpleBuilder extends BaseDocumentBuilder {

    public FacturaSimpleBuilder(GlifoFactory fabrica) {
        super(fabrica);
    }

    @Override
    protected Estilo estiloEncabezado(int nivel) {
        return new Estilo("Monospaced-Bold", "#000000", 1.2);
    }

    @Override
    protected Estilo estiloTexto() {
        return new Estilo("Monospaced", "#000000", 0.9);
    }

    @Override
    protected String iconoEncabezado() {
        return null;
    }
}
