package cn.tenbit.haw.core.extend.util;

import cn.tenbit.haw.core.support.HawExecutor;
import cn.tenbit.haw.core.util.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * @Author bangquan.qian
 * @Date 2019-06-14 16:32
 */
public class HareBeans {

    private static final String PREFIX_GETTER = "get";
    private static final String PREFIX_SETTER = "set";

    public static Method getGetterMethod(Class clz, Field field) {
        return getGetterMethod(clz, field.getName());
    }

    public static Method getGetterMethod(Class clz, String fieldName) {
        return getGsetMethod(clz, PREFIX_GETTER, fieldName);
    }

    public static Method getSetterMethod(Class clz, Field field) {
        return getSetterMethod(clz, field.getName());
    }

    public static Method getSetterMethod(Class clz, String fieldName) {
        return getGsetMethod(clz, PREFIX_SETTER, fieldName);
    }

    private static Method getGsetMethod(final Class clz, final String prefix, final String fieldName) {
        return HawInvokes.invokeWithTurnRe(new HawExecutor<Method>() {
            @Override
            public Method execute() throws Throwable {
                return HawClass.getPublicMethod(clz, getGsetMethodName(prefix, fieldName));
            }
        });
    }

    private static String getGsetMethodName(String prefix, String fieldName) {
        HawAsserts.isTrue(HawStrings.isNotBlank(fieldName), "field name invalid");
        fieldName = HawStrings.trim(fieldName);
        String firstPart = fieldName.substring(0, 1).toUpperCase();
        final String finalFieldName = fieldName;
        String secondPart = finalFieldName.length() < 2 ? HawDefaults.STRING_EMPTY : finalFieldName.substring(1);
        return prefix + firstPart + secondPart;
    }

    /**
     * 对象浅拷贝
     */
    public static <S, T> T copyObject(S s, Class<T> clz, String... ignoreProperties) {
        return HawCopys.copyObject(s, clz, ignoreProperties);
    }

    /**
     * list浅拷贝
     */
    public static <S, T> List<T> copyList(Collection<S> srcs, Class<T> clz, String... ignoreProperties) {
        return HawCopys.copyList(srcs, clz, ignoreProperties);
    }
}