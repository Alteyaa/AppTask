package com.apptask.fragment;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apptask.R;


/**
 * A simple {@link Fragment} subclass.
 */


public class BoardFragment extends Fragment {


    public BoardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textDesc = view.findViewById(R.id.textDesc);
        RelativeLayout rl = view.findViewById(R.id.backround_view);
        int pos = getArguments().getInt("pos");

        switch (pos) {
            case 0:

                textTitle.setText("Here you can find everything");
                textDesc.setText("you were looking for");
                imageView.setImageResource(R.drawable.photoshoot);
                rl.setBackgroundColor(Color.LTGRAY);
                break;
            case 1:

                textTitle.setText("Your life consists of adventure");
                textDesc.setText("Breath");
                rl.setBackgroundColor(Color.TRANSPARENT);
                imageView.setImageResource(R.drawable.photoshoot2);

                break;

            case 2:

                textTitle.setText("How you love yourself");
                textDesc.setText("is how you teach others to love you");
                rl.setBackgroundColor(Color.WHITE);
                imageView.setImageResource(R.drawable.photoshoot4);
                break;
        }

        return view;
    }


}
