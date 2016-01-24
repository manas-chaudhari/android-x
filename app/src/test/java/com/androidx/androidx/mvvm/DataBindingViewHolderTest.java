package com.androidx.androidx.mvvm;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.androidx.androidx.BR;
import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.utils.TestViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DataBindingViewHolderTest {
    DataBindingViewHolder<TestViewModel> sut;
    ViewDataBinding mockView;

    @Before
    public void setUp() throws Exception {
        mockView = mock(ViewDataBinding.class);
        when(mockView.getRoot()).thenReturn(mock(View.class));
        sut = new DataBindingViewHolder<>(mockView);
    }

    @Test
    public void bind_ShouldInvokeInnerBind() {
        TestViewModel vm = new TestViewModel();
        sut.bindViewModel(vm);

        verify(mockView).setVariable(BR.vm, vm);
    }
}