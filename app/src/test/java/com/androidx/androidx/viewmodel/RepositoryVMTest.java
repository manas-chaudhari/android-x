package com.androidx.androidx.viewmodel;

import com.androidx.androidx.model.Event;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RepositoryVMTest {
    @Test
    public void testTitle_ShouldBeUpperCase() {
        Event.Repository r = new Event.Repository();
        r.name = "test name";
        RepositoryVM sut = new RepositoryVM(r);
        assertThat(sut.title, is("TEST NAME"));
    }
}
