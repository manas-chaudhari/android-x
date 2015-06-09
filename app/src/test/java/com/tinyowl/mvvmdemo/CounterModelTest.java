package com.tinyowl.mvvmdemo;

import android.test.AndroidTestCase;

import com.tinyowl.mvvm.core.OnChangedListener;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CounterModelTest extends AndroidTestCase {

    CounterModel model;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        model = new CounterModel();
    }

    @Override
    protected void tearDown() throws Exception {
        model = null;
        super.tearDown();
    }

    public void testInitialValue_IsZero() {
        assertEquals(0, model.getCount());
    }

    public void testIncrementOnce() {
        model.increment();

        assertEquals(1, model.getCount());
    }

    public void testIncrementTwice() {
        model.increment();
        model.increment();

        assertEquals(2, model.getCount());
    }

    public void testListenerOnIncrement() {
        OnChangedListener mockListener = mock(OnChangedListener.class);
        model.addOnChangedListener(mockListener);

        model.increment();

        verify(mockListener).onChanged();
    }
}
