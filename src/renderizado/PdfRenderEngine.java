// Patrón implementado: Bridge
package renderizado;

import coordinacion_exportacion.model.Bloque;
import coordinacion_exportacion.model.Contenido;
import coordinacion_exportacion.model.Encabezado;
import coordinacion_exportacion.model.Parrafo;
import coordinacion_exportacion.model.Tabla;
import coordinacion_exportacion.model.Texto;


import java.util.List;

public class PdfRenderEngine implements RenderizadorEngine {
    private StringBuilder sb = new StringBuilder();

    @Override
    public String getFormato() {
        return "PDF";
    }

    @Override
    public String getExtension() {
        return "pdf.txt";
    }

    @Override
    public void iniciar(String titulo) {
        sb = new StringBuilder();
        sb.append("%PDF-SIMULADO 1.0\n");
        sb.append("% Titulo: ").append(titulo).append("\n");
        sb.append("--- PAGINA 1 ---\n");
    }

    @Override
    public void encabezado(String texto, int nivel, String icono) {
        if (icono != null) {
            sb.append("/Img ").append(icono).append(" Do\n");
        }
        int tamano = nivel == 1 ? 20 : 14;
        sb.append("BT /F-Bold ").append(tamano).append(" Tf (").append(texto).append(") Tj ET\n");
    }

    @Override
    public void parrafo(String texto) {
        sb.append("BT /F1 11 Tf (").append(texto).append(") Tj ET\n");
    }

    @Override
    public void tabla(List<String> cabecera, List<List<String>> filas) {
        sb.append("TABLA [").append(String.join(" | ", cabecera)).append("]\n");
        for (List<String> fila : filas) {
            sb.append("FILA  [").append(String.join(" | ", fila)).append("]\n");
        }
    }

    @Override
    public void pie(String texto) {
        sb.append("BT /F1 9 Tf (").append(texto).append(") Tj ET\n");
    }

    @Override
    public void saltoDePagina(int numero) {
        sb.append("--- PAGINA ").append(numero).append(" ---\n");
    }

    @Override
    public String finalizar() {
        sb.append("%%EOF\n");
        return sb.toString();
    }
}
