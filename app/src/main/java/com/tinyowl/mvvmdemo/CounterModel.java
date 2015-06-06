package com.tinyowl.mvvmdemo;

import com.tinyowl.mvvm.core.BaseObservable;

public class CounterModel extends BaseObservable {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyChanged();
    }

    public void increment() {
        setCount(getCount() + 1);
    }

    public void decrement() {
        setCount(getCount() - 1);
    }
}
