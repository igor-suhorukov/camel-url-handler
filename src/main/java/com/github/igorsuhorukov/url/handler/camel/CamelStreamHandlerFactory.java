package com.github.igorsuhorukov.url.handler.camel;

import com.github.igorsuhorukov.url.handler.camel.classloader.ClassLoaderUtils;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CamelStreamHandlerFactory implements java.net.URLStreamHandlerFactory{

    public static final String CAMEL_PROTOCOL = "camel";
    private static final int DEFAULT_DURATION = Integer.parseInt(System.getProperty("camelComponentTimeout","180"));
    private ConcurrentHashMap<String, ClassLoader> camelModules = new ConcurrentHashMap<>();

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
                                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                                ClassLoader moduleClassLoader = getModuleClassloader(protocolArtifact);
                                ClassLoaderUtils.restoreCtxClassloader(moduleClassLoader);
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

    private ClassLoader getModuleClassloader(String protocolArtifact) throws Exception {
        return camelModules.computeIfAbsent(protocolArtifact, ClassLoaderUtils::loadModule);
    }

    private String getArtifact(String protocol) {
        String module = CamelSubprotocols.PROTOCOL_MAPPING.get(protocol);
        if(module!=null){
            return module.startsWith("/") ? module.substring(1) : String.format("org.apache.camel:%s:2.20.0", module);
        } else {
            throw new IllegalArgumentException(String.format("module for protocol '%s' not found", protocol));
        }
    }


    private byte[] getPayload(String path) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.start();
        loadComponent(path, camelContext);
        byte[] exchangePayload;
        try {
            Endpoint endpoint = camelContext.getEndpoint(path);
            endpoint.start();
            try {
                exchangePayload = extractContent(camelContext.getTypeConverter(), endpoint);
            } finally {
                endpoint.stop();
            }
        } finally {
            camelContext.stop();
        }
        return exchangePayload;
    }

    private void loadComponent(String path, CamelContext camelContext) {
        String[] protocol = path.split(":");
        if(protocol.length > 0) camelContext.getComponent(protocol[0], true);
    }

    private byte[] extractContent(TypeConverter typeConverter, Endpoint endpoint) throws Exception {
        byte[] exchangePayload;
        PollingConsumer pollingConsumer = endpoint.createPollingConsumer();
        pollingConsumer.start();
        try {
            Exchange exchange = pollingConsumer.receive(TimeUnit.SECONDS.toMillis(DEFAULT_DURATION));
            exchangePayload = extractContent(typeConverter, exchange);
        } finally {
            try {
                pollingConsumer.stop();
            } catch (Exception ignore) {
            }
        }
        return exchangePayload;
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
}
