package com.github.igorsuhorukov.url.handler.camel;

import java.net.URLStreamHandler;

public class CamelHandlerProvider extends java.net.spi.URLStreamHandlerProvider{

    private static final CamelStreamHandlerFactory CAMEL_STREAM_HANDLER_FACTORY = new CamelStreamHandlerFactory();

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return CAMEL_STREAM_HANDLER_FACTORY.createURLStreamHandler(protocol);
    }
}
