package com.github.igorsuhorukov.url.handler.camel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CamelSubprotocols {

    private static final String CAMEL_CORE = "camel-core";
    private static final String CAMEL_SOLR = "camel-solr";
    private static final String CAMEL_MAIL = "camel-mail";
    private static final String CAMEL_FTP = "camel-ftp";
    private static final String CAMEL_GAE = "camel-gae";
    private static final String CAMEL_CXF = "camel-cxf";
    private static final String CAMEL_AWS = "camel-aws";

    static final Map<String, String> PROTOCOL_MAPPING;

    static {
        HashMap<String, String> mapping = new HashMap<>();
        mapping.put("ahc","camel-ahc");
        mapping.put("ahc-wss","camel-ahc-ws");
        mapping.put("ahc-ws","camel-ahc-ws");
        mapping.put("amqp","camel-amqp");
        mapping.put("apns","camel-apns");
        mapping.put("atmos","camel-atmos");
        mapping.put("atmosphere-websocket","camel-atmosphere-websocket");
        mapping.put("atom","camel-atom");
        mapping.put("avro","camel-avro");
        mapping.put("aws-ddb", CAMEL_AWS);
        mapping.put("aws-ses", CAMEL_AWS);
        mapping.put("aws-s3","/com.github.igor-suhorukov:camel-aws:LATEST");
        mapping.put("aws-swf", CAMEL_AWS);
        mapping.put("aws-sdb", CAMEL_AWS);
        mapping.put("aws-sns", CAMEL_AWS);
        mapping.put("aws-ec2", CAMEL_AWS);
        mapping.put("aws-cw", CAMEL_AWS);
        mapping.put("aws-sqs", CAMEL_AWS);
        mapping.put("beanstalk","camel-beanstalk");
        mapping.put("bean-validator","camel-bean-validator");
        mapping.put("box","camel-box");
        mapping.put("cache","camel-cache");
        mapping.put("cql","camel-cassandraql");
        mapping.put("chunk","camel-chunk");
        mapping.put("cmis","camel-cmis");
        mapping.put("coap","camel-coap");
        mapping.put("cometd","camel-cometd");
        mapping.put("cometds","camel-cometd");
        mapping.put("context","camel-context");
        mapping.put("couchdb","camel-couchdb");
        mapping.put("crypto","camel-crypto");
        mapping.put("cxf", CAMEL_CXF);
        mapping.put("cxfrs", CAMEL_CXF);
        mapping.put("cxfbean", CAMEL_CXF);
        mapping.put("disruptor-vm","camel-disruptor");
        mapping.put("disruptor","camel-disruptor");
        mapping.put("dns","camel-dns");
        mapping.put("docker","camel-docker");
        mapping.put("dozer","camel-dozer");
        mapping.put("dropbox","camel-dropbox");
        mapping.put("ejb","camel-ejb");
        mapping.put("elasticsearch","camel-elasticsearch");
        mapping.put("elsql","camel-elsql");
        mapping.put("eventadmin","camel-eventadmin");
        mapping.put("exec","camel-exec");
        mapping.put("facebook","camel-facebook");
        mapping.put("flatpack","camel-flatpack");
        mapping.put("fop","camel-fop");
        mapping.put("freemarker","camel-freemarker");
        mapping.put("ftp", CAMEL_FTP);
        mapping.put("ftps", CAMEL_FTP);
        mapping.put("sftp", CAMEL_FTP);
        mapping.put("gauth", CAMEL_GAE);
        mapping.put("ghttp", CAMEL_GAE);
        mapping.put("glogin", CAMEL_GAE);
        mapping.put("gtask", CAMEL_GAE);
        mapping.put("gmail", CAMEL_GAE);
        mapping.put("ghttps", CAMEL_GAE);
        mapping.put("ganglia","camel-ganglia");
        mapping.put("geocoder","camel-geocoder");
        mapping.put("git","camel-git");
        mapping.put("github","camel-github");
        mapping.put("google-calendar","camel-google-calendar");
        mapping.put("google-drive","camel-google-drive");
        mapping.put("google-mail","camel-google-mail");
        mapping.put("gora","camel-gora");
        mapping.put("grape","camel-grape");
        mapping.put("guava-eventbus","camel-guava-eventbus");
        mapping.put("hazelcast","camel-hazelcast");
        mapping.put("hbase","camel-hbase");
        mapping.put("hdfs","camel-hdfs");
        mapping.put("hdfs2","camel-hdfs2");
        mapping.put("hipchat","camel-hipchat");
        mapping.put("http","camel-http");
        mapping.put("https","camel-http");
        mapping.put("https4","camel-http4");
        mapping.put("http4","camel-http4");
        mapping.put("ibatis","camel-ibatis");
        mapping.put("infinispan","camel-infinispan");
        mapping.put("ircs","camel-irc");
        mapping.put("irc","camel-irc");
        mapping.put("javaspace","camel-javaspace");
        mapping.put("jbpm","camel-jbpm");
        mapping.put("jclouds","camel-jclouds");
        mapping.put("jcr","camel-jcr");
        mapping.put("jdbc","camel-jdbc");
        mapping.put("jetty","camel-jetty8");
        mapping.put("jetty","camel-jetty9");
        mapping.put("jgroups","camel-jgroups");
        mapping.put("jing","camel-jing");
        mapping.put("jira","camel-jira");
        mapping.put("jms","camel-jms");
        mapping.put("jmx","camel-jmx");
        mapping.put("jolt","camel-jolt");
        mapping.put("jpa","camel-jpa");
        mapping.put("scp","camel-jsch");
        mapping.put("jt400","camel-jt400");
        mapping.put("kafka","camel-kafka");
        mapping.put("kestrel","camel-kestrel");
        mapping.put("krati","camel-krati");
        mapping.put("ldap","camel-ldap");
        mapping.put("lucene","camel-lucene");
        mapping.put("imap", CAMEL_MAIL);
        mapping.put("pop3", CAMEL_MAIL);
        mapping.put("smtp", CAMEL_MAIL);
        mapping.put("pop3s", CAMEL_MAIL);
        mapping.put("imaps", CAMEL_MAIL);
        mapping.put("smtps", CAMEL_MAIL);
        mapping.put("metrics","camel-metrics");
        mapping.put("mina","camel-mina");
        mapping.put("mina2","camel-mina2");
        mapping.put("mongodb","camel-mongodb");
        mapping.put("mqtt","camel-mqtt");
        mapping.put("msv","camel-msv");
        mapping.put("mustache","camel-mustache");
        mapping.put("mvel","camel-mvel");
        mapping.put("mybatis","camel-mybatis");
        mapping.put("nagios","camel-nagios");
        mapping.put("netty","camel-netty");
        mapping.put("netty4","camel-netty4");
        mapping.put("netty4-http","camel-netty4-http");
        mapping.put("netty-http","camel-netty-http");
        mapping.put("openshift","camel-openshift");
        mapping.put("optaplanner","camel-optaplanner");
        mapping.put("paho","camel-paho");
        mapping.put("paxlogging","camel-paxlogging");
        mapping.put("pdf","camel-pdf");
        mapping.put("pgevent","camel-pgevent");
        mapping.put("lpr","camel-printer");
        mapping.put("quartz","camel-quartz");
        mapping.put("quartz2","camel-quartz2");
        mapping.put("quickfix","camel-quickfix");
        mapping.put("rabbitmq","camel-rabbitmq");
        mapping.put("restlet","camel-restlet");
        mapping.put("rmi","camel-rmi");
        mapping.put("routebox","camel-routebox");
        mapping.put("rss","camel-rss");
        mapping.put("sap-netweaver","camel-sap-netweaver");
        mapping.put("xquery","camel-saxon");
        mapping.put("schematron","camel-schematron");
        mapping.put("servlet","camel-servlet");
        mapping.put("sip","camel-sip");
        mapping.put("sips","camel-sip");
        mapping.put("sjms-batch","camel-sjms");
        mapping.put("sjms","camel-sjms");
        mapping.put("slack","camel-slack");
        mapping.put("smpps","camel-smpp");
        mapping.put("smpp","camel-smpp");
        mapping.put("snmp","camel-snmp");
        mapping.put("solr", CAMEL_SOLR);
        mapping.put("solrs", CAMEL_SOLR);
        mapping.put("solrCloud", CAMEL_SOLR);
        mapping.put("spark-rest","camel-spark-rest");
        mapping.put("splunk","camel-splunk");
        mapping.put("spring-event","camel-spring");
        mapping.put("spring-batch","camel-spring-batch");
        mapping.put("spring-integration","camel-spring-integration");
        mapping.put("spring-ldap","camel-spring-ldap");
        mapping.put("spring-redis","camel-spring-redis");
        mapping.put("spring-ws","camel-spring-ws");
        mapping.put("sql","camel-sql");
        mapping.put("ssh","camel-ssh");
        mapping.put("stax","camel-stax");
        mapping.put("stomp","camel-stomp");
        mapping.put("stream","camel-stream");
        mapping.put("string-template","camel-stringtemplate");
        mapping.put("twitter","camel-twitter");
        mapping.put("undertow","camel-undertow");
        mapping.put("velocity","camel-velocity");
        mapping.put("vertx","camel-vertx");
        mapping.put("weather","camel-weather");
        mapping.put("websocket","camel-websocket");
        mapping.put("xmlrpc","camel-xmlrpc");
        mapping.put("xmlsecurity","camel-xmlsecurity");
        mapping.put("xmpp","camel-xmpp");
        mapping.put("yammer","camel-yammer");
        mapping.put("zookeeper","camel-zookeeper");
        mapping.put("webcam","/io.rhiot:camel-webcam:LATEST");

        mapping.put("file", CAMEL_CORE);
        mapping.put("bean", CAMEL_CORE);
        mapping.put("browse", CAMEL_CORE);
        mapping.put("class", CAMEL_CORE);
        mapping.put("controlbus", CAMEL_CORE);
        mapping.put("dataformat", CAMEL_CORE);
        mapping.put("dataset", CAMEL_CORE);
        mapping.put("direct", CAMEL_CORE);
        mapping.put("direct-vm", CAMEL_CORE);
        mapping.put("language", CAMEL_CORE);
        mapping.put("properties", CAMEL_CORE);
        mapping.put("ref", CAMEL_CORE);
        mapping.put("rest", CAMEL_CORE);
        mapping.put("scheduler", CAMEL_CORE);
        mapping.put("timer", CAMEL_CORE);
        mapping.put("validation", CAMEL_CORE);
        mapping.put("vm", CAMEL_CORE);
        mapping.put("xslt", CAMEL_CORE);
        PROTOCOL_MAPPING = Collections.unmodifiableMap(mapping);
    }

    private CamelSubprotocols() {
    }
}
