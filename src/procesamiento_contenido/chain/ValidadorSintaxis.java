// Patrón implementado: Chain of Responsibility
package procesamiento_contenido.chain;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Tabla;
import coordinacion_exportacion.model.Texto;



public class ValidadorSintaxis extends ProcesadorHandler {

    @Override
    protected void manejar(ContextoProcesamiento contexto) {
        for (Texto texto : contexto.getContenido().getTextos()) {
            String error = validar(texto.getContenido());
            if (error != null) {
                contexto.errorCritico(error + " en: \"" + texto.getContenido() + "\"");
                return;
            }
        }
        contexto.registrar("Sintaxis correcta");
    }

    private String validar(String texto) {
        int i = 0;
        while ((i = texto.indexOf("#{", i)) >= 0) {
            int fin = texto.indexOf('}', i);
            if (fin < 0) {
                return "Marcador sin cerrar";
            }
            String interno = texto.substring(i + 2, fin);
            if (interno.contains("#{")) {
                return "Marcador anidado";
            }
            if (interno.trim().isEmpty()) {
                return "Marcador vacio";
            }
            i = fin + 1;
        }
        return null;
    }
}
