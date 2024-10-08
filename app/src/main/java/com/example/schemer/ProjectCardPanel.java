package com.example.schemer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schemer.databinding.ProjectCardBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectCardPanel extends AppCompatActivity {
    public static String projectName;
    public static int projectCode;

    //DB
    public static SQLiteDatabase appDataBase;
    private Cursor appData;

    ProjectCardBinding binding;


    public static boolean[] flags;

    private ImageButton project_card_activity_top_bar_back_button;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flags = null;


        appData = appDataBase.rawQuery("SELECT * FROM Projects WHERE ID = ?", new String[]{String.valueOf(projectCode)});
        flags = new boolean[] {false, false, false, false};
        while (appData.moveToNext()){
            if(appData.getInt(2) == 1){
                flags[0] = true;
            }
            if(appData.getInt(3) == 1){
                flags[1] = true;
            }
            if(appData.getInt(4) == 1){
                flags[2] = true;
            }
            if(appData.getInt(5) == 1){
                flags[3] = true;
            }
        }

        binding = ProjectCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(flags[0]){
            TaskFragment taskFragment = new TaskFragment();
            taskFragment.appDataBase = appDataBase;
            taskFragment.PID = projectCode;
            replaceFragment(taskFragment);
        }
        else if(flags[1]){
            DescriptionsFragment descriptionsFragment = new DescriptionsFragment();
            descriptionsFragment.appDataBase = appDataBase;
            descriptionsFragment.PID = projectCode;
            replaceFragment(descriptionsFragment);
            binding.BottomNavigationView.setSelectedItemId(R.id.tab_description);
        }
        else if(flags[2]){
            IdeasFragment ideasFragment = new IdeasFragment();
            ideasFragment.appDataBase = appDataBase;
            ideasFragment.PID = projectCode;
            replaceFragment(ideasFragment);
            binding.BottomNavigationView.setSelectedItemId(R.id.tab_ideas);
        }
        else if(flags[3]){
            ScriptFragment scriptFragment = new ScriptFragment();
            scriptFragment.appDataBase = appDataBase;
            scriptFragment.PID = projectCode;
            replaceFragment(scriptFragment);
            binding.BottomNavigationView.setSelectedItemId(R.id.tab_script);
        }
        else {
            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.appDataBase = appDataBase;
            settingsFragment.projectCode = projectCode;
            settingsFragment.flags = flags;
            replaceFragment(settingsFragment);
            binding.BottomNavigationView.setSelectedItemId(R.id.tab_settings);
        }
        binding.BottomNavigationView.setOnItemSelectedListener(item -> {
            //flagTrue
            if (item.getItemId() == R.id.tab_tasks && flags[0]){
                TaskFragment taskFragment = new TaskFragment();
                taskFragment.appDataBase = appDataBase;
                taskFragment.PID = projectCode;
                replaceFragment(taskFragment);
            }
            else if(item.getItemId() == R.id.tab_description && flags[1]){
                DescriptionsFragment descriptionsFragment = new DescriptionsFragment();
                descriptionsFragment.appDataBase = appDataBase;
                descriptionsFragment.PID = projectCode;
                replaceFragment(descriptionsFragment);
            }
            else if(item.getItemId() == R.id.tab_ideas && flags[2]){
                IdeasFragment ideasFragment = new IdeasFragment();
                ideasFragment.appDataBase = appDataBase;
                ideasFragment.PID = projectCode;
                replaceFragment(ideasFragment);
            }
            else if(item.getItemId() == R.id.tab_script && flags[3]){
                ScriptFragment scriptFragment = new ScriptFragment();
                scriptFragment.appDataBase = appDataBase;
                scriptFragment.PID = projectCode;
                replaceFragment(scriptFragment);
            }
            else if(item.getItemId() == R.id.tab_settings){
                SettingsFragment settingsFragment = new SettingsFragment();
                settingsFragment.appDataBase = appDataBase;
                settingsFragment.projectCode = projectCode;
                settingsFragment.flags = flags;
                replaceFragment(settingsFragment);
            }
            //flagFalse
            else if((item.getItemId() == R.id.tab_tasks && !flags[0]) ||
                    (item.getItemId() == R.id.tab_description && !flags[1]) ||
                    (item.getItemId() == R.id.tab_ideas && !flags[2]) ||
                    (item.getItemId() == R.id.tab_script && !flags[3])){
                replaceFragment(new StubFragment());
            }
            return true;
        });

        project_card_activity_top_bar_back_button = findViewById(R.id.project_card_activity_top_bar_back_button);
        project_card_activity_top_bar_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectCode = 0;
                finish();
            }
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.commit();
    }


}
