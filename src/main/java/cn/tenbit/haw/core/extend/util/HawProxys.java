package cn.tenbit.haw.core.extend.util;

import cn.tenbit.haw.core.extend.proxy.cglib.HawDefaultMethodInterceptor;
import cn.tenbit.haw.core.extend.proxy.cglib.HawMethodInterceptor;
import cn.tenbit.haw.core.extend.proxy.cglib.HawSimpleMethodInterceptor;
import cn.tenbit.haw.core.extend.proxy.jdk.HawDefaultInvocationHandler;
import cn.tenbit.haw.core.extend.proxy.jdk.HawInvocationHandler;
import cn.tenbit.haw.core.util.HawDefaults;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @Author bangquan.qian
 * @Date 2019-07-19 13:52
 */
@SuppressWarnings(HawDefaults.SUPPRESS_WARNING_UNCHECKED)
public class HawProxys {

    public static <T> T jdk(ClassLoader cl, Class<?>[] ifs, HawInvocationHandler<T> ih) {
        return (T) Proxy.newProxyInstance(cl, ifs, ih);
    }

    public static <T> T jdk(Class<?>[] ifs, HawInvocationHandler<T> ih) {
        return jdk(ClassLoader.getSystemClassLoader(), ifs, ih);
    }

    public static <T> T jdk(Class<?> inf, HawInvocationHandler<T> ih) {
        return jdk(new Class[]{inf}, ih);
    }

    public static <T> T jdk(Class<?> inf, T target) {
        return jdk(inf, new HawDefaultInvocationHandler<>(target));
    }

    public static <T> T jdk(T target) {
        return jdk(target.getClass().getInterfaces(), new HawDefaultInvocationHandler<>(target));
    }

    public static <T> T cglib(T target, HawMethodInterceptor<T> mi) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(mi);
        return (T) enhancer.create();
    }

    public static <T> T cglib(T target) {
        return cglib(target, new HawDefaultMethodInterceptor<>(target));
    }

    public static <T> T simpleCglib(T target) {
        return cglib(target, new HawSimpleMethodInterceptor<T>());
    }
}