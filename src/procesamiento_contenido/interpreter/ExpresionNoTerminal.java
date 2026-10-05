// Patrón implementado: Interpreter
package procesamiento_contenido.interpreter;

import procesamiento_contenido.interpreter.Expresion;


public abstract class ExpresionNoTerminal implements Expresion {
    protected final Expresion izquierda;
    protected final Expresion derecha;

    protected ExpresionNoTerminal(Expresion izquierda, Expresion derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    public Object interpretar(ContextoInterprete contexto) {
        double a = aNumero(izquierda.interpretar(contexto));
        double b = aNumero(derecha.interpretar(contexto));
        return operar(a, b);
    }

    private double aNumero(Object valor) {
        if (valor instanceof Double) {
            return (Double) valor;
        }
        throw new ErrorInterpretacion("Operando no numerico: " + valor);
    }

    protected abstract double operar(double a, double b);

    protected abstract String simbolo();

    @Override
    public String toString() {
        return "(" + izquierda + " " + simbolo() + " " + derecha + ")";
    }
}
