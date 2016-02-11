package com.github.igorsuhorukov.url.handler.vfs;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class CamelStreamHandlerFactory implements java.net.URLStreamHandlerFactory{

    public static final String CAMEL_PROTOCOL = "camel";

    public CamelStreamHandlerFactory() throws IOException {
    }

    public URLStreamHandler createURLStreamHandler(String protocol) {
        if(CAMEL_PROTOCOL.equals(protocol)){
            return new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    String path = getPath(url);
                    return null;
                }

                private String getPath(URL url) {
                    String path = url.getFile();
                    path = path!=null && path.startsWith(CAMEL_PROTOCOL) ? path.substring(CAMEL_PROTOCOL.length()+1) : path;
                    return path!=null && path.startsWith("/") && path.length()>1 ? path.substring(1) : path;
                }
            };
        }
        return null;
    }
}
