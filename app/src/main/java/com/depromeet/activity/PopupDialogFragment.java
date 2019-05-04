package com.depromeet.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.depromeet.R;

public class PopupDialogFragment extends DialogFragment {

    private Button mbackBtn;
    private Button mheartBtn;

    private TextView mNameText;
    private TextView mLikeCountText;

    private TextView mFirstText;
    private TextView mFirstNameText;

    private TextView mSecondText;
    private TextView mSecondNameText;

    private TextView mThirdText;
    private TextView mThirdNameText;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = getActivity().getLayoutInflater().inflate(R.layout.activity_popup, null);

        builder.setView(view);

        mbackBtn = view.findViewById(R.id.tv_popup_back);
        mbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        mheartBtn = view.findViewById(R.id.tv_popup_heart);
        mNameText = view.findViewById(R.id.tv_popup_name);
        mLikeCountText = view.findViewById(R.id.tv_popup_like);

        mFirstText = view.findViewById(R.id.tv_popup_first);
        mFirstNameText = view.findViewById(R.id.tv_popup_first_name);

        mSecondText = view.findViewById(R.id.tv_popup_second);
        mSecondNameText = view.findViewById(R.id.tv_popup_second_name);

        mThirdText = view.findViewById(R.id.tv_popup_third);
        mThirdNameText = view.findViewById(R.id.tv_popup_third_name);


        return builder.create();

    }

}
