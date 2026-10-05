// Patrón implementado: Builder
package construccion_documento.builder;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Encabezado;
import coordinacion_exportacion.model.Estilo;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Texto;
import construccion_documento.flyweight.ElementoVisual;
import construccion_documento.flyweight.Glifo;
import construccion_documento.flyweight.GlifoFactory;



public class ReporteEjecutivoBuilder extends BaseDocumentBuilder {

    public ReporteEjecutivoBuilder(GlifoFactory fabrica) {
        super(fabrica);
    }

    @Override
    protected Estilo estiloEncabezado(int nivel) {
        return new Estilo("Serif-Bold", "#1A237E", nivel == 1 ? 1.6 : 1.3);
    }

    @Override
    protected Estilo estiloTexto() {
        return new Estilo("Serif", "#212121", 1.0);
    }

    @Override
    protected String iconoEncabezado() {
        return "logo-empresa";
    }
}
