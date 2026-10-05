// Patrón implementado: Chain of Responsibility
package procesamiento_contenido.chain;


public abstract class ProcesadorHandler {
    private ProcesadorHandler siguiente;

    public ProcesadorHandler setSiguiente(ProcesadorHandler siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public final void procesar(ContextoProcesamiento contexto) {
        contexto.registrar(getClass().getSimpleName() + " ejecutando");
        manejar(contexto);
        if (contexto.isDetenido()) {
            contexto.registrar("Cadena interrumpida en " + getClass().getSimpleName());
            return;
        }
        if (siguiente != null) {
            siguiente.procesar(contexto);
        }
    }

    protected abstract void manejar(ContextoProcesamiento contexto);
}
