package com.androidx.androidx.viewmodel;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.model.Event;
import com.androidx.androidx.service.EventService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventsVMTest {
    EventsVM sut;
    EventService mockEventService;
    List<Event> dummyEvents;

    @Before
    public void setUp() {
        mockEventService = mock(EventService.class);
        dummyEvents = new ArrayList<Event>();
        dummyEvents.add(new Event());
        when(mockEventService.loadEvents()).thenReturn(dummyEvents);

        sut = new EventsVM(mockEventService);
    }

    @Test
    public void fetchCommand_ShouldBeNotNull() {
        assertThat(sut.getFetchCommand()).isNotNull();
    }

    @Test
    public void fetchCommand_ShouldCallEventsApi() {
        sut.getFetchCommand().execute();

        verify(mockEventService).loadEvents();
    }

    @Test
    public void fetchCommand_ShouldSaveLoadedData() {
        sut.getFetchCommand().execute();

        assertThat(sut.getLoadedEvents()).isNotNull();
        assertThat(sut.getLoadedEvents()).isEqualTo(dummyEvents);
    }

    @Test
    public void fetchCommand_ShouldInvokeUpdatedCallback() {
        EventsVM.OnEventsVMUpdatedListener listener = mock(EventsVM.OnEventsVMUpdatedListener.class);
        sut.setListener(listener);

        sut.getFetchCommand().execute();

        verify(listener).onEventsUpdated();
    }


}
