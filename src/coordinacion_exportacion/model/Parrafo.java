package coordinacion_exportacion.model;


import java.util.List;

public class Parrafo implements Bloque {
    private final Texto texto;

    public Parrafo(Texto texto) {
        this.texto = texto;
    }

    public Texto getTexto() {
        return texto;
    }

    @Override
    public List<Texto> getTextos() {
        return List.of(texto);
    }
}
