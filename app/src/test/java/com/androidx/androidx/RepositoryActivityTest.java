package com.androidx.androidx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class RepositoryActivityTest {
    @Test
    public void testViewModel_ShouldExist_AfterCreate() throws Exception {
        RepositoryActivity sut = Robolectric.setupActivity(RepositoryActivity.class);
        assertThat(sut.vm).isNotNull();
    }
}
