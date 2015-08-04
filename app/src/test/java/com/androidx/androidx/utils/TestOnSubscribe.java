package com.androidx.androidx.utils;

import android.os.Looper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

import static org.assertj.core.api.Assertions.assertThat;

public class TestOnSubscribe <T> implements Observable.OnSubscribe<T> {

    int subscriberCount = 0;
    Thread subscribeThread = null;
    CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void call(Subscriber<? super T> subscriber) {
        subscriberCount += 1;
        subscribeThread = Thread.currentThread();
        latch.countDown();
    }

    public void assertSubscribed() throws InterruptedException {
        latch.await(200, TimeUnit.MILLISECONDS);
        assertThat(subscriberCount).isPositive();
    }

    public void assertBackgroundThread() throws InterruptedException {
        latch.await(200, TimeUnit.MILLISECONDS);
        assertThat(subscribeThread).isNotEqualTo(Looper.getMainLooper().getThread());
    }
}
