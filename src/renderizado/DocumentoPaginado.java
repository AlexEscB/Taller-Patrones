// Patrón implementado: Bridge
package renderizado;

import coordinacion_exportacion.model.Contenido;


import coordinacion_exportacion.model.Bloque;

public class DocumentoPaginado extends Documento {
    private final int bloquesPorPagina;

    public DocumentoPaginado(Contenido contenido, RenderizadorEngine engine, int bloquesPorPagina) {
        super(contenido, engine);
        this.bloquesPorPagina = bloquesPorPagina;
    }

    @Override
    public String renderizar() {
        engine.iniciar(contenido.getTitulo());
        int enPagina = 0;
        int pagina = 1;
        for (Bloque bloque : contenido.getBloques()) {
            if (enPagina == bloquesPorPagina) {
                pagina++;
                engine.saltoDePagina(pagina);
                enPagina = 0;
            }
            dibujar(bloque);
            enPagina++;
        }
        return engine.finalizar();
    }
}
