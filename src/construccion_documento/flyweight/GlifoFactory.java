// Patrón implementado: Flyweight
package construccion_documento.flyweight;

import coordinacion_exportacion.model.Estilo;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GlifoFactory {
    private final Map<String, Glifo> cache = new HashMap<>();
    private int solicitados;

    public Glifo obtenerCaracter(char caracter, String tipografia) {
        return obtener(tipografia + ":" + caracter, () -> new GlifoCaracter(caracter, tipografia));
    }

    public Glifo obtenerIcono(String nombre) {
        return obtener("icono:" + nombre, () -> new GlifoIcono(nombre));
    }

    private Glifo obtener(String clave, Supplier<Glifo> creador) {
        solicitados++;
        return cache.computeIfAbsent(clave, k -> creador.get());
    }

    public int getTotalCreados() {
        return cache.size();
    }

    public int getTotalSolicitados() {
        return solicitados;
    }

    public double getPorcentajeReutilizacion() {
        if (solicitados == 0) {
            return 0;
        }
        return 100.0 * (solicitados - cache.size()) / solicitados;
    }
}
