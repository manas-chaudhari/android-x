package com.androidx.androidx.mvvm;

import android.view.View;

import rx.Observable;

public class Binder {
    public static void bindVisibility(final View view, Observable<Boolean> visibilityObservable) {
        visibilityObservable.subscribe(aBoolean -> {
            view.setVisibility(aBoolean == true ? View.VISIBLE : View.INVISIBLE);
        });
    }
}
