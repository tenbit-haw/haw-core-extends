package cn.tenbit.haw.core.extend.proxy.jdk;

import java.lang.reflect.Method;

/**
 * @Author bangquan.qian
 * @Date 2019-07-19 13:49
 */
public class HawDefaultInvocationHandler<T> implements HawInvocationHandler<T> {

    private T target;

    public HawDefaultInvocationHandler(T target) {
        this.target = target;
    }

    @Override
    public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return doInvoke(proxy, method, args);
    }

    protected Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}