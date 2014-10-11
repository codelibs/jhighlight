JHighlight
==================

## Overview

JHighlight is an embeddable pure Java syntax highlighting library that supports Java, HTML, XHTML, XML and LZX languages and outputs to XHTML.
It also supports RIFE templates tags and highlights them clearly so that you can easily identify the difference between your RIFE markup and the actual marked up source.

This project is forked from https://jhighlight.dev.java.net/ to fix several bugs.

## Reference

The original jhighlight handles multi-byte characters as garbled one. To solve this problem, replace with CodeLibs jhighlight.

### Tika

Tike uses jhighlight to parse source code files, such as .java.
If a source code file has a multi-byte chracter, it becomes a garbled one.
To avoid this problem, change your pom.xml to:

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
      <version>1.0.1</version>
    </dependency>

