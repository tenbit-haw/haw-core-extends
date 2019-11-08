package cn.tenbit.haw.core.extend.util;

import cn.tenbit.haw.core.extend.support.TransFormFeature;
import cn.tenbit.haw.core.support.HawExecutor;
import cn.tenbit.haw.core.util.HawCollections;
import cn.tenbit.haw.core.util.HawDefaults;
import cn.tenbit.haw.core.util.HawInvokes;
import cn.tenbit.haw.core.util.HawObjects;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author bangquan.qian
 * @Date 2019-05-28 17:49
 */
@SuppressWarnings(HawDefaults.SUPPRESS_WARNING_UNCHECKED)
public class HareTransforms {

    private static final TransFormFeature[] DEFAULT_FEATURES = {
            TransFormFeature.IGNORE_IF_EXIST,
            TransFormFeature.IGNORE_IF_NULL
    };

    public static <E, S> List<S> list2List(List<E> list, String field) {
        return list2List(list, field, DEFAULT_FEATURES);
    }

    public static <E, S> Set<S> list2Set(List<E> list, String field) {
        return list2Set(list, field, DEFAULT_FEATURES);
    }

    public static <K, E> Map<K, E> list2Map(List<E> list, String field) {
        return list2Map(list, field, DEFAULT_FEATURES);
    }

    public static <E, S> List<S> list2List(List<E> list, String field, TransFormFeature... features) {
        Set<S> set = list2Set(list, field, features);
        return HawCollections.isEmpty(set) ? Collections.<S>emptyList() : new ArrayList<S>(set);
    }

    public static <E, S> Set<S> list2Set(List<E> list, String field, TransFormFeature... features) {
        Map<S, E> map = list2Map(list, field, features);
        if (HawCollections.isEmpty(map)) {
            return Collections.emptySet();
        }
        return map.keySet();
    }

    public static <K, E> Map<K, E> list2Map(final List<E> list, final String field, final TransFormFeature... features) {
        return HawInvokes.invokeWithTurnRe(new HawExecutor<Map<K, E>>() {
            @Override
            public Map<K, E> execute() throws Throwable {
                return doList2Map(list, field, features);
            }
        });
    }

    private static <K, E> Map<K, E> doList2Map(List<E> list, String field, TransFormFeature... features) throws Exception {
        if (HawCollections.isEmpty(list)) {
            return Collections.emptyMap();
        }

        List<TransFormFeature> featureList = HawObjects.emptyIfNull(HawCollections.asList(features));

        boolean coverIfExist = true;
        if (featureList.contains(TransFormFeature.IGNORE_IF_EXIST)) {
            coverIfExist = false;
        }

        boolean ignoreIfNull = true;
        if (featureList.contains(TransFormFeature.PUT_IF_NULL)) {
            ignoreIfNull = false;
        }

        Method rm = HareBeans.getGetterMethod(list.get(0).getClass(), field);
        rm.setAccessible(true);

        Map<K, E> map = new LinkedHashMap<>();
        for (E e : list) {
            K key = (K) rm.invoke(e);
            E val = map.get(key);
            if (key == null && ignoreIfNull) {
                continue;
            }
            if (val != null && !coverIfExist) {
                continue;
            }
            map.put(key, e);
        }

        return map;
    }
}