// Patrón implementado: Mediator
package coordinacion_exportacion.mediator;


public interface Mediator {
    void notificar(Componente emisor, Evento evento);
}
