# Security Policy

## Supported Versions

| Version | Supported          |
| ------- | ------------------ |
| 1.1.x   | :white_check_mark: |
| < 1.1   | :x:                |

## Reporting a Vulnerability

If you discover a security vulnerability in JHighlight, please report it through
[GitHub Security Advisories](https://github.com/codelibs/jhighlight/security/advisories/new).

Please do **not** open a public issue for security vulnerabilities. Using GitHub Security Advisories
allows us to privately discuss and fix the issue before public disclosure.

## Verifying Artifact Signatures

All release artifacts published to Maven Central are signed with GPG.
You can verify the signature of any artifact using the following key:

| Property    | Value                                            |
| ----------- | ------------------------------------------------ |
| Fingerprint | `C76701D6BD7E088EFFA4548A0DC3968BA181B331`       |
| Owner       | CodeLibs, Inc. \<info@codelibs.co\>              |
| Key type    | RSA 3072-bit                                     |
| Expires     | 2027-04-13                                       |

### Verification steps

```bash
# 1. Import the public key from a keyserver
gpg --keyserver keyserver.ubuntu.com --recv-keys C76701D6BD7E088EFFA4548A0DC3968BA181B331

# 2. Download the artifact and its signature
curl -O https://repo1.maven.org/maven2/org/codelibs/jhighlight/1.1.1/jhighlight-1.1.1.jar
curl -O https://repo1.maven.org/maven2/org/codelibs/jhighlight/1.1.1/jhighlight-1.1.1.jar.asc

# 3. Verify
gpg --verify jhighlight-1.1.1.jar.asc jhighlight-1.1.1.jar
```

### Gradle dependency verification

To use this key with [Gradle dependency verification](https://docs.gradle.org/current/userguide/dependency_verification.html),
add the following trusted key to your `verification-metadata.xml`:

```xml
<trusted-key id="C76701D6BD7E088EFFA4548A0DC3968BA181B331" group="org.codelibs"/>
```
