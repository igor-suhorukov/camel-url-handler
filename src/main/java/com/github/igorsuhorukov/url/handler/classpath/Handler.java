package com.github.igorsuhorukov.url.handler.classpath;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        URL resource = getResource(url, getClass().getClassLoader());
        if (resource != null){
            return resource.openConnection();
        } else {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if(classLoader==null){
                return null;
            }
            URL contextResource = getResource(url, classLoader);
            if(contextResource==null){
                return null;
            }
            return contextResource.openConnection();
        }
    }

    private URL getResource(URL url, ClassLoader classLoader) {
        return classLoader.getResource(url.getPath());
    }
}
