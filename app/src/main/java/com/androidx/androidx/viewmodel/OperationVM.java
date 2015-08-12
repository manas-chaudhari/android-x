package com.androidx.androidx.viewmodel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;

public class OperationVM {
    Observable<OperationState> mStateObservable;

    public OperationVM(Observable<OperationState> stateObservable) {
        mStateObservable = stateObservable.asObservable().observeOn(AndroidSchedulers.mainThread());
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
                new Func2<Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean isFailed, Boolean isDefault) {
                        return isFailed | isDefault;
                    }
                });
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
