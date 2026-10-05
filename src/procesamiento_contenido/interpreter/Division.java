// Patrón implementado: Interpreter
package procesamiento_contenido.interpreter;


public class Division extends ExpresionNoTerminal {

    public Division(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }

    @Override
    protected double operar(double a, double b) {
        if (b == 0) {
            throw new ErrorInterpretacion("Division por cero");
        }
        return a / b;
    }

    @Override
    protected String simbolo() {
        return "/";
    }
}
