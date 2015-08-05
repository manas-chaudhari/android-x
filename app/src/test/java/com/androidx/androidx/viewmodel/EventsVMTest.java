package com.androidx.androidx.viewmodel;

import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.model.Event;
import com.androidx.androidx.service.EventService;
import com.androidx.androidx.utils.TestOnSubscribe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;
import rx.subjects.BehaviorSubject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
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
        when(mockEventService.loadEvents(any(Scheduler.class))).thenReturn(Observable.just(dummyEvents));

        sut = new EventsVM(mockEventService, RuntimeEnvironment.application);
    }

    @Test
    public void fetchCommand_ShouldBeNotNull() {
        assertThat(sut.getFetchCommand()).isNotNull();
    }

    @Test
    public void fetchCommand_ShouldCallEventsApi() throws InterruptedException {
        TestOnSubscribe<List<Event>> onSubscribe = new TestOnSubscribe<>();
        when(mockEventService.loadEvents(any(Scheduler.class))).thenReturn(Observable.create(onSubscribe));

        sut.getFetchCommand().execute();

        onSubscribe.assertSubscribed();
    }

    @Test
    public void fetchCommand_ShouldSaveLoadedData() {
        sut.getFetchCommand().execute();

        List<EventItemVM> eventItems = sut.getEventItems();
        assertThat(eventItems).isNotNull();
        for (int i = 0; i < dummyEvents.size(); i++) {
            assertThat(eventItems.get(i)).isEqualToComparingFieldByField(new EventItemVM(dummyEvents.get(i)));
        }
    }

    @Test
    public void fetchCommand_ShouldInvokeUpdatedCallback() {
        EventsVM.OnEventsVMUpdatedListener listener = mock(EventsVM.OnEventsVMUpdatedListener.class);
        sut.setListener(listener);

        sut.getFetchCommand().execute();

        verify(listener).onEventsUpdated();
    }

    private void resizeDummyEvents(int numberOfEvents) {
        dummyEvents.clear();
        for (int i = 0; i < numberOfEvents; ++i) {
            dummyEvents.add(new Event());
        }
    }

    @Test
    public void initiallyLoadedEvents_ShouldBeEmpty() {
        assertThat(sut.getEventItems()).isNotNull();
        assertThat(sut.getEventItems()).isEmpty();
    }

    //region CountText
    @Test
    public void countText_ShouldBe2_WhenLoadedEventsReturns2Events() {
        resizeDummyEvents(2);

        sut.getFetchCommand().execute();

        assertThat(sut.getCountText()).isEqualTo("2 events");
    }

    @Test
    public void countText_ShouldBe3_WhenLoadedEventsReturns3Events() {
        resizeDummyEvents(3);

        sut.getFetchCommand().execute();

        assertThat(sut.getCountText()).isEqualTo("3 events");
    }

    @Test
    public void countText_ShouldBeSingular_WhenLoadedEventsReturns1Event() {
        resizeDummyEvents(1);

        sut.getFetchCommand().execute();

        assertThat(sut.getCountText()).isEqualTo("1 event");
    }

    @Test
    public void countText_ShouldBeNoEvents_WhenLoadedEventsReturns0Events() {
        resizeDummyEvents(0);

        sut.getFetchCommand().execute();

        assertThat(sut.getCountText()).isEqualTo("No events");
    }

    @Test
    public void countText_ShouldBeEmpty_BeforeLoading() {
        assertThat(sut.getCountText()).isEmpty();
    }

    @Test
    public void countText_ShouldBeEmpty_WhenLoadedEventsReturnsNull() {
        when(mockEventService.loadEvents(any(Scheduler.class))).thenReturn(Observable.<List<Event>>just(null));

        sut.getFetchCommand().execute();

        assertThat(sut.getCountText()).isEmpty();
    }
    //endregion

    //region Progress, Retry

    @Test
    public void initiallyFetchEventOperationStateShouldBeDefault() {
        assertThat(sut.getLoadOperationState()).isEqualTo(OperationState.DEFAULT);
    }

    @Test
    public void fetchCommandShouldChangeStateToRunning() {
        when(mockEventService.loadEvents(any(Scheduler.class))).thenReturn(Observable.<List<Event>>never());

        sut.getFetchCommand().execute();

        assertThat(sut.getLoadOperationState()).isEqualTo(OperationState.RUNNING);
    }

    @Test
    public void afterLoadingStateShouldChangeToSuccessful() {
        sut.getFetchCommand().execute();

        assertThat(sut.getLoadOperationState()).isEqualTo(OperationState.SUCCESSFUL);
    }

    @Test
    public void onErrorStateShouldChangeToFailed() {
        when(mockEventService.loadEvents(any(Scheduler.class))).thenReturn(Observable.<List<Event>>error(new Exception()));

        sut.getFetchCommand().execute();

        assertThat(sut.getLoadOperationState()).isEqualTo(OperationState.FAILED);
    }

    @Test
    public void whenLoadStateChangesUpdateListenerShouldGetInvoked() {
        // TODO: Remove/Cleanup test
        EventsVM.OnEventsVMUpdatedListener listener = mock(EventsVM.OnEventsVMUpdatedListener.class);
        sut.setListener(listener);
        BehaviorSubject<List<Event>> eventsBehaviour = BehaviorSubject.create();
        when(mockEventService.loadEvents(any(Scheduler.class))).thenReturn(eventsBehaviour);
        TestSubscriber<OperationState> testSubscriber = new TestSubscriber();
        sut.getLoadOperationStateObservable().subscribe(testSubscriber);

        sut.getFetchCommand().execute();

        testSubscriber.assertValueCount(2);

        eventsBehaviour.onNext(dummyEvents);

        testSubscriber.assertValueCount(3);

        eventsBehaviour.onError(new Exception());

        testSubscriber.assertValueCount(4);
    }

    //endregion
}
