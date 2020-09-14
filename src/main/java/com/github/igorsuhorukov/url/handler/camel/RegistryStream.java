package com.github.igorsuhorukov.url.handler.camel;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.BeanRepository;
import org.apache.camel.spi.Registry;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RegistryStream extends ByteArrayOutputStream implements Registry, Map<String, Object>,
        Consumer<BeanRepository>, Supplier<List<BeanRepository>> {

    protected final List<BeanRepository> repositories = new CopyOnWriteArrayList<>();
    private Registry registry = new UrlHandlerDefaultRegistry(repositories);

    @Override
    public void accept(BeanRepository beanRepository) {
        repositories.add(beanRepository);
    }

    @Override
    public List<BeanRepository> get() {
        return repositories;
    }

    @Override
    public void bind(String id, Object bean) throws RuntimeCamelException {
        registry.bind(id, bean);
    }

    @Override
    public void bind(String id, Class<?> type, Object bean) throws RuntimeCamelException {
        registry.bind(id, type, bean);
    }

    @Override
    public Object wrap(Object value) {
        return registry.unwrap(value);
    }

    @Override
    public Object lookupByName(String name) {
        return registry.lookupByName(name);
    }

    @Override
    public <T> T lookupByNameAndType(String name, Class<T> type) {
        return registry.lookupByNameAndType(name, type);
    }

    @Override
    public <T> Map<String, T> findByTypeWithName(Class<T> type) {
        return registry.findByTypeWithName(type);
    }

    @Override
    public <T> Set<T> findByType(Class<T> type) {
        return registry.findByType(type);
    }

    @Override
    public Object unwrap(Object value) {
        return registry.unwrap(value);
    }


    @Override
    public boolean containsKey(Object key) {
        return registry.lookupByName(key.toString())!=null;
    }

    @Override
    public Object get(Object key) {
        return registry.lookupByName(key.toString());
    }

    @Override
    public Object put(String key, Object value) {
        final Object prevValue = get(key);
        bind(key, value);
        return prevValue;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        m.forEach(this::bind);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Object> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
