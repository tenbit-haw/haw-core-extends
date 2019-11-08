package cn.tenbit.haw.core.extend.proxy.cglib;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author bangquan.qian
 * @Date 2019-07-19 14:04
 */
public class HawSimpleMethodInterceptor<T> implements HawMethodInterceptor<T> {

    @Override
    public final Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return doIntercept(proxy, method, args, methodProxy);
    }

    protected Object doIntercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return methodProxy.invokeSuper(proxy, args);
    }
}