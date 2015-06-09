package com.tinyowl.mvvm.utils;

import android.view.View;
import android.widget.TextView;

import com.tinyowl.mvvm.core.Command;

import org.w3c.dom.Text;

import rx.Observable;
import rx.functions.Action1;

public class BindUtils {

    public static void bindClick(View view, final Command command) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.run();
            }
        });
    }

    public static void bindText(final TextView textView, Observable<String> stringObservable) {
        stringObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                textView.setText(s);
            }
        });
    }

}
