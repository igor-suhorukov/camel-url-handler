package com.github.igorsuhorukov.url.handler.camel.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class ModuleClassLoader extends URLClassLoader{

    public ModuleClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ModuleClassLoader(URL[] urls) {
        super(urls);
    }

    public ModuleClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    protected void addURL(URL url) {
        super.addURL(url);
    }
}
