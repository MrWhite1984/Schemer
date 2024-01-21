package com.example.schemer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScriptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScriptFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScriptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScriptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScriptFragment newInstance(String param1, String param2) {
        ScriptFragment fragment = new ScriptFragment();
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

    private ImageButton script_fragment_top_bar_save_button;
    private EditText script_fragment_edit_text;

    public SQLiteDatabase appDataBase;
    public int PID;
    private int ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_script, container, false);
        script_fragment_edit_text = inf.findViewById(R.id.script_fragment_edit_text);
        Cursor appData = appDataBase.rawQuery("SELECT * FROM Scripts WHERE PID = ?", new String[]{String.valueOf(PID)});
        while (appData.moveToNext()){
            ID = appData.getInt(0);
            script_fragment_edit_text.setText(appData.getString(2));
        }

        script_fragment_top_bar_save_button = inf.findViewById(R.id.script_fragment_top_bar_save_button);
        script_fragment_top_bar_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues row = new ContentValues();
                row.put("Data", script_fragment_edit_text.getText().toString());
                appDataBase.update("Scripts", row, "ID = ?", new String[]{String.valueOf(ID)});
                script_fragment_top_bar_save_button.setVisibility(View.GONE);
            }
        });

        script_fragment_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                script_fragment_top_bar_save_button.setVisibility(View.VISIBLE);
            }
        });

        return inf;
    }
}