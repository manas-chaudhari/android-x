package com.androidx.androidx;

import com.androidx.androidx.model.Event;
import com.androidx.androidx.viewmodel.RepositoryVM;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class RepositoryActivityTest {
    // TODO: Remove these tests and add instrumentation test

    @Test
    public void testViewModel_ShouldExist_AfterCreate() throws Exception {
        RepositoryActivity sut = Robolectric.setupActivity(RepositoryActivity.class);
        assertThat(sut.vm).isNotNull();
    }

    @Test
    public void testTitle_ShouldBeBound_OnCreate() throws Exception {
        RepositoryActivity sut = Robolectric.setupActivity(RepositoryActivity.class);

        // Ideally, test should be that vm should get bound on create
        assertThat(sut.getTitle()).isEqualTo(sut.vm.title);
    }

    @Test
    public void testTitle_ShouldBeBound() throws Exception {
        RepositoryActivity sut = Robolectric.setupActivity(RepositoryActivity.class);
        Event.Repository repository = new Event.Repository();
        RepositoryVM repositoryVM = new RepositoryVM(repository);

        sut.bindViewModel(repositoryVM);

        assertThat(sut.getTitle()).isEqualTo(repositoryVM.title);
    }
}
