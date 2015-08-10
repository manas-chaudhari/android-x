package com.androidx.androidx.viewmodel;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.utils.RxTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collection;

import rx.Observable;

@RunWith(ParameterizedRobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE)
public class OperationVMTest {

    private final OperationVM sut;

    // Directly using source enums as parameters doesn't work.
    // Only enums declared in test code can be used
    @ParameterizedRobolectricTestRunner.Parameters(name = "State = {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { OperationState.DEFAULT.toString() },
                { OperationState.RUNNING.toString() },
                { OperationState.SUCCESSFUL.toString() },
                { OperationState.FAILED.toString() },
        });
    }

    private final OperationState mStateToTest;

    public OperationVMTest(String stateString) {
        mStateToTest = OperationState.valueOf(stateString);
        sut = new OperationVM(Observable.just(mStateToTest));
    }

    @Test
    public void runningView_ShouldBeVisibleInOnlyRunningState() {
        RxTestUtils.testSubscriber(sut.getRunningViewVisibility()).assertValue(mStateToTest == OperationState.RUNNING);
    }

    @Test
    public void successfulView_ShouldBeVisibleInOnlySuccessfulState() {
        RxTestUtils.testSubscriber(sut.getSuccessfulViewVisibility()).assertValue(mStateToTest == OperationState.SUCCESSFUL);
    }

    @Test
    public void failedView_ShouldBeVisibleInOnlyFailedState() {
        RxTestUtils.testSubscriber(sut.getFailedViewVisibility()).assertValue(mStateToTest == OperationState.FAILED);
    }
}
