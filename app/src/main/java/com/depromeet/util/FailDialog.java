package com.depromeet.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.depromeet.R;

public class FailDialog extends Dialog {
    Context context;

    public FailDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fail);
    }
}
