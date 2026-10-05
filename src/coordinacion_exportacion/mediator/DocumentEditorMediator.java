// Patrón implementado: Mediator
package coordinacion_exportacion.mediator;

import coordinacion_exportacion.app.ExportadorPipeline;
import construccion_documento.builder.Plantilla;
import construccion_documento.flyweight.GlifoFactory;
import renderizado.Formato;
import renderizado.MarkdownRenderEngine;
import renderizado.PdfRenderEngine;


import renderizado.RenderizadorEngine;
import construccion_documento.builder.DocumentBuilder;

public class DocumentEditorMediator implements Mediator {
    private final GlifoFactory fabrica;
    private final Exportador exportador;
    private final SelectorDeFormato selector;
    private final BarraDeHerramientasBuilder barra;
    private final VistaPrevia vista;
    private final BotonExportar boton;

    private Plantilla plantilla;
    private Formato formato;
    private DocumentBuilder builder;
    private RenderizadorEngine engine;

    public DocumentEditorMediator(GlifoFactory fabrica, Exportador exportador, SelectorDeFormato selector,
                                  BarraDeHerramientasBuilder barra, VistaPrevia vista, BotonExportar boton) {
        this.fabrica = fabrica;
        this.exportador = exportador;
        this.selector = selector;
        this.barra = barra;
        this.vista = vista;
        this.boton = boton;
        selector.setMediator(this);
        barra.setMediator(this);
        vista.setMediator(this);
        boton.setMediator(this);
        boton.setHabilitado(false);
    }

    @Override
    public void notificar(Componente emisor, Evento evento) {
        switch (evento) {
            case FORMATO_CAMBIADO:
                formato = selector.getFormato();
                engine = formato.crearEngine();
                actualizarEstado();
                break;
            case PLANTILLA_CAMBIADA:
                plantilla = barra.getPlantilla();
                builder = plantilla.crearBuilder(fabrica);
                actualizarEstado();
                break;
            case EXPORTAR_SOLICITADO:
                exportador.exportar(plantilla, formato, builder, engine);
                break;
        }
    }

    private void actualizarEstado() {
        boton.setHabilitado(builder != null && engine != null);
        String nombrePlantilla = plantilla == null ? "sin elegir" : plantilla.getNombre();
        String nombreFormato = formato == null ? "sin elegir" : formato.getNombre();
        vista.mostrar("Plantilla: " + nombrePlantilla + " | Formato: " + nombreFormato
                + " | Exportar habilitado: " + boton.isHabilitado());
    }
}
