package com.androidx.androidx.viewmodel;

import rx.Observable;
import rx.functions.Func1;

public class OperationVM {
    Observable<OperationState> mStateObservable;

    public OperationVM(Observable<OperationState> stateObservable) {
        mStateObservable = stateObservable;
    }

    public Observable<OperationState> getOperationStateObservable() {
        return mStateObservable;
    }

    public Observable<Boolean> getRunningViewVisibility() {
        return getViewVisibilityObservable(OperationState.RUNNING);
    }

    public Observable<Boolean> getSuccessfulViewVisibility() {
        return getViewVisibilityObservable(OperationState.SUCCESSFUL);
    }

    public Observable<Boolean> getFailedViewVisibility() {
        return getViewVisibilityObservable(OperationState.FAILED);
    }

    private Observable<Boolean> getViewVisibilityObservable(final OperationState viewState) {
        return mStateObservable.map(new Func1<OperationState, Boolean>() {
            @Override
            public Boolean call(OperationState operationState) {
                return operationState == viewState;
            }
        });
    }

}
