package cn.com.codingtu.func4j.ls;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import cn.com.codingtu.func4j.ls.each.Each;
import cn.com.codingtu.func4j.ls.each.FilterEach;
import cn.com.codingtu.func4j.ls.each.MapEach;
import cn.com.codingtu.func4j.ls.each.MapFilterEach;
import cn.com.codingtu.func4j.ls.filter.Filter;
import cn.com.codingtu.func4j.ls.filter.MapFilter;

import static cn.com.codingtu.func4j.CountFunc.*;

public class Ls {

    public static <T> void ls(Collection<? extends T> ts, Each<T> each) {
        if (count(ts) > 0) {
            int index = 0;
            Iterator<? extends T> iterator = ts.iterator();
            while (iterator.hasNext()) {
                if (each.each(index, iterator.next())) {
                    break;
                }
                index++;
            }
        }
    }

    public static <K, T> void ls(final Map<K, T> ts, final MapEach<K, T> each) {
        if (ts != null) {
            ls(ts.keySet(), new Each<K>() {
                @Override
                public boolean each(int position, K k) {
                    return each.each(position, k, ts.get(k));
                }
            });
        }
    }

    public static <T> void ls(T[] ts, Each<T> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(short[] ts, Each<Short> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(boolean[] ts, Each<Boolean> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(int[] ts, Each<Integer> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(double[] ts, Each<Double> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(float[] ts, Each<Float> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(char[] ts, Each<Character> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(long[] ts, Each<Long> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(byte[] ts, Each<Byte> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static <T> void ls(Collection<? extends T> ts, Filter<T> filter, FilterEach<T> each) {
        if (count(ts) > 0) {
            int index = 0;
            Iterator<? extends T> iterator = ts.iterator();
            T t = null;
            T next = null;
            while (iterator.hasNext()) {
                next = iterator.next();
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static <K, T> void ls(final Map<K, ? extends T> ts, final MapFilter<K, T> filter,
                                 final MapFilterEach<K, T> each) {
        if (count(ts) > 0) {
            ls(ts.keySet(), new Filter<K>() {
                @Override
                public boolean filter(K k) {
                    return filter.filter(k, ts.get(k));
                }
            }, new FilterEach<K>() {
                @Override
                public boolean each(int position, boolean isLast, K k) {
                    return each.each(position, isLast, k, ts.get(k));
                }
            });
        }
    }

    public static <T> void ls(T[] ts, Filter<T> filter, FilterEach<T> each) {
        if (count(ts) > 0) {
            int index = 0;
            T t = null;
            T next = null;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static void ls(short[] ts, Filter<Short> filter, FilterEach<Short> each) {
        if (count(ts) > 0) {
            int index = 0;
            Short t = null;
            short next;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static void ls(boolean[] ts, Filter<Boolean> filter, FilterEach<Boolean> each) {
        if (count(ts) > 0) {
            int index = 0;
            Boolean t = null;
            boolean next;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static void ls(int[] ts, Filter<Integer> filter, FilterEach<Integer> each) {
        if (count(ts) > 0) {
            int index = 0;
            Integer t = null;
            int next;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static void ls(double[] ts, Filter<Double> filter, FilterEach<Double> each) {
        if (count(ts) > 0) {
            int index = 0;
            Double t = null;
            double next;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static void ls(float[] ts, Filter<Float> filter, FilterEach<Float> each) {
        if (count(ts) > 0) {
            int index = 0;
            Float t = null;
            float next;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static void ls(char[] ts, Filter<Character> filter, FilterEach<Character> each) {
        if (count(ts) > 0) {
            int index = 0;
            Character t = null;
            char next;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static void ls(long[] ts, Filter<Long> filter, FilterEach<Long> each) {
        if (count(ts) > 0) {
            int index = 0;
            Long t = null;
            long next;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static void ls(byte[] ts, Filter<Byte> filter, FilterEach<Byte> each) {
        if (count(ts) > 0) {
            int index = 0;
            Byte t = null;
            byte next;
            for (int i = 0; i < count(ts); i++) {
                next = ts[i];
                if (t == null) {
                    if (!filter.filter(next)) {
                        t = next;
                    }
                    continue;
                }
                if (!filter.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

}
