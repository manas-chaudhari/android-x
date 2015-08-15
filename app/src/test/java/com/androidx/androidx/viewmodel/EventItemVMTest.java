package com.androidx.androidx.viewmodel;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.model.Event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventItemVMTest {
    private Event mockEvent;
    private EventItemVM sut;

    @Before
    public void setUp() throws Exception {
        mockEvent = mock(Event.class);
        sut = new EventItemVM(mockEvent);
    }

    @Test
    public void userLogin_ShouldGetPopulated() {
        when(mockEvent.getUserLogin()).thenReturn("test_login");

        assertThat(sut.getUserLogin()).isEqualTo("test_login");
    }

    @Test
    public void repoName_ShouldGetPopulated() {
        when(mockEvent.getRepositoryName()).thenReturn("test_repo");

        assertThat(sut.getRepositoryName()).isEqualTo("test_repo");
    }
}
