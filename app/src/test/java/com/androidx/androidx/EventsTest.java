package com.androidx.androidx;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventsTest {
    EventsActivity eventsActivity;

    @Before
    public void setup() {
        eventsActivity = Robolectric.setupActivity(EventsActivity.class);
    }

    @Test
    public void textViewHello_shouldExist() {
        TextView v = (TextView) eventsActivity.findViewById(R.id.txt_hello);

        assertTrue(v != null);
    }

    @Test
    public void textViewHello_shouldDisplayCorrectText() {
        TextView v = (TextView) eventsActivity.findViewById(R.id.txt_hello);

        assertTrue(v.getText().equals("Hello"));
    }

}
