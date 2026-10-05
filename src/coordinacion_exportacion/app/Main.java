package coordinacion_exportacion.app;

import renderizado.Formato;
import construccion_documento.builder.DocumentBuilder;
import construccion_documento.builder.Plantilla;
import procesamiento_contenido.chain.ContextoProcesamiento;
import construccion_documento.flyweight.GlifoFactory;
import coordinacion_exportacion.mediator.BarraDeHerramientasBuilder;
import coordinacion_exportacion.mediator.BotonExportar;
import coordinacion_exportacion.mediator.DocumentEditorMediator;
import coordinacion_exportacion.mediator.SelectorDeFormato;
import coordinacion_exportacion.mediator.VistaPrevia;
import coordinacion_exportacion.model.Contenido;



import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        GlifoFactory fabrica = new GlifoFactory();
        ExportadorPipeline pipeline = new ExportadorPipeline(fabrica, Path.of("salida"));

        SelectorDeFormato selector = new SelectorDeFormato();
        BarraDeHerramientasBuilder barra = new BarraDeHerramientasBuilder();
        VistaPrevia vista = new VistaPrevia();
        BotonExportar boton = new BotonExportar();
        new DocumentEditorMediator(fabrica, pipeline, selector, barra, vista, boton);

        System.out.println("=== [1] MEDIATOR: edicion y configuracion ===");
        boton.hacerClick();
        barra.seleccionarPlantilla(Plantilla.REPORTE_EJECUTIVO);
        selector.seleccionar(Formato.PDF);
        boton.hacerClick();

        System.out.println("\n=== El usuario cambia el formato: solo se reconfigura el renderizador ===");
        selector.seleccionar(Formato.HTML);
        boton.hacerClick();

        System.out.println("\n=== El usuario cambia el formato a Markdown ===");
        selector.seleccionar(Formato.MARKDOWN);
        boton.hacerClick();

        System.out.println("\n=== El usuario cambia la plantilla: solo se reconfigura el builder ===");
        barra.seleccionarPlantilla(Plantilla.FACTURA_SIMPLE);
        boton.hacerClick();

        System.out.println("\n=== Caso de error: la cadena se interrumpe ===");
        demostrarCadenaInterrumpida(fabrica);

        System.out.println("\n=== Resumen final del Flyweight ===");
        System.out.println("Glifos solicitados: " + fabrica.getTotalSolicitados());
        System.out.println("Glifos unicos en memoria: " + fabrica.getTotalCreados());
        System.out.printf("Reutilizacion total: %.1f%%%n", fabrica.getPorcentajeReutilizacion());
    }

    private static void demostrarCadenaInterrumpida(GlifoFactory fabrica) {
        DocumentBuilder builder = Plantilla.FACTURA_SIMPLE.crearBuilder(fabrica);
        builder.addHeader("Documento corrupto", 1)
                .addParagraph("Total: #{PRECIO_BASE * 1.19 - ")
                .addParagraph("Este parrafo no deberia procesarse: #{PRECIO_BASE}");
        Contenido contenido = builder.build();

        ContextoProcesamiento contexto = new ContextoProcesamiento(contenido, ExportadorPipeline.crearVariables());
        ExportadorPipeline.crearCadena().procesar(contexto);
        System.out.println("   Errores registrados: " + contexto.getErrores().size());
    }
}
