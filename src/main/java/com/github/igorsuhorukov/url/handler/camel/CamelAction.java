package com.github.igorsuhorukov.url.handler.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;

@FunctionalInterface
public interface CamelAction<T> {
    T processRequest(CamelContext camelContext, Endpoint endpoint);
}
