package coordinacion_exportacion.model;


import java.util.List;

public class Pie implements Bloque {
    private final Texto texto;

    public Pie(Texto texto) {
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
