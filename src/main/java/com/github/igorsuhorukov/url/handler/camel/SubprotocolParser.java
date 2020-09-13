package com.github.igorsuhorukov.url.handler.camel;

import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class SubprotocolParser {
    public static void main(String[] args) throws Exception{
        final XPath xPath = XPathFactory.newInstance().newXPath();

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final Document projectPom = documentBuilder.parse("pom.xml");
        String camelVersion = xPath.evaluate("/project/properties/camel.version", projectPom);

        final URL pom = new URL("camel:/https://raw.githubusercontent.com/apache/camel/release/"+camelVersion+"/components/pom.xml");
        try (InputStream componentsStream = pom.openStream()){
            Document components = documentBuilder.parse(componentsStream);
            NodeList modules = (NodeList) xPath.evaluate("/project/modules/module", components, XPathConstants.NODESET);
            final int modulesLength = modules.getLength();
            Map<String, String> protocolMapping = new TreeMap<>();
            for(int idx = 0; idx < modulesLength; idx++){
                Node module = modules.item(idx);
                String moduleName = module.getTextContent();
                String mavenModule = "org.apache.camel:" + moduleName + ":" + camelVersion;
                URL resource;
                try (URLClassLoader moduleCl = MavenClassLoader.forMavenCoordinates(mavenModule)) {
                    resource = moduleCl.findResource("META-INF/services/org/apache/camel/component.properties");
                } catch (Exception e){
                    continue;
                }
                if(resource==null) continue;

                try (InputStream inStream = resource.openStream()){
                    final Properties properties = new Properties();
                    properties.load(inStream);
                    final String[] protocols = properties.get("components").toString().split(" ");
                    for(String protocol: protocols) {
                        protocolMapping.put(protocol, mavenModule);
                    }
                }
            }
            Properties protocolDescriptor = new Properties();
            protocolMapping.forEach(protocolDescriptor::put);
            protocolDescriptor.store(new FileWriter("target/classes/protocols.properties"), null );
        }
    }
}
