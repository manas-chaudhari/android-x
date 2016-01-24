package com.androidx.androidx.utils;

import android.content.Context;
import android.view.View;

import com.androidx.androidx.mvvm.Bindable;

public class TestBindableView extends View implements Bindable<TestViewModel> {

    public TestBindableView(Context context) {
        super(context);
    }

    @Override
    public void bindViewModel(TestViewModel viewModel) {

    }

}
