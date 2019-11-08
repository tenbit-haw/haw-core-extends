package cn.tenbit.haw.core.extend.beancopy;

import cn.tenbit.haw.core.util.HawClass;
import cn.tenbit.haw.core.util.HawDefaults;
import cn.tenbit.haw.core.util.HawStrings;
import net.sf.cglib.beans.BeanCopier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bangquan.qian
 * @date 2019-11-04 20:13
 */
public class HawCglibBeanCopy<S, R> implements HawBeanCopy<S, R> {

    @Override
    public R copy(S src, Class<R> retCLz, String... ignoreProperties) {
        if (src == null) {
            return null;
        }
        BeanCopier beanCopier = findBeanCopier(src.getClass(), retCLz);
        R ret = HawClass.newInstance(retCLz);
        beanCopier.copy(src, ret, null);
        return ret;
    }

    @Override
    public R copy(S src, Class<R> retCLz) {
        return copy(src, retCLz, null);
    }

    private static final Map<String, BeanCopier> CACHE = new ConcurrentHashMap<>();

    private static BeanCopier findBeanCopier(Class<?> s, Class<?> t) {
        String cacheKey = getCacheKey(s, t);
        BeanCopier beanCopier = CACHE.get(cacheKey);
        if (beanCopier == null) {
            CACHE.put(cacheKey, beanCopier = BeanCopier.create(s, t, false));
        }
        return beanCopier;
    }

    private static String getCacheKey(Class<?> s, Class<?> t) {
        return HawStrings.joinWith(HawDefaults.STRING_UL, s.getCanonicalName(), t.getCanonicalName());
    }

    @Override
    public String getHawBeanCopyType() {
        return HawBeanCopyConsts.BEAN_COPY_TYPE_CGLIB;
    }
}
