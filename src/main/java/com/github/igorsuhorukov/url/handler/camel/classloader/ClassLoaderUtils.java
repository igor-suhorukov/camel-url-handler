package com.github.igorsuhorukov.url.handler.camel.classloader;

import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader;
import org.apache.camel.CamelContext;

import java.net.URL;
import java.util.List;

public class ClassLoaderUtils {

    public static ClassLoader loadModule(String moduleName) throws Exception {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        List<URL> artifactUrlsCollection = MavenClassLoader.usingCentralRepo().getArtifactUrlsCollection(moduleName, null);
        URL[] classpath = artifactUrlsCollection.toArray(new URL[artifactUrlsCollection.size()]);
        ClassLoader camelCoreClassLoader = CamelContext.class.getClassLoader();
        Thread.currentThread().setContextClassLoader(new ModuleClassLoader(classpath, camelCoreClassLoader));
        return contextClassLoader;
    }

    public static void restoreCtxClassloader(ClassLoader contextClassLoader) {
        Thread.currentThread().setContextClassLoader(contextClassLoader);
    }
}
