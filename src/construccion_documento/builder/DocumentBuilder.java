// Patrón implementado: Builder
package construccion_documento.builder;

import coordinacion_exportacion.model.Contenido;



import java.util.List;

public interface DocumentBuilder {
    DocumentBuilder addHeader(String texto, int nivel);

    DocumentBuilder addParagraph(String texto);

    DocumentBuilder addTable(List<String> cabecera, List<List<String>> filas);

    DocumentBuilder addFooter(String texto);

    Contenido build();
}
