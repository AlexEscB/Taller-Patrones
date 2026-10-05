package coordinacion_exportacion.app;

import renderizado.Documento;
import renderizado.DocumentoContinuo;
import renderizado.DocumentoPaginado;
import renderizado.Formato;
import renderizado.RenderizadorEngine;
import construccion_documento.builder.DocumentBuilder;
import construccion_documento.builder.DocumentDirector;
import construccion_documento.builder.Plantilla;
import procesamiento_contenido.chain.ContextoProcesamiento;
import procesamiento_contenido.chain.EvaluadorExpresiones;
import procesamiento_contenido.chain.ProcesadorHandler;
import procesamiento_contenido.chain.Sanitizador;
import procesamiento_contenido.chain.ValidadorSintaxis;
import construccion_documento.flyweight.GlifoFactory;
import procesamiento_contenido.interpreter.ContextoInterprete;
import coordinacion_exportacion.mediator.Exportador;
import coordinacion_exportacion.model.Contenido;



import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class ExportadorPipeline implements Exportador {
    private final GlifoFactory fabrica;
    private final Path carpetaSalida;
    private final DocumentDirector director = new DocumentDirector();

    public ExportadorPipeline(GlifoFactory fabrica, Path carpetaSalida) {
        this.fabrica = fabrica;
        this.carpetaSalida = carpetaSalida;
    }

    public static ProcesadorHandler crearCadena() {
        ProcesadorHandler validador = new ValidadorSintaxis();
        validador.setSiguiente(new Sanitizador("confidencial", "secreto", "password"))
                .setSiguiente(new EvaluadorExpresiones());
        return validador;
    }

    public static ContextoInterprete crearVariables() {
        ContextoInterprete variables = new ContextoInterprete();
        variables.definir("PRECIO_BASE", 1000000.0);
        variables.definir("DESCUENTO", 50000.0);
        variables.definir("CANTIDAD", 3.0);
        variables.definir("FECHA_ACTUAL", LocalDate.now().toString());
        return variables;
    }

    public static void imprimirEstadisticas(GlifoFactory fabrica, Contenido contenido) {
        System.out.println("   Elementos visuales del documento: " + contenido.contarElementosVisuales());
        System.out.println("   Glifos solicitados a la fabrica: " + fabrica.getTotalSolicitados());
        System.out.println("   Glifos unicos creados: " + fabrica.getTotalCreados());
        System.out.printf("   Reutilizacion: %.1f%%%n", fabrica.getPorcentajeReutilizacion());
    }

    @Override
    public void exportar(Plantilla plantilla, Formato formato, DocumentBuilder builder, RenderizadorEngine engine) {
        System.out.println("\n----- Exportacion: " + plantilla.getNombre() + " -> " + formato.getNombre() + " -----");

        System.out.println("\n[2] BUILDER + FLYWEIGHT");
        Contenido contenido = director.construir(plantilla, builder);
        System.out.println("   Documento construido: \"" + contenido.getTitulo() + "\" con "
                + contenido.getBloques().size() + " bloques");
        imprimirEstadisticas(fabrica, contenido);

        System.out.println("\n[3] CHAIN OF RESPONSIBILITY (el evaluador usa el INTERPRETER)");
        ContextoProcesamiento contexto = new ContextoProcesamiento(contenido, crearVariables());
        crearCadena().procesar(contexto);
        if (contexto.isDetenido()) {
            System.out.println("   El documento no se renderiza por errores criticos");
            return;
        }

        System.out.println("\n[4] BRIDGE (" + formato.getNombre() + ")");
        Documento documento = formato == Formato.PDF
                ? new DocumentoPaginado(contenido, engine, 4)
                : new DocumentoContinuo(contenido, engine);
        String salida = documento.renderizar();
        Path archivo = carpetaSalida.resolve(plantilla.name().toLowerCase() + "." + engine.getExtension());
        guardar(archivo, salida);
        System.out.println("   Archivo generado: " + archivo);
        System.out.println("   ---------- contenido ----------");
        System.out.println(salida);
    }

    private void guardar(Path archivo, String contenido) {
        try {
            Files.createDirectories(archivo.getParent());
            Files.writeString(archivo, contenido);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
