package cn.com.codingtu.func4j.ls.filter;

public interface MapFilter<K, T> {
    public boolean filter(K k, T t);
}