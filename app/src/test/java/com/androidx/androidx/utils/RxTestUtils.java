package com.androidx.androidx.utils;

import rx.Observable;
import rx.observers.TestSubscriber;

public class RxTestUtils {

    public static <T> TestSubscriber<T> testSubscriber(Observable<T> observable) {
        TestSubscriber<T> testSubscriber = new TestSubscriber<T>();
        observable.subscribe(testSubscriber);
        return testSubscriber;
    }

}
