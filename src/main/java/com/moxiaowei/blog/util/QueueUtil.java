package com.moxiaowei.blog.util;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 队列
 * @author moxiaowei    18:58 2018/4/2
 */
public class QueueUtil<T> {

    private List<T> list;

    private static QueueUtil q = null;

    private QueueUtil(){
        this.list = Collections.synchronizedList(new LinkedList<T>());
    }

    public static <T> QueueUtil<T> getInstance(){
        if(q == null){
            synchronized (QueueUtil.class){
                if(q == null){
                    q = new QueueUtil<T>();
                }
            }
        }
        return q;
    }

    public void push(T t) throws ClassCastException {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if(genericSuperclass != t.getClass().getGenericSuperclass()) {
            throw new ClassCastException();
        }
        this.list.add(t);
    }

    public synchronized T getFirst() {
        T o = null;
        if (this.list.size() > 0) {
            o = this.list.get(0);
            this.list.remove(0);
        }
        return o;
    }

    public int size() {
        return this.list.size();
    }

    public void pushAll(T... o) {
        List<T> ts = Arrays.asList(o);
        this.list.addAll(ts);
    }

    public void pushAll(Collection<T> o) {
        this.list.addAll(o);
    }

    public  List getAll() {
        return this.list;
    }
}
