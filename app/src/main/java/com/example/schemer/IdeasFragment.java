package com.example.schemer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import classes.ButtonCreator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdeasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdeasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IdeasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdeasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdeasFragment newInstance(String param1, String param2) {
        IdeasFragment fragment = new IdeasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private LinearLayout linearLayout;

    private ImageButton ideas_fragment_top_bar_add_button;

    public SQLiteDatabase appDataBase;
    public int PID;
    private Cursor appData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_ideas, container, false);

        linearLayout = inf.findViewById(R.id.ideas_fragment_ll);
        ideas_fragment_top_bar_add_button = inf.findViewById(R.id.ideas_fragment_top_bar_add_button);
        //appData = appDataBase.rawQuery("SELECT * FROM Ideas WHERE PID = ?", new String[]{String.valueOf(PID)});
        ideas_fragment_top_bar_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appData = appDataBase.rawQuery("SELECT * FROM Ideas WHERE PID = ?", new String[]{String.valueOf(PID)});
                IdeasDataActivity.creating = true;
                IdeasDataActivity.PID = PID;
                IdeasDataActivity.appDataBase = appDataBase;
                appData = appDataBase.rawQuery("SELECT * FROM Ideas ", null);
                IdeasDataActivity.quantityOfRecords = appData.getCount();
                startActivity(new Intent(getContext(), IdeasDataActivity.class));
            }
        });

        return inf;
    }

    @Override
    public void onResume() {
        super.onResume();
        appData = appDataBase.rawQuery("SELECT * FROM Ideas WHERE PID = ?", new String[]{String.valueOf(PID)});
        linearLayout.removeAllViews();
        while (appData.moveToNext()){
            Button button = ButtonCreator.CreateButton(appData.getString(2), getContext(), appData.getInt(0));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IdeasDataActivity.creating = false;
                    IdeasDataActivity.PID = PID;
                    IdeasDataActivity.appDataBase = appDataBase;
                    IdeasDataActivity.ID = Integer.parseInt(button.getContentDescription().toString());
                    IdeasDataActivity.ideaText = button.getText().toString();
                    startActivity(new Intent(getContext(), IdeasDataActivity.class));
                }
            });
            linearLayout.addView(button);
        }
    }
}