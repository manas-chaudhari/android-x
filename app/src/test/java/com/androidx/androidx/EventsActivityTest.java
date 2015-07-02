package com.androidx.androidx;

import android.widget.TextView;

import com.androidx.androidx.mvvm.Command;
import com.androidx.androidx.viewmodel.EventsVM;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowToast;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventsActivityTest {
    EventsActivity sut;
    EventsVM mockVM;

    @Before
    public void setup() {
        sut = Robolectric.setupActivity(EventsActivity.class);
        mockVM = mock(EventsVM.class);
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
    public void countText_ShouldExist() {
        assertThat(sut.countText).isNotNull();
    }

    @Test
    public void settingViewModel_ShouldInvokeSetListener() {
        sut.setViewModel(mockVM);

        verify(mockVM).setListener(any(EventsVM.OnEventsVMUpdatedListener.class));
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

    private EventsVM.OnEventsVMUpdatedListener setMockVMAndCaptureListener() {
        sut.setViewModel(mockVM);

        ArgumentCaptor<EventsVM.OnEventsVMUpdatedListener> listenerArgumentCaptor = ArgumentCaptor.forClass(EventsVM.OnEventsVMUpdatedListener.class);
        verify(mockVM).setListener(listenerArgumentCaptor.capture());
        return listenerArgumentCaptor.getValue();
    }

    @Test
    public void settingVM_ShouldUpdateCount() {
        when(mockVM.getCountText()).thenReturn("4 events");

        sut.setViewModel(mockVM);

        assertThat(sut.countText.getText()).isEqualTo("4 events");
    }

    @Test
    public void initialCountText_ShouldBeEmpty() {
        assertThat(sut.countText.getText()).isEmpty();
    }
}
