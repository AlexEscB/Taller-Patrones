package coordinacion_exportacion.model;


import java.util.ArrayList;
import java.util.List;

public class Tabla implements Bloque {
    private final List<Texto> cabecera;
    private final List<List<Texto>> filas;

    public Tabla(List<Texto> cabecera, List<List<Texto>> filas) {
        this.cabecera = cabecera;
        this.filas = filas;
    }

    public List<Texto> getCabecera() {
        return cabecera;
    }

    public List<List<Texto>> getFilas() {
        return filas;
    }

    @Override
    public List<Texto> getTextos() {
        List<Texto> todos = new ArrayList<>(cabecera);
        for (List<Texto> fila : filas) {
            todos.addAll(fila);
        }
        return todos;
    }
}
