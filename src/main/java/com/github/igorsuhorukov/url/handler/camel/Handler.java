package com.github.igorsuhorukov.url.handler.camel;

import org.apache.camel.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.concurrent.TimeUnit;

public class Handler extends URLStreamHandler {

    private static final int DEFAULT_DURATION = Integer.parseInt(System.getProperty("camelComponentTimeout","180"));

    private InputStream extractContent(TypeConverter typeConverter, Endpoint endpoint){

        PollingConsumer pollingConsumer = null;
        try {
            pollingConsumer = endpoint.createPollingConsumer();
            pollingConsumer.start();
            Exchange exchange = pollingConsumer.receive(TimeUnit.SECONDS.toMillis(DEFAULT_DURATION));
            return new ByteArrayInputStream(extractContent(typeConverter, exchange));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pollingConsumer.stop();
            } catch (Exception ignore) {
                //ignore
            }
        }
    }

    private byte[] extractContent(TypeConverter typeConverter, Exchange exchange) {
        byte[] exchangePayload;
        Message exchangeOut = exchange.getOut();
        Object exchangeOutBody = exchangeOut.getBody();
        if(exchangeOutBody!=null) {
            exchangePayload = typeConverter.convertTo(byte[].class, exchangeOutBody);
        } else {
            exchangePayload = typeConverter.convertTo(byte[].class, exchange.getIn().getBody());
        }
        return exchangePayload;
    }


    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new URLConnection(url) {
            @Override
            public void connect() throws IOException {
                //do nothing on connect invocation
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new HandleRequest<>((camelContext, endpoint) ->
                        extractContent(camelContext.getTypeConverter(), endpoint)).handleRequest(url);
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                return new ByteArrayOutputStream(){
                    @Override
                    public void close() throws IOException {
                        new HandleRequest<>((camelContext, endpoint) -> {
                            camelContext.createProducerTemplate().sendBody(endpoint, toByteArray());
                            return null;
                        }).handleRequest(url);
                    }
                };
            }
        };
    }
}
