package com.example.schemer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import classes.ButtonCreator;
import classes.DBHandler;
import classes.Project;

public class MainActivity extends Activity {

    private Button addProjectButton;
    private Button projectButtonExample;
    private LinearLayout content;
    
    List<Button> projectsButton = new ArrayList<Button>();
    SQLiteDatabase appDataBase;
    private List<String> projectsNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projectButtonExample = findViewById(R.id.projectButtonExample);
        content = findViewById(R.id.content);

        appDataBase = openOrCreateDatabase("projectData.db", MODE_PRIVATE, null);
        appDataBase = DBHandler.checkingAndUpdatingTheDatabase(appDataBase);

        Cursor appData = appDataBase.rawQuery("SELECT * FROM Projects", null);
        while (appData.moveToNext()){
            projectsNames.add(appData.getString(1));
        }

        for(String name : projectsNames){
            content.addView(ButtonCreator.CreateButton(name, projectButtonExample.getContext()));
        }

        projectButtonExample.setVisibility(View.GONE);

        addProjectButton = findViewById(R.id.addProjectButton);
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddProjectPanel.class));
            }
        });
    }

}

