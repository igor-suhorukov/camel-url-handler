package com.github.igorsuhorukov.url.handler.camel;

import org.apache.camel.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.concurrent.TimeUnit;

public class Handler extends URLStreamHandler {

    private static final int DEFAULT_DURATION = Integer.parseInt(System.getProperty("camelComponentTimeout","180"));
    private HandleRequest<InputStream> camelStreamHandler = new HandleRequest<>();
    protected RegistryStream registry = new RegistryStream();

    private InputStream extractContent(TypeConverter typeConverter, Endpoint endpoint){

        PollingConsumer pollingConsumer = null;
        try {
            pollingConsumer = endpoint.createPollingConsumer();
            pollingConsumer.start();
            Exchange exchange = pollingConsumer.receive(TimeUnit.SECONDS.toMillis(DEFAULT_DURATION));
            return extractContent(typeConverter, exchange);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(pollingConsumer!=null) {
                    pollingConsumer.stop();
                }
            } catch (Exception ignore) {
                //ignore
            }
        }
    }

    private InputStream extractContent(TypeConverter typeConverter, Exchange exchange) {
        byte[] exchangePayload;
        Message exchangeOut = exchange.getOut();
        Object exchangeOutBody = exchangeOut.getBody();
        if(exchangeOutBody!=null) {
            exchangePayload = typeConverter.convertTo(byte[].class, exchangeOutBody);
        } else {
            exchangePayload = typeConverter.convertTo(byte[].class, exchange.getIn().getBody());
        }
        return new ByteArrayInputStream(exchangePayload);
    }


    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new URLConnection(url) {
            @Override
            public void connect() throws IOException {
                //do nothing on connection event
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return camelStreamHandler.handleRequest(url, registry, (camelContext, endpoint) ->
                        extractContent(camelContext.getTypeConverter(), endpoint));
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                if(url.toExternalForm().endsWith(":/registry")){
                    return registry;
                }
                return new ByteArrayOutputStream(){
                    @Override
                    public void close() throws IOException {
                        camelStreamHandler.handleRequest(url, registry, (camelContext, endpoint) -> {
                            Exception exception = null;
                            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
                            try {
                                producerTemplate.sendBody(endpoint, toByteArray());
                            } catch (Exception ex){
                                exception = ex;
                            } finally {
                                try {
                                    producerTemplate.stop();
                                } catch (Exception e) {
                                    if(exception == null){
                                        exception = e;
                                    } else {
                                        exception.addSuppressed(e);
                                    }
                                }
                            }
                            if(exception==null) {
                                return null;
                            } else {
                                throw new RuntimeException(exception);
                            }
                        });
                    }
                };
            }
        };
    }
}
