package com.depromeet.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.depromeet.R;

public class PassDialog extends Dialog {
    Context context;

    public PassDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pass);
    }
}
