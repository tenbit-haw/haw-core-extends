package cn.tenbit.haw.core.extend.util;

import cn.tenbit.haw.core.collection.HawLists;
import cn.tenbit.haw.core.constant.HawConsts;
import cn.tenbit.haw.core.extend.beancopy.HawBeanCopy;
import cn.tenbit.haw.core.extend.beancopy.HawBeanCopyFactory;
import cn.tenbit.haw.core.support.HawExecutor;
import cn.tenbit.haw.core.util.HawCollections;
import cn.tenbit.haw.core.util.HawInvokes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author bangquan.qian
 * @date 2019-10-23 18:45
 */
public class HawCopys {

    private static final HawBeanCopy BEAN_COPY = HawBeanCopyFactory.getCglibBeanCopy();

    /**
     * 对象浅拷贝
     */
    public static <S, T> T copyObject(S s, Class<T> clz, String... ignoreProperties) {
        return copyProperties(s, clz, ignoreProperties);
    }

    /**
     * list浅拷贝
     */
    public static <S, T> List<T> copyList(final Collection<S> srcs, final Class<T> clz, final String... ignoreProperties) {
        if (srcs == null) {
            return null;
        }
        if (HawCollections.isEmpty(srcs)) {
            return HawLists.newArrayList();
        }
        return HawInvokes.invokeWithTurnRe(new HawExecutor<List<T>>() {
            @Override
            public List<T> execute() throws Throwable {
                List<T> dsts = new ArrayList<>(srcs.size());
                for (S s : srcs) {
                    dsts.add(copyObject(s, clz, ignoreProperties));
                }
                return dsts;
            }
        });
    }

    @SuppressWarnings(HawConsts.SUPPRESS_WARNING_UNCHECKED)
    private static <S, T> T copyProperties(S s, Class<T> clz, String... ignoreProperties) {
        return s == null ? null : (T) BEAN_COPY.copy(s, clz, ignoreProperties);
    }

}
