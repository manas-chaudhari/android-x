package com.androidx.androidx.utils;

import android.view.View;

import rx.subjects.BehaviorSubject;

import static org.assertj.core.api.Assertions.assertThat;

public class BindingTest {
    public static void assertVisibilityBinding(View view, BehaviorSubject<Boolean> visibilityBehaviour) {
        visibilityBehaviour.onNext(true);
        assertThat(view.getVisibility()).isEqualTo(View.VISIBLE);

        visibilityBehaviour.onNext(false);
        assertThat(view.getVisibility()).isNotEqualTo(View.VISIBLE);

        visibilityBehaviour.onNext(true);
        assertThat(view.getVisibility()).isEqualTo(View.VISIBLE);
    }
}
