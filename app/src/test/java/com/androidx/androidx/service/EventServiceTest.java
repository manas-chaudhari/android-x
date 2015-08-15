package com.androidx.androidx.service;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.model.Event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventServiceTest {

    @Test
    public void loadData_ShouldReturnNonEmptyData() {
        EventService eventService = new EventService();

        TestSubscriber<List<Event>> subscriber = new TestSubscriber<>();
        eventService.loadEvents(Schedulers.immediate()).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertValueCount(1);
        List<Event> events = subscriber.getOnNextEvents().get(0);
        assertThat(events).isNotNull();
        assertThat(events.size()).isPositive();
    }

    @Test
    public void parsedEvents_ShouldContainUserNameAndRepo() {
        EventService eventService = new EventService();

        TestSubscriber<List<Event>> subscriber = new TestSubscriber<>();
        eventService.loadEvents(Schedulers.immediate()).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        List<Event> events = subscriber.getOnNextEvents().get(0);

        for (Event e :
                events) {
            assertThat(e.getUserLogin()).isNotEmpty();
            assertThat(e.getRepositoryName()).isNotEmpty();
        }
    }
}
