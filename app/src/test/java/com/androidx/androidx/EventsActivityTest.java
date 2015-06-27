package com.androidx.androidx;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowToast;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventsActivityTest {
    EventsActivity eventsActivity;

    @Before
    public void setup() {
        eventsActivity = Robolectric.setupActivity(EventsActivity.class);
    }

    @Test
    public void textViewHello_ShouldExist() {
        TextView v = (TextView) eventsActivity.findViewById(R.id.txt_hello);

        assertThat(v).isNotNull();
    }

    @Test
    public void textViewHello_ShouldDisplayCorrectText() {
        TextView v = (TextView) eventsActivity.findViewById(R.id.txt_hello);

        assertThat(v.getText()).isEqualTo("Hello");
    }

    @Test
    public void settingsFromMenu_ShouldShowToast() {
        eventsActivity.onOptionsItemSelected(new RoboMenuItem(R.id.action_settings));

        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Settings Clicked");
    }

}
