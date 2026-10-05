// Patrón implementado: Interpreter
package procesamiento_contenido.interpreter;


public class ParserExpresiones {
    private final String fuente;
    private int pos;

    private ParserExpresiones(String fuente) {
        this.fuente = fuente;
    }

    public static Expresion parsear(String fuente) {
        ParserExpresiones parser = new ParserExpresiones(fuente);
        Expresion resultado = parser.expresion();
        parser.saltarEspacios();
        if (parser.pos < fuente.length()) {
            throw new ErrorInterpretacion("Caracter inesperado '" + fuente.charAt(parser.pos)
                    + "' en la posicion " + parser.pos);
        }
        return resultado;
    }

    private Expresion expresion() {
        Expresion izquierda = termino();
        while (true) {
            saltarEspacios();
            char c = actual();
            if (c == '+') {
                pos++;
                izquierda = new Suma(izquierda, termino());
            } else if (c == '-') {
                pos++;
                izquierda = new Resta(izquierda, termino());
            } else {
                return izquierda;
            }
        }
    }

    private Expresion termino() {
        Expresion izquierda = factor();
        while (true) {
            saltarEspacios();
            char c = actual();
            if (c == '*') {
                pos++;
                izquierda = new Multiplicacion(izquierda, factor());
            } else if (c == '/') {
                pos++;
                izquierda = new Division(izquierda, factor());
            } else {
                return izquierda;
            }
        }
    }

    private Expresion factor() {
        saltarEspacios();
        char c = actual();
        if (c == '(') {
            pos++;
            Expresion interna = expresion();
            saltarEspacios();
            if (actual() != ')') {
                throw new ErrorInterpretacion("Falta cerrar el parentesis");
            }
            pos++;
            return interna;
        }
        if (Character.isDigit(c) || c == '.') {
            int inicio = pos;
            while (Character.isDigit(actual()) || actual() == '.') {
                pos++;
            }
            String numero = fuente.substring(inicio, pos);
            try {
                Double.parseDouble(numero);
            } catch (NumberFormatException e) {
                throw new ErrorInterpretacion("Numero invalido: " + numero);
            }
            return new ExpresionTerminal(numero, true);
        }
        if (Character.isLetter(c) || c == '_') {
            int inicio = pos;
            while (Character.isLetterOrDigit(actual()) || actual() == '_') {
                pos++;
            }
            return new ExpresionTerminal(fuente.substring(inicio, pos), false);
        }
        throw new ErrorInterpretacion("Se esperaba un operando en la posicion " + pos);
    }

    private void saltarEspacios() {
        while (Character.isWhitespace(actual())) {
            pos++;
        }
    }

    private char actual() {
        return pos < fuente.length() ? fuente.charAt(pos) : '\0';
    }
}
