package com.github.aaitmouloud.jdk.agent;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpUrlConnectionPatchFixAgentIT {

    public static Stream<Arguments> methods() {
        return Stream.of( "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE", "PATCH")
                .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("methods")
    void testMethodIsAccepted(String method) throws Exception {
        URL url = new URI("http://example.com").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);

        assertEquals(method, connection.getRequestMethod());
    }

}