// Patrón implementado: Flyweight
package construccion_documento.flyweight;

import coordinacion_exportacion.model.Estilo;


public class GlifoIcono implements Glifo {
    private final String nombre;
    private final String recurso;

    public GlifoIcono(String nombre) {
        this.nombre = nombre;
        this.recurso = "iconos/" + nombre + ".png";
    }

    public String getNombre() {
        return nombre;
    }

    public String getRecurso() {
        return recurso;
    }

    @Override
    public String getClave() {
        return "icono:" + nombre;
    }

    @Override
    public double getAncho() {
        return 16.0;
    }
}
