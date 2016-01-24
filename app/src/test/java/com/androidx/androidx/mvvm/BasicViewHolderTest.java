package com.androidx.androidx.mvvm;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.utils.TestBindableView;
import com.androidx.androidx.utils.TestViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class BasicViewHolderTest {
    BasicViewHolder<TestBindableView, TestViewModel> sut;
    TestBindableView mockView;

    @Before
    public void setUp() throws Exception {
        mockView = mock(TestBindableView.class);
        sut = new BasicViewHolder<>(mockView);
    }

    @Test
    public void bind_ShouldInvokeInnerBind() {
        TestViewModel vm = new TestViewModel();
        sut.bindViewModel(vm);

        verify(mockView).bindViewModel(vm);
    }
}
