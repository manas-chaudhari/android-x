package com.androidx.androidx.mvvm;

import android.support.v7.widget.RecyclerView;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.utils.TestView;
import com.androidx.androidx.utils.TestViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.subjects.BehaviorSubject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class VMRecyclerAdapterTest {
    VMRecyclerAdapter<TestView, TestViewModel> sut;
    BehaviorSubject<List<TestViewModel>> source;
    ViewProvider<TestView> mockViewProvider;
    TestView mockView;

    @Before
    public void setUp() throws Exception {
        source = BehaviorSubject.create(new ArrayList<>());
        mockViewProvider = mock(ViewProvider.class);
        mockView = mock(TestView.class);
        when(mockViewProvider.createView()).thenReturn(mockView);
        sut = new VMRecyclerAdapter<>(source, mockViewProvider);
    }

    @Test
    public void getCount_ShouldReturn0_Initially() {
        assertThat(sut.getItemCount()).isEqualTo(0);
    }

    @Test
    public void getCount_For2Items_ShouldReturn2() {
        source.onNext(Arrays.asList(new TestViewModel(), new TestViewModel()));

        assertThat(sut.getItemCount()).isEqualTo(2);
    }

    @Test
    public void close_ShouldClearSubscriptions() {
        sut.close();

        assertThat(source.hasObservers()).isFalse();
    }

    @Test
    public void onCreateViewHolder_ShouldInvokeCreateView() {
        RecyclerView.ViewHolder holder = sut.onCreateViewHolder(null, 0);

        verify(mockViewProvider).createView();
        assertThat(holder).isNotNull();
        assertThat(holder.itemView).isSameAs(mockView);
    }

    @Test
    public void onBind_At0_ShouldInvokeBindWithFirstViewModel() {
        VMRecyclerAdapter.BasicViewHolder<TestView, TestViewModel> holder = new VMRecyclerAdapter.BasicViewHolder<>(mockView);
        TestViewModel firstViewModel = new TestViewModel();
        source.onNext(Collections.singletonList(firstViewModel));
        sut.onBindViewHolder(holder, 0);

        verify(mockView).bindViewModel(firstViewModel);
    }

    @Test
    public void onBind_At1_ShouldInvokeBindWithSecondViewModel() {
        VMRecyclerAdapter.BasicViewHolder<TestView, TestViewModel> holder = new VMRecyclerAdapter.BasicViewHolder<>(mockView);
        TestViewModel secondViewModel = new TestViewModel();
        source.onNext(Arrays.asList(null, secondViewModel));
        sut.onBindViewHolder(holder, 1);

        verify(mockView).bindViewModel(secondViewModel);
    }
}
