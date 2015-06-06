package com.tinyowl.mvvm.core;

public interface PropertyChangedListener<T> {
    void onPropertyChanged(T property);
}