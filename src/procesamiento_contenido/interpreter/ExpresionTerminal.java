// Patrón implementado: Interpreter
package procesamiento_contenido.interpreter;


public class ExpresionTerminal implements Expresion {
    private final String token;
    private final boolean literal;

    public ExpresionTerminal(String token, boolean literal) {
        this.token = token;
        this.literal = literal;
    }

    @Override
    public Object interpretar(ContextoInterprete contexto) {
        if (literal) {
            return Double.valueOf(token);
        }
        return contexto.obtener(token);
    }

    @Override
    public String toString() {
        return token;
    }
}
