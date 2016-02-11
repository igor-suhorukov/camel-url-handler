package com.github.igorsuhorukov.url.handler.camel.classloader;

import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader;

import java.net.URL;
import java.util.List;

public class ClassLoaderUtils {

    public static ClassLoader loadModule(String moduleName) throws Exception {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        List<URL> artifactUrlsCollection = MavenClassLoader.usingCentralRepo().getArtifactUrlsCollection(moduleName, null);
        Thread.currentThread().setContextClassLoader(new ModuleClassLoader(artifactUrlsCollection.toArray(new URL[artifactUrlsCollection.size()]), contextClassLoader));
        return contextClassLoader;
    }

    public static void restoreCtxClassloader(ClassLoader contextClassLoader) {
        Thread.currentThread().setContextClassLoader(contextClassLoader);
    }
}
