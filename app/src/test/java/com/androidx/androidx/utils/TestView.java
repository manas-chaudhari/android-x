package com.androidx.androidx.utils;

import android.content.Context;
import android.view.View;

import com.androidx.androidx.mvvm.Bindable;

public class TestView extends View implements Bindable<TestViewModel> {

    public TestView(Context context) {
        super(context);
    }

    @Override
    public void bindViewModel(TestViewModel viewModel) {

    }

}
