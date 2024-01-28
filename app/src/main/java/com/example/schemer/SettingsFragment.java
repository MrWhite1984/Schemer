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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

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

    private ImageButton fragment_settings_top_bar_save_button;

    private CheckBox projectTasksFlag;
    private CheckBox projectDescriptionFlag;
    private CheckBox projectIdeasFlag;
    private CheckBox projectScriptFlag;

    private EditText fragment_settings_project_name;

    public SQLiteDatabase appDataBase;
    public static Cursor appData;
    public boolean[] flags;
    public int projectCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_settings, container, false);

        fragment_settings_top_bar_save_button = inf.findViewById(R.id.fragment_settings_top_bar_save_button);

        fragment_settings_project_name = inf.findViewById(R.id.fragment_settings_project_name);

        projectTasksFlag = inf.findViewById(R.id.settings_fragment_projectTasksFlag);
        projectDescriptionFlag = inf.findViewById(R.id.settings_fragment_projectDescriptionFlag);
        projectIdeasFlag = inf.findViewById(R.id.settings_fragment_projectIdeasFlag);
        projectScriptFlag = inf.findViewById(R.id.settings_fragment_projectScriptFlag);

        projectTasksFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragment_settings_top_bar_save_button.setVisibility(View.VISIBLE);
            }
        });
        projectDescriptionFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragment_settings_top_bar_save_button.setVisibility(View.VISIBLE);
            }
        });
        projectIdeasFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragment_settings_top_bar_save_button.setVisibility(View.VISIBLE);
            }
        });
        projectScriptFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragment_settings_top_bar_save_button.setVisibility(View.VISIBLE);
            }
        });

        fragment_settings_top_bar_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues row = new ContentValues();
                row.put("Name", fragment_settings_project_name.getText().toString());
                row.put("Tasks", projectTasksFlag.isChecked());
                row.put("Description", projectDescriptionFlag.isChecked());
                row.put("Ideas", projectIdeasFlag.isChecked());
                row.put("Script", projectScriptFlag.isChecked());
                appDataBase.update("Projects", row, "ID = ?", new String[]{String.valueOf(projectCode)});
                fragment_settings_top_bar_save_button.setVisibility(View.GONE);
                ProjectCardPanel.flags = null;
                ProjectCardPanel.flags = new boolean[] {projectTasksFlag.isChecked(), projectDescriptionFlag.isChecked(), projectIdeasFlag.isChecked(), projectScriptFlag.isChecked()};
            }
        });


        fragment_settings_project_name.setText(ProjectCardPanel.projectName);
        fragment_settings_project_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragment_settings_top_bar_save_button.setVisibility(View.VISIBLE);
            }
        });

        CheckBox[] checkBoxes = new CheckBox[]{projectTasksFlag, projectDescriptionFlag, projectIdeasFlag, projectScriptFlag};
        for (int i = 0; i <= 3; i++){
            if(flags[i]){
                checkBoxes[i].setChecked(true);
            }
        }

        fragment_settings_top_bar_save_button.setVisibility(View.GONE);

        return inf;
    }
}