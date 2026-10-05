package procesamiento_contenido.interpreter;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ContextoInterprete {
    private final Map<String, Object> variables = new HashMap<>();

    public void definir(String nombre, Object valor) {
        variables.put(nombre, valor);
    }

    public Object obtener(String nombre) {
        if (!variables.containsKey(nombre)) {
            throw new ErrorInterpretacion("Variable no definida: " + nombre);
        }
        return variables.get(nombre);
    }

    public static String formatear(Object valor) {
        if (valor instanceof Double) {
            return String.format(Locale.US, "%,.2f", (Double) valor);
        }
        return String.valueOf(valor);
    }
}
