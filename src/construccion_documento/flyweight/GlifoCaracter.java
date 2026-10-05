// Patrón implementado: Flyweight
package construccion_documento.flyweight;

import coordinacion_exportacion.model.Estilo;


public class GlifoCaracter implements Glifo {
    private final char caracter;
    private final String tipografia;

    public GlifoCaracter(char caracter, String tipografia) {
        this.caracter = caracter;
        this.tipografia = tipografia;
    }

    public char getCaracter() {
        return caracter;
    }

    public String getTipografia() {
        return tipografia;
    }

    @Override
    public String getClave() {
        return tipografia + ":" + caracter;
    }

    @Override
    public double getAncho() {
        return caracter == ' ' ? 4.0 : 7.0;
    }
}
