// Patrón implementado: Chain of Responsibility
package procesamiento_contenido.chain;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Tabla;
import coordinacion_exportacion.model.Texto;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Sanitizador extends ProcesadorHandler {
    private final List<Pattern> prohibidas = new ArrayList<>();

    public Sanitizador(String... palabras) {
        int flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS;
        for (String palabra : palabras) {
            prohibidas.add(Pattern.compile("\\b" + Pattern.quote(palabra) + "\\b", flags));
        }
    }

    @Override
    protected void manejar(ContextoProcesamiento contexto) {
        int ocultas = 0;
        for (Texto texto : contexto.getContenido().getTextos()) {
            String original = texto.getContenido();
            String nuevo = original;
            for (Pattern patron : prohibidas) {
                nuevo = patron.matcher(nuevo).replaceAll(m -> "*".repeat(m.group().length()));
            }
            if (!nuevo.equals(original)) {
                texto.setContenido(nuevo);
                ocultas++;
            }
        }
        contexto.registrar("Textos sanitizados: " + ocultas);
    }
}
