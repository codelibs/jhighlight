# JHighlight

[![Java CI with Maven](https://github.com/codelibs/jhighlight/actions/workflows/maven.yml/badge.svg)](https://github.com/codelibs/jhighlight/actions/workflows/maven.yml)
[![Maven Central](https://img.shields.io/maven-central/v/org.codelibs/jhighlight.svg)](https://central.sonatype.com/artifact/org.codelibs/jhighlight)
[![License](https://img.shields.io/badge/License-CDDL%201.0-blue.svg)](http://www.opensource.org/licenses/cddl1.php)
[![License](https://img.shields.io/badge/License-LGPL%202.1-blue.svg)](http://www.opensource.org/licenses/lgpl-license.php)

A pure Java syntax highlighting library that generates XHTML output from source code. This is a maintained fork of the original jhighlight project with critical bug fixes, particularly for multi-byte character handling.

## Features

- **Pure Java Implementation** - No external dependencies for highlighting
- **Multiple Language Support** - Java, Groovy, JavaScript, C++, XML, HTML, XHTML, LZX
- **XHTML Output** - Clean, standards-compliant output
- **Multi-byte Character Support** - Properly handles UTF-8 and other encodings
- **Command-line Tool** - Batch process files and directories
- **Servlet Filter** - Easy integration with web applications
- **RIFE Template Support** - Highlights RIFE template tags

## Installation

### Maven

```xml
<dependency>
    <groupId>org.codelibs</groupId>
    <artifactId>jhighlight</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'org.codelibs:jhighlight:1.1.0'
```

## Quick Start

### Programmatic Usage

```java
import org.codelibs.jhighlight.renderer.XhtmlRendererFactory;
import org.codelibs.jhighlight.renderer.Renderer;
import java.io.*;

// Get a renderer for Java code
Renderer renderer = XhtmlRendererFactory.getRenderer("java");

// Highlight code from an input stream
InputStream input = new FileInputStream("Example.java");
OutputStream output = new FileOutputStream("Example.java.html");

renderer.highlight("Example.java", input, output, "UTF-8", false);
```

### Command-line Usage

```bash
# Highlight a single file
java -jar jhighlight.jar MyClass.java

# Highlight all files in a directory
java -jar jhighlight.jar src/

# Specify output directory and encoding
java -jar jhighlight.jar -d output -e UTF-8 src/

# Generate fragments instead of complete HTML documents
java -jar jhighlight.jar --fragment MyClass.java
```

### Command-line Options

| Option | Description |
|--------|-------------|
| `--verbose` | Output progress messages |
| `--fragment` | Generate HTML fragments instead of complete documents |
| `-d <dir>` | Specify destination directory for output files |
| `-e <encoding>` | Specify file encoding (default: UTF-8) |

## Supported Languages

| Language | File Extensions | Renderer Type |
|----------|----------------|---------------|
| Java | `.java` | `java` |
| Groovy | `.groovy` | `groovy` |
| BeanShell | `.bsh`, `.beanshell` | `beanshell` / `bsh` |
| JavaScript | `.js` | (via factory) |
| C++ | `.cpp`, `.cxx`, `.c++` | `cpp` / `cxx` / `c++` |
| XML | `.xml` | `xml` |
| HTML | `.html`, `.htm` | `html` |
| XHTML | `.xhtml` | `xhtml` |
| LZX | `.lzx` | `lzx` |

### Getting Available Renderers

```java
import org.codelibs.jhighlight.renderer.XhtmlRendererFactory;

Set<String> supportedTypes = XhtmlRendererFactory.getSupportedTypes();
System.out.println("Supported types: " + supportedTypes);
```

## Migration from Original JHighlight

### Why This Fork?

The original jhighlight library (from jhighlight.dev.java.net) had critical issues with multi-byte character handling, causing garbled output for source files containing UTF-8 characters, Japanese, Chinese, Korean, and other non-ASCII text. This fork fixes those issues and is actively maintained.

### Migrating from Apache Tika

If you're using Apache Tika (which depends on the original jhighlight), you'll need to exclude the old dependency and include this version:

```xml
<dependency>
    <groupId>org.apache.tika</groupId>
    <artifactId>tika-parsers</artifactId>
    <version>1.6</version>
    <exclusions>
        <exclusion>
            <groupId>com.uwyn</groupId>
            <artifactId>jhighlight</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.codelibs</groupId>
    <artifactId>jhighlight</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Package Changes

This fork maintains the original package structure with additions:

- Original packages: `com.uwyn.jhighlight.*`
- New packages: `org.codelibs.jhighlight.*`

Most classes are now available in the `org.codelibs.jhighlight` package for better namespace management.

## Building from Source

### Requirements

- Java 8 or higher
- Maven 3.6 or higher

### Build Steps

```bash
# Clone the repository
git clone https://github.com/codelibs/jhighlight.git
cd jhighlight

# Build with Maven
mvn clean install

# Run tests
mvn test

# Generate code coverage report
mvn jacoco:report
```

The compiled JAR will be available in the `target/` directory.

## Advanced Usage

### Using as a Servlet Filter

```java
import org.codelibs.jhighlight.servlet.HighlightFilter;

// Configure in web.xml
<filter>
    <filter-name>HighlightFilter</filter-name>
    <filter-class>org.codelibs.jhighlight.servlet.HighlightFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>HighlightFilter</filter-name>
    <url-pattern>*.java</url-pattern>
</filter-mapping>
```

### Custom Styling

The generated XHTML includes CSS classes that you can style:

```html
<span class="java_keyword">public</span>
<span class="java_type">class</span>
<span class="java_plain">Example</span>
```

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

### Development Guidelines

1. Follow existing code style and conventions
2. Add unit tests for new features
3. Ensure all tests pass: `mvn test`
4. Update documentation as needed

## License

This project is dual-licensed under:

- [Common Development and Distribution License (CDDL) v1.0](http://www.opensource.org/licenses/cddl1.php)
- [GNU Lesser General Public License (LGPL) v2.1 or later](http://www.opensource.org/licenses/lgpl-license.php)

You may choose either license to govern your use of this software.

## Acknowledgments

- Original JHighlight project by Geert Bevin
- Maintained by [CodeLibs Project](https://www.codelibs.org/)
- Original project: https://jhighlight.dev.java.net/

## Links

- **Maven Central**: https://central.sonatype.com/artifact/org.codelibs/jhighlight
- **GitHub Repository**: https://github.com/codelibs/jhighlight
- **Issue Tracker**: https://github.com/codelibs/jhighlight/issues
- **CodeLibs Project**: https://www.codelibs.org/

