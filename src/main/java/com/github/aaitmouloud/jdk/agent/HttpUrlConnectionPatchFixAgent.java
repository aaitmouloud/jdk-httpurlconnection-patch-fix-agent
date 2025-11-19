package com.github.aaitmouloud.jdk.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.Instrumentation;
import java.net.HttpURLConnection;

public class HttpUrlConnectionPatchFixAgent {

    private static final boolean DISABLE_LOGGING;
    private static final String HTTP_URL_CONNECTION_CLASS_NAME = HttpURLConnection.class.getCanonicalName();

    static {
        final var disableLoggingProperty = System.getProperty("HttpUrlConnectionPatchFixAgent:disableLogging");
        DISABLE_LOGGING = disableLoggingProperty != null && !"false".equalsIgnoreCase(disableLoggingProperty);
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        try {
            log("Starting PATCH support injection to " + HTTP_URL_CONNECTION_CLASS_NAME);

            final ClassPool pool = ClassPool.getDefault();
            pool.appendSystemPath();

            final CtClass httpUrlConnectionCtClass = pool.get(HTTP_URL_CONNECTION_CLASS_NAME);
            final CtMethod methodToFix = httpUrlConnectionCtClass.getDeclaredMethod("setRequestMethod");

            methodToFix.insertBefore("""
                    {\s
                        if ($1.equals("PATCH")) {\s
                           if (connected) {\s
                               throw new java.net.ProtocolException("Can't reset method: already connected");\s
                           }\s
                           this.method = "PATCH"; return;\s
                        }\s
                    }""");

            final byte[] byteCode = httpUrlConnectionCtClass.toBytecode();
            httpUrlConnectionCtClass.detach();

            inst.redefineClasses(new java.lang.instrument.ClassDefinition(HttpURLConnection.class, byteCode));

            log("PATCH support injected successfully to " + HTTP_URL_CONNECTION_CLASS_NAME);
        } catch (Exception e) {
            log("Error while injecting PATCH support to" + HTTP_URL_CONNECTION_CLASS_NAME);
            e.printStackTrace();
        }
    }

    private static void log(String message) {
        if (DISABLE_LOGGING) {
            System.out.println("[" + HttpUrlConnectionPatchFixAgent.class.getName() + "]" + message);
        }
    }
}
