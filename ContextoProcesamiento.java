// Patrón implementado: Chain of Responsibility
package procesamiento_contenido.chain;

import coordinacion_exportacion.model.Contenido;
import procesamiento_contenido.interpreter.ContextoInterprete;
import procesamiento_contenido.interpreter.ErrorInterpretacion;



import java.util.ArrayList;
import java.util.List;

public class ContextoProcesamiento {
    private final Contenido contenido;
    private final ContextoInterprete variables;
    private final List<String> errores = new ArrayList<>();
    private boolean detenido;

    public ContextoProcesamiento(Contenido contenido, ContextoInterprete variables) {
        this.contenido = contenido;
        this.variables = variables;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public ContextoInterprete getVariables() {
        return variables;
    }

    public List<String> getErrores() {
        return errores;
    }

    public boolean isDetenido() {
        return detenido;
    }

    public void registrar(String mensaje) {
        System.out.println("   [cadena] " + mensaje);
    }

    public void errorCritico(String mensaje) {
        errores.add(mensaje);
        detenido = true;
        registrar("ERROR CRITICO: " + mensaje);
    }
}
