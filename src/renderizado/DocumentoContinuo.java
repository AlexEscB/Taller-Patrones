package renderizado;

import coordinacion_exportacion.model.Contenido;


import coordinacion_exportacion.model.Bloque;

public class DocumentoContinuo extends Documento {

    public DocumentoContinuo(Contenido contenido, RenderizadorEngine engine) {
        super(contenido, engine);
    }

    @Override
    public String renderizar() {
        engine.iniciar(contenido.getTitulo());
        for (Bloque bloque : contenido.getBloques()) {
            dibujar(bloque);
        }
        return engine.finalizar();
    }
}
