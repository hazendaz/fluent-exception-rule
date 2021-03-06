package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Before;
import pl.wkr.fluentrule.test_.BaseWithFluentThrownTest;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseAssertFactoryTest<A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        extends BaseWithFluentThrownTest {

    protected AssertFactory<A,T> factory;

    @Before
    public void baseBefore() {
        factory = getFactory();
    }

    abstract protected AssertFactory<A,T> getFactory();


    protected final A assertThatCreatesNotNullAssertAndItHasGivenActual(T exception) {
        A anAssert = factory.getAssert(exception);

        assertThat(anAssert).isNotNull();
        anAssert.isSameAs(exception);
        return anAssert;
    }

}
