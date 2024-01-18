package com.example.schemer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

    private TextView projectNameLabel;

    //DB
    public static SQLiteDatabase appDataBase;
    private Cursor appData;

    ProjectCardBinding binding;

    RelativeLayout relativeLayout;

    private boolean[] flags;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding.projectNameLabel.setText("projectName");
        //projectNameLabel = findViewById(R.id.projectNameLabel);
        //projectNameLabel.setText(projectName);


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
            replaceFragment(new TaskFragment());
        }
        else if(flags[1]){
            replaceFragment(new DescriptionsFragment());
            binding.BottomNavigationView.setSelectedItemId(R.id.tab_description);
        }
        else if(flags[2]){
            replaceFragment(new IdeasFragment());
            binding.BottomNavigationView.setSelectedItemId(R.id.tab_ideas);
        }
        else if(flags[3]){
            replaceFragment(new ScriptFragment());
            binding.BottomNavigationView.setSelectedItemId(R.id.tab_script);
        }
        else {
            replaceFragment(new SettingsFragment());
            binding.BottomNavigationView.setSelectedItemId(R.id.tab_settings);
        }
        binding.BottomNavigationView.setOnItemSelectedListener(item -> {
            //flagTrue
            if (item.getItemId() == R.id.tab_tasks && flags[0]){
                TaskFragment taskFragment = new TaskFragment();
                replaceFragment(taskFragment);
            }
            else if(item.getItemId() == R.id.tab_description && flags[1]){
                replaceFragment(new DescriptionsFragment());
            }
            else if(item.getItemId() == R.id.tab_ideas && flags[2]){
                replaceFragment(new IdeasFragment());
            }
            else if(item.getItemId() == R.id.tab_script && flags[3]){
                replaceFragment(new ScriptFragment());
            }
            else if(item.getItemId() == R.id.tab_settings){
                replaceFragment(new SettingsFragment());
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



    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.commit();
    }
}
