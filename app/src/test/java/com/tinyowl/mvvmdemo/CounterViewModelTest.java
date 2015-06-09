package com.tinyowl.mvvmdemo;

import android.test.AndroidTestCase;

import com.tinyowl.mvvm.core.PropertyChangedListener;
import com.tinyowl.mvvmdemo.CounterModel;
import com.tinyowl.mvvmdemo.CounterViewModel;

import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class CounterViewModelTest extends AndroidTestCase {
    CounterViewModel counterViewModel;
    CounterModel spyCounterModel;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        spyCounterModel = Mockito.spy(CounterModel.class);
        counterViewModel = new CounterViewModel(spyCounterModel);
    }

    @Override
    protected void tearDown() throws Exception {
        counterViewModel = null;
        super.tearDown();
    }

    public void testInitialCountText() {
        // given
        CounterModel model = new CounterModel();
        model.setCount(2);
        CounterViewModel vm = new CounterViewModel(model);

        // then
        assertEquals("2 \u20b9", vm.getCountText());
    }

    public void testIncrementCommand() {
        counterViewModel.incrementCommand.run();

        verify(spyCounterModel).increment();
    }

    public void testViewModelUpdates() {
        CounterModel model = new CounterModel();
        model.setCount(2);
        CounterViewModel vm = new CounterViewModel(model);

        model.setCount(4);

        assertEquals("4 \u20b9", vm.getCountText());
    }

    public void testCountTextListener() {
        PropertyChangedListener<CounterViewModel.CounterVMProperty> mockListener = mock(PropertyChangedListener.class);
        counterViewModel.addPropertyChangedListener(mockListener);

        counterViewModel.incrementCommand.run();

        verify(mockListener).onPropertyChanged(CounterViewModel.CounterVMProperty.COUNT_TEXT);
    }
}
