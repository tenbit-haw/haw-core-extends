package cn.tenbit.haw.core.extend.beancopy;

/**
 * @author bangquan.qian
 * @date 2019-11-04 20:11
 */
public interface HawBeanCopy<S, R> {

    R copy(S src, Class<R> retCLz, String... ignoreProperties);

    R copy(S src, Class<R> retCLz);

    String getHawBeanCopyType();
}
