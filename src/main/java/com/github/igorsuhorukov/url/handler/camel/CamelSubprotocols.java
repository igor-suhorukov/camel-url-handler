package com.github.igorsuhorukov.url.handler.camel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CamelSubprotocols {

    static final Map<String, String> PROTOCOL_MAPPING = new HashMap<>();

    static {
        Properties properties = new Properties();
        try {
            properties.load(CamelSubprotocols.class.getResourceAsStream("/protocols.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException("protocols.properties", e);
        }
        properties.forEach((o, o2) -> PROTOCOL_MAPPING.put(o.toString(),o2.toString()));
    }

    private CamelSubprotocols() {
    }
}
