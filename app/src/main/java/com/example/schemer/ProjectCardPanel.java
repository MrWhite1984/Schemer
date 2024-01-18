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

import java.util.Objects;

public class ProjectCardPanel extends AppCompatActivity {
    public static String projectName;
    public static int projectCode;

    private TextView projectNameLabel;

    //DB
    public static SQLiteDatabase appDataBase;
    private Cursor appData;

    ProjectCardBinding binding;
    FragmentActivity activity;

    RelativeLayout relativeLayout;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding.projectNameLabel.setText(projectName);
        //projectNameLabel = findViewById(R.id.projectNameLabel);
        //projectNameLabel.setText(projectName);


        appData = appDataBase.rawQuery("SELECT * FROM Projects WHERE ID = ?", new String[]{String.valueOf(projectCode)});

        binding = ProjectCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new TaskFragment());
        binding.BottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.tab_tasks){
                TaskFragment taskFragment = new TaskFragment();
                replaceFragment(taskFragment);
            }
            else if(item.getItemId() == R.id.tab_description){
                replaceFragment(new DescriptionsFragment());
            }
            else if(item.getItemId() == R.id.tab_ideas){
                replaceFragment(new IdeasFragment());
            }
            else if(item.getItemId() == R.id.tab_script){
                replaceFragment(new ScriptFragment());
            }
            else if(item.getItemId() == R.id.tab_settings){
                replaceFragment(new SettingsFragment());
            }
            return true;
        });
/*
        while (appData.moveToPosition(1)){
            int qw = appData.getInt(2);
            if(qw == 1){
                tasksSV = new ScrollView(getApplicationContext());
                //tasksSV.set
            }
        }*/
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.commit();
    }
}
