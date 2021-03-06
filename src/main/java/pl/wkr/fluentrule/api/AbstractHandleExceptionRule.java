package pl.wkr.fluentrule.api;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;


abstract class AbstractHandleExceptionRule<S extends AbstractHandleExceptionRule<S>> implements TestRule {

    private boolean handleAssumptionViolatedExceptions = false;
    private boolean handleAssertionErrors = false;

    private final S myself;

    @SuppressWarnings("unchecked")
    public AbstractHandleExceptionRule() {
        myself = (S) this;
    }

    protected S myself() {
        return myself;
    }

    /**
     * Starts handling of assertion errors.
     *
     * @return {@code this} to allow chaining
     */
    public final S handleAssertionErrors() {
        handleAssertionErrors = true;
        return myself();
    }

    /**
     * Starts handling of AssumptionViolatedException.
     *
     * @return {@code this} to allow chaining
     */
    public final S handleAssumptionViolatedExceptions() {
        handleAssumptionViolatedExceptions = true;
        return myself();
    }

    /**
     * Method required by junit from <b>rules</b> implementations.
     */
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    @Override
    public final Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch(AssumptionViolatedException e) {
                    optionallyHandle(e, handleAssumptionViolatedExceptions);
                    return;
                } catch(AssertionError e) {
                    optionallyHandle(e, handleAssertionErrors);
                    return;
                } catch(Throwable e) {
                    handleOrRethrow(e);
                    return;
                }
                if(isExceptionExpected()) {
                    failBecauseExceptionWasNotThrown();
                }
            }
        };
    }


    private void optionallyHandle(Throwable e, boolean handle) throws Throwable {
        if( handle ) {
            handleOrRethrow(e);
        }
        else {
            throw e;
        }
    }

    private void handleOrRethrow(Throwable e) throws Throwable {
        if(!isExceptionExpected()) {
            throw e;
        }
        handleException(e);
    }


    // -- to override --------------------------------------------
    protected abstract boolean isExceptionExpected();

    protected abstract void failBecauseExceptionWasNotThrown();

    protected abstract void handleException(Throwable e);
}
