package com.github.igorsuhorukov.url.handler.camel.classloader;

import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader;
import org.apache.camel.CamelContext;

import java.net.URL;
import java.util.List;

public class ClassLoaderUtils {

    private ClassLoaderUtils() {
        throw new UnsupportedOperationException("utility class");
    }

    public static ClassLoader loadModule(String moduleName) {
        try {
            String mavenRepository = System.getProperty("url.protocol.handler.mvn-repo");
            List<URL> artifactUrlsCollection = mavenRepository == null|| mavenRepository.isEmpty() ?
                    MavenClassLoader.usingCentralRepo().getArtifactUrlsCollection(moduleName, null):
                    MavenClassLoader.using(mavenRepository).getArtifactUrlsCollection(moduleName, null);
            URL[] classpath = artifactUrlsCollection.toArray(new URL[0]);
            ClassLoader camelCoreClassLoader = CamelContext.class.getClassLoader();
            return new ModuleClassLoader(classpath, camelCoreClassLoader);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
