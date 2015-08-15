package com.androidx.androidx.view;


import com.androidx.androidx.BuildConfig;
import com.androidx.androidx.viewmodel.EventItemVM;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EventViewTest {

    private EventView sut;

    @Before
    public void setUp() throws Exception {
        sut = new EventView(RuntimeEnvironment.application);
    }

    @Test
    public void userNameTextView_ShouldExist() {
        assertThat(sut.getUserNameTextView()).isNotNull();
    }

    @Test
    public void repositoryNameTextView_ShouldExist() {
        assertThat(sut.getRepositoryNameTextView()).isNotNull();
    }

    @Test
    public void bindWithUser1_ShouldDisplayUser1() {
        EventItemVM vm = mock(EventItemVM.class);
        when(vm.getUserLogin()).thenReturn("user1");

        sut.bindViewModel(vm);

        assertThat(sut.getUserNameTextView().getText()).isEqualTo("user1");
    }

    // TODO: Parameterise test
    @Test
    public void bindWithUser2_ShouldDisplayUser2() {
        EventItemVM vm = mock(EventItemVM.class);
        when(vm.getUserLogin()).thenReturn("user2");

        sut.bindViewModel(vm);

        assertThat(sut.getUserNameTextView().getText()).isEqualTo("user2");
    }

    @Test
    public void bindWithRepo1_ShouldDisplayRepo1() {
        EventItemVM vm = mock(EventItemVM.class);
        when(vm.getRepositoryName()).thenReturn("repo1");

        sut.bindViewModel(vm);

        assertThat(sut.getRepositoryNameTextView().getText()).isEqualTo("repo1");
    }

    // TODO: Parameterise test
    @Test
    public void bindWithRepo2_ShouldDisplayRepo2() {
        EventItemVM vm = mock(EventItemVM.class);
        when(vm.getRepositoryName()).thenReturn("repo2");

        sut.bindViewModel(vm);

        assertThat(sut.getRepositoryNameTextView().getText()).isEqualTo("repo2");
    }


}
