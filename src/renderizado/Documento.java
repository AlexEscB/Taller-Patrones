// Patrón implementado: Bridge
package renderizado;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;


import coordinacion_exportacion.model.Encabezado;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Pie;
import coordinacion_exportacion.model.Tabla;
import coordinacion_exportacion.model.Texto;

import java.util.ArrayList;
import java.util.List;

public abstract class Documento {
    protected final Contenido contenido;
    protected RenderizadorEngine engine;

    protected Documento(Contenido contenido, RenderizadorEngine engine) {
        this.contenido = contenido;
        this.engine = engine;
    }

    public void setEngine(RenderizadorEngine engine) {
        this.engine = engine;
    }

    public abstract String renderizar();

    protected void dibujar(Bloque bloque) {
        if (bloque instanceof Encabezado) {
            Encabezado e = (Encabezado) bloque;
            engine.encabezado(e.getTexto().getContenido(), e.getNivel(), e.getRecursoIcono());
        } else if (bloque instanceof Parrafo) {
            engine.parrafo(((Parrafo) bloque).getTexto().getContenido());
        } else if (bloque instanceof Tabla) {
            Tabla t = (Tabla) bloque;
            List<List<String>> filas = new ArrayList<>();
            for (List<Texto> fila : t.getFilas()) {
                filas.add(aCadenas(fila));
            }
            engine.tabla(aCadenas(t.getCabecera()), filas);
        } else if (bloque instanceof Pie) {
            engine.pie(((Pie) bloque).getTexto().getContenido());
        }
    }

    private List<String> aCadenas(List<Texto> textos) {
        List<String> cadenas = new ArrayList<>();
        for (Texto texto : textos) {
            cadenas.add(texto.getContenido());
        }
        return cadenas;
    }
}
