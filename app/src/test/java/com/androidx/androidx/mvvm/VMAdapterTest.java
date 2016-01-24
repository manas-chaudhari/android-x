package com.androidx.androidx.mvvm;

import android.view.View;
import android.view.ViewGroup;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.utils.TestBindableView;
import com.androidx.androidx.utils.TestViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class VMAdapterTest {

    private List<TestViewModel> itemVms;
    private VMAdapter sut;
    private ViewProvider viewProvider;
    private TestBindableView itemView;
    private ViewGroup parentViewGroup;

    private void setupSut(int size) {
        itemVms = getTestViewModels(size);
        viewProvider = mock(ViewProvider.class);

        sut = new VMAdapter(RuntimeEnvironment.application, itemVms, viewProvider);

        itemView = mock(TestBindableView.class);
        when(viewProvider.createView()).thenReturn(itemView);
        parentViewGroup = mock(ViewGroup.class);
    }

    @Test
    public void getCount_ShouldReturn2For2ViewModels() {
        setupSut(2);

        assertThat(sut.getCount()).isEqualTo(2);
    }

    @Test
    public void getCount_ShouldReturn3For3ViewModels() {
        setupSut(3);

        assertThat(sut.getCount()).isEqualTo(3);
    }

    @Test
    public void getItem_ShouldReturnCorrespondingViewModel() {
        setupSut(3);

        for (int i = 0; i < 3; i++) {
            assertThat(sut.getItem(i)).isEqualTo(itemVms.get(i));
        }
    }

    @Test
    public void getView_WithoutConvertView_ShouldConstructNewView() {
        setupSut(4);

        View createdView = sut.getView(1, null, parentViewGroup);

        verify(viewProvider).createView();
        assertThat(createdView).isEqualTo(itemView);
    }

    @Test
    public void getView_At1_WithoutConvertView_ShouldBindViewModel_At1() {
        setupSut(4);

        sut.getView(1, null, parentViewGroup);

        verify(itemView).bindViewModel(itemVms.get(1));
    }

    @Test
    public void getView_At2_WithoutConvertView_ShouldBindViewModel_At2() {
        setupSut(4);

        sut.getView(2, null, parentViewGroup);

        verify(itemView).bindViewModel(itemVms.get(2));
    }

    @Test
    public void getView_WithConvertView_ShouldReuseView() {
        setupSut(4);
        TestBindableView convertView = mock(TestBindableView.class);

        View returnedView = sut.getView(1, convertView, parentViewGroup);

        assertThat(returnedView).isEqualTo(convertView);
    }

    @Test
    public void getView_WithConvertView_ShouldNotCreateView() {
        setupSut(4);
        TestBindableView convertView = mock(TestBindableView.class);

        sut.getView(1, convertView, parentViewGroup);

        verifyZeroInteractions(viewProvider);
    }

    @Test
    public void getView_At1_WithConvertView_ShouldBindViewModel_At1() {
        setupSut(4);
        TestBindableView convertView = mock(TestBindableView.class);

        sut.getView(1, convertView, parentViewGroup);

        verify(convertView).bindViewModel(itemVms.get(1));
    }

    @Test
    public void getView_At2_WithConvertView_ShouldBindViewModel_At2() {
        setupSut(4);
        TestBindableView convertView = mock(TestBindableView.class);

        sut.getView(2, convertView, parentViewGroup);

        verify(convertView).bindViewModel(itemVms.get(2));
    }

    @Test
    public void settingNewViewModels_ShouldChangeCount() {
        setupSut(2);

        List<TestViewModel> newVMs = getTestViewModels(5);
        sut.setViewModels(newVMs);

        assertThat(sut.getCount()).isEqualTo(5);
    }

    private List<TestViewModel> getTestViewModels(int size) {
        List<TestViewModel> itemVms = new ArrayList<TestViewModel>();
        for (int i = 0; i < size; i++) {
            itemVms.add(new TestViewModel());
        }
        return itemVms;
    }
}
