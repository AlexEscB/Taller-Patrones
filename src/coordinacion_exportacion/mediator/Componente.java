// Patrón implementado: Mediator
package coordinacion_exportacion.mediator;


public abstract class Componente {
    protected Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
}
