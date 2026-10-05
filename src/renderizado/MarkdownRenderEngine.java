package renderizado;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Encabezado;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Tabla;
import coordinacion_exportacion.model.Texto;


import java.util.List;

public class MarkdownRenderEngine implements RenderizadorEngine {
    private StringBuilder sb = new StringBuilder();

    @Override
    public String getFormato() {
        return "Markdown";
    }

    @Override
    public String getExtension() {
        return "md";
    }

    @Override
    public void iniciar(String titulo) {
        sb = new StringBuilder();
    }

    @Override
    public void encabezado(String texto, int nivel, String icono) {
        sb.append("#".repeat(Math.min(Math.max(nivel, 1), 6))).append(" ");
        if (icono != null) {
            sb.append("![icono](").append(icono).append(") ");
        }
        sb.append(texto).append("\n\n");
    }

    @Override
    public void parrafo(String texto) {
        sb.append(texto).append("\n\n");
    }

    @Override
    public void tabla(List<String> cabecera, List<List<String>> filas) {
        sb.append(fila(cabecera));
        sb.append("|");
        for (int i = 0; i < cabecera.size(); i++) {
            sb.append(" --- |");
        }
        sb.append("\n");
        for (List<String> f : filas) {
            sb.append(fila(f));
        }
        sb.append("\n");
    }

    @Override
    public void pie(String texto) {
        sb.append("---\n*").append(texto).append("*\n");
    }

    @Override
    public void saltoDePagina(int numero) {
        sb.append("---\n\n");
    }

    @Override
    public String finalizar() {
        return sb.toString();
    }

    private String fila(List<String> celdas) {
        StringBuilder fila = new StringBuilder("|");
        for (String celda : celdas) {
            fila.append(" ").append(celda.replace("|", "\\|")).append(" |");
        }
        return fila.append("\n").toString();
    }
}
