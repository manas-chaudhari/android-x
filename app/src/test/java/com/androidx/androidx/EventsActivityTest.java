package com.androidx.androidx;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.androidx.androidx.model.Event;
import com.androidx.androidx.mvvm.Command;
import com.androidx.androidx.utils.BindingTest;
import com.androidx.androidx.viewmodel.EventItemVM;
import com.androidx.androidx.viewmodel.EventsVM;
import com.androidx.androidx.viewmodel.OperationState;
import com.androidx.androidx.viewmodel.OperationVM;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventsActivityTest {
    EventsActivity sut;
    EventsVM mockVM;
    private OperationVM mockOperationVM;

    @Before
    public void setup() {
        sut = Robolectric.setupActivity(EventsActivity.class);
        mockVM = mock(EventsVM.class);
        mockOperationVM = spy(new OperationVM(Observable.<OperationState>empty()));
        when(mockVM.getLoadOperationVM()).thenReturn(mockOperationVM);
    }

    @Test
    public void textViewHello_ShouldExist() {
        TextView v = (TextView) sut.findViewById(R.id.txt_hello);

        assertThat(v).isNotNull();
    }

    @Test
    public void textViewHello_ShouldDisplayCorrectText() {
        TextView v = (TextView) sut.findViewById(R.id.txt_hello);

        assertThat(v.getText()).isEqualTo("Hello");
    }

    @Test
    public void settingsFromMenu_ShouldShowToast() {
        sut.onOptionsItemSelected(new RoboMenuItem(R.id.action_settings));

        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Settings Clicked");
    }

    @Test
    public void fetchButton_ShouldExist() {
        assertThat(sut.fetchButton).isNotNull();
    }

    @Test
    public void viewModel_ShouldExist() {
        assertThat(sut.getViewModel()).isNotNull();
    }

    @Test
    public void fetchButton_ShouldInvokeFetchCommand() {
        Command mockCommand = mock(Command.class);
        when(mockVM.getFetchCommand()).thenReturn(mockCommand);
        sut.setViewModel(mockVM);

        sut.fetchButton.performClick();

        verify(mockCommand).execute();
    }

    @Test
    public void settingViewModel_ShouldInvokeSetListener() {
        sut.setViewModel(mockVM);

        verify(mockVM).setListener(any(EventsVM.OnEventsVMUpdatedListener.class));
    }

    //region Helpers
    private EventsVM.OnEventsVMUpdatedListener setMockVMAndCaptureListener() {
        sut.setViewModel(mockVM);

        ArgumentCaptor<EventsVM.OnEventsVMUpdatedListener> listenerArgumentCaptor = ArgumentCaptor.forClass(EventsVM.OnEventsVMUpdatedListener.class);
        verify(mockVM).setListener(listenerArgumentCaptor.capture());
        return listenerArgumentCaptor.getValue();
    }
    //endregion

    //region Count Text
    @Test
    public void countText_ShouldExist() {
        assertThat(sut.countText).isNotNull();
    }

    @Test
    public void initialCountText_ShouldBeEmpty() {
        assertThat(sut.countText.getText()).isEmpty();
    }

    @Test
    public void loadingEvents_ShouldUpdateCountText() {
        EventsVM.OnEventsVMUpdatedListener listener = setMockVMAndCaptureListener();
        when(mockVM.getCountText()).thenReturn("50 events");

        listener.onEventsUpdated();

        assertThat(sut.countText.getText()).isEqualTo("50 events");
    }

    @Test
    public void loading3Events_ShouldUpdateEventsCountTo3() {
        EventsVM.OnEventsVMUpdatedListener listener = setMockVMAndCaptureListener();
        when(mockVM.getCountText()).thenReturn("3 events");

        listener.onEventsUpdated();

        assertThat(sut.countText.getText()).isEqualTo("3 events");
    }

    @Test
    public void settingVM_ShouldUpdateCount() {
        when(mockVM.getCountText()).thenReturn("4 events");

        sut.setViewModel(mockVM);

        assertThat(sut.countText.getText()).isEqualTo("4 events");
    }

    //endregion

    //region Events List
    private List<EventItemVM> getDummyEventItems(int number) {
        List<EventItemVM> dummyEventItems = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            dummyEventItems.add(new EventItemVM(new Event()));
        }
        return dummyEventItems;
    }

    @Test
    public void eventsListView_ShouldExist() throws Exception {
        assertThat(sut.eventsListView).isNotNull();
    }

    @Test
    public void eventsListViewAdapter_ShouldExist() {
        assertThat(sut.eventsListView.getAdapter()).isNotNull();
    }

    @Test
    public void initiallyEventsListViewAdapter_ShouldBeEmpty() {
        assertThat(sut.eventsListView.getAdapter().getCount()).isZero();
    }

    @Test
    public void loading2Events_ShouldShow2Items() {
        EventsVM.OnEventsVMUpdatedListener listener = setMockVMAndCaptureListener();
        when(mockVM.getEventItems()).thenReturn(getDummyEventItems(2));

        listener.onEventsUpdated();

        assertThat(sut.eventsListView.getAdapter().getCount()).isEqualTo(2);
    }

    @Test
    public void loading3Events_ShouldShow3Items() {
        EventsVM.OnEventsVMUpdatedListener listener = setMockVMAndCaptureListener();
        when(mockVM.getEventItems()).thenReturn(getDummyEventItems(3));

        listener.onEventsUpdated();

        assertThat(sut.eventsListView.getAdapter().getCount()).isEqualTo(3);
    }
    //endregion

    //region Progress and Retry

    @Test
    public void progressBar_ShouldExist() {
        assertThat(sut.progressBar).isNotNull();
    }

    @Test
    public void progressBar_ShouldBindToRunningVisibility() {
        BehaviorSubject<Boolean> visibilityBehaviour = BehaviorSubject.create(true);
        when(mockOperationVM.getRunningViewVisibility()).thenReturn(visibilityBehaviour);
        setMockVMAndCaptureListener();

        BindingTest.assertVisibilityBinding(sut.progressBar, visibilityBehaviour);
    }

    @Test
    public void listView_ShouldBindToSuccessfulVisibility() {
        BehaviorSubject<Boolean> visibilityBehaviour = BehaviorSubject.create(true);
        when(mockOperationVM.getSuccessfulViewVisibility()).thenReturn(visibilityBehaviour);
        setMockVMAndCaptureListener();

        BindingTest.assertVisibilityBinding(sut.eventsListView, visibilityBehaviour);
    }

    @Test
    public void fetchButton_ShouldBindToFailedVisibility() {
        BehaviorSubject<Boolean> visibilityBehaviour = BehaviorSubject.create(true);
        when(mockOperationVM.getFailedViewVisibility()).thenReturn(visibilityBehaviour);
        setMockVMAndCaptureListener();

        BindingTest.assertVisibilityBinding(sut.fetchButton, visibilityBehaviour);
    }

    @Test
    public void fetchButton_ShouldBeVisibleInitially() {
        assertThat(sut.fetchButton.getVisibility()).isEqualTo(View.VISIBLE);
    }
    //endregion

    //region Extras
    @Test
    public void adapter_ShouldBeSetOnlyOnce() {
        sut.eventsListView = mock(ListView.class);

        sut.onEventsUpdated();

        verifyZeroInteractions(sut.eventsListView);
    }
    //endregion

}
