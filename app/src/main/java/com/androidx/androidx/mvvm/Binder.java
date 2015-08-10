package com.androidx.androidx.mvvm;

import android.view.View;

import rx.Observable;
import rx.functions.Action1;

public class Binder {
    public static void bindVisibility(final View view, Observable<Boolean> visibilityObservable) {
        visibilityObservable.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                view.setVisibility(aBoolean == true ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }
}
