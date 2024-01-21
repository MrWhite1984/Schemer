package com.example.schemer;

import static classes.ButtonCreator.DropButton;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import classes.ButtonCreator;
import classes.DBHandler;
import classes.Project;

public class MainActivity extends Activity {

    //EditTexts
    private EditText searchProjectTextField;

    //Buttons
    private Button addProjectButton;
    private Button projectButtonExample;

    //LinearLayout
    private LinearLayout content;

    //Sensor
    private SensorManager sm;
    private Sensor s;
    private SensorEventListener sv;

    //DataBase
    SQLiteDatabase appDataBase;
    private Cursor appData;


    private List<String> projectsNames = new ArrayList<String>();

    private boolean dataHided;
    private boolean positionMark1;
    private boolean positionMark2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SearcTextField
        //searchProjectTextField = findViewById(R.id.searchProjectTextField);

        dataHided = false;
        //
        projectButtonExample = findViewById(R.id.projectButtonExample);
        content = findViewById(R.id.content);

        //DB creation
        appDataBase = openOrCreateDatabase("projectData.db", MODE_PRIVATE, null);
        appDataBase = DBHandler.checkingAndUpdatingTheDatabase(appDataBase);
        //deleteDatabase("projectData.db");
        //ButtonGeneration
        //appData = appDataBase.rawQuery("SELECT * FROM Projects", null);

        //Sensor
        positionMark1 = false;
        positionMark2 = false;
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sm != null)
            s = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sv = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_X,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix);

                float[] orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orientations);
                for(float orientation : orientations){
                    orientation = (float) (Math.toDegrees(orientation));
                }
                if(orientations[1] < -1){
                    if(!positionMark1 && !positionMark2){
                        positionMark2 = true;
                        positionMark1 = true;
                    }

                    if(!dataHided && positionMark2){
                        content.removeAllViews();
                        dataHided = true;
                    }
                    else if(!positionMark1 && positionMark2){
                        ButtonGenerator();
                        dataHided = false;
                        positionMark2 = false;
                        positionMark1 = true;
                    }
                }
                else {
                    positionMark1 = false;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //Set ProjectButtonExample Visibility None
        projectButtonExample.setVisibility(View.GONE);

        addProjectButton = findViewById(R.id.addProjectButton);
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProjectPanel.quantityOfRecords = appData.getCount();
                startActivity(new Intent(getApplicationContext(), AddProjectPanel.class));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        sm.registerListener(sv, s, SensorManager.SENSOR_DELAY_FASTEST);
        ButtonGenerator();

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sv);
    }

    private void ButtonGenerator(){
        appData = appDataBase.rawQuery("SELECT * FROM Projects", null);
        content.removeAllViews();
        while (appData.moveToNext()){
            Button button = ButtonCreator.CreateButton(appData.getString(1), getApplicationContext(), appData.getInt(0));
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(content.getContext());
                    builder.setTitle("Вы уверены, что хотите удалить проект?");
                    builder.setMessage("Проект " + button.getText() + " будет удален");
                    builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            appDataBase.delete("Tasks", "PID = ?", new String[]{String.valueOf(button.getContentDescription())});
                            appDataBase.delete("Projects", "Name = ?", new String[]{button.getText().toString()});
                            DropButton(button, content);
                        }
                    });
                    builder.setNegativeButton("Нет", null);
                    builder.show();
                    return true;
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProjectCardPanel.projectName = button.getText().toString();
                    ProjectCardPanel.projectCode = Integer.parseInt(button.getContentDescription().toString());
                    ProjectCardPanel.appDataBase = appDataBase;
                    startActivity(new Intent(getApplicationContext(), ProjectCardPanel.class));
                }
            });
            content.addView(button);
        }
    }
}

