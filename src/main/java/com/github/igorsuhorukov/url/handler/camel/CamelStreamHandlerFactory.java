package com.github.igorsuhorukov.url.handler.camel;

import java.net.URLStreamHandler;

public class CamelStreamHandlerFactory implements java.net.URLStreamHandlerFactory{

    public static final String CAMEL_PROTOCOL = "camel";

    public URLStreamHandler createURLStreamHandler(String protocol) {
        if(CAMEL_PROTOCOL.equals(protocol)){
            return new Handler();
        }
        return null;
    }
}
