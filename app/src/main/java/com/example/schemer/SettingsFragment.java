package com.example.schemer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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

    private CheckBox projectTasksFlag;
    private CheckBox projectDescriptionFlag;
    private CheckBox projectIdeasFlag;
    private CheckBox projectScriptFlag;

    public static SQLiteDatabase appDataBase;
    public static Cursor appData;
    public boolean[] flags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_settings, container, false);

        projectTasksFlag = inf.findViewById(R.id.settings_fragment_projectTasksFlag);
        projectDescriptionFlag = inf.findViewById(R.id.settings_fragment_projectDescriptionFlag);
        projectIdeasFlag = inf.findViewById(R.id.settings_fragment_projectIdeasFlag);
        projectScriptFlag = inf.findViewById(R.id.settings_fragment_projectScriptFlag);

        CheckBox[] checkBoxes = new CheckBox[]{projectTasksFlag, projectDescriptionFlag, projectIdeasFlag, projectScriptFlag};
        for (int i = 0; i <= 3; i++){
            if(flags[i]){
                checkBoxes[i].setChecked(true);
            }
        }

        return inf;
    }
}