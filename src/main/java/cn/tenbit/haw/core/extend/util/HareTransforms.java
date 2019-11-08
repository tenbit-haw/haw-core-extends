package cn.tenbit.haw.core.extend.util;

import cn.tenbit.haw.core.support.HawExecutor;
import cn.tenbit.haw.core.util.HawCollections;
import cn.tenbit.haw.core.util.HawDefaults;
import cn.tenbit.haw.core.util.HawInvokes;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author bangquan.qian
 * @Date 2019-05-28 17:49
 */
@SuppressWarnings(HawDefaults.SUPPRESS_WARNING_UNCHECKED)
public class HareTransforms {

    private static final boolean DEFAULT_COVER_IF_EXIST = false;

    public static <E, S> List<S> list2List(List<E> list, String field, boolean coverIfExist) {
        Set<S> set = list2Set(list, field, coverIfExist);
        return HawCollections.isEmpty(set) ? Collections.<S>emptyList() : new ArrayList<S>(set);
    }

    public static <E, S> List<S> list2List(List<E> list, String field) {
        return list2List(list, field, DEFAULT_COVER_IF_EXIST);
    }

    public static <E, S> Set<S> list2Set(List<E> list, String field, boolean coverIfExist) {
        Map<S, E> map = list2Map(list, field, coverIfExist);
        if (HawCollections.isEmpty(map)) {
            return Collections.emptySet();
        }
        return map.keySet();
    }

    public static <E, S> Set<S> list2Set(List<E> list, String field) {
        return list2Set(list, field, DEFAULT_COVER_IF_EXIST);
    }

    public static <K, E> Map<K, E> list2Map(List<E> list, String field) {
        return list2Map(list, field, false);
    }

    public static <K, E> Map<K, E> list2Map(final List<E> list, final String field, final boolean coverIfExist) {
        return HawInvokes.invokeWithTurnRe(new HawExecutor<Map<K, E>>() {
            @Override
            public Map<K, E> execute() throws Throwable {
                return doList2Map(list, field, coverIfExist);
            }
        });
    }

    private static <K, E> Map<K, E> doList2Map(List<E> list, String field, boolean coverIfExist) throws Exception {
        if (HawCollections.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Method rm = HareBeans.getGetterMethod(list.get(0).getClass(), field);
        rm.setAccessible(true);

        Map<K, E> map = new LinkedHashMap<>();
        for (E e : list) {
            K key = (K) rm.invoke(e);
            E val = map.get(key);
            if (coverIfExist || val == null) {
                map.put(key, e);
            }
        }

        return map;
    }
}