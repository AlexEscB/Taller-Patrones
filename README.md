# Motor de Documentos Inteligentes

Actividad de integración de patrones (Modelos de Programación, Ingeniería de Sistemas, Universidad Distrital).
Implementación en Java que integra **Bridge, Builder, Chain of Responsibility, Flyweight, Interpreter y Mediator**.

## Estructura de paquetes

```
src/
├── app/           Main y ExportadorPipeline (orquesta el flujo completo)
├── mediator/      Mediator: DocumentEditorMediator y componentes de la interfaz
├── builder/       Builder: DocumentBuilder, builders concretos, Director y Plantilla
├── flyweight/     Flyweight: Glifo, GlifoFactory y ElementoVisual (estado extrínseco)
├── model/         Estructura del documento: Contenido, Bloque, Texto y derivados
├── chain/         Chain of Responsibility: ProcesadorHandler y manejadores
├── interpreter/   Interpreter: Expresion, terminales, no terminales y parser
└── bridge/        Bridge: Documento (abstracción) y RenderizadorEngine (implementación)
```

## Cómo se integran los patrones

| Patrón | Dónde | Papel en el sistema |
|---|---|---|
| Mediator | `DocumentEditorMediator` | Recibe los eventos de `SelectorDeFormato`, `BarraDeHerramientasBuilder`, `VistaPrevia` y `BotonExportar`; reconfigura el Builder y el Renderizador. |
| Builder | `DocumentBuilder`, `ReporteEjecutivoBuilder`, `FacturaSimpleBuilder`, `DocumentDirector` | Ensambla el `Contenido` paso a paso, independiente del formato final. |
| Flyweight | `GlifoFactory`, `GlifoCaracter`, `GlifoIcono`, `ElementoVisual` | El Builder pide los glifos a la fábrica; el estado intrínseco se comparte y la posición, el color y la escala quedan en `ElementoVisual`. |
| Chain of Responsibility | `ValidadorSintaxis` → `Sanitizador` → `EvaluadorExpresiones` | Procesa el `Contenido` antes de renderizar; un error crítico interrumpe la cadena. |
| Interpreter | `ExpresionTerminal`, `ExpresionNoTerminal` (`Suma`, `Resta`, `Multiplicacion`, `Division`), `ParserExpresiones` | El `EvaluadorExpresiones` lo usa para calcular los marcadores `#{...}`. |
| Bridge | `Documento` (`DocumentoPaginado`, `DocumentoContinuo`) y `RenderizadorEngine` (`PdfRenderEngine`, `HtmlRenderEngine`, `MarkdownRenderEngine`) | Permite combinar cualquier tipo de documento con cualquier formato de salida. |

## Compilar y ejecutar

Linux / macOS:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out app.Main
```

Windows (PowerShell):

```powershell
mkdir out
Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName } | Out-File -Encoding ascii sources.txt
javac -encoding UTF-8 -d out "@sources.txt"
java -cp out app.Main
```

Requiere Java 11 o superior. Los archivos generados quedan en la carpeta `salida/`.
El PDF es simulado: se escribe como texto con operadores tipo PDF (`.pdf.txt`).

## Flujo de la demo (`app.Main`)

1. **Mediator**: se configura plantilla y formato a través de los componentes; el botón se habilita solo cuando hay ambos.
2. **Builder + Flyweight**: el Director arma el documento y los caracteres e iconos repetidos se reutilizan (se imprimen las estadísticas).
3. **Chain of Responsibility**: validación, sanitización y evaluación de fórmulas.
4. **Interpreter**: se evalúan `#{FECHA_ACTUAL}`, `#{PRECIO_BASE * CANTIDAD}` y `#{PRECIO_BASE * 1.19 - DESCUENTO}`.
5. **Bridge**: el mismo contenido se renderiza en PDF, HTML y Markdown.
6. Caso de error: un marcador sin cerrar interrumpe la cadena y el documento no se renderiza.

## Diagrama de clases UML

```mermaid
classDiagram
    %% ===== MEDIATOR =====
    class Mediator {
        <<interface>>
        +notificar(Componente emisor, Evento evento) void
    }
    class Componente {
        <<abstract>>
        #Mediator mediator
        +setMediator(Mediator m) void
    }
    class SelectorDeFormato {
        -Formato formato
        +seleccionar(Formato f) void
    }
    class BarraDeHerramientasBuilder {
        -Plantilla plantilla
        +seleccionarPlantilla(Plantilla p) void
    }
    class VistaPrevia {
        +mostrar(String mensaje) void
    }
    class BotonExportar {
        -boolean habilitado
        +hacerClick() void
    }
    class DocumentEditorMediator {
        -Plantilla plantilla
        -Formato formato
        -DocumentBuilder builder
        -RenderizadorEngine engine
        +notificar(Componente emisor, Evento evento) void
    }
    class Exportador {
        <<interface>>
        +exportar(Plantilla p, Formato f, DocumentBuilder b, RenderizadorEngine e) void
    }
    class ExportadorPipeline {
        +exportar(Plantilla p, Formato f, DocumentBuilder b, RenderizadorEngine e) void
    }

    %% ===== BUILDER =====
    class DocumentBuilder {
        <<interface>>
        +addHeader(String texto, int nivel) DocumentBuilder
        +addParagraph(String texto) DocumentBuilder
        +addTable(List~String~ cabecera, List~List~String~~ filas) DocumentBuilder
        +addFooter(String texto) DocumentBuilder
        +build() Contenido
    }
    class BaseDocumentBuilder {
        <<abstract>>
        #estiloEncabezado(int nivel) Estilo
        #estiloTexto() Estilo
        #iconoEncabezado() String
    }
    class ReporteEjecutivoBuilder
    class FacturaSimpleBuilder
    class DocumentDirector {
        +construir(Plantilla p, DocumentBuilder b) Contenido
    }
    class Plantilla {
        <<enumeration>>
        REPORTE_EJECUTIVO
        FACTURA_SIMPLE
        +crearBuilder(GlifoFactory f) DocumentBuilder
    }

    %% ===== FLYWEIGHT =====
    class Glifo {
        <<interface>>
        +getClave() String
        +getAncho() double
    }
    class GlifoCaracter {
        -char caracter
        -String tipografia
    }
    class GlifoIcono {
        -String nombre
        -String recurso
    }
    class GlifoFactory {
        -Map~String, Glifo~ cache
        +obtenerCaracter(char c, String tipografia) Glifo
        +obtenerIcono(String nombre) Glifo
    }
    class ElementoVisual {
        -double x
        -double y
        -String color
        -double escala
    }

    %% ===== MODELO DEL DOCUMENTO =====
    class Contenido {
        -String titulo
        +getTextos() List~Texto~
    }
    class Bloque {
        <<interface>>
        +getTextos() List~Texto~
    }
    class Encabezado
    class Parrafo
    class Tabla
    class Pie
    class Texto {
        -String contenido
        +setContenido(String nuevo) void
    }

    %% ===== CHAIN OF RESPONSIBILITY =====
    class ProcesadorHandler {
        <<abstract>>
        -ProcesadorHandler siguiente
        +setSiguiente(ProcesadorHandler s) ProcesadorHandler
        +procesar(ContextoProcesamiento c) void
        #manejar(ContextoProcesamiento c) void
    }
    class ValidadorSintaxis
    class Sanitizador
    class EvaluadorExpresiones
    class ContextoProcesamiento {
        -boolean detenido
        +errorCritico(String mensaje) void
    }

    %% ===== INTERPRETER =====
    class Expresion {
        <<interface>>
        +interpretar(ContextoInterprete c) Object
    }
    class ExpresionTerminal {
        -String token
        -boolean literal
    }
    class ExpresionNoTerminal {
        <<abstract>>
        #Expresion izquierda
        #Expresion derecha
        #operar(double a, double b) double
    }
    class Suma
    class Resta
    class Multiplicacion
    class Division
    class ContextoInterprete {
        -Map~String, Object~ variables
    }
    class ParserExpresiones {
        +parsear(String fuente) Expresion
    }

    %% ===== BRIDGE =====
    class Documento {
        <<abstract>>
        #RenderizadorEngine engine
        +renderizar() String
    }
    class DocumentoPaginado
    class DocumentoContinuo
    class RenderizadorEngine {
        <<interface>>
        +iniciar(String titulo) void
        +encabezado(String t, int n, String icono) void
        +parrafo(String t) void
        +tabla(List~String~ c, List~List~String~~ f) void
        +pie(String t) void
        +finalizar() String
    }
    class PdfRenderEngine
    class HtmlRenderEngine
    class MarkdownRenderEngine
    class Formato {
        <<enumeration>>
        PDF
        HTML
        MARKDOWN
        +crearEngine() RenderizadorEngine
    }

    %% ----- Mediator -----
    Mediator <|.. DocumentEditorMediator
    Componente <|-- SelectorDeFormato
    Componente <|-- BarraDeHerramientasBuilder
    Componente <|-- VistaPrevia
    Componente <|-- BotonExportar
    Componente --> Mediator : notifica
    DocumentEditorMediator o-- SelectorDeFormato
    DocumentEditorMediator o-- BarraDeHerramientasBuilder
    DocumentEditorMediator o-- VistaPrevia
    DocumentEditorMediator o-- BotonExportar
    DocumentEditorMediator --> Exportador : delega la exportacion
    Exportador <|.. ExportadorPipeline

    %% ----- Mediator configura Builder y Bridge -----
    DocumentEditorMediator --> DocumentBuilder : reconfigura
    DocumentEditorMediator --> RenderizadorEngine : reconfigura
    Plantilla ..> DocumentBuilder : crea
    Formato ..> RenderizadorEngine : crea

    %% ----- Builder -----
    DocumentBuilder <|.. BaseDocumentBuilder
    BaseDocumentBuilder <|-- ReporteEjecutivoBuilder
    BaseDocumentBuilder <|-- FacturaSimpleBuilder
    DocumentDirector --> DocumentBuilder : dirige
    BaseDocumentBuilder ..> Contenido : produce
    BaseDocumentBuilder --> GlifoFactory : pide glifos

    %% ----- Flyweight -----
    Glifo <|.. GlifoCaracter
    Glifo <|.. GlifoIcono
    GlifoFactory o-- Glifo : cache compartido
    ElementoVisual --> Glifo : estado intrinseco
    Texto o-- ElementoVisual : estado extrinseco
    Texto --> GlifoFactory

    %% ----- Modelo -----
    Contenido *-- Bloque
    Bloque <|.. Encabezado
    Bloque <|.. Parrafo
    Bloque <|.. Tabla
    Bloque <|.. Pie
    Bloque o-- Texto

    %% ----- Chain -----
    ProcesadorHandler <|-- ValidadorSintaxis
    ProcesadorHandler <|-- Sanitizador
    ProcesadorHandler <|-- EvaluadorExpresiones
    ProcesadorHandler o-- ProcesadorHandler : siguiente
    ProcesadorHandler ..> ContextoProcesamiento
    ContextoProcesamiento --> Contenido : procesa
    ContextoProcesamiento --> ContextoInterprete : variables
    ExportadorPipeline ..> ProcesadorHandler : arma la cadena
    ExportadorPipeline ..> DocumentDirector : construye
    ExportadorPipeline ..> Documento : renderiza

    %% ----- Interpreter -----
    Expresion <|.. ExpresionTerminal
    Expresion <|.. ExpresionNoTerminal
    ExpresionNoTerminal <|-- Suma
    ExpresionNoTerminal <|-- Resta
    ExpresionNoTerminal <|-- Multiplicacion
    ExpresionNoTerminal <|-- Division
    ExpresionNoTerminal o-- Expresion : operandos
    ParserExpresiones ..> Expresion : construye el arbol
    EvaluadorExpresiones ..> ParserExpresiones : usa
    EvaluadorExpresiones ..> Expresion : interpreta

    %% ----- Bridge -----
    Documento <|-- DocumentoPaginado
    Documento <|-- DocumentoContinuo
    Documento o-- RenderizadorEngine : puente
    Documento --> Contenido
    RenderizadorEngine <|.. PdfRenderEngine
    RenderizadorEngine <|.. HtmlRenderEngine
    RenderizadorEngine <|.. MarkdownRenderEngine
```

## Diagrama de secuencia del flujo completo

```mermaid
sequenceDiagram
    actor Usuario
    participant Med as DocumentEditorMediator
    participant Exp as ExportadorPipeline
    participant Dir as DocumentDirector
    participant Bld as DocumentBuilder
    participant Fab as GlifoFactory
    participant Cad as Cadena de handlers
    participant Int as Interpreter
    participant Doc as Documento
    participant Eng as RenderizadorEngine

    Usuario->>Med: elige plantilla (BarraDeHerramientasBuilder)
    Med->>Bld: crea el Builder de la plantilla
    Usuario->>Med: elige formato (SelectorDeFormato)
    Med->>Eng: crea el motor de renderizado
    Usuario->>Med: click en BotonExportar
    Med->>Exp: exportar(plantilla, formato, builder, engine)
    Exp->>Dir: construir(plantilla, builder)
    Dir->>Bld: addHeader, addParagraph, addTable, addFooter
    Bld->>Fab: obtenerCaracter / obtenerIcono
    Fab-->>Bld: glifos compartidos
    Bld-->>Exp: Contenido
    Exp->>Cad: procesar(contexto)
    Cad->>Cad: ValidadorSintaxis
    Cad->>Cad: Sanitizador
    Cad->>Int: EvaluadorExpresiones evalua los marcadores
    Int-->>Cad: valores calculados
    Cad-->>Exp: contexto (sin errores criticos)
    Exp->>Doc: renderizar()
    Doc->>Eng: encabezado, parrafo, tabla, pie
    Eng-->>Exp: salida en PDF, HTML o Markdown
```
