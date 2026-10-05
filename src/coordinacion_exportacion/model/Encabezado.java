package coordinacion_exportacion.model;


import construccion_documento.flyweight.ElementoVisual;
import construccion_documento.flyweight.GlifoIcono;

import java.util.List;

public class Encabezado implements Bloque {
    private final Texto texto;
    private final int nivel;
    private final ElementoVisual icono;

    public Encabezado(Texto texto, int nivel, ElementoVisual icono) {
        this.texto = texto;
        this.nivel = nivel;
        this.icono = icono;
    }

    public Texto getTexto() {
        return texto;
    }

    public int getNivel() {
        return nivel;
    }

    public ElementoVisual getIcono() {
        return icono;
    }

    public String getRecursoIcono() {
        if (icono == null) {
            return null;
        }
        return ((GlifoIcono) icono.getGlifo()).getRecurso();
    }

    @Override
    public List<Texto> getTextos() {
        return List.of(texto);
    }
}
