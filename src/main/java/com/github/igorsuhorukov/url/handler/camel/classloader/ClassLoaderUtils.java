package com.github.igorsuhorukov.url.handler.camel.classloader;

import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader;
import org.apache.camel.CamelContext;

import java.net.URL;
import java.util.List;

public class ClassLoaderUtils {

    public static ClassLoader loadModule(String moduleName) {
        try {
            List<URL> artifactUrlsCollection = MavenClassLoader.usingCentralRepo().getArtifactUrlsCollection(moduleName, null);
            URL[] classpath = artifactUrlsCollection.toArray(new URL[artifactUrlsCollection.size()]);
            ClassLoader camelCoreClassLoader = CamelContext.class.getClassLoader();
            return new ModuleClassLoader(classpath, camelCoreClassLoader);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void restoreCtxClassloader(ClassLoader contextClassLoader) {
        Thread.currentThread().setContextClassLoader(contextClassLoader);
    }
}
