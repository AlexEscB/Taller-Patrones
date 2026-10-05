// Patrón implementado: Chain of Responsibility
package procesamiento_contenido.chain;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Texto;
import procesamiento_contenido.interpreter.ContextoInterprete;
import procesamiento_contenido.interpreter.Expresion;
import procesamiento_contenido.interpreter.ParserExpresiones;


import procesamiento_contenido.interpreter.ErrorInterpretacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluadorExpresiones extends ProcesadorHandler {
    private static final Pattern MARCADOR = Pattern.compile("#\\{([^}]*)\\}");

    @Override
    protected void manejar(ContextoProcesamiento contexto) {
        for (Texto texto : contexto.getContenido().getTextos()) {
            Matcher m = MARCADOR.matcher(texto.getContenido());
            StringBuffer resultado = new StringBuffer();
            boolean huboMarcadores = false;
            while (m.find()) {
                huboMarcadores = true;
                String formula = m.group(1).trim();
                try {
                    Expresion arbol = ParserExpresiones.parsear(formula);
                    Object valor = arbol.interpretar(contexto.getVariables());
                    String valorTexto = ContextoInterprete.formatear(valor);
                    contexto.registrar("Interpreter: " + arbol + " = " + valorTexto);
                    m.appendReplacement(resultado, Matcher.quoteReplacement(valorTexto));
                } catch (ErrorInterpretacion e) {
                    contexto.errorCritico("Formula invalida '" + formula + "': " + e.getMessage());
                    return;
                }
            }
            if (huboMarcadores) {
                m.appendTail(resultado);
                texto.setContenido(resultado.toString());
            }
        }
    }
}
