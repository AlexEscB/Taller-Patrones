package renderizado;


public enum Formato {
    PDF("PDF"),
    HTML("HTML"),
    MARKDOWN("Markdown");

    private final String nombre;

    Formato(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public RenderizadorEngine crearEngine() {
        switch (this) {
            case PDF:
                return new PdfRenderEngine();
            case HTML:
                return new HtmlRenderEngine();
            default:
                return new MarkdownRenderEngine();
        }
    }
}
