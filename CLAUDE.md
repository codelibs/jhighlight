# JHighlight

Embeddable pure Java syntax highlighting library (Java, Groovy, JS, C++, XML, HTML).
Fork of original jhighlight with multi-byte character fixes.

## Commands

```bash
mvn clean install        # Build and install
mvn test                 # Run tests
mvn jacoco:report        # Generate coverage report (target/site/jacoco/)
```

## Architecture

```
src/main/java/
  org/codelibs/jhighlight/
    highlighter/          # Language-specific syntax highlighters
    renderer/             # XHTML output renderers (XhtmlRenderer, XhtmlRendererFactory)
    servlet/              # HighlightFilter for web integration
    tools/                # StringUtils, FileUtils, ExceptionUtils
    fastutil/             # Inlined fastutil data structures (chars, ints, objects)
  com/uwyn/jhighlight/    # Legacy package (backward compatibility)
```

## Gotchas

- Dual package structure: `org.codelibs.jhighlight` (preferred) and `com.uwyn.jhighlight` (legacy compat)
- Java 17 source/target level with Jakarta Servlet 6.1 (requires Java 17+ runtime)
- `fastutil/` is inlined (not a dependency) - do not add fastutil as a Maven dependency
- Servlet API is `provided` scope (Jakarta Servlet 6.1) - only available at compile time
- Release uses maven-release-plugin + GPG signing + Sonatype Central publishing
