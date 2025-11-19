# HttpUrlConnection PATCH method fix Java agent
[![CI](https://github.com/aaitmouloud/jdk-httpurlconnection-patch-fix-agent/actions/workflows/build.yml/badge.svg)](https://github.com/aaitmouloud/jdk-httpurlconnection-patch-fix-agent/actions/workflows/build.yml)

This library defines a [Java Agent](https://docs.oracle.com/en/java/javase/25/docs/api/java.instrument/java/lang/instrument/package-summary.html),
using [JavaAssist](https://github.com/jboss-javassist/javassist), that patches de `java.net.HttpUrlConnection` in order to allow
the usage of the `PATCH` HTTP method in the context of the this class and its children.

`java.net.HttpUrlConnection` does not support the HTTP `PATCH` verb, even if the [RFC 5789](https://datatracker.ietf.org/doc/html/rfc5789) 
allows it. There is a [bug listed on OpenJDK](https://bugs.openjdk.org/browse/JDK-8207840) that has been closed and won't be fixed
because Java encourages to migrate to the more modern new Java HTTP Client API `java.net.http`.

If you can do that, I also encourage you to do so.

# Usage
Add command-line argument to use agent

```*
-javaagent:/path/to/jar/jdk-httpurlconnection-patch-fix-agent.jar
```

# Compatbility
This plugin is compiled using JDK 25.

## Contributors
- @aaitmouloud