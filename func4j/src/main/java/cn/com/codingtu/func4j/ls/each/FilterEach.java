package cn.com.codingtu.func4j.ls.each;

public interface FilterEach<T> {
    public boolean each(int position, boolean isLast, T t);
}