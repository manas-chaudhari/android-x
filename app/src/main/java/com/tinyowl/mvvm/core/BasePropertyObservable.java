package com.tinyowl.mvvm.core;

import java.util.ArrayList;
import java.util.List;

public class BasePropertyObservable<T> {
    private List<PropertyChangedListener<T>> mListeners = new ArrayList<>();

    public void notifyPropertyChanged(T property) {
        for (PropertyChangedListener<T> listener : mListeners) {
            listener.onPropertyChanged(property);
        }
    }

    public void addPropertyChangedListener(PropertyChangedListener<T> listener) {
        mListeners.add(listener);
    }

    public void removePropertyChangedListener(PropertyChangedListener<T> listener) {
        mListeners.remove(listener);
    }

}
