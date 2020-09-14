package com.github.igorsuhorukov.url.handler.camel;

import org.apache.camel.spi.BeanRepository;
import org.apache.camel.support.DefaultRegistry;

import java.util.List;

class UrlHandlerDefaultRegistry extends DefaultRegistry {
    public UrlHandlerDefaultRegistry(List<BeanRepository> repositories) {
        this.repositories = repositories;
    }
}
