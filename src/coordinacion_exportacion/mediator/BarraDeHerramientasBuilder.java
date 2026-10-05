// Patrón implementado: Mediator
package coordinacion_exportacion.mediator;


import construccion_documento.builder.Plantilla;

public class BarraDeHerramientasBuilder extends Componente {
    private Plantilla plantilla;

    public void seleccionarPlantilla(Plantilla plantilla) {
        this.plantilla = plantilla;
        System.out.println("[BarraDeHerramientasBuilder] El usuario eligio " + plantilla.getNombre());
        mediator.notificar(this, Evento.PLANTILLA_CAMBIADA);
    }

    public Plantilla getPlantilla() {
        return plantilla;
    }
}
