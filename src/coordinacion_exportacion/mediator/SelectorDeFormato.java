// Patrón implementado: Mediator
package coordinacion_exportacion.mediator;


import renderizado.Formato;

public class SelectorDeFormato extends Componente {
    private Formato formato;

    public void seleccionar(Formato formato) {
        this.formato = formato;
        System.out.println("[SelectorDeFormato] El usuario eligio " + formato.getNombre());
        mediator.notificar(this, Evento.FORMATO_CAMBIADO);
    }

    public Formato getFormato() {
        return formato;
    }
}
