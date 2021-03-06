package pl.wkr.fluentrule.api;

import pl.wkr.fluentrule.api.check.Check;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.Preconditions.checkNotNull;

abstract class AbstractCheckExpectedException<S extends AbstractCheckExpectedException<S>>
        extends AbstractHandleExceptionRule<S> {

    private List<Check> checks = new ArrayList<Check>();

    /**
     * Add assertion callback to callback list.
     *
     * @param check callback to assert thrown exception
     */
    protected void addCheck(Check check) {
        checks.add(checkNotNull(check, "check"));
    }

    /**
     * Returns if rule is expecting any exception.
     *
     * @return {@code true} if any exception is expected
     */
    @Override
    public boolean isExceptionExpected() {
        return checks.size() > 0;
    }

    /**
     * Handles thrown exception.
     *
     * @param e thrown exception
     */
    @Override
    protected void handleException(Throwable e) {
        for(Check c : checks) {
            c.check(e);
        }
    }

    /**
     * Throws AssertionError with info that exception was expected but not thrown.
     */
    @Override
    protected void failBecauseExceptionWasNotThrown() {
        throw new AssertionError("Exception was expected but was not thrown");
    }
}
