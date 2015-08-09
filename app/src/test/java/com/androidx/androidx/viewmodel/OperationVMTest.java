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
    }

    @Test
    public void runningState_ShouldShowOnlyRunningView() {
        OperationVM sut = new OperationVM(Observable.just(mStateToTest));

        for (OperationState state :
                OperationState.values()) {
            RxTestUtils.testSubscriber(sut.getViewVisibilityObservable(state)).assertValue(state == mStateToTest);
        }
    }
}
