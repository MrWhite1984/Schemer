package com.example.schemer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import classes.DBHandler;
import classes.Project;

public class AddProjectPanel extends Activity {
    private Button addNewProjectButton;
    private Button cancelAddNewProject;

    private EditText projectNameTextField;
    private CheckBox projectTasksFlag;
    private CheckBox projectDescriptionFlag;
    private CheckBox projectIdeasFlag;
    private CheckBox projectScriptFlag;



    SQLiteDatabase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_project);

        projectNameTextField = findViewById(R.id.projectNameTextField);
        projectTasksFlag = findViewById(R.id.projectTasksFlag);
        projectDescriptionFlag = findViewById(R.id.projectDescriptionFlag);
        projectIdeasFlag = findViewById(R.id.projectIdeasFlag);
        projectScriptFlag = findViewById(R.id.projectScriptFlag);

        addNewProjectButton = findViewById(R.id.addNewProject);

        addNewProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!projectNameTextField.getText().toString().equals("")){
                    appDataBase = openOrCreateDatabase("projectData.db", MODE_PRIVATE, null);
                    ContentValues row = new ContentValues();
                    row.put("Name", projectNameTextField.getText().toString());
                    row.put("Tasks", projectTasksFlag.isChecked());
                    row.put("Description", projectDescriptionFlag.isChecked());
                    row.put("Ideas", projectIdeasFlag.isChecked());
                    row.put("Script", projectScriptFlag.isChecked());
                    appDataBase.insert("Projects", null, row);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });


        cancelAddNewProject = findViewById(R.id.cancelAddNewProject);
        cancelAddNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


    }

}
