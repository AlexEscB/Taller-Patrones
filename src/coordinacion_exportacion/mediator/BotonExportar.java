// Patrón implementado: Mediator
package coordinacion_exportacion.mediator;


public class BotonExportar extends Componente {
    private boolean habilitado;

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void hacerClick() {
        if (!habilitado) {
            System.out.println("[BotonExportar] Click ignorado: falta elegir plantilla y formato");
            return;
        }
        System.out.println("[BotonExportar] Click en exportar");
        mediator.notificar(this, Evento.EXPORTAR_SOLICITADO);
    }
}
