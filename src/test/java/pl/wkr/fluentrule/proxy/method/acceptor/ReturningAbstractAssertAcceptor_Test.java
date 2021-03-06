package pl.wkr.fluentrule.proxy.method.acceptor;

import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.description.Description;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReturningAbstractAssertAcceptor_Test {

    private ReturningAbstractAssertAcceptor accepter = new ReturningAbstractAssertAcceptor();

    @Test
    public void should_filter_methods_properly() throws NoSuchMethodException {

        assertFilter(false, "isNull");
        assertFilter(false, "descriptionText");
        assertFilter(false, "toString");

        assertFilter(true, "hasNoCause");
        assertFilter(true, "isInstanceOf", Class.class);
        assertFilter(true, "as", Description.class);
    }

    private void assertFilter(boolean expectedResult, String methodName, Class<?> ...argTypes)
            throws NoSuchMethodException {
        boolean result = accepter.accept(ThrowableAssert.class.getMethod(methodName, argTypes));
        assertThat(result).as(methodName).isEqualTo(expectedResult);
    }


}
