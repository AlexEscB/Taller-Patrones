// Patrón implementado: Bridge
package renderizado;

import coordinacion_exportacion.model.Contenido;


import java.util.List;

public interface RenderizadorEngine {
    String getFormato();

    String getExtension();

    void iniciar(String titulo);

    void encabezado(String texto, int nivel, String icono);

    void parrafo(String texto);

    void tabla(List<String> cabecera, List<List<String>> filas);

    void pie(String texto);

    void saltoDePagina(int numero);

    String finalizar();
}
