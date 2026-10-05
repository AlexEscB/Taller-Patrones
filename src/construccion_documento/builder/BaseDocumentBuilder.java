// Patrón implementado: Builder
package construccion_documento.builder;


import construccion_documento.flyweight.ElementoVisual;
import construccion_documento.flyweight.GlifoFactory;
import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Encabezado;
import coordinacion_exportacion.model.Estilo;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Pie;
import coordinacion_exportacion.model.Tabla;
import coordinacion_exportacion.model.Texto;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDocumentBuilder implements DocumentBuilder {
    private final GlifoFactory fabrica;
    private final List<Bloque> bloques = new ArrayList<>();
    private String titulo;
    private double y;

    protected BaseDocumentBuilder(GlifoFactory fabrica) {
        this.fabrica = fabrica;
    }

    protected abstract Estilo estiloEncabezado(int nivel);

    protected abstract Estilo estiloTexto();

    protected abstract String iconoEncabezado();

    @Override
    public DocumentBuilder addHeader(String texto, int nivel) {
        if (titulo == null) {
            titulo = texto;
        }
        Estilo estilo = estiloEncabezado(nivel);
        Texto contenido = crearTexto(texto, estilo);
        ElementoVisual icono = null;
        String nombreIcono = iconoEncabezado();
        if (nombreIcono != null) {
            icono = new ElementoVisual(fabrica.obtenerIcono(nombreIcono), 0, contenido.getY(),
                    estilo.getColor(), estilo.getEscala());
        }
        bloques.add(new Encabezado(contenido, nivel, icono));
        return this;
    }

    @Override
    public DocumentBuilder addParagraph(String texto) {
        bloques.add(new Parrafo(crearTexto(texto, estiloTexto())));
        return this;
    }

    @Override
    public DocumentBuilder addTable(List<String> cabecera, List<List<String>> filas) {
        List<Texto> textosCabecera = new ArrayList<>();
        for (String celda : cabecera) {
            textosCabecera.add(crearTexto(celda, estiloTexto()));
        }
        List<List<Texto>> textosFilas = new ArrayList<>();
        for (List<String> fila : filas) {
            List<Texto> textosFila = new ArrayList<>();
            for (String celda : fila) {
                textosFila.add(crearTexto(celda, estiloTexto()));
            }
            textosFilas.add(textosFila);
        }
        bloques.add(new Tabla(textosCabecera, textosFilas));
        return this;
    }

    @Override
    public DocumentBuilder addFooter(String texto) {
        bloques.add(new Pie(crearTexto(texto, estiloTexto())));
        return this;
    }

    @Override
    public Contenido build() {
        Contenido resultado = new Contenido(titulo == null ? "Sin titulo" : titulo, new ArrayList<>(bloques));
        bloques.clear();
        titulo = null;
        y = 0;
        return resultado;
    }

    private Texto crearTexto(String contenido, Estilo estilo) {
        double yActual = y;
        y += 20 * estilo.getEscala();
        return new Texto(contenido, fabrica, estilo, yActual);
    }
}
