package com.github.igorsuhorukov.url.handler.camel;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HandlerTest {

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

    @Test
    public void testWriteToUrl() throws Exception {
        String expectedString = "HELLO WORLD";
        String fileName = UUID.randomUUID().toString();

        try {
            URLConnection urlConnection = new URL("camel:/file://|-.xml?fileName=" + fileName).openConnection();
            try (OutputStream outputStream = urlConnection.getOutputStream()){
                IOUtils.copy(new StringReader(expectedString), outputStream);
            }
            assertEquals(expectedString, IOUtils.toString(new FileInputStream(fileName)));
        } finally {
            new File(fileName).delete();
        }
    }
}
