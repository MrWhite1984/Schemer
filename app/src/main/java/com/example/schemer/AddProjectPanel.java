package com.example.schemer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

    //Add and cancelAdd project buttons
    private Button addNewProjectButton;
    private Button cancelAddNewProject;

    //Project parametrs
    private EditText projectNameTextField;
    private CheckBox projectTasksFlag;
    private CheckBox projectDescriptionFlag;
    private CheckBox projectIdeasFlag;
    private CheckBox projectScriptFlag;

    //DB
    SQLiteDatabase appDataBase;
    public static int quantityOfRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_project);

        //Project parametrs init
        projectNameTextField = findViewById(R.id.projectNameTextField);
        projectTasksFlag = findViewById(R.id.projectTasksFlag);
        projectDescriptionFlag = findViewById(R.id.projectDescriptionFlag);
        projectIdeasFlag = findViewById(R.id.projectIdeasFlag);
        projectScriptFlag = findViewById(R.id.projectScriptFlag);

        //Add new project button init
        addNewProjectButton = findViewById(R.id.addNewProject);

        //AddProjectButton code
        //DB data adding
        addNewProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!projectNameTextField.getText().toString().equals("")){
                    appDataBase = openOrCreateDatabase("projectData.db", MODE_PRIVATE, null);
                    ContentValues row = new ContentValues();
                    row.put("ID", quantityOfRecords +1);
                    row.put("Name", projectNameTextField.getText().toString());
                    row.put("Tasks", projectTasksFlag.isChecked());
                    row.put("Description", projectDescriptionFlag.isChecked());
                    row.put("Ideas", projectIdeasFlag.isChecked());
                    row.put("Script", projectScriptFlag.isChecked());
                    appDataBase.insert("Projects", null, row);
                    if(projectDescriptionFlag.isChecked()){
                        row = new ContentValues();
                        row.put("ID", appDataBase.rawQuery("SELECT * FROM Descriptions", null).getCount() + 1);
                        row.put("PID", quantityOfRecords +1);
                        row.put("Data", "");
                        appDataBase.insert("Descriptions", null, row);
                    }
                    if(projectScriptFlag.isChecked()){
                        row = new ContentValues();
                        row.put("ID", appDataBase.rawQuery("SELECT * FROM Scripts", null).getCount() + 1);
                        row.put("PID", quantityOfRecords +1);
                        row.put("Data", "");
                        appDataBase.insert("Scripts", null, row);
                    }
                    finish();
                }
            }
        });

        //CancelAddProjectButton code
        cancelAddNewProject = findViewById(R.id.cancelAddNewProject);
        cancelAddNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

}
