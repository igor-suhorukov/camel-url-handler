package com.github.igorsuhorukov.url.handler.camel;

import org.apache.camel.spi.BeanRepository;
import org.apache.camel.spring.spi.ApplicationContextBeanRepository;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.Assert.*;

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

    @Test(expected = java.io.IOException.class)
    public void testNonExsistingRegistry() throws Exception{
        URLConnection urlConnection = new URL("camel:/bean:helloWorld").openConnection();
        try (OutputStream outputStream = urlConnection.getOutputStream()){
            IOUtils.copy(new StringReader("Ping"), outputStream);
        }
    }

    private static class AssertionBean{
        private String recievedStr;
        public void say(String string){
            recievedStr = string;
        }
    }

    @Test
    public void testRegistry() throws Exception{

        final AssertionBean assertionBean = new AssertionBean();

        final URLConnection registryConnection = new URL("camel:/registry").openConnection();
        try (OutputStream registryStream = registryConnection.getOutputStream()){
            if(registryStream instanceof Map){
                Map<String, Object> registry = (Map) registryStream;
                registry.put("hello", assertionBean);
            }
        }
        URLConnection urlConnection = new URL("camel:/bean:hello?method=say").openConnection();
        try (OutputStream outputStream = urlConnection.getOutputStream()){
            IOUtils.copy(new StringReader("Ping"), outputStream);
        }
        assertEquals("Ping", assertionBean.recievedStr);
    }

    @Test
    public void testSpringContextAsBeanRegistry() throws Exception{
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(AssertionBean.class).getBeanDefinition();
        defaultListableBeanFactory.registerBeanDefinition("helloBean", beanDefinition);

        List<BeanRepository> beanRepositories = null;
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(defaultListableBeanFactory)){
            applicationContext.refresh();

            final URLConnection registryConnection = new URL("camel:/registry").openConnection();
            try (OutputStream registryStream = registryConnection.getOutputStream()){
                if(registryStream instanceof Consumer){
                    Consumer<BeanRepository> repositoryConsumer = (Consumer<BeanRepository>) registryStream;
                    repositoryConsumer.accept(new ApplicationContextBeanRepository(applicationContext));
                }
                if(registryStream instanceof Supplier){
                    Supplier<List<BeanRepository>> repositorySupplier = (Supplier<List<BeanRepository>>) registryStream;

                    beanRepositories = repositorySupplier.get();
                    assertEquals(1, beanRepositories.size());
                }
            }
            URLConnection urlConnection = new URL("camel:/bean:helloBean?method=say").openConnection();
            try (OutputStream outputStream = urlConnection.getOutputStream()){
                IOUtils.copy(new StringReader("Ping"), outputStream);
            }

            AssertionBean assertionBean = applicationContext.getBean(AssertionBean.class);
            assertEquals("Ping", assertionBean.recievedStr);
        } finally {
            if(beanRepositories!=null) {
                beanRepositories.clear(); //deregister ApplicationContextBeanRepository from RegistryStream as it cause IllegalStateException on closed spring context
            }
        }
    }
}
