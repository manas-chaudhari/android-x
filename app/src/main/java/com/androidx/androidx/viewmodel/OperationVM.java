package com.androidx.androidx.viewmodel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

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

    // This behaviour may not be generalisable to all operationVMs.
    // Might require refactoring if a different behaviour is required
    public Observable<Boolean> getFailedViewVisibility() {
        return Observable.combineLatest(getViewVisibilityObservable(OperationState.FAILED),
                getViewVisibilityObservable(OperationState.DEFAULT),
                (isFailed, isDefault) -> isFailed | isDefault);
    }

    private Observable<Boolean> getViewVisibilityObservable(final OperationState viewState) {
        return mStateObservable.observeOn(AndroidSchedulers.mainThread())
                .map(operationState -> operationState == viewState);
    }

}
