package com.androidx.androidx.utils;

import rx.Observable;
import rx.Subscriber;

import static org.assertj.core.api.Assertions.assertThat;

public class TestOnSubscribe <T> implements Observable.OnSubscribe<T> {

    int subscriberCount = 0;
    Thread subscribeThread = null;

    @Override
    public void call(Subscriber<? super T> subscriber) {
        subscriberCount += 1;
        subscribeThread = Thread.currentThread();
    }

    public void assertSubscribed() {
        assertThat(subscriberCount).isPositive();
    }

    public Thread getThread() {
        return subscribeThread;
    }
}
