package com.github.igorsuhorukov.url.handler.camel;

import com.github.igorsuhorukov.url.handler.camel.classloader.ClassLoaderUtils;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class HandleRequest<T> {

    private ConcurrentHashMap<String, ClassLoader> camelModules = new ConcurrentHashMap<>();

    public T handleRequest(URL url, CamelAction<T> camelAction) throws IOException {
        try {
            String path = getPath(url);
            String protocolArtifact = getArtifact(getProtocol(path));
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            ClassLoader moduleClassLoader = getModuleClassLoader(protocolArtifact);
            Thread.currentThread().setContextClassLoader(moduleClassLoader);
            try {
                CamelContext camelContext = new DefaultCamelContext();
                camelContext.start();
                loadComponent(path, camelContext);
                try {
                    String endpointUrl = path.replaceAll("\\|-.*\\?", "?");
                    Endpoint endpoint = camelContext.getEndpoint(endpointUrl);
                    endpoint.start();
                    try {
                        return camelAction.processRequest(camelContext, endpoint);
                    } finally {
                        endpoint.stop();
                    }
                } finally {
                    camelContext.stop();
                }
            } finally {
                Thread.currentThread().setContextClassLoader(contextClassLoader);
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
    private String getPath(URL url) {
        String path = url.getFile();
        path = path != null && path.startsWith(CamelStreamHandlerFactory.CAMEL_PROTOCOL) ? path.substring(CamelStreamHandlerFactory.CAMEL_PROTOCOL.length() + 1) : path;
        return path != null && path.startsWith("/") && path.length() > 1 ? path.substring(1) : path;
    }

    private String getProtocol(String path) {
        if (path != null && path.indexOf(':') !=-1) {
            return path.substring(0, path.indexOf(':'));
        }
        throw new IllegalArgumentException("Unknown Apache Camel subprotocol");
    }

    private ClassLoader getModuleClassLoader(String protocolArtifact) {
        ClassLoader moduleClassLoader;
        if (Boolean.getBoolean("url.protocol.handler.use-classpath")) {
            moduleClassLoader = org.apache.camel.Handler.class.getClassLoader();
        } else {
            ClassLoader result;
            if(Boolean.getBoolean("url.protocol.handler.disable-cache")) {
                result = ClassLoaderUtils.loadModule(protocolArtifact);
            } else {
                result = camelModules.computeIfAbsent(protocolArtifact, ClassLoaderUtils::loadModule);
            }
            moduleClassLoader = result;
        }
        return moduleClassLoader;
    }

    private String getArtifact(String protocol) {
        String module = CamelSubprotocols.PROTOCOL_MAPPING.get(protocol);
        if(module!=null){
            return module.startsWith("/") ? module.substring(1) : String.format("org.apache.camel:%s:3.3.0", module);
        } else {
            throw new IllegalArgumentException(String.format("module for protocol '%s' not found", protocol));
        }
    }


    private void loadComponent(String path, CamelContext camelContext) {
        String[] protocol = path.split(":");
        if(protocol.length > 0) camelContext.getComponent(protocol[0], true);
    }

}
