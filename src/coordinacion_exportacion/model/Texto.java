package coordinacion_exportacion.model;


import construccion_documento.flyweight.ElementoVisual;
import construccion_documento.flyweight.Glifo;
import construccion_documento.flyweight.GlifoFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Texto {
    private String contenido;
    private final GlifoFactory fabrica;
    private final Estilo estilo;
    private final double y;
    private final List<ElementoVisual> elementos = new ArrayList<>();

    public Texto(String contenido, GlifoFactory fabrica, Estilo estilo, double y) {
        this.contenido = contenido;
        this.fabrica = fabrica;
        this.estilo = estilo;
        this.y = y;
        regenerar();
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String nuevoContenido) {
        this.contenido = nuevoContenido;
        regenerar();
    }

    public double getY() {
        return y;
    }

    public List<ElementoVisual> getElementos() {
        return Collections.unmodifiableList(elementos);
    }

    private void regenerar() {
        elementos.clear();
        double x = 0;
        for (char c : contenido.toCharArray()) {
            Glifo glifo = fabrica.obtenerCaracter(c, estilo.getTipografia());
            elementos.add(new ElementoVisual(glifo, x, y, estilo.getColor(), estilo.getEscala()));
            x += glifo.getAncho() * estilo.getEscala();
        }
    }
}
