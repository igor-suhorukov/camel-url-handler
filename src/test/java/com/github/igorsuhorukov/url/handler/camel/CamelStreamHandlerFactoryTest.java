package com.github.igorsuhorukov.url.handler.camel;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CamelStreamHandlerFactoryTest {

    @BeforeClass
    public static void setUp() throws Exception {
        URL.setURLStreamHandlerFactory(new CamelStreamHandlerFactory());
    }

    @Test
    public void testHttpProtocol() throws Exception {
        String page = IOUtils.toString(new URL(
                "camel:/https://raw.githubusercontent.com/igor-suhorukov/camel-url-handler/master/pom.xml")
                .openStream());

        assertNotNull(page);
        assertTrue(page.contains("camel-url-handler"));
    }
}
