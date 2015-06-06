package com.tinyowl.mvvm.utils;

import android.view.View;
import android.widget.TextView;

import com.tinyowl.mvvm.core.Command;

public class BindUtils {

    public static void bindClick(View view, final Command command) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.run();
            }
        });
    }

}
