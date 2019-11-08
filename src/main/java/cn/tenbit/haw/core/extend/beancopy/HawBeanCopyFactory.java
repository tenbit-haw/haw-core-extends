package cn.tenbit.haw.core.extend.beancopy;

/**
 * @author bangquan.qian
 * @date 2019-11-04 20:12
 */
public class HawBeanCopyFactory {

    public static <S, R> HawBeanCopy<S, R> getCglibBeanCopy() {
        return new HawCglibBeanCopy<>();
    }
}
