package com.github.igorsuhorukov.url.handler.camel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CamelSubprotocols {
    public static final Map<String, String> PROTOCOL_MAPPING;
    static {
        HashMap<String, String> mapping = new HashMap<String, String>();
        mapping.put("ahc","camel-ahc");
        mapping.put("ahc-wss","camel-ahc-ws");
        mapping.put("ahc-ws","camel-ahc-ws");
        mapping.put("amqp","camel-amqp");
        mapping.put("apns","camel-apns");
        mapping.put("atmos","camel-atmos");
        mapping.put("atmosphere-websocket","camel-atmosphere-websocket");
        mapping.put("atom","camel-atom");
        mapping.put("avro","camel-avro");
        mapping.put("aws-ddb","camel-aws");
        mapping.put("aws-ses","camel-aws");
        mapping.put("aws-s3","camel-aws");
        mapping.put("aws-swf","camel-aws");
        mapping.put("aws-sdb","camel-aws");
        mapping.put("aws-sns","camel-aws");
        mapping.put("aws-ec2","camel-aws");
        mapping.put("aws-cw","camel-aws");
        mapping.put("aws-sqs","camel-aws");
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
        mapping.put("cxf","camel-cxf");
        mapping.put("cxfrs","camel-cxf");
        mapping.put("cxfbean","camel-cxf");
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
        mapping.put("ftp","camel-ftp");
        mapping.put("ftps","camel-ftp");
        mapping.put("sftp","camel-ftp");
        mapping.put("gauth","camel-gae");
        mapping.put("ghttp","camel-gae");
        mapping.put("glogin","camel-gae");
        mapping.put("gtask","camel-gae");
        mapping.put("gmail","camel-gae");
        mapping.put("ghttps","camel-gae");
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
        mapping.put("imap","camel-mail");
        mapping.put("pop3","camel-mail");
        mapping.put("smtp","camel-mail");
        mapping.put("pop3s","camel-mail");
        mapping.put("imaps","camel-mail");
        mapping.put("smtps","camel-mail");
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
        mapping.put("solr","camel-solr");
        mapping.put("solrs","camel-solr");
        mapping.put("solrCloud","camel-solr");
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

        mapping.put("file","camel-core");
        mapping.put("bean","camel-core");
        mapping.put("browse","camel-core");
        mapping.put("class","camel-core");
        mapping.put("controlbus","camel-core");
        mapping.put("dataformat","camel-core");
        mapping.put("dataset","camel-core");
        mapping.put("direct","camel-core");
        mapping.put("direct-vm","camel-core");
        mapping.put("language","camel-core");
        mapping.put("properties","camel-core");
        mapping.put("ref","camel-core");
        mapping.put("rest","camel-core");
        mapping.put("scheduler","camel-core");
        mapping.put("timer","camel-core");
        mapping.put("validation","camel-core");
        mapping.put("vm","camel-core");
        mapping.put("xslt","camel-core");
        PROTOCOL_MAPPING = Collections.unmodifiableMap(mapping);
    }
}
