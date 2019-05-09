package com.depromeet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.depromeet.R;
import com.depromeet.data.Poem;

import java.util.ArrayList;

public class PoemGridAdapter extends BaseAdapter {
    private ArrayList<Poem> poems;

    public PoemGridAdapter(ArrayList<Poem> poems) {
        this.poems = poems;
    }

    public void setPoems(ArrayList<Poem> poems) {
        this.poems = poems;
    }

    public void addPoem(Poem poem) {
        poems.add(poem);
    }

    public void clear() {
        poems.clear();
    }

    @Override
    public int getCount() {
        return poems.size();
    }

    @Override
    public Poem getItem(int i) {
        return poems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        Poem poemItem = poems.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_poem, viewGroup, false);
        }

        TextView userNameText = (TextView) view.findViewById(R.id.tv_poem_name);
        TextView titleText1 = (TextView) view.findViewById(R.id.tv_poem_title1);
        TextView titleText2 = (TextView) view.findViewById(R.id.tv_poem_title2);
        TextView titleText3 = (TextView) view.findViewById(R.id.tv_poem_title3);
        TextView contentText1 = (TextView) view.findViewById(R.id.tv_poem_content1);
        TextView contentText2 = (TextView) view.findViewById(R.id.tv_poem_content2);
        TextView contentText3 = (TextView) view.findViewById(R.id.tv_poem_content3);
        LinearLayout poemLayout = (LinearLayout) view.findViewById(R.id.layout_poem);

        userNameText.setText(poemItem.getUserName());
        titleText1.setText(poemItem.getWordFirst().substring(0, 1));
        titleText2.setText(poemItem.getWordSecond().substring(0, 1));
        titleText3.setText(poemItem.getWordThird().substring(0, 1));

        contentText1.setText(poemItem.getWordFirst().substring(1));
        contentText2.setText(poemItem.getWordSecond().substring(1));
        contentText3.setText(poemItem.getWordThird().substring(1));

        return view;
    }
}
