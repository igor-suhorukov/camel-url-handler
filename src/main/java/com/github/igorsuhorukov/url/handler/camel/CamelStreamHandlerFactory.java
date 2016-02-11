package com.github.igorsuhorukov.url.handler.camel;

import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader;
import com.github.igorsuhorukov.url.handler.camel.classloader.ClassLoaderUtils;
import com.github.igorsuhorukov.url.handler.camel.classloader.ModuleClassLoader;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CamelStreamHandlerFactory implements java.net.URLStreamHandlerFactory{

    public static final String CAMEL_PROTOCOL = "camel";
    private static final int DEFAULT_DURATION = Integer.parseInt(System.getProperty("camelComponentTimeout","180"));

    public CamelStreamHandlerFactory() throws IOException {
    }

    public URLStreamHandler createURLStreamHandler(String protocol) {
        if(CAMEL_PROTOCOL.equals(protocol)){
            return new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    return new URLConnection(url) {
                        @Override
                        public void connect() throws IOException {
                        }

                        @Override
                        public InputStream getInputStream() throws IOException {
                            try {
                                String path = getPath(url);
                                String protocolArtifact = getArtifact(getProtocol(path));
                                ClassLoader contextClassLoader = ClassLoaderUtils.loadModule(protocolArtifact);
                                try {
                                    return new ByteArrayInputStream(getPayload(path));
                                } finally {
                                    ClassLoaderUtils.restoreCtxClassloader(contextClassLoader);
                                }
                            } catch (Exception e) {
                                throw new IOException(e);
                            }
                        }
                    };
                }

                private String getPath(URL url) {
                    String path = url.getFile();
                    path = path!=null && path.startsWith(CAMEL_PROTOCOL) ? path.substring(CAMEL_PROTOCOL.length()+1) : path;
                    return path!=null && path.startsWith("/") && path.length()>1 ? path.substring(1) : path;
                }

                private String getProtocol(String path){
                    if(path!=null && path.indexOf(':')>0){
                        return path.substring(0,path.indexOf(':'));
                    }
                    throw new IllegalArgumentException("Unknown Apache Camel subprotocol");
                }
            };
        }
        return null;
    }

    private String getArtifact(String protocol) {
        String module = CamelSubprotocols.PROTOCOL_MAPPING.get(protocol);
        return String.format("org.apache.camel:%s:2.16.2", module);
    }


    private byte[] getPayload(String path) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        TypeConverter typeConverter = camelContext.getTypeConverter();
        Endpoint endpoint = camelContext.getEndpoint(path);
        PollingConsumer pollingConsumer = endpoint.createPollingConsumer();
        byte[] exchangePayload;
        try {
            Exchange exchange = pollingConsumer.receive(TimeUnit.SECONDS.toMillis(DEFAULT_DURATION));
            Message exchangeOut = exchange.getOut();
            Object exchangeOutBody = exchangeOut.getBody();
            exchangePayload = typeConverter.convertTo(byte[].class, exchangeOutBody);
        } finally {
            try {
                pollingConsumer.stop();
            } catch (Exception ignore) {
            }
        }
        return exchangePayload;
    }
}
