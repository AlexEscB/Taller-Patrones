package coordinacion_exportacion.model;


import java.util.ArrayList;
import java.util.List;

public class Contenido {
    private final String titulo;
    private final List<Bloque> bloques;

    public Contenido(String titulo, List<Bloque> bloques) {
        this.titulo = titulo;
        this.bloques = bloques;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Bloque> getBloques() {
        return bloques;
    }

    public List<Texto> getTextos() {
        List<Texto> todos = new ArrayList<>();
        for (Bloque bloque : bloques) {
            todos.addAll(bloque.getTextos());
        }
        return todos;
    }

    public int contarElementosVisuales() {
        int total = 0;
        for (Texto texto : getTextos()) {
            total += texto.getElementos().size();
        }
        for (Bloque bloque : bloques) {
            if (bloque instanceof Encabezado && ((Encabezado) bloque).getIcono() != null) {
                total++;
            }
        }
        return total;
    }
}
