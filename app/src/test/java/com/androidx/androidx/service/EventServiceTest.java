package com.androidx.androidx.service;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.model.Event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventServiceTest {

    @Test
    public void loadData_ShouldReturnNonEmptyData() {
        EventService eventService = new EventService();

        List<Event> events = eventService.loadEvents();

        assertThat(events).isNotNull();
        assertThat(events.size()).isPositive();
    }
}
