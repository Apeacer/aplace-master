package com.aplace.core.web.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * web session 的model对象,通过组合一个map 存储信息
 *
 * @author apeace
 * @version 2017/10/2.
 */
public class Session implements Map<String, Object>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * map默认长度
     */
    private static final int DEFAULT_LENGTH = 8;

    /**
     * 用来保存键值对
     */
    private Map<String, Object> values;

    /**
     * 创建一个session对象
     */
    public Session() {
        this(DEFAULT_LENGTH);
    }

    /**
     * 创建一个session对象，并指定初始容量
     *
     * @param initialCapacity 初始容量
     */
    public Session(int initialCapacity) {
        values = new HashMap<String, Object>(initialCapacity);
    }

    /**
     * 确保values可用
     */
    private void ensureEnable() {
        if (null == values())
            throw new NullPointerException();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return values.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return values.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return values.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        values.putAll(m);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public Set keySet() {
        return values.keySet();
    }

    @Override
    public Collection values() {
        return values.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return values.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return values.equals(o);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }
}
