// Patrón implementado: Bridge
package renderizado;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Encabezado;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Tabla;
import coordinacion_exportacion.model.Texto;


import java.util.List;

public class HtmlRenderEngine implements RenderizadorEngine {
    private StringBuilder sb = new StringBuilder();

    @Override
    public String getFormato() {
        return "HTML";
    }

    @Override
    public String getExtension() {
        return "html";
    }

    @Override
    public void iniciar(String titulo) {
        sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n");
        sb.append("<meta charset=\"UTF-8\">\n");
        sb.append("<title>").append(escapar(titulo)).append("</title>\n");
        sb.append("</head>\n<body>\n");
    }

    @Override
    public void encabezado(String texto, int nivel, String icono) {
        int h = Math.min(Math.max(nivel, 1), 6);
        sb.append("<h").append(h).append(">");
        if (icono != null) {
            sb.append("<img src=\"").append(icono).append("\" alt=\"icono\" height=\"24\"> ");
        }
        sb.append(escapar(texto)).append("</h").append(h).append(">\n");
    }

    @Override
    public void parrafo(String texto) {
        sb.append("<p>").append(escapar(texto)).append("</p>\n");
    }

    @Override
    public void tabla(List<String> cabecera, List<List<String>> filas) {
        sb.append("<table border=\"1\">\n<tr>");
        for (String celda : cabecera) {
            sb.append("<th>").append(escapar(celda)).append("</th>");
        }
        sb.append("</tr>\n");
        for (List<String> fila : filas) {
            sb.append("<tr>");
            for (String celda : fila) {
                sb.append("<td>").append(escapar(celda)).append("</td>");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
    }

    @Override
    public void pie(String texto) {
        sb.append("<footer><small>").append(escapar(texto)).append("</small></footer>\n");
    }

    @Override
    public void saltoDePagina(int numero) {
        sb.append("<hr>\n");
    }

    @Override
    public String finalizar() {
        sb.append("</body>\n</html>\n");
        return sb.toString();
    }

    private String escapar(String texto) {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
